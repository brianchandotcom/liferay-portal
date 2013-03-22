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

package com.liferay.portlet.messageboards.social;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.trash.util.TrashUtil;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 * @author Zsolt Berentey
 */
public class MBActivityInterpreter extends BaseSocialActivityInterpreter {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String getBody(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		MBMessage message = getMessage(activity);

		if (message.getCategoryId() <= 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(serviceContext.getPortalURL());
		sb.append(serviceContext.getPathMain());
		sb.append("/message_boards/find_category?mbCategoryId=");
		sb.append(message.getCategoryId());

		String categoryLink = sb.toString();

		return wrapLink(categoryLink, "go-to-category", serviceContext);
	}

	@Override
	protected String getEntryTitle(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		MBMessage message = getMessage(activity);

		return message.getSubject();
	}

	@Override
	protected String getLink(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		MBMessage message = getMessage(activity);

		MBThread thread = message.getThread();

		if (thread.isInTrash()) {
			PortletURL portletURL = TrashUtil.getViewContentURL(
				MBThread.class.getName(), thread.getThreadId(),
				serviceContext.getRequest());

			return portletURL.toString();
		}

		StringBundler sb = new StringBundler(4);

		sb.append(serviceContext.getPortalURL());
		sb.append(serviceContext.getPathMain());
		sb.append("/message_boards/find_message?messageId=");
		sb.append(message.getMessageId());

		return sb.toString();
	}

	protected MBMessage getMessage(SocialActivity activity) throws Exception {
		if (activity.isClassName(MBThread.class.getName())) {
			MBThread thread = MBThreadLocalServiceUtil.getThread(
				activity.getClassPK());

			return MBMessageLocalServiceUtil.getMessage(
				thread.getRootMessageId());
		}

		return MBMessageLocalServiceUtil.getMessage(activity.getClassPK());
	}

	@Override
	protected String getTitlePattern(
		String groupName, SocialActivity activity) {

		int activityType = activity.getType();

		long receiverUserId = activity.getReceiverUserId();

		if (activityType == MBActivityKeys.ADD_MESSAGE) {
			if (receiverUserId == 0) {
				if (Validator.isNull(groupName)) {
					return "activity-message-boards-add-message";
				}
				else {
					return "activity-message-boards-add-message-in";
				}
			}
			else {
				if (Validator.isNull(groupName)) {
					return "activity-message-boards-reply-message";
				}
				else {
					return "activity-message-boards-reply-message-in";
				}
			}
		}
		else if ((activityType == MBActivityKeys.REPLY_MESSAGE) &&
				 (receiverUserId > 0)) {

			if (Validator.isNull(groupName)) {
				return "activity-message-boards-reply-message";
			}
			else {
				return "activity-message-boards-reply-message-in";
			}
		}
		else if (activityType == SocialActivityConstants.TYPE_MOVE_TO_TRASH) {
			if (Validator.isNull(groupName)) {
				return "activity-message-boards-move-to-trash";
			}
			else {
				return "activity-message-boards-move-to-trash-in";
			}
		}
		else if (activityType ==
					SocialActivityConstants.TYPE_RESTORE_FROM_TRASH) {

			if (Validator.isNull(groupName)) {
				return "activity-message-boards-restore-from-trash";
			}
			else {
				return "activity-message-boards-restore-from-trash-in";
			}
		}

		return null;
	}

	@Override
	protected boolean hasPermissions(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ServiceContext serviceContext)
		throws Exception {

		MBMessage message = getMessage(activity);

		return MBMessagePermission.contains(
			permissionChecker, message.getMessageId(), actionId);
	}

	private static final String[] _CLASS_NAMES =
		{MBMessage.class.getName(), MBThread.class.getName()};

}