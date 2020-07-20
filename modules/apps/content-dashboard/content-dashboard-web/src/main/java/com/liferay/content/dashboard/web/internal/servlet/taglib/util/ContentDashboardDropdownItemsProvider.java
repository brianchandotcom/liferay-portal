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

package com.liferay.content.dashboard.web.internal.servlet.taglib.util;

import com.liferay.content.dashboard.web.internal.item.ContentDashboardItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class ContentDashboardDropdownItemsProvider {

	public ContentDashboardDropdownItemsProvider(
		Http http, Language language,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, Portal portal) {

		_http = http;
		_language = language;
		_liferayPortletRequest = liferayPortletRequest;
		_portal = portal;

		_currentURL = String.valueOf(
			PortletURLUtil.getCurrent(
				_liferayPortletRequest, liferayPortletResponse));
	}

	public List<DropdownItem> getDropdownItems(
		ContentDashboardItem contentDashboardItem) {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			_liferayPortletRequest);

		Locale locale = _portal.getLocale(_liferayPortletRequest);

		return DropdownItemList.of(
			() -> {
				if (!contentDashboardItem.isViewURLEnabled(
						httpServletRequest)) {

					return null;
				}

				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.setHref(
					_getURLWithBackURL(
						contentDashboardItem.getViewURL(httpServletRequest)));
				dropdownItem.setLabel(_language.get(locale, "view"));

				return dropdownItem;
			},
			() -> {
				if (!contentDashboardItem.isEditURLEnabled(
						httpServletRequest)) {

					return null;
				}

				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.setHref(
					_getURLWithBackURL(
						contentDashboardItem.getEditURL(httpServletRequest)));
				dropdownItem.setLabel(_language.get(locale, "edit"));

				return dropdownItem;
			},
			() -> {
				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.putData("action", "showInfo");
				dropdownItem.setLabel(_language.get(locale, "info"));

				return dropdownItem;
			},
			() -> {
				if (!contentDashboardItem.isViewURLEnabled(
						httpServletRequest)) {

					return null;
				}

				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.putData("action", "showMetrics");
				dropdownItem.setLabel(_language.get(locale, "metrics"));

				return dropdownItem;
			});
	}

	private String _getURLWithBackURL(String url) {
		String backURL = ParamUtil.getString(_liferayPortletRequest, "backURL");

		if (Validator.isNotNull(backURL)) {
			return _http.setParameter(url, "p_l_back_url", backURL);
		}

		return _http.setParameter(url, "p_l_back_url", _currentURL);
	}

	private final String _currentURL;
	private final Http _http;
	private final Language _language;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final Portal _portal;

}