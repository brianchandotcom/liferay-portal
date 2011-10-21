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

import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.Serializable;

import java.text.Format;

/**
 * @author Bruno Basto
 */
public class DateFieldRenderer extends BaseFieldRenderer {

	@Override
	protected String doRender(
		ThemeDisplay themeDisplay, Serializable fieldValue) {

		Format format = FastDateFormatFactoryUtil.getDate(
			themeDisplay.getLocale());

		return format.format(fieldValue);
	}

}