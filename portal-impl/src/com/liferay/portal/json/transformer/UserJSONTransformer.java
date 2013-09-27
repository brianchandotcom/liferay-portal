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

package com.liferay.portal.json.transformer;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.permission.UserPermissionUtil;

/**
 * @author Igor Spasic
 */
public class UserJSONTransformer extends FlexjsonObjectJSONTransformer {

	@Override
	public void transform(Object object) {
		User user = (User)object;

		boolean hidePrivateUserData = true;

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker != null) {
			boolean hasViewPermission = UserPermissionUtil.contains(
				permissionChecker, user.getUserId(), ActionKeys.VIEW);

			long userId = permissionChecker.getUserId();

			if (hasViewPermission || (user.getUserId() == userId)) {
				hidePrivateUserData = false;
			}
		}

		if (hidePrivateUserData) {
			user.setPasswordUnencrypted(StringPool.BLANK);
			user.setReminderQueryQuestion(StringPool.BLANK);
			user.setReminderQueryAnswer(StringPool.BLANK);
			user.setEmailAddress(StringPool.BLANK);
			user.setFacebookId(0);
			user.setComments(StringPool.BLANK);
		}

		super.transform(object);
	}

}