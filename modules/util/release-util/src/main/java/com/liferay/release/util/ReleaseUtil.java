/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.release.util;

import com.liferay.release.util.internal.ReleaseProvider;
import com.liferay.release.util.internal.constants.ReleaseConstants;
import com.liferay.release.util.internal.util.StringUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author Drew Brokke
 */
public class ReleaseUtil {

	public static <T> T getFromReleaseEntry(
		String releaseKey, Function<ReleaseEntry, T> function) {

		ReleaseEntry releaseEntry = getReleaseEntry(releaseKey);

		if (releaseEntry != null) {
			return function.apply(releaseEntry);
		}

		return null;
	}

	public static List<ReleaseEntry> getReleaseEntries() {
		_checkInstance();

		return new ArrayList<>(_releaseProvider.getReleaseEntries());
	}

	public static ReleaseEntry getReleaseEntry(String releaseKey) {
		Map<String, ReleaseEntry> releaseEntryMap = getReleaseEntryMap();

		return releaseEntryMap.get(releaseKey);
	}

	public static Map<String, ReleaseEntry> getReleaseEntryMap() {
		_checkInstance();

		return new HashMap<>(_releaseProvider.getReleaseEntryMap());
	}

	public static Stream<ReleaseEntry> getReleaseEntryStream() {
		List<ReleaseEntry> releaseEntries = getReleaseEntries();

		return releaseEntries.stream();
	}

	public static void initialize() {
		initialize(ReleaseConstants.DEFAULT_MAX_AGE);
	}

	public static void initialize(long maxAge) {
		List<String> releasesMirrors = new ArrayList<>(
			StringUtil.split(System.getenv("LIFERAY_RELEASES_MIRRORS")));

		releasesMirrors.add("https://releases-cdn.liferay.com");

		initialize(maxAge, releasesMirrors, ReleaseConstants.CACHE_DIR);
	}

	public static void initialize(
		long maxAge, List<String> releasesMirrors, File cacheDir) {

		_releaseProvider = new ReleaseProvider(
			maxAge, releasesMirrors, cacheDir);
	}

	private static void _checkInstance() {
		if (_releaseProvider == null) {
			initialize();
		}
	}

	private static ReleaseProvider _releaseProvider;

}