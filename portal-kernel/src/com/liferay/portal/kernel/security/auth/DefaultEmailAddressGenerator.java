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

package com.liferay.portal.kernel.security.auth;

import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Wesley Gong
 */
public class DefaultEmailAddressGenerator implements EmailAddressGenerator {

	@Override
	public String generate(long companyId, long userId) {
		return userId + UserConstants.USERS_EMAIL_ADDRESS_AUTO_SUFFIX;
	}

	@Override
	public boolean isFake(String emailAddress) {
		if (Validator.isNull(emailAddress) ||
			StringUtil.endsWith(
				emailAddress, UserConstants.USERS_EMAIL_ADDRESS_AUTO_SUFFIX)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isGenerated(String emailAddress) {
		return isFake(emailAddress);
	}

}