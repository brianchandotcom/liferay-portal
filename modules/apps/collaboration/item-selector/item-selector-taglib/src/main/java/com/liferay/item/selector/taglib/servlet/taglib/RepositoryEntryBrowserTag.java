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

import com.liferay.document.library.display.context.DLMimeTypeDisplayContext;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.constants.ItemSelectorPortletKeys;
import com.liferay.item.selector.taglib.ItemSelectorRepositoryEntryBrowserReturnTypeUtil;
import com.liferay.item.selector.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelperUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
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

	/**
	 * @deprecated As of 1.1.0, with no direct replacement
	 */
	@Deprecated
	public void setDesiredItemSelectorReturnTypes(
		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes) {

		_desiredItemSelectorReturnTypes = desiredItemSelectorReturnTypes;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setDlMimeTypeDisplayContext(
		DLMimeTypeDisplayContext dlMimeTypeDisplayContext) {

		_dlMimeTypeDisplayContext = dlMimeTypeDisplayContext;
	}

	public void setEmptyResultsMessage(String emptyResultsMessage) {
		_emptyResultsMessage = emptyResultsMessage;
	}

	public void setExtensions(List<String> extensions) {
		_extensions = extensions;
	}

	public void setItemSelectedEventName(String itemSelectedEventName) {
		_itemSelectedEventName = itemSelectedEventName;
	}

	public void setItemSelectorReturnTypeResolver(
		ItemSelectorReturnTypeResolver itemSelectorReturnTypeResolver) {

		_itemSelectorReturnTypeResolver = itemSelectorReturnTypeResolver;
	}

	public void setMaxFileSize(long maxFileSize) {
		_maxFileSize = maxFileSize;
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
		_dlMimeTypeDisplayContext = null;
		_extensions = new ArrayList<>();
		_itemSelectedEventName = null;
		_maxFileSize = UploadServletRequestConfigurationHelperUtil.getMaxSize();
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
				"dlMimeTypeDisplayContext",
			_dlMimeTypeDisplayContext);
		request.setAttribute(
			"liferay-item-selector:repository-entry-browser:" +
				"emptyResultsMessage",
			_getEmptyResultsMessage(request));

		if (_desiredItemSelectorReturnTypes != null) {
			request.setAttribute(
				"liferay-item-selector:repository-entry-browser:" +
					"existingFileEntryReturnType",
				ItemSelectorRepositoryEntryBrowserReturnTypeUtil.
					getFirstAvailableExistingFileEntryReturnType(
						_desiredItemSelectorReturnTypes));
		}

		request.setAttribute(
			"liferay-item-selector:repository-entry-browser:extensions",
			_extensions);
		request.setAttribute(
			"liferay-item-selector:repository-entry-browser:" +
				"itemSelectedEventName",
			_itemSelectedEventName);
		request.setAttribute(
			"liferay-item-selector:repository-entry-browser:" +
				"itemSelectorReturnTypeResolver",
			_itemSelectorReturnTypeResolver);
		request.setAttribute(
			"liferay-item-selector:repository-entry-browser:maxFileSize",
			_maxFileSize);
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

	private String _getEmptyResultsMessage(HttpServletRequest request) {
		if (Validator.isNotNull(_emptyResultsMessage)) {
			return _emptyResultsMessage;
		}

		return LanguageUtil.get(request, "no-results-were-found");
	}

	private List<ItemSelectorReturnType> _desiredItemSelectorReturnTypes;
	private String _displayStyle;
	private DLMimeTypeDisplayContext _dlMimeTypeDisplayContext;
	private String _emptyResultsMessage;
	private List<String> _extensions = new ArrayList<>();
	private String _itemSelectedEventName;
	private ItemSelectorReturnTypeResolver _itemSelectorReturnTypeResolver;
	private long _maxFileSize =
		UploadServletRequestConfigurationHelperUtil.getMaxSize();
	private PortletURL _portletURL;
	private List<RepositoryEntry> _repositoryEntries = new ArrayList<>();
	private int _repositoryEntriesCount;
	private boolean _showBreadcrumb;
	private boolean _showDragAndDropZone = true;
	private String _tabName;
	private PortletURL _uploadURL;

}