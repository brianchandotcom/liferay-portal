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

package com.liferay.item.selector.taglib.servlet.taglib;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UploadableFileReturnType;
import com.liferay.item.selector.taglib.ItemSelectorRepositoryEntryBrowserReturnTypeUtil;
import com.liferay.item.selector.taglib.servlet.ServletContextUtil;
import com.liferay.item.selector.web.constants.ItemSelectorPortletKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortalPreferences;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Roberto Díaz
 */
public class RepositoryEntryBrowserTag extends IncludeTag {

	public static final String[] DISPLAY_STYLES =
		new String[] {"icon", "descriptive", "list"};

	public void setDesiredItemSelectorReturnTypes(
		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes) {

		_desiredItemSelectorReturnTypes = desiredItemSelectorReturnTypes;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setEmptyResultsMessage(String emptyResultsMessage) {
		_emptyResultsMessage = emptyResultsMessage;
	}

	public void setItemSelectedEventName(String itemSelectedEventName) {
		_itemSelectedEventName = itemSelectedEventName;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	public void setRepositoryEntries(List<RepositoryEntry> repositoryEntries) {
		_repositoryEntries = repositoryEntries;
	}

	public void setRepositoryEntriesCount(int repositoryEntriesCount) {
		_repositoryEntriesCount = repositoryEntriesCount;
	}

	public void setShowBreadcrumb(boolean showBreadcrumb) {
		_showBreadcrumb = showBreadcrumb;
	}

	public void setShowDragAndDropZone(boolean showDragAndDropZone) {
		_showDragAndDropZone = showDragAndDropZone;
	}

	public void setTabName(String tabName) {
		_tabName = tabName;
	}

	public void setUploadURL(PortletURL uploadURL) {
		_uploadURL = uploadURL;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_desiredItemSelectorReturnTypes = null;
		_emptyResultsMessage = null;
		_displayStyle = null;
		_itemSelectedEventName = null;
		_portletURL = null;
		_repositoryEntries = new ArrayList<>();
		_repositoryEntriesCount = 0;
		_showBreadcrumb = false;
		_showDragAndDropZone = true;
		_tabName = null;
		_uploadURL = null;
	}

	protected String getDisplayStyle() {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(request);

		String displayStyle = ParamUtil.getString(request, "displayStyle");

		if (Validator.isNotNull(displayStyle)) {
			displayStyle = getSafeDisplayStyle(displayStyle);

			portalPreferences.setValue(
				ItemSelectorPortletKeys.ITEM_SELECTOR, "display-style",
				displayStyle);

			return displayStyle;
		}

		if (Validator.isNotNull(_displayStyle)) {
			return getSafeDisplayStyle(_displayStyle);
		}

		return portalPreferences.getValue(
			ItemSelectorPortletKeys.ITEM_SELECTOR, "display-style",
			DISPLAY_STYLES[0]);
	}

	protected ItemSelectorReturnType getDraggableFileReturnType() {
		ItemSelectorReturnType firstDraggableFileReturnType =
			ItemSelectorRepositoryEntryBrowserReturnTypeUtil.
				getFirstAvailableDraggableFileReturnType(
					_desiredItemSelectorReturnTypes);

		if (Validator.equals(
				ClassUtil.getClassName(firstDraggableFileReturnType),
				UploadableFileReturnType.class.getName()) &&
			(_uploadURL == null)) {

			return null;
		}

		return firstDraggableFileReturnType;
	}

	@Override
	protected String getPage() {
		return "/repository_entry_browser/page.jsp";
	}

	protected String getSafeDisplayStyle(String displayStyle) {
		if (ArrayUtil.contains(DISPLAY_STYLES, displayStyle)) {
			return displayStyle;
		}

		return DISPLAY_STYLES[0];
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-item-selector:repository-entry-browser:displayStyle",
			getDisplayStyle());
		request.setAttribute(
			"liferay-item-selector:repository-entry-browser:" +
				"emptyResultsMessage",
			getEmptyResultsMessage(request));
		request.setAttribute(
			"liferay-item-selector:repository-entry-browser:" +
				"draggableFileReturnType",
			getDraggableFileReturnType());
		request.setAttribute(
			"liferay-item-selector:repository-entry-browser:" +
				"existingFileEntryReturnType",
			ItemSelectorRepositoryEntryBrowserReturnTypeUtil.
				getFirstAvailableExistingFileEntryReturnType(
					_desiredItemSelectorReturnTypes));
		request.setAttribute(
			"liferay-item-selector:repository-entry-browser:" +
				"itemSelectedEventName",
			_itemSelectedEventName);
		request.setAttribute(
			"liferay-item-selector:repository-entry-browser:portletURL",
			_portletURL);
		request.setAttribute(
			"liferay-item-selector:repository-entry-browser:repositoryEntries",
			_repositoryEntries);
		request.setAttribute(
			"liferay-item-selector:repository-entry-browser:" +
				"repositoryEntriesCount",
			_repositoryEntriesCount);
		request.setAttribute(
			"liferay-item-selector:repository-entry-browser:showBreadcrumb",
			_showBreadcrumb);
		request.setAttribute(
			"liferay-item-selector:repository-entry-browser:" +
				"showDragAndDropZone",
			_showDragAndDropZone);
		request.setAttribute(
			"liferay-item-selector:repository-entry-browser:tabName", _tabName);
		request.setAttribute(
			"liferay-item-selector:repository-entry-browser:uploadURL",
			_uploadURL);
	}

	private String getEmptyResultsMessage(HttpServletRequest request) {
		if (Validator.isNotNull(_emptyResultsMessage)) {
			return _emptyResultsMessage;
		}

		return LanguageUtil.get(request, "no-results-were-found");
	}

	private List<ItemSelectorReturnType> _desiredItemSelectorReturnTypes;
	private String _displayStyle;
	private String _emptyResultsMessage;
	private String _itemSelectedEventName;
	private PortletURL _portletURL;
	private List<RepositoryEntry> _repositoryEntries = new ArrayList<>();
	private int _repositoryEntriesCount = 0;
	private boolean _showBreadcrumb;
	private boolean _showDragAndDropZone = true;
	private String _tabName;
	private PortletURL _uploadURL;

}