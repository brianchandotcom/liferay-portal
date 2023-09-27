/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.storage;

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.storage.BaseFieldRenderer;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Bruno Basto
 */
public class DocumentLibraryFieldRenderer extends BaseFieldRenderer {

	protected DocumentLibraryFieldRenderer(
		DLAppService dlAppService, JSONFactory jsonFactory, Language language) {

		_dlAppService = dlAppService;
		_jsonFactory = jsonFactory;
		_language = language;
	}

	@Override
	protected String doRender(Field field, Locale locale) throws Exception {
		List<String> values = new ArrayList<>();

		for (Serializable value : field.getValues(locale)) {
			String valueString = String.valueOf(value);

			if (Validator.isNull(valueString)) {
				continue;
			}

			values.add(handleJSON(valueString, locale));
		}

		return StringUtil.merge(values, StringPool.COMMA_AND_SPACE);
	}

	@Override
	protected String doRender(Field field, Locale locale, int valueIndex) {
		Serializable value = field.getValue(locale, valueIndex);

		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		return handleJSON(String.valueOf(value), locale);
	}

	protected String handleJSON(String json, Locale locale) {
		JSONObject jsonObject = null;

		try {
			jsonObject = _jsonFactory.createJSONObject(json);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON", jsonException);
			}

			return StringPool.BLANK;
		}

		long fileEntryGroupId = jsonObject.getLong("groupId");
		String fileEntryUUID = jsonObject.getString("uuid");

		try {
			FileEntry fileEntry = _dlAppService.getFileEntryByUuidAndGroupId(
				fileEntryUUID, fileEntryGroupId);

			return fileEntry.getTitle();
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchFileEntryException ||
				exception instanceof PrincipalException) {

				return _language.format(
					locale, "is-temporarily-unavailable", "content");
			}
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DocumentLibraryFieldRenderer.class);

	private final DLAppService _dlAppService;
	private final JSONFactory _jsonFactory;
	private final Language _language;

}