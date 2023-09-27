/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.document.library.internal.item.type;

import com.liferay.content.dashboard.document.library.internal.item.provider.FileExtensionGroupsProvider;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtype;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtypeFactory;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.GroupLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = ContentDashboardItemSubtypeFactory.class)
public class DLFileEntryTypeContentDashboardItemSubtypeFactory
	implements ContentDashboardItemSubtypeFactory<DLFileEntryType> {

	@Override
	public ContentDashboardItemSubtype<DLFileEntryType> create(
			long classPK, long entryClassPK)
		throws PortalException {

		DLFileEntryType basicDocumentDLFileEntryType =
			_dlFileEntryTypeLocalService.fetchDLFileEntryType(
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);
		DLFileEntryType dlFileEntryType =
			_dlFileEntryTypeLocalService.getFileEntryType(classPK);

		return new DLFileEntryTypeContentDashboardItemSubtype(
			basicDocumentDLFileEntryType,
			_dlFileEntryLocalService.fetchDLFileEntry(entryClassPK),
			dlFileEntryType, _fileExtensionGroupsProvider,
			_groupLocalService.fetchGroup(dlFileEntryType.getGroupId()),
			_language);
	}

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private FileExtensionGroupsProvider _fileExtensionGroupsProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

}