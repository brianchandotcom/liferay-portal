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

package com.liferay.cobertura.coveragedata;

/**
 * @author Shuyang Zhou
 */
public class PackageData extends CoverageDataContainer {

	public PackageData(String name) {
		_name = name;
	}

	public ClassData addClassData(ClassData classData) {
		ClassData previousClassData = (ClassData)children.putIfAbsent(
			classData.getBaseName(), classData);

		if (previousClassData != null) {
			classData = previousClassData;
		}

		return classData;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PackageData)) {
			return false;
		}

		PackageData packageData = (PackageData)obj;

		if (_name.equals(packageData._name) && super.equals(packageData)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return _name.hashCode();
	}

	private static final long serialVersionUID = 1;

	private final String _name;

}