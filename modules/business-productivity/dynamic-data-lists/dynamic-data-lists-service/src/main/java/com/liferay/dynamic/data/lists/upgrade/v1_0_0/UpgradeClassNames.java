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

package com.liferay.dynamic.data.lists.upgrade.v1_0_0;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.classname.ClassNameDependency;
import com.liferay.portal.upgrade.util.classname.ClassNameDependencyUpgrader;
import com.liferay.portal.upgrade.util.classname.dependency.ResourcePermissionClassNameDependency;

import java.util.Collections;
import java.util.List;

/**
 * @author Marcellus Tavares
 */
public class UpgradeClassNames extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		List<ClassNameDependency> classNameDependencies =
			Collections.singletonList(
				(ClassNameDependency)(
					new ResourcePermissionClassNameDependency()));

		upgradeDDLClassName(classNameDependencies);
		upgradeDDLRecordClassName(classNameDependencies);
		upgradeDDLRecordSetClassName(classNameDependencies);
		upgradeDDLRecordVersionClassName(classNameDependencies);
	}

	protected void upgradeDDLClassName(
		List<ClassNameDependency> classNameDependencies) {

		ClassNameDependencyUpgrader classNameDependencyUpgrader =
			new ClassNameDependencyUpgrader(
				"com.liferay.portlet.dynamicdatalists",
				"com.liferay.dynamic.data.lists", classNameDependencies);

		classNameDependencyUpgrader.upgrade();
	}

	protected void upgradeDDLRecordClassName(
		List<ClassNameDependency> classNameDependencies) {

		ClassNameDependencyUpgrader classNameDependencyUpgrader =
			new ClassNameDependencyUpgrader(
				"com.liferay.portlet.dynamicdatalists.model.DDLRecord",
				DDLRecord.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();
	}

	protected void upgradeDDLRecordSetClassName(
		List<ClassNameDependency> classNameDependencies) {

		ClassNameDependencyUpgrader classNameDependencyUpgrader =
			new ClassNameDependencyUpgrader(
				"com.liferay.portlet.dynamicdatalists.model.DDLRecordSet",
				DDLRecordSet.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();
	}

	protected void upgradeDDLRecordVersionClassName(
		List<ClassNameDependency> classNameDependencies) {

		ClassNameDependencyUpgrader classNameDependencyUpgrader =
			new ClassNameDependencyUpgrader(
				"com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion",
				DDLRecordVersion.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();
	}

}