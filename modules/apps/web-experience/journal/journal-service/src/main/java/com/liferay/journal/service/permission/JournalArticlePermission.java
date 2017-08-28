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

import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermissionUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Map;
import java.util.Objects;

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
		String actionId) {

		Map<Object, Object> permissionChecksMap =
			permissionChecker.getPermissionChecksMap();

		PermissionCacheKey permissionCacheKey = new PermissionCacheKey(
			article.getGroupId(), article.getArticleId(), actionId);

		Boolean contains = (Boolean)permissionChecksMap.get(permissionCacheKey);

		if (contains == null) {
			contains = _contains(permissionChecker, article, actionId);

			permissionChecksMap.put(permissionCacheKey, contains);
		}

		return contains;
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long classPK, String actionId) {

		JournalArticle article = _journalArticleLocalService.fetchLatestArticle(
			classPK);

		if (article == null) {
			article = _journalArticleLocalService.fetchArticle(classPK);

			if (article == null) {
				_log.error("Unable to find journal article " + classPK);

				return false;
			}
		}

		return contains(permissionChecker, article, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long groupId, String articleId,
		double version, String actionId) {

		JournalArticle article = _journalArticleLocalService.fetchArticle(
			groupId, articleId, version);

		if (article == null) {
			_log.error(
				"Unable to get journal article with group ID " + groupId +
					", article ID " + articleId + ", and version " + version);

			return false;
		}

		return contains(permissionChecker, article, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long groupId, String articleId,
		int status, String actionId) {

		JournalArticle article = _journalArticleLocalService.fetchLatestArticle(
			groupId, articleId, status);

		if (article == null) {
			_log.error(
				"Unable to get journal article with group ID " + groupId +
					", article ID " + articleId + ", and status " + status);

			return false;
		}

		return contains(permissionChecker, article, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long groupId, String articleId,
		String actionId) {

		JournalArticle article = _journalArticleLocalService.fetchArticle(
			groupId, articleId);

		if (article == null) {
			_log.error(
				"Unable to get journal article with group ID " + groupId +
					" and article ID " + articleId);

			return false;
		}

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
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
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

	private static boolean _contains(
		PermissionChecker permissionChecker, JournalArticle article,
		String actionId) {

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

		if (actionId.equals(ActionKeys.VIEW)) {
			JournalServiceConfiguration journalServiceConfiguration = null;

			try {
				journalServiceConfiguration =
					_configurationProvider.getCompanyConfiguration(
						JournalServiceConfiguration.class,
						permissionChecker.getCompanyId());
			}
			catch (ConfigurationException ce) {
				_log.error(
					"Unable to get journal service configuration for company " +
						permissionChecker.getCompanyId(),
					ce);

				return false;
			}

			if (!journalServiceConfiguration.
					articleViewPermissionsCheckEnabled()) {

				return true;
			}

			if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
				long folderId = article.getFolderId();

				if (folderId ==
						JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

					if (!JournalPermission.contains(
							permissionChecker, article.getGroupId(),
							actionId)) {

						return false;
					}
				}
				else {
					JournalFolder folder =
						_journalFolderLocalService.fetchFolder(folderId);

					if (folder != null) {
						if (!JournalFolderPermission.contains(
								permissionChecker, folder, ActionKeys.ACCESS) &&
							!JournalFolderPermission.contains(
								permissionChecker, folder, ActionKeys.VIEW)) {

							return false;
						}
					}
					else {
						if (!article.isInTrash()) {
							_log.error(
								"Unable to get journal folder " + folderId);

							return false;
						}
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

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticlePermission.class);

	private static ConfigurationProvider _configurationProvider;
	private static JournalArticleLocalService _journalArticleLocalService;
	private static JournalFolderLocalService _journalFolderLocalService;

	private static class PermissionCacheKey {

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (!(obj instanceof PermissionCacheKey)) {
				return false;
			}

			PermissionCacheKey permissionCacheKey = (PermissionCacheKey)obj;

			if ((_groupId == permissionCacheKey._groupId) &&
				Objects.equals(_articleId, permissionCacheKey._articleId) &&
				Objects.equals(_actionId, permissionCacheKey._actionId)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _groupId);

			hash = HashUtil.hash(hash, _articleId);

			return HashUtil.hash(hash, _actionId);
		}

		private PermissionCacheKey(
			long groupId, String articleId, String actionId) {

			_groupId = groupId;
			_articleId = articleId;
			_actionId = actionId;
		}

		private final String _actionId;
		private final String _articleId;
		private final long _groupId;

	}

}