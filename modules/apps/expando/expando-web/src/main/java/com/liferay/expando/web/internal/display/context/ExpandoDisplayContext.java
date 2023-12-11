/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.web.internal.display.context;

import com.liferay.expando.constants.ExpandoPortletKeys;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.expando.web.internal.search.CustomFieldChecker;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class ExpandoDisplayContext {

	public ExpandoDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteCustomFields");
				dropdownItem.setIcon("trash");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	public Map<String, Object> getAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"deleteExpandosURL",
			() -> PortletURLBuilder.createActionURL(
				PortalUtil.getLiferayPortletResponse(_renderResponse)
			).setActionName(
				"deleteExpandos"
			).buildString()
		).build();
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				String modelResource = ParamUtil.getString(
					_httpServletRequest, "modelResource");

				dropdownItem.setHref(
					_renderResponse.createRenderURL(), "mvcPath",
					"/edit/select_field_type.jsp", "redirect",
					PortalUtil.getCurrentURL(_httpServletRequest),
					"modelResource", modelResource, "backTitle",
					ResourceActionsUtil.getModelResource(
						_httpServletRequest, modelResource));

				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "add-custom-field"));
			}
		).build();
	}

	public List<NavigationItem> getNavigationItems(String label) {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(true);
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, label));
			}
		).build();
	}

	public SearchContainer<String> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		String modelResource = ParamUtil.getString(
			_httpServletRequest, "modelResource");

		String modelResourceName = ResourceActionsUtil.getModelResource(
			_httpServletRequest, modelResource);

		SearchContainer<String> searchContainer = new SearchContainer<>(
			_renderRequest, _renderResponse.createRenderURL(), null,
			LanguageUtil.format(
				_httpServletRequest, "no-custom-fields-are-defined-for-x",
				HtmlUtil.escape(modelResourceName), false));

		searchContainer.setId("customFields");
		searchContainer.setRowChecker(
			new CustomFieldChecker(_renderRequest, _renderResponse));

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			_themeDisplay.getCompanyId(), modelResource);

		searchContainer.setResultsAndTotal(
			Collections.list(expandoBridge.getAttributeNames()));

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public boolean showCreationMenu() throws PortalException {
		return PortletPermissionUtil.contains(
			_themeDisplay.getPermissionChecker(), ExpandoPortletKeys.EXPANDO,
			ActionKeys.ADD_EXPANDO);
	}

	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<String> _searchContainer;
	private final ThemeDisplay _themeDisplay;

}