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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;

/**
 * @author Neil Griffin
 */
public class PortletDependency {

	public PortletDependency() {
		this(null, null, null);
	}

	public PortletDependency(String name, String scope, String version) {
		_name = name;
		_scope = scope;
		_version = version;
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

	public void setName(String name) {
		_name = name;
	}

	public void setScope(String scope) {
		_scope = scope;
	}

	public void setVersion(String version) {
		_version = version;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		if (_name != null) {
			sb.append(_name);
		}

		sb.append(CharPool.SEMICOLON);

		if (_scope != null) {
			sb.append(_scope);
		}

		sb.append(CharPool.SEMICOLON);

		if (_version != null) {
			sb.append(_version);
		}

		return sb.toString();
	}

	private String _name;
	private String _scope;
	private String _version;

}