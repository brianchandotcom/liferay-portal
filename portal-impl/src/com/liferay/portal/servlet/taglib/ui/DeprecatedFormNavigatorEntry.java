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

package com.liferay.portal.servlet.taglib.ui;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BaseJSPFormNavigatorEntry;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;

import java.util.Locale;

/**
 * @author Sergio González
 */
@OSGiBeanProperties
public class DeprecatedFormNavigatorEntry
	extends BaseJSPFormNavigatorEntry<Object> {

	public DeprecatedFormNavigatorEntry(
		String key, String label, String categoryKey, String formNavigatorId,
		String jspPath) {

		_key = key;
		_label = label;
		_categoryKey = categoryKey;
		_formNavigatorId = formNavigatorId;
		_jspPath = jspPath;
	}

	@Override
	public String getCategoryKey() {
		return _categoryKey;
	}

	@Override
	public String getFormNavigatorId() {
		return _formNavigatorId;
	}

	@Override
	public String getKey() {
		return _key;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, _label);
	}

	@Override
	protected String getJspPath() {
		return _jspPath;
	}

	private final String _categoryKey;
	private final String _formNavigatorId;
	private final String _jspPath;
	private final String _key;
	private final String _label;

}