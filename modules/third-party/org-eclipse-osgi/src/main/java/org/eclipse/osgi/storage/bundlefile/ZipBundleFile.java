/*******************************************************************************
 * Copyright (c) 2005, 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Rob Harrop - SpringSource Inc. (bug 253942)
 *******************************************************************************/

package org.eclipse.osgi.storage.bundlefile;

import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.eclipse.osgi.container.ModuleContainerAdaptor.ContainerEvent;
import org.eclipse.osgi.container.ModuleRevision;
import org.eclipse.osgi.framework.log.FrameworkLogEntry;
import org.eclipse.osgi.internal.debug.Debug;
import org.eclipse.osgi.internal.framework.EquinoxContainer;
import org.eclipse.osgi.internal.messages.Msg;
import org.eclipse.osgi.storage.BundleInfo;
import org.eclipse.osgi.util.NLS;

/**
 * A BundleFile that uses a ZipFile as it base file.
 */
public class ZipBundleFile extends BundleFile {

	// A reentrant lock is used here (instead of intrinsic synchronization)
	// to allow the lock conditional held
	// see lockOpen() and getZipFile()
	private final ReentrantLock openLock = new ReentrantLock();
	private final Condition refCondition = openLock.newCondition();

	private final MRUBundleFileList mruList;

	private final BundleInfo.Generation generation;

	private final Debug debug;
	/**
	 * The zip file
	 */
	private volatile ZipFile zipFile;
	/**
	 * The closed flag
	 */
	private volatile boolean closed = true;

	private int referenceCount = 0;

	public ZipBundleFile(File basefile, BundleInfo.Generation generation, MRUBundleFileList mruList, Debug debug) throws IOException {
		super(basefile);
		if (!BundleFile.secureAction.exists(basefile))
			throw new IOException(NLS.bind(Msg.ADAPTER_FILEEXIST_EXCEPTION, basefile));
		this.debug = debug;
		this.generation = generation;
		this.closed = true;
		this.mruList = mruList;
	}

	/**
	 * Checks if the zip file is open
	 * @return true if the zip file is open
	 */
	private boolean lockOpen() {
		try {
			return getZipFile(true) != null;
		} catch (IOException e) {
			if (generation != null) {
				ModuleRevision r = generation.getRevision();
				if (r != null) {
					ContainerEvent eventType = ContainerEvent.ERROR;
					// If the revision has been removed from the list of revisions then it has been deleted
					// because the bundle has been uninstalled or updated
					if (!r.getRevisions().getModuleRevisions().contains(r)) {
						// instead of filling the log with errors about missing files from 
						// uninstalled/updated bundles just give it an info level
						eventType = ContainerEvent.INFO;
					}
					generation.getBundleInfo().getStorage().getAdaptor().publishContainerEvent(eventType, r.getRevisions().getModule(), e);
				}
			}
			// TODO not sure if throwing a runtime exception is better
			// throw new RuntimeException("Failed to open bundle file.", e);
			return false;
		}
	}

	/**
	 * Returns an open ZipFile for this bundle file.  If an open
	 * ZipFile does not exist then a new one is created and
	 * returned.
	 * @param keepLock true if the open zip lock should be retained
	 * @return an open ZipFile for this bundle
	 * @throws IOException
	 */
	private ZipFile getZipFile(boolean keepLock) throws IOException {
		openLock.lock();
		try {
			if (closed) {
				boolean needBackPressure = mruListAdd();
				if (needBackPressure) {
					// release lock before applying back pressure
					openLock.unlock();
					try {
						mruListApplyBackPressure();
					} finally {
						// get lock back after back pressure
						openLock.lock();
					}
				}
				// check close again after getting open lock again
				if (closed) {
					// always add again if back pressure was applied in case
					// the bundle file got removed while releasing the open lock
					if (needBackPressure) {
						mruListAdd();
					}
					// This can throw an IO exception resulting in closed remaining true on exit
					zipFile = BundleFile.secureAction.getZipFile(this.basefile);
					closed = false;
				}
			} else {
				mruListUse();
			}
			return zipFile;
		} finally {
			if (!keepLock || closed) {
				openLock.unlock();
			}
		}
	}

	/**
	* Returns a ZipEntry for the bundle file. Must be called while holding the open lock.
	* This method does not ensure that the ZipFile is opened. Callers may need to call getZipfile() prior to calling this 
	* method.
	* @param path the path to an entry
	* @return a ZipEntry or null if the entry does not exist
	*/
	private ZipEntry getZipEntry(String path) {
		if (path.length() > 0 && path.charAt(0) == '/')
			path = path.substring(1);
		ZipEntry entry = zipFile.getEntry(path);
		if (entry != null && entry.getSize() == 0 && !entry.isDirectory()) {
			// work around the directory bug see bug 83542
			ZipEntry dirEntry = zipFile.getEntry(path + '/');
			if (dirEntry != null)
				entry = dirEntry;
		}
		return entry;
	}

	/**
	 * Extracts a directory and all sub content to disk
	 * @param dirName the directory name to extract
	 * @return the File used to extract the content to.  A value
	 * of <code>null</code> is returned if the directory to extract does 
	 * not exist or if content extraction is not supported.
	 */
	File extractDirectory(String dirName) {
		if (!lockOpen()) {
			return null;
		}
		try {
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				String entryPath = entries.nextElement().getName();
				if (entryPath.startsWith(dirName) && !entryPath.endsWith("/")) //$NON-NLS-1$
					getFile(entryPath, false);
			}
			return getExtractFile(dirName);
		} finally {
			openLock.unlock();
		}
	}

	private File getExtractFile(String entryName) {
		if (generation == null)
			return null;
		String path = ".cp"; /* put all these entries in this subdir *///$NON-NLS-1$
		String name = entryName.replace('/', File.separatorChar);
		if ((name.length() > 1) && (name.charAt(0) == File.separatorChar)) /* if name has a leading slash */
			path = path.concat(name);
		else
			path = path + File.separator + name;
		return generation.getExtractFile(path);
	}

	public File getFile(String entry, boolean nativeCode) {
		if (!lockOpen()) {
			return null;
		}
		try {
			ZipEntry zipEntry = getZipEntry(entry);
			if (zipEntry == null)
				return null;

			try {
				File nested = getExtractFile(zipEntry.getName());
				if (nested != null) {
					if (nested.exists()) {
						/* the entry is already cached */
						if (debug.DEBUG_BUNDLE_FILE)
							Debug.println("File already present: " + nested.getPath()); //$NON-NLS-1$
						if (nested.isDirectory())
							// must ensure the complete directory is extracted (bug 182585)
							extractDirectory(zipEntry.getName());
					} else {
						if (zipEntry.getName().endsWith("/")) { //$NON-NLS-1$
							nested.mkdirs();
							if (!nested.isDirectory()) {
								if (debug.DEBUG_BUNDLE_FILE)
									Debug.println("Unable to create directory: " + nested.getPath()); //$NON-NLS-1$
								throw new IOException(NLS.bind(Msg.ADAPTOR_DIRECTORY_CREATE_EXCEPTION, nested.getAbsolutePath()));
							}
							extractDirectory(zipEntry.getName());
						} else {
							InputStream in = zipFile.getInputStream(zipEntry);
							if (in == null)
								return null;
							generation.storeContent(nested, in, nativeCode);
						}
					}

					return nested;
				}
			} catch (IOException e) {
				if (debug.DEBUG_BUNDLE_FILE)
					Debug.printStackTrace(e);
				generation.getBundleInfo().getStorage().getLogServices().log(EquinoxContainer.NAME, FrameworkLogEntry.ERROR, "Unable to extract content: " + generation.getRevision() + ": " + entry, e); //$NON-NLS-1$ //$NON-NLS-2$
			}
		} finally {
			openLock.unlock();
		}
		return null;
	}

	public boolean containsDir(String dir) {
		if (!lockOpen()) {
			return false;
		}
		try {
			if (dir == null)
				return false;

			if (dir.length() == 0)
				return true;

			if (dir.charAt(0) == '/') {
				if (dir.length() == 1)
					return true;
				dir = dir.substring(1);
			}

			if (dir.length() > 0 && dir.charAt(dir.length() - 1) != '/')
				dir = dir + '/';

			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			ZipEntry zipEntry;
			String entryPath;
			while (entries.hasMoreElements()) {
				zipEntry = entries.nextElement();
				entryPath = zipEntry.getName();
				if (entryPath.startsWith(dir)) {
					return true;
				}
			}
		} finally {
			openLock.unlock();
		}
		return false;
	}

	public BundleEntry getEntry(String path) {
		if (!lockOpen()) {
			return null;
		}
		try {
			ZipEntry zipEntry = getZipEntry(path);
			if (zipEntry == null) {
				if (path.length() == 0 || path.charAt(path.length() - 1) == '/') {
					// this is a directory request lets see if any entries exist in this directory
					if (containsDir(path))
						return new DirZipBundleEntry(this, path);
				}
				return null;
			}

			return new ZipBundleEntry(zipEntry, this);
		} finally {
			openLock.unlock();
		}
	}

	@Override
	public Enumeration<String> getEntryPaths(String path, boolean recurse) {
		if (!lockOpen()) {
			return null;
		}
		try {
			if (path == null)
				throw new NullPointerException();

			// Strip any leading '/' off of path.
			if (path.length() > 0 && path.charAt(0) == '/')
				path = path.substring(1);
			// Append a '/', if not already there, to path if not an empty string.
			if (path.length() > 0 && path.charAt(path.length() - 1) != '/')
				path = new StringBuilder(path).append("/").toString(); //$NON-NLS-1$

			LinkedHashSet<String> result = new LinkedHashSet<>();
			// Get all zip file entries and add the ones of interest.
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry zipEntry = entries.nextElement();
				String entryPath = zipEntry.getName();
				// Is the entry of possible interest? Note that 
				// string.startsWith("") == true.
				if (entryPath.startsWith(path)) {
					// If we get here, we know that the entry is either (1) equal to
					// path, (2) a file under path, or (3) a subdirectory of path.
					if (path.length() < entryPath.length()) {
						// If we get here, we know that entry is not equal to path.
						getEntryPaths(path, entryPath.substring(path.length()), recurse, result);
					}
				}
			}
			return result.size() == 0 ? null : Collections.enumeration(result);
		} finally {
			openLock.unlock();
		}
	}

	private void getEntryPaths(String path, String entry, boolean recurse, LinkedHashSet<String> entries) {
		if (entry.length() == 0)
			return;
		int slash = entry.indexOf('/');
		if (slash == -1)
			entries.add(path + entry);
		else {
			path = path + entry.substring(0, slash + 1);
			entries.add(path);
			if (recurse)
				getEntryPaths(path, entry.substring(slash + 1), true, entries);
		}
	}

	public void close() throws IOException {
		openLock.lock();
		try {
			if (!closed) {
				if (referenceCount > 0 && isMruListClosing()) {
					// there are some opened streams to this BundleFile still;
					// wait for them all to close because this is being closed by the MRUBundleFileList
					try {
						refCondition.await(1000, TimeUnit.MICROSECONDS); // timeout after 1 second
					} catch (InterruptedException e) {
						// do nothing for now ...
					}
					if (referenceCount != 0 || closed)
						// either another thread closed the bundle file or we timed waiting for all the reference inputstreams to close
						// If the referenceCount did not reach zero then this bundle file will remain open until the
						// bundle file is closed explicitly (i.e. bundle is updated/uninstalled or framework is shutdown)
						return;

				}
				closed = true;
				zipFile.close();
				mruListRemove();
				zipFile = null;
			}
		} finally {
			openLock.unlock();
		}
	}

	private boolean isMruListClosing() {
		return this.mruList != null && this.mruList.isClosing(this);
	}

	private boolean isMruEnabled() {
		return this.mruList != null && this.mruList.isEnabled();
	}

	private void mruListRemove() {
		if (this.mruList != null) {
			this.mruList.remove(this);
		}
	}

	private void mruListUse() {
		if (this.mruList != null) {
			mruList.use(this);
		}
	}

	private void mruListApplyBackPressure() {
		if (this.mruList != null) {
			this.mruList.applyBackpressure();
		}
	}

	private boolean mruListAdd() {
		if (this.mruList != null) {
			return mruList.add(this);
		}
		return false;
	}

	public void open() throws IOException {
		getZipFile(false);
	}

	void incrementReference() {
		openLock.lock();
		try {
			referenceCount += 1;
		} finally {
			openLock.unlock();
		}
	}

	void decrementReference() {
		openLock.lock();
		try {
			referenceCount = Math.max(0, referenceCount - 1);
			// only notify if the referenceCount is zero.
			if (referenceCount == 0)
				refCondition.signal();
		} finally {
			openLock.unlock();
		}
	}

	InputStream getInputStream(ZipEntry entry) throws IOException {
		if (!lockOpen()) {
			throw new IOException("Failed to open zip file."); //$NON-NLS-1$
		}
		try {
			InputStream zipStream = zipFile.getInputStream(entry);
			if (isMruEnabled()) {
				zipStream = new ZipBundleEntryInputStream(zipStream);
			}
			return zipStream;
		} finally {
			openLock.unlock();
		}
	}

	private class ZipBundleEntryInputStream extends FilterInputStream {

		private boolean streamClosed = false;

		public ZipBundleEntryInputStream(InputStream stream) {
			super(stream);
			incrementReference();
		}

		public int available() throws IOException {
			try {
				return super.available();
			} catch (IOException e) {
				throw enrichExceptionWithBaseFile(e);
			}
		}

		public void close() throws IOException {
			try {
				super.close();
			} catch (IOException e) {
				throw enrichExceptionWithBaseFile(e);
			} finally {
				synchronized (this) {
					if (streamClosed)
						return;
					streamClosed = true;
				}
				decrementReference();
			}
		}

		public int read() throws IOException {
			try {
				return super.read();
			} catch (IOException e) {
				throw enrichExceptionWithBaseFile(e);
			}
		}

		public int read(byte[] var0, int var1, int var2) throws IOException {
			try {
				return super.read(var0, var1, var2);
			} catch (IOException e) {
				throw enrichExceptionWithBaseFile(e);
			}
		}

		public int read(byte[] var0) throws IOException {
			try {
				return super.read(var0);
			} catch (IOException e) {
				throw enrichExceptionWithBaseFile(e);
			}
		}

		public void reset() throws IOException {
			try {
				super.reset();
			} catch (IOException e) {
				throw enrichExceptionWithBaseFile(e);
			}
		}

		public long skip(long var0) throws IOException {
			try {
				return super.skip(var0);
			} catch (IOException e) {
				throw enrichExceptionWithBaseFile(e);
			}
		}

		private IOException enrichExceptionWithBaseFile(IOException e) {
			return new IOException(getBaseFile().toString(), e);
		}
	}
}
