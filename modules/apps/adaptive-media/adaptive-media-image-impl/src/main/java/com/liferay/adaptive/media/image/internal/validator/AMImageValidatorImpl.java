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

package com.liferay.adaptive.media.image.internal.validator;

import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.adaptive.media.image.size.AMImageSizeProvider;
import com.liferay.adaptive.media.image.validator.AMImageValidator;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.document.library.kernel.util.RawMetadataProcessor;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueValidationException;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManager;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@Component(service = AMImageValidator.class)
public class AMImageValidatorImpl implements AMImageValidator {

	@Override
	public boolean isProcessingSupported(FileVersion fileVersion) {
		if (!isValid(fileVersion) ||
			Objects.equals(
				fileVersion.getMimeType(), ContentTypes.IMAGE_SVG_XML)) {

			return false;
		}

		return true;
	}

	@Override
	public boolean isProcessingSupported(String mimeType) {
		if (StringUtil.equalsIgnoreCase(mimeType, ContentTypes.IMAGE_SVG_XML)) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isValid(FileVersion fileVersion) {
		long imageMaxSize = _amImageSizeProvider.getImageMaxSize();

		if ((imageMaxSize != -1) &&
			((imageMaxSize == 0) || (fileVersion.getSize() == 0) ||
			 (fileVersion.getSize() >= imageMaxSize))) {

			return false;
		}

		if (!_amImageMimeTypeProvider.isMimeTypeSupported(
				fileVersion.getMimeType()) ||
			!_isFileVersionStoredMetadataSupported(fileVersion)) {

			return false;
		}

		return true;
	}

	private boolean _isFileVersionStoredMetadataSupported(
		FileVersion fileVersion) {

		List<DDMStructure> ddmStructures =
			_ddmStructureManager.getClassStructures(
				fileVersion.getCompanyId(),
				_portal.getClassNameId(RawMetadataProcessor.class),
				DDMStructureManager.STRUCTURE_COMPARATOR_STRUCTURE_KEY);

		for (DDMStructure ddmStructure : ddmStructures) {
			DLFileEntryMetadata fileEntryMetadata =
				_dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
					ddmStructure.getStructureId(),
					fileVersion.getFileVersionId());

			if (fileEntryMetadata == null) {
				continue;
			}

			try {
				DDMFormValues ddmFormValues = _storageEngine.getDDMFormValues(
					fileEntryMetadata.getDDMStorageId());

				Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
					ddmFormValues.getDDMFormFieldValuesMap(true);

				for (Map.Entry<String, List<DDMFormFieldValue>> entry :
						ddmFormFieldValuesMap.entrySet()) {

					if (Objects.equals(entry.getKey(), "TIFF_IMAGE_LENGTH") &&
						!_isValidDimension(
							entry.getValue(),
							PropsValues.IMAGE_TOOL_IMAGE_MAX_HEIGHT)) {

						return false;
					}
					else if (Objects.equals(
								entry.getKey(), "TIFF_IMAGE_WIDTH") &&
							 !_isValidDimension(
								 entry.getValue(),
								 PropsValues.IMAGE_TOOL_IMAGE_MAX_WIDTH)) {

						return false;
					}
				}
			}
			catch (DDMFormFieldValueValidationException
						ddmFormFieldValueValidationException) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to validate dynamic data mapping form values " +
							"for file version " +
								fileVersion.getFileVersionId());
				}

				if (_log.isDebugEnabled()) {
					_log.debug(ddmFormFieldValueValidationException);
				}
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Unable to find dynamic data mapping form values ",
							"for ", fileVersion.getFileVersionId(),
							" in structure ", ddmStructure.getStructureKey()));
				}

				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}
			}
		}

		return true;
	}

	private boolean _isValidDimension(
			List<DDMFormFieldValue> ddmFormFieldValues,
			long imageToolImageMaxValue)
		throws DDMFormFieldValueValidationException {

		if (imageToolImageMaxValue <= 0) {
			return true;
		}

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		Value value = ddmFormFieldValue.getValue();

		String valueString = value.getString(value.getDefaultLocale());

		if (Validator.isNull(valueString)) {
			for (Locale availableLocale : value.getAvailableLocales()) {
				valueString = value.getString(availableLocale);

				if (Validator.isNotNull(valueString)) {
					break;
				}
			}
		}

		if (Validator.isNull(valueString)) {
			throw new DDMFormFieldValueValidationException();
		}

		if (Long.valueOf(valueString) >= imageToolImageMaxValue) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AMImageValidatorImpl.class);

	@Reference
	private AMImageMimeTypeProvider _amImageMimeTypeProvider;

	@Reference
	private AMImageSizeProvider _amImageSizeProvider;

	@Reference
	private DDMStructureManager _ddmStructureManager;

	@Reference
	private DLFileEntryMetadataLocalService _dlFileEntryMetadataLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private StorageEngine _storageEngine;

}