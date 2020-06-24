/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.file.install.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.file.install.FileInstaller;
import com.liferay.portal.file.install.internal.manifest.Clause;
import com.liferay.portal.file.install.internal.manifest.Parser;
import com.liferay.portal.file.install.internal.version.VersionRange;
import com.liferay.portal.file.install.internal.version.VersionTable;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;
import org.osgi.framework.Constants;
import org.osgi.framework.Version;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.osgi.framework.startlevel.FrameworkStartLevel;
import org.osgi.framework.wiring.BundleRevision;

/**
 * @author Matthew Tambara
 */
public class DirectoryWatcher extends Thread implements BundleListener {

	public static final String ACTIVE_LEVEL = "file.install.active.level";

	public static final String CONFIG_ENCODING = "file.install.configEncoding";

	public static final String DIR = "file.install.dir";

	public static final String DISABLE_CONFIG_SAVE =
		"file.install.disableConfigSave";

	public static final String ENABLE_CONFIG_SAVE =
		"file.install.enableConfigSave";

	public static final String FILENAME = "felix.fileinstall.filename";

	public static final String FILTER = "file.install.filter";

	public static final String FRAGMENT_SCOPE =
		"file.install.fragmentRefreshScope";

	public static final String LOG_DEFAULT = "file.install.log.default";

	public static final String LOG_JUL = "jul";

	public static final String LOG_LEVEL = "file.install.log.level";

	public static final String LOG_STDOUT = "stdout";

	public static final String NO_INITIAL_DELAY = "file.install.noInitialDelay";

	public static final String OPTIONAL_SCOPE =
		"file.install.optionalImportRefreshScope";

	public static final String POLL = "file.install.poll";

	public static final String SCOPE_ALL = "all";

	public static final String SCOPE_MANAGED = "managed";

	public static final String SCOPE_NONE = "none";

	public static final String START_LEVEL = "file.install.start.level";

	public static final String START_NEW_BUNDLES =
		"file.install.bundles.new.start";

	public static final String SUBDIR_MODE = "file.install.subdir.mode";

	public static final String TMPDIR = "file.install.tmpdir";

	public static final String UPDATE_WITH_LISTENERS =
		"file.install.bundles.updateWithListeners";

	public static final String USE_START_ACTIVATION_POLICY =
		"file.install.bundles.startActivationPolicy";

	public static final String USE_START_TRANSIENT =
		"file.install.bundles.startTransient";

	public static final String WEB_START_LEVEL = "file.install.web.start.level";

	public static String getThreadName(Map<String, String> properties) {
		String name = properties.get(DIR);

		if (name != null) {
			return name;
		}

		return "./load";
	}

	public DirectoryWatcher(
		FileInstallImplBundleActivator fileInstall,
		Map<String, String> properties, BundleContext bundleContext) {

		super("fileinstall-" + getThreadName(properties));

		_fileInstall = fileInstall;
		_properties = properties;
		_bundleContext = bundleContext;
		_systemBundle = bundleContext.getBundle(
			Constants.SYSTEM_BUNDLE_LOCATION);
		_poll = _getLong(properties, POLL, 2000);
		_logLevel = _getInt(
			properties, LOG_LEVEL, Util.getGlobalLogLevel(bundleContext));
		_watchedDirectory = _getFile(properties, DIR, new File("./load"));
		_verifyWatchedDir();
		_tmpDir = _getFile(properties, TMPDIR, null);
		_prepareTempDir();
		_startBundles = _getBoolean(properties, START_NEW_BUNDLES, true);
		_useStartTransient = _getBoolean(
			properties, USE_START_TRANSIENT, false);
		_useStartActivationPolicy = _getBoolean(
			properties, USE_START_ACTIVATION_POLICY, true);
		_filter = properties.get(FILTER);
		_noInitialDelay = _getBoolean(properties, NO_INITIAL_DELAY, false);
		_startLevel = _getInt(properties, START_LEVEL, 0);
		_activeLevel = _getInt(properties, ACTIVE_LEVEL, 0);
		_updateWithListeners = _getBoolean(
			properties, UPDATE_WITH_LISTENERS, false);
		_fragmentScope = properties.get(FRAGMENT_SCOPE);
		_optionalScope = properties.get(OPTIONAL_SCOPE);
		_webStartLevel = _getInt(properties, WEB_START_LEVEL, _startLevel);
		_bundleContext.addBundleListener(this);

		scanner = new Scanner(
			_watchedDirectory, _filter, properties.get(SUBDIR_MODE));
	}

	public void addFileInstaller(FileInstaller fileInstaller, long timeStamp) {
		if (_updateWithListeners) {
			for (Artifact artifact : _getArtifacts()) {
				long bundleId = artifact.getBundleId();

				if ((artifact.getFileInstaller() == null) && (bundleId > 0)) {
					Bundle bundle = _bundleContext.getBundle(bundleId);

					if ((bundle != null) &&
						(bundle.getLastModified() < timeStamp)) {

						File path = artifact.getPath();

						if (fileInstaller.canInstall(path) ||
							fileInstaller.canTransformURL(path)) {

							synchronized (_processingFailures) {
								_processingFailures.add(path);
							}
						}
					}
				}
			}
		}

		synchronized (this) {
			notifyAll();
		}
	}

	@Override
	public void bundleChanged(BundleEvent bundleEvent) {
		int type = bundleEvent.getType();

		if (type == BundleEvent.UNINSTALLED) {
			List<Artifact> artifacts = _getArtifacts();

			Iterator<?> iterator = artifacts.iterator();

			while (iterator.hasNext()) {
				Artifact artifact = (Artifact)iterator.next();

				Bundle bundle = bundleEvent.getBundle();

				long bundleId = bundle.getBundleId();

				if (artifact.getBundleId() == bundleId) {
					_log(
						Util.Logger.LOG_DEBUG,
						StringBundler.concat(
							"Bundle ", bundleId, " has been uninstalled"),
						null);

					iterator.remove();

					break;
				}
			}
		}

		if ((type == BundleEvent.INSTALLED) || (type == BundleEvent.RESOLVED) ||
			(type == BundleEvent.UNINSTALLED) ||
			(type == BundleEvent.UNRESOLVED) || (type == BundleEvent.UPDATED)) {

			_setStateChanged(true);
		}
	}

	public void close() {
		_bundleContext.removeBundleListener(this);

		interrupt();

		for (Artifact artifact : _getArtifacts()) {
			_deleteJaredDirectory(artifact);
		}

		try {
			scanner.close();
		}
		catch (IOException ioException) {
		}

		try {
			join(10000);
		}
		catch (InterruptedException interruptedException) {
		}
	}

	public Map<String, String> getProperties() {
		return _properties;
	}

	public void removeFileInstaller(FileInstaller fileInstaller) {
		for (Artifact artifact : _getArtifacts()) {
			if (artifact.getFileInstaller() == fileInstaller) {
				artifact.setFileInstaller(null);
			}
		}

		synchronized (this) {
			notifyAll();
		}
	}

	public void run() {
		Lock readLock = _fileInstall.getReadLock();

		try {
			readLock.lockInterruptibly();
		}
		catch (InterruptedException interruptedException) {
			Thread currentThread = Thread.currentThread();

			currentThread.interrupt();

			_log(
				Util.Logger.LOG_INFO,
				StringBundler.concat(
					"Watcher for ", _watchedDirectory,
					" exiting because of interruption."),
				interruptedException);

			return;
		}

		try {
			StringBundler sb = new StringBundler(33);

			sb.append(StringPool.OPEN_CURLY_BRACE);
			sb.append(POLL);
			sb.append(" (ms) = ");
			sb.append(_poll);
			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append(DIR);
			sb.append(" = ");
			sb.append(_watchedDirectory.getAbsolutePath());
			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append(LOG_LEVEL);
			sb.append(" = ");
			sb.append(_logLevel);
			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append(START_NEW_BUNDLES);
			sb.append(" = ");
			sb.append(_startBundles);
			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append(START_NEW_BUNDLES);
			sb.append(" = ");
			sb.append(_startBundles);
			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append(TMPDIR);
			sb.append(" = ");
			sb.append(_tmpDir);
			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append(FILTER);
			sb.append(" = ");
			sb.append(_filter);
			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append(START_LEVEL);
			sb.append(" = ");
			sb.append(_startLevel);
			sb.append(StringPool.CLOSE_CURLY_BRACE);

			_log(Util.Logger.LOG_DEBUG, sb.toString(), null);

			if (!_noInitialDelay) {
				try {
					Thread.sleep(_poll);
				}
				catch (InterruptedException interruptedException) {
					_log(
						Util.Logger.LOG_DEBUG,
						StringBundler.concat(
							"Watcher for ", _watchedDirectory,
							" was interrupted while waiting ", _poll,
							" milliseconds for initial directory scan."),
						interruptedException);

					return;
				}

				_initializeCurrentManagedBundles();
			}
		}
		finally {
			readLock.unlock();
		}

		while (!interrupted()) {
			try {
				FrameworkStartLevel frameworkStartLevel = _systemBundle.adapt(
					FrameworkStartLevel.class);

				if ((frameworkStartLevel.getStartLevel() >= _activeLevel) &&
					(_systemBundle.getState() == Bundle.ACTIVE)) {

					Set<File> files = scanner.scan(false);

					if (files != null) {
						_process(files);
					}
				}

				synchronized (this) {
					wait(_poll);
				}
			}
			catch (InterruptedException interruptedException) {
				interrupt();

				return;
			}
			catch (Throwable throwable) {
				try {
					_bundleContext.getBundle();
				}
				catch (IllegalStateException illegalStateException) {
					return;
				}

				_log(
					Util.Logger.LOG_ERROR,
					"In main loop, we have serious trouble", throwable);
			}
		}
	}

	public void start() {
		if (_noInitialDelay) {
			_log(Util.Logger.LOG_DEBUG, "Starting initial scan", null);

			_initializeCurrentManagedBundles();

			Set<File> files = scanner.scan(true);

			if (files != null) {
				try {
					_process(files);
				}
				catch (InterruptedException interruptedException) {
					throw new RuntimeException(interruptedException);
				}
			}
		}

		super.start();
	}

	public final Scanner scanner;

	protected void findBundlesWithFragmentsToRefresh(Set<Bundle> toRefresh) {
		Set<Bundle> fragments = new HashSet<>();

		Set<Bundle> bundles = getScopedBundles(_fragmentScope);

		for (Bundle bundle : toRefresh) {
			if (bundle.getState() != Bundle.UNINSTALLED) {
				Dictionary<String, String> headers = bundle.getHeaders(
					StringPool.BLANK);

				String hostHeader = headers.get(Constants.FRAGMENT_HOST);

				if (hostHeader != null) {
					Clause[] clauses = Parser.parseHeader(hostHeader);

					if ((clauses != null) && (clauses.length > 0)) {
						Clause clause = clauses[0];

						for (Bundle hostBundle : bundles) {
							String hostSymbolicName =
								hostBundle.getSymbolicName();

							if ((hostSymbolicName != null) &&
								Objects.equals(
									hostSymbolicName, clause.getName())) {

								String versionString = clause.getAttribute(
									Constants.BUNDLE_VERSION_ATTRIBUTE);

								if (versionString != null) {
									VersionRange versionRange =
										VersionRange.parseVersionRange(
											versionString);

									headers = hostBundle.getHeaders(
										StringPool.BLANK);

									if (versionRange.contains(
											VersionTable.getVersion(
												headers.get(
													Constants.
														BUNDLE_VERSION)))) {

										fragments.add(hostBundle);
									}
								}
								else {
									fragments.add(hostBundle);
								}
							}
						}
					}
				}
			}
		}

		toRefresh.addAll(fragments);
	}

	protected void findBundlesWithOptionalPackagesToRefresh(
		Set<Bundle> toRefresh) {

		Set<Bundle> bundles = getScopedBundles(_optionalScope);

		bundles.removeAll(toRefresh);

		if (bundles.isEmpty()) {
			return;
		}

		Map<Bundle, List<Clause>> imports = new HashMap<>();

		Iterator<Bundle> iterator = bundles.iterator();

		while (iterator.hasNext()) {
			Bundle bundle = iterator.next();

			Dictionary<String, String> header = bundle.getHeaders(
				StringPool.BLANK);

			String importsString = header.get(Constants.IMPORT_PACKAGE);

			List<Clause> importsList = getOptionalImports(importsString);

			if (importsList.isEmpty()) {
				iterator.remove();
			}
			else {
				imports.put(bundle, importsList);
			}
		}

		if (bundles.isEmpty()) {
			return;
		}

		List<Clause> exports = new ArrayList<>();

		for (Bundle bundle : toRefresh) {
			if (bundle.getState() != Bundle.UNINSTALLED) {
				Dictionary<String, String> headers = bundle.getHeaders(
					StringPool.BLANK);

				String exportsString = headers.get(Constants.EXPORT_PACKAGE);

				if (exportsString != null) {
					Clause[] exportsList = Parser.parseHeader(exportsString);

					Collections.addAll(exports, exportsList);
				}
			}
		}

		iterator = bundles.iterator();

		while (iterator.hasNext()) {
			Bundle bundle = iterator.next();

			List<Clause> importsList = imports.get(bundle);

			Iterator<Clause> importIterator = importsList.iterator();

			while (importIterator.hasNext()) {
				Clause importClause = importIterator.next();

				boolean matching = false;

				for (Clause exportClause : exports) {
					if (Objects.equals(
							importClause.getName(), exportClause.getName())) {

						String exportVersionString = exportClause.getAttribute(
							Constants.VERSION_ATTRIBUTE);

						String importVersionString = importClause.getAttribute(
							Constants.VERSION_ATTRIBUTE);

						Version exported = Version.emptyVersion;

						if (exportVersionString != null) {
							exported = Version.parseVersion(
								exportVersionString);
						}

						VersionRange imported = VersionRange.ANY_VERSION;

						if (importVersionString != null) {
							imported = VersionRange.parseVersionRange(
								importVersionString);
						}

						if (imported.contains(exported)) {
							matching = true;

							break;
						}
					}
				}

				if (!matching) {
					importIterator.remove();
				}
			}

			if (importsList.isEmpty()) {
				iterator.remove();
			}
		}

		toRefresh.addAll(bundles);
	}

	protected List<Clause> getOptionalImports(String importsString) {
		Clause[] imports = Parser.parseHeader(importsString);

		List<Clause> result = new LinkedList<>();

		for (Clause importClause : imports) {
			String resolution = importClause.getDirective(
				Constants.RESOLUTION_DIRECTIVE);

			if (Constants.RESOLUTION_OPTIONAL.equals(resolution)) {
				result.add(importClause);
			}
		}

		return result;
	}

	protected Set<Bundle> getScopedBundles(String scope) {
		if (SCOPE_NONE.equals(scope)) {
			return new HashSet<>();
		}
		else if (SCOPE_MANAGED.equals(scope)) {
			Set<Bundle> bundles = new HashSet<>();

			for (Artifact artifact : _getArtifacts()) {
				long bundleId = artifact.getBundleId();

				if (bundleId > 0) {
					Bundle bundle = _bundleContext.getBundle(bundleId);

					if (bundle != null) {
						bundles.add(bundle);
					}
				}
			}

			return bundles;
		}
		else {
			return new HashSet<>(Arrays.asList(_bundleContext.getBundles()));
		}
	}

	private void _deleteJaredDirectory(Artifact artifact) {
		File directory = artifact.getJaredDirectory();

		if ((directory != null) && !directory.equals(artifact.getPath()) &&
			!directory.delete()) {

			_log(
				Util.Logger.LOG_WARNING,
				"Unable to delete jared artifact: " +
					directory.getAbsolutePath(),
				null);
		}
	}

	private void _doProcess(Set<File> files) throws InterruptedException {
		List<FileInstaller> fileInstallers = _fileInstall.getFileInstallers();
		List<Artifact> deleted = new ArrayList<>();
		List<Artifact> modified = new ArrayList<>();
		List<Artifact> created = new ArrayList<>();

		synchronized (_processingFailures) {
			files.addAll(_processingFailures);
			_processingFailures.clear();
		}

		for (File file : files) {
			boolean exists = file.exists();

			Artifact artifact = _getArtifact(file);

			if (!exists) {
				if (artifact != null) {
					_deleteJaredDirectory(artifact);
					deleted.add(artifact);
				}
			}
			else {
				File jar = file;

				URL jaredUrl = null;

				try {
					URI uri = file.toURI();

					jaredUrl = uri.toURL();
				}
				catch (MalformedURLException malformedURLException) {
				}

				if (file.isDirectory()) {
					_prepareTempDir();

					try {
						jar = new File(_tmpDir, file.getName() + ".jar");

						Util.jarDir(file, jar);

						jaredUrl = new URL(
							JarDirUrlHandler.PROTOCOL, null, file.getPath());
					}
					catch (IOException ioException) {
						_log(
							Util.Logger.LOG_ERROR,
							"Unable to create jar for: " +
								file.getAbsolutePath(),
							ioException);

						continue;
					}
				}

				if (artifact != null) {
					artifact.setChecksum(scanner.getChecksum(file));

					if (artifact.getFileInstaller() == null) {
						FileInstaller fileInstaller = _findFileInstaller(
							jar, fileInstallers);

						if (fileInstaller == null) {
							synchronized (_processingFailures) {
								_processingFailures.add(file);
							}

							continue;
						}

						artifact.setFileInstaller(fileInstaller);
					}

					FileInstaller fileInstaller = artifact.getFileInstaller();

					if (!fileInstallers.contains(fileInstaller) ||
						!(fileInstaller.canInstall(jar) ||
						  fileInstaller.canTransformURL(jar))) {

						deleted.add(artifact);
					}
					else {
						artifact.setJaredDirectory(jar);
						artifact.setJaredUrl(jaredUrl);

						if (_transformArtifact(artifact)) {
							modified.add(artifact);
						}
						else {
							_deleteJaredDirectory(artifact);

							deleted.add(artifact);
						}
					}
				}
				else {
					FileInstaller fileInstaller = _findFileInstaller(
						jar, fileInstallers);

					if (fileInstaller == null) {
						synchronized (_processingFailures) {
							_processingFailures.add(file);
						}

						continue;
					}

					artifact = new Artifact();

					artifact.setPath(file);
					artifact.setJaredDirectory(jar);
					artifact.setJaredUrl(jaredUrl);
					artifact.setFileInstaller(fileInstaller);
					artifact.setChecksum(scanner.getChecksum(file));

					if (_transformArtifact(artifact)) {
						created.add(artifact);
					}
					else {
						_deleteJaredDirectory(artifact);
					}
				}
			}
		}

		Collection<Bundle> uninstalledBundles = _uninstall(deleted);

		Collection<Bundle> updatedBundles = _update(modified);

		Collection<Bundle> installedBundles = _install(created);

		if (!uninstalledBundles.isEmpty() || !updatedBundles.isEmpty() ||
			!installedBundles.isEmpty()) {

			Set<Bundle> toRefresh = new HashSet<>();

			toRefresh.addAll(uninstalledBundles);

			toRefresh.addAll(updatedBundles);

			toRefresh.addAll(installedBundles);

			findBundlesWithFragmentsToRefresh(toRefresh);

			findBundlesWithOptionalPackagesToRefresh(toRefresh);

			if (!toRefresh.isEmpty()) {
				_refresh(toRefresh);

				_setStateChanged(true);
			}
		}

		if (_startBundles) {
			FrameworkStartLevel frameworkStartLevel = _systemBundle.adapt(
				FrameworkStartLevel.class);

			int startLevel = frameworkStartLevel.getStartLevel();

			if (_isStateChanged() || (startLevel != _frameworkStartLevel)) {
				_frameworkStartLevel = startLevel;

				_startAllBundles();

				_delayedStart.addAll(installedBundles);
				_delayedStart.removeAll(uninstalledBundles);

				_startBundles(_delayedStart);

				_consistentlyFailingBundles.clear();
				_consistentlyFailingBundles.addAll(_delayedStart);

				_setStateChanged(false);
			}
		}
	}

	private FileInstaller _findFileInstaller(
		File artifact, List<FileInstaller> fileInstallers) {

		for (FileInstaller fileInstaller : fileInstallers) {
			if (fileInstaller.canInstall(artifact) ||
				fileInstaller.canTransformURL(artifact)) {

				return fileInstaller;
			}
		}

		return null;
	}

	private Artifact _getArtifact(File file) {
		synchronized (_currentManagedArtifacts) {
			return _currentManagedArtifacts.get(file);
		}
	}

	private List<Artifact> _getArtifacts() {
		synchronized (_currentManagedArtifacts) {
			return new ArrayList<>(_currentManagedArtifacts.values());
		}
	}

	private boolean _getBoolean(
		Map<String, String> properties, String property, boolean defaultValue) {

		String value = properties.get(property);

		if (value != null) {
			return Boolean.valueOf(value);
		}

		return defaultValue;
	}

	private File _getFile(
		Map<String, String> properties, String property, File defaultValue) {

		String value = properties.get(property);

		if (value != null) {
			return new File(value);
		}

		return defaultValue;
	}

	private int _getInt(
		Map<String, String> properties, String property, int defaultValue) {

		String value = properties.get(property);

		if (value != null) {
			try {
				return Integer.parseInt(value);
			}
			catch (Exception exception) {
				_log(
					Util.Logger.LOG_WARNING,
					StringBundler.concat(
						property, " set, but not a int: ", value),
					null);
			}
		}

		return defaultValue;
	}

	private long _getLong(
		Map<String, String> properties, String property, long defaultValue) {

		String value = properties.get(property);

		if (value != null) {
			try {
				return Long.parseLong(value);
			}
			catch (Exception exception) {
				_log(
					Util.Logger.LOG_WARNING,
					StringBundler.concat(
						property, " set, but not a long: ", value),
					null);
			}
		}

		return defaultValue;
	}

	private void _initializeCurrentManagedBundles() {
		Bundle[] bundles = _bundleContext.getBundles();

		URI uri = _watchedDirectory.toURI();

		uri = uri.normalize();

		String watchedDirPath = uri.getPath();

		Map<File, Long> checksums = new HashMap<>();

		Pattern filePattern = null;

		if ((_filter != null) && !_filter.isEmpty()) {
			filePattern = Pattern.compile(_filter);
		}

		for (Bundle bundle : bundles) {
			String location = bundle.getLocation();

			uri = null;

			try {
				uri = new URI(location);
				uri = uri.normalize();
			}
			catch (URISyntaxException uriSyntaxException) {
				File file = new File(location);

				uri = file.toURI();

				uri = uri.normalize();
			}

			String locationPath = uri.getPath();

			if (locationPath == null) {
				continue;
			}

			String path = null;

			if ((location != null) && locationPath.contains(watchedDirPath)) {
				String schemeSpecificPart = uri.getSchemeSpecificPart();

				if (uri.isOpaque() && (schemeSpecificPart != null)) {
					int lastIndexOfFileProtocol =
						schemeSpecificPart.lastIndexOf("file:");

					int offsetFileProtocol = 0;

					if (lastIndexOfFileProtocol >= 0) {
						offsetFileProtocol =
							lastIndexOfFileProtocol + "file:".length();
					}

					int firstIndexOfDollar = schemeSpecificPart.indexOf(
						StringPool.DOLLAR);

					int endOfPath = schemeSpecificPart.length();

					if (firstIndexOfDollar >= 0) {
						endOfPath = firstIndexOfDollar;
					}

					path = schemeSpecificPart.substring(
						offsetFileProtocol, endOfPath);
				}
				else {
					path = uri.getPath();
				}
			}

			if (path == null) {
				continue;
			}

			int index = path.lastIndexOf(CharPool.SLASH);

			if ((index != -1) && path.startsWith(watchedDirPath)) {
				String fileName = path.substring(index + 1);

				if (filePattern != null) {
					Matcher matcher = filePattern.matcher(fileName);

					if (!matcher.matches()) {
						continue;
					}
				}

				Artifact artifact = new Artifact();

				artifact.setBundleId(bundle.getBundleId());
				artifact.setChecksum(Util.loadChecksum(bundle, _bundleContext));
				artifact.setFileInstaller(null);
				artifact.setPath(new File(path));

				_setArtifact(new File(path), artifact);

				checksums.put(new File(path), artifact.getChecksum());
			}
		}

		scanner.initialize(checksums);
	}

	private Bundle _install(Artifact artifact) {
		File path = artifact.getPath();

		Bundle bundle = null;

		AtomicBoolean modified = new AtomicBoolean();

		try {
			FileInstaller fileInstaller = artifact.getFileInstaller();

			long checksum = artifact.getChecksum();

			if (fileInstaller.canInstall(path)) {
				fileInstaller.install(path);
			}
			else if (fileInstaller.canTransformURL(path)) {
				Artifact badArtifact = _installationFailures.get(path);

				if ((badArtifact != null) &&
					(badArtifact.getChecksum() == checksum)) {

					return null;
				}

				URL transformed = artifact.getTransformedUrl();

				String location = transformed.toString();

				try (BufferedInputStream inputStream = new BufferedInputStream(
						transformed.openStream())) {

					bundle = _installOrUpdateBundle(
						location, inputStream, checksum, modified);

					artifact.setBundleId(bundle.getBundleId());
				}
			}

			_installationFailures.remove(path);
			_setArtifact(path, artifact);
		}
		catch (Exception exception) {
			_log(
				Util.Logger.LOG_ERROR, "Failed to install artifact: " + path,
				exception);

			_installationFailures.put(path, artifact);
		}

		if (modified.get()) {
			return bundle;
		}

		return null;
	}

	private Collection<Bundle> _install(Collection<Artifact> artifacts) {
		List<Bundle> bundles = new ArrayList<>();

		for (Artifact artifact : artifacts) {
			Bundle bundle = _install(artifact);

			if (bundle != null) {
				bundles.add(bundle);
			}
		}

		return bundles;
	}

	private Bundle _installOrUpdateBundle(
			String bundleLocation, BufferedInputStream bufferedInputStream,
			long checksum, AtomicBoolean modified)
		throws Exception {

		Bundle bundle = _bundleContext.getBundle(bundleLocation);

		if ((bundle != null) &&
			(Util.loadChecksum(bundle, _bundleContext) != checksum)) {

			bundle.update(bufferedInputStream);

			Util.storeChecksum(bundle, checksum, _bundleContext);

			return bundle;
		}

		bufferedInputStream.mark(256 * 1024);

		try (JarInputStream jarInputStream = new JarInputStream(
				bufferedInputStream)) {

			Manifest manifest = jarInputStream.getManifest();

			if (manifest == null) {
				throw new BundleException(
					StringBundler.concat(
						"The bundle ", bundleLocation, " does not have a ",
						"META-INF/MANIFEST.MF! Make sure, META-INF and ",
						"MANIFEST.MF are the first 2 entries in your JAR!"));
			}

			Attributes attributes = manifest.getMainAttributes();

			String symbolicName = attributes.getValue(
				Constants.BUNDLE_SYMBOLICNAME);

			String versionString = attributes.getValue(
				Constants.BUNDLE_VERSION);

			Version version = Version.emptyVersion;

			if (versionString != null) {
				version = Version.parseVersion(versionString);
			}

			for (Bundle currentBundle : _bundleContext.getBundles()) {
				String currentSymbolicName = currentBundle.getSymbolicName();

				if ((currentSymbolicName != null) &&
					Objects.equals(currentSymbolicName, symbolicName)) {

					Dictionary<String, String> headers =
						currentBundle.getHeaders(StringPool.BLANK);

					versionString = headers.get(Constants.BUNDLE_VERSION);

					Version currentVersion = Version.emptyVersion;

					if (versionString != null) {
						currentVersion = Version.parseVersion(versionString);
					}

					if (version.equals(currentVersion)) {
						bufferedInputStream.reset();

						if (Util.loadChecksum(currentBundle, _bundleContext) !=
								checksum) {

							_log(
								Util.Logger.LOG_WARNING,
								StringBundler.concat(
									"A bundle with the same symbolic name (",
									symbolicName, ") and version (",
									versionString, ") is already installed. ",
									"Updating this bundle instead."),
								null);

							_stopTransient(currentBundle);

							Util.storeChecksum(
								currentBundle, checksum, _bundleContext);

							currentBundle.update(bufferedInputStream);

							modified.set(true);
						}

						return currentBundle;
					}
				}
			}

			bufferedInputStream.reset();
			Util.log(
				_bundleContext, Util.Logger.LOG_INFO,
				StringBundler.concat(
					"Installing bundle ", symbolicName, " / ", version),
				null);

			bundle = _bundleContext.installBundle(
				bundleLocation, bufferedInputStream);

			if (bundle.getState() == Bundle.UNINSTALLED) {
				return bundle;
			}

			Util.storeChecksum(bundle, checksum, _bundleContext);

			modified.set(true);

			Dictionary<String, String> headers = bundle.getHeaders("");

			String header = headers.get("Web-ContextPath");

			BundleStartLevel bundleStartLevel = bundle.adapt(
				BundleStartLevel.class);

			if (header != null) {
				bundleStartLevel.setStartLevel(_webStartLevel);
			}
			else if (_startLevel != 0) {
				bundleStartLevel.setStartLevel(_startLevel);
			}

			return bundle;
		}
	}

	private boolean _isFragment(Bundle bundle) {
		BundleRevision bundleRevision = bundle.adapt(BundleRevision.class);

		if ((bundleRevision.getTypes() & BundleRevision.TYPE_FRAGMENT) != 0) {
			return true;
		}

		return false;
	}

	private boolean _isStateChanged() {
		return _stateChanged.get();
	}

	private void _log(int messageLevel, String message, Throwable throwable) {
		Util.log(_bundleContext, _logLevel, messageLevel, message, throwable);
	}

	private void _prepareDir(File dir) {
		if (!dir.exists() && !dir.mkdirs()) {
			_log(
				Util.Logger.LOG_ERROR,
				StringBundler.concat(
					"Cannot create folder ", dir,
					". Is the folder write-protected?"),
				null);

			throw new RuntimeException("Cannot create folder: " + dir);
		}

		if (!dir.isDirectory()) {
			_log(
				Util.Logger.LOG_ERROR,
				StringBundler.concat(
					"Cannot use ", dir, " because it is not a directory"),
				null);

			throw new RuntimeException(
				"Cannot start FileInstall using something that is not a " +
					"directory");
		}
	}

	private void _prepareTempDir() {
		if (_tmpDir == null) {
			File javaIoTmpdir = new File(System.getProperty("java.io.tmpdir"));

			if (!javaIoTmpdir.exists() && !javaIoTmpdir.mkdirs()) {
				throw new IllegalStateException(
					"Unable to create temporary directory " + javaIoTmpdir);
			}

			Random random = new Random();

			while (_tmpDir == null) {
				File file = new File(
					javaIoTmpdir,
					"fileinstall-" + String.valueOf(random.nextLong()));

				if (!file.exists() && file.mkdirs()) {
					_tmpDir = file;

					_tmpDir.deleteOnExit();
				}
			}
		}
		else {
			_prepareDir(_tmpDir);
		}
	}

	private void _process(Set<File> files) throws InterruptedException {
		Lock readLock = _fileInstall.getReadLock();

		readLock.lockInterruptibly();

		try {
			_doProcess(files);
		}
		finally {
			readLock.unlock();
		}
	}

	private void _refresh(Collection<Bundle> bundles)
		throws InterruptedException {

		FileInstallImplBundleActivator.refresh(_systemBundle, bundles);
	}

	private void _removeArtifact(File file) {
		synchronized (_currentManagedArtifacts) {
			_currentManagedArtifacts.remove(file);
		}
	}

	private void _setArtifact(File file, Artifact artifact) {
		synchronized (_currentManagedArtifacts) {
			_currentManagedArtifacts.put(file, artifact);
		}
	}

	private void _setStateChanged(boolean changed) {
		_stateChanged.set(changed);
	}

	private void _startAllBundles() {
		FrameworkStartLevel frameworkStartLevel = _systemBundle.adapt(
			FrameworkStartLevel.class);

		Set<Bundle> bundles = new LinkedHashSet<>();

		for (Artifact artifact : _getArtifacts()) {
			long bundleId = artifact.getBundleId();

			if (bundleId > 0) {
				Bundle bundle = _bundleContext.getBundle(bundleId);

				if (bundle != null) {
					int state = bundle.getState();

					BundleStartLevel bundleStartLevel = bundle.adapt(
						BundleStartLevel.class);

					if ((state != Bundle.STARTING) &&
						(state != Bundle.ACTIVE) &&
						(_useStartTransient ||
						 bundleStartLevel.isPersistentlyStarted()) &&
						(frameworkStartLevel.getStartLevel() >=
							bundleStartLevel.getStartLevel())) {

						bundles.add(bundle);
					}
				}
			}
		}

		_startBundles(bundles);
	}

	private boolean _startBundle(Bundle bundle, boolean logFailures) {
		BundleStartLevel bundleStartLevel = bundle.adapt(
			BundleStartLevel.class);

		if (_startBundles && (bundle.getState() != Bundle.UNINSTALLED) &&
			!_isFragment(bundle) &&
			(_frameworkStartLevel >= bundleStartLevel.getStartLevel())) {

			try {
				int options = 0;

				if (_useStartTransient) {
					options = Bundle.START_TRANSIENT;
				}

				if (_useStartActivationPolicy) {
					options |= Bundle.START_ACTIVATION_POLICY;
				}

				bundle.start(options);

				_log(
					Util.Logger.LOG_INFO,
					"Started bundle: " + bundle.getLocation(), null);

				return true;
			}
			catch (IllegalStateException illegalStateException) {
				if (bundle.getState() == Bundle.UNINSTALLED) {
					return true;
				}

				throw illegalStateException;
			}
			catch (BundleException bundleException) {
				if (logFailures) {
					_log(
						Util.Logger.LOG_ERROR,
						"Error while starting bundle: " + bundle.getLocation(),
						bundleException);
				}
			}
		}

		return false;
	}

	private void _startBundles(Set<Bundle> bundles) {
		Iterator<Bundle> iterator = bundles.iterator();

		while (iterator.hasNext()) {
			Bundle bundle = iterator.next();

			if (_startBundle(bundle, true)) {
				iterator.remove();
			}
		}
	}

	private void _stopTransient(Bundle bundle) throws BundleException {
		if (_startBundles && !_isFragment(bundle)) {
			bundle.stop(Bundle.STOP_TRANSIENT);
		}
	}

	private boolean _transformArtifact(Artifact artifact) {
		FileInstaller fileInstaller = artifact.getFileInstaller();

		File file = artifact.getPath();

		if (fileInstaller.canTransformURL(file)) {
			try {
				URL url = artifact.getJaredUrl();

				URL transformed = fileInstaller.transform(url);

				if (transformed != null) {
					artifact.setTransformedUrl(transformed);

					return true;
				}
			}
			catch (Exception exception) {
				_log(
					Util.Logger.LOG_WARNING,
					"Unable to transform artifact: " + file.getAbsolutePath(),
					exception);
			}

			return false;
		}

		return true;
	}

	private Bundle _uninstall(Artifact artifact) {
		Bundle bundle = null;

		try {
			File path = artifact.getPath();

			if (artifact.getFileInstaller() == null) {
				artifact.setFileInstaller(
					_findFileInstaller(path, _fileInstall.getFileInstallers()));
			}

			_removeArtifact(path);

			FileInstaller fileInstaller = artifact.getFileInstaller();

			long bundleId = artifact.getBundleId();

			if (fileInstaller.canInstall(path)) {
				fileInstaller.uninstall(path);
			}
			else if (bundleId != 0) {
				bundle = _bundleContext.getBundle(bundleId);

				if (bundle == null) {
					StringBundler sb = new StringBundler(5);

					sb.append("Failed to uninstall bundle: ");
					sb.append(path);
					sb.append(" with id: ");
					sb.append(bundleId);
					sb.append(". The bundle has already been uninstalled");

					_log(Util.Logger.LOG_WARNING, sb.toString(), null);

					return null;
				}

				_log(
					Util.Logger.LOG_INFO,
					StringBundler.concat(
						"Uninstalling bundle ", bundle.getBundleId(), " (",
						bundle.getSymbolicName(), StringPool.CLOSE_PARENTHESIS),
					null);

				bundle.uninstall();
			}
		}
		catch (Exception exception) {
			_log(
				Util.Logger.LOG_WARNING,
				"Failed to uninstall artifact: " + artifact.getPath(),
				exception);
		}

		return bundle;
	}

	private Collection<Bundle> _uninstall(Collection<Artifact> artifacts) {
		List<Bundle> bundles = new ArrayList<>();

		for (Artifact artifact : artifacts) {
			Bundle bundle = _uninstall(artifact);

			if (bundle != null) {
				bundles.add(bundle);
			}
		}

		return bundles;
	}

	private Bundle _update(Artifact artifact) {
		Bundle bundle = null;

		try {
			File path = artifact.getPath();

			FileInstaller fileInstaller = artifact.getFileInstaller();

			long checksum = artifact.getChecksum();

			long bundleId = artifact.getBundleId();

			if (fileInstaller.canInstall(path)) {
				fileInstaller.update(path);
			}
			else if (fileInstaller.canTransformURL(path)) {
				bundle = _bundleContext.getBundle(bundleId);

				if (bundle == null) {
					StringBundler sb = new StringBundler(5);

					sb.append("Failed to update bundle: ");
					sb.append(path);
					sb.append(" with ID ");
					sb.append(bundleId);
					sb.append(". The bundle has been uninstalled");

					_log(Util.Logger.LOG_WARNING, sb.toString(), null);

					return null;
				}

				Util.log(
					_bundleContext, Util.Logger.LOG_INFO,
					StringBundler.concat(
						"Updating bundle ", bundle.getSymbolicName(), " / ",
						bundle.getVersion()),
					null);

				_stopTransient(bundle);

				Util.storeChecksum(bundle, checksum, _bundleContext);

				URL transformed = artifact.getTransformedUrl();

				if (transformed != null) {
					try (InputStream inputStream = transformed.openStream()) {
						bundle.update(inputStream);
					}
				}
				else {
					try (InputStream inputStream = new FileInputStream(path)) {
						bundle.update(inputStream);
					}
				}
			}
		}
		catch (Throwable throwable) {
			_log(
				Util.Logger.LOG_WARNING,
				"Failed to update artifact " + artifact.getPath(), throwable);
		}

		return bundle;
	}

	private Collection<Bundle> _update(Collection<Artifact> artifacts) {
		List<Bundle> bundles = new ArrayList<>();

		for (Artifact artifact : artifacts) {
			Bundle bundle = _update(artifact);

			if (bundle != null) {
				bundles.add(bundle);
			}
		}

		return bundles;
	}

	private void _verifyWatchedDir() {
		if (!_watchedDirectory.exists()) {
			_log(
				Util.Logger.LOG_WARNING,
				_watchedDirectory + " does not exist, please create it.", null);
		}
		else if (!_watchedDirectory.isDirectory()) {
			_log(
				Util.Logger.LOG_ERROR,
				StringBundler.concat(
					"Cannot use ", _watchedDirectory,
					" because it is not a directory"),
				null);

			throw new RuntimeException(
				StringBundler.concat(
					"File Install cannot monitor ", _watchedDirectory,
					" because it is not a directory"));
		}
	}

	private final int _activeLevel;
	private final BundleContext _bundleContext;
	private final Set<Bundle> _consistentlyFailingBundles = new HashSet<>();
	private final Map<File, Artifact> _currentManagedArtifacts =
		new HashMap<>();
	private final Set<Bundle> _delayedStart = new HashSet<>();
	private final FileInstallImplBundleActivator _fileInstall;
	private final String _filter;
	private final String _fragmentScope;
	private int _frameworkStartLevel;
	private final Map<File, Artifact> _installationFailures = new HashMap<>();
	private final int _logLevel;
	private final boolean _noInitialDelay;
	private final String _optionalScope;
	private final long _poll;
	private final Set<File> _processingFailures = new HashSet<>();
	private final Map<String, String> _properties;
	private final boolean _startBundles;
	private final int _startLevel;
	private final AtomicBoolean _stateChanged = new AtomicBoolean();
	private final Bundle _systemBundle;
	private File _tmpDir;
	private final boolean _updateWithListeners;
	private final boolean _useStartActivationPolicy;
	private final boolean _useStartTransient;
	private final File _watchedDirectory;
	private final int _webStartLevel;

}