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

package com.liferay.blogs.web.social;

import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.service.permission.BlogsEntryPermission;
import com.liferay.blogs.web.constants.BlogsPortletKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ClassResourceBundleLoader;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.blogs.social.BlogsActivityKeys;
import com.liferay.social.kernel.model.BaseSocialActivityInterpreter;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.model.SocialActivityConstants;
import com.liferay.social.kernel.model.SocialActivityInterpreter;

import java.text.Format;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 * @author Zsolt Berentey
 */
@Component(
	property = {"javax.portlet.name=" + BlogsPortletKeys.BLOGS},
	service = SocialActivityInterpreter.class
)
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
	protected ResourceBundleLoader getResourceBundleLoader() {
		return _resourceBundleLoader;
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

		BlogsEntry entry = _blogsEntryLocalService.getEntry(
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

		return new Object[] {
			groupName, creatorUserName, receiverUserName, wrapLink(link, title),
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
			BlogsEntry entry = _blogsEntryLocalService.getEntry(
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

	@Reference(unbind = "-")
	protected void setBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
	}

	private static final String[] _CLASS_NAMES = {BlogsEntry.class.getName()};

	private BlogsEntryLocalService _blogsEntryLocalService;
	private final ResourceBundleLoader _resourceBundleLoader =
		new ClassResourceBundleLoader(
			"content.Language", BlogsActivityInterpreter.class);

}