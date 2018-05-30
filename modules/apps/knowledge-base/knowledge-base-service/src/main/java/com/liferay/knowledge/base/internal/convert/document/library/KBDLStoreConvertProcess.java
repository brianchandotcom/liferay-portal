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

package com.liferay.knowledge.base.internal.convert.document.library;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.portal.convert.documentlibrary.DLStoreConvertProcess;
import com.liferay.portal.convert.documentlibrary.DLStoreConverter;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.util.MaintenanceUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(service = DLStoreConvertProcess.class)
public class KBDLStoreConvertProcess implements DLStoreConvertProcess {

	@Override
	public void migrate(final DLStoreConverter dlStoreConverter)
		throws PortalException {

		int count = _kbArticleLocalService.getKBArticlesCount();

		MaintenanceUtil.appendStatus(
			"Migrating knowledge base attachments in " + count + " articles");

		ActionableDynamicQuery actionableDynamicQuery =
			_kbArticleLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<KBArticle>() {

				@Override
				public void performAction(KBArticle kbArticle)
					throws PortalException {

					for (FileEntry fileEntry :
							kbArticle.getAttachmentsFileEntries()) {

						DLFileEntry dlFileEntry =
							(DLFileEntry)fileEntry.getModel();

						dlStoreConverter.migrateDLFileEntry(
							kbArticle.getCompanyId(),
							DLFolderConstants.getDataRepositoryId(
								dlFileEntry.getRepositoryId(),
								dlFileEntry.getFolderId()),
							new LiferayFileEntry(dlFileEntry));
					}
				}

			});

		actionableDynamicQuery.performActions();
	}

	@Reference(unbind = "-")
	public void setKBArticleLocalService(
		KBArticleLocalService kbArticleLocalService) {

		_kbArticleLocalService = kbArticleLocalService;
	}

	private KBArticleLocalService _kbArticleLocalService;

}