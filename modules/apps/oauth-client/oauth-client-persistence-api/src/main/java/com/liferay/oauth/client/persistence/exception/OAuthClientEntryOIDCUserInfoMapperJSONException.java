/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class OAuthClientEntryOIDCUserInfoMapperJSONException
	extends PortalException {

	public OAuthClientEntryOIDCUserInfoMapperJSONException() {
	}

	public OAuthClientEntryOIDCUserInfoMapperJSONException(String msg) {
		super(msg);
	}

	public OAuthClientEntryOIDCUserInfoMapperJSONException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public OAuthClientEntryOIDCUserInfoMapperJSONException(
		Throwable throwable) {

		super(throwable);
	}

}