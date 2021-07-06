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

package com.liferay.templates.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public class TemplatesDisplayContext {

	public TemplatesDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			_liferayPortletRequest);
	}

	public List<NavigationItem> getNavigationItems() {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(
					Objects.equals(_getTabs1(), _INFORMATION_TEMPLATES));
				navigationItem.setHref(
					_liferayPortletResponse.createRenderURL(), "tabs1",
					_INFORMATION_TEMPLATES);
				navigationItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest, _INFORMATION_TEMPLATES));
			}
		).add(
			navigationItem -> {
				navigationItem.setActive(
					Objects.equals(_getTabs1(), _WIDGET_TEMPLATES));
				navigationItem.setHref(
					_liferayPortletResponse.createRenderURL(), "tabs1",
					_WIDGET_TEMPLATES);
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, _WIDGET_TEMPLATES));
			}
		).build();
	}

	private String _getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(
			_liferayPortletRequest, "tabs1", _INFORMATION_TEMPLATES);

		return _tabs1;
	}

	private static final String _INFORMATION_TEMPLATES =
		"information-templates";

	private static final String _WIDGET_TEMPLATES = "widget-templates";

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _tabs1;

}