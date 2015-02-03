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

package com.liferay.wiki.upgrade.v1_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.classname.ClassNameDependency;
import com.liferay.portal.upgrade.util.classname.ClassNameDependencyUpgrader;
import com.liferay.portal.upgrade.util.classname.dependency.ResourcePermissionDependency;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;

import java.util.Collections;
import java.util.List;

/**
 * @author Miguel Pastor
 */
public class UpgradeClassNames extends UpgradeProcess {

	protected List<ClassNameDependency> buildDependencies() {
		ClassNameDependency classNameDependency =
			new ResourcePermissionDependency();

		return Collections.singletonList(classNameDependency);
	}

	@Override
	protected void doUpgrade() throws Exception {
		List<ClassNameDependency> classNameDependencies = buildDependencies();

		ClassNameDependencyUpgrader classNameDependencyUpgrader =
			new ClassNameDependencyUpgrader(
				"com.liferay.portlet.wiki.model.WikiNode",
				WikiNode.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();

		classNameDependencyUpgrader =
			new ClassNameDependencyUpgrader(
				"com.liferay.portlet.wiki.model.WikiPage",
				WikiPage.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();

		classNameDependencyUpgrader =
			new ClassNameDependencyUpgrader(
				"com.liferay.portlet.wiki", "com.liferay.wiki",
				classNameDependencies);

		classNameDependencyUpgrader.upgrade();
	}

}