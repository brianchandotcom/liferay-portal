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

package com.liferay.gradle.plugins.workspace.tasks;

import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.portal.tools.bundle.support.commands.CreateTokenCommand;
import com.liferay.portal.tools.bundle.support.constants.BundleSupportConstants;

import java.io.File;

import java.net.URL;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
public class CreateTokenTask extends DefaultTask {

	@TaskAction
	public void createToken() throws Exception {
		CreateTokenCommand createTokenCommand = new CreateTokenCommand();

		createTokenCommand.setEmailAddress(getEmailAddress());
		createTokenCommand.setPassword(getPassword());
		createTokenCommand.setTokenFile(getTokenFile());
		createTokenCommand.setTokenUrl(getTokenUrl());

		createTokenCommand.execute();
	}

	@Input
	@Optional
	public String getEmailAddress() {
		return GradleUtil.toString(_emailAddress);
	}

	@Input
	@Optional
	public String getPassword() {
		return GradleUtil.toString(_password);
	}

	@Input
	public File getTokenFile() {
		return GradleUtil.toFile(getProject(), _tokenFile);
	}

	@Input
	public URL getTokenUrl() {
		return GradleUtil.toURL(_tokenUrl);
	}

	public void setEmailAddress(Object emailAddress) {
		_emailAddress = emailAddress;
	}

	public void setPassword(Object password) {
		_password = password;
	}

	@Input
	public void setTokenFile(Object tokenFile) {
		_tokenFile = tokenFile;
	}

	public void setURL(Object tokenUrl) {
		_tokenUrl = tokenUrl;
	}

	private Object _emailAddress;
	private Object _password;
	private Object _tokenFile = BundleSupportConstants.DEFAULT_TOKEN_FILE;
	private Object _tokenUrl = BundleSupportConstants.DEFAULT_TOKEN_URL;

}