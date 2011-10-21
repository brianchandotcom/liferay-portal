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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.Serializable;

/**
 * @author Bruno Basto
 */
public abstract class BaseFieldRenderer implements FieldRenderer {

	public String render(ThemeDisplay themeDisplay, Serializable fieldValue) {
		try {
			return doRender(themeDisplay, fieldValue);
		}
		catch (Exception e) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to render field", e);
			}
		}

		return null;
	}

	protected abstract String doRender(
			ThemeDisplay themeDisplay, Serializable fieldValue)
		throws Exception;

	private static Log _log = LogFactoryUtil.getLog(BaseFieldRenderer.class);

}