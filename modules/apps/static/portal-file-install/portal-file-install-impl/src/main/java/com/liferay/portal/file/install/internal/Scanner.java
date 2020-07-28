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

import java.io.Closeable;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

/**
 * @author Matthew Tambara
 */
public class Scanner implements Closeable {

	public static final String SUBDIR_MODE_JAR = "jar";

	public static final String SUBDIR_MODE_RECURSE = "recurse";

	public static final String SUBDIR_MODE_SKIP = "skip";

	public Scanner(File directory) {
		this(directory, null, null);
	}

	public Scanner(
		File directory, final String filterString, String subdirMode) {

		watchedDirectory = _canon(directory);

		if ((filterString != null) && (filterString.length() > 0)) {
			_filenameFilter = new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					Matcher matcher = _pattern.matcher(name);

					return matcher.matches();
				}

				private final Pattern _pattern = Pattern.compile(filterString);

			};
		}
		else {
			_filenameFilter = null;
		}

		if ((subdirMode == null) || SUBDIR_MODE_JAR.equals(subdirMode)) {
			jarSubdir = true;
		}
		else {
			jarSubdir = false;
		}

		skipSubdir = SUBDIR_MODE_SKIP.equals(subdirMode);
		_recurseSubdir = SUBDIR_MODE_RECURSE.equals(subdirMode);
	}

	@Override
	public void close() throws IOException {
	}

	public long getChecksum(File file) {
		Long checksum = storedChecksums.get(file);

		if (checksum != null) {
			return checksum;
		}

		return 0;
	}

	public void initialize(Map<File, Long> checksums) {
		storedChecksums.putAll(checksums);
	}

	public Set<File> scan(boolean reportImmediately) {
		File[] list = watchedDirectory.listFiles(_filenameFilter);

		Set<File> files = _processFiles(reportImmediately, list);

		return new TreeSet<>(files);
	}

	public void updateChecksum(File file) {
		if ((file != null) && storedChecksums.containsKey(file)) {
			long newChecksum = checksum(file);

			storedChecksums.put(file, newChecksum);
		}
	}

	protected static long checksum(File file) {
		CRC32 crc32 = new CRC32();

		_checksum(file, crc32);

		return crc32.getValue();
	}

	protected final boolean jarSubdir;
	protected final Map<File, Long> lastChecksums = new HashMap<>();
	protected final boolean skipSubdir;
	protected final Map<File, Long> storedChecksums = new HashMap<>();
	protected final File watchedDirectory;

	private static File _canon(File file) {
		try {
			return file.getCanonicalFile();
		}
		catch (IOException ioException) {
			return file;
		}
	}

	private static void _checksum(File file, CRC32 crc32) {
		String name = file.getName();

		crc32.update(name.getBytes());

		if (file.isFile()) {
			_checksum(file.lastModified(), crc32);
			_checksum(file.length(), crc32);
		}
		else if (file.isDirectory()) {
			File[] children = file.listFiles();

			if (children != null) {
				for (File child : children) {
					_checksum(child, crc32);
				}
			}
		}
	}

	private static void _checksum(long l, CRC32 crc32) {
		for (int i = 0; i < 8; i++) {
			crc32.update((int)(l & 0x000000ff));

			l >>= 8;
		}
	}

	private Set<File> _processFiles(boolean reportImmediately, File[] list) {
		if (list == null) {
			return new HashSet<>();
		}

		Set<File> files = new HashSet<>();

		Set<File> removed = new HashSet<>(storedChecksums.keySet());

		for (File file : list) {
			if (file.isDirectory()) {
				if (skipSubdir) {
					continue;
				}
				else if (_recurseSubdir) {
					files.addAll(
						_processFiles(
							reportImmediately,
							file.listFiles(_filenameFilter)));

					continue;
				}
			}

			long lastChecksum = 0;

			if (lastChecksums.get(file) != null) {
				lastChecksum = lastChecksums.get(file);
			}

			long storedChecksum = 0;

			if (storedChecksums.get(file) != null) {
				storedChecksum = storedChecksums.get(file);
			}

			long newChecksum = checksum(file);

			lastChecksums.put(file, newChecksum);

			// Only handle file when it does not change anymore and it has
			// changed since last reported

			if (((newChecksum == lastChecksum) || reportImmediately) &&
				(newChecksum != storedChecksum)) {

				storedChecksums.put(file, newChecksum);
				files.add(file);
			}

			removed.remove(file);
		}

		// Make sure we'll handle a file that has been deleted

		files.addAll(removed);

		for (File file : removed) {

			// Remove no longer used checksums

			lastChecksums.remove(file);
			storedChecksums.remove(file);
		}

		return files;
	}

	private final FilenameFilter _filenameFilter;
	private final boolean _recurseSubdir;

}