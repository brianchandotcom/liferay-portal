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

package com.liferay.portal.file.install.internal.version;

import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.Map;

import org.osgi.framework.Version;

/**
 * @author Matthew Tambara
 */
public final class VersionTable {

	public static Version getVersion(int major, int minor, int micro) {
		return getVersion(major, minor, micro, null);
	}

	public static Version getVersion(
		int major, int minor, int micro, String qualifier) {

		String key = StringBundler.concat(
			major, StringPool.PERIOD, minor, StringPool.PERIOD, micro);

		if ((qualifier != null) && (qualifier.length() != 0)) {
			key = key + StringPool.PERIOD + qualifier;
		}

		synchronized (_versions) {
			Version version = _versions.get(key);

			if (version == null) {
				version = new Version(major, minor, micro, qualifier);

				_versions.put(key, version);
			}

			return version;
		}
	}

	public static Version getVersion(String version) {
		return getVersion(version, true);
	}

	public static Version getVersion(String versionString, boolean clean) {
		if (clean) {
			versionString = VersionCleaner.clean(versionString);
		}

		synchronized (_versions) {
			Version version = _versions.get(versionString);

			if (version == null) {
				version = Version.parseVersion(versionString);

				_versions.put(versionString, version);
			}

			return version;
		}
	}

	private VersionTable() {
	}

	private static final Map<String, Version> _versions =
		new ConcurrentReferenceKeyHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);

}