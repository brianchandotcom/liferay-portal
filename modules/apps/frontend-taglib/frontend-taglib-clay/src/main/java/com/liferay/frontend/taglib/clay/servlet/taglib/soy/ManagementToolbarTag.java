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

package com.liferay.frontend.taglib.clay.servlet.taglib.soy;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.clay.internal.js.loader.modules.extender.npm.NPMResolverProvider;
import com.liferay.frontend.taglib.clay.internal.servlet.taglib.display.context.ManagementToolbarDefaults;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.ManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.base.BaseClayTag;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Carlos Lancha
 */
public class ManagementToolbarTag extends BaseClayTag {

	@Override
	public int doStartTag() {
		setComponentBaseName(
			"com.liferay.frontend.taglib.clay.ManagementToolbar");
		setHydrate(true);
		setModuleBaseName("management-toolbar");

		if (_managementToolbarDisplayContext != null) {
			populateContext(_managementToolbarDisplayContext);
		}

		Map<String, Object> context = getContext();

		String searchContainerId = (String)context.get("searchContainerId");

		if (Validator.isNotNull(searchContainerId)) {
			String componentId = getComponentId();

			putValue("cacheState", _CACHE_STATE);

			if (Validator.isNull(componentId)) {
				setComponentId(searchContainerId + "ManagementToolbar");
			}
		}

		String searchInputName = (String)context.get("searchInputName");

		if (Validator.isNull(searchInputName)) {
			searchInputName = ManagementToolbarDefaults.getSearchInputName();

			setSearchInputName(searchInputName);
		}

		String searchFormMethod = GetterUtil.getString(
			context.get("searchFormMethod"),
			ManagementToolbarDefaults.getSearchFormMethod());

		setSearchFormMethod(searchFormMethod);

		String searchActionURL = (String)context.get("searchActionURL");

		if (searchFormMethod.equals("GET") &&
			Validator.isNotNull(searchActionURL)) {

			Map<String, Object> searchData = _getSearchData(searchActionURL);

			putValue("searchData", searchData);

			String contentRenderer = GetterUtil.getString(
				context.get("contentRenderer"),
				ManagementToolbarDefaults.getContentRenderer());

			setContentRenderer(contentRenderer);
		}

		String searchValue = (String)context.get("searchValue");

		if (Validator.isNull(searchValue) &&
			Validator.isNotNull(searchInputName)) {

			setSearchValue(ParamUtil.getString(request, searchInputName));
		}

		boolean selectable = GetterUtil.getBoolean(
			context.get("selectable"), true);

		setSelectable(selectable);

		CreationMenu creationMenu = (CreationMenu)context.get("creationMenu");

		boolean showCreationMenu = GetterUtil.getBoolean(
			context.get("showCreationMenu"),
			ManagementToolbarDefaults.isShowCreationMenu(creationMenu));

		setShowCreationMenu(showCreationMenu);

		boolean showFiltersDoneButton = GetterUtil.getBoolean(
			context.get("showFiltersDoneButton"));

		setShowFiltersDoneButton(showFiltersDoneButton);

		String infoPanelId = (String)context.get("infoPanelId");

		boolean showInfoButton = GetterUtil.getBoolean(
			context.get("showInfoButton"),
			ManagementToolbarDefaults.isShowInfoButton(infoPanelId));

		setShowInfoButton(showInfoButton);

		if (Validator.isNotNull(context.get("searchValue"))) {
			setShowResultsBar(true);
		}
		else {
			List filterLabels = (List)context.get("filterLabels");

			if ((filterLabels != null) && !filterLabels.isEmpty()) {
				setShowResultsBar(true);
			}
		}

		return super.doStartTag();
	}

	public ManagementToolbarDisplayContext getDisplayContext() {
		return _managementToolbarDisplayContext;
	}

	@Override
	public String getModule() {
		NPMResolver npmResolver = NPMResolverProvider.getNPMResolver();

		if (npmResolver == null) {
			return StringPool.BLANK;
		}

		return npmResolver.resolveModuleName(
			"frontend-taglib-clay/management_toolbar/ManagementToolbar.es");
	}

	public void setActionDropdownItems(List<DropdownItem> actionDropdownItems) {
		putValue("actionItems", actionDropdownItems);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #setDefaultEventHandler(String)}
	 */
	@Deprecated
	public void setActionHandler(String actionHandler) {
		putValue("actionHandler", actionHandler);
	}

	public void setCheckboxStatus(String checkboxStatus) {
		putValue("checkboxStatus", checkboxStatus);
	}

	public void setClearResultsURL(String clearResultsURL) {
		putValue("clearResultsURL", clearResultsURL);
	}

	public void setClearSelectionURL(String clearSelectionURL) {
		putValue("clearSelectionURL", clearSelectionURL);
	}

	public void setContentRenderer(String contentRenderer) {
		putValue("contentRenderer", contentRenderer);
	}

	public void setCreationMenu(CreationMenu creationMenu) {
		putValue("creationMenu", creationMenu);
	}

	public void setDisabled(Boolean disabled) {
		putValue("disabled", disabled);
	}

	public void setDisplayContext(
		ManagementToolbarDisplayContext managementToolbarDisplayContext) {

		_managementToolbarDisplayContext = managementToolbarDisplayContext;
	}

	public void setFilterDropdownItems(List<DropdownItem> filterDropdownItems) {
		putValue("filterItems", filterDropdownItems);
	}

	public void setFilterLabelItems(List<LabelItem> filterLabelItems) {
		putValue("filterLabels", filterLabelItems);
	}

	public void setInfoPanelId(String infoPanelId) {
		putValue("infoPanelId", infoPanelId);
	}

	public void setItemsTotal(int itemsTotal) {
		putValue("totalItems", itemsTotal);
	}

	public void setSearchActionURL(String searchActionURL) {
		putValue("searchActionURL", searchActionURL);
	}

	public void setSearchContainerId(String searchContainerId) {
		putValue("searchContainerId", searchContainerId);
	}

	public void setSearchFormMethod(String searchFormMethod) {
		putValue("searchFormMethod", searchFormMethod);
	}

	public void setSearchFormName(String searchFormName) {
		putValue("searchFormName", searchFormName);
	}

	public void setSearchInputName(String searchInputName) {
		putValue("searchInputName", searchInputName);
	}

	public void setSearchValue(String searchValue) {
		putValue("searchValue", searchValue);
	}

	public void setSelectable(Boolean selectable) {
		putValue("selectable", selectable);
	}

	public void setSelectAllURL(String selectAllURL) {
		putValue("selectAllURL", selectAllURL);
	}

	public void setSelectedItems(int selectedItems) {
		putValue("selectedItems", selectedItems);
	}

	public void setShowAdvancedSearch(Boolean showAdvancedSearch) {
		putValue("showAdvancedSearch", showAdvancedSearch);
	}

	public void setShowCreationMenu(Boolean showCreationMenu) {
		putValue("showCreationMenu", showCreationMenu);
	}

	public void setShowFiltersDoneButton(Boolean showFiltersDoneButton) {
		putValue("showFiltersDoneButton", showFiltersDoneButton);
	}

	public void setShowInfoButton(Boolean showInfoButton) {
		putValue("showInfoButton", showInfoButton);
	}

	public void setShowResultsBar(Boolean showResultsBar) {
		putValue("showResultsBar", showResultsBar);
	}

	public void setShowSearch(Boolean showSearch) {
		putValue("showSearch", showSearch);
	}

	public void setShowSelectAllButton(Boolean showSelectAllButton) {
		putValue("showSelectAllButton", showSelectAllButton);
	}

	public void setSortingOrder(String sortingOrder) {
		putValue("sortingOrder", sortingOrder);
	}

	public void setSortingURL(String sortingURL) {
		putValue("sortingURL", sortingURL);
	}

	public void setSupportsBulkActions(Boolean supportsBulkActions) {
		putValue("supportsBulkActions", supportsBulkActions);
	}

	public void setViewTypeItems(List<ViewTypeItem> viewTypeItems) {
		putValue("viewTypes", viewTypeItems);
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_managementToolbarDisplayContext = null;
	}

	@Override
	protected String[] getNamespacedParams() {
		return _NAMESPACED_PARAMS;
	}

	protected void populateContext(
		ManagementToolbarDisplayContext managementToolbarDisplayContext) {

		Map<String, Object> context = getContext();

		if (context.get("actionItems") == null) {
			setActionDropdownItems(
				managementToolbarDisplayContext.getActionDropdownItems());
		}

		if (context.get("clearResultsURL") == null) {
			setClearResultsURL(
				managementToolbarDisplayContext.getClearResultsURL());
		}

		if (context.get("componentId") == null) {
			setComponentId(managementToolbarDisplayContext.getComponentId());
		}

		if (context.get("contentRenderer") == null) {
			setContentRenderer(
				managementToolbarDisplayContext.getContentRenderer());
		}

		if (context.get("creationMenu") == null) {
			setCreationMenu(managementToolbarDisplayContext.getCreationMenu());
		}

		if (context.get("defaultEventHandler") == null) {
			setDefaultEventHandler(
				managementToolbarDisplayContext.getDefaultEventHandler());
		}

		if (context.get("disabled") == null) {
			setDisabled(managementToolbarDisplayContext.isDisabled());
		}

		if (context.get("filterItems") == null) {
			setFilterDropdownItems(
				managementToolbarDisplayContext.getFilterDropdownItems());
		}

		if (context.get("filterLabels") == null) {
			setFilterLabelItems(
				managementToolbarDisplayContext.getFilterLabelItems());
		}

		if (context.get("infoPanelId") == null) {
			setInfoPanelId(managementToolbarDisplayContext.getInfoPanelId());
		}

		if (context.get("totalItems") == null) {
			setItemsTotal(managementToolbarDisplayContext.getItemsTotal());
		}

		if (context.get("searchActionURL") == null) {
			setSearchActionURL(
				managementToolbarDisplayContext.getSearchActionURL());
		}

		if (context.get("searchContainerId") == null) {
			setSearchContainerId(
				managementToolbarDisplayContext.getSearchContainerId());
		}

		if (context.get("searchFormMethod") == null) {
			setSearchFormMethod(
				managementToolbarDisplayContext.getSearchFormMethod());
		}

		if (context.get("searchFormName") == null) {
			setSearchFormName(
				managementToolbarDisplayContext.getSearchFormName());
		}

		if (context.get("searchInputName") == null) {
			setSearchInputName(
				managementToolbarDisplayContext.getSearchInputName());
		}

		if (context.get("searchValue") == null) {
			setSearchValue(managementToolbarDisplayContext.getSearchValue());
		}

		if (context.get("selectable") == null) {
			setSelectable(managementToolbarDisplayContext.isSelectable());
		}

		if (context.get("selectedItems") == null) {
			setSelectedItems(
				managementToolbarDisplayContext.getSelectedItems());
		}

		if (context.get("showAdvancedSearch") == null) {
			setShowAdvancedSearch(
				managementToolbarDisplayContext.isShowAdvancedSearch());
		}

		if (context.get("showCreationMenu") == null) {
			setShowCreationMenu(
				managementToolbarDisplayContext.isShowCreationMenu());
		}

		if (context.get("showFiltersDoneButton") == null) {
			setShowFiltersDoneButton(
				managementToolbarDisplayContext.isShowFiltersDoneButton());
		}

		if (context.get("showInfoButton") == null) {
			setShowInfoButton(
				managementToolbarDisplayContext.isShowInfoButton());
		}

		if (context.get("showSearch") == null) {
			setShowSearch(managementToolbarDisplayContext.isShowSearch());
		}

		if (context.get("sortingOrder") == null) {
			setSortingOrder(managementToolbarDisplayContext.getSortingOrder());
		}

		if (context.get("sortingURL") == null) {
			setSortingURL(managementToolbarDisplayContext.getSortingURL());
		}

		if (context.get("supportsBulkActions") == null) {
			setSupportsBulkActions(
				managementToolbarDisplayContext.getSupportsBulkActions());
		}

		if (context.get("viewTypes") == null) {
			setViewTypeItems(
				managementToolbarDisplayContext.getViewTypeItems());
		}
	}

	private Map<String, Object> _getSearchData(String searchActionURL) {
		Map<String, Object> searchData = new HashMap<>();

		String[] parameters = StringUtil.split(
			HttpUtil.getQueryString(searchActionURL), CharPool.AMPERSAND);

		for (String parameter : parameters) {
			if (parameter.length() == 0) {
				continue;
			}

			String[] parameterParts = StringUtil.split(
				parameter, CharPool.EQUAL);

			if (ArrayUtil.isEmpty(parameterParts)) {
				continue;
			}

			String parameterName = parameterParts[0];

			String parameterValue = StringPool.BLANK;

			if (parameterParts.length > 1) {
				parameterValue = parameterParts[1];
			}

			parameterValue = HttpUtil.decodeURL(parameterValue);

			searchData.put(parameterName, parameterValue);
		}

		return searchData;
	}

	private static final String[] _CACHE_STATE = {
		"checkboxStatus", "showSelectAllButton", "selectedItems"
	};

	private static final String[] _NAMESPACED_PARAMS = {
		"infoPanelId", "searchContainerId", "searchFormName", "searchInputName"
	};

	private ManagementToolbarDisplayContext _managementToolbarDisplayContext;

}