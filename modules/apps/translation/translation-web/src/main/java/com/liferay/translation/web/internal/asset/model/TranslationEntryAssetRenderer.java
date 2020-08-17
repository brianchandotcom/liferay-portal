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

package com.liferay.translation.web.internal.asset.model;

import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.translation.model.TranslationEntry;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class TranslationEntryAssetRenderer
	extends BaseJSPAssetRenderer<TranslationEntry> {

	public TranslationEntryAssetRenderer(
		ServletContext servletContext, TranslationEntry translationEntry) {

		_translationEntry = translationEntry;

		setServletContext(servletContext);
	}

	@Override
	public TranslationEntry getAssetObject() {
		return _translationEntry;
	}

	@Override
	public String getClassName() {
		return TranslationEntry.class.getName();
	}

	@Override
	public long getClassPK() {
		return _translationEntry.getTranslationEntryId();
	}

	@Override
	public long getGroupId() {
		return _translationEntry.getGroupId();
	}

	@Override
	public String getJspPath(
		HttpServletRequest httpServletRequest, String template) {

		return "/asset/full_content.jsp";
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return getTitle(PortalUtil.getLocale(portletRequest));
	}

	@Override
	public String getTitle(Locale locale) {
		return LanguageUtil.get(locale, "translation");
	}

	@Override
	public long getUserId() {
		return _translationEntry.getUserId();
	}

	@Override
	public String getUserName() {
		return _translationEntry.getUserName();
	}

	@Override
	public String getUuid() {
		return _translationEntry.getUuid();
	}

	private final TranslationEntry _translationEntry;

}