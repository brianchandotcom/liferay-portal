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

package com.liferay.object.internal.field.business.type;

import com.liferay.document.library.kernel.util.DLValidatorUtil;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.dynamic.data.mapping.form.field.type.constants.ObjectDDMFormFieldTypeConstants;
import com.liferay.object.exception.ObjectFieldSettingNameException;
import com.liferay.object.exception.ObjectFieldSettingValueException;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.render.ObjectFieldRenderingContext;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.extension.PropertyDefinition;

import java.math.BigDecimal;

import java.util.Collections;
import java.util.HashSet;
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
 * @author Carolina Barbosa
 */
@Component(
	property = "object.field.business.type.key=" + ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
	service = ObjectFieldBusinessType.class
)
public class AttachmentObjectFieldBusinessType
	implements ObjectFieldBusinessType {

	@Override
	public Set<String> getAllowedObjectFieldSettingsNames() {
		return SetUtil.fromArray(
			"showFilesInDocumentsAndMedia", "storageDLFolderPath");
	}

	@Override
	public String getDBType() {
		return ObjectFieldConstants.DB_TYPE_LONG;
	}

	@Override
	public String getDDMFormFieldTypeName() {
		return ObjectDDMFormFieldTypeConstants.ATTACHMENT;
	}

	@Override
	public String getDescription(Locale locale) {
		return _language.get(
			locale, "upload-files-or-select-from-documents-and-media");
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "attachment");
	}

	@Override
	public String getName() {
		return ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT;
	}

	@Override
	public Map<String, Object> getProperties(
		ObjectField objectField,
		ObjectFieldRenderingContext objectFieldRenderingContext) {

		Map<String, Object> properties = HashMapBuilder.<String, Object>put(
			"objectFieldId", objectField.getObjectFieldId()
		).put(
			"portletId", objectFieldRenderingContext.getPortletId()
		).build();

		ListUtil.isNotEmptyForEach(
			_objectFieldSettingLocalService.getObjectFieldObjectFieldSettings(
				objectField.getObjectFieldId()),
			objectFieldSetting -> properties.put(
				objectFieldSetting.getName(), objectFieldSetting.getValue()));

		properties.remove("showFilesInDocumentsAndMedia");
		properties.remove("storageDLFolderPath");

		return properties;
	}

	@Override
	public PropertyDefinition.PropertyType getPropertyType() {
		return PropertyDefinition.PropertyType.LONG;
	}

	@Override
	public Set<String> getRequiredObjectFieldSettingsNames() {
		return SetUtil.fromArray(
			"acceptedFileExtensions", "fileSource", "maximumFileSize");
	}

	@Override
	public void validateObjectFieldSettings(
			long objectDefinitionId, String objectFieldName,
			List<ObjectFieldSetting> objectFieldSettings)
		throws PortalException {

		ObjectFieldBusinessType.super.validateObjectFieldSettings(
			objectDefinitionId, objectFieldName, objectFieldSettings);

		Stream<ObjectFieldSetting> stream = objectFieldSettings.stream();

		Map<String, String> objectFieldSettingsValuesMap = stream.collect(
			Collectors.toMap(
				ObjectFieldSetting::getName, ObjectFieldSetting::getValue));

		_validateObjectFieldSettingFileSource(
			objectFieldSettingsValuesMap.get("fileSource"), objectFieldName,
			objectFieldSettingsValuesMap.get("showFilesInDocumentsAndMedia"),
			objectFieldSettingsValuesMap.get("storageDLFolderPath"));
		_validateObjectFieldSettingMaximumFileSize(
			objectFieldName,
			objectFieldSettingsValuesMap.get("maximumFileSize"));
	}

	private void _validateObjectFieldSettingFileSource(
			String fileSource, String objectFieldName,
			String showFilesInDocumentsAndMedia, String storageDLFolderPath)
		throws PortalException {

		if (Objects.equals(fileSource, "documentsAndMedia")) {
			_validateObjectFieldSettingFileSourceDocumentsAndMedia(
				objectFieldName, showFilesInDocumentsAndMedia,
				storageDLFolderPath);
		}
		else if (Objects.equals(fileSource, "userComputer")) {
			_validateObjectFieldSettingFileSourceUserComputer(
				objectFieldName, showFilesInDocumentsAndMedia,
				storageDLFolderPath);
		}
		else {
			throw new ObjectFieldSettingValueException.InvalidValue(
				objectFieldName, "fileSource", fileSource);
		}
	}

	private void _validateObjectFieldSettingFileSourceDocumentsAndMedia(
			String objectFieldName, String showFilesInDocumentsAndMedia,
			String storageDLFolderPath)
		throws PortalException {

		Set<String> notAllowedObjectFieldSettingsNames = new HashSet<>();

		if (Validator.isNotNull(showFilesInDocumentsAndMedia)) {
			notAllowedObjectFieldSettingsNames.add(
				"showFilesInDocumentsAndMedia");
		}

		if (Validator.isNotNull(storageDLFolderPath)) {
			notAllowedObjectFieldSettingsNames.add("storageDLFolderPath");
		}

		if (!notAllowedObjectFieldSettingsNames.isEmpty()) {
			throw new ObjectFieldSettingNameException.NotAllowedNames(
				objectFieldName, notAllowedObjectFieldSettingsNames);
		}
	}

	private void _validateObjectFieldSettingFileSourceUserComputer(
			String objectFieldName, String showFilesInDocumentsAndMedia,
			String storageDLFolderPath)
		throws PortalException {

		if (Validator.isNull(showFilesInDocumentsAndMedia) ||
			StringUtil.equalsIgnoreCase(
				showFilesInDocumentsAndMedia, StringPool.FALSE)) {

			if (Validator.isNotNull(storageDLFolderPath)) {
				throw new ObjectFieldSettingNameException.NotAllowedNames(
					objectFieldName,
					Collections.singleton("storageDLFolderPath"));
			}
		}
		else if (StringUtil.equalsIgnoreCase(
					showFilesInDocumentsAndMedia, StringPool.TRUE)) {

			if (Validator.isNull(storageDLFolderPath)) {
				throw new ObjectFieldSettingValueException.
					MissingRequiredValues(
						objectFieldName,
						Collections.singleton("storageDLFolderPath"));
			}
			else if (Validator.isNotNull(storageDLFolderPath) &&
					 (storageDLFolderPath.length() > 255)) {

				throw new ObjectFieldSettingValueException.
					MustBeLessThan256Characters();
			}

			for (String directoryName :
					StringUtil.split(
						storageDLFolderPath, CharPool.FORWARD_SLASH)) {

				DLValidatorUtil.validateDirectoryName(directoryName);
			}
		}
		else {
			throw new ObjectFieldSettingValueException.InvalidValue(
				objectFieldName, "showFilesInDocumentsAndMedia",
				showFilesInDocumentsAndMedia);
		}
	}

	private void _validateObjectFieldSettingMaximumFileSize(
			String objectFieldName, String objectFieldSettingValue)
		throws PortalException {

		try {
			BigDecimal maximumFileSize = new BigDecimal(
				objectFieldSettingValue);

			if (maximumFileSize.signum() == -1) {
				throw new ObjectFieldSettingValueException.InvalidValue(
					objectFieldName, "maximumFileSize",
					objectFieldSettingValue);
			}
		}
		catch (NumberFormatException numberFormatException) {
			throw new ObjectFieldSettingValueException.InvalidValue(
				objectFieldName, "maximumFileSize", objectFieldSettingValue,
				numberFormatException);
		}
	}

	@Reference
	private Language _language;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

}