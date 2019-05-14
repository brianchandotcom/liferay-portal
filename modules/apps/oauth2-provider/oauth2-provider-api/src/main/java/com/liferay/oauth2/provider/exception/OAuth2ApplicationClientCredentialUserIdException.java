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

package com.liferay.oauth2.provider.exception;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class OAuth2ApplicationClientCredentialUserIdException
	extends PortalException {

	public OAuth2ApplicationClientCredentialUserIdException(
		long userId, long clientCredentialUserId) {

		super(
			StringBundler.concat(
				"User ", userId, " is not allowed to impersonate user ",
				clientCredentialUserId, " via client credentials grant"));

		this.userId = userId;
		this.clientCredentialUserId = clientCredentialUserId;
	}

	public long clientCredentialUserId;
	public long userId;

}