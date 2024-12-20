/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.modules.util;

import com.liferay.petra.string.StringBundler;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Shuyang Zhou
 */
public class Module {

	public Module(String id, List<String> dependencyIds) {
		_id = id;
		_dependencyIds = new TreeSet<>(dependencyIds);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Module)) {
			return false;
		}

		Module module = (Module)object;

		return Objects.equals(_id, module._id);
	}

	public Set<String> getDependencyIds() {
		return _dependencyIds;
	}

	public String getId() {
		return _id;
	}

	@Override
	public int hashCode() {
		return _id.hashCode();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{id=");
		sb.append(_id);
		sb.append(", _dependencyIds=");
		sb.append(_dependencyIds);
		sb.append("}");

		return sb.toString();
	}

	private final Set<String> _dependencyIds;
	private final String _id;

}