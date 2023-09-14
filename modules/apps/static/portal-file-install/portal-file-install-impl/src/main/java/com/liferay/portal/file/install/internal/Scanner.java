/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.file.install.internal;

import com.liferay.petra.reflect.ReflectionUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.zip.CRC32;

/**
 * @author Matthew Tambara
 */
public class Scanner {

	public static final String SUBDIR_MODE_RECURSE = "recurse";

	public Scanner(
		List<File> dirs, FilenameFilter filenameFilter, String subdirMode) {

		_filenameFilter = filenameFilter;

		_watchedDirs = dirs;

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

	private static long _checksum(File file) {
		CRC32 crc32 = new CRC32();

		_checksum(file, crc32);

		return crc32.getValue();
	}

	private static void _checksum(File file, CRC32 crc32) {
		String name = file.getName();

		crc32.update(name.getBytes());

		if (file.isFile()) {
			_checksum(file.canWrite() ? 1000L : -1000L, crc32);
			_checksum(file.lastModified() / 1000, crc32);
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
			if (_recurseSubdir) {
				try {
					Files.walkFileTree(
						dir.toPath(),
						new SimpleFileVisitor<Path>() {

							@Override
							public FileVisitResult visitFile(
									Path path,
									BasicFileAttributes basicFileAttributes)
								throws IOException {

								File file = path.toFile();

								if (_filenameFilter.accept(
										file.getParentFile(), file.getName())) {

									files.add(file);
								}

								return FileVisitResult.CONTINUE;
							}

						});
				}
				catch (IOException ioException) {
					ReflectionUtil.throwException(ioException);
				}
			}
			else {
				File[] list = dir.listFiles(_filenameFilter);

				if (list != null) {
					Collections.addAll(files, list);
				}
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