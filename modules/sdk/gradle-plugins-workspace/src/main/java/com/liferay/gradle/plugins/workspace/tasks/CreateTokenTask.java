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
import com.liferay.gradle.util.Validator;
import com.liferay.portal.tools.bundle.support.commands.CreateTokenCommand;
import com.liferay.portal.tools.bundle.support.constants.BundleSupportConstants;

import groovy.lang.Closure;

import java.io.File;

import java.net.URL;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.gradle.api.AntBuilder;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
public class CreateTokenTask extends DefaultTask {

	public CreateTokenTask() {
		onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					CreateTokenTask createTokenTask = (CreateTokenTask)task;

					File tokenFile = createTokenTask.getTokenFile();

					if (createTokenTask.isForce() || !tokenFile.exists()) {
						return true;
					}

					return false;
				}

			});
	}

	@TaskAction
	public void createToken() throws Exception {
		_setCredentials();

		CreateTokenCommand createTokenCommand = new CreateTokenCommand();

		createTokenCommand.setEmailAddress(getEmailAddress());
		createTokenCommand.setForce(isForce());
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

	@Input
	public void setTokenFile(Object tokenFile) {
		_tokenFile = tokenFile;
	}

	public void setURL(Object tokenUrl) {
		_tokenUrl = tokenUrl;
	}

	private String _readInput(
		final AntBuilder antBuilder, String message, String propertySuffix,
		final boolean secure) {

		String propertyName = getName() + "." + propertySuffix;

		Map<String, Object> args = new HashMap<>();

		args.put("addproperty", propertyName);
		args.put("message", message);

		Closure<Void> closure = new Closure<Void>(antBuilder) {

			@SuppressWarnings("unused")
			public void doCall() {
				if (secure) {
					antBuilder.invokeMethod(
						"handler", Collections.singletonMap("type", "secure"));
				}
			}

		};

		antBuilder.invokeMethod("input", new Object[] {args, closure});

		return (String)antBuilder.getProperty(propertyName);
	}

	private void _setCredentials() {
		String emailAddress = getEmailAddress();
		String password = getPassword();

		if (Validator.isNotNull(emailAddress) &&
			Validator.isNotNull(password)) {

			return;
		}

		Project project = getProject();

		AntBuilder antBuilder = project.createAntBuilder();

		while (Validator.isNull(emailAddress)) {
			emailAddress = _readInput(
				antBuilder, "Email Address:", "email.address", false);
		}

		setEmailAddress(emailAddress);

		while (Validator.isNull(password)) {
			password = _readInput(antBuilder, "Password:", "password", true);
		}

		setPassword(password);
	}

	private Object _emailAddress;
	private Object _force;
	private Object _password;
	private Object _tokenFile = BundleSupportConstants.DEFAULT_TOKEN_FILE;
	private Object _tokenUrl = BundleSupportConstants.DEFAULT_TOKEN_URL;

}