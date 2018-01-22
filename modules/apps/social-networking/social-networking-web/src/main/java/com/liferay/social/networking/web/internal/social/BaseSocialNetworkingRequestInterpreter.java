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

package com.liferay.social.networking.web.internal.social;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.social.kernel.model.BaseSocialRequestInterpreter;
import com.liferay.social.kernel.model.SocialRelationConstants;
import com.liferay.social.kernel.model.SocialRequest;
import com.liferay.social.kernel.model.SocialRequestFeedEntry;
import com.liferay.social.kernel.service.SocialActivityLocalService;
import com.liferay.social.kernel.service.SocialRelationLocalService;
import com.liferay.social.networking.web.internal.friends.social.FriendsActivityKeys;
import com.liferay.social.networking.web.internal.friends.social.FriendsRequestKeys;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseSocialNetworkingRequestInterpreter
	extends BaseSocialRequestInterpreter {

	@Override
	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected SocialRequestFeedEntry doInterpret(
			SocialRequest request, ThemeDisplay themeDisplay)
		throws Exception {

		String creatorUserName = getUserName(request.getUserId(), themeDisplay);

		UserLocalService userLocalService = getUserLocalService();

		User creatorUser = userLocalService.getUserById(request.getUserId());

		int requestType = request.getType();

		// Title

		String title = StringPool.BLANK;

		if (requestType == FriendsRequestKeys.ADD_FRIEND) {
			StringBuilder sb = new StringBuilder();

			sb.append("<a href=\"");
			sb.append(themeDisplay.getPortalURL());
			sb.append(themeDisplay.getPathFriendlyURLPublic());
			sb.append(StringPool.SLASH);
			sb.append(creatorUser.getScreenName());
			sb.append("/profile\">");
			sb.append(creatorUserName);
			sb.append("</a>");

			String creatorUserNameURL = sb.toString();

			title = themeDisplay.translate(
				"request-social-networking-summary-add-friend",
				new Object[] {creatorUserNameURL});
		}

		// Body

		String body = StringPool.BLANK;

		String extraData = request.getExtraData();

		try {
			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject(
				extraData);

			body = extraDataJSONObject.getString("addFriendMessage");

			if (Validator.isNotNull(body)) {
				body = StringUtil.quote(body);
				body = HtmlUtil.escape(body);
			}
		}
		catch (JSONException jsone) {
			_log.error("Unable to create JSON object from " + extraData, jsone);
		}

		return new SocialRequestFeedEntry(title, body);
	}

	@Override
	protected boolean doProcessConfirmation(
		SocialRequest request, ThemeDisplay themeDisplay) {

		try {
			SocialRelationLocalService socialRelationLocalService =
				getSocialRelationLocalService();

			socialRelationLocalService.addRelation(
				request.getUserId(), request.getReceiverUserId(),
				SocialRelationConstants.TYPE_BI_FRIEND);

			SocialActivityLocalService socialActivityLocalService =
				getSocialActivityLocalService();

			socialActivityLocalService.addActivity(
				request.getUserId(), 0, User.class.getName(),
				request.getUserId(), FriendsActivityKeys.ADD_FRIEND,
				StringPool.BLANK, request.getReceiverUserId());
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return true;
	}

	protected abstract SocialActivityLocalService
		getSocialActivityLocalService();

	protected abstract SocialRelationLocalService
		getSocialRelationLocalService();

	protected abstract UserLocalService getUserLocalService();

	private static final String[] _CLASS_NAMES = {User.class.getName()};

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSocialNetworkingRequestInterpreter.class.getName());

}