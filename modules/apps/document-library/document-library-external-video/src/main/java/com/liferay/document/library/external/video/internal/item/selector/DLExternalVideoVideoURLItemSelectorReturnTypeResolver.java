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

package com.liferay.document.library.external.video.internal.item.selector;

import com.liferay.document.library.external.video.internal.constants.DLExternalVideoConstants;
import com.liferay.document.library.external.video.internal.helper.DLExternalVideoMetadataHelper;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.criteria.VideoURLItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "service.ranking:Integer=100",
	service = ItemSelectorReturnTypeResolver.class
)
public class DLExternalVideoVideoURLItemSelectorReturnTypeResolver
	implements ItemSelectorReturnTypeResolver
		<VideoURLItemSelectorReturnType, FileEntry> {

	@Override
	public Class<VideoURLItemSelectorReturnType>
		getItemSelectorReturnTypeClass() {

		return VideoURLItemSelectorReturnType.class;
	}

	@Override
	public Class<FileEntry> getModelClass() {
		return FileEntry.class;
	}

	@Override
	public String getValue(FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		if (fileEntry.getModel() instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			DLExternalVideoMetadataHelper dlExternalVideoMetadataHelper =
				new DLExternalVideoMetadataHelper(
					_ddmFormValuesToFieldsConverter, _ddmStructureLocalService,
					dlFileEntry, _dlFileEntryMetadataLocalService,
					_fieldsToDDMFormValuesConverter, _storageEngine);

			if (dlExternalVideoMetadataHelper.isExternalVideo() &&
				dlExternalVideoMetadataHelper.containsField(
					DLExternalVideoConstants.DDM_FIELD_NAME_HTML)) {

				return dlExternalVideoMetadataHelper.getFieldValue(
					DLExternalVideoConstants.DDM_FIELD_NAME_URL);
			}
		}

		return _getVideoURL(fileEntry, themeDisplay);
	}

	private String _getVideoURL(FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		if (fileEntry.getGroupId() == fileEntry.getRepositoryId()) {
			return _dlURLHelper.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), themeDisplay,
				StringPool.BLANK, false, true);
		}

		return _portletFileRepository.getPortletFileEntryURL(
			themeDisplay, fileEntry, StringPool.BLANK, false);
	}

	@Reference
	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DLFileEntryMetadataLocalService _dlFileEntryMetadataLocalService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference
	private StorageEngine _storageEngine;

}