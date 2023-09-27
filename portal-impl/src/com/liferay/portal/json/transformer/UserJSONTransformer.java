/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.transformer;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONContext;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.permission.UserPermissionUtil;

/**
 * @author Igor Spasic
 */
public class UserJSONTransformer extends ObjectTransformer {

	@Override
	public void transform(JSONContext jsonContext, Object object) {
		User user = (User)object;

		boolean hidePrivateUserData = true;

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((permissionChecker != null) && !user.isGuestUser() &&
			((user.getUserId() == permissionChecker.getUserId()) ||
			 UserPermissionUtil.contains(
				 permissionChecker, user.getUserId(), ActionKeys.VIEW))) {

			hidePrivateUserData = false;
		}

		if (hidePrivateUserData) {
			user.setPasswordUnencrypted(StringPool.BLANK);
			user.setReminderQueryQuestion(StringPool.BLANK);
			user.setReminderQueryAnswer(StringPool.BLANK);
			user.setEmailAddress(StringPool.BLANK);
			user.setFacebookId(0);
			user.setComments(StringPool.BLANK);
		}

		super.transform(jsonContext, object);
	}

}