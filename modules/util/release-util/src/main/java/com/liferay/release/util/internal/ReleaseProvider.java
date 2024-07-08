/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.release.util.internal;

import com.liferay.release.util.ReleaseEntry;
import com.liferay.release.util.ResourceUtil;
import com.liferay.release.util.internal.util.StringUtil;

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

/**
 * @author Drew Brokke
 */
public class ReleaseProvider {

	public ReleaseProvider(
		long maxAge, List<String> releasesMirrors, File cacheDir) {

		_cacheDir = cacheDir;

		for (String releasesMirror : releasesMirrors) {
			_releasesMirrors.add(_normalizeReleasesMirror(releasesMirror));
		}

		File releasesJsonFile = new File(_cacheDir, "releases.json");

		ReleaseEntryList releaseEntries = ResourceUtil.readJSON(
			ReleaseEntryList.class,
			ResourceUtil.getLocalFileResolver(
				releasesJsonFile, maxAge, ChronoUnit.DAYS));

		if (releaseEntries == null) {
			for (String releasesMirror : _releasesMirrors) {
				releaseEntries = ResourceUtil.readJSON(
					ReleaseEntryList.class,
					ResourceUtil.getURLResolver(
						cacheDir, releasesMirror + "/releases.json"));

				if (releaseEntries != null) {
					break;
				}
			}
		}

		if (releaseEntries == null) {
			releaseEntries = ResourceUtil.readJSON(
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

			releaseEntries = ResourceUtil.readJSON(
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

		for (ReleaseEntryImpl releaseEntryImpl : releaseEntries) {
			releaseEntryImpl.setPropertiesSupplier(
				() -> _createReleaseProperties(
					releaseEntryImpl.getReleaseKey()));

			_releaseEntries.add(releaseEntryImpl);

			_releaseEntryMap.put(
				releaseEntryImpl.getReleaseKey(), releaseEntryImpl);
		}
	}

	public List<ReleaseEntry> getReleaseEntries() {
		return _releaseEntries;
	}

	public Map<String, ReleaseEntry> getReleaseEntryMap() {
		return _releaseEntryMap;
	}

	private Properties _createReleaseProperties(String releaseKey) {
		ReleaseEntry releaseEntry = _releaseEntryMap.get(releaseKey);

		if (releaseEntry == null) {
			return new Properties();
		}

		String product = releaseEntry.getProduct();

		File productReleasePropertiesCacheDir = new File(
			new File(_cacheDir, "releaseProperties"),
			String.format("%s/%s", product, releaseKey));

		Properties properties = ResourceUtil.readProperties(
			ResourceUtil.getLocalFileResolver(
				new File(
					productReleasePropertiesCacheDir, "release.properties")));

		if (properties == null) {
			String releasesCDNURL =
				releaseEntry.getURL() + "/release.properties";

			URI cdnURI = URI.create(releasesCDNURL);

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

		return properties;
	}

	private String _normalizeReleasesMirror(String releasesMirror) {
		if (releasesMirror.endsWith(StringUtil.FORWARD_SLASH)) {
			return releasesMirror.substring(0, releasesMirror.length() - 1);
		}

		return releasesMirror;
	}

	private final File _cacheDir;
	private final List<ReleaseEntry> _releaseEntries = new ArrayList<>();
	private final Map<String, ReleaseEntry> _releaseEntryMap = new HashMap<>();
	private final List<String> _releasesMirrors = new ArrayList<>();

	private static class ReleaseEntryList extends ArrayList<ReleaseEntryImpl> {
	}

}