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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian I. Kim
 */
public class DLViewMoreMenuItemsDisplayContext {

	public DLViewMoreMenuItemsDisplayContext(
		long folderId, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_folderId = folderId;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
	}

	public String getClearResultsURL() {
		return getSearchActionURL();
	}

	public String getDLFileEntryTypeScopeName(
			DLFileEntryType dlFileEntryType, Locale locale)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group scopeGroup = themeDisplay.getScopeGroup();

		if (dlFileEntryType.getGroupId() == scopeGroup.getGroupId()) {
			if (scopeGroup.getType() == GroupConstants.TYPE_DEPOT) {
				return LanguageUtil.get(
					_httpServletRequest, "current-asset-library");
			}

			return LanguageUtil.get(_httpServletRequest, "current-site");
		}

		Group dlFileEntryTypeGroup = GroupLocalServiceUtil.getGroup(
			dlFileEntryType.getGroupId());

		return dlFileEntryTypeGroup.getName(locale);
	}

	public String getEventName() {
		if (_eventName != null) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(
			_renderRequest, "eventName",
			_renderResponse.getNamespace() + "selectAddMenuItem");

		return _eventName;
	}

	public List<NavigationItem> getNavigationItems() {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(true);
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "document-types"));
			}
		).build();
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcPath", "/document_library/view_more_menu_items.jsp");
		portletURL.setParameter("folderId", String.valueOf(_folderId));
		portletURL.setParameter("eventName", getEventName());

		return portletURL;
	}

	public String getSearchActionURL() {
		return String.valueOf(getPortletURL());
	}

	public SearchContainer<DLFileEntryType> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<DLFileEntryType> searchContainer = new SearchContainer(
			_renderRequest, new DisplayTerms(_httpServletRequest),
			new DisplayTerms(_httpServletRequest),
			SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA,
			getPortletURL(), null,
			LanguageUtil.get(_httpServletRequest, "there-are-no-results"));

		DisplayTerms searchTerms = searchContainer.getSearchTerms();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long folderId = _getPrimaryFolderId(_folderId);
		boolean includeBasicFileEntryType = ParamUtil.getBoolean(
			_renderRequest, "includeBasicFileEntryType");

		searchContainer.setResults(
			DLFileEntryTypeServiceUtil.search(
				themeDisplay.getCompanyId(), folderId,
				_getCurrentAndAncestorSiteAndDepotGroupIds(
					themeDisplay.getScopeGroupId()),
				searchTerms.getKeywords(), includeBasicFileEntryType,
				_inherited, searchContainer.getStart(),
				searchContainer.getEnd()));
		searchContainer.setTotal(
			DLFileEntryTypeServiceUtil.searchCount(
				themeDisplay.getCompanyId(), folderId,
				_getCurrentAndAncestorSiteAndDepotGroupIds(
					themeDisplay.getScopeGroupId()),
				searchTerms.getKeywords(), includeBasicFileEntryType,
				_inherited));

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public int getTotalItems() throws PortalException {
		SearchContainer<DLFileEntryType> searchContainer = getSearchContainer();

		return searchContainer.getTotal();
	}

	private long[] _getCurrentAndAncestorSiteAndDepotGroupIds(long groupId)
		throws PortalException {

		return ArrayUtil.append(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId),
			ListUtil.toLongArray(
				DepotEntryLocalServiceUtil.getGroupConnectedDepotEntries(
					groupId, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS),
				DepotEntry::getGroupId));
	}

	private long _getPrimaryFolderId(long folderId) throws PortalException {
		while (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			Folder folder = DLAppServiceUtil.getFolder(folderId);

			if ((folder != null) && (folder.getModel() instanceof DLFolder)) {
				DLFolder dlFolder = (DLFolder)folder.getModel();

				if (dlFolder.getRestrictionType() ==
						DLFolderConstants.
							RESTRICTION_TYPE_FILE_ENTRY_TYPES_AND_WORKFLOW) {

					_inherited = false;

					break;
				}

				folderId = dlFolder.getParentFolderId();
			}
		}

		return folderId;
	}

	private String _eventName;
	private final long _folderId;
	private final HttpServletRequest _httpServletRequest;
	private boolean _inherited = true;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<DLFileEntryType> _searchContainer;

}