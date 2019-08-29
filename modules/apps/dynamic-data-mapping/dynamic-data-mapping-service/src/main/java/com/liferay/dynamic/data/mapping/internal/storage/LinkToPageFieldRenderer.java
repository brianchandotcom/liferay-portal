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

package com.liferay.dynamic.data.mapping.internal.storage;

import com.liferay.dynamic.data.mapping.storage.BaseFieldRenderer;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Bruno Basto
 */
public class LinkToPageFieldRenderer extends BaseFieldRenderer {

	protected LinkToPageFieldRenderer(
		JSONFactory jsonFactory, Language language,
		LayoutService layoutService) {

		_jsonFactory = jsonFactory;
		_language = language;
		_layoutService = layoutService;
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

	protected String handleJSON(String value, Locale locale) {
		JSONObject jsonObject = null;

		try {
			jsonObject = _jsonFactory.createJSONObject(value);
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON", jsone);
			}

			return StringPool.BLANK;
		}

		long groupId = jsonObject.getLong("groupId");
		boolean privateLayout = jsonObject.getBoolean("privateLayout");
		long layoutId = jsonObject.getLong("layoutId");

		try {
			return _layoutService.getLayoutName(
				groupId, privateLayout, layoutId,
				_language.getLanguageId(locale));
		}
		catch (Exception e) {
			if (e instanceof NoSuchLayoutException ||
				e instanceof PrincipalException) {

				return _language.format(
					locale, "is-temporarily-unavailable", "content");
			}
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LinkToPageFieldRenderer.class);

	private final JSONFactory _jsonFactory;
	private final Language _language;
	private final LayoutService _layoutService;

}