/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.form.navigator.internal.servlet.taglib.ui;

import com.liferay.frontend.taglib.form.navigator.FormNavigatorCategory;

import java.util.Locale;

/**
 * @author Eudaldo Alonso
 */
public class WrapperFormNavigatorCategory implements FormNavigatorCategory {

	public WrapperFormNavigatorCategory(
		com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorCategory
			formNavigatorCategory) {

		_formNavigatorCategory = formNavigatorCategory;
	}

	@Override
	public String getFormNavigatorId() {
		return _formNavigatorCategory.getFormNavigatorId();
	}

	@Override
	public String getKey() {
		return _formNavigatorCategory.getKey();
	}

	@Override
	public String getLabel(Locale locale) {
		return _formNavigatorCategory.getLabel(locale);
	}

	private final
		com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorCategory
			_formNavigatorCategory;

}