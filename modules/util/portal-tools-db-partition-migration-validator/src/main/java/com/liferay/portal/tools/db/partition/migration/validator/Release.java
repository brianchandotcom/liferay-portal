/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.migration.validator;

import com.liferay.portal.kernel.version.Version;

import java.util.Objects;

/**
 * @author Luis Ortiz
 */
public class Release {

	public Release() {
	}

	public Release(
		Version schemaVersion, String servletContextName, int state,
		boolean verified) {

		_schemaVersion = schemaVersion;
		_servletContextName = servletContextName;
		_state = state;
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
			(_state == release._state) && (_verified == release._verified)) {

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

	public int getState() {
		return _state;
	}

	public boolean getVerified() {
		return _verified;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			_schemaVersion, _servletContextName, _state, _verified);
	}

	public void setSchemaVersion(Version schemaVersion) {
		_schemaVersion = schemaVersion;
	}

	public void setServletContextName(String servletContextName) {
		_servletContextName = servletContextName;
	}

	public void setState(int state) {
		_state = state;
	}

	public void setVerified(boolean verified) {
		_verified = verified;
	}

	private Version _schemaVersion;
	private String _servletContextName;
	private int _state;
	private boolean _verified;

}