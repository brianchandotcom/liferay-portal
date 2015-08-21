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

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import groovy.lang.Writable;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.groovy.runtime.EncodingGroovyMethods;

import org.gradle.api.Project;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;

/**
 * @author Andrea Di Giorgi
 */
public class PublishNodeModuleTask extends ExecuteNpmTask {

	@Override
	public void executeNode() {
		setArgs(getCompleteArgs());

		File npmrcFile = null;

		try {
			if (isSyncVersion()) {
				syncVersion();
			}

			npmrcFile = createNpmrcFile();

			super.executeNode();
		}
		catch (IOException ioe) {
		}
		finally {
			if (npmrcFile != null) {
				npmrcFile.delete();
			}
		}
	}

	@Input
	public String getNpmEmailAddress() {
		return GradleUtil.toString(_npmEmailAddress);
	}

	@Input
	public String getNpmPassword() {
		return GradleUtil.toString(_npmPassword);
	}

	@Input
	public String getNpmUserName() {
		return GradleUtil.toString(_npmUserName);
	}

	@InputDirectory
	@Override
	public File getWorkingDir() {
		return super.getWorkingDir();
	}

	@Input
	public boolean isSyncVersion() {
		return _syncVersion;
	}

	public void setNpmEmailAddress(Object npmEmailAddress) {
		_npmEmailAddress = npmEmailAddress;
	}

	public void setNpmPassword(Object npmPassword) {
		_npmPassword = npmPassword;
	}

	public void setNpmUserName(Object npmUserName) {
		_npmUserName = npmUserName;
	}

	public void setSyncVersion(boolean syncVersion) {
		_syncVersion = syncVersion;
	}

	protected File createNpmrcFile() throws IOException {
		File npmrcFile = new File(getWorkingDir(), ".npmrc");

		List<String> npmrcContents = new ArrayList<>(2);

		npmrcContents.add("_auth = " + getNpmAuth());
		npmrcContents.add("email = " + getNpmEmailAddress());

		FileUtil.write(npmrcFile, npmrcContents);

		return npmrcFile;
	}

	protected List<Object> getCompleteArgs() {
		List<Object> completeArgs = new ArrayList<>();

		completeArgs.add("publish");

		completeArgs.addAll(getArgs());

		return completeArgs;
	}

	protected String getNpmAuth() {
		String auth = getNpmUserName() + ":" + getNpmPassword();

		Writable writable = EncodingGroovyMethods.encodeBase64(auth.getBytes());

		return writable.toString();
	}

	protected void syncVersion() throws IOException {
		Project project = getProject();

		File packageJsonFile = new File(getWorkingDir(), "package.json");

		String packageJson = new String(
			Files.readAllBytes(packageJsonFile.toPath()),
			StandardCharsets.UTF_8);

		String version = GradleUtil.toString(project.getVersion());

		packageJson = packageJson.replaceFirst(
			"\"version\": \".+\"", "\"version\": \"" + version + "\"");

		Files.write(
			packageJsonFile.toPath(),
			packageJson.getBytes(StandardCharsets.UTF_8));
	}

	private Object _npmEmailAddress;
	private Object _npmPassword;
	private Object _npmUserName;
	private boolean _syncVersion = true;

}