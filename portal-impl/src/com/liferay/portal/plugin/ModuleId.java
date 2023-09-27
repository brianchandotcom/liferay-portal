/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.plugin;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.plugin.Version;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jorge Ferrer
 */
public class ModuleId implements Serializable {

	public static ModuleId getInstance(String moduleId) {
		ModuleId moduleIdObj = _moduleIds.get(moduleId);

		if (moduleIdObj == null) {
			moduleIdObj = new ModuleId(moduleId);

			_moduleIds.put(moduleId, moduleIdObj);
		}

		return moduleIdObj;
	}

	public static String toString(
		String groupId, String artifactId, String version, String type) {

		return StringBundler.concat(
			groupId, StringPool.SLASH, artifactId, StringPool.SLASH, version,
			StringPool.SLASH, type);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ModuleId)) {
			return false;
		}

		ModuleId moduleId = (ModuleId)object;

		return Objects.equals(toString(), moduleId.toString());
	}

	public String getArtifactId() {
		return _artifactId;
	}

	public String getArtifactPath() {
		return StringBundler.concat(
			StringPool.SLASH, _groupId, StringPool.SLASH, _artifactId,
			StringPool.SLASH, _pluginVersion, StringPool.SLASH,
			getArtifactWARName());
	}

	public String getArtifactWARName() {
		return StringBundler.concat(
			_artifactId, StringPool.DASH, _pluginVersion, StringPool.PERIOD,
			_type);
	}

	public String getGroupId() {
		return _groupId;
	}

	public String getPackageId() {
		return _groupId + StringPool.SLASH + _artifactId;
	}

	public String getType() {
		return _type;
	}

	public String getVersion() {
		return _pluginVersion.toString();
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	public boolean isLaterVersionThan(String version) {
		return _pluginVersion.isLaterVersionThan(version);
	}

	public boolean isPreviousVersionThan(String version) {
		return _pluginVersion.isPreviousVersionThan(version);
	}

	public boolean isSameVersionAs(String version) {
		return _pluginVersion.isSameVersionAs(version);
	}

	@Override
	public String toString() {
		return toString(
			_groupId, _artifactId, _pluginVersion.toString(), _type);
	}

	protected ModuleId(String moduleId) {
		StringTokenizer st = new StringTokenizer(moduleId, StringPool.SLASH);

		if (st.countTokens() < 4) {
			throw new RuntimeException(
				"The moduleId " + moduleId + " is not correct");
		}

		_groupId = st.nextToken();
		_artifactId = st.nextToken();
		_pluginVersion = Version.getInstance(st.nextToken());
		_type = st.nextToken();
	}

	protected ModuleId(
		String groupId, String artifactId, Version pluginVersion, String type) {

		_groupId = groupId;
		_artifactId = artifactId;
		_pluginVersion = pluginVersion;
		_type = type;
	}

	private static final Map<String, ModuleId> _moduleIds =
		new ConcurrentHashMap<>();

	private final String _artifactId;
	private final String _groupId;
	private final Version _pluginVersion;
	private final String _type;

}