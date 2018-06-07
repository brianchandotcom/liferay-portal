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

package com.liferay.html.preview.internal.convert.document.library;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.html.preview.model.HtmlPreviewEntry;
import com.liferay.html.preview.service.HtmlPreviewEntryLocalService;
import com.liferay.portal.convert.documentlibrary.DLStoreConvertProcess;
import com.liferay.portal.convert.documentlibrary.DLStoreConverter;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.util.MaintenanceUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(service = DLStoreConvertProcess.class)
public class HTMLPreviewDLStoreConvertProcess implements DLStoreConvertProcess {

	@Override
	public void migrate(final DLStoreConverter dlStoreConverter)
		throws PortalException {

		int count = _htmlPreviewEntryLocalService.getHtmlPreviewEntriesCount();

		MaintenanceUtil.appendStatus(
			"Migrating entry attachments in " + count + " html previews");

		ActionableDynamicQuery actionableDynamicQuery =
			_htmlPreviewEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<HtmlPreviewEntry>() {

				@Override
				public void performAction(HtmlPreviewEntry entry)
					throws PortalException {

					FileEntry fileEntry =
						PortletFileRepositoryUtil.getPortletFileEntry(
							entry.getFileEntryId());

					dlStoreConverter.migrateDLFileEntry(
						entry.getCompanyId(),
						DLFolderConstants.getDataRepositoryId(
							fileEntry.getRepositoryId(),
							fileEntry.getFolderId()),
						fileEntry);
				}

			});

		actionableDynamicQuery.performActions();
	}

	@Reference
	private HtmlPreviewEntryLocalService _htmlPreviewEntryLocalService;

}