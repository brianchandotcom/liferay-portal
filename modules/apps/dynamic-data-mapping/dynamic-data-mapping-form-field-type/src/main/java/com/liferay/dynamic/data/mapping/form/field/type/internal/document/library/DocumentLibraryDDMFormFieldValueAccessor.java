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

package com.liferay.dynamic.data.mapping.form.field.type.internal.document.library;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.trash.TrashHelper;

import java.util.Locale;
import java.util.function.IntFunction;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Queiroz
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.DOCUMENT_LIBRARY,
	service = {
		DDMFormFieldValueAccessor.class,
		DocumentLibraryDDMFormFieldValueAccessor.class
	}
)
public class DocumentLibraryDDMFormFieldValueAccessor
	implements DDMFormFieldValueAccessor<JSONObject> {

	@Override
	public IntFunction<JSONObject[]> getArrayGeneratorIntFunction() {
		return JSONObject[]::new;
	}

	@Override
	public JSONObject getValue(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			return jsonFactory.createJSONObject();
		}

		try {
			JSONObject valueJSONObject = jsonFactory.createJSONObject(
				value.getString(locale));

			FileEntry fileEntry = _getFileEntry(valueJSONObject);

			if ((fileEntry != null) && fileEntry.isInTrash()) {
				valueJSONObject.put(
					"title",
					_trashHelper.getOriginalTitle(fileEntry.getTitle()));
			}

			return valueJSONObject;
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON object", jsonException);
			}

			return jsonFactory.createJSONObject();
		}
	}

	@Override
	public JSONObject getValueForEvaluation(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		return getValue(ddmFormFieldValue, locale);
	}

	@Override
	public boolean isEmpty(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		JSONObject valueJSONObject = getValue(ddmFormFieldValue, locale);

		if (valueJSONObject.length() == 0) {
			return true;
		}

		return false;
	}

	@Reference
	protected JSONFactory jsonFactory;

	private FileEntry _getFileEntry(JSONObject valueJSONObject) {
		if ((valueJSONObject == null) || (valueJSONObject.length() <= 0)) {
			return null;
		}

		try {
			return _dlAppService.getFileEntryByUuidAndGroupId(
				valueJSONObject.getString("uuid"),
				valueJSONObject.getLong("groupId"));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to retrieve file entry ", portalException);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DocumentLibraryDDMFormFieldValueAccessor.class);

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private TrashHelper _trashHelper;

}