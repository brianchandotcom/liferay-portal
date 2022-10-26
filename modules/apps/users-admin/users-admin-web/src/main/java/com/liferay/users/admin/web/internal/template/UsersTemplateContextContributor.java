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

package com.liferay.users.admin.web.internal.template;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 */
@Component(
	property = "type=" + TemplateContextContributor.TYPE_THEME,
	service = TemplateContextContributor.class
)
public class UsersTemplateContextContributor
	implements TemplateContextContributor {

	@Override
	public void prepare(
		Map<String, Object> contextObjects,
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		User user1 = themeDisplay.getUser();

		contextObjects.put("is_default_user", user1.isDefaultUser());

		try {
			Contact contact = user1.getContact();

			contextObjects.put("is_female", !contact.isMale());
			contextObjects.put("is_male", contact.isMale());
			contextObjects.put("user_birthday", contact.getBirthday());
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		contextObjects.put("is_setup_complete", user1.isSetupComplete());
		contextObjects.put("language", themeDisplay.getLanguageId());
		contextObjects.put("language_id", user1.getLanguageId());
		contextObjects.put("user_comments", user1.getComments());
		contextObjects.put("user_email_address", user1.getEmailAddress());
		contextObjects.put("user_first_name", user1.getFirstName());
		contextObjects.put(
			"user_greeting", HtmlUtil.escape(user1.getGreeting()));
		contextObjects.put("user_id", user1.getUserId());
		contextObjects.put("user_initialized", true);
		contextObjects.put("user_last_login_ip", user1.getLastLoginIP());
		contextObjects.put("user_last_name", user1.getLastName());
		contextObjects.put("user_login_ip", user1.getLoginIP());
		contextObjects.put("user_middle_name", user1.getMiddleName());
		contextObjects.put("user_name", user1.getFullName());

		Group group = themeDisplay.getSiteGroup();

		if (group.isUser()) {
			try {
				User user2 = _userLocalService.getUserById(group.getClassPK());

				contextObjects.put("user2", user2);
			}
			catch (PortalException portalException) {
				_log.error(portalException);
			}
		}

		contextObjects.put(
			"w3c_language_id",
			LocaleUtil.toW3cLanguageId(themeDisplay.getLanguageId()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UsersTemplateContextContributor.class);

	@Reference
	private UserLocalService _userLocalService;

}