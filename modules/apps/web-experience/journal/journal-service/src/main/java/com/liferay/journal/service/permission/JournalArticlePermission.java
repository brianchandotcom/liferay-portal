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

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @deprecated As of 4.0.0, with no direct replacement
 */
@Component(
	property = {"model.class.name=com.liferay.journal.model.JournalArticle"},
	service = BaseModelPermissionChecker.class
)
@Deprecated
public class JournalArticlePermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, JournalArticle article,
			String actionId)
		throws PortalException {

		_journalArticleModelResourcePermission.check(
			permissionChecker, article, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long resourcePrimKey,
			String actionId)
		throws PortalException {

		_journalArticleModelResourcePermission.check(
			permissionChecker, resourcePrimKey, actionId);
	}

	/**
	 * @deprecated As of 4.0.0, with no direct replacement
	 */
	@Deprecated
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

	/**
	 * @deprecated As of 4.0.0, with no direct replacement
	 */
	@Deprecated
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

	/**
	 * @deprecated As of 4.0.0, with no direct replacement
	 */
	@Deprecated
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

		return _journalArticleModelResourcePermission.contains(
			permissionChecker, article, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		return _journalArticleModelResourcePermission.contains(
			permissionChecker, classPK, actionId);
	}

	/**
	 * @deprecated As of 4.0.0, with no direct replacement
	 */
	@Deprecated
	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, String articleId,
			double version, String actionId)
		throws PortalException {

		JournalArticle article = _journalArticleLocalService.getArticle(
			groupId, articleId, version);

		return _journalArticleModelResourcePermission.contains(
			permissionChecker, article, actionId);
	}

	/**
	 * @deprecated As of 4.0.0, with no direct replacement
	 */
	@Deprecated
	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, String articleId,
			int status, String actionId)
		throws PortalException {

		JournalArticle article = _journalArticleLocalService.getLatestArticle(
			groupId, articleId, status);

		return _journalArticleModelResourcePermission.contains(
			permissionChecker, article, actionId);
	}

	/**
	 * @deprecated As of 4.0.0, with no direct replacement
	 */
	@Deprecated
	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, String articleId,
			String actionId)
		throws PortalException {

		JournalArticle article = _journalArticleLocalService.getArticle(
			groupId, articleId);

		return _journalArticleModelResourcePermission.contains(
			permissionChecker, article, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		_journalArticleModelResourcePermission.check(
			permissionChecker, primaryKey, actionId);
	}

	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	protected void setJournalFolderLocalService(
		JournalFolderLocalService journalFolderLocalService) {
	}

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalArticle)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<JournalArticle> modelResourcePermission) {

		_journalArticleModelResourcePermission = modelResourcePermission;
	}

	private static JournalArticleLocalService _journalArticleLocalService;
	private static ModelResourcePermission<JournalArticle>
		_journalArticleModelResourcePermission;

}