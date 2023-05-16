/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.gulp;

import com.liferay.gradle.plugins.node.task.ExecuteNodeScriptTask;
import com.liferay.gradle.util.GradleUtil;

import java.util.List;

import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;

/**
 * @author     David Truong
 * @author     Andrea Di Giorgi
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@CacheableTask
@Deprecated
public class ExecuteGulpTask extends ExecuteNodeScriptTask {

	public ExecuteGulpTask() {
		setScriptFile("node_modules/gulp/bin/gulp.js");
	}

	@Input
	public String getGulpCommand() {
		return GradleUtil.toString(_gulpCommand);
	}

	public void setGulpCommand(Object gulpCommand) {
		_gulpCommand = gulpCommand;
	}

	@Internal
	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		completeArgs.add(getGulpCommand());

		return completeArgs;
	}

	private Object _gulpCommand;

}