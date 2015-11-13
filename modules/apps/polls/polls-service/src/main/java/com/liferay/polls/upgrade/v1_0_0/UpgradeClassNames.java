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

package com.liferay.polls.upgrade.v1_0_0;

import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.model.PollsVote;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.classname.ClassNameDependency;
import com.liferay.portal.upgrade.util.classname.ClassNameDependencyUpgrader;
import com.liferay.portal.upgrade.util.classname.dependency.ResourcePermissionClassNameDependency;

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
				(ClassNameDependency)
					(new ResourcePermissionClassNameDependency()));

		ClassNameDependencyUpgrader classNameDependencyUpgrader =
			new ClassNameDependencyUpgrader(
				"com.liferay.portlet.polls", "com.liferay.polls",
				classNameDependencies);

		classNameDependencyUpgrader.upgrade();

		classNameDependencyUpgrader = new ClassNameDependencyUpgrader(
			"com.liferay.portlet.polls.model.PollsChoice",
			PollsChoice.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();

		classNameDependencyUpgrader = new ClassNameDependencyUpgrader(
			"com.liferay.portlet.polls.model.PollsQuestion",
			PollsQuestion.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();

		classNameDependencyUpgrader = new ClassNameDependencyUpgrader(
			"com.liferay.portlet.polls.model.PollsVote",
			PollsVote.class.getName(), classNameDependencies);

		classNameDependencyUpgrader.upgrade();
	}

}