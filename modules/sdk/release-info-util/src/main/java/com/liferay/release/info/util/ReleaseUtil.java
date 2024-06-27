/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.release.info.util;

import com.liferay.release.info.util.internal.StringUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URI;

import java.nio.file.Files;
import java.nio.file.attribute.FileTime;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author Drew Brokke
 */
public class ReleaseUtil {

	public static <T> T getFromReleaseProperties(
		String releaseKey, Function<ReleaseProperties, T> function) {

		return function.apply(getReleaseProperties(releaseKey));
	}

	public static List<ReleaseEntry> getReleaseEntries() {
		_checkInstance();

		return new ArrayList<>(_releaseUtil._releaseEntries);
	}

	public static ReleaseEntry getReleaseEntry(String releaseKey) {
		Map<String, ReleaseEntry> releaseEntryMap = getReleaseEntryMap();

		return releaseEntryMap.get(releaseKey);
	}

	public static Map<String, ReleaseEntry> getReleaseEntryMap() {
		_checkInstance();

		return new HashMap<>(_releaseUtil._releaseEntryMap);
	}

	public static Stream<ReleaseEntry> getReleaseEntryStream() {
		List<ReleaseEntry> releaseEntries = getReleaseEntries();

		return releaseEntries.stream();
	}

	public static ReleaseProperties getReleaseProperties(String releaseKey) {
		if (releaseKey == null) {
			return _EMPTY_RELEASE_PROPERTIES;
		}

		_checkInstance();

		return _releaseUtil._releasePropertiesMap.computeIfAbsent(
			releaseKey, _releaseUtil::_createReleaseProperties);
	}

	public static void initialize(long maxAge) {
		ArrayList<String> releasesMirrors = new ArrayList<>(
			StringUtil.split(System.getenv("LIFERAY_RELEASES_MIRRORS")));

		releasesMirrors.add("https://releases-cdn.liferay.com");

		initialize(maxAge, releasesMirrors, _DEFAULT_WORKSPACE_CACHE_DIR);
	}

	public static void initialize(
		long maxAge, List<String> releasesMirrors, File workspaceCacheDir) {

		_releaseUtil = new ReleaseUtil(
			maxAge, releasesMirrors, workspaceCacheDir);
	}

	private static void _checkInstance() {
		if (_releaseUtil == null) {
			initialize(_DEFAULT_MAX_AGE);
		}
	}

	private ReleaseUtil(
		long maxAge, List<String> releasesMirrors, File workspaceCacheDir) {

		_workspaceCacheDir = workspaceCacheDir;

		for (String releasesMirror : releasesMirrors) {
			_releasesMirrors.add(_normalizeReleasesMirror(releasesMirror));
		}

		File releasesJsonFile = new File(_workspaceCacheDir, "releases.json");

		ReleaseEntryList releaseEntries = ResourceUtil.readJson(
			ReleaseEntryList.class,
			ResourceUtil.getLocalFileResolver(
				releasesJsonFile, maxAge, ChronoUnit.DAYS));

		if (releaseEntries == null) {
			for (String releasesMirror : _releasesMirrors) {
				releaseEntries = ResourceUtil.readJson(
					ReleaseEntryList.class,
					ResourceUtil.getURLResolver(
						workspaceCacheDir, releasesMirror + "/releases.json"));

				if (releaseEntries != null) {
					break;
				}
			}
		}

		if (releaseEntries == null) {
			releaseEntries = ResourceUtil.readJson(
				ReleaseEntryList.class,
				ResourceUtil.getLocalFileResolver(releasesJsonFile));

			if (releaseEntries != null) {
				try {
					Files.setLastModifiedTime(
						releasesJsonFile.toPath(),
						FileTime.from(Instant.now()));
				}
				catch (IOException ioException) {
					throw new RuntimeException(ioException.getMessage());
				}
			}
		}

		if (releaseEntries == null) {
			ResourceUtil.Resolver classLoaderResolver =
				ResourceUtil.getClassLoaderResolver("/releases.json");

			releaseEntries = ResourceUtil.readJson(
				ReleaseEntryList.class, classLoaderResolver);

			try (InputStream inputStream = classLoaderResolver.resolve()) {
				Files.copy(inputStream, releasesJsonFile.toPath());
			}
			catch (Exception exception) {
				throw new RuntimeException(exception.getMessage());
			}
		}

		if (releaseEntries == null) {
			throw new RuntimeException("Unable to read releases.json");
		}

		for (ReleaseEntry releaseEntry : releaseEntries) {
			_releaseEntries.add(releaseEntry);

			_releaseEntryMap.put(releaseEntry.getReleaseKey(), releaseEntry);
		}
	}

	private ReleaseProperties _createReleaseProperties(String releaseKey) {
		ReleaseEntry releaseEntry = _releaseEntryMap.get(releaseKey);

		if (releaseEntry == null) {
			return _EMPTY_RELEASE_PROPERTIES;
		}

		String product = releaseEntry.getProduct();

		File productReleasePropertiesCacheDir = new File(
			new File(_workspaceCacheDir, "releaseProperties"),
			String.format("%s/%s", product, releaseKey));

		Properties properties = ResourceUtil.readProperties(
			ResourceUtil.getLocalFileResolver(
				new File(
					productReleasePropertiesCacheDir, "release.properties")));

		if (properties == null) {
			String releasesCDNUrl =
				releaseEntry.getUrl() + "/release.properties";

			URI cdnURI = URI.create(releasesCDNUrl);

			String cdnURIPath = cdnURI.getPath();

			for (String releasesMirror : _releasesMirrors) {
				String fullMirrorPath = releasesMirror + cdnURIPath;

				properties = ResourceUtil.readProperties(
					ResourceUtil.getURLResolver(
						productReleasePropertiesCacheDir, fullMirrorPath));

				if (properties != null) {
					break;
				}
			}
		}

		if (properties == null) {
			throw new RuntimeException(
				"No release properties found for product key " + releaseKey);
		}

		return new ReleaseProperties(properties);
	}

	private String _normalizeReleasesMirror(String releasesMirror) {
		if (releasesMirror.endsWith(StringUtil.FORWARD_SLASH)) {
			return releasesMirror.substring(0, releasesMirror.length() - 1);
		}

		return releasesMirror;
	}

	private static final long _DEFAULT_MAX_AGE = 7;

	private static final File _DEFAULT_WORKSPACE_CACHE_DIR = new File(
		System.getProperty("user.home"), ".liferay/workspace");

	private static final ReleaseProperties _EMPTY_RELEASE_PROPERTIES =
		new ReleaseProperties();

	private static ReleaseUtil _releaseUtil;

	private final List<ReleaseEntry> _releaseEntries = new ArrayList<>();
	private final Map<String, ReleaseEntry> _releaseEntryMap = new HashMap<>();
	private final Map<String, ReleaseProperties> _releasePropertiesMap =
		new HashMap<>();
	private final List<String> _releasesMirrors = new ArrayList<>();
	private final File _workspaceCacheDir;

	private static class ReleaseEntryList extends ArrayList<ReleaseEntry> {
	}

}