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

import com.liferay.document.library.kernel.store.DLStore;
import com.liferay.document.library.kernel.store.DLStoreRequest;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FileUtil;

/**
 * @author Peter Shin
 */
public class KBArticleAttachmentsHelper {

	public KBArticleAttachmentsHelper(DLStore dlStore) {
		_dlStore = dlStore;
	}

	public void deleteAttachmentsDirectory(long companyId) {
		try {
			String[] fileNames = _dlStore.getFileNames(
				companyId, CompanyConstants.SYSTEM, "knowledgebase/articles");

			if (fileNames.length > 0) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to delete knowledgebase/articles");
				}

				return;
			}

			_dlStore.deleteDirectory(
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

			String[] fileNames = _dlStore.getFileNames(
				kbArticle.getCompanyId(), CompanyConstants.SYSTEM, oldDirName);

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(kbArticle.getCompanyId());
			serviceContext.setScopeGroupId(kbArticle.getGroupId());

			for (String fileName : fileNames) {
				String shortFileName = FileUtil.getShortFileName(fileName);
				byte[] bytes = _dlStore.getFileAsBytes(
					kbArticle.getCompanyId(), CompanyConstants.SYSTEM,
					fileName);

				_dlStore.addFile(
					DLStoreRequest.builder(
						kbArticle.getCompanyId(), CompanyConstants.SYSTEM,
						newDirName + StringPool.SLASH + shortFileName
					).className(
						KBArticleAttachmentsHelper.class.getName()
					).size(
						bytes.length
					).build(),
					bytes);
			}

			_dlStore.deleteDirectory(
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

	private final DLStore _dlStore;

}