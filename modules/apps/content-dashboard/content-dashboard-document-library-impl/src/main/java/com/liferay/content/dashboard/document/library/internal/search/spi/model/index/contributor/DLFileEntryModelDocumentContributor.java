/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.document.library.internal.search.spi.model.index.contributor;

import com.liferay.content.dashboard.document.library.internal.constants.ContentDashboardConstants;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.DDMStorageEngineManager;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(
	property = "indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = ModelDocumentContributor.class
)
public class DLFileEntryModelDocumentContributor
	implements ModelDocumentContributor<DLFileEntry> {

	@Override
	public void contribute(Document document, DLFileEntry dlFileEntry) {
		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Indexing document file entry " + dlFileEntry);
			}

			_addFileEntryTypeAttributes(document, dlFileEntry.getFileVersion());

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Document file entry " + dlFileEntry +
						" indexed successfully");
			}
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private void _addFileEntryTypeAttributes(
		Document document, DLFileVersion dlFileVersion) {

		List<DLFileEntryMetadata> dlFileEntryMetadatas =
			_dlFileEntryMetadataLocalService.getFileVersionFileEntryMetadatas(
				dlFileVersion.getFileVersionId());

		for (DLFileEntryMetadata dlFileEntryMetadata : dlFileEntryMetadatas) {
			try {
				DDMFormValues ddmFormValues =
					_ddmStorageEngineManager.getDDMFormValues(
						dlFileEntryMetadata.getDDMStorageId());

				if (ddmFormValues == null) {
					continue;
				}

				Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
					ddmFormValues.getDDMFormFieldValuesMap(false);

				long tiffImageLength = _getDDMFormFieldsValueValue(
					ddmFormFieldValuesMap.get("TIFF_IMAGE_LENGTH"));

				if (tiffImageLength <= 0) {
					continue;
				}

				long tiffImageWidth = _getDDMFormFieldsValueValue(
					ddmFormFieldValuesMap.get("TIFF_IMAGE_WIDTH"));

				if (tiffImageWidth <= 0) {
					continue;
				}

				String aspectRatio =
					ContentDashboardConstants.AspectRatio.SQUARE.toString();

				if (tiffImageLength > tiffImageWidth) {
					aspectRatio =
						ContentDashboardConstants.AspectRatio.TALL.toString();
				}
				else if (tiffImageLength < tiffImageWidth) {
					aspectRatio =
						ContentDashboardConstants.AspectRatio.WIDE.toString();
				}

				String resolution =
					ContentDashboardConstants.Resolution.LARGE.toString();

				if ((tiffImageLength <=
						ContentDashboardConstants.Resolution.SMALL.
							getEndLengthValue()) &&
					(tiffImageWidth <=
						ContentDashboardConstants.Resolution.SMALL.
							getEndWidthValue())) {

					resolution =
						ContentDashboardConstants.Resolution.SMALL.toString();
				}
				else if ((tiffImageLength <=
							ContentDashboardConstants.Resolution.MEDIUM.
								getEndLengthValue()) &&
						 (tiffImageWidth <=
							 ContentDashboardConstants.Resolution.MEDIUM.
								 getEndWidthValue())) {

					resolution =
						ContentDashboardConstants.Resolution.MEDIUM.toString();
				}

				document.addText("aspectRatio", aspectRatio);
				document.addText("resolution", resolution);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to retrieve metadata values", exception);
				}
			}
		}
	}

	private long _getDDMFormFieldsValueValue(
		List<DDMFormFieldValue> ddmFormFieldValues) {

		if (ListUtil.isEmpty(ddmFormFieldValues)) {
			return 0;
		}

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		if (ddmFormFieldValue == null) {
			return 0;
		}

		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			return 0;
		}

		return GetterUtil.getLong(value.getString(LocaleUtil.ROOT));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryModelDocumentContributor.class);

	@Reference
	private DDMStorageEngineManager _ddmStorageEngineManager;

	@Reference
	private DLFileEntryMetadataLocalService _dlFileEntryMetadataLocalService;

}