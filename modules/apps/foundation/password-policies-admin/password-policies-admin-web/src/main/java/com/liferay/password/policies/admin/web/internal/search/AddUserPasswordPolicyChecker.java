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

package com.liferay.password.policies.admin.web.internal.search;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.PasswordPolicyRel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.PasswordPolicyRelLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

import javax.portlet.RenderResponse;

/**
 * @author Scott Lee
 */
public class AddUserPasswordPolicyChecker extends EmptyOnClickRowChecker {

	public AddUserPasswordPolicyChecker(
		RenderResponse renderResponse, PasswordPolicy passwordPolicy) {

		super(renderResponse);

		_passwordPolicy = passwordPolicy;
	}

	@Override
	public boolean isChecked(Object obj) {
		User user = (User)obj;

		try {
			return UserLocalServiceUtil.hasPasswordPolicyUser(
				_passwordPolicy.getPasswordPolicyId(), user.getUserId());
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	@Override
	public boolean isDisabled(Object obj) {
		User user = (User)obj;

		if (isChecked(obj)) {
			return true;
		}

		try {
			PasswordPolicyRel passwordPolicyRel =
				PasswordPolicyRelLocalServiceUtil.fetchPasswordPolicyRel(
					User.class.getName(), user.getUserId());

			if ((passwordPolicyRel != null) &&
				(passwordPolicyRel.getPasswordPolicyId() !=
					_passwordPolicy.getPasswordPolicyId())) {

				return true;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddUserPasswordPolicyChecker.class);

	private final PasswordPolicy _passwordPolicy;

}