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

package com.liferay.portlet.blogs.social;

import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.permission.BlogsEntryPermission;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityConstants;

import java.text.Format;

/**
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 * @author Zsolt Berentey
 */
public class BlogsActivityInterpreter extends BaseSocialActivityInterpreter {

	@Override
	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String getPath(
		SocialActivity activity, ServiceContext serviceContext) {

		return "/blogs/find_entry?entryId=" + activity.getClassPK();
	}

	@Override
	protected Object[] getTitleArguments(
			String groupName, SocialActivity activity, String link,
			String title, ServiceContext serviceContext)
		throws Exception {

		String creatorUserName = getUserName(
			activity.getUserId(), serviceContext);
		String receiverUserName = getUserName(
			activity.getReceiverUserId(), serviceContext);

		BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(
			activity.getClassPK());

		String displayDate = StringPool.BLANK;

		if ((activity.getType() == BlogsActivityKeys.ADD_ENTRY) &&
			(entry.getStatus() == WorkflowConstants.STATUS_SCHEDULED)) {

			link = null;

			Format dateFormatDate =
				FastDateFormatFactoryUtil.getSimpleDateFormat(
					"MMMM d", serviceContext.getLocale(),
					serviceContext.getTimeZone());

			displayDate = dateFormatDate.format(entry.getDisplayDate());
		}

		String displayTitle = wrapLink(link, entry.getTitle());

		return new Object[] {
			groupName, creatorUserName, receiverUserName, displayTitle,
			displayDate
		};
	}

	@Override
	protected String getTitlePattern(String groupName, SocialActivity activity)
		throws Exception {

		int activityType = activity.getType();

		if ((activityType == BlogsActivityKeys.ADD_COMMENT) ||
			(activityType == SocialActivityConstants.TYPE_ADD_COMMENT)) {

			if (Validator.isNull(groupName)) {
				return "activity-blogs-entry-add-comment";
			}
			else {
				return "activity-blogs-entry-add-comment-in";
			}
		}
		else if (activityType == BlogsActivityKeys.ADD_ENTRY) {
			BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(
				activity.getClassPK());

			if (entry.getStatus() == WorkflowConstants.STATUS_SCHEDULED) {
				if (Validator.isNull(groupName)) {
					return "activity-blogs-entry-schedule-entry";
				}
				else {
					return "activity-blogs-entry-schedule-entry-in";
				}
			}
			else {
				if (Validator.isNull(groupName)) {
					return "activity-blogs-entry-add-entry";
				}
				else {
					return "activity-blogs-entry-add-entry-in";
				}
			}
		}
		else if (activityType == SocialActivityConstants.TYPE_MOVE_TO_TRASH) {
			if (Validator.isNull(groupName)) {
				return "activity-blogs-entry-move-to-trash";
			}
			else {
				return "activity-blogs-entry-move-to-trash-in";
			}
		}
		else if (activityType ==
					SocialActivityConstants.TYPE_RESTORE_FROM_TRASH) {

			if (Validator.isNull(groupName)) {
				return "activity-blogs-entry-restore-from-trash";
			}
			else {
				return "activity-blogs-entry-restore-from-trash-in";
			}
		}
		else if (activityType == BlogsActivityKeys.UPDATE_ENTRY) {
			if (Validator.isNull(groupName)) {
				return "activity-blogs-entry-update-entry";
			}
			else {
				return "activity-blogs-entry-update-entry-in";
			}
		}

		return null;
	}

	@Override
	protected boolean hasPermissions(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ServiceContext serviceContext)
		throws Exception {

		return BlogsEntryPermission.contains(
			permissionChecker, activity.getClassPK(), actionId);
	}

	private static final String[] _CLASS_NAMES = {BlogsEntry.class.getName()};

}