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

/**
 * @author Bruno Basto
 */
public abstract class BaseFieldRenderer implements FieldRenderer {

	public String render(ThemeDisplay themeDisplay, Field field) {
		try {
			return doRender(themeDisplay, field);
		}
		catch (Exception e) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to render field", e);
			}
		}

		return null;
	}

	protected abstract String doRender(ThemeDisplay themeDisplay, Field field)
		throws Exception;

	private static Log _log = LogFactoryUtil.getLog(BaseFieldRenderer.class);

}