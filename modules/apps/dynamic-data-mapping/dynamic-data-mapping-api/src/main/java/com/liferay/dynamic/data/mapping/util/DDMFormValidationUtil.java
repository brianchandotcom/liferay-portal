/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManagerUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.repository.model.FileVersion;

/**
 * @author Vamshi Krishna
 */
public class DDMFormValidationUtil {

	public static boolean isValidDDMFormValues(FileVersion fileVersion) {
		try {
			if (!(fileVersion.getModel() instanceof DLFileVersion)) {
				return true;
			}

			DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();

			if (dlFileVersion.getFileEntryTypeId() ==
					DLFileEntryTypeConstants.
						FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

				return true;
			}

			DLFileEntryType dlFileEntryType =
				DLFileEntryTypeLocalServiceUtil.getFileEntryType(
					dlFileVersion.getFileEntryTypeId());

			for (DDMStructure ddmStructure :
					dlFileEntryType.getDDMStructures()) {

				DLFileEntryMetadata dlFileEntryMetadata =
					DLFileEntryMetadataLocalServiceUtil.fetchFileEntryMetadata(
						ddmStructure.getStructureId(),
						fileVersion.getFileVersionId());

				if (dlFileEntryMetadata != null) {
					DDMFormValues translatedDDMFormValues =
						DDMBeanTranslatorUtil.translate(
							StorageEngineManagerUtil.getDDMFormValues(
								dlFileEntryMetadata.getDDMStorageId()));

					if (translatedDDMFormValues != null) {
						_validate(translatedDDMFormValues);
					}
				}
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return false;
		}

		return true;
	}

	private static void _validate(DDMFormValues ddmFormValues)
		throws DDMFormValuesValidationException {

		DDMFormValuesValidator ddmFormValuesValidator =
			_ddmFormValuesValidatorSnapshot.get();

		if (ddmFormValuesValidator == null) {
			throw new IllegalStateException(
				"DDMFormValuesValidator service is not available");
		}

		ddmFormValuesValidator.validate(ddmFormValues);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormValidationUtil.class);

	private static final Snapshot<DDMFormValuesValidator>
		_ddmFormValuesValidatorSnapshot = new Snapshot<>(
			DDMFormValidationUtil.class, DDMFormValuesValidator.class);

}