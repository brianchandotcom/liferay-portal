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

package com.liferay.template.web.internal.display.context;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.templates.web.internal.util.TemplateActionDropdownItemsProvider;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public class TemplateDisplayContext {

	public TemplateDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			_liferayPortletRequest);
	}

	public List<DropdownItem> getDDMTemplateActionDropdownItems(
			DDMTemplate ddmTemplate)
		throws Exception {

		TemplateActionDropdownItemsProvider ddmTemplateActionDropdownItems =
			new TemplateActionDropdownItemsProvider(
				ddmTemplate, _httpServletRequest, _liferayPortletResponse);

		return ddmTemplateActionDropdownItems.getActionDropdownItems();
	}

	public List<NavigationItem> getNavigationItems() {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(
					Objects.equals(_getTabs1(), "information-templates"));
				navigationItem.setHref(
					_liferayPortletResponse.createRenderURL(), "tabs1",
					"information-templates");
				navigationItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest, "information-templates"));
			}
		).add(
			navigationItem -> {
				navigationItem.setActive(
					Objects.equals(_getTabs1(), "widget-templates"));
				navigationItem.setHref(
					_liferayPortletResponse.createRenderURL(), "tabs1",
					"widget-templates");
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "widget-templates"));
			}
		).build();
	}

	public SearchContainer<DDMTemplate> getTemplateSearchContainer() {
		if (_ddmTemplateSearchContainer != null) {
			return _ddmTemplateSearchContainer;
		}

		SearchContainer<DDMTemplate> ddmTemplateSearchContainer =
			new SearchContainer<>(
				_liferayPortletRequest, _getPortletURL(), null,
				"there-are-no-templates");

		ddmTemplateSearchContainer.setResults(Collections.emptyList());
		ddmTemplateSearchContainer.setTotal(0);

		_ddmTemplateSearchContainer = ddmTemplateSearchContainer;

		return _ddmTemplateSearchContainer;
	}

	private PortletURL _getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setTabs1(
			_getTabs1()
		).build();
	}

	private String _getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(
			_liferayPortletRequest, "tabs1", "information-templates");

		return _tabs1;
	}

	private SearchContainer<DDMTemplate> _ddmTemplateSearchContainer;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _tabs1;

}