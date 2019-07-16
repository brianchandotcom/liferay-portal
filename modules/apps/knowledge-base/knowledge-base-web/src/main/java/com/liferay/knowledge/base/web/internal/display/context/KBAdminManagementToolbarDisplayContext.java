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

package com.liferay.knowledge.base.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBArticleSearchDisplay;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.service.KBArticleServiceUtil;
import com.liferay.knowledge.base.service.KBFolderServiceUtil;
import com.liferay.knowledge.base.service.KBTemplateServiceUtil;
import com.liferay.knowledge.base.web.internal.search.EntriesChecker;
import com.liferay.knowledge.base.web.internal.search.KBObjectsSearch;
import com.liferay.knowledge.base.web.internal.security.permission.resource.AdminPermission;
import com.liferay.knowledge.base.web.internal.security.permission.resource.KBArticlePermission;
import com.liferay.knowledge.base.web.internal.security.permission.resource.KBFolderPermission;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class KBAdminManagementToolbarDisplayContext {

	public KBAdminManagementToolbarDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			HttpServletRequest httpServletRequest, RenderRequest renderRequest,
			RenderResponse renderResponse, PortletConfig portletConfig)
		throws PortalException, PortletException {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_portletConfig = portletConfig;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_createSearchContainer();
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteEntries");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public List<String> getAvailableActions(KBArticle kbArticle)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		if (KBArticlePermission.contains(
				_themeDisplay.getPermissionChecker(), kbArticle,
				ActionKeys.DELETE)) {

			availableActions.add("deleteEntries");
		}

		return availableActions;
	}

	public List<String> getAvailableActions(KBFolder kbFolder)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		if (KBFolderPermission.contains(
				_themeDisplay.getPermissionChecker(), kbFolder,
				ActionKeys.DELETE)) {

			availableActions.add("deleteEntries");
		}

		return availableActions;
	}

	public CreationMenu getCreationMenu() throws PortalException {
		CreationMenu creationMenu = null;

		long kbFolderClassNameId = PortalUtil.getClassNameId(
			KBFolderConstants.getClassName());

		long parentResourceClassNameId = ParamUtil.getLong(
			_httpServletRequest, "parentResourceClassNameId",
			kbFolderClassNameId);

		long parentResourcePrimKey = ParamUtil.getLong(
			_httpServletRequest, "parentResourcePrimKey",
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		boolean hasAddKBArticlePermission = false;
		boolean hasAddKBFolderPermission = false;

		PermissionChecker permissionChecker =
			_themeDisplay.getPermissionChecker();

		if (parentResourceClassNameId == kbFolderClassNameId) {
			hasAddKBArticlePermission = KBFolderPermission.contains(
				permissionChecker, _themeDisplay.getScopeGroupId(),
				parentResourcePrimKey, KBActionKeys.ADD_KB_ARTICLE);
			hasAddKBFolderPermission = KBFolderPermission.contains(
				permissionChecker, _themeDisplay.getScopeGroupId(),
				parentResourcePrimKey, KBActionKeys.ADD_KB_FOLDER);
		}
		else {
			hasAddKBArticlePermission = AdminPermission.contains(
				permissionChecker, _themeDisplay.getScopeGroupId(),
				KBActionKeys.ADD_KB_ARTICLE);
		}

		if (hasAddKBFolderPermission) {
			if (creationMenu == null) {
				creationMenu = new CreationMenu();
			}

			creationMenu.addDropdownItem(
				dropdownItem -> {
					PortletURL addFolderURL =
						_liferayPortletResponse.createRenderURL();

					addFolderURL.setParameter(
						"mvcPath", "/admin/common/edit_folder.jsp");
					addFolderURL.setParameter(
						"redirect",
						PortalUtil.getCurrentURL(_httpServletRequest));
					addFolderURL.setParameter(
						"parentResourceClassNameId",
						String.valueOf(
							PortalUtil.getClassNameId(
								KBFolderConstants.getClassName())));
					addFolderURL.setParameter(
						"parentResourcePrimKey",
						String.valueOf(parentResourcePrimKey));

					dropdownItem.setHref(addFolderURL);

					dropdownItem.setLabel(
						LanguageUtil.get(_httpServletRequest, "folder"));
				});
		}

		if (hasAddKBArticlePermission) {
			if (creationMenu == null) {
				creationMenu = new CreationMenu();
			}

			String templatePath = _getTemplatePath();

			creationMenu.addDropdownItem(
				dropdownItem -> {
					PortletURL addBasicKBArticleURL =
						_liferayPortletResponse.createRenderURL();

					addBasicKBArticleURL.setParameter(
						"mvcPath", templatePath + "edit_article.jsp");

					addBasicKBArticleURL.setParameter(
						"redirect",
						PortalUtil.getCurrentURL(_httpServletRequest));
					addBasicKBArticleURL.setParameter(
						"parentResourceClassNameId",
						String.valueOf(parentResourceClassNameId));
					addBasicKBArticleURL.setParameter(
						"parentResourcePrimKey",
						String.valueOf(parentResourcePrimKey));

					dropdownItem.setHref(addBasicKBArticleURL);

					dropdownItem.setLabel(
						LanguageUtil.get(_httpServletRequest, "basic-article"));
				});

			OrderByComparator<KBTemplate> obc =
				OrderByComparatorFactoryUtil.create(
					"KBTemplate", "title", false);

			List<KBTemplate> kbTemplates =
				KBTemplateServiceUtil.getGroupKBTemplates(
					_themeDisplay.getScopeGroupId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, obc);

			if (!kbTemplates.isEmpty()) {
				for (KBTemplate kbTemplate : kbTemplates) {
					creationMenu.addDropdownItem(
						dropdownItem -> {
							PortletURL addKBArticleURL =
								_liferayPortletResponse.createRenderURL();

							addKBArticleURL.setParameter(
								"mvcPath", templatePath + "edit_article.jsp");
							addKBArticleURL.setParameter(
								"redirect",
								PortalUtil.getCurrentURL(_httpServletRequest));
							addKBArticleURL.setParameter(
								"parentResourceClassNameId",
								String.valueOf(parentResourceClassNameId));
							addKBArticleURL.setParameter(
								"parentResourcePrimKey",
								String.valueOf(parentResourcePrimKey));
							addKBArticleURL.setParameter(
								"kbTemplateId",
								String.valueOf(kbTemplate.getKbTemplateId()));

							dropdownItem.setHref(addKBArticleURL);

							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest,
									kbTemplate.getTitle()));
						});
				}
			}
		}

		if ((parentResourceClassNameId == kbFolderClassNameId) &&
			AdminPermission.contains(
				permissionChecker, _themeDisplay.getScopeGroupId(),
				KBActionKeys.ADD_KB_ARTICLE)) {

			if (creationMenu == null) {
				creationMenu = new CreationMenu();
			}

			creationMenu.addDropdownItem(
				dropdownItem -> {
					PortletURL importURL =
						_liferayPortletResponse.createRenderURL();

					importURL.setParameter("mvcPath", "/admin/import.jsp");
					importURL.setParameter(
						"redirect",
						PortalUtil.getCurrentURL(_httpServletRequest));
					importURL.setParameter(
						"parentKBFolderId",
						String.valueOf(parentResourcePrimKey));

					dropdownItem.setHref(importURL);

					dropdownItem.setLabel(
						LanguageUtil.get(_httpServletRequest, "import"));
				});
		}

		return creationMenu;
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "order-by"));
					});
			}
		};
	}

	public String getOrderByType() {
		return _searchContainer.getOrderByType();
	}

	public SearchContainer getSearchContainer() {
		return _searchContainer;
	}

	public PortletURL getSearchURL() {
		PortletURL searchURL = _liferayPortletResponse.createRenderURL();

		searchURL.setParameter("mvcPath", "/admin/search.jsp");
		searchURL.setParameter("redirect", _getRedirect());

		return searchURL;
	}

	public PortletURL getSortingURL() throws PortletException {
		PortletURL sortingURL = _getCurrentSortingURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL;
	}

	public int getTotal() {
		return _searchContainer.getTotal();
	}

	public boolean isDisabled() {
		return !_searchContainer.hasResults();
	}

	public boolean isShowInfoButton() {
		String keywords = _getKeywords();

		return Validator.isNull(keywords);
	}

	private SearchContainer _createSearchContainer()
		throws PortalException, PortletException {

		long kbFolderClassNameId = PortalUtil.getClassNameId(
			KBFolderConstants.getClassName());

		long parentResourceClassNameId = ParamUtil.getLong(
			_httpServletRequest, "parentResourceClassNameId",
			kbFolderClassNameId);

		long parentResourcePrimKey = ParamUtil.getLong(
			_httpServletRequest, "parentResourcePrimKey",
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_searchContainer = new KBObjectsSearch(
			_renderRequest,
			PortletURLUtil.clone(
				PortletURLUtil.getCurrent(
					_liferayPortletRequest, _liferayPortletResponse),
				_renderResponse));

		boolean kbFolderView = false;

		if (parentResourceClassNameId == kbFolderClassNameId) {
			kbFolderView = true;
		}

		String keywords = _getKeywords();

		if (Validator.isNotNull(keywords)) {
			KBArticleSearchDisplay kbArticleSearchDisplay =
				KBArticleServiceUtil.getKBArticleSearchDisplay(
					_themeDisplay.getScopeGroupId(), keywords, keywords,
					WorkflowConstants.STATUS_ANY, null, null, false, new int[0],
					_searchContainer.getCur(), _searchContainer.getDelta(),
					_searchContainer.getOrderByComparator());

			_searchContainer.setResults(kbArticleSearchDisplay.getResults());
			_searchContainer.setTotal(kbArticleSearchDisplay.getTotal());
		}
		else if (kbFolderView) {
			_searchContainer.setTotal(
				KBFolderServiceUtil.getKBFoldersAndKBArticlesCount(
					_themeDisplay.getScopeGroupId(), parentResourcePrimKey,
					WorkflowConstants.STATUS_ANY));
			_searchContainer.setResults(
				KBFolderServiceUtil.getKBFoldersAndKBArticles(
					_themeDisplay.getScopeGroupId(), parentResourcePrimKey,
					WorkflowConstants.STATUS_ANY, _searchContainer.getStart(),
					_searchContainer.getEnd(),
					_searchContainer.getOrderByComparator()));
		}
		else {
			_searchContainer.setTotal(
				KBArticleServiceUtil.getKBArticlesCount(
					_themeDisplay.getScopeGroupId(), parentResourcePrimKey,
					WorkflowConstants.STATUS_ANY));
			_searchContainer.setResults(
				KBArticleServiceUtil.getKBArticles(
					_themeDisplay.getScopeGroupId(), parentResourcePrimKey,
					WorkflowConstants.STATUS_ANY, _searchContainer.getStart(),
					_searchContainer.getEnd(),
					_searchContainer.getOrderByComparator()));
		}

		_searchContainer.setRowChecker(
			new EntriesChecker(
				_liferayPortletRequest, _liferayPortletResponse));

		return _searchContainer;
	}

	private PortletURL _getCurrentSortingURL() throws PortletException {
		PortletURL sortingURL = PortletURLUtil.clone(
			PortletURLUtil.getCurrent(
				_liferayPortletRequest, _liferayPortletResponse),
			_liferayPortletResponse);

		String keywords = _getKeywords();

		if (Validator.isNotNull(keywords)) {
			sortingURL.setParameter("keywords", keywords);
		}

		return sortingURL;
	}

	private String _getKeywords() {
		return ParamUtil.getString(_httpServletRequest, "keywords");
	}

	private String _getOrderByCol() {
		return _searchContainer.getOrderByCol();
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				final Map<String, String> orderColumnsMap = new HashMap<>();

				orderColumnsMap.put("modifiedDate", "modified-date");
				orderColumnsMap.put("priority", "priority");
				orderColumnsMap.put("title", "title");
				orderColumnsMap.put("viewCount", "view-count");

				String[] orderColumns = {
					"priority", "modifiedDate", "title", "viewCount"
				};

				for (String orderByCol : orderColumns) {
					add(
						dropdownItem -> {
							dropdownItem.setActive(
								orderByCol.equals(_getOrderByCol()));
							dropdownItem.setHref(
								_getCurrentSortingURL(), "orderByCol",
								orderByCol);
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest,
									orderColumnsMap.get(orderByCol)));
						});
				}
			}
		};
	}

	private String _getRedirect() {
		return PortalUtil.escapeRedirect(
			ParamUtil.getString(
				_httpServletRequest, "redirect",
				PortalUtil.getCurrentURL(_httpServletRequest)));
	}

	private String _getTemplatePath() {
		return _portletConfig.getInitParameter("template-path");
	}

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final PortletConfig _portletConfig;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer _searchContainer;
	private final ThemeDisplay _themeDisplay;

}