/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.storage;

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
		catch (Exception exception) {
			_log.error("Unable to render field", exception);
		}

		return null;
	}

	@Override
	public String render(Field field, Locale locale, int valueIndex) {
		try {
			return doRender(field, locale, valueIndex);
		}
		catch (Exception exception) {
			_log.error("Unable to render field", exception);
		}

		return null;
	}

	protected abstract String doRender(Field field, Locale locale)
		throws Exception;

	protected abstract String doRender(
			Field field, Locale locale, int valueIndex)
		throws Exception;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseFieldRenderer.class);

}