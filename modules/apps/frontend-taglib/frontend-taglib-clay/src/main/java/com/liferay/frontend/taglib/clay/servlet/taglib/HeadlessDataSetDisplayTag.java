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
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilterSerializer;
import com.liferay.frontend.taglib.clay.data.set.model.ClayPaginationEntry;
import com.liferay.frontend.taglib.clay.data.set.servlet.taglib.util.ClayDataSetActionDropdownItem;
import com.liferay.frontend.taglib.clay.internal.js.loader.modules.extender.npm.NPMResolverProvider;
import com.liferay.frontend.taglib.clay.internal.servlet.ServletContextUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class HeadlessDataSetDisplayTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		try {
			if (_creationMenu == null) {
				_creationMenu = new CreationMenu();
			}

			_setClayDataSetDisplayViewsContext();
			_setClayDataSetFiltersContext();
			_setClayPaginationEntries();

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

	public String getApiURL() {
		return _apiURL;
	}

	public List<DropdownItem> getBulkActionDropdownItems() {
		return _bulkActionDropdownItems;
	}

	public List<ClayDataSetActionDropdownItem>
		getClayDataSetActionDropdownItems() {

		return _clayDataSetActionDropdownItems;
	}

	public CreationMenu getCreationMenu() {
		return _creationMenu;
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

	public void setApiURL(String apiURL) {
		_apiURL = apiURL;
	}

	public void setBulkActionDropdownItems(List<DropdownItem> bulkActions) {
		_bulkActionDropdownItems = bulkActions;
	}

	public void setClayDataSetActionDropdownItems(
		List<ClayDataSetActionDropdownItem> clayDataSetActionDropdownItems) {

		_clayDataSetActionDropdownItems = clayDataSetActionDropdownItems;
	}

	public void setCreationMenu(CreationMenu creationMenu) {
		_creationMenu = creationMenu;
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

		_clayDataSetFilterSerializer =
			ServletContextUtil.getClayDataSetFilterSerializer();

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

		_apiURL = null;
		_bulkActionDropdownItems = new ArrayList<>();
		_clayDataSetActionDropdownItems = new ArrayList<>();
		_clayDataSetDisplayViewsContext = null;
		_clayDataSetDisplayViewSerializer = null;
		_clayDataSetFiltersContext = null;
		_clayDataSetFilterSerializer = null;
		_clayPaginationEntries = null;
		_creationMenu = new CreationMenu();
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
		request.setAttribute("clay:headless-data-set-display:apiURL", _apiURL);
		request.setAttribute(
			"clay:headless-data-set-display:bulkActionDropdownItems",
			_bulkActionDropdownItems);
		request.setAttribute(
			"clay:headless-data-set-display:clayDataSetActionDropdownItems",
			_clayDataSetActionDropdownItems);
		request.setAttribute(
			"clay:headless-data-set-display:clayDataSetDisplayViewsContext",
			_clayDataSetDisplayViewsContext);
		request.setAttribute(
			"clay:headless-data-set-display:clayDataSetFiltersContext",
			_clayDataSetFiltersContext);
		request.setAttribute(
			"clay:headless-data-set-display:clayPaginationEntries",
			_clayPaginationEntries);
		request.setAttribute(
			"clay:headless-data-set-display:creationMenu", _creationMenu);
		request.setAttribute("clay:headless-data-set-display:formId", _formId);
		request.setAttribute("clay:headless-data-set-display:id", _id);
		request.setAttribute(
			"clay:headless-data-set-display:itemsPerPage", _itemsPerPage);
		request.setAttribute(
			"clay:headless-data-set-display:namespace", _namespace);
		request.setAttribute(
			"clay:headless-data-set-display:nestedItemsKey", _nestedItemsKey);
		request.setAttribute(
			"clay:headless-data-set-display:nestedItemsReferenceKey",
			_nestedItemsReferenceKey);
		request.setAttribute(
			"clay:headless-data-set-display:pageNumber", _pageNumber);
		request.setAttribute(
			"clay:headless-data-set-display:paginationSelectedEntry",
			_paginationSelectedEntry);
		request.setAttribute(
			"clay:headless-data-set-display:portletURL", _portletURL);
		request.setAttribute(
			"clay:headless-data-set-display:selectedItems", _selectedItems);
		request.setAttribute(
			"clay:headless-data-set-display:selectedItemsKey",
			_selectedItemsKey);
		request.setAttribute(
			"clay:headless-data-set-display:selectionType", _selectionType);
		request.setAttribute(
			"clay:headless-data-set-display:showManagementBar",
			_showManagementBar);
		request.setAttribute(
			"clay:headless-data-set-display:showPagination", _showPagination);
		request.setAttribute(
			"clay:headless-data-set-display:showSearch", _showSearch);
		request.setAttribute("clay:headless-data-set-display:style", _style);
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

	private void _setClayDataSetDisplayViewsContext() {
		_clayDataSetDisplayViewsContext =
			_clayDataSetDisplayViewSerializer.serialize(
				_id, PortalUtil.getLocale(request));
	}

	private void _setClayDataSetFiltersContext() {
		_clayDataSetFiltersContext = _clayDataSetFilterSerializer.serialize(
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

	private static final String _PAGE = "/headless_data_set_display/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		HeadlessDataSetDisplayTag.class);

	private String _apiURL;
	private List<DropdownItem> _bulkActionDropdownItems = new ArrayList<>();
	private List<ClayDataSetActionDropdownItem>
		_clayDataSetActionDropdownItems = new ArrayList<>();
	private Object _clayDataSetDisplayViewsContext;
	private ClayDataSetDisplayViewSerializer _clayDataSetDisplayViewSerializer;
	private Object _clayDataSetFiltersContext;
	private ClayDataSetFilterSerializer _clayDataSetFilterSerializer;
	private List<ClayPaginationEntry> _clayPaginationEntries;
	private CreationMenu _creationMenu = new CreationMenu();
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