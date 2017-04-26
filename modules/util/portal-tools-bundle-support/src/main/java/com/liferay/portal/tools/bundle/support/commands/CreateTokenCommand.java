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

package com.liferay.portal.tools.bundle.support.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import com.liferay.portal.tools.bundle.support.internal.util.HttpUtil;

import java.io.Console;
import java.io.File;

/**
 * @author David Truong
 */
@Parameters(
	commandDescription = "Create a Liferay.com download token.",
	commandNames = "createToken"
)
public class CreateTokenCommand implements Command {

	@Override
	public void execute() throws Exception {
		File tokenFile = new File(_cacheDir, ".token");

		if (tokenFile.exists()) {
			System.out.println("Token already exists.");

			return;
		}

		Console console = System.console();

		while ((_emailAddress == null) || _emailAddress.equals("")) {
			_emailAddress = console.readLine("Email Address: ");
		}

		while ((_password == null) || _password.equals("")) {
			_password = console.readPassword("Password: ");
		}

		HttpUtil.createToken(
			_emailAddress, new String(_password), _cacheDir.toPath());
	}

	public File getCacheDir() {
		return _cacheDir;
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public char[] getPassword() {
		return _password;
	}

	public void setCacheDir(File cacheDir) {
		_cacheDir = cacheDir;
	}

	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	public void setPassword(char[] password) {
		_password = password;
	}

	@Parameter(
		description = "The directory where to store your liferay tokens.",
		names = "--cache-dir"
	)
	private File _cacheDir = new File(
		System.getProperty("user.home"), ".liferay");

	@Parameter(
		description = "Your liferay.com email", names = {"-e", "--email"}
	)
	private String _emailAddress;

	@Parameter(
		description = "Your liferay.com password", names = {"-p", "--password"}
	)
	private char[] _password;

}