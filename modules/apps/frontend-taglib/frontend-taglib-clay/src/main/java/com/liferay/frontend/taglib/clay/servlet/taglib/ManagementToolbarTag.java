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

import com.liferay.frontend.taglib.clay.internal.servlet.taglib.BaseContainerTag;
import com.liferay.frontend.taglib.clay.internal.servlet.taglib.display.context.ManagementToolbarDefaults;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.ManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Marko Cikos
 */
public class ManagementToolbarTag extends BaseContainerTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		String searchValue = getSearchValue();
		String searchInputName = getSearchInputName();

		if ((searchValue == null) && (searchInputName != null)) {
			String searchValueParamValue = ParamUtil.getString(
				request, searchInputName);

			if (!searchValueParamValue.equals(StringPool.BLANK)) {
				setSearchValue(searchValueParamValue);
			}
		}

		List<LabelItem> filterLabelItems = getFilterLabelItems();

		if ((getSearchValue() != null) ||
			((filterLabelItems != null) && !filterLabelItems.isEmpty())) {

			setShowResultsBar(true);
		}

		setShowCreationMenu(
			ManagementToolbarDefaults.isShowCreationMenu(getCreationMenu()));

		return super.doStartTag();
	}

	public List<DropdownItem> getActionDropdownItems() {
		if ((_actionDropdownItems == null) && (_displayContext != null)) {
			return _displayContext.getActionDropdownItems();
		}

		return _actionDropdownItems;
	}

	public String getCheckboxStatus() {
		return _checkboxStatus;
	}

	public String getClearResultsURL() {
		if ((_clearResultsURL == null) && (_displayContext != null)) {
			return _displayContext.getClearResultsURL();
		}

		return _clearResultsURL;
	}

	public String getClearSelectionURL() {
		return _clearSelectionURL;
	}

	public CreationMenu getCreationMenu() {
		if ((_creationMenu == null) && (_displayContext != null)) {
			return _displayContext.getCreationMenu();
		}

		return _creationMenu;
	}

	public ManagementToolbarDisplayContext getDisplayContext() {
		return _displayContext;
	}

	public List<DropdownItem> getFilterDropdownItems() {
		if ((_filterDropdownItems == null) && (_displayContext != null)) {
			return _displayContext.getFilterDropdownItems();
		}

		return _filterDropdownItems;
	}

	public List<LabelItem> getFilterLabelItems() {
		if ((_filterLabelItems == null) && (_displayContext != null)) {
			return _displayContext.getFilterLabelItems();
		}

		return _filterLabelItems;
	}

	public String getInfoPanelId() {
		String infoPanelId = _infoPanelId;

		if (infoPanelId == null) {
			if (_displayContext == null) {
				return null;
			}

			infoPanelId = _displayContext.getInfoPanelId();
		}

		return infoPanelId;
	}

	public Integer getItemsTotal() {
		if (_itemsTotal == null) {
			if (_displayContext == null) {
				return 0;
			}

			return _displayContext.getItemsTotal();
		}

		return _itemsTotal;
	}

	public String getNamespace() {
		if ((_namespace == null) && (_displayContext != null)) {
			return _displayContext.getNamespace();
		}

		return _namespace;
	}

	public String getSearchActionURL() {
		if ((_searchActionURL == null) && (_displayContext != null)) {
			return _displayContext.getSearchActionURL();
		}

		return _searchActionURL;
	}

	public String getSearchContainerId() {
		String searchContainerId = _searchContainerId;

		if (searchContainerId == null) {
			if (_displayContext == null) {
				return null;
			}

			searchContainerId = _displayContext.getSearchContainerId();
		}

		return searchContainerId;
	}

	public String getSearchFormMethod() {
		if (_searchFormMethod == null) {
			if (_displayContext == null) {
				return ManagementToolbarDefaults.getSearchFormMethod();
			}

			return _displayContext.getSearchFormMethod();
		}

		return _searchFormMethod;
	}

	public String getSearchFormName() {
		String searchFormName = _searchFormName;

		if (searchFormName == null) {
			if (_displayContext == null) {
				return null;
			}

			searchFormName = _displayContext.getSearchFormName();
		}

		return searchFormName;
	}

	public String getSearchInputName() {
		String searchInputName = _searchInputName;

		if (searchInputName == null) {
			if (_displayContext == null) {
				return ManagementToolbarDefaults.getSearchInputName();
			}

			searchInputName = _displayContext.getSearchInputName();
		}

		return searchInputName;
	}

	public String getSearchValue() {
		if ((_searchValue == null) && (_displayContext != null)) {
			return _displayContext.getSearchValue();
		}

		return _searchValue;
	}

	public String getSelectAllURL() {
		return _selectAllURL;
	}

	public Integer getSelectedItems() {
		if (_selectedItems == null) {
			if (_displayContext == null) {
				return 0;
			}

			return _displayContext.getSelectedItems();
		}

		return _selectedItems;
	}

	public String getSortingOrder() {
		if ((_sortingOrder == null) && (_displayContext != null)) {
			return _displayContext.getSortingOrder();
		}

		return _sortingOrder;
	}

	public String getSortingURL() {
		if ((_sortingURL == null) && (_displayContext != null)) {
			return _displayContext.getSortingURL();
		}

		return _sortingURL;
	}

	public List<ViewTypeItem> getViewTypeItems() {
		if ((_viewTypeItems == null) && (_displayContext != null)) {
			return _displayContext.getViewTypeItems();
		}

		return _viewTypeItems;
	}

	public Boolean isDisabled() {
		if (_disabled == null) {
			if (_displayContext != null) {
				return _displayContext.isDisabled();
			}

			return false;
		}

		return _disabled;
	}

	public Boolean isSelectable() {
		if ((_selectable == null) && (_displayContext != null)) {
			return _displayContext.isSelectable();
		}

		return _selectable;
	}

	public Boolean isShowCreationMenu() {
		if ((_showCreationMenu == null) && (_displayContext != null)) {
			return _displayContext.isShowCreationMenu();
		}

		return _showCreationMenu;
	}

	public Boolean isShowInfoButton() {
		Boolean showInfoButton = _showInfoButton;

		if (showInfoButton == null) {
			if (_displayContext == null) {
				return ManagementToolbarDefaults.isShowInfoButton(
					getInfoPanelId());
			}

			showInfoButton = _displayContext.isShowInfoButton();
		}

		return showInfoButton;
	}

	public Boolean isShowResultsBar() {
		return _showResultsBar;
	}

	public Boolean isShowSearch() {
		if (_showSearch == null) {
			if (_displayContext != null) {
				return _displayContext.isShowSearch();
			}

			return true;
		}

		return _showSearch;
	}

	public Boolean isShowSelectAllButton() {
		return _showSelectAllButton;
	}

	public Boolean isSupportsBulkActions() {
		if (_supportsBulkActions == null) {
			if (_supportsBulkActions != null) {
				return _displayContext.getSupportsBulkActions();
			}

			return true;
		}

		return _supportsBulkActions;
	}

	public void setActionDropdownItems(List<DropdownItem> actionDropdownItems) {
		_actionDropdownItems = actionDropdownItems;
	}

	public void setCheckboxStatus(String checkboxStatus) {
		_checkboxStatus = checkboxStatus;
	}

	public void setClearResultsURL(String clearResultsURL) {
		_clearResultsURL = clearResultsURL;
	}

	public void setClearSelectionURL(String clearSelectionURL) {
		_clearSelectionURL = clearSelectionURL;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public void setContentRenderer(String contentRenderer) {
		_contentRenderer = contentRenderer;
	}

	public void setCreationMenu(CreationMenu creationMenu) {
		_creationMenu = creationMenu;
	}

	public void setDisabled(Boolean disabled) {
		_disabled = disabled;
	}

	public void setDisplayContext(
		ManagementToolbarDisplayContext displayContext) {

		_displayContext = displayContext;
	}

	public void setFilterDropdownItems(List<DropdownItem> filterDropdownItems) {
		_filterDropdownItems = filterDropdownItems;
	}

	public void setFilterLabelItems(List<LabelItem> filterLabelItems) {
		_filterLabelItems = filterLabelItems;
	}

	public void setInfoPanelId(String infoPanelId) {
		_infoPanelId = infoPanelId;
	}

	public void setItemsTotal(Integer itemsTotal) {
		_itemsTotal = itemsTotal;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	public void setSearchActionURL(String searchActionURL) {
		_searchActionURL = searchActionURL;
	}

	public void setSearchContainerId(String searchContainerId) {
		_searchContainerId = searchContainerId;
	}

	public void setSearchFormMethod(String searchFormMethod) {
		_searchFormMethod = searchFormMethod;
	}

	public void setSearchFormName(String searchFormName) {
		_searchFormName = searchFormName;
	}

	public void setSearchInputName(String searchInputName) {
		_searchInputName = searchInputName;
	}

	public void setSearchValue(String searchValue) {
		_searchValue = searchValue;
	}

	public void setSelectable(Boolean selectable) {
		_selectable = selectable;
	}

	public void setSelectAllURL(String selectAllURL) {
		_selectAllURL = selectAllURL;
	}

	public void setSelectedItems(Integer selectedItems) {
		_selectedItems = selectedItems;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public void setShowAdvancedSearch(Boolean showAdvancedSearch) {
		_showAdvancedSearch = showAdvancedSearch;
	}

	public void setShowCreationMenu(Boolean showCreationMenu) {
		_showCreationMenu = showCreationMenu;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public void setShowFiltersDoneButton(Boolean showFiltersDoneButton) {
		_showFiltersDoneButton = showFiltersDoneButton;
	}

	public void setShowInfoButton(Boolean showInfoButton) {
		_showInfoButton = showInfoButton;
	}

	public void setShowResultsBar(Boolean showResultsBar) {
		_showResultsBar = showResultsBar;
	}

	public void setShowSearch(Boolean showSearch) {
		_showSearch = showSearch;
	}

	public void setShowSelectAllButton(Boolean showSelectAllButton) {
		_showSelectAllButton = showSelectAllButton;
	}

	public void setSortingOrder(String sortingOrder) {
		_sortingOrder = sortingOrder;
	}

	public void setSortingURL(String sortingURL) {
		_sortingURL = sortingURL;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public void setSpritemap(String spritemap) {
		_spritemap = spritemap;
	}

	public void setSupportsBulkActions(Boolean supportsBulkActions) {
		_supportsBulkActions = supportsBulkActions;
	}

	public void setViewTypeItems(List<ViewTypeItem> viewTypeItems) {
		_viewTypeItems = viewTypeItems;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_actionDropdownItems = null;
		_checkboxStatus = "unchecked";
		_clearResultsURL = null;
		_clearSelectionURL = null;
		_contentRenderer = null;
		_creationMenu = null;
		_disabled = null;
		_displayContext = null;
		_filterDropdownItems = null;
		_filterLabelItems = null;
		_infoPanelId = null;
		_itemsTotal = null;
		_namespace = null;
		_searchActionURL = null;
		_searchContainerId = null;
		_searchFormMethod = null;
		_searchFormName = null;
		_searchInputName = null;
		_searchValue = null;
		_selectable = null;
		_selectAllURL = null;
		_selectedItems = null;
		_showAdvancedSearch = null;
		_showCreationMenu = null;
		_showFiltersDoneButton = null;
		_showInfoButton = null;
		_showResultsBar = false;
		_showSearch = null;
		_showSelectAllButton = false;
		_sortingOrder = null;
		_sortingURL = null;
		_spritemap = null;
		_supportsBulkActions = null;
		_viewTypeItems = null;
	}

	@Override
	protected String getHydratedModuleName() {
		return "frontend-taglib-clay/management_toolbar/ManagementToolbar";
	}

	@Override
	protected Map<String, Object> prepareProps(Map<String, Object> props) {
		props.put("clearSelectionURL", getClearSelectionURL());
		props.put("clearResultsURL", getClearResultsURL());
		props.put("creationMenu", getCreationMenu());
		props.put("disabled", isDisabled());
		props.put("filterDropdownItems", getFilterDropdownItems());
		props.put("filterLabelItems", getFilterLabelItems());

		String namespace = getNamespace();

		props.put("infoPanelId", _namespace(namespace, getInfoPanelId()));

		props.put("initialActionDropdownItems", getActionDropdownItems());
		props.put("initialCheckboxStatus", getCheckboxStatus());
		props.put("initialSelectAllButtonVisible", isShowSelectAllButton());
		props.put("initialSelectedItems", getSelectedItems());
		props.put("itemsTotal", getItemsTotal());

		String searchActionURL = getSearchActionURL();

		props.put("searchActionURL", searchActionURL);

		props.put(
			"searchContainerId", _namespace(namespace, getSearchContainerId()));

		String searchFormMethod = getSearchFormMethod();

		if (searchFormMethod.equals("GET") && (searchActionURL != null)) {
			props.put("searchData", _getParamsMap(searchActionURL));
		}

		props.put("searchFormMethod", searchFormMethod);
		props.put("searchFormName", _namespace(namespace, getSearchFormName()));
		props.put(
			"searchInputName", _namespace(namespace, getSearchInputName()));
		props.put("searchValue", getSearchValue());
		props.put("selectAllURL", getSelectAllURL());
		props.put("selectable", isSelectable());
		props.put("showCreationMenu", isShowCreationMenu());
		props.put("showInfoButton", isShowInfoButton());
		props.put("showResultsBar", isShowResultsBar());
		props.put("showSearch", isShowSearch());
		props.put("sortingOrder", getSortingOrder());
		props.put("sortingURL", getSortingURL());
		props.put("supportsBulkActions", isSupportsBulkActions());
		props.put("viewTypeItems", getViewTypeItems());

		return super.prepareProps(props);
	}

	@Override
	protected int processStartTag() throws Exception {
		super.processStartTag();

		JspWriter jspWriter = pageContext.getOut();

		Boolean active = !getCheckboxStatus().equals("unchecked");

		jspWriter.write("<nav class=\"management-bar navbar navbar-expand-md");

		if (active) {
			jspWriter.write(" management-bar-primary navbar-nowrap");
		}
		else {
			jspWriter.write(" management-bar-light");
		}

		jspWriter.write("\"><div class=\"container-fluid");
		jspWriter.write(" container-fluid-max-xl\"><ul class=\"navbar-nav\">");

		Boolean disabled = isDisabled();

		if (isSelectable()) {
			jspWriter.write("<li class=\"nav-item\"><div class=\"");
			jspWriter.write("custom-control custom-checkbox\"><label><input");

			if (active) {
				jspWriter.write(" checked");
			}

			if (disabled) {
				jspWriter.write(" disabled");
			}

			jspWriter.write(" class=\"custom-control-input\" type=\"checkbox");
			jspWriter.write("\" /><span class=\"custom-control-label\">");
			jspWriter.write("</span></label></div></li>");
		}

		IconTag iconTag;

		ResourceBundle resourceBundle = TagResourceBundleUtil.getResourceBundle(
			pageContext);

		if (active) {
			Integer itemsTotal = getItemsTotal();
			Integer selectedItems = getSelectedItems();

			jspWriter.write("<li class=\"nav-item\"><span class=\"navbar-text");
			jspWriter.write("\">");

			if (itemsTotal == selectedItems) {
				jspWriter.write(
					LanguageUtil.get(resourceBundle, "all-selected"));
			}
			else {
				jspWriter.write(
					LanguageUtil.format(
						resourceBundle, "x-of-x",
						new Object[] {selectedItems, itemsTotal}));

				jspWriter.write(StringPool.SPACE);
				jspWriter.write(LanguageUtil.get(resourceBundle, "selected"));
			}

			jspWriter.write("</span></li>");

			if (isSupportsBulkActions()) {
				jspWriter.write("<li class=\"nav-item nav-item-shrink\">");

				String clearSelectionURL = getClearSelectionURL();

				if (clearSelectionURL == null) {
					jspWriter.write("<button class=\"btn btn-unstyled");
					jspWriter.write(" nav-link\" type=\"button\"><span class=");
					jspWriter.write("\"text-truncate-inline\"><span class=\"");
					jspWriter.write("text-truncate\">");

					jspWriter.write(LanguageUtil.get(resourceBundle, "clear"));

					jspWriter.write("</span></span></button>");
				}
				else {
					LinkTag linkTag = new LinkTag();

					linkTag.setCssClass("nav-link");
					linkTag.setHref(clearSelectionURL);
					linkTag.setLabel("clear");

					linkTag.doTag(pageContext);
				}

				jspWriter.write("</li>");

				if (isShowSelectAllButton()) {
					jspWriter.write("<li class=\"nav-item nav-item-shrink\">");

					String selectAllURL = getSelectAllURL();

					if (selectAllURL == null) {
						jspWriter.write("<button class=\"btn btn-unstyled");
						jspWriter.write(" nav-link\" type=\"button\"><span");
						jspWriter.write(" class=\"text-truncate-inline\"><");
						jspWriter.write("span class=\"text-truncate\">");

						jspWriter.write(
							LanguageUtil.get(resourceBundle, "select-all"));

						jspWriter.write("</span></span></button>");
					}
					else {
						LinkTag linkTag = new LinkTag();

						linkTag.setCssClass("nav-link");
						linkTag.setHref(selectAllURL);
						linkTag.setLabel("select-all");

						linkTag.doTag(pageContext);
					}

					jspWriter.write("</li>");
				}
			}
		}

		if (!active && (getFilterDropdownItems() != null)) {
			jspWriter.write("<li class=\"nav-item\"><div class=\"dropdown\">");
			jspWriter.write("<button class=\"btn btn-unstyled dropdown-toggle");
			jspWriter.write(" nav-link\" type=\"button\"><span class=\"");
			jspWriter.write("navbar-breakpoint-down-d-none\"><span class=\"");
			jspWriter.write("navbar-text-truncate\">");
			jspWriter.write(
				LanguageUtil.get(resourceBundle, "filter-and-order"));
			jspWriter.write("</span>");

			iconTag = new IconTag();

			iconTag.setCssClass("inline-item inline-item-after");
			iconTag.setSymbol("caret-bottom");

			iconTag.doTag(pageContext);

			jspWriter.write("</span><span class=\"navbar-breakpoint-d-none\">");

			iconTag = new IconTag();

			iconTag.setSymbol("filter");

			iconTag.doTag(pageContext);

			jspWriter.write("</span></button></div></li>");
		}

		if (getSortingURL() != null) {
			jspWriter.write("<li class=\"nav-item\">");

			LinkTag linkTag = new LinkTag();

			linkTag.setCssClass("nav-link nav-link-monospaced");
			linkTag.setDisplayType("unstyled");
			linkTag.setIcon("order-arrow");

			linkTag.doTag(pageContext);

			jspWriter.write("</li>");
		}

		jspWriter.write("</ul>");

		List<DropdownItem> actionDropdownItems = getActionDropdownItems();

		if (active && (actionDropdownItems != null) &&
			!actionDropdownItems.isEmpty()) {

			jspWriter.write("<ul class=\"navbar-nav\">");

			for (DropdownItem actionDropdownItem : actionDropdownItems) {
				jspWriter.write(
					"<li class=\"nav-item navbar-breakpoint-down-d-none\">");

				LinkTag linkTag = new LinkTag();

				linkTag.setCssClass("nav-link nav-link-monospaced");
				linkTag.setDisplayType("unstyled");
				linkTag.setIcon((String)actionDropdownItem.get("icon"));

				linkTag.doTag(pageContext);

				jspWriter.write("</li>");
			}

			jspWriter.write("<li class=\"nav-item\"><div class=\"dropdown\">");
			jspWriter.write("<button class=\"dropdown-toggle nav-link");
			jspWriter.write(" nav-link-monospaced btn btn-monospaced");
			jspWriter.write(" btn-unstyled\" type=\"button\">");

			IconTag icon = new IconTag();

			icon.setSymbol("ellipsis-v");

			icon.doTag(pageContext);

			jspWriter.write("</div></li></ul>");
		}

		String searchValue = getSearchValue();

		if (!active && isShowSearch()) {
			jspWriter.write("<div class=\"navbar-form navbar-form-autofit ");
			jspWriter.write(" navbar-overlay navbar-overlay-sm-down\"><div");
			jspWriter.write(" class=\"container-fluid container-fluid-max-xl");
			jspWriter.write("\"><form");

			String searchActionURL = getSearchActionURL();

			if (searchActionURL != null) {
				jspWriter.write(" action=\"");
				jspWriter.write(searchActionURL);
				jspWriter.write("\"");
			}

			String searchFormMethod = getSearchFormMethod();

			if (searchFormMethod != null) {
				jspWriter.write(" method=\"");
				jspWriter.write(searchFormMethod);
				jspWriter.write("\"");
			}

			String searchFormName = getSearchFormName();
			String namespace = getNamespace();

			if (searchFormName != null) {
				jspWriter.write(" name=\"");
				jspWriter.write(namespace + searchFormName);
				jspWriter.write("\"");
			}

			jspWriter.write("role=\"search\"><div class=\"input-group\"><div");
			jspWriter.write(" class=\"input-group-item\"><input class=\"");
			jspWriter.write("form-control form-control input-group-inset");
			jspWriter.write(" input-group-inset-after\"");

			if (disabled) {
				jspWriter.write(" disabled");
			}

			String searchInputName = getSearchInputName();

			if (searchInputName != null) {
				jspWriter.write(" name=\"");
				jspWriter.write(namespace + searchInputName);
				jspWriter.write("\"");
			}

			jspWriter.write(" placeholder=\"");
			jspWriter.write(LanguageUtil.get(resourceBundle, "search-for"));
			jspWriter.write("\" type=\"text\"");

			if (searchValue != null) {
				jspWriter.write(" value=\"");
				jspWriter.write(searchValue);
				jspWriter.write("\"");
			}

			jspWriter.write(" /><span class=\"input-group-inset-item");
			jspWriter.write(" input-group-inset-item-after\"><button class=\"");
			jspWriter.write(" navbar-breakpoint-d-none btn btn-monospaced");
			jspWriter.write(" btn-unstyled\">");

			if (disabled) {
				jspWriter.write(" disabled");
			}

			jspWriter.write(" type=\"button\">");

			iconTag = new IconTag();

			iconTag.setSymbol("times");

			iconTag.doTag(pageContext);

			jspWriter.write("</button><button class=\"btn btn-monospaced");
			jspWriter.write(" btn-unstyled\"");

			if (disabled) {
				jspWriter.write(" disabled");
			}

			jspWriter.write(" type=\"submit\">");

			iconTag = new IconTag();

			iconTag.setSymbol("search");

			iconTag.doTag(pageContext);

			jspWriter.write("</button></span></div></div></form></div></div>");
		}

		if (!active) {
			jspWriter.write("<ul class=\"navbar-nav\"><li class=\"nav-item");
			jspWriter.write(" navbar-breakpoint-d-none\"><button class=\"");
			jspWriter.write("nav-link nav-link-monospaced btn btn-monospaced");
			jspWriter.write(" btn-unstyled\" type=\"button\">");

			iconTag = new IconTag();

			iconTag.setSymbol("search");

			iconTag.doTag(pageContext);

			jspWriter.write("</button></li>");

			if (isShowInfoButton()) {
				jspWriter.write("<li class=\"nav-item\"><button class=\"");
				jspWriter.write(" nav-link nav-link-monospaced btn");
				jspWriter.write(" btn-monospaced btn-unstyled\" type=\"button");
				jspWriter.write("\">");

				iconTag = new IconTag();

				iconTag.setSymbol("info-circle-open");

				iconTag.doTag(pageContext);

				jspWriter.write("</button></li>");
			}

			if (getViewTypeItems() != null) {
				jspWriter.write("<li class=\"nav-item\"><div class=\"dropdown");
				jspWriter.write("\"><button class=\"dropdown-toggle nav-link");
				jspWriter.write(" nav-link-monospaced btn btn-monospaced");
				jspWriter.write(" btn-unstyled\" type=\"button\">");

				for (ViewTypeItem viewTypeItem : getViewTypeItems()) {
					if ((Boolean)viewTypeItem.get("active")) {
						iconTag = new IconTag();

						iconTag.setSymbol((String)viewTypeItem.get("icon"));

						iconTag.doTag(pageContext);

						break;
					}
				}

				jspWriter.write("</button></div></li>");
			}

			if (isShowCreationMenu() && (getCreationMenu() != null)) {
				jspWriter.write("<li class=\"nav-item\">");

				LinkTag linkTag = new LinkTag();

				linkTag.setCssClass(
					"nav-btn nav-btn-monospaced btn btn-primary");
				linkTag.setIcon("plus");

				linkTag.doTag(pageContext);

				jspWriter.write("</li>");
			}

			jspWriter.write("</ul>");
		}

		jspWriter.write("</div></nav>");

		if (isShowResultsBar()) {
			jspWriter.write("<nav class=\"subnav-tbar subnav-tbar-primary");
			jspWriter.write(" tbar tbar-inline-xs-down\"><div class=\"");
			jspWriter.write("container-fluid container-fluid-max-xl\">");
			jspWriter.write("<ul class=\"tbar-nav tbar-nav-wrap\">");
			jspWriter.write("<li class=\"tbar-item");

			List<LabelItem> filterLabelItems = getFilterLabelItems();

			if ((filterLabelItems == null) || filterLabelItems.isEmpty()) {
				jspWriter.write(" tbar-item-expand");
			}

			jspWriter.write("\"><div class=\"tbar-section\"><span class=\"");
			jspWriter.write("component-text text-truncate-inline\"><span");
			jspWriter.write(" class=\"text-truncate\">");

			jspWriter.write(
				LanguageUtil.format(
					resourceBundle, "x-results-for-x",
					new Object[] {
						getItemsTotal(),
						(searchValue == null) ? StringPool.BLANK : searchValue
					}));

			jspWriter.write("</span></span></div></li>");

			if (filterLabelItems != null) {
				for (int i = 0; i < filterLabelItems.size(); i++) {
					_writeLabelItem(
						jspWriter, filterLabelItems.get(i),
						i == (filterLabelItems.size() - 1));
				}
			}

			jspWriter.write("<li class=\"tbar-item\"><div class=\"");
			jspWriter.write("tbar-section\">");

			LinkTag linkTag = new LinkTag();

			linkTag.setCssClass("component-link tbar-link");
			linkTag.setHref(getClearResultsURL());
			linkTag.setLabel(LanguageUtil.get(resourceBundle, "clear"));

			linkTag.doTag(pageContext);

			jspWriter.write("</div></li></ul></div></nav>");
		}

		return SKIP_BODY;
	}

	private Map<String, String> _getParamsMap(String url) {
		Map<String, String> searchData = new HashMap<>();

		String[] parameters = StringUtil.split(
			HttpUtil.getQueryString(url), CharPool.AMPERSAND);

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

	private String _namespace(String namespace, String prop) {
		if (prop == null) {
			return null;
		}

		if (namespace == null) {
			return prop;
		}

		return namespace + prop;
	}

	private void _writeLabelItem(
			JspWriter jspWriter, LabelItem labelItem, boolean expand)
		throws Exception {

		jspWriter.write("<li class=\"tbar-item");

		if (expand) {
			jspWriter.write(" tbar-item-expand");
		}

		jspWriter.write("\"><div class=\"tbar-section\">");

		LabelTag labelTag = new LabelTag();

		labelTag.setCssClass("component-label tbar-label");
		labelTag.setDismissible((boolean)labelItem.get("dismissible"));
		labelTag.setDisplayType("unstyled");
		labelTag.setLabel((String)labelItem.get("label"));

		labelTag.doTag(pageContext);

		jspWriter.write("</div></li>");
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"clay:management-toolbar:";

	private List<DropdownItem> _actionDropdownItems;
	private String _checkboxStatus = "unchecked";
	private String _clearResultsURL;
	private String _clearSelectionURL;
	private String _contentRenderer;
	private CreationMenu _creationMenu;
	private Boolean _disabled;
	private ManagementToolbarDisplayContext _displayContext;
	private List<DropdownItem> _filterDropdownItems;
	private List<LabelItem> _filterLabelItems;
	private String _infoPanelId;
	private Integer _itemsTotal;
	private String _namespace;
	private String _searchActionURL;
	private String _searchContainerId;
	private String _searchFormMethod;
	private String _searchFormName;
	private String _searchInputName;
	private String _searchValue;
	private Boolean _selectable;
	private String _selectAllURL;
	private Integer _selectedItems;
	private Boolean _showAdvancedSearch;
	private Boolean _showCreationMenu;
	private Boolean _showFiltersDoneButton;
	private Boolean _showInfoButton;
	private Boolean _showResultsBar = false;
	private Boolean _showSearch;
	private Boolean _showSelectAllButton = false;
	private String _sortingOrder;
	private String _sortingURL;
	private String _spritemap;
	private Boolean _supportsBulkActions;
	private List<ViewTypeItem> _viewTypeItems;

}