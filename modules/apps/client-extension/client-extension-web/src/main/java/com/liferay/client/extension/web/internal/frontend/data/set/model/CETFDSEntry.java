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

package com.liferay.client.extension.web.internal.frontend.data.set.model;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.type.CET;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Bruno Basto
 */
public class CETFDSEntry {

	public CETFDSEntry(CET cet, Locale locale) {
		_cet = cet;
		_locale = locale;
	}

	public String getClientExtensionEntryId() {
		return _cet.getPrimaryKey();
	}

	public String getName() {
		return _cet.getName();
	}

	public StatusInfo getStatus() {
		String label = WorkflowConstants.getStatusLabel(_cet.getStatus());

		return new StatusInfo(label, LanguageUtil.get(_locale, label));
	}

	public String getType() {
		String type = _cet.getType();

		if (Objects.equals(
				type, ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT)) {

			return LanguageUtil.get(_locale, "custom-element");
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_IFRAME)) {

			return LanguageUtil.get(_locale, "iframe");
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_THEME_CSS)) {

			return LanguageUtil.get(_locale, "theme-css");
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_THEME_FAVICON)) {

			return LanguageUtil.get(_locale, "theme-favicon");
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_THEME_JS)) {

			return LanguageUtil.get(_locale, "theme-js");
		}

		return type;
	}

	public boolean isReadOnly() {
		return _cet.isReadOnly();
	}

	private final CET _cet;
	private final Locale _locale;

}