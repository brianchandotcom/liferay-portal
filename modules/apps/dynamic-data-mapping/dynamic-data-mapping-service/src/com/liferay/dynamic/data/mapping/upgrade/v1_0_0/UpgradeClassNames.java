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

package com.liferay.dynamic.data.mapping.upgrade.v1_0_0;

import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
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

		upgradeDDMClassName(classNameDependencies);
		upgradeDDMContentClassName(classNameDependencies);
		upgradeDDMStructureClassName(classNameDependencies);
		upgradeDDMTemplateClassName(classNameDependencies);
	}

	protected void upgradeDDMClassName(
		List<ClassNameDependency> classNameDependencies) {

		ClassNameDependencyUpgrader classNameDependencyUpgrader =
			new ClassNameDependencyUpgrader(
				"com.liferay.portlet.dynamicdatamapping",
				"com.liferay.dynamic.data.mapping", classNameDependencies);

		classNameDependencyUpgrader.upgrade();
	}

	protected void upgradeDDMContentClassName(
		List<ClassNameDependency> classNameDependencies) {

		ClassNameDependencyUpgrader classNameDependencyUpgrader =
			new ClassNameDependencyUpgrader(
				"com.liferay.portlet.dynamicdatamapping.DDMContent",
				DDMContent.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();
	}

	protected void upgradeDDMStructureClassName(
		List<ClassNameDependency> classNameDependencies) {

		ClassNameDependencyUpgrader classNameDependencyUpgrader =
			new ClassNameDependencyUpgrader(
				"com.liferay.portlet.dynamicdatamapping.DDMStructure",
				DDMStructure.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();
	}

	protected void upgradeDDMTemplateClassName(
		List<ClassNameDependency> classNameDependencies) {

		ClassNameDependencyUpgrader classNameDependencyUpgrader =
			new ClassNameDependencyUpgrader(
				"com.liferay.portlet.dynamicdatamapping.DDMTemplate",
				DDMTemplate.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();
	}

}