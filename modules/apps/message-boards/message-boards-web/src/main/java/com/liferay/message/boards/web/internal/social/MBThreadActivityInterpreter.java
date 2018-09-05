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
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.social.kernel.model.BaseSocialActivityInterpreter;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.model.SocialActivityConstants;
import com.liferay.social.kernel.model.SocialActivityInterpreter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Zsolt Berentey
 */
@Component(
	property = "javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS,
	service = SocialActivityInterpreter.class
)
public class MBThreadActivityInterpreter extends BaseSocialActivityInterpreter {

	@Override
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

		categoryLink = addNoSuchEntryRedirect(
			categoryLink, MBCategory.class.getName(), message.getCategoryId(),
			serviceContext);

		return wrapLink(categoryLink, "go-to-category", serviceContext);
	}

	protected MBMessage getMessage(SocialActivity activity) throws Exception {
		MBThread thread = _mbThreadLocalService.getThread(
			activity.getClassPK());

		return _mbMessageLocalService.getMessage(thread.getRootMessageId());
	}

	@Override
	protected String getPath(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		MBThread thread = _mbThreadLocalService.getThread(
			activity.getClassPK());

		return "/message_boards/find_message?messageId=" +
			thread.getRootMessageId();
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

		if (activityType == SocialActivityConstants.TYPE_MOVE_TO_TRASH) {
			if (Validator.isNull(groupName)) {
				return "activity-message-boards-thread-move-to-trash";
			}

			return "activity-message-boards-thread-move-to-trash-in";
		}
		else if (activityType ==
					 SocialActivityConstants.TYPE_RESTORE_FROM_TRASH) {

			if (Validator.isNull(groupName)) {
				return "activity-message-boards-thread-restore-from-trash";
			}

			return "activity-message-boards-thread-restore-from-trash-in";
		}

		return null;
	}

	@Override
	protected boolean hasPermissions(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ServiceContext serviceContext)
		throws Exception {

		MBMessage message = getMessage(activity);

		return _messageModelResourcePermission.contains(
			permissionChecker, message.getMessageId(), actionId);
	}

	private static final String[] _CLASS_NAMES = {MBThread.class.getName()};

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

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