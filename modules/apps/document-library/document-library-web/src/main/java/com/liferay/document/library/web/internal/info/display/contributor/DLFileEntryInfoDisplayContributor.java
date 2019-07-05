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

package com.liferay.document.library.web.internal.info.display.contributor;

import com.liferay.asset.info.display.field.AssetEntryInfoDisplayFieldProvider;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.display.field.ClassTypesInfoDisplayFieldProvider;
import com.liferay.info.display.field.ExpandoInfoDisplayFieldProvider;
import com.liferay.info.display.field.InfoDisplayFieldProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.documentlibrary.asset.model.DLFileEntryClassTypeReader;

import java.text.DateFormat;
import java.text.NumberFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = InfoDisplayContributor.class)
public class DLFileEntryInfoDisplayContributor
	implements InfoDisplayContributor<FileEntry> {

	@Override
	public String getClassName() {
		return DLFileEntryConstants.getClassName();
	}

	@Override
	public List<ClassType> getClassTypes(long groupId, Locale locale)
		throws PortalException {

		return _classTypesInfoDisplayFieldProvider.getClassTypes(
			groupId, new DLFileEntryClassTypeReader(), locale);
	}

	@Override
	public Set<InfoDisplayField> getInfoDisplayFields(
			long classTypeId, Locale locale)
		throws PortalException {

		Set<InfoDisplayField> infoDisplayFields =
			_infoDisplayFieldProvider.getContributorInfoDisplayFields(
				locale, AssetEntry.class.getName(), getClassName());

		DLFileEntryClassTypeReader dlFileEntryClassTypeReader =
			new DLFileEntryClassTypeReader();

		ClassType classType = dlFileEntryClassTypeReader.getClassType(
			classTypeId, locale);

		if (classType != null) {
			infoDisplayFields.addAll(
				_classTypesInfoDisplayFieldProvider.
					getClassTypeInfoDisplayFields(classType, locale));
		}

		infoDisplayFields.addAll(
			_expandoInfoDisplayFieldProvider.
				getContributorExpandoInfoDisplayFields(getClassName(), locale));

		return infoDisplayFields;
	}

	@Override
	public Map<String, Object> getInfoDisplayFieldsValues(
			FileEntry fileEntry, Locale locale)
		throws PortalException {

		Map<String, Object> infoDisplayFieldValues = new HashMap<>();

		infoDisplayFieldValues.putAll(
			_assetEntryInfoDisplayFieldProvider.
				getAssetEntryInfoDisplayFieldsValues(
					getClassName(), fileEntry.getFileEntryId(), locale));
		infoDisplayFieldValues.putAll(
			_infoDisplayFieldProvider.getContributorInfoDisplayFieldsValues(
				getClassName(), fileEntry, locale));
		infoDisplayFieldValues.putAll(
			_expandoInfoDisplayFieldProvider.
				getContributorExpandoInfoDisplayFieldsValues(
					getClassName(), fileEntry, locale));

		if (fileEntry.getModel() instanceof DLFileEntry) {
			_addClassTypeDDMFormFieldValues(
				fileEntry, locale, infoDisplayFieldValues);
		}

		return infoDisplayFieldValues;
	}

	@Override
	public InfoDisplayObjectProvider getInfoDisplayObjectProvider(long classPK)
		throws PortalException {

		LocalRepository localRepository =
			_repositoryProvider.fetchFileEntryLocalRepository(classPK);

		if (localRepository == null) {
			return null;
		}

		FileEntry fileEntry = localRepository.getFileEntry(classPK);

		return new DLFileEntryInfoDisplayObjectProvider(fileEntry);
	}

	@Override
	public InfoDisplayObjectProvider getInfoDisplayObjectProvider(
			long groupId, String urlTitle)
		throws PortalException {

		return getInfoDisplayObjectProvider(Long.valueOf(urlTitle));
	}

	@Override
	public String getInfoURLSeparator() {
		return "/d/";
	}

	private void _addClassTypeDDMFormFieldValues(
			FileEntry fileEntry, Locale locale,
			Map<String, Object> infoDisplayFieldValues)
		throws PortalException {

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		DLFileEntryType dlFileEntryType = dlFileEntry.getDLFileEntryType();

		for (DDMStructure ddmStructure : dlFileEntryType.getDDMStructures()) {
			FileVersion fileVersion = fileEntry.getFileVersion();

			DLFileEntryMetadata fileEntryMetadata =
				_dlFileEntryMetadataLocalService.getFileEntryMetadata(
					ddmStructure.getStructureId(),
					fileVersion.getFileVersionId());

			DDMFormValues ddmFormValues = _storageEngine.getDDMFormValues(
				fileEntryMetadata.getDDMStorageId());

			Map<String, List<DDMFormFieldValue>> ddmFormFieldsValuesMap =
				ddmFormValues.getDDMFormFieldValuesMap();

			for (Map.Entry<String, List<DDMFormFieldValue>> entry :
					ddmFormFieldsValuesMap.entrySet()) {

				_addDDMFormFieldValues(
					fileEntry, entry.getKey(), entry.getValue(),
					infoDisplayFieldValues, locale);
			}
		}
	}

	private void _addDDMFormFieldValues(
			FileEntry fileEntry, String key,
			List<DDMFormFieldValue> ddmFormFieldValues,
			Map<String, Object> classTypeValues, Locale locale)
		throws PortalException {

		Object fieldValue = null;

		if (ddmFormFieldValues.size() == 1) {
			DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

			_addNestedFields(
				fileEntry, ddmFormFieldValue, classTypeValues, locale);

			fieldValue = _sanitizeFieldValue(
				fileEntry, ddmFormFieldValue, locale);
		}
		else {
			Stream<DDMFormFieldValue> stream = ddmFormFieldValues.stream();

			fieldValue = stream.map(
				ddmFormFieldValue -> {
					try {
						_addNestedFields(
							fileEntry, ddmFormFieldValue, classTypeValues,
							locale);

						return _sanitizeFieldValue(
							fileEntry, ddmFormFieldValue, locale);
					}
					catch (PortalException pe) {
						_log.error(
							"Unable to sanitize field " +
								ddmFormFieldValue.getName(),
							pe);

						return null;
					}
				}
			).filter(
				value -> value != null
			).collect(
				Collectors.toList()
			);
		}

		classTypeValues.put(key, fieldValue);
	}

	private void _addNestedFields(
			FileEntry fileEntry, DDMFormFieldValue ddmFormFieldValue,
			Map<String, Object> classTypeValues, Locale locale)
		throws PortalException {

		Map<String, List<DDMFormFieldValue>> nestedDDMFormFieldsValuesMap =
			ddmFormFieldValue.getNestedDDMFormFieldValuesMap();

		for (Map.Entry<String, List<DDMFormFieldValue>> entry :
				nestedDDMFormFieldsValuesMap.entrySet()) {

			List<DDMFormFieldValue> ddmFormFieldValues = entry.getValue();

			_addDDMFormFieldValues(
				fileEntry, entry.getKey(), ddmFormFieldValues, classTypeValues,
				locale);
		}
	}

	private Object _sanitizeFieldValue(
			FileEntry fileEntry, DDMFormFieldValue ddmFormFieldValue,
			Locale locale)
		throws PortalException {

		Value value = ddmFormFieldValue.getValue();

		String valueString = value.getString(locale);

		if (Objects.equals(ddmFormFieldValue.getType(), "ddm-date")) {
			try {
				DateFormat dateFormat = DateFormat.getDateInstance(
					DateFormat.SHORT, locale);

				Date date = DateUtil.parseDate(
					"yyyy-MM-dd", valueString, locale);

				return dateFormat.format(date);
			}
			catch (Exception e) {
				return valueString;
			}
		}
		else if (Objects.equals(ddmFormFieldValue.getType(), "ddm-decimal")) {
			NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);

			return numberFormat.format(GetterUtil.getDouble(valueString));
		}
		else if (Objects.equals(ddmFormFieldValue.getType(), "ddm-image")) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				valueString);

			jsonObject.put("url", _transformFileEntryURL(valueString));

			return jsonObject;
		}

		return SanitizerUtil.sanitize(
			fileEntry.getCompanyId(), fileEntry.getGroupId(),
			fileEntry.getUserId(), DLFileEntryConstants.getClassName(),
			fileEntry.getFileEntryId(), ContentTypes.TEXT_HTML,
			Sanitizer.MODE_ALL, valueString, null);
	}

	private String _transformFileEntryURL(String data) {
		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(data);

			String uuid = jsonObject.getString("uuid");
			long groupId = jsonObject.getLong("groupId");

			if (Validator.isNull(uuid) && (groupId == 0)) {
				return StringPool.BLANK;
			}

			FileEntry fileEntry = _dlAppService.getFileEntryByUuidAndGroupId(
				uuid, groupId);

			return _dlURLHelper.getDownloadURL(
				fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryInfoDisplayContributor.class);

	@Reference
	private AssetEntryInfoDisplayFieldProvider
		_assetEntryInfoDisplayFieldProvider;

	@Reference
	private ClassTypesInfoDisplayFieldProvider
		_classTypesInfoDisplayFieldProvider;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLFileEntryMetadataLocalService _dlFileEntryMetadataLocalService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private ExpandoInfoDisplayFieldProvider _expandoInfoDisplayFieldProvider;

	@Reference
	private InfoDisplayFieldProvider _infoDisplayFieldProvider;

	@Reference
	private RepositoryProvider _repositoryProvider;

	@Reference
	private StorageEngine _storageEngine;

}