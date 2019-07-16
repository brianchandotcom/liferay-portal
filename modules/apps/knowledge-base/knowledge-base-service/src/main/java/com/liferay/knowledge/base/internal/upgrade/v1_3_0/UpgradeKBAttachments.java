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

package com.liferay.knowledge.base.internal.upgrade.v1_3_0;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Sergio González
 */
public class UpgradeKBAttachments extends UpgradeProcess {

	public UpgradeKBAttachments(Store store) {
		_store = store;
	}

	protected void deleteEmptyDirectories() throws Exception {
		for (long companyId : PortalUtil.getCompanyIds()) {
			_store.deleteDirectory(
				companyId, CompanyConstants.SYSTEM, "knowledgebase/kbarticles");
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateAttachments();

		deleteEmptyDirectories();
	}

	protected String[] getAttachments(long companyId, long resourcePrimKey)
		throws Exception {

		String dirName = "knowledgebase/kbarticles/" + resourcePrimKey;

		return _store.getFileNames(companyId, CompanyConstants.SYSTEM, dirName);
	}

	/**
	 * @see KBArticleAttachmentsUtil#getFolderId(long, long, long)
	 */
	protected long getFolderId(long groupId, long userId, long resourcePrimKey)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			groupId, _PORTLET_ID, serviceContext);

		Folder folder = PortletFileRepositoryUtil.addPortletFolder(
			userId, repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			String.valueOf(resourcePrimKey), serviceContext);

		return folder.getFolderId();
	}

	protected void updateAttachments() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select kbArticleId, resourcePrimKey, groupId, companyId, " +
					"userId, status from KBArticle");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");

				long classPK = rs.getLong("resourcePrimKey");

				int status = rs.getInt("status");

				if (status != WorkflowConstants.STATUS_APPROVED) {
					classPK = rs.getLong("kbArticleId");
				}

				long userId = rs.getLong("userId");

				updateAttachments(companyId, groupId, classPK, userId);
			}
		}
	}

	protected void updateAttachments(
			long companyId, long groupId, long resourcePrimKey, long userId)
		throws Exception {

		for (String attachment : getAttachments(companyId, resourcePrimKey)) {
			try {
				if (!_store.hasFile(
						companyId, CompanyConstants.SYSTEM, attachment)) {

					continue;
				}

				long folderId = getFolderId(groupId, userId, resourcePrimKey);

				byte[] bytes = _store.getFileAsBytes(
					companyId, CompanyConstants.SYSTEM, attachment);

				String title = FileUtil.getShortFileName(attachment);

				String mimeType = MimeTypesUtil.getExtensionContentType(
					FileUtil.getExtension(title));

				PortletFileRepositoryUtil.addPortletFileEntry(
					groupId, userId, _KB_ARTICLE_CLASS_NAME, resourcePrimKey,
					_PORTLET_ID, folderId, bytes, title, mimeType, false);

				_store.deleteFile(
					companyId, CompanyConstants.SYSTEM, attachment);
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to upgrade attachment " + attachment, pe);
				}
			}
		}
	}

	private static final String _KB_ARTICLE_CLASS_NAME =
		"com.liferay.knowledgebase.model.KBArticle";

	private static final String _PORTLET_ID = "3_WAR_knowledgebaseportlet";

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeKBAttachments.class);

	private final Store _store;

}