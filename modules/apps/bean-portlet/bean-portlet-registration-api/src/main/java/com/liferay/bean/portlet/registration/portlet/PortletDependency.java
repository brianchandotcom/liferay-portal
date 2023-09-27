/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.registration.portlet;

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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if ((object == null) || !(object instanceof PortletDependency)) {
			return false;
		}

		PortletDependency portletDependency = (PortletDependency)object;

		if (Objects.equals(_name, portletDependency._name) &&
			Objects.equals(_scope, portletDependency._scope) &&
			Objects.equals(_version, portletDependency._version)) {

			return true;
		}

		return false;
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