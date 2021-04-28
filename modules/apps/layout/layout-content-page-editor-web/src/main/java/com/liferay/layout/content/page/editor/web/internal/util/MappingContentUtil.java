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

import java.util.Locale;
import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class MappingContentUtil {

	public static JSONArray getMappingFieldsJSONArray(
			String formVariationKey, long groupId,
			InfoItemServiceTracker infoItemServiceTracker, String itemClassName,
			Locale locale)
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

		JSONArray defaultFieldSetFieldsJSONArray =
			JSONFactoryUtil.createJSONArray();

		JSONArray fieldSetsJSONArray = JSONUtil.put(
			JSONUtil.put("fields", defaultFieldSetFieldsJSONArray));

		InfoForm infoForm = infoItemFormProvider.getInfoForm(
			formVariationKey, groupId);

		for (InfoFieldSetEntry infoFieldSetEntry :
				infoForm.getInfoFieldSetEntries()) {

			if (infoFieldSetEntry instanceof InfoField) {
				InfoField infoField = (InfoField)infoFieldSetEntry;

				InfoFieldType infoFieldType = infoField.getInfoFieldType();

				defaultFieldSetFieldsJSONArray.put(
					JSONUtil.put(
						"key", infoField.getName()
					).put(
						"label", infoField.getLabel(locale)
					).put(
						"type", infoFieldType.getName()
					));
			}
			else if (infoFieldSetEntry instanceof InfoFieldSet) {
				JSONArray fieldSetFieldsJSONArray =
					JSONFactoryUtil.createJSONArray();

				InfoFieldSet infoFieldSet = (InfoFieldSet)infoFieldSetEntry;

				for (InfoField infoField : infoFieldSet.getAllInfoFields()) {
					InfoFieldType infoFieldType = infoField.getInfoFieldType();

					fieldSetFieldsJSONArray.put(
						JSONUtil.put(
							"key", infoField.getName()
						).put(
							"label", infoField.getLabel(locale)
						).put(
							"type", infoFieldType.getName()
						));
				}

				if (fieldSetFieldsJSONArray.length() > 0) {
					fieldSetsJSONArray.put(
						JSONUtil.put(
							"fields", fieldSetFieldsJSONArray
						).put(
							"label", infoFieldSet.getLabel(locale)
						));
				}
			}
		}

		return fieldSetsJSONArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MappingContentUtil.class);

}