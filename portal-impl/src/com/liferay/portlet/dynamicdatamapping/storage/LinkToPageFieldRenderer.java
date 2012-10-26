/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutServiceUtil;

import java.io.Serializable;

import java.util.Locale;

/**
 * @author Bruno Basto
 */
public class LinkToPageFieldRenderer extends BaseFieldRenderer {

	@Override
	protected String doRender(Field field, Locale locale) {
		Serializable fieldValue = field.getValue();

		if (Validator.isNull(fieldValue) ||
			fieldValue.equals(JSONFactoryUtil.getNullJSON())) {

			return StringPool.BLANK;
		}

		JSONObject fieldValueJSONObject = null;

		try {
			fieldValueJSONObject = JSONFactoryUtil.createJSONObject(
				String.valueOf(fieldValue));
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON", jsone);
			}

			return StringPool.BLANK;
		}

		long groupId = fieldValueJSONObject.getLong("groupId");
		boolean privateLayout = fieldValueJSONObject.getBoolean(
			"privateLayout");
		long layoutId = fieldValueJSONObject.getLong("layoutId");

		try {
			return LayoutServiceUtil.getLayoutName(
				groupId, privateLayout, layoutId,
				LanguageUtil.getLanguageId(locale));
		}
		catch (Exception e) {
			if (e instanceof NoSuchLayoutException ||
				e instanceof PrincipalException) {

				return LanguageUtil.format(
					locale, "is-temporarily-unavailable", "content");
			}
		}

		return StringPool.BLANK;
	}

	private static Log _log = LogFactoryUtil.getLog(
		LinkToPageFieldRenderer.class);

}