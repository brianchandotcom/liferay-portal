/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.task;

import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.portal.tools.bundle.support.constants.BundleSupportConstants;

import java.io.File;

import java.net.URL;

import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

/**
 * @author     Andrea Di Giorgi
 * @author     Gregory Amerson
 * @deprecated The token is no longer in use.
 */
@Deprecated
public class CreateTokenTask extends DefaultTask {

	@TaskAction
	public void createToken() throws Exception {
		Logger logger = getLogger();

		if (logger.isWarnEnabled()) {
			logger.warn(
				"The CreateTokenTask is deprecated and no longer in use.");
		}
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

	@InputFile
	@Optional
	public File getPasswordFile() {
		return GradleUtil.toFile(getProject(), _passwordFile);
	}

	@InputFiles
	public File getTokenFile() {
		return GradleUtil.toFile(getProject(), _tokenFile);
	}

	@Input
	public URL getTokenUrl() {
		return GradleUtil.toURL(_tokenUrl);
	}

	@Input
	public boolean isForce() {
		return GradleUtil.toBoolean(_force);
	}

	public void setEmailAddress(Object emailAddress) {
		_emailAddress = emailAddress;
	}

	public void setForce(Object force) {
		_force = force;
	}

	public void setPassword(Object password) {
		_password = password;
	}

	public void setPasswordFile(Object passwordFile) {
		_passwordFile = passwordFile;
	}

	public void setTokenFile(Object tokenFile) {
		_tokenFile = tokenFile;
	}

	public void setURL(Object tokenUrl) {
		_tokenUrl = tokenUrl;
	}

	private Object _emailAddress;
	private Object _force;
	private Object _password;
	private Object _passwordFile;
	private Object _tokenFile = BundleSupportConstants.DEFAULT_TOKEN_FILE;
	private Object _tokenUrl = BundleSupportConstants.DEFAULT_TOKEN_URL;

}