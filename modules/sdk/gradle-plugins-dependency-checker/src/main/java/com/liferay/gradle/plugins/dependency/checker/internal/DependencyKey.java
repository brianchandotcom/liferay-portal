/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.dependency.checker.internal;

/**
 * @author Andrea Di Giorgi
 */
public class DependencyKey {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DependencyKey)) {
			return false;
		}

		DependencyKey dependencyKey = (DependencyKey)object;

		if (_configuration.equals(dependencyKey.getConfiguration()) &&
			_group.equals(dependencyKey.getGroup()) &&
			_name.equals(dependencyKey.getName())) {

			return true;
		}

		return false;
	}

	public String getConfiguration() {
		return _configuration;
	}

	public String getGroup() {
		return _group;
	}

	public String getName() {
		return _name;
	}

	@Override
	public int hashCode() {
		String s = toString();

		return s.hashCode();
	}

	public void setConfiguration(String configuration) {
		_configuration = configuration;
	}

	public void setGroup(String group) {
		_group = group;
	}

	public void setName(String name) {
		_name = name;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(_configuration);
		sb.append(':');
		sb.append(_group);
		sb.append(':');
		sb.append(_name);

		return sb.toString();
	}

	private String _configuration;
	private String _group;
	private String _name;

}