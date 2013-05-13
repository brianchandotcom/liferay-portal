/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.util.Locale;

/**
 * @author Bruno Basto
 * @author Manuel de la Peña
 */
public abstract class BaseFieldRenderer implements FieldRenderer {

	@Override
	public String render(Field field, Locale locale) {
		try {
			return doRender(field, locale);
		}
		catch (Exception e) {
			_log.error("Unable to render field", e);
		}

		return null;
	}

	@Override
	public String render(Field field, Locale locale, int valueIndex) {
		try {
			return doRender(field, locale, valueIndex);
		}
		catch (Exception e) {
			_log.error("Unable to render field", e);
		}

		return null;
	}

	protected abstract String doRender(Field field, Locale locale)
		throws Exception;

	protected abstract String doRender(
			Field field, Locale locale, int valueIndex)
		throws Exception;

	private static Log _log = LogFactoryUtil.getLog(BaseFieldRenderer.class);

}