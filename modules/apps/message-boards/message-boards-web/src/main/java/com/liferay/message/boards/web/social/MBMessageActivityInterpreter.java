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

package com.liferay.message.boards.web.social;

import com.liferay.message.boards.web.constants.MBPortletKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.messageboards.social.MBActivityKeys;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityInterpreter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 * @author Zsolt Berentey
 */
@Component(
	property = {"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS},
	service = SocialActivityInterpreter.class
)
public class MBMessageActivityInterpreter
	extends BaseSocialActivityInterpreter {

	@Override
	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String getBody(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		MBMessage message = _mbMessageLocalService.getMessage(
			activity.getClassPK());

		if (message.getCategoryId() <= 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(serviceContext.getPortalURL());
		sb.append(serviceContext.getPathMain());
		sb.append("/message_boards/find_category?mbCategoryId=");
		sb.append(message.getCategoryId());

		String categoryLink = sb.toString();

		categoryLink = addNoSuchEntryRedirect(
			categoryLink, MBCategory.class.getName(), message.getCategoryId(),
			serviceContext);

		return wrapLink(categoryLink, "go-to-category", serviceContext);
	}

	@Override
	protected String getPath(
		SocialActivity activity, ServiceContext serviceContext) {

		return "/message_boards/find_message?messageId=" +
			activity.getClassPK();
	}

	@Override
	protected Object[] getTitleArguments(
		String groupName, SocialActivity activity, String link, String title,
		ServiceContext serviceContext) {

		String userName = getUserName(activity.getUserId(), serviceContext);
		String receiverUserName = StringPool.BLANK;

		if (activity.getReceiverUserId() > 0) {
			receiverUserName = getUserName(
				activity.getReceiverUserId(), serviceContext);
		}

		return new Object[] {
			groupName, userName, receiverUserName, wrapLink(link, title)
		};
	}

	@Override
	protected String getTitlePattern(
		String groupName, SocialActivity activity) {

		int activityType = activity.getType();

		long receiverUserId = activity.getReceiverUserId();

		if (activityType == MBActivityKeys.ADD_MESSAGE) {
			if (receiverUserId == 0) {
				if (Validator.isNull(groupName)) {
					return "activity-message-boards-message-add-message";
				}
				else {
					return "activity-message-boards-message-add-message-in";
				}
			}
			else {
				if (Validator.isNull(groupName)) {
					return "activity-message-boards-message-reply-message";
				}
				else {
					return "activity-message-boards-message-reply-message-in";
				}
			}
		}
		else if ((activityType == MBActivityKeys.REPLY_MESSAGE) &&
				 (receiverUserId > 0)) {

			if (Validator.isNull(groupName)) {
				return "activity-message-boards-message-reply-message";
			}
			else {
				return "activity-message-boards-message-reply-message-in";
			}
		}

		return null;
	}

	@Override
	protected boolean hasPermissions(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ServiceContext serviceContext)
		throws Exception {

		MBMessage message = _mbMessageLocalService.getMessage(
			activity.getClassPK());

		return MBMessagePermission.contains(
			permissionChecker, message.getMessageId(), actionId);
	}

	@Reference(unbind = "-")
	protected void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	private static final String[] _CLASS_NAMES = {MBMessage.class.getName()};

	private volatile MBMessageLocalService _mbMessageLocalService;

}