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

package com.liferay.journal.web.internal.display.context;

import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.service.JournalFolderServiceUtil;
import com.liferay.journal.util.comparator.FolderArticleModifiedDateComparator;
import com.liferay.journal.util.comparator.FolderArticleTitleComparator;
import com.liferay.journal.web.internal.item.selector.JournalArticleItemSelectorView;
import com.liferay.journal.web.internal.util.JournalPortletUtil;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalArticleItemSelectorViewDisplayContext {

	public JournalArticleItemSelectorViewDisplayContext(
		HttpServletRequest httpServletRequest,
		InfoItemItemSelectorCriterion infoItemItemSelectorCriterion,
		String itemSelectedEventName,
		JournalArticleItemSelectorView journalArticleItemSelectorView,
		PortletURL portletURL, boolean search) {

		_httpServletRequest = httpServletRequest;
		_infoItemItemSelectorCriterion = infoItemItemSelectorCriterion;
		_itemSelectedEventName = itemSelectedEventName;
		_journalArticleItemSelectorView = journalArticleItemSelectorView;
		_portletURL = portletURL;
		_search = search;

		_portletRequest = (PortletRequest)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
		_portletResponse = (RenderResponse)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getDDMStructureKey() {
		if (_ddmStructureKey != null) {
			return _ddmStructureKey;
		}

		String ddmStructureKey = ParamUtil.getString(
			_httpServletRequest, "ddmStructureKey");

		if (Validator.isNull(ddmStructureKey)) {
			ddmStructureKey = _infoItemItemSelectorCriterion.getItemSubtype();
		}

		_ddmStructureKey = ddmStructureKey;

		return _ddmStructureKey;
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle", "descriptive");

		return _displayStyle;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public JournalArticle getLatestArticle(JournalArticle journalArticle) {
		JournalArticle latestArticle =
			JournalArticleLocalServiceUtil.fetchLatestArticle(
				journalArticle.getGroupId(), journalArticle.getArticleId(),
				WorkflowConstants.STATUS_ANY);

		if (latestArticle != null) {
			return latestArticle;
		}

		return journalArticle;
	}

	public List<BreadcrumbEntry> getPortletBreadcrumbEntries()
		throws Exception {

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(LanguageUtil.get(_httpServletRequest, "home"));

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter(
			"folderId",
			String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID));

		breadcrumbEntry.setURL(portletURL.toString());

		breadcrumbEntries.add(breadcrumbEntry);

		JournalFolder folder = _getFolder();

		if (folder == null) {
			return breadcrumbEntries;
		}

		List<JournalFolder> ancestorFolders = folder.getAncestors();

		Collections.reverse(ancestorFolders);

		for (JournalFolder ancestorFolder : ancestorFolders) {
			BreadcrumbEntry folderBreadcrumbEntry = new BreadcrumbEntry();

			folderBreadcrumbEntry.setTitle(ancestorFolder.getName());

			portletURL.setParameter(
				"folderId", String.valueOf(ancestorFolder.getFolderId()));

			folderBreadcrumbEntry.setURL(portletURL.toString());

			breadcrumbEntries.add(folderBreadcrumbEntry);
		}

		if (folder.getFolderId() !=
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			BreadcrumbEntry folderBreadcrumbEntry = new BreadcrumbEntry();

			JournalFolder unescapedFolder = folder.toUnescapedModel();

			folderBreadcrumbEntry.setTitle(unescapedFolder.getName());

			portletURL.setParameter(
				"folderId", String.valueOf(folder.getFolderId()));

			folderBreadcrumbEntry.setURL(portletURL.toString());

			breadcrumbEntries.add(folderBreadcrumbEntry);
		}

		return breadcrumbEntries;
	}

	public PortletURL getPortletURL() throws PortletException {
		PortletURL portletURL = PortletURLUtil.clone(
			_portletURL,
			PortalUtil.getLiferayPortletResponse(_portletResponse));

		portletURL.setParameter("displayStyle", getDisplayStyle());
		portletURL.setParameter(
			"selectedTab",
			String.valueOf(_getTitle(_httpServletRequest.getLocale())));

		return portletURL;
	}

	public SearchContainer getSearchContainer() throws PortletException {
		if (_articleSearchContainer != null) {
			return _articleSearchContainer;
		}

		SearchContainer articleSearchContainer = new SearchContainer(
			_portletRequest, getPortletURL(), null, null);

		OrderByComparator<JournalArticle> orderByComparator =
			JournalPortletUtil.getArticleOrderByComparator(
				_getOrderByCol(), _getOrderByType());

		articleSearchContainer.setOrderByCol(_getOrderByCol());
		articleSearchContainer.setOrderByComparator(orderByComparator);
		articleSearchContainer.setOrderByType(_getOrderByType());

		if (Validator.isNotNull(getDDMStructureKey())) {
			OrderByComparator<JournalArticle> structuresOrderByComparator =
				JournalPortletUtil.getArticleOrderByComparator(
					_getOrderByCol(), _getOrderByType());

			int total = JournalArticleServiceUtil.getArticlesCountByStructureId(
				_themeDisplay.getScopeGroupId(), getDDMStructureKey(),
				WorkflowConstants.STATUS_APPROVED);

			articleSearchContainer.setTotal(total);

			List results = JournalArticleServiceUtil.getArticlesByStructureId(
				_themeDisplay.getScopeGroupId(), getDDMStructureKey(),
				WorkflowConstants.STATUS_APPROVED,
				articleSearchContainer.getStart(),
				articleSearchContainer.getEnd(), structuresOrderByComparator);

			articleSearchContainer.setResults(results);
		}
		else {
			int total = JournalFolderServiceUtil.getFoldersAndArticlesCount(
				_themeDisplay.getScopeGroupId(), 0, _getFolderId(),
				_infoItemItemSelectorCriterion.getStatus());

			articleSearchContainer.setTotal(total);

			OrderByComparator<Object> folderOrderByComparator = null;

			boolean orderByAsc = false;

			if (Objects.equals(_getOrderByType(), "asc")) {
				orderByAsc = true;
			}

			if (Objects.equals(_getOrderByCol(), "modified-date")) {
				folderOrderByComparator =
					new FolderArticleModifiedDateComparator(orderByAsc);
			}
			else if (Objects.equals(_getOrderByCol(), "title")) {
				folderOrderByComparator = new FolderArticleTitleComparator(
					orderByAsc);
			}

			List results = JournalFolderServiceUtil.getFoldersAndArticles(
				_themeDisplay.getScopeGroupId(), 0, _getFolderId(),
				_infoItemItemSelectorCriterion.getStatus(),
				_themeDisplay.getLocale(), articleSearchContainer.getStart(),
				articleSearchContainer.getEnd(), folderOrderByComparator);

			articleSearchContainer.setResults(results);
		}

		_articleSearchContainer = articleSearchContainer;

		return _articleSearchContainer;
	}

	private JournalFolder _getFolder() {
		if (_folder != null) {
			return _folder;
		}

		long folderId = ParamUtil.getLong(_httpServletRequest, "folderId");

		_folder = JournalFolderLocalServiceUtil.fetchFolder(folderId);

		return _folder;
	}

	private long _getFolderId() {
		if (_folderId != null) {
			return _folderId;
		}

		_folderId = BeanParamUtil.getLong(
			_getFolder(), _httpServletRequest, "folderId",
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return _folderId;
	}

	private String _getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		String defaultOrderByCol = "modified-date";

		if (_isSearch()) {
			defaultOrderByCol = "relevance";
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", defaultOrderByCol);

		return _orderByCol;
	}

	private String _getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		if (Objects.equals(_getOrderByCol(), "relevance")) {
			return "desc";
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		return _orderByType;
	}

	private String _getTitle(Locale locale) {
		return _journalArticleItemSelectorView.getTitle(locale);
	}

	private boolean _isSearch() {
		return _search;
	}

	private SearchContainer _articleSearchContainer;
	private String _ddmStructureKey;
	private String _displayStyle;
	private JournalFolder _folder;
	private Long _folderId;
	private final HttpServletRequest _httpServletRequest;
	private final InfoItemItemSelectorCriterion _infoItemItemSelectorCriterion;
	private final String _itemSelectedEventName;
	private final JournalArticleItemSelectorView
		_journalArticleItemSelectorView;
	private String _orderByCol;
	private String _orderByType;
	private final PortletRequest _portletRequest;
	private final PortletResponse _portletResponse;
	private final PortletURL _portletURL;
	private final boolean _search;
	private final ThemeDisplay _themeDisplay;

}