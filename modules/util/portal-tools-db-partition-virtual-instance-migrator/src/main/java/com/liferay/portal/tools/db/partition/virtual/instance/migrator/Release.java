/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.virtual.instance.migrator;

import com.liferay.portal.kernel.version.Version;

import java.util.Objects;

/**
 * @author Luis Ortiz
 */
public class Release {

	public Release(
		Version schemaVersion, String servletContextName, boolean verified) {

		_schemaVersion = schemaVersion;
		_servletContextName = servletContextName;
		_verified = verified;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Release)) {
			return false;
		}

		Release release = (Release)object;

		if (_schemaVersion.equals(release._schemaVersion) &&
			_servletContextName.equals(release._servletContextName) &&
			(_verified == release._verified)) {

			return true;
		}

		return false;
	}

	public Version getSchemaVersion() {
		return _schemaVersion;
	}

	public String getServletContextName() {
		return _servletContextName;
	}

	public boolean getVerified() {
		return _verified;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_schemaVersion, _servletContextName, _verified);
	}

	private final Version _schemaVersion;
	private final String _servletContextName;
	private final boolean _verified;

}