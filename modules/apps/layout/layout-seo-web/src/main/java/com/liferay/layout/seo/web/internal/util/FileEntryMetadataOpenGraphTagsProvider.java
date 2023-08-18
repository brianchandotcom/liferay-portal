/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.web.internal.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.document.library.kernel.util.RawMetadataProcessor;
import com.liferay.dynamic.data.mapping.model.DDMField;
import com.liferay.dynamic.data.mapping.model.DDMFieldAttribute;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.comparator.StructureStructureKeyComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class FileEntryMetadataOpenGraphTagsProvider {

	public FileEntryMetadataOpenGraphTagsProvider(
		DDMFieldLocalService ddmFieldLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService,
		Portal portal) {

		_ddmFieldLocalService = ddmFieldLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_dlFileEntryMetadataLocalService = dlFileEntryMetadataLocalService;
		_portal = portal;
	}

	public Iterable<KeyValuePair> getFileEntryMetadataOpenGraphTagKeyValuePairs(
			FileEntry fileEntry)
		throws PortalException {

		if (!(fileEntry.getModel() instanceof DLFileEntry)) {
			return Collections.emptyList();
		}

		List<KeyValuePair> keyValuePairs = new ArrayList<>();

		FileVersion fileVersion = fileEntry.getFileVersion();

		List<DDMStructure> ddmStructures =
			_ddmStructureLocalService.getClassStructures(
				fileEntry.getCompanyId(),
				_portal.getClassNameId(RawMetadataProcessor.class),
				StructureStructureKeyComparator.INSTANCE_DESCENDING);

		for (DDMStructure ddmStructure : ddmStructures) {
			DLFileEntryMetadata fileEntryMetadata =
				_dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
					ddmStructure.getStructureId(),
					fileVersion.getFileVersionId());

			if (fileEntryMetadata == null) {
				continue;
			}

			String tiffImageLength = _getDDMFormFieldsValueValue(
				fileEntryMetadata.getDDMStorageId(), "TIFF_IMAGE_LENGTH");

			if (tiffImageLength != null) {
				keyValuePairs.add(
					new KeyValuePair("og:image:height", tiffImageLength));
			}

			String tiffImageWidth = _getDDMFormFieldsValueValue(
				fileEntryMetadata.getDDMStorageId(), "TIFF_IMAGE_WIDTH");

			if (tiffImageWidth != null) {
				keyValuePairs.add(
					new KeyValuePair("og:image:width", tiffImageWidth));
			}
		}

		return keyValuePairs;
	}

	private String _getDDMFormFieldsValueValue(
		long ddmStorageId, String fieldName) {

		List<DDMField> ddmFields = _ddmFieldLocalService.getDDMFields(
			ddmStorageId, fieldName);

		if (ListUtil.isEmpty(ddmFields)) {
			return null;
		}

		DDMField ddmField = ddmFields.get(0);

		DDMFieldAttribute ddmFieldAttribute =
			_ddmFieldLocalService.fetchDDMFieldAttribute(
				ddmField.getFieldId(), StringPool.BLANK, StringPool.BLANK);

		if (ddmFieldAttribute == null) {
			return null;
		}

		return ddmFieldAttribute.getAttributeValue();
	}

	private final DDMFieldLocalService _ddmFieldLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DLFileEntryMetadataLocalService
		_dlFileEntryMetadataLocalService;
	private final Portal _portal;

}