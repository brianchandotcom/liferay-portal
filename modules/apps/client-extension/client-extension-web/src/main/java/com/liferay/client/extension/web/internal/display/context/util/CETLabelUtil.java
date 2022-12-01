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

package com.liferay.client.extension.web.internal.display.context.util;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Iván Zaera Avellón
 */
public class CETLabelUtil {

	public static String getAddLabel(Locale locale, String type) {
		return LanguageUtil.format(
			locale, "add-x", _getCETTypeLanguageKey(type));
	}

	public static String getNewLabel(Locale locale, String type) {
		return LanguageUtil.format(
			locale, "new-x", _getCETTypeLanguageKey(type));
	}

	public static String getTypeLabel(Locale locale, String type) {
		return LanguageUtil.get(locale, _getCETTypeLanguageKey(type));
	}

	private static String _getCETTypeLanguageKey(String type) {
		if (Objects.equals(
				type, ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT)) {

			return "custom-element";
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_GLOBAL_CSS)) {

			return "css";
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_GLOBAL_JS)) {

			return "js";
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_IFRAME)) {

			return "iframe";
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_THEME_CSS)) {

			return "theme-css";
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_THEME_FAVICON)) {

			return "theme-favicon";
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_THEME_JS)) {

			return "theme-js";
		}

		return type;
	}

}