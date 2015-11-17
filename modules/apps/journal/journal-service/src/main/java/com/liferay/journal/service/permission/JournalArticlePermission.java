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

package com.liferay.journal.service.permission;

import com.liferay.journal.configuration.JournalServiceConfigurationValues;
import com.liferay.journal.exception.NoSuchFolderException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermissionUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.exportimport.staging.permission.StagingPermissionUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
@Component(
	property = {"model.class.name=com.liferay.journal.model.JournalArticle"},
	service = BaseModelPermissionChecker.class
)
public class JournalArticlePermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, JournalArticle article,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, article, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, JournalArticle.class.getName(),
				article.getArticleId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long resourcePrimKey,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, resourcePrimKey, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, JournalArticle.class.getName(),
				resourcePrimKey, actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, String articleId,
			double version, String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, groupId, articleId, version, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, JournalArticle.class.getName(), articleId,
				actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, String articleId,
			int status, String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, groupId, articleId, status, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, JournalArticle.class.getName(), articleId,
				actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, String articleId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, articleId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, JournalArticle.class.getName(), articleId,
				actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, JournalArticle article,
			String actionId)
		throws PortalException {

		String portletId = PortletProviderUtil.getPortletId(
			JournalArticle.class.getName(), PortletProvider.Action.EDIT);

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, article.getGroupId(),
			JournalArticle.class.getName(), article.getResourcePrimKey(),
			portletId, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (article.isDraft()) {
			if (actionId.equals(ActionKeys.VIEW) &&
				!contains(permissionChecker, article, ActionKeys.UPDATE)) {

				return false;
			}
		}
		else if (article.isPending()) {
			hasPermission = WorkflowPermissionUtil.hasPermission(
				permissionChecker, article.getGroupId(),
				JournalArticle.class.getName(), article.getResourcePrimKey(),
				actionId);

			if (hasPermission != null) {
				return hasPermission.booleanValue();
			}
		}

		if (actionId.equals(ActionKeys.VIEW) &&
			!JournalServiceConfigurationValues.
				JOURNAL_ARTICLE_VIEW_PERMISSION_CHECK_ENABLED) {

			return true;
		}

		if (actionId.equals(ActionKeys.VIEW) &&
			PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {

			long folderId = article.getFolderId();

			if (folderId == JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				if (!JournalPermission.contains(
						permissionChecker, article.getGroupId(), actionId)) {

					return false;
				}
			}
			else {
				try {
					JournalFolder folder = _journalFolderLocalService.getFolder(
						folderId);

					if (!JournalFolderPermission.contains(
							permissionChecker, folder, ActionKeys.ACCESS) &&
						!JournalFolderPermission.contains(
							permissionChecker, folder, ActionKeys.VIEW)) {

						return false;
					}
				}
				catch (NoSuchFolderException nsfe) {
					if (!article.isInTrash()) {
						throw nsfe;
					}
				}
			}
		}

		if (permissionChecker.hasOwnerPermission(
				article.getCompanyId(), JournalArticle.class.getName(),
				article.getResourcePrimKey(), article.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			article.getGroupId(), JournalArticle.class.getName(),
			article.getResourcePrimKey(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		JournalArticle article = _journalArticleLocalService.fetchLatestArticle(
			classPK);

		if (article == null) {
			article = _journalArticleLocalService.getArticle(classPK);
		}

		return contains(permissionChecker, article, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, String articleId,
			double version, String actionId)
		throws PortalException {

		JournalArticle article = _journalArticleLocalService.getArticle(
			groupId, articleId, version);

		return contains(permissionChecker, article, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, String articleId,
			int status, String actionId)
		throws PortalException {

		JournalArticle article = _journalArticleLocalService.getLatestArticle(
			groupId, articleId, status);

		return contains(permissionChecker, article, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, String articleId,
			String actionId)
		throws PortalException {

		JournalArticle article = _journalArticleLocalService.getArticle(
			groupId, articleId);

		return contains(permissionChecker, article, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalFolderLocalService(
		JournalFolderLocalService journalFolderLocalService) {

		_journalFolderLocalService = journalFolderLocalService;
	}

	private static JournalArticleLocalService _journalArticleLocalService;
	private static JournalFolderLocalService _journalFolderLocalService;

}