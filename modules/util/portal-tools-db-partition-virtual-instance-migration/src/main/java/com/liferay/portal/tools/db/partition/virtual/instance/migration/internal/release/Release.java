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

package com.liferay.portal.tools.db.partition.virtual.instance.migration.internal.release;

import com.liferay.portal.tools.db.partition.virtual.instance.migration.internal.version.Version;

import java.util.Objects;

/**
 * @author Luis Ortiz
 */
public class Release {

	public Release(
		String servletContextName, Version schemaVersion, boolean verified) {

		_servletContextName = servletContextName;
		_schemaVersion = schemaVersion;
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

		if (_servletContextName.equals(release._servletContextName) &&
			_schemaVersion.equals(release._schemaVersion) &&
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