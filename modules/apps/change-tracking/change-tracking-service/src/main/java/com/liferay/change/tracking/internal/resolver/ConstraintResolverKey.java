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

		_modelClassName = modelClass.getName();
		_uniqueIndexNames = uniqueIndexNames;
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
		StringBundler sb = new StringBundler(_uniqueIndexNames.length * 2 + 2);

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