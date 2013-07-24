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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.permission.BlogsPermission;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.permission.JournalPermission;
import com.liferay.portlet.messageboards.NoSuchDiscussionException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.messageboards.service.permission.MBPermission;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;
import com.liferay.portlet.wiki.service.permission.WikiPagePermission;

/**
 * @author Mate Thurzo
 * @author Raymond Augé
 */
public class SubscriptionPermissionImpl implements SubscriptionPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, String subscriptionClassName,
			long subscriptionClassPK, String inferredClassName,
			long inferredClassPK)
		throws PortalException, SystemException {

		if (!contains(
				permissionChecker, subscriptionClassName, subscriptionClassPK,
				inferredClassName, inferredClassPK)) {

			throw new PrincipalException();
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, String subscriptionClassName,
			long subscriptionClassPK, String inferredClassName,
			long inferredClassPK)
		throws PortalException, SystemException {

		if (subscriptionClassName == null) {
			return false;
		}

		try {
			MBDiscussionLocalServiceUtil.getDiscussion(
				subscriptionClassName, subscriptionClassPK);

			return true;
		}
		catch (NoSuchDiscussionException nsde) {
		}

		if (Validator.isNotNull(inferredClassName)) {
			boolean hasViewPermission = false;

			if (inferredClassName.equals(BlogsEntry.class.getName())) {
				hasViewPermission = BlogsPermission.contains(
					permissionChecker, inferredClassPK, ActionKeys.VIEW);
			}
			else if (inferredClassName.equals(JournalArticle.class.getName())) {
				hasViewPermission = JournalPermission.contains(
					permissionChecker, inferredClassPK, ActionKeys.VIEW);
			}
			else if (inferredClassName.equals(MBCategory.class.getName())) {
				Group group = GroupLocalServiceUtil.fetchGroup(inferredClassPK);

				if (group == null) {
					hasViewPermission = MBCategoryPermission.contains(
						permissionChecker, inferredClassPK, ActionKeys.VIEW);
				}
				else {
					hasViewPermission = MBPermission.contains(
						permissionChecker, inferredClassPK, ActionKeys.VIEW);
				}
			}
			else if (inferredClassName.equals(MBThread.class.getName())) {
				MBThread mbThread = MBThreadLocalServiceUtil.fetchThread(
					inferredClassPK);

				if (mbThread == null) {
					hasViewPermission = false;
				}
				else {
					hasViewPermission = MBMessagePermission.contains(
						permissionChecker, mbThread.getRootMessageId(),
						ActionKeys.VIEW);
				}
			}
			else if (inferredClassName.equals(WikiNode.class.getName())) {
				hasViewPermission = WikiNodePermission.contains(
					permissionChecker, inferredClassPK, ActionKeys.VIEW);
			}
			else if (inferredClassName.equals(WikiPage.class.getName())) {
				hasViewPermission = WikiPagePermission.contains(
					permissionChecker, inferredClassPK, ActionKeys.VIEW);
			}

			if (!hasViewPermission) {
				return false;
			}
		}

		if (subscriptionClassName.equals(BlogsEntry.class.getName())) {
			return BlogsPermission.contains(
				permissionChecker, subscriptionClassPK, ActionKeys.SUBSCRIBE);
		}
		else if (subscriptionClassName.equals(JournalArticle.class.getName())) {
			return JournalPermission.contains(
				permissionChecker, subscriptionClassPK, ActionKeys.SUBSCRIBE);
		}
		else if (subscriptionClassName.equals(MBCategory.class.getName())) {
			Group group = GroupLocalServiceUtil.fetchGroup(subscriptionClassPK);

			if (group == null) {
				return MBCategoryPermission.contains(
					permissionChecker, subscriptionClassPK, ActionKeys.SUBSCRIBE);
			}

			return MBPermission.contains(
				permissionChecker, subscriptionClassPK, ActionKeys.SUBSCRIBE);
		}
		else if (subscriptionClassName.equals(MBThread.class.getName())) {
			MBThread mbThread = MBThreadLocalServiceUtil.fetchThread(subscriptionClassPK);

			if (mbThread == null) {
				return false;
			}

			return MBMessagePermission.contains(
				permissionChecker, mbThread.getRootMessageId(),
				ActionKeys.SUBSCRIBE);
		}
		else if (subscriptionClassName.equals(WikiNode.class.getName())) {
			return WikiNodePermission.contains(
				permissionChecker, subscriptionClassPK, ActionKeys.SUBSCRIBE);
		}
		else if (subscriptionClassName.equals(WikiPage.class.getName())) {
			return WikiPagePermission.contains(
				permissionChecker, subscriptionClassPK, ActionKeys.SUBSCRIBE);
		}

		return true;
	}

}