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

package com.liferay.gradle.plugins.jasper.jspc;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gradle.api.GradleException;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;

/**
 * @author Andrea Di Giorgi
 */
public class CompileJSPTask extends JavaExec implements CompileJSPSpec {

	public CompileJSPTask() {
		setMain("com.liferay.jasper.jspc.JspC");
	}

	@Override
	public void exec() {
		super.setClasspath(getClasspath());

		setArgs(getCompleteArgs());
		setSystemProperties(getCompleteSystemProperties());

		OutputStream taskErrorOutput = getErrorOutput();

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		try {
			setErrorOutput(byteArrayOutputStream);

			super.exec();

			String output = byteArrayOutputStream.toString();

			if (output.contains("JasperException")) {
				throw new GradleException("JSPs failed to compile.");
			}
		}
		finally {
			try {
				byteArrayOutputStream.writeTo(taskErrorOutput);
			}
			catch (IOException ioe) {
				throw new GradleException(ioe.getMessage(), ioe);
			}

			setErrorOutput(taskErrorOutput);
		}
	}

	@Override
	public FileCollection getClasspath() {
		return GradleUtil.getConfiguration(
			getProject(), JspCPlugin.CONFIGURATION_NAME);
	}

	@OutputDirectory
	public File getDestinationDir() {
		return GradleUtil.toFile(getProject(), _destinationDir);
	}

	@InputDirectory
	@Optional
	@Override
	public File getPortalDir() {
		return GradleUtil.toFile(getProject(), _portalDir);
	}

	@InputDirectory
	@Override
	public File getWebAppDir() {
		return GradleUtil.toFile(getProject(), _webAppDir);
	}

	@Input
	@Override
	public boolean isModuleWeb() {
		return _moduleWeb;
	}

	@Override
	public JavaExec setClasspath(FileCollection classpath) {
		throw new UnsupportedOperationException();
	}

	public void setDestinationDir(Object destinationDir) {
		_destinationDir = destinationDir;
	}

	@Override
	public void setModuleWeb(boolean moduleWeb) {
		_moduleWeb = moduleWeb;
	}

	@Override
	public void setPortalDir(Object portalDir) {
		_portalDir = portalDir;
	}

	@Override
	public JavaExec setStandardOutput(OutputStream outputStream) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setWebAppDir(Object webAppDir) {
		_webAppDir = webAppDir;
	}

	protected List<String> getCompleteArgs() {
		List<String> completeArgs = new ArrayList<>(getArgs());

		completeArgs.add("-d");
		completeArgs.add(
			FileUtil.relativize(getDestinationDir(), getWorkingDir()));

		completeArgs.add("-webapp");
		completeArgs.add(FileUtil.getAbsolutePath(getWebAppDir()));

		return completeArgs;
	}

	protected Map<String, Object> getCompleteSystemProperties() {
		Map<String, Object> completeSystemProperties = new HashMap<>(
			getSystemProperties());

		completeSystemProperties.put("jspc.module.web", isModuleWeb());

		File portalDir = getPortalDir();

		if (portalDir != null) {
			completeSystemProperties.put("jspc.portal.dir", portalDir);
		}

		return completeSystemProperties;
	}

	private Object _destinationDir;
	private boolean _moduleWeb;
	private Object _portalDir;
	private Object _webAppDir;

}