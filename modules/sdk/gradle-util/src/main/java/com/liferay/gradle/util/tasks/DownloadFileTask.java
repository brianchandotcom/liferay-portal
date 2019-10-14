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

package com.liferay.gradle.util.tasks;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Peter Shin
 */
public class DownloadFileTask extends DefaultTask {

	@TaskAction
	public void downloadFile() throws IOException {
		String password = getPassword();
		String username = getUserName();

		if (isShowLoginDialog()) {
			JTextField passwordJTextField = new JPasswordField();
			JTextField usernameJTextField = new JTextField();

			Object[] message = {
				"Username: ", usernameJTextField, "Password: ",
				passwordJTextField
			};

			int option = JOptionPane.showConfirmDialog(
				null, message, "Login", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

			if (option != JOptionPane.OK_OPTION) {
				throw new GradleException("Invalid login");
			}

			password = passwordJTextField.getText();
			username = usernameJTextField.getText();
		}

		FileUtil.get(
			getProject(), getUrl(), username, password, getDestinationFile(),
			false, true);
	}

	@OutputFile
	public File getDestinationFile() {
		return GradleUtil.toFile(getProject(), _destinationFile);
	}

	@Input
	@Optional
	public String getPassword() {
		return GradleUtil.toString(_password);
	}

	@Input
	public String getUrl() {
		return GradleUtil.toString(_url);
	}

	@Input
	@Optional
	public String getUserName() {
		return GradleUtil.toString(_userName);
	}

	@Input
	public boolean isShowLoginDialog() {
		return _showLoginDialog;
	}

	public void setDestinationFile(Object destinationFile) {
		_destinationFile = destinationFile;
	}

	public void setPassword(Object password) {
		_password = password;
	}

	public void setShowLoginDialog(boolean showLoginDialog) {
		_showLoginDialog = showLoginDialog;
	}

	public void setUrl(Object url) {
		_url = url;
	}

	public void setUserName(Object userName) {
		_userName = userName;
	}

	private Object _destinationFile;
	private Object _password;
	private boolean _showLoginDialog;
	private Object _url;
	private Object _userName;

}