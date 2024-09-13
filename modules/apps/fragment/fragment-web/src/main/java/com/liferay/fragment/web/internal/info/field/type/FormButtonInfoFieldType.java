/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.info.field.type;

import com.liferay.info.field.type.InfoFieldType;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

/**
 * @author Víctor Galán
 */
public class FormButtonInfoFieldType implements InfoFieldType {

	public static final FormButtonInfoFieldType INSTANCE =
		new FormButtonInfoFieldType();

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "form-button");
	}

	@Override
	public String getName() {
		return "formButton";
	}

	private FormButtonInfoFieldType() {
	}

}