/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.social;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;

/**
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 */
public class MBActivityInterpreter extends BaseSocialActivityInterpreter {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected SocialActivityFeedEntry doInterpret(
			SocialActivity activity, ThemeDisplay themeDisplay)
		throws Exception {

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!MBMessagePermission.contains(
				permissionChecker, activity.getClassPK(), ActionKeys.VIEW)) {

			return null;
		}

		String groupName = StringPool.BLANK;

		if (activity.getGroupId() != themeDisplay.getScopeGroupId()) {
			groupName = getGroupName(activity.getGroupId(), themeDisplay);
		}

		String creatorUserName = getUserName(
			activity.getUserId(), themeDisplay);
		String receiverUserName = getUserName(
			activity.getReceiverUserId(), themeDisplay);

		int activityType = activity.getType();

		// Link

		MBMessage message = MBMessageLocalServiceUtil.getMessage(
			activity.getClassPK());

		StringBundler sb = new StringBundler(4);

		sb.append(themeDisplay.getPortalURL());
		sb.append(themeDisplay.getPathMain());
		sb.append("/message_boards/find_message?messageId=");
		sb.append(message.getMessageId());

		String link = sb.toString();

		// Title

		String titlePattern = null;

		if (activityType == MBActivityKeys.ADD_MESSAGE) {
			if (activity.getReceiverUserId() == 0) {
				if (Validator.isNull(groupName)) {
					titlePattern = "activity-message-boards-add-message";
				}
				else {
					titlePattern = "activity-message-boards-add-message-in";
				}
			}
			else {
				if (Validator.isNull(groupName)) {
					titlePattern = "activity-message-boards-reply-message";
				}
				else {
					titlePattern = "activity-message-boards-reply-message-in";
				}
			}
		}
		else if ((activityType == MBActivityKeys.REPLY_MESSAGE) &&
				 (activity.getReceiverUserId() > 0)) {

			if (Validator.isNull(groupName)) {
				titlePattern = "activity-message-boards-reply-message";
			}
			else {
				titlePattern = "activity-message-boards-reply-message-in";
			}
		}

		String messageSubject = getValue(
			activity.getExtraData(), "title", message.getSubject());

		Object[] titleArguments = new Object[] {
			groupName, creatorUserName, receiverUserName,
			wrapLink(link, messageSubject)
		};

		String title = themeDisplay.translate(titlePattern, titleArguments);

		// Body

		String body = StringPool.BLANK;

		if (message.getCategoryId() > 0) {
			sb.setIndex(0);

			sb.append(themeDisplay.getPortalURL());
			sb.append(themeDisplay.getPathMain());
			sb.append("/message_boards/find_category?mbCategoryId=");
			sb.append(message.getCategoryId());

			String categoryLink = sb.toString();

			body = wrapLink(categoryLink, "go-to-category", themeDisplay);
		}

		return new SocialActivityFeedEntry(link, title, body);
	}

	private static final String[] _CLASS_NAMES = new String[] {
		MBMessage.class.getName()
	};

}