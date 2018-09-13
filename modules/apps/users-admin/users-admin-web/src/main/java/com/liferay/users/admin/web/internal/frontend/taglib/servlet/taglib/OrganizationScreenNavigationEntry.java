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

package com.liferay.users.admin.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.users.admin.constants.UserFormConstants;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.BiFunction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Drew Brokke
 */
public class OrganizationScreenNavigationEntry
	implements ScreenNavigationEntry<Organization> {

	public OrganizationScreenNavigationEntry(
		JSPRenderer jspRenderer, Portal portal, String entryKey,
		String categoryKey, String jspPath) {

		this(
			jspRenderer, portal, entryKey, categoryKey, jspPath,
			_defaultIsVisiblePredicate);
	}

	public OrganizationScreenNavigationEntry(
		JSPRenderer jspRenderer, Portal portal, String entryKey,
		String categoryKey, String jspPath,
		BiFunction<User, Organization, Boolean> isVisiblePredicate) {

		_jspRenderer = jspRenderer;
		_portal = portal;
		_entryKey = entryKey;
		_categoryKey = categoryKey;
		_jspPath = jspPath;
		_isVisiblePredicate = isVisiblePredicate;
	}

	@Override
	public String getCategoryKey() {
		return _categoryKey;
	}

	@Override
	public String getEntryKey() {
		return _entryKey;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(getResourceBundle(locale), _entryKey);
	}

	@Override
	public String getScreenNavigationKey() {
		return UserFormConstants.SCREEN_NAVIGATION_KEY_ORGANIZATIONS;
	}

	@Override
	public boolean isVisible(User user, Organization organization) {
		return _isVisiblePredicate.apply(user, organization);
	}

	@Override
	public void render(HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		_jspRenderer.renderJSP(request, response, _jspPath);
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return new AggregateResourceBundle(
			resourceBundle, _portal.getResourceBundle(locale));
	}

	private static final BiFunction<User, Organization, Boolean>
		_defaultIsVisiblePredicate = (user, organization) -> true;

	private final String _categoryKey;
	private final String _entryKey;
	private final BiFunction<User, Organization, Boolean> _isVisiblePredicate;
	private final String _jspPath;
	private final JSPRenderer _jspRenderer;
	private final Portal _portal;

}