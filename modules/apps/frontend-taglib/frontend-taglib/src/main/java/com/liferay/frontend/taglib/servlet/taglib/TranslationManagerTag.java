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

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Carlos Lancha
 * @author Diego Nascimento
 */
public class TranslationManagerTag extends IncludeTag {

	public Locale[] getAvailableLocales() {
		return _availableLocales;
	}

	public String getComponentId() {
		return _componentId;
	}

	public String getCssClass() {
		return _cssClass;
	}

	public String getDefaultLanguageId() {
		return _defaultLanguageId;
	}

	public String getEditingLanguageId() {
		return _editingLanguageId;
	}

	public String getId() {
		return _id;
	}

	public boolean isChangeableDefaultLanguage() {
		return _changeableDefaultLanguage;
	}

	public boolean isInitialize() {
		return _initialize;
	}

	public boolean isReadOnly() {
		return _readOnly;
	}

	public void setAvailableLocales(Locale[] availableLocales) {
		_availableLocales = availableLocales;
	}

	public void setChangeableDefaultLanguage(
		boolean changeableDefaultLanguage) {

		_changeableDefaultLanguage = changeableDefaultLanguage;
	}

	public void setComponentId(String componentId) {
		_componentId = componentId;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDefaultLanguageId(String defaultLanguageId) {
		_defaultLanguageId = defaultLanguageId;
	}

	public void setEditingLanguageId(String editingLanguageId) {
		_editingLanguageId = editingLanguageId;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setInitialize(boolean initialize) {
		_initialize = initialize;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setReadOnly(boolean readOnly) {
		_readOnly = readOnly;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_availableLocales = null;
		_changeableDefaultLanguage = false;
		_componentId = null;
		_cssClass = null;
		_defaultLanguageId = null;
		_editingLanguageId = null;
		_id = null;
		_initialize = false;
		_readOnly = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		JSONArray availableLocalesJSONArray = JSONFactoryUtil.createJSONArray();
		JSONArray localesJSONArray = JSONFactoryUtil.createJSONArray();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Set<Locale> locales = LanguageUtil.getAvailableLocales(
			themeDisplay.getSiteGroupId());

		for (Locale locale : locales) {
			String languageId = LocaleUtil.toLanguageId(locale);

			String w3cLanguageId = LocaleUtil.toW3cLanguageId(locale);

			JSONObject localeJSONObject = JSONUtil.put(
				"code", w3cLanguageId
			).put(
				"icon", StringUtil.toLowerCase(w3cLanguageId)
			).put(
				"id", languageId
			).put(
				"label", locale.getDisplayName(themeDisplay.getLocale())
			);

			if (ArrayUtil.contains(_availableLocales, locale)) {
				availableLocalesJSONArray.put(localeJSONObject);
			}

			localesJSONArray.put(localeJSONObject);
		}

		Map<String, Object> data = HashMapBuilder.<String, Object>put(
			"availableLocales", availableLocalesJSONArray
		).put(
			"changeableDefaultLanguage", _changeableDefaultLanguage
		).put(
			"cssClass", _cssClass
		).put(
			"defaultLanguageId", _defaultLanguageId
		).put(
			"editingLanguageId", _editingLanguageId
		).put(
			"id", _id
		).put(
			"initialize", _initialize
		).put(
			"locales", localesJSONArray
		).put(
			"readOnly", _readOnly
		).build();

		httpServletRequest.setAttribute(
			"liferay-frontend:translation-manager:data", data);
	}

	private static final String _PAGE = "/translation_manager/page.jsp";

	private Locale[] _availableLocales;
	private boolean _changeableDefaultLanguage;
	private String _componentId;
	private String _cssClass;
	private String _defaultLanguageId;
	private String _editingLanguageId;
	private String _id;
	private boolean _initialize;
	private boolean _readOnly;

}