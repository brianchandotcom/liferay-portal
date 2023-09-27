/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.node.task;

import com.liferay.gradle.plugins.node.internal.util.GradleUtil;

import java.util.List;

import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;

/**
 * @author David Truong
 * @author Peter Shin
 */
@CacheableTask
public class PackageRunTask extends ExecutePackageManagerTask {

	@Input
	public String getScriptName() {
		return GradleUtil.toString(_scriptName);
	}

	public void setScriptName(Object scriptName) {
		_scriptName = scriptName;
	}

	@Internal
	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		if (isUseNpm()) {
			completeArgs.add("run-script");
		}
		else {
			completeArgs.add("run");
		}

		completeArgs.add(getScriptName());

		return completeArgs;
	}

	private Object _scriptName;

}