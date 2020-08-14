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

package com.liferay.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayViewSerializer;
import com.liferay.frontend.taglib.clay.data.set.model.ClayPaginationEntry;
import com.liferay.frontend.taglib.clay.internal.js.loader.modules.extender.npm.NPMResolverProvider;
import com.liferay.frontend.taglib.clay.internal.servlet.ServletContextUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class DataSetDisplayTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		try {
			if (_creationMenu == null) {
				_creationMenu = new CreationMenu();
			}

			_setActiveViewSettingsJSON();
			_setClayDataSetDisplayViewsContext();
			_setClayPaginationEntries();

			_appURL =
				PortalUtil.getPortalURL(request) +
					"/o/frontend-taglib-clay/app";

			StringBundler sb = new StringBundler(
				11 + (_contextParams.size() * 4));

			sb.append(_appURL);
			sb.append("/data-set/");
			sb.append(_id);
			sb.append(StringPool.FORWARD_SLASH);
			sb.append(_dataProviderKey);
			sb.append("?groupId=");
			sb.append(themeDisplay.getScopeGroupId());
			sb.append("&plid=");
			sb.append(layout.getPlid());
			sb.append("&portletId=");
			sb.append(portletDisplay.getId());

			for (Map.Entry<String, String> entry : _contextParams.entrySet()) {
				sb.append(StringPool.AMPERSAND);
				sb.append(entry.getKey());
				sb.append(StringPool.EQUAL);
				sb.append(entry.getValue());
			}

			_apiURL = sb.toString();

			NPMResolver npmResolver = NPMResolverProvider.getNPMResolver();

			if ((npmResolver != null) && Validator.isNull(_module)) {
				_module = npmResolver.resolveModuleName(
					"frontend-taglib-clay/data_set_display/entry");
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return super.doStartTag();
	}

	public String getActionParameterName() {
		return _actionParameterName;
	}

	public List<DropdownItem> getBulkActionDropdownItems() {
		return _bulkActionDropdownItems;
	}

	public Map<String, String> getContextParams() {
		return _contextParams;
	}

	public CreationMenu getCreationMenu() {
		return _creationMenu;
	}

	public String getDataProviderKey() {
		return _dataProviderKey;
	}

	public String getDeltaParam() {
		return _deltaParam;
	}

	public String getFormId() {
		return _formId;
	}

	public String getId() {
		return _id;
	}

	public int getItemsPerPage() {
		return _itemsPerPage;
	}

	public String getModule() {
		return _module;
	}

	public String getNamespace() {
		return _namespace;
	}

	public String getNestedItemsKey() {
		return _nestedItemsKey;
	}

	public String getNestedItemsReferenceKey() {
		return _nestedItemsReferenceKey;
	}

	public int getPageNumber() {
		return _pageNumber;
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public List<String> getSelectedItems() {
		return _selectedItems;
	}

	public String getSelectedItemsKey() {
		return _selectedItemsKey;
	}

	public String getSelectionType() {
		return _selectionType;
	}

	public String getStyle() {
		return _style;
	}

	public boolean isShowManagementBar() {
		return _showManagementBar;
	}

	public boolean isShowPagination() {
		return _showPagination;
	}

	public boolean isShowSearch() {
		return _showSearch;
	}

	public void setActionParameterName(String actionParameterName) {
		_actionParameterName = actionParameterName;
	}

	public void setBulkActionDropdownItems(
		List<DropdownItem> bulkActionDropdownItems) {

		_bulkActionDropdownItems = bulkActionDropdownItems;
	}

	public void setContextParams(Map<String, String> contextParams) {
		_contextParams = contextParams;
	}

	public void setCreationMenu(CreationMenu clayCreationMenu) {
		_creationMenu = clayCreationMenu;
	}

	public void setDataProviderKey(String dataProviderKey) {
		_dataProviderKey = dataProviderKey;
	}

	public void setDeltaParam(String deltaParam) {
		_deltaParam = deltaParam;
	}

	public void setFormId(String formId) {
		_formId = formId;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setItemsPerPage(int itemsPerPage) {
		_itemsPerPage = itemsPerPage;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	public void setNestedItemsKey(String nestedItemsKey) {
		_nestedItemsKey = nestedItemsKey;
	}

	public void setNestedItemsReferenceKey(String nestedItemsReferenceKey) {
		_nestedItemsReferenceKey = nestedItemsReferenceKey;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		_clayDataSetDisplayViewSerializer =
			ServletContextUtil.getClayDataSetDisplayViewSerializer();

		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setPageNumber(int pageNumber) {
		_pageNumber = pageNumber;
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	public void setSelectedItems(List<String> selectedItems) {
		_selectedItems = selectedItems;
	}

	public void setSelectedItemsKey(String selectedItemsKey) {
		_selectedItemsKey = selectedItemsKey;
	}

	public void setSelectionType(String selectionType) {
		_selectionType = selectionType;
	}

	public void setShowManagementBar(boolean showManagementBar) {
		_showManagementBar = showManagementBar;
	}

	public void setShowPagination(boolean showPagination) {
		_showPagination = showPagination;
	}

	public void setShowSearch(boolean showSearch) {
		_showSearch = showSearch;
	}

	public void setStyle(String style) {
		_style = style;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_actionParameterName = null;
		_activeViewSettingsJSON = null;
		_apiURL = null;
		_appURL = null;
		_bulkActionDropdownItems = new ArrayList<>();
		_clayDataSetDisplayViewsContext = null;
		_clayDataSetDisplayViewSerializer = null;
		_clayPaginationEntries = null;
		_contextParams = new HashMap<>();
		_creationMenu = new CreationMenu();
		_dataProviderKey = null;
		_deltaParam = null;
		_formId = null;
		_id = null;
		_itemsPerPage = 0;
		_module = null;
		_namespace = null;
		_nestedItemsKey = null;
		_nestedItemsReferenceKey = null;
		_pageNumber = 0;
		_paginationSelectedEntry = 0;
		_portletURL = null;
		_selectedItems = null;
		_selectedItemsKey = null;
		_selectionType = null;
		_showManagementBar = true;
		_showPagination = true;
		_showSearch = true;
		_style = "default";
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		request.setAttribute(
			"clay:data-set-display:actionParameterName", _actionParameterName);
		request.setAttribute(
			"clay:data-set-display:activeViewSettingsJSON",
			_activeViewSettingsJSON);
		request.setAttribute("clay:data-set-display:apiURL", _apiURL);
		request.setAttribute("clay:data-set-display:appURL", _appURL);
		request.setAttribute(
			"clay:data-set-display:bulkActionDropdownItems",
			_bulkActionDropdownItems);
		request.setAttribute(
			"clay:data-set-display:clayDataSetDisplayViewsContext",
			_clayDataSetDisplayViewsContext);
		request.setAttribute(
			"clay:data-set-display:clayPaginationEntries",
			_clayPaginationEntries);
		request.setAttribute(
			"clay:data-set-display:creationMenu", _creationMenu);
		request.setAttribute(
			"clay:data-set-display:dataProviderKey", _dataProviderKey);
		request.setAttribute("clay:data-set-display:deltaParam", _deltaParam);
		request.setAttribute("clay:data-set-display:formId", _formId);
		request.setAttribute("clay:data-set-display:id", _id);
		request.setAttribute(
			"clay:data-set-display:itemsPerPage", _itemsPerPage);
		request.setAttribute("clay:data-set-display:module", _module);
		request.setAttribute("clay:data-set-display:namespace", _namespace);
		request.setAttribute(
			"clay:data-set-display:nestedItemsKey", _nestedItemsKey);
		request.setAttribute(
			"clay:data-set-display:nestedItemsReferenceKey",
			_nestedItemsReferenceKey);
		request.setAttribute("clay:data-set-display:pageNumber", _pageNumber);
		request.setAttribute(
			"clay:data-set-display:paginationSelectedEntry",
			_paginationSelectedEntry);
		request.setAttribute("clay:data-set-display:portletURL", _portletURL);
		request.setAttribute(
			"clay:data-set-display:selectedItems", _selectedItems);
		request.setAttribute(
			"clay:data-set-display:selectedItemsKey", _selectedItemsKey);
		request.setAttribute(
			"clay:data-set-display:selectionType", _selectionType);
		request.setAttribute(
			"clay:data-set-display:showManagementBar", _showManagementBar);
		request.setAttribute(
			"clay:data-set-display:showPagination", _showPagination);
		request.setAttribute("clay:data-set-display:showSearch", _showSearch);
		request.setAttribute("clay:data-set-display:style", _style);
	}

	private List<ClayPaginationEntry> _getClayPaginationEntries() {
		List<ClayPaginationEntry> clayPaginationEntries = new ArrayList<>();

		for (int curDelta : PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) {
			if (curDelta > SearchContainer.MAX_DELTA) {
				continue;
			}

			clayPaginationEntries.add(new ClayPaginationEntry(null, curDelta));
		}

		return clayPaginationEntries;
	}

	private void _setActiveViewSettingsJSON() {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(request);

		String clayDataSetDisplaySettingsNamespace =
			ServletContextUtil.getClayDataSetDisplaySettingsNamespace(
				request, _id);

		_activeViewSettingsJSON = portalPreferences.getValue(
			clayDataSetDisplaySettingsNamespace, "activeViewSettingsJSON");
	}

	private void _setClayDataSetDisplayViewsContext() {
		_clayDataSetDisplayViewsContext =
			_clayDataSetDisplayViewSerializer.serialize(
				_id, PortalUtil.getLocale(request));
	}

	private void _setClayPaginationEntries() {
		_clayPaginationEntries = _getClayPaginationEntries();

		Stream<ClayPaginationEntry> stream = _clayPaginationEntries.stream();

		ClayPaginationEntry clayPaginationEntry = stream.filter(
			entry -> entry.getLabel() == _itemsPerPage
		).findAny(
		).orElse(
			null
		);

		_paginationSelectedEntry = _clayPaginationEntries.indexOf(
			clayPaginationEntry);
	}

	private static final String _PAGE = "/data_set_display/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		DataSetDisplayTag.class);

	private String _actionParameterName;
	private String _activeViewSettingsJSON;
	private String _apiURL;
	private String _appURL;
	private List<DropdownItem> _bulkActionDropdownItems = new ArrayList<>();
	private Object _clayDataSetDisplayViewsContext;
	private ClayDataSetDisplayViewSerializer _clayDataSetDisplayViewSerializer;
	private List<ClayPaginationEntry> _clayPaginationEntries;
	private Map<String, String> _contextParams = new HashMap<>();
	private CreationMenu _creationMenu = new CreationMenu();
	private String _dataProviderKey;
	private String _deltaParam;
	private String _formId;
	private String _id;
	private int _itemsPerPage;
	private String _module;
	private String _namespace;
	private String _nestedItemsKey;
	private String _nestedItemsReferenceKey;
	private int _pageNumber;
	private int _paginationSelectedEntry;
	private PortletURL _portletURL;
	private List<String> _selectedItems;
	private String _selectedItemsKey;
	private String _selectionType;
	private boolean _showManagementBar = true;
	private boolean _showPagination = true;
	private boolean _showSearch = true;
	private String _style = "default";

}