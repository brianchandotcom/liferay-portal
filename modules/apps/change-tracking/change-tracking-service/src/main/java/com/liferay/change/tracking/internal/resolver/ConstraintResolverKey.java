/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.resolver;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import java.util.Arrays;

/**
 * @author Preston Crary
 */
public class ConstraintResolverKey {

	public ConstraintResolverKey(
		Class<?> modelClass, String[] uniqueIndexNames) {

		this(modelClass.getName(), uniqueIndexNames);
	}

	public ConstraintResolverKey(
		String modelClassName, String[] uniqueIndexNames) {

		_modelClassName = modelClassName;
		_uniqueIndexNames = uniqueIndexNames;
	}

	@Override
	public boolean equals(Object object) {
		ConstraintResolverKey constraintResolverKey =
			(ConstraintResolverKey)object;

		if (_modelClassName.equals(constraintResolverKey._modelClassName) &&
			Arrays.equals(
				_uniqueIndexNames, constraintResolverKey._uniqueIndexNames)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _modelClassName);

		for (String uniqueIndexName : _uniqueIndexNames) {
			hash = HashUtil.hash(hash, uniqueIndexName);
		}

		return hash;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(
			(_uniqueIndexNames.length * 2) + 2);

		sb.append("{modelClassName=");
		sb.append(_modelClassName);
		sb.append(", uniqueIndexNames=[");

		for (String uniqueIndexName : _uniqueIndexNames) {
			sb.append(uniqueIndexName);
			sb.append(", ");
		}

		sb.setStringAt("]}", sb.index() - 1);

		return sb.toString();
	}

	private final String _modelClassName;
	private final String[] _uniqueIndexNames;

}