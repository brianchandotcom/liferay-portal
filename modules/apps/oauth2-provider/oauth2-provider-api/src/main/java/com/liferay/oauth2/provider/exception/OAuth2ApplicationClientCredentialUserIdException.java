/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.exception;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class OAuth2ApplicationClientCredentialUserIdException
	extends PortalException {

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public OAuth2ApplicationClientCredentialUserIdException() {
	}

	public OAuth2ApplicationClientCredentialUserIdException(
		long userId, String userScreenName, long clientCredentialUserId,
		String clientCredentialUserScreenName) {

		super(
			StringBundler.concat(
				"User ", userId, " is not allowed to impersonate user ",
				clientCredentialUserId, " via client credentials grant"));

		_userId = userId;
		_userScreenName = userScreenName;
		_clientCredentialUserId = clientCredentialUserId;
		_clientCredentialUserScreenName = clientCredentialUserScreenName;
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public OAuth2ApplicationClientCredentialUserIdException(String msg) {
		super(msg);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public OAuth2ApplicationClientCredentialUserIdException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public OAuth2ApplicationClientCredentialUserIdException(
		Throwable throwable) {

		super(throwable);
	}

	public long getClientCredentialUserId() {
		return _clientCredentialUserId;
	}

	public String getClientCredentialUserScreenName() {
		return _clientCredentialUserScreenName;
	}

	public long getUserId() {
		return _userId;
	}

	public String getUserScreenName() {
		return _userScreenName;
	}

	private long _clientCredentialUserId;
	private String _clientCredentialUserScreenName;
	private long _userId;
	private String _userScreenName;

}