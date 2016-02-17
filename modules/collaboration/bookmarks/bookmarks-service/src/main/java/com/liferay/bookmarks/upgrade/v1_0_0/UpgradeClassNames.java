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

package com.liferay.bookmarks.upgrade.v1_0_0;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.classname.ClassNameDependency;
import com.liferay.portal.upgrade.util.classname.ClassNameDependencyUpgrader;
import com.liferay.portal.upgrade.util.classname.dependency.ResourceBlockClassNameDependency;

import java.util.Collections;
import java.util.List;

/**
 * @author Miguel Pastor
 */
public class UpgradeClassNames extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		List<ClassNameDependency> classNameDependencies =
			Collections.singletonList(
				(ClassNameDependency)(new ResourceBlockClassNameDependency()));

		ClassNameDependencyUpgrader classNameDependencyUpgrader =
			new ClassNameDependencyUpgrader(
				"com.liferay.portlet.bookmarks", "com.liferay.bookmarks",
				classNameDependencies);

		classNameDependencyUpgrader.upgrade();

		classNameDependencyUpgrader = new ClassNameDependencyUpgrader(
			"com.liferay.portlet.bookmarks.model.BookmarksEntry",
			BookmarksEntry.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();

		classNameDependencyUpgrader = new ClassNameDependencyUpgrader(
			"com.liferay.portlet.bookmarks.model.BookmarksFolder",
			BookmarksFolder.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();
	}

}