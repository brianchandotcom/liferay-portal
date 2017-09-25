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

package com.liferay.users.admin.internal.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true, property = {"key=" + PropsKeys.LOGIN_EVENTS_POST},
	service = LifecycleAction.class
)
public class LoginPostAction extends Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
		throws ActionException {

		try {
			User user = _portal.getUser(request);

			if ((user == null) || user.isSetupComplete()) {
				return;
			}

			boolean termsOfUseRequired = PrefsPropsUtil.getBoolean(
				user.getCompanyId(), PropsKeys.TERMS_OF_USE_REQUIRED,
				PropsValues.TERMS_OF_USE_REQUIRED);

			if (termsOfUseRequired && user.isAgreedToTermsOfUse()) {
				return;
			}

			if (PropsValues.USERS_REMINDER_QUERIES_ENABLED &&
				user.isReminderQueryComplete()) {

				return;
			}

			PasswordPolicy passwordPolicy = user.getPasswordPolicy();

			if (passwordPolicy.isChangeRequired() && !user.isPasswordReset()) {
				return;
			}

			String redirect =
				_portal.getPathMain() + _PATH_PORTAL_SELECT_LANGUAGE;

			response.sendRedirect(redirect);
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	private static final String _PATH_PORTAL_SELECT_LANGUAGE =
		"/portal/select_language";

	@Reference
	private Portal _portal;

}