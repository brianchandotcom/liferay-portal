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
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.web.internal.constants.DLWebKeys;
import com.liferay.document.library.web.internal.display.context.logic.DLPortletInstanceSettingsHelper;
import com.liferay.document.library.web.internal.display.context.util.DLRequestHelper;
import com.liferay.document.library.web.internal.portlet.toolbar.contributor.DLPortletToolbarContributor;
import com.liferay.document.library.web.internal.settings.DLPortletInstanceSettings;
import com.liferay.document.library.web.internal.util.DLTrashUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SafeConsumer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class DLAdminManagementToolbarDisplayContext {

	public DLAdminManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		DLAdminDisplayContext dlAdminDisplayContext) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_dlAdminDisplayContext = dlAdminDisplayContext;

		_request = liferayPortletRequest.getHttpServletRequest();

		_dlRequestHelper = new DLRequestHelper(_request);

		_dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(
			_dlRequestHelper);

		_dlTrashUtil = (DLTrashUtil)_request.getAttribute(
			DLWebKeys.DOCUMENT_LIBRARY_TRASH_UTIL);

		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				User user = _themeDisplay.getUser();
				Group scopeGroup = _themeDisplay.getScopeGroup();

				if (!user.isDefaultUser() &&
					(!scopeGroup.isStaged() || scopeGroup.isStagingGroup() ||
					 !scopeGroup.isStagedPortlet(
						 DLPortletKeys.DOCUMENT_LIBRARY))) {

					add(
						SafeConsumer.ignore(
							dropdownItem -> {
								dropdownItem.setHref(
									StringBundler.concat(
										"javascript:Liferay.fire('",
										_liferayPortletResponse.getNamespace(),
										"editEntry', {action: 'download'});",
										"void(0);"));
								dropdownItem.setIcon("download");
								dropdownItem.setLabel(
									LanguageUtil.get(_request, "download"));
								dropdownItem.setQuickAction(true);
							}));

					add(
						SafeConsumer.ignore(
							dropdownItem -> {
								dropdownItem.setHref(
									StringBundler.concat(
										"javascript:Liferay.fire('",
										_liferayPortletResponse.getNamespace(),
										"editEntry', {action: '",
										Constants.CHECKIN, "'}); void(0);"));
								dropdownItem.setIcon("unlock");
								dropdownItem.setLabel(
									LanguageUtil.get(_request, "checkin"));
								dropdownItem.setQuickAction(true);
							}));

					add(
						SafeConsumer.ignore(
							dropdownItem -> {
								dropdownItem.setHref(
									StringBundler.concat(
										"javascript:Liferay.fire('",
										_liferayPortletResponse.getNamespace(),
										"editEntry', {action: '",
										Constants.CHECKOUT, "'}); void(0);"));
								dropdownItem.setIcon("lock");
								dropdownItem.setLabel(
									LanguageUtil.get(
										_request, "checkout[document]"));
								dropdownItem.setQuickAction(true);
							}));

					add(
						SafeConsumer.ignore(
							dropdownItem -> {
								dropdownItem.setHref(
									StringBundler.concat(
										"javascript:Liferay.fire('",
										_liferayPortletResponse.getNamespace(),
										"editEntry', {action: '",
										Constants.MOVE, "'}); void(0);"));
								dropdownItem.setIcon("change");
								dropdownItem.setLabel(
									LanguageUtil.get(_request, "move"));
								dropdownItem.setQuickAction(true);
							}));
				}

				if (!user.isDefaultUser()) {
					add(
						SafeConsumer.ignore(
							dropdownItem -> {
								dropdownItem.setHref(
									StringBundler.concat(
										"javascript:",
										_liferayPortletResponse.getNamespace(),
										"deleteEntries();"));

								if (_dlTrashUtil.isTrashEnabled(
										scopeGroup.getGroupId(),
										_getRepositoryId())) {

									dropdownItem.setIcon("trash");
									dropdownItem.setLabel(
										LanguageUtil.get(
											_request, "recycle-bin"));
								}
								else {
									dropdownItem.setIcon("times");
									dropdownItem.setLabel(
										LanguageUtil.get(_request, "delete"));
								}

								dropdownItem.setQuickAction(true);
							}));
				}
			}
		};
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = _liferayPortletResponse.createRenderURL();

		clearResultsURL.setParameter(
			"mvcRenderCommandName", "/document_library/view");

		return clearResultsURL.toString();
	}

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

	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					SafeConsumer.ignore(
						dropdownGroupItem -> {
							dropdownGroupItem.setDropdownItems(
								_getFilterNavigationDropdownItems());
							dropdownGroupItem.setLabel(
								LanguageUtil.get(
									_request, "filter-by-navigation"));
						}));

				if (!_isSearch() && !_isNavigationRecent()) {
					addGroup(
						dropdownGroupItem -> {
							dropdownGroupItem.setDropdownItems(
								_getOrderByDropdownItems());
							dropdownGroupItem.setLabel(
								LanguageUtil.get(_request, "order-by"));
						});
				}
			}
		};
	}

	public PortletURL getSearchURL() {
		PortletURL searchURL = _liferayPortletResponse.createRenderURL();

		searchURL.setParameter(
			"mvcRenderCommandName", "/document_library/search");

		long repositoryId = _getRepositoryId();

		searchURL.setParameter("repositoryId", String.valueOf(repositoryId));
		searchURL.setParameter(
			"searchRepositoryId", String.valueOf(repositoryId));

		long folderId = _getFolderId();

		searchURL.setParameter("folderId", String.valueOf(folderId));
		searchURL.setParameter("searchFolderId", String.valueOf(folderId));
		searchURL.setParameter(
			"showRepositoryTabs", Boolean.toString(folderId == 0));

		searchURL.setParameter("showSearchInfo", Boolean.TRUE.toString());

		return searchURL;
	}

	public String getSortingOrder() {
		return _dlAdminDisplayContext.getOrderByType();
	}

	public PortletURL getSortingURL() {
		int deltaEntry = ParamUtil.getInteger(_request, "deltaEntry");

		PortletURL sortingURL = _liferayPortletResponse.createRenderURL();

		long folderId = _getFolderId();

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			sortingURL.setParameter(
				"mvcRenderCommandName", "/document_library/view");
		}
		else {
			sortingURL.setParameter(
				"mvcRenderCommandName", "/document_library/view_folder");
		}

		sortingURL.setParameter("navigation", _getNavigation());

		if (deltaEntry > 0) {
			sortingURL.setParameter("deltaEntry", String.valueOf(deltaEntry));
		}

		sortingURL.setParameter("folderId", String.valueOf(folderId));

		long fileEntryTypeId = ParamUtil.getLong(
			_request, "fileEntryTypeId", -1);

		sortingURL.setParameter(
			"fileEntryTypeId", String.valueOf(fileEntryTypeId));

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(_getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL;
	}

	public int getTotalItems() {
		SearchContainer searchContainer =
			_dlAdminDisplayContext.getSearchContainer();

		return searchContainer.getTotal();
	}

	public ViewTypeItemList getViewTypes() {
		if (_isSearch()) {
			return null;
		}

		String navigation = ParamUtil.getString(_request, "navigation", "home");

		int curEntry = ParamUtil.getInteger(_request, "curEntry");
		int deltaEntry = ParamUtil.getInteger(_request, "deltaEntry");

		long folderId = _getFolderId();

		long fileEntryTypeId = ParamUtil.getLong(
			_request, "fileEntryTypeId", -1);

		String keywords = ParamUtil.getString(_request, "keywords");

		PortletURL displayStyleURL = _liferayPortletResponse.createRenderURL();

		String mvcRenderCommandName = "/document_library/search";

		if (Validator.isNull(keywords)) {
			if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				mvcRenderCommandName = "/document_library/view";
			}
			else {
				mvcRenderCommandName = "/document_library/view_folder";
			}
		}

		displayStyleURL.setParameter(
			"mvcRenderCommandName", mvcRenderCommandName);

		displayStyleURL.setParameter(
			"navigation", HtmlUtil.escapeJS(navigation));

		if (curEntry > 0) {
			displayStyleURL.setParameter("curEntry", String.valueOf(curEntry));
		}

		if (deltaEntry > 0) {
			displayStyleURL.setParameter(
				"deltaEntry", String.valueOf(deltaEntry));
		}

		displayStyleURL.setParameter("folderId", String.valueOf(folderId));

		if (fileEntryTypeId != -1) {
			displayStyleURL.setParameter(
				"fileEntryTypeId", String.valueOf(fileEntryTypeId));
		}

		return new ViewTypeItemList(displayStyleURL, _getDisplayStyle()) {
			{
				if (ArrayUtil.contains(_getDisplayViews(), "icon")) {
					addCardViewTypeItem();
				}

				if (ArrayUtil.contains(_getDisplayViews(), "descriptive")) {
					addListViewTypeItem();
				}

				if (ArrayUtil.contains(_getDisplayViews(), "list")) {
					addTableViewTypeItem();
				}
			}

		};
	}

	public boolean isDisabled() {
		try {
			if (DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(
					_getRepositoryId(), _getFolderId(),
					WorkflowConstants.STATUS_ANY, true) <= 0) {

				return true;
			}

			return false;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public boolean isSelectable() {
		return _dlPortletInstanceSettingsHelper.isShowActions();
	}

	public boolean isShowSearch() {
		return _dlPortletInstanceSettingsHelper.isShowSearch();
	}

	public boolean isShowSearchInfo() {
		if (_isSearch() && ParamUtil.getBoolean(_request, "showSearchInfo")) {
			return true;
		}

		return false;
	}

	private String _getDisplayStyle() {
		return _dlAdminDisplayContext.getDisplayStyle();
	}

	private String[] _getDisplayViews() {
		DLPortletInstanceSettings dlPortletInstanceSettings =
			_dlRequestHelper.getDLPortletInstanceSettings();

		return dlPortletInstanceSettings.getDisplayViews();
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		long fileEntryTypeId = ParamUtil.getLong(
			_request, "fileEntryTypeId", -1);
		long folderId = _getFolderId();
		final String navigation = ParamUtil.getString(
			_request, "navigation", "home");
		final long rootFolderId = _getRootFolderId();

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							(navigation.equals("home")) &&
							(folderId == rootFolderId) &&
							(fileEntryTypeId == -1));

						PortletURL viewDocumentsHomeURL =
							_liferayPortletResponse.createRenderURL();

						viewDocumentsHomeURL.setParameter(
							"mvcRenderCommandName", "/document_library/view");
						viewDocumentsHomeURL.setParameter(
							"folderId", String.valueOf(rootFolderId));

						dropdownItem.setHref(viewDocumentsHomeURL);

						dropdownItem.setLabel(
							LanguageUtil.get(_request, "all"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(navigation.equals("recent"));

						PortletURL viewRecentDocumentsURL =
							_liferayPortletResponse.createRenderURL();

						viewRecentDocumentsURL.setParameter(
							"mvcRenderCommandName", "/document_library/view");
						viewRecentDocumentsURL.setParameter(
							"navigation", "recent");
						viewRecentDocumentsURL.setParameter(
							"folderId", String.valueOf(rootFolderId));

						dropdownItem.setHref(viewRecentDocumentsURL);

						dropdownItem.setLabel(
							LanguageUtil.get(_request, "recent"));
					});

				if (_themeDisplay.isSignedIn()) {
					add(
						dropdownItem -> {
							dropdownItem.setActive(navigation.equals("mine"));

							PortletURL viewMyDocumentsURL =
								_liferayPortletResponse.createRenderURL();

							viewMyDocumentsURL.setParameter(
								"mvcRenderCommandName",
								"/document_library/view");
							viewMyDocumentsURL.setParameter(
								"navigation", "mine");
							viewMyDocumentsURL.setParameter(
								"folderId", String.valueOf(rootFolderId));

							dropdownItem.setHref(viewMyDocumentsURL);

							dropdownItem.setLabel(
								LanguageUtil.get(_request, "mine"));
						});
				}

				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setActive(fileEntryTypeId != -1);

							dropdownItem.setHref(
								"javascript:" +
									_liferayPortletResponse.getNamespace() +
										"openDocumentTypesSelector();");

							String label = LanguageUtil.get(
								_request, "document-types");

							if (fileEntryTypeId != -1) {
								String fileEntryTypeName = LanguageUtil.get(
									_request, "basic-document");

								if (fileEntryTypeId !=
										DLFileEntryTypeConstants.
											FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

									DLFileEntryType fileEntryType =
										DLFileEntryTypeLocalServiceUtil.
											getFileEntryType(fileEntryTypeId);

									fileEntryTypeName = fileEntryType.getName(
										_request.getLocale());
								}

								label = String.format(
									"%s: %s", label, fileEntryTypeName);
							}

							dropdownItem.setLabel(label);
						}));
			}
		};
	}

	private long _getFolderId() {
		return _dlAdminDisplayContext.getFolderId();
	}

	private String _getNavigation() {
		return ParamUtil.getString(_request, "navigation", "home");
	}

	private String _getOrderByCol() {
		return _dlAdminDisplayContext.getOrderByCol();
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		final Map<String, String> orderColumns = new HashMap<>();

		orderColumns.put("creationDate", "create-date");
		orderColumns.put("downloads", "downloads");
		orderColumns.put("modifiedDate", "modified-date");
		orderColumns.put("size", "size");
		orderColumns.put("title", "title");

		return new DropdownItemList() {
			{
				for (Map.Entry<String, String> orderByColEntry :
						orderColumns.entrySet()) {

					String orderByCol = orderByColEntry.getKey();

					add(
						SafeConsumer.ignore(
							dropdownItem -> {
								dropdownItem.setActive(
									orderByCol.equals(_getOrderByCol()));
								dropdownItem.setHref(
									getSortingURL(), "orderByCol", orderByCol);
								dropdownItem.setLabel(
									LanguageUtil.get(
										_request, orderByColEntry.getValue()));
							}));
				}
			}
		};
	}

	private String _getOrderByType() {
		return _dlAdminDisplayContext.getOrderByType();
	}

	private long _getRepositoryId() {
		return _dlAdminDisplayContext.getRepositoryId();
	}

	private long _getRootFolderId() {
		return _dlAdminDisplayContext.getRootFolderId();
	}

	private boolean _isNavigationRecent() {
		if (Objects.equals(_getNavigation(), "recent")) {
			return true;
		}

		return false;
	}

	private boolean _isSearch() {
		return _dlAdminDisplayContext.isSearch();
	}

	private final DLAdminDisplayContext _dlAdminDisplayContext;
	private final DLPortletInstanceSettingsHelper
		_dlPortletInstanceSettingsHelper;
	private final DLRequestHelper _dlRequestHelper;
	private final DLTrashUtil _dlTrashUtil;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;

}