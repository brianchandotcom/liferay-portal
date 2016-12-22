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

package com.liferay.gradle.plugins.dependency.checker.internal;

/**
 * @author Andrea Di Giorgi
 */
public class DependencyKey {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DependencyKey)) {
			return false;
		}

		DependencyKey dependencyKey = (DependencyKey)obj;

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