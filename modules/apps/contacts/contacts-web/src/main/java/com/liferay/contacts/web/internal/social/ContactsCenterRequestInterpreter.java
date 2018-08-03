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

package com.liferay.contacts.web.internal.social;

import com.liferay.contacts.web.internal.constants.ContactsPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.social.kernel.model.BaseSocialRequestInterpreter;
import com.liferay.social.kernel.model.SocialRelationConstants;
import com.liferay.social.kernel.model.SocialRequest;
import com.liferay.social.kernel.model.SocialRequestFeedEntry;
import com.liferay.social.kernel.model.SocialRequestInterpreter;
import com.liferay.social.kernel.service.SocialActivityLocalService;
import com.liferay.social.kernel.service.SocialRelationLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Hai Yu
 */
@Component(
	property = "javax.portlet.name=" + ContactsPortletKeys.CONTACTS_CENTER,
	service = SocialRequestInterpreter.class
)
public class ContactsCenterRequestInterpreter
	extends BaseSocialRequestInterpreter {

	@Override
	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected SocialRequestFeedEntry doInterpret(
			SocialRequest request, ThemeDisplay themeDisplay)
		throws Exception {

		if (request.getType() != SocialRelationConstants.TYPE_BI_CONNECTION) {
			return new SocialRequestFeedEntry(
				StringPool.BLANK, StringPool.BLANK);
		}

		StringBundler sb = new StringBundler(8);

		sb.append("<a href=\"");
		sb.append(themeDisplay.getPortalURL());
		sb.append(themeDisplay.getPathFriendlyURLPublic());
		sb.append(StringPool.SLASH);

		User creatorUser = _userLocalService.getUserById(request.getUserId());

		sb.append(creatorUser.getScreenName());

		sb.append("/profile\">");
		sb.append(getUserName(request.getUserId(), themeDisplay));
		sb.append("</a>");

		String creatorUserNameURL = sb.toString();

		String title = themeDisplay.translate(
			"request-social-networking-summary-add-connection",
			new Object[] {creatorUserNameURL});

		return new SocialRequestFeedEntry(title, StringPool.BLANK);
	}

	@Override
	protected boolean doProcessConfirmation(
		SocialRequest request, ThemeDisplay themeDisplay) {

		try {
			_socialRelationLocalService.addRelation(
				request.getUserId(), request.getReceiverUserId(),
				SocialRelationConstants.TYPE_BI_CONNECTION);

			_socialActivityLocalService.addActivity(
				request.getUserId(), 0, User.class.getName(),
				request.getUserId(), SocialRelationConstants.TYPE_BI_CONNECTION,
				StringPool.BLANK, request.getReceiverUserId());
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return true;
	}

	private static final String[] _CLASS_NAMES = {User.class.getName()};

	private static final Log _log = LogFactoryUtil.getLog(
		ContactsCenterRequestInterpreter.class);

	@Reference
	private SocialActivityLocalService _socialActivityLocalService;

	@Reference
	private SocialRelationLocalService _socialRelationLocalService;

	@Reference
	private UserLocalService _userLocalService;

}