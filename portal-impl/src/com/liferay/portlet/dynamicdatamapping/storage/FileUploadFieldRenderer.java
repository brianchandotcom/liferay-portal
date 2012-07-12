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

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Locale;

/**
 * @author Bruno Basto
 */
public class FileUploadFieldRenderer extends BaseFieldRenderer {

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

		return fieldValueJSONObject.getString("name");
	}

	private static Log _log = LogFactoryUtil.getLog(
		FileUploadFieldRenderer.class);

}