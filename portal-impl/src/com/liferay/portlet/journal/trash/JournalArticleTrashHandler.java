/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.journal.asset.JournalArticleAssetRenderer;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.portlet.trash.DuplicateEntryException;
import com.liferay.portlet.trash.model.TrashEntry;

import javax.portlet.PortletRequest;

/**
 * Implements trash handling for the journal article entity.
 *
 * @author Levente Hudák
 * @author Sergio González
 */
public class JournalArticleTrashHandler extends BaseTrashHandler {

	public static final String CLASS_NAME = JournalArticle.class.getName();

	@Override
	public void checkDuplicateTrashEntry(
			TrashEntry trashEntry, long containerModelId, String newName)
		throws PortalException, SystemException {

		JournalArticle article =
			JournalArticleLocalServiceUtil.getLatestArticle(
				trashEntry.getClassPK());

		String originalTitle = trashEntry.getTypeSettingsProperty("title");

		JournalArticleResource articleResource =
			JournalArticleResourceLocalServiceUtil.fetchArticleResource(
				article.getGroupId(), originalTitle);

		if (articleResource != null) {
			DuplicateEntryException dee = new DuplicateEntryException();

			JournalArticle duplicateArticle =
				JournalArticleLocalServiceUtil.getArticle(
					articleResource.getGroupId(), originalTitle);

			dee.setDuplicateEntryId(duplicateArticle.getResourcePrimKey());
			dee.setOldName(duplicateArticle.getArticleId());
			dee.setTrashEntryId(trashEntry.getEntryId());

			throw dee;
		}
	}

	public void deleteTrashEntries(long[] classPKs, boolean checkPermission)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			JournalArticle article =
				JournalArticleLocalServiceUtil.getLatestArticle(classPK);

			if (checkPermission) {
				JournalArticleServiceUtil.deleteArticle(
					article.getGroupId(), article.getArticleId(), null, null);
			}
			else {
				JournalArticleLocalServiceUtil.deleteArticle(
					article.getGroupId(), article.getArticleId(), null);
			}
		}
	}

	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public String getRestoreLink(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		JournalArticle article =
			JournalArticleLocalServiceUtil.getLatestArticle(classPK);

		return JournalUtil.getJournalControlPanelLink(
			portletRequest, article.getFolderId());
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		JournalArticle article =
			JournalArticleLocalServiceUtil.getLatestArticle(classPK);

		return JournalUtil.getAbsolutePath(
			portletRequest, article.getFolderId());
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK)
		throws PortalException, SystemException {

		JournalArticle article =
			JournalArticleLocalServiceUtil.getLatestArticle(classPK);

		return new JournalArticleAssetRenderer(article);
	}

	public boolean isInTrash(long classPK)
		throws PortalException, SystemException {

		JournalArticle article =
			JournalArticleLocalServiceUtil.getLatestArticle(classPK);

		return article.isInTrash();
	}

	public void restoreTrashEntries(long[] classPKs)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			JournalArticleServiceUtil.restoreArticleFromTrash(classPK);
		}
	}

	@Override
	public void updateTitle(long classPK, String name)
		throws PortalException, SystemException {

		JournalArticle article =
			JournalArticleLocalServiceUtil.getLatestArticle(classPK);

		article.setArticleId(name);

		JournalArticleLocalServiceUtil.updateJournalArticle(article);

		JournalArticleResource articleResource =
			JournalArticleResourceLocalServiceUtil.getArticleResource(
				article.getResourcePrimKey());

		articleResource.setArticleId(name);

		JournalArticleResourceLocalServiceUtil.updateJournalArticleResource(
			articleResource);
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException, SystemException {

		return JournalArticlePermission.contains(
			permissionChecker, classPK, actionId);
	}

}