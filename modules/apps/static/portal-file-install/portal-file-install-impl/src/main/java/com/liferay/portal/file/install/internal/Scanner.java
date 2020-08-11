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

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

/**
 * @author Matthew Tambara
 */
public class Scanner {

	public static final String SUBDIR_MODE_RECURSE = "recurse";

	public Scanner(
		List<File> dirs, final String filterString, String subdirMode) {

		_watchedDirs = _canon(dirs);

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

		_recurseSubdir = SUBDIR_MODE_RECURSE.equals(subdirMode);
	}

	public long getChecksum(File file) {
		Long checksum = _storedChecksums.get(file);

		if (checksum != null) {
			return checksum;
		}

		return 0;
	}

	public void initialize(Map<File, Long> checksums) {
		_storedChecksums.putAll(checksums);
	}

	public Set<File> scan(boolean reportImmediately) {
		Set<File> files = _processFiles(reportImmediately, _list());

		return new TreeSet<>(files);
	}

	public void updateChecksum(File file) {
		if ((file != null) && _storedChecksums.containsKey(file)) {
			long newChecksum = _checksum(file);

			_storedChecksums.put(file, newChecksum);
		}
	}

	private static List<File> _canon(List<File> files) {
		List<File> canonicalFiles = new ArrayList<>(files.size());

		for (File file : files) {
			try {
				canonicalFiles.add(file.getCanonicalFile());
			}
			catch (IOException ioException) {
				canonicalFiles.add(file);
			}
		}

		return canonicalFiles;
	}

	private static long _checksum(File file) {
		CRC32 crc32 = new CRC32();

		_checksum(file, crc32);

		return crc32.getValue();
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

	private File[] _list() {
		List<File> files = new ArrayList<>();

		for (File dir : _watchedDirs) {
			File[] list = dir.listFiles(_filenameFilter);

			if (list != null) {
				Collections.addAll(files, list);
			}
		}

		return files.toArray(new File[0]);
	}

	private Set<File> _processFiles(boolean reportImmediately, File[] list) {
		if (list == null) {
			return new HashSet<>();
		}

		Set<File> files = new HashSet<>();

		Set<File> removed = new HashSet<>(_storedChecksums.keySet());

		for (File file : list) {
			if (file.isDirectory()) {
				if (_recurseSubdir) {
					files.addAll(
						_processFiles(
							reportImmediately,
							file.listFiles(_filenameFilter)));
				}

				continue;
			}

			long lastChecksum = 0;

			if (_lastChecksums.get(file) != null) {
				lastChecksum = _lastChecksums.get(file);
			}

			long storedChecksum = 0;

			if (_storedChecksums.get(file) != null) {
				storedChecksum = _storedChecksums.get(file);
			}

			long newChecksum = _checksum(file);

			_lastChecksums.put(file, newChecksum);

			// Only handle file when it does not change anymore and it has
			// changed since last reported

			if (((newChecksum == lastChecksum) || reportImmediately) &&
				(newChecksum != storedChecksum)) {

				_storedChecksums.put(file, newChecksum);
				files.add(file);
			}

			removed.remove(file);
		}

		// Make sure we'll handle a file that has been deleted

		files.addAll(removed);

		for (File file : removed) {

			// Remove no longer used checksums

			_lastChecksums.remove(file);
			_storedChecksums.remove(file);
		}

		return files;
	}

	private final FilenameFilter _filenameFilter;
	private final Map<File, Long> _lastChecksums = new HashMap<>();
	private final boolean _recurseSubdir;
	private final Map<File, Long> _storedChecksums = new HashMap<>();
	private final List<File> _watchedDirs;

}