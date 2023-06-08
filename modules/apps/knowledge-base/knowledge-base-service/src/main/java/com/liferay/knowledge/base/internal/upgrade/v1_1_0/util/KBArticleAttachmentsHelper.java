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

package com.liferay.knowledge.base.internal.upgrade.v1_1_0.util;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.InputStream;

/**
 * @author Peter Shin
 */
public class KBArticleAttachmentsHelper {

	public KBArticleAttachmentsHelper(Store store) {
		_store = store;
	}

	public void deleteAttachmentsDirectory(long companyId) {
		try {
			String[] fileNames = _store.getFileNames(
				companyId, CompanyConstants.SYSTEM, "knowledgebase/articles");

			if (fileNames.length > 0) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to delete knowledgebase/articles");
				}

				return;
			}

			_store.deleteDirectory(
				companyId, CompanyConstants.SYSTEM, "knowledgebase/articles");
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	public void updateAttachments(KBArticle kbArticle) {
		try {
			long folderId = kbArticle.getClassPK();

			String oldDirName = "knowledgebase/articles/" + folderId;

			String newDirName = "knowledgebase/kbarticles/" + folderId;

			String[] fileNames = _store.getFileNames(
				kbArticle.getCompanyId(), CompanyConstants.SYSTEM, oldDirName);

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(kbArticle.getCompanyId());
			serviceContext.setScopeGroupId(kbArticle.getGroupId());

			for (String fileName : fileNames) {
				String shortFileName = FileUtil.getShortFileName(fileName);

				try (InputStream inputStream = _store.getFileAsStream(
						kbArticle.getCompanyId(), CompanyConstants.SYSTEM,
						fileName, StringPool.BLANK)) {

					_store.addFile(
						kbArticle.getCompanyId(), CompanyConstants.SYSTEM,
						newDirName + StringPool.SLASH + shortFileName,
						Store.VERSION_DEFAULT, inputStream);
				}
			}

			_store.deleteDirectory(
				kbArticle.getCompanyId(), CompanyConstants.SYSTEM, oldDirName);

			if (_log.isInfoEnabled()) {
				_log.info("Added attachments for " + folderId);
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KBArticleAttachmentsHelper.class);

	private final Store _store;

}