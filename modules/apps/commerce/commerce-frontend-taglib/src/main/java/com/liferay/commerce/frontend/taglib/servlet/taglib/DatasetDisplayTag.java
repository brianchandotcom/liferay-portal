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

package com.liferay.commerce.frontend.taglib.servlet.taglib;

import com.liferay.commerce.frontend.ClayCreationMenu;
import com.liferay.commerce.frontend.ClayMenuActionItem;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetDisplayViewSerializer;
import com.liferay.commerce.frontend.taglib.internal.model.ClayPaginationEntry;
import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
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
public class DatasetDisplayTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		try {
			if (_clayCreationMenu == null) {
				_clayCreationMenu = new ClayCreationMenu();
			}

			_setTableContext();
			_setPagination();

			StringBundler sb = new StringBundler(
				11 + (_contextParams.size() * 4));

			sb.append(PortalUtil.getPortalURL(request));
			sb.append("/o/commerce-ui/commerce-data-set/");
			sb.append(themeDisplay.getScopeGroupId());
			sb.append(StringPool.FORWARD_SLASH);
			sb.append(_id);
			sb.append(StringPool.FORWARD_SLASH);
			sb.append(_dataProviderKey);
			sb.append("?plid=");
			sb.append(layout.getPlid());
			sb.append("&portletId=");
			sb.append(portletDisplay.getId());

			for (Map.Entry<String, String> entry : _contextParams.entrySet()) {
				sb.append(StringPool.AMPERSAND);
				sb.append(entry.getKey());
				sb.append(StringPool.EQUAL);
				sb.append(entry.getValue());
			}

			_dataSetAPI = sb.toString();

			_spritemap =
				themeDisplay.getPathThemeImages() + "/lexicon/icons.svg";
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return super.doStartTag();
	}

	public void setBulkActions(List<ClayMenuActionItem> bulkActions) {
		_bulkActions = bulkActions;
	}

	public void setClayCreationMenu(ClayCreationMenu clayCreationMenu) {
		_clayCreationMenu = clayCreationMenu;
	}

	public void setContextParams(Map<String, String> contextParams) {
		_contextParams = contextParams;
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

		_bulkActions = new ArrayList<>();
		_clayCreationMenu = new ClayCreationMenu();
		_clayDataSetDisplayViewsContext = null;
		_clayDataSetDisplayViewSerializer = null;
		_contextParams = new HashMap<>();
		_dataProviderKey = null;
		_dataSetAPI = null;
		_deltaParam = null;
		_formId = null;
		_id = null;
		_itemsPerPage = 0;
		_namespace = null;
		_nestedItemsKey = null;
		_nestedItemsReferenceKey = null;
		_pageNumber = 0;
		_paginationEntries = null;
		_paginationSelectedEntry = 0;
		_portletURL = null;
		_selectedItems = null;
		_selectedItemsKey = null;
		_selectionType = null;
		_showManagementBar = true;
		_showPagination = true;
		_showSearch = true;
		_spritemap = null;
		_style = "default";
	}

	protected List<ClayPaginationEntry> getClayPaginationEntries() {
		List<ClayPaginationEntry> clayPaginationEntries = new ArrayList<>();

		for (int curDelta : PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) {
			if (curDelta > SearchContainer.MAX_DELTA) {
				continue;
			}

			clayPaginationEntries.add(new ClayPaginationEntry(null, curDelta));
		}

		return clayPaginationEntries;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		request.setAttribute(
			"liferay-commerce:dataset-display:bulkActions", _bulkActions);
		request.setAttribute(
			"liferay-commerce:dataset-display:clayCreationMenu",
			_clayCreationMenu);
		request.setAttribute(
			"liferay-commerce:dataset-display:clayDataSetDisplayViewsContext",
			_clayDataSetDisplayViewsContext);
		request.setAttribute(
			"liferay-commerce:dataset-display:dataProviderKey",
			_dataProviderKey);
		request.setAttribute(
			"liferay-commerce:dataset-display:dataSetAPI", _dataSetAPI);
		request.setAttribute(
			"liferay-commerce:dataset-display:deltaParam", _deltaParam);
		request.setAttribute(
			"liferay-commerce:dataset-display:formId", _formId);
		request.setAttribute("liferay-commerce:dataset-display:id", _id);
		request.setAttribute(
			"liferay-commerce:dataset-display:itemsPerPage", _itemsPerPage);
		request.setAttribute(
			"liferay-commerce:dataset-display:namespace", _namespace);
		request.setAttribute(
			"liferay-commerce:dataset-display:nestedItemsKey", _nestedItemsKey);
		request.setAttribute(
			"liferay-commerce:dataset-display:nestedItemsReferenceKey",
			_nestedItemsReferenceKey);
		request.setAttribute(
			"liferay-commerce:dataset-display:pageNumber", _pageNumber);
		request.setAttribute(
			"liferay-commerce:dataset-display:paginationEntries",
			_paginationEntries);
		request.setAttribute(
			"liferay-commerce:dataset-display:paginationSelectedEntry",
			_paginationSelectedEntry);
		request.setAttribute(
			"liferay-commerce:dataset-display:portletURL", _portletURL);
		request.setAttribute(
			"liferay-commerce:dataset-display:selectedItems", _selectedItems);
		request.setAttribute(
			"liferay-commerce:dataset-display:selectedItemsKey",
			_selectedItemsKey);
		request.setAttribute(
			"liferay-commerce:dataset-display:selectionType", _selectionType);
		request.setAttribute(
			"liferay-commerce:dataset-display:showManagementBar",
			_showManagementBar);
		request.setAttribute(
			"liferay-commerce:dataset-display:showPagination", _showPagination);
		request.setAttribute(
			"liferay-commerce:dataset-display:showSearch", _showSearch);
		request.setAttribute(
			"liferay-commerce:dataset-display:spritemap", _spritemap);
		request.setAttribute("liferay-commerce:dataset-display:style", _style);
	}

	private void _setPagination() {
		_paginationEntries = getClayPaginationEntries();

		Stream<ClayPaginationEntry> stream = _paginationEntries.stream();

		ClayPaginationEntry clayPaginationEntry = stream.filter(
			entry -> entry.getLabel() == _itemsPerPage
		).findAny(
		).orElse(
			null
		);

		_paginationSelectedEntry = _paginationEntries.indexOf(
			clayPaginationEntry);
	}

	private void _setTableContext() {
		_clayDataSetDisplayViewsContext =
			_clayDataSetDisplayViewSerializer.serialize(
				_id, PortalUtil.getLocale(request));
	}

	private static final String _PAGE = "/dataset_display/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		DatasetDisplayTag.class);

	private List<ClayMenuActionItem> _bulkActions = new ArrayList<>();
	private ClayCreationMenu _clayCreationMenu = new ClayCreationMenu();
	private Object _clayDataSetDisplayViewsContext;
	private ClayDataSetDisplayViewSerializer _clayDataSetDisplayViewSerializer;
	private Map<String, String> _contextParams = new HashMap<>();
	private String _dataProviderKey;
	private String _dataSetAPI;
	private String _deltaParam;
	private String _formId;
	private String _id;
	private int _itemsPerPage;
	private String _namespace;
	private String _nestedItemsKey;
	private String _nestedItemsReferenceKey;
	private int _pageNumber;
	private List<ClayPaginationEntry> _paginationEntries;
	private int _paginationSelectedEntry;
	private PortletURL _portletURL;
	private List<String> _selectedItems;
	private String _selectedItemsKey;
	private String _selectionType;
	private boolean _showManagementBar = true;
	private boolean _showPagination = true;
	private boolean _showSearch = true;
	private String _spritemap;
	private String _style = "default";

}