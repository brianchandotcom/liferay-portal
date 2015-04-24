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

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.sourceforge.cobertura.coveragedata.ClassData;
import net.sourceforge.cobertura.coveragedata.CoverageData;
import net.sourceforge.cobertura.coveragedata.PackageData;

/**
 * @author Shuyang Zhou
 */
public class ProjectData extends CoverageDataContainer {

	public ClassData getClassData(String className) {
		return _classDataMap.get(className);
	}

	public ClassData getOrCreateClassData(String className) {
		ClassData classData = _classDataMap.get(className);

		if (classData == null) {
			classData = new ClassData(className);

			String packageName = classData.getPackageName();

			PackageData packageData = (PackageData)children.get(packageName);

			if (packageData == null) {
				packageData = new PackageData(packageName);

				PackageData previousPackageData =
					(PackageData)children.putIfAbsent(packageName, packageData);

				if (previousPackageData != null) {
					packageData = previousPackageData;
				}
			}

			packageData.addClassData(classData);

			_classDataMap.put(classData.getName(), classData);
		}

		return classData;
	}

	@Override
	public void merge(CoverageData coverageData) {
		if (coverageData == null) {
			return;
		}

		ProjectData projectData = (ProjectData)coverageData;
			super.merge(coverageData);

		Map<String, ClassData> classDataMap = projectData._classDataMap;

		for (Entry<String, ClassData> entry : classDataMap.entrySet()) {
			_classDataMap.putIfAbsent(entry.getKey(), entry.getValue());
		}
	}

	private static final long serialVersionUID = 1;

	private final ConcurrentMap<String, ClassData> _classDataMap =
		new ConcurrentHashMap<>();

}