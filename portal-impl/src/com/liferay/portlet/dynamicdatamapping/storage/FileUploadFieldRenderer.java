/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.Serializable;

/**
 * @author Bruno Basto
 */
public class FileUploadFieldRenderer extends BaseFieldRenderer {

	@Override
	protected String doRender(
			ThemeDisplay themeDisplay, Serializable fieldValue)
		throws PortalException {

		if (Validator.isNull(fieldValue) ||
			fieldValue.equals(JSONFactoryUtil.getNullJSON())) {

			return StringPool.BLANK;
		}

		JSONObject fieldValueJSON = null;

		try {
			fieldValueJSON = JSONFactoryUtil.createJSONObject(
				String.valueOf(fieldValue));
		}
		catch (JSONException e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON", e);
			}

			return StringPool.BLANK;
		}

		return fieldValueJSON.getString("name");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileUploadFieldRenderer.class);

}