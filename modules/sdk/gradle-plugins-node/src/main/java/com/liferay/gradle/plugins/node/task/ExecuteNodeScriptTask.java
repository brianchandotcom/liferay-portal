/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.node.task;

import com.liferay.gradle.plugins.node.internal.util.FileUtil;
import com.liferay.gradle.plugins.node.internal.util.GradleUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Internal;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class ExecuteNodeScriptTask extends ExecuteNodeTask {

	@Override
	public void executeNode() throws Exception {
		List<Object> args = getArgs();

		try {
			setArgs(getCompleteArgs());

			super.executeNode();
		}
		finally {
			setArgs(args);
		}
	}

	@Internal
	public File getScriptFile() {
		File file = GradleUtil.toFile(getProject(), _scriptFile);

		if (file == null) {
			return null;
		}

		return file;
	}

	public void setScriptFile(Object scriptFile) {
		_scriptFile = scriptFile;
	}

	@Internal
	protected List<String> getCompleteArgs() {
		File scriptFile = getScriptFile();

		List<String> args = GradleUtil.toStringList(getArgs());

		if (scriptFile == null) {
			return args;
		}

		List<String> completeArgs = new ArrayList<>();

		completeArgs.add(FileUtil.getAbsolutePath(scriptFile));

		completeArgs.addAll(args);

		return completeArgs;
	}

	private Object _scriptFile;

}