/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;

/**
 * @author Bruno Farache
 */
public class CurrentUserIdStrategy implements UserIdStrategy {

	public CurrentUserIdStrategy(User user) {
		_user = user;
	}

	@Override
	public long getUserId(String userUuid) {
		if (Validator.isNull(userUuid)) {
			return _user.getUserId();
		}

		try {
			User user = UserLocalServiceUtil.getUserByUuidAndCompanyId(
				userUuid, _user.getCompanyId());

			return user.getUserId();
		}
		catch (Exception e) {
			return _user.getUserId();
		}
	}

	private User _user;

}