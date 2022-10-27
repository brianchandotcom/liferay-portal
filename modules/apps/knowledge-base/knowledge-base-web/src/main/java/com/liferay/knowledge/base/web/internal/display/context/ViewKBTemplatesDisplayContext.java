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

package com.liferay.knowledge.base.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.ManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.model.KBTemplateSearchDisplay;
import com.liferay.knowledge.base.service.KBTemplateServiceUtil;
import com.liferay.knowledge.base.web.internal.search.KBTemplateSearch;
import com.liferay.knowledge.base.web.internal.security.permission.resource.AdminPermission;
import com.liferay.knowledge.base.web.internal.security.permission.resource.KBTemplatePermission;
import com.liferay.knowledge.base.web.internal.util.KBDropdownItemsProvider;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class ViewKBTemplatesDisplayContext {

	public ViewKBTemplatesDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_currentURL = PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);
		_kbDropdownItemsProvider = new KBDropdownItemsProvider(
			liferayPortletRequest, liferayPortletResponse);
	}

	public List<String> getAvailableActions(KBTemplate kbTemplate)
		throws PortalException {

		if (KBTemplatePermission.contains(
				_themeDisplay.getPermissionChecker(), kbTemplate,
				ActionKeys.DELETE)) {

			return Collections.singletonList("deleteKBTemplates");
		}

		return Collections.emptyList();
	}

	public String getEditKBTemplateURL(KBTemplate kbTemplate) {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCPath(
			"/admin/common/edit_kb_template.jsp"
		).setRedirect(
			_currentURL
		).setParameter(
			"kbTemplateId", kbTemplate.getKbTemplateId()
		).buildString();
	}

	public List<DropdownItem> getEmptyStateActionDropdownItems() {
		return DropdownItemListBuilder.add(
			() ->
				Validator.isNull(
					ParamUtil.getString(_httpServletRequest, "keywords")) &&
				AdminPermission.contains(
					_themeDisplay.getPermissionChecker(),
					_themeDisplay.getScopeGroupId(),
					KBActionKeys.ADD_KB_TEMPLATE),
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						_liferayPortletResponse
					).setMVCPath(
						"/admin/common/edit_kb_template.jsp"
					).setRedirect(
						PortalUtil.getCurrentURL(_httpServletRequest)
					).buildPortletURL());
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "new"));
			}
		).build();
	}

	public List<DropdownItem> getKBTemplateDropdownItems(
		KBTemplate kbTemplate) {

		return _kbDropdownItemsProvider.getKBTemplateDropdownItems(kbTemplate);
	}

	public String getKBTemplateModifiedDateDescription(KBTemplate kbTemplate) {
		Date modifiedDate = kbTemplate.getModifiedDate();

		return LanguageUtil.getTimeDescription(
			_httpServletRequest,
			System.currentTimeMillis() - modifiedDate.getTime(), true);
	}

	public ManagementToolbarDisplayContext getManagementToolbarDisplayContext()
		throws PortalException {

		return new KBTemplatesManagementToolbarDisplayContext(
			_getDeleteKBTemplatesURL(), _httpServletRequest,
			_liferayPortletRequest, _liferayPortletResponse,
			getSearchContainer());
	}

	public SearchContainer<KBTemplate> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<KBTemplate> searchContainer = new KBTemplateSearch(
			_liferayPortletRequest,
			PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).setMVCRenderCommandName(
				"/knowledge_base/view_kb_templates"
			).buildPortletURL());

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		if (Validator.isNull(keywords)) {
			searchContainer.setResultsAndTotal(
				() -> KBTemplateServiceUtil.getGroupKBTemplates(
					_themeDisplay.getScopeGroupId(), searchContainer.getStart(),
					searchContainer.getEnd(),
					searchContainer.getOrderByComparator()),
				KBTemplateServiceUtil.getGroupKBTemplatesCount(
					_themeDisplay.getScopeGroupId()));
		}
		else {
			KBTemplateSearchDisplay kbTemplateSearchDisplay =
				KBTemplateServiceUtil.getKBTemplateSearchDisplay(
					_themeDisplay.getScopeGroupId(), keywords, keywords, null,
					null, false, new int[0], searchContainer.getCur(),
					searchContainer.getDelta(),
					searchContainer.getOrderByComparator());

			searchContainer.setResultsAndTotal(
				kbTemplateSearchDisplay::getResults,
				kbTemplateSearchDisplay.getTotal());
		}

		searchContainer.setRowChecker(_getRowChecker());

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public String getSearchURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/knowledge_base/view_kb_templates"
		).buildString();
	}

	public boolean hasKBTemplates() throws PortalException {
		SearchContainer<KBTemplate> searchContainer = getSearchContainer();

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-156421")) ||
			searchContainer.hasResults() || searchContainer.isSearch()) {

			return true;
		}

		return false;
	}

	private String _getDeleteKBTemplatesURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/knowledge_base/delete_kb_templates"
		).setRedirect(
			_currentURL
		).buildString();
	}

	private RowChecker _getRowChecker() {
		if (AdminPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(),
				KBActionKeys.DELETE_KB_TEMPLATES)) {

			return new RowChecker(_liferayPortletResponse);
		}

		return null;
	}

	private final PortletURL _currentURL;
	private final HttpServletRequest _httpServletRequest;
	private final KBDropdownItemsProvider _kbDropdownItemsProvider;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private SearchContainer<KBTemplate> _searchContainer;
	private final ThemeDisplay _themeDisplay;

}