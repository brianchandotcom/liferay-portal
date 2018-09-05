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

package com.liferay.message.boards.web.internal.social;

import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.social.MBActivityKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.social.kernel.model.BaseSocialActivityInterpreter;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.model.SocialActivityInterpreter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 * @author Zsolt Berentey
 */
@Component(
	property = "javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS,
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
	protected ResourceBundleLoader getResourceBundleLoader() {
		return _resourceBundleLoader;
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

				return "activity-message-boards-message-add-message-in";
			}
			else {
				if (Validator.isNull(groupName)) {
					return "activity-message-boards-message-reply-message";
				}

				return "activity-message-boards-message-reply-message-in";
			}
		}
		else if ((activityType == MBActivityKeys.REPLY_MESSAGE) &&
				 (receiverUserId > 0)) {

			if (Validator.isNull(groupName)) {
				return "activity-message-boards-message-reply-message";
			}

			return "activity-message-boards-message-reply-message-in";
		}
		else if (activityType == MBActivityKeys.UPDATE_MESSAGE) {
			if (Validator.isNull(groupName)) {
				return "activity-message-boards-message-update-message";
			}

			return "activity-message-boards-message-update-message-in";
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

		return _messageModelResourcePermission.contains(
			permissionChecker, message.getMessageId(), actionId);
	}

	private static final String[] _CLASS_NAMES = {MBMessage.class.getName()};

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.message.boards.model.MBMessage)"
	)
	private ModelResourcePermission<MBMessage> _messageModelResourcePermission;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.message.boards.web)"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;

}