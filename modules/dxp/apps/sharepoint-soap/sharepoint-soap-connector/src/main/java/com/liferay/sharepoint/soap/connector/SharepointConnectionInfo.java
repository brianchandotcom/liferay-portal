/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.soap.connector;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sharepoint.soap.connector.operation.URLUtil;

import java.net.URL;

/**
 * @author Iván Zaera
 */
public class SharepointConnectionInfo {

	public SharepointConnectionInfo(
			SharepointConnection.ServerVersion serverVersion,
			String serverProtocol, String serverAddress, int serverPort,
			String sitePath, String libraryName, String libraryPath,
			String username, String password)
		throws SharepointRuntimeException {

		validate(sitePath, username, password);

		_serverVersion = serverVersion;
		_serverProtocol = serverProtocol;
		_serverAddress = serverAddress;
		_serverPort = serverPort;
		_sitePath = sitePath;
		_libraryName = libraryName;
		_libraryPath = libraryPath;
		_username = username;
		_password = password;

		_serviceURL = URLUtil.toURL(
			StringBundler.concat(
				_serverProtocol, "://", _serverAddress, StringPool.COLON,
				_serverPort, _sitePath, StringPool.SLASH));
	}

	public String getLibraryName() {
		return _libraryName;
	}

	public String getLibraryPath() {
		return _libraryPath;
	}

	public String getPassword() {
		return _password;
	}

	public String getServerAddress() {
		return _serverAddress;
	}

	public int getServerPort() {
		return _serverPort;
	}

	public SharepointConnection.ServerVersion getServerVersion() {
		return _serverVersion;
	}

	public URL getServiceURL() {
		return _serviceURL;
	}

	public String getSitePath() {
		return _sitePath;
	}

	public String getUsername() {
		return _username;
	}

	protected void validate(String sitePath, String username, String password) {
		if (sitePath.equals(StringPool.BLANK)) {
			return;
		}

		if (sitePath.equals(StringPool.SLASH)) {
			throw new IllegalArgumentException(
				"Use an empty string instead of a forward slash for the root " +
					"site path");
		}

		if (!sitePath.startsWith(StringPool.SLASH)) {
			throw new IllegalArgumentException(
				"Site path must start with a forward slash");
		}

		if (!sitePath.equals(StringPool.SLASH) &&
			sitePath.endsWith(StringPool.SLASH)) {

			throw new IllegalArgumentException(
				"Site path must not end with a forward slash");
		}

		if (Validator.isNull(username)) {
			throw new SharepointRuntimeException("Username is null");
		}

		if (Validator.isNull(password)) {
			throw new SharepointRuntimeException("Password is null");
		}
	}

	private final String _libraryName;
	private final String _libraryPath;
	private final String _password;
	private final String _serverAddress;
	private final int _serverPort;
	private final String _serverProtocol;
	private final SharepointConnection.ServerVersion _serverVersion;
	private final URL _serviceURL;
	private final String _sitePath;
	private final String _username;

}