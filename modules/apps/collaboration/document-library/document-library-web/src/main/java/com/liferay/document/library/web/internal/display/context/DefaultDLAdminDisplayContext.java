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

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.display.context.DLAdminDisplayContext;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.web.internal.constants.DLWebKeys;
import com.liferay.document.library.web.internal.display.context.util.DLRequestHelper;
import com.liferay.document.library.web.internal.portlet.toolbar.contributor.DLPortletToolbarContributor;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webdav.WebDAVUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class DefaultDLAdminDisplayContext implements DLAdminDisplayContext {

	public DefaultDLAdminDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, PortletURL currentURLObj,
		HttpServletRequest request) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_currentURLObj = currentURLObj;
		_request = request;
	}

	@Override
	public String getClearResultsURL() throws Exception {
		PortletURL clearResultsURL = _liferayPortletResponse.createRenderURL();

		clearResultsURL.setParameter(
			"mvcRenderCommandName", "/document_library/view");

		return clearResultsURL.toString();
	}

	@Override
	public CreationMenu getCreationMenu() {
		String portletName = _liferayPortletRequest.getPortletName();

		if (!portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN)) {
			return null;
		}

		DLPortletToolbarContributor dlPortletToolbarContributor =
			(DLPortletToolbarContributor)_request.getAttribute(
				DLWebKeys.DOCUMENT_LIBRARY_PORTLET_TOOLBAR_CONTRIBUTOR);

		List<Menu> menus = dlPortletToolbarContributor.getPortletTitleMenus(
			_liferayPortletRequest, _liferayPortletResponse);

		CreationMenu creationMenu = new CreationMenu();

		for (Menu menu : menus) {
			List<URLMenuItem> urlMenuItems =
				(List<URLMenuItem>)(List<?>)menu.getMenuItems();

			for (URLMenuItem urlMenuItem : urlMenuItems) {
				creationMenu.addDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(urlMenuItem.getURL());
						dropdownItem.setLabel(urlMenuItem.getLabel());
					});
			}
		}

		return creationMenu;
	}

	@Override
	public List<NavigationItem> getNavigationItems() {
		String mvcRenderCommandName = ParamUtil.getString(
			_liferayPortletRequest, "mvcRenderCommandName",
			"/document_library/view");

		DLRequestHelper dlRequestHelper = new DLRequestHelper(
			_liferayPortletRequest.getHttpServletRequest());

		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						_populateDocumentLibraryNavigationItem(
							navigationItem, mvcRenderCommandName);
					});

				if (DLPortletKeys.DOCUMENT_LIBRARY_ADMIN.equals(
						dlRequestHelper.getPortletName())) {

					add(
						navigationItem -> {
							_populateFileEntryTypesNavigationItem(
								navigationItem, mvcRenderCommandName);
						});

					add(
						DefaultDLAdminDisplayContext.this
							::_populateMetadataSetsNavigationItem);
				}
			}
		};
	}

	@Override
	public PortletURL getPortletURL() throws Exception {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/search");

		String redirect = ParamUtil.getString(_request, "redirect");

		portletURL.setParameter("redirect", redirect);

		long searchFolderId = ParamUtil.getLong(_request, "searchFolderId");

		portletURL.setParameter(
			"searchFolderId", String.valueOf(searchFolderId));

		String keywords = ParamUtil.getString(_request, "keywords");

		portletURL.setParameter("keywords", keywords);

		return portletURL;
	}

	@Override
	public SearchContainer getSearchContainer() throws Exception {
		SearchContainer searchContainer = new SearchContainer(
			_liferayPortletRequest, getPortletURL(), null, null);

		String mvcRenderCommandName = ParamUtil.getString(
			_request, "mvcRenderCommandName");

		if (mvcRenderCommandName.equals("/document_library/search")) {
			searchContainer.setResults(_getSearchResults(searchContainer));
		}
		else {

			// meter el de /document_library/view_entries.jsp

		}

		return searchContainer;
	}

	@Override
	public PortletURL getSearchURL() {

		// TODO cuidado con el redirect

		PortletURL searchURL = _liferayPortletResponse.createRenderURL();

		searchURL.setParameter(
			"mvcRenderCommandName", "/document_library/search");

		long repositoryId = GetterUtil.getLong(
			(String)_request.getAttribute("view.jsp-repositoryId"));

		searchURL.setParameter("repositoryId", String.valueOf(repositoryId));
		searchURL.setParameter(
			"searchRepositoryId", String.valueOf(repositoryId));

		long folderId = GetterUtil.getLong(
			(String)_request.getAttribute("view.jsp-folderId"));

		searchURL.setParameter("folderId", String.valueOf(folderId));
		searchURL.setParameter("searchFolderId", String.valueOf(folderId));
		searchURL.setParameter(
			"showRepositoryTabs", Boolean.toString(folderId == 0));

		searchURL.setParameter("showSearchInfo", Boolean.TRUE.toString());

		return searchURL;
	}

	@Override
	public int getTotalItems() throws Exception {
		SearchContainer searchContainer = getSearchContainer();

		return searchContainer.getTotal();
	}

	private PortletURL _clonePortletURL() {
		try {
			return PortletURLUtil.clone(
				_currentURLObj, _liferayPortletResponse);
		}
		catch (PortletException pe) {
			throw new RuntimeException(pe);
		}
	}

	private Portlet _getPortlet(ThemeDisplay themeDisplay) {
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return PortletLocalServiceUtil.getPortletById(portletDisplay.getId());
	}

	private List _getSearchResults(SearchContainer searchContainer)
		throws Exception {

		SearchContext searchContext = SearchContextFactory.getInstance(
			_request);

		searchContext.setAttribute("paginationType", "regular");

		long searchRepositoryId = ParamUtil.getLong(
			_request, "searchRepositoryId");

		if (searchRepositoryId == 0) {
			ThemeDisplay themeDisplay = _getThemeDisplay(_request);

			searchRepositoryId = themeDisplay.getScopeGroupId();
		}

		searchContext.setAttribute("searchRepositoryId", searchRepositoryId);
		searchContext.setEnd(searchContainer.getEnd());

		long searchFolderId = ParamUtil.getLong(_request, "searchFolderId");

		searchContext.setFolderIds(new long[] {searchFolderId});

		searchContext.setIncludeDiscussions(true);

		String keywords = ParamUtil.getString(_request, "keywords");

		searchContext.setKeywords(keywords);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setSearchSubfolders(true);

		searchContext.setStart(searchContainer.getStart());

		Hits hits = DLAppServiceUtil.search(searchRepositoryId, searchContext);

		List<SearchResult> searchResults = SearchResultUtil.getSearchResults(
			hits, _request.getLocale());

		List dlSearchResults = new ArrayList<>();

		for (SearchResult searchResult : searchResults) {
			FileEntry fileEntry = null;
			Folder curFolder = null;

			String className = searchResult.getClassName();

			if (className.equals(DLFileEntry.class.getName()) ||
				FileEntry.class.isAssignableFrom(Class.forName(className))) {

				fileEntry = DLAppLocalServiceUtil.getFileEntry(
					searchResult.getClassPK());

				dlSearchResults.add(fileEntry);
			}
			else if (className.equals(DLFolder.class.getName())) {
				curFolder = DLAppLocalServiceUtil.getFolder(
					searchResult.getClassPK());

				dlSearchResults.add(curFolder);
			}
		}

		return dlSearchResults;
	}

	private ThemeDisplay _getThemeDisplay(HttpServletRequest request) {
		return (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
	}

	private void _populateDocumentLibraryNavigationItem(
		NavigationItem navigationItem, String mvcRenderCommandName) {

		navigationItem.setActive(
			!mvcRenderCommandName.equals(
				"/document_library/view_file_entry_types"));

		PortletURL viewDocumentLibraryURL = _clonePortletURL();

		viewDocumentLibraryURL.setParameter(
			"mvcRenderCommandName", "/document_library/view");
		viewDocumentLibraryURL.setParameter(
			"redirect", _currentURLObj.toString());

		navigationItem.setHref(viewDocumentLibraryURL.toString());

		navigationItem.setLabel(
			LanguageUtil.get(
				_liferayPortletRequest.getHttpServletRequest(),
				"documents-and-media"));
	}

	private void _populateFileEntryTypesNavigationItem(
		NavigationItem navigationItem, String mvcRenderCommandName) {

		navigationItem.setActive(
			mvcRenderCommandName.equals(
				"/document_library/view_file_entry_types"));

		PortletURL viewFileEntryTypesURL = _clonePortletURL();

		viewFileEntryTypesURL.setParameter(
			"mvcRenderCommandName", "/document_library/view_file_entry_types");
		viewFileEntryTypesURL.setParameter(
			"redirect", _currentURLObj.toString());

		navigationItem.setHref(viewFileEntryTypesURL.toString());

		navigationItem.setLabel(
			LanguageUtil.get(
				_liferayPortletRequest.getHttpServletRequest(),
				"document-types"));
	}

	private void _populateMetadataSetsNavigationItem(
		NavigationItem navigationItem) {

		navigationItem.setActive(false);

		ThemeDisplay themeDisplay = _getThemeDisplay(
			_liferayPortletRequest.getHttpServletRequest());

		Portlet portlet = _getPortlet(themeDisplay);

		PortletURL viewMetadataSetsURL = PortletURLFactoryUtil.create(
			_liferayPortletRequest,
			PortletProviderUtil.getPortletId(
				DDMStructure.class.getName(), PortletProvider.Action.VIEW),
			PortletRequest.RENDER_PHASE);

		viewMetadataSetsURL.setParameter("mvcPath", "/view.jsp");
		viewMetadataSetsURL.setParameter(
			"backURL", themeDisplay.getURLCurrent());
		viewMetadataSetsURL.setParameter(
			"groupId", String.valueOf(themeDisplay.getScopeGroupId()));
		viewMetadataSetsURL.setParameter(
			"refererPortletName", DLPortletKeys.DOCUMENT_LIBRARY);
		viewMetadataSetsURL.setParameter(
			"refererWebDAVToken", WebDAVUtil.getStorageToken(portlet));
		viewMetadataSetsURL.setParameter(
			"showAncestorScopes", Boolean.TRUE.toString());
		viewMetadataSetsURL.setParameter(
			"showManageTemplates", Boolean.FALSE.toString());

		navigationItem.setHref(viewMetadataSetsURL.toString());

		navigationItem.setLabel(
			LanguageUtil.get(
				_liferayPortletRequest.getHttpServletRequest(),
				"metadata-sets"));
	}

	private final PortletURL _currentURLObj;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final HttpServletRequest _request;

}