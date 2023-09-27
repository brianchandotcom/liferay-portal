/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector;

/**
 * @author Iván Zaera
 */
public class SharepointConnectionFactory {

	public static SharepointConnection getInstance(
		SharepointConnection.ServerVersion serverVersion, String serverProtocol,
		String serverAddress, int serverPort, String sitePath,
		String libraryName, String libraryPath, String userName,
		String password) {

		return new SharepointConnectionImpl(
			serverVersion, serverProtocol, serverAddress, serverPort, sitePath,
			libraryName, libraryPath, userName, password);
	}

}