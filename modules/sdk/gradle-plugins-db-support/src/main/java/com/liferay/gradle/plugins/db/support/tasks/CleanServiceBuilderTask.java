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

package com.liferay.gradle.plugins.db.support.tasks;

import com.liferay.gradle.plugins.db.support.internal.util.GradleUtil;
import com.liferay.gradle.util.FileUtil;

import java.io.File;

import java.util.List;

import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;

/**
 * @author Andrea Di Giorgi
 */
public class CleanServiceBuilderTask extends BaseDBSupportTask {

	@Override
	public String getCommand() {
		return "clean-service-builder";
	}

	@InputFile
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