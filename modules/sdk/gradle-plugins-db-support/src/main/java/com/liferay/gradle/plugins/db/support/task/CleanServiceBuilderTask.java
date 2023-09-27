/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.db.support.task;

import com.liferay.gradle.plugins.db.support.internal.util.GradleUtil;
import com.liferay.gradle.util.FileUtil;

import java.io.File;

import java.util.List;

import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class CleanServiceBuilderTask extends BaseDBSupportTask {

	@Override
	public String getCommand() {
		return "clean-service-builder";
	}

	@InputFile
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getServiceXmlFile() {
		return GradleUtil.toFile(getProject(), _serviceXmlFile);
	}

	@Input
	public String getServletContextName() {
		return GradleUtil.toString(_servletContextName);
	}

	public void setServiceXmlFile(Object serviceXmlFile) {
		_serviceXmlFile = serviceXmlFile;
	}

	public void setServletContextName(Object servletContextName) {
		_servletContextName = servletContextName;
	}

	@Internal
	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		completeArgs.add("--service-xml-file");
		completeArgs.add(FileUtil.getAbsolutePath(getServiceXmlFile()));

		completeArgs.add("--servlet-context-name");
		completeArgs.add(getServletContextName());

		return completeArgs;
	}

	private Object _serviceXmlFile;
	private Object _servletContextName;

}