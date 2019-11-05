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

package com.liferay.layout.seo.web.internal.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.document.library.kernel.util.RawMetadataProcessor;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.comparator.StructureStructureKeyComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Adolfo Pérez
 */
public class FileEntryMetadataOpenGraphTagsProvider {

	public FileEntryMetadataOpenGraphTagsProvider(
		DDMStructureLocalService ddmStructureLocalService,
		DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService,
		Portal portal, StorageEngine storageEngine) {

		_ddmStructureLocalService = ddmStructureLocalService;
		_dlFileEntryMetadataLocalService = dlFileEntryMetadataLocalService;
		_portal = portal;
		_storageEngine = storageEngine;
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

			DDMFormValues ddmFormValues = _storageEngine.getDDMFormValues(
				fileEntryMetadata.getDDMStorageId());

			if (ddmFormValues == null) {
				continue;
			}

			Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
				ddmFormValues.getDDMFormFieldValuesMap();

			Optional<String> tiffImageLengthOptional =
				_getDDMFormFieldsValueValue(
					ddmFormFieldValuesMap.get("TIFF_IMAGE_LENGTH"));

			tiffImageLengthOptional.ifPresent(
				tiffImageLength -> keyValuePairs.add(
					new KeyValuePair("og:image:height", tiffImageLength)));

			Optional<String> tiffImageWidthOptional =
				_getDDMFormFieldsValueValue(
					ddmFormFieldValuesMap.get("TIFF_IMAGE_WIDTH"));

			tiffImageWidthOptional.ifPresent(
				tiffImageWidth -> keyValuePairs.add(
					new KeyValuePair("og:image:width", tiffImageWidth)));
		}

		return keyValuePairs;
	}

	private Optional<String> _getDDMFormFieldsValueValue(
		List<DDMFormFieldValue> ddmFormFieldValues) {

		if (ListUtil.isEmpty(ddmFormFieldValues)) {
			return Optional.empty();
		}

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		Value value = ddmFormFieldValue.getValue();

		return Optional.of(value.getString(value.getDefaultLocale()));
	}

	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DLFileEntryMetadataLocalService
		_dlFileEntryMetadataLocalService;
	private final Portal _portal;
	private final StorageEngine _storageEngine;

}