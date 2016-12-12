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
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.Optional;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BaseDBSupportTask extends JavaExec {

	public BaseDBSupportTask() {
		setMain("com.liferay.portal.tools.db.support.DBSupport");
	}

	@Override
	public void exec() {
		setArgs(getCompleteArgs());

		super.exec();
	}

	@Input
	public abstract String getCommand();

	@Input
	@Optional
	public String getPassword() {
		return GradleUtil.toString(_password);
	}

	@InputFile
	@Optional
	public File getPropertiesFile() {
		return GradleUtil.toFile(getProject(), _propertiesFile);
	}

	@Input
	@Optional
	public String getUrl() {
		return GradleUtil.toString(_url);
	}

	@Input
	@Optional
	public String getUserName() {
		return GradleUtil.toString(_userName);
	}

	public void setPassword(Object password) {
		_password = password;
	}

	public void setPropertiesFile(Object propertiesFile) {
		_propertiesFile = propertiesFile;
	}

	public void setUrl(Object url) {
		_url = url;
	}

	public void setUserName(Object userName) {
		_userName = userName;
	}

	protected List<String> getCompleteArgs() {
		List<String> completeArgs = new ArrayList<>();

		GUtil.addToCollection(completeArgs, getArgs());

		String password = getPassword();

		if (Validator.isNotNull(password)) {
			completeArgs.add("--password");
			completeArgs.add(password);
		}

		File propertiesFile = getPropertiesFile();

		if ((propertiesFile != null) && propertiesFile.exists()) {
			completeArgs.add("--properties-file");
			completeArgs.add(FileUtil.getAbsolutePath(propertiesFile));
		}

		String url = getUrl();

		if (Validator.isNotNull(url)) {
			completeArgs.add("--url");
			completeArgs.add(url);
		}

		String userName = getUserName();

		if (Validator.isNotNull(userName)) {
			completeArgs.add("--user-name");
			completeArgs.add(userName);
		}

		completeArgs.add(getCommand());

		return completeArgs;
	}

	private Object _password;
	private Object _propertiesFile;
	private Object _url;
	private Object _userName;

}