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

package com.liferay.object.web.internal.util;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.BooleanInfoFieldType;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.FileInfoFieldType;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.SelectInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.localized.bundle.FunctionInfoLocalizedValue;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalServiceUtil;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldValidationConstants;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectFieldSettingLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class ObjectFieldDBTypeUtil {

	public static InfoField<?> addAttributes(
		InfoField.FinalStep finalStep, ObjectField objectField) {

		if (Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

			finalStep.attribute(
				FileInfoFieldType.ALLOWED_FILE_EXTENSIONS,
				_getAcceptedFileExtensions(objectField));

			finalStep.attribute(
				FileInfoFieldType.FILE_SOURCE, _getFileSourceType(objectField));

			finalStep.attribute(
				FileInfoFieldType.MAX_FILE_SIZE,
				_getMaximumFileSize(objectField));
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_DECIMAL)) {

			finalStep.attribute(NumberInfoFieldType.DECIMAL, true);
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_INTEGER)) {

			finalStep.attribute(
				NumberInfoFieldType.MAX_VALUE,
				BigDecimal.valueOf(
					ObjectFieldValidationConstants.
						BUSINESS_TYPE_INTEGER_VALUE_MAX)
			).attribute(
				NumberInfoFieldType.MIN_VALUE,
				BigDecimal.valueOf(
					ObjectFieldValidationConstants.
						BUSINESS_TYPE_INTEGER_VALUE_MIN)
			);
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER)) {

			finalStep.attribute(
				NumberInfoFieldType.MAX_VALUE,
				BigDecimal.valueOf(
					ObjectFieldValidationConstants.BUSINESS_TYPE_LONG_VALUE_MAX)
			).attribute(
				NumberInfoFieldType.MIN_VALUE,
				BigDecimal.valueOf(
					ObjectFieldValidationConstants.BUSINESS_TYPE_LONG_VALUE_MIN)
			);
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

			finalStep.attribute(
				SelectInfoFieldType.OPTIONS, _getOptions(objectField));
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_PRECISION_DECIMAL)) {

			finalStep.attribute(
				NumberInfoFieldType.DECIMAL, true
			).attribute(
				NumberInfoFieldType.DECIMAL_PART_MAX_LENGTH, 16
			).attribute(
				NumberInfoFieldType.MAX_VALUE,
				new BigDecimal(
					ObjectFieldValidationConstants.
						BUSINESS_TYPE_PRECISION_DECIMAL_VALUE_MAX)
			).attribute(
				NumberInfoFieldType.MIN_VALUE,
				new BigDecimal(
					ObjectFieldValidationConstants.
						BUSINESS_TYPE_PRECISION_DECIMAL_VALUE_MIN)
			);
		}

		return finalStep.build();
	}

	public static InfoFieldType getInfoFieldType(ObjectField objectField) {
		if (Validator.isNotNull(objectField.getRelationshipType())) {
			return TextInfoFieldType.INSTANCE;
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN)) {

			return BooleanInfoFieldType.INSTANCE;
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_DECIMAL) ||
				 Objects.equals(
					 objectField.getBusinessType(),
					 ObjectFieldConstants.BUSINESS_TYPE_INTEGER) ||
				 Objects.equals(
					 objectField.getBusinessType(),
					 ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER) ||
				 Objects.equals(
					 objectField.getBusinessType(),
					 ObjectFieldConstants.BUSINESS_TYPE_PRECISION_DECIMAL)) {

			return NumberInfoFieldType.INSTANCE;
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

			return FileInfoFieldType.INSTANCE;
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_DATE)) {

			return DateInfoFieldType.INSTANCE;
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_PICKLIST) ||
				 Objects.equals(
					 objectField.getBusinessType(),
					 ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP)) {

			return SelectInfoFieldType.INSTANCE;
		}

		return TextInfoFieldType.INSTANCE;
	}

	private static String _getAcceptedFileExtensions(ObjectField objectField) {
		ObjectFieldSetting acceptedFileExtensionsObjectFieldSetting =
			ObjectFieldSettingLocalServiceUtil.fetchObjectFieldSetting(
				objectField.getObjectFieldId(), "acceptedFileExtensions");

		if (acceptedFileExtensionsObjectFieldSetting == null) {
			return StringPool.BLANK;
		}

		return acceptedFileExtensionsObjectFieldSetting.getValue();
	}

	private static FileInfoFieldType.FileSourceType _getFileSourceType(
		ObjectField objectField) {

		ObjectFieldSetting objectFieldSetting =
			ObjectFieldSettingLocalServiceUtil.fetchObjectFieldSetting(
				objectField.getObjectFieldId(), "fileSource");

		if (objectFieldSetting == null) {
			return null;
		}

		if (Objects.equals(
				objectFieldSetting.getValue(), "documentsAndMedia")) {

			return FileInfoFieldType.FileSourceType.DOCUMENTS_AND_MEDIA;
		}
		else if (Objects.equals(
					objectFieldSetting.getValue(), "userComputer")) {

			return FileInfoFieldType.FileSourceType.USER_COMPUTER;
		}

		return null;
	}

	private static long _getMaximumFileSize(ObjectField objectField) {
		ObjectFieldSetting objectFieldSetting =
			ObjectFieldSettingLocalServiceUtil.fetchObjectFieldSetting(
				objectField.getObjectFieldId(), "maximumFileSize");

		if (objectFieldSetting == null) {
			return 0;
		}

		return GetterUtil.getLong(objectFieldSetting.getValue());
	}

	private static List<SelectInfoFieldType.Option> _getOptions(
		ObjectField objectField) {

		List<SelectInfoFieldType.Option> options = new ArrayList<>();

		List<ListTypeEntry> listTypeEntries =
			ListTypeEntryLocalServiceUtil.getListTypeEntries(
				objectField.getListTypeDefinitionId());

		for (ListTypeEntry listTypeEntry : listTypeEntries) {
			options.add(
				new SelectInfoFieldType.Option(
					new FunctionInfoLocalizedValue<>(listTypeEntry::getName),
					listTypeEntry.getKey()));
		}

		return options;
	}

}