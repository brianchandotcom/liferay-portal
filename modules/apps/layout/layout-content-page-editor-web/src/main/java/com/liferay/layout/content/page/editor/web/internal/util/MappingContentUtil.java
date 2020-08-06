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

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

import javax.portlet.ResourceRequest;

/**
 * @author Eudaldo Alonso
 */
public class MappingContentUtil {

	public static JSONArray getMappingFieldsJSONArray(
			String fieldType, String formVariationKey,
			InfoItemServiceTracker infoItemServiceTracker, String itemClassName,
			ResourceRequest resourceRequest)
		throws Exception {

		// LPS-111037

		if (Objects.equals(
				DLFileEntryConstants.getClassName(), itemClassName)) {

			itemClassName = FileEntry.class.getName();
		}

		InfoItemFormProvider<?> infoItemFormProvider =
			infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormProvider.class, itemClassName);

		if (infoItemFormProvider == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get info item form provider for class " +
						itemClassName);
			}

			return JSONFactoryUtil.createJSONArray();
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray defaultFieldSetFieldsJSONArray =
			JSONFactoryUtil.createJSONArray();

		JSONArray fieldSetsJSONArray = JSONUtil.put(
			JSONUtil.put("fields", defaultFieldSetFieldsJSONArray));

		InfoForm infoForm = infoItemFormProvider.getInfoForm(
			formVariationKey, themeDisplay.getScopeGroupId());

		for (InfoFieldSetEntry infoFieldSetEntry :
				infoForm.getInfoFieldSetEntries()) {

			if (infoFieldSetEntry instanceof InfoField) {
				InfoField infoField = (InfoField)infoFieldSetEntry;

				InfoFieldType infoFieldType = infoField.getInfoFieldType();

				if (_isFieldMappable(infoField, fieldType)) {
					defaultFieldSetFieldsJSONArray.put(
						JSONUtil.put(
							"key", infoField.getName()
						).put(
							"label",
							infoField.getLabel(themeDisplay.getLocale())
						).put(
							"type", infoFieldType.getName()
						));
				}
			}
			else if (infoFieldSetEntry instanceof InfoFieldSet) {
				JSONArray fieldSetFieldsJSONArray =
					JSONFactoryUtil.createJSONArray();

				InfoFieldSet infoFieldSet = (InfoFieldSet)infoFieldSetEntry;

				List<InfoField> infoFields = ListUtil.filter(
					infoFieldSet.getAllInfoFields(),
					infoField -> _isFieldMappable(infoField, fieldType));

				for (InfoField infoField : infoFields) {
					fieldSetFieldsJSONArray.put(
						JSONUtil.put(
							"key", infoField.getName()
						).put(
							"label",
							infoField.getLabel(themeDisplay.getLocale())
						).put(
							"type",
							infoField.getInfoFieldType(
							).getName()
						));
				}

				if (fieldSetFieldsJSONArray.length() > 0) {
					fieldSetsJSONArray.put(
						JSONUtil.put(
							"fields", fieldSetFieldsJSONArray
						).put(
							"label",
							infoFieldSet.getLabel(themeDisplay.getLocale())
						));
				}
			}
		}

		return fieldSetsJSONArray;
	}

	private static boolean _isFieldMappable(
		InfoField infoField, String fieldType) {

		boolean imageInfoFieldType =
			infoField.getInfoFieldType() instanceof ImageInfoFieldType;

		if (Objects.equals(fieldType, "background-image") ||
			Objects.equals(fieldType, "image")) {

			return imageInfoFieldType;
		}

		return !imageInfoFieldType;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MappingContentUtil.class);

}