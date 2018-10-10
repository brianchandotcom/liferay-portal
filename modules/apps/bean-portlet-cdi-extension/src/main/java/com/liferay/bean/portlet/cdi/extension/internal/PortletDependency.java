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

package com.liferay.bean.portlet.cdi.extension.internal;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.Objects;

/**
 * @author Neil Griffin
 */
public class PortletDependency {

	public PortletDependency(String name, String scope, String version) {
		_name = name;
		_scope = scope;
		_version = version;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if ((obj == null) || !(obj instanceof PortletDependency)) {
			return false;
		}

		PortletDependency portletDependency = (PortletDependency)obj;

		if (Objects.equals(_name, portletDependency.getName()) &&
			Objects.equals(_scope, portletDependency.getScope()) &&
			Objects.equals(_version, portletDependency.getVersion())) {

			return true;
		}

		return false;
	}

	public String getName() {
		return _name;
	}

	public String getScope() {
		return _scope;
	}

	public String getVersion() {
		return _version;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, _name);

		hashCode = HashUtil.hash(hashCode, _scope);

		return HashUtil.hash(hashCode, _version);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		if (_name != null) {
			sb.append(_name);
		}

		sb.append(StringPool.SEMICOLON);

		if (_scope != null) {
			sb.append(_scope);
		}

		sb.append(StringPool.SEMICOLON);

		if (_version != null) {
			sb.append(_version);
		}

		return sb.toString();
	}

	private final String _name;
	private final String _scope;
	private final String _version;

}