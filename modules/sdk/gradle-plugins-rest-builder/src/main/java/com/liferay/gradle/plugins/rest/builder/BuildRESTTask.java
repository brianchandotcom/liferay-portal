/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.rest.builder;

import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;

/**
 * @author Peter Shin
 */
@CacheableTask
public class BuildRESTTask extends JavaExec {

	public BuildRESTTask() {
		Property<String> mainClass = getMainClass();

		mainClass.set("com.liferay.portal.tools.rest.builder.RESTBuilder");

		_forceClientVersionDescription = GradleUtil.getTaskPrefixedProperty(
			this, "forceClientVersionDescription");
		_forcePredictableOperationId = GradleUtil.getTaskPrefixedProperty(
			this, "forcePredictableOperationId");
	}

	@Override
	public void exec() {
		setArgs(_getCompleteArgs());

		super.exec();
	}

	@InputFile
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getCopyrightFile() {
		return GradleUtil.toFile(getProject(), _copyrightFile);
	}

	@Input
	@Optional
	public String getForceClientVersionDescription() {
		return GradleUtil.toString(_forceClientVersionDescription);
	}

	@Input
	@Optional
	public String getForcePredictableOperationId() {
		return GradleUtil.toString(_forcePredictableOperationId);
	}

	@InputDirectory
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getRESTConfigDir() {
		return GradleUtil.toFile(getProject(), _restConfigDir);
	}

	public void setCopyrightFile(Object copyrightFile) {
		_copyrightFile = copyrightFile;
	}

	public void setForceClientVersionDescription(
		Object forceClientVersionDescription) {

		_forceClientVersionDescription = forceClientVersionDescription;
	}

	public void setForcePredictableOperationId(
		Object forcePredictableOperationId) {

		_forcePredictableOperationId = forcePredictableOperationId;
	}

	public void setRESTConfigDir(Object restConfigDir) {
		_restConfigDir = restConfigDir;
	}

	private static void _addArg(List<String> args, String name, File file) {
		if (file != null) {
			_addArg(args, name, file.getAbsolutePath());
		}
	}

	private static void _addArg(List<String> args, String name, String value) {
		if (Validator.isNotNull(value)) {
			args.add(name);
			args.add(value);
		}
	}

	private List<String> _getCompleteArgs() {
		List<String> args = new ArrayList<>(getArgs());

		_addArg(args, "--copyright-file", getCopyrightFile());
		_addArg(args, "--rest-config-dir", getRESTConfigDir());

		String forceClientVersionDescription =
			getForceClientVersionDescription();

		if (forceClientVersionDescription != null) {
			args.add("--force-client-version-description");
			args.add(forceClientVersionDescription);
		}

		String forcePredictableOperationId = getForcePredictableOperationId();

		if (forcePredictableOperationId != null) {
			args.add("--force-predictable-operation-id");
			args.add(forcePredictableOperationId);
		}

		return args;
	}

	private Object _copyrightFile;
	private Object _forceClientVersionDescription;
	private Object _forcePredictableOperationId;
	private Object _restConfigDir;

}