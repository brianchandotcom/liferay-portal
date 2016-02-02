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

package com.liferay.journal.web.social;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.permission.JournalFolderPermission;
import com.liferay.journal.web.util.JournalResourceBundleLoader;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.social.model.SocialActivityInterpreter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Zsolt Berentey
 */
@Component(
	property = {"javax.portlet.name=" + JournalPortletKeys.JOURNAL},
	service = SocialActivityInterpreter.class
)
public class JournalFolderActivityInterpreter
	extends BaseSocialActivityInterpreter {

	@Override
	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String getLink(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		String className = activity.getClassName();
		long classPK = activity.getClassPK();

		String viewEntryInTrashURL = getViewEntryInTrashURL(
			className, classPK, serviceContext);

		if (viewEntryInTrashURL != null) {
			return viewEntryInTrashURL;
		}

		return getViewEntryURL(className, classPK, serviceContext);
	}

	@Override
	protected ResourceBundleLoader getResourceBundleLoader() {
		return JournalResourceBundleLoader.INSTANCE;
	}

	@Override
	protected String getTitlePattern(
		String groupName, SocialActivity activity) {

		int activityType = activity.getType();

		if (activityType == SocialActivityConstants.TYPE_MOVE_TO_TRASH) {
			if (Validator.isNull(groupName)) {
				return "activity-journal-folder-move-to-trash";
			}
			else {
				return "activity-journal-folder-move-to-trash-in";
			}
		}
		else if (activityType ==
					SocialActivityConstants.TYPE_RESTORE_FROM_TRASH) {

			if (Validator.isNull(groupName)) {
				return "activity-journal-folder-restore-from-trash";
			}
			else {
				return "activity-journal-folder-restore-from-trash-in";
			}
		}

		return null;
	}

	@Override
	protected boolean hasPermissions(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ServiceContext serviceContext)
		throws Exception {

		return JournalFolderPermission.contains(
			permissionChecker, activity.getGroupId(), activity.getClassPK(),
			actionId);
	}

	private static final String[] _CLASS_NAMES =
		{JournalFolder.class.getName()};

}