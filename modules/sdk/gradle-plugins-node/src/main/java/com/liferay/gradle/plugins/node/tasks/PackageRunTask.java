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

package com.liferay.gradle.plugins.node.tasks;

import com.liferay.gradle.plugins.node.internal.util.GradleUtil;

import java.util.List;

/**
 * @author David Truong
 * @author Peter Shin
 */
public class PackageRunTask extends ExecutePackageManagerTask {

	public String getScriptName() {
		return GradleUtil.toString(_scriptName);
	}

	public void setScriptName(Object scriptName) {
		_scriptName = scriptName;
	}

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