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

package com.liferay.asset.display.template.web.internal.display.context;

import com.liferay.asset.display.template.constants.AssetDisplayTemplateActionKeys;
import com.liferay.asset.display.template.constants.AssetDisplayTemplatePortletKeys;
import com.liferay.asset.display.template.model.AssetDisplayTemplate;
import com.liferay.asset.display.template.service.AssetDisplayTemplateLocalServiceUtil;
import com.liferay.asset.display.template.util.comparator.AssetDisplayTemplateClassNameIdComparator;
import com.liferay.asset.display.template.util.comparator.AssetDisplayTemplateCreateDateComparator;
import com.liferay.asset.display.template.web.internal.security.permission.resource.AssetDisplayPermission;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.util.DDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMDisplayRegistry;
import com.liferay.dynamic.data.mapping.util.DDMTemplateHelper;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class AssetDisplayTemplateDisplayContext {

	public AssetDisplayTemplateDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest request, DDMDisplayRegistry ddmDisplayRegistry,
		DDMTemplateHelper ddmTemplateHelper) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_request = request;

		_ddmDisplayRegistry = ddmDisplayRegistry;
		_ddmTemplateHelper = ddmTemplateHelper;
	}

	public AssetDisplayTemplate getAssetDisplayTemplate()
		throws PortalException {

		if (_assetDisplayTemplate == null) {
			_assetDisplayTemplate =
				AssetDisplayTemplateLocalServiceUtil.fetchAssetDisplayTemplate(
					getAssetDisplayTemplateId());
		}

		return _assetDisplayTemplate;
	}

	public long getAssetDisplayTemplateId() {
		if (_assetDisplayTemplateId == null) {
			_assetDisplayTemplateId = ParamUtil.getLong(
				_request, "assetDisplayTemplateId");
		}

		return _assetDisplayTemplateId;
	}

	public String getAutocompleteJSON(
			HttpServletRequest request, String language)
		throws Exception {

		return _ddmTemplateHelper.getAutocompleteJSON(request, language);
	}

	public Set<Long> getAvailableClassNameIds() {
		if (_availableClassNameIdsSet != null) {
			return _availableClassNameIdsSet;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] availableClassNameIds =
			AssetRendererFactoryRegistryUtil.getClassNameIds(
				themeDisplay.getCompanyId(), true);

		_availableClassNameIdsSet = SetUtil.fromArray(availableClassNameIds);

		return _availableClassNameIdsSet;
	}

	public DDMDisplay getDDMDisplay() {
		return _ddmDisplayRegistry.getDDMDisplay(
			AssetDisplayTemplatePortletKeys.ASSET_DISPLAY_TEMPLATE);
	}

	public DDMTemplate getDDMTemplate() throws Exception {
		if (_ddmTemplate != null) {
			return _ddmTemplate;
		}

		AssetDisplayTemplate assetDisplayTemplate = getAssetDisplayTemplate();

		if (assetDisplayTemplate != null) {
			_ddmTemplate = DDMTemplateLocalServiceUtil.getDDMTemplate(
				assetDisplayTemplate.getDDMTemplateId());
		}

		return _ddmTemplate;
	}

	public long getDefaultClassNameId() {
		return PortalUtil.getClassNameId(AssetDisplayTemplate.class);
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_request);

		_displayStyle = portalPreferences.getValue(
			AssetDisplayTemplatePortletKeys.ASSET_DISPLAY_TEMPLATE,
			"display-style", "list");

		return _displayStyle;
	}

	public String getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_request, "keywords", null);

		return _keywords;
	}

	public List<NavigationItem> getNavigationItems() {
		List<NavigationItem> navigationItems = new ArrayList<>();

		NavigationItem entriesNavigationItem = new NavigationItem();

		entriesNavigationItem.setActive(true);

		PortletURL mainURL = _renderResponse.createRenderURL();

		entriesNavigationItem.setHref(mainURL.toString());

		entriesNavigationItem.setLabel(
			LanguageUtil.get(_request, "asset-display-templates"));

		navigationItems.add(entriesNavigationItem);

		return navigationItems;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_request, "orderByCol", "create-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType", "asc");

		return _orderByType;
	}

	public SearchContainer getSearchContainer() throws PortalException {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContainer searchContainer = new SearchContainer(
			_renderRequest, _renderResponse.createRenderURL(), null,
			"there-are-no-asset-display-templates");

		if (isSearch()) {
			searchContainer.setSearch(true);
		}

		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));
		searchContainer.setOrderByCol(getOrderByCol());

		OrderByComparator<AssetDisplayTemplate> orderByComparator =
			_getOrderByComparator(getOrderByCol(), getOrderByType());

		searchContainer.setOrderByComparator(orderByComparator);

		searchContainer.setOrderByType(getOrderByType());

		int assetDisplayTemplatesCount = 0;
		List<AssetDisplayTemplate> assetDisplayTemplates = null;

		if (isSearch()) {
			assetDisplayTemplatesCount =
				AssetDisplayTemplateLocalServiceUtil.
					getAssetDisplayTemplatesCount(
						themeDisplay.getScopeGroupId(), getKeywords());

			assetDisplayTemplates =
				AssetDisplayTemplateLocalServiceUtil.getAssetDisplayTemplates(
					themeDisplay.getScopeGroupId(), getKeywords(),
					searchContainer.getStart(), searchContainer.getEnd(),
					orderByComparator);
		}
		else {
			assetDisplayTemplatesCount =
				AssetDisplayTemplateLocalServiceUtil.
					getAssetDisplayTemplatesCount(
						themeDisplay.getScopeGroupId());

			assetDisplayTemplates =
				AssetDisplayTemplateLocalServiceUtil.getAssetDisplayTemplates(
					themeDisplay.getScopeGroupId(), searchContainer.getStart(),
					searchContainer.getEnd(), orderByComparator);
		}

		searchContainer.setTotal(assetDisplayTemplatesCount);
		searchContainer.setResults(assetDisplayTemplates);

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public boolean isAutocompleteEnabled(String language) {
		return _ddmTemplateHelper.isAutocompleteEnabled(language);
	}

	public boolean isDisabledManagementBar() throws PortalException {
		SearchContainer searchContainer = getSearchContainer();

		if (searchContainer.getTotal() <= 0) {
			return true;
		}

		return false;
	}

	public boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	public boolean isShowAddButton() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (AssetDisplayPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getSiteGroupId(),
				AssetDisplayTemplateActionKeys.ADD_ASSET_DISPLAY_TEMPLATE)) {

			return true;
		}

		return false;
	}

	private OrderByComparator<AssetDisplayTemplate> _getOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<AssetDisplayTemplate> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new AssetDisplayTemplateCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("asset-type")) {
			orderByComparator = new AssetDisplayTemplateClassNameIdComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	private AssetDisplayTemplate _assetDisplayTemplate;
	private Long _assetDisplayTemplateId;
	private Set<Long> _availableClassNameIdsSet;
	private final DDMDisplayRegistry _ddmDisplayRegistry;
	private DDMTemplate _ddmTemplate;
	private final DDMTemplateHelper _ddmTemplateHelper;
	private String _displayStyle;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private SearchContainer _searchContainer;

}