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

package com.liferay.journal.web.display.context;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverterUtil;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.constants.JournalWebKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.service.JournalFolderServiceUtil;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.util.comparator.FolderArticleDisplayDateComparator;
import com.liferay.journal.util.comparator.FolderArticleModifiedDateComparator;
import com.liferay.journal.web.configuration.JournalWebConfiguration;
import com.liferay.journal.web.portlet.action.ActionUtil;
import com.liferay.journal.web.search.ArticleSearch;
import com.liferay.journal.web.search.EntriesChecker;
import com.liferay.journal.web.search.EntriesMover;
import com.liferay.journal.web.util.JournalPortletUtil;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortalPreferences;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalDisplayContext {

	public JournalDisplayContext(
		HttpServletRequest request, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		PortletPreferences portletPreferences) {

		_request = request;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_portletPreferences = portletPreferences;
		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_request);
	}

	public JournalArticle getArticle() throws PortalException {
		if (_article != null) {
			return _article;
		}

		_article = ActionUtil.getArticle(_request);

		return _article;
	}

	public DDMFormValues getDDMFormValues(DDMStructure ddmStructure)
		throws PortalException {

		if (_ddmFormValues != null) {
			return _ddmFormValues;
		}

		JournalArticle article = getArticle();

		if (article == null) {
			return _ddmFormValues;
		}

		String content = article.getContent();

		if (Validator.isNull(content)) {
			return _ddmFormValues;
		}

		JournalConverter journalConverter = getJournalConverter();

		Fields fields = journalConverter.getDDMFields(ddmStructure, content);

		if (fields == null) {
			return _ddmFormValues;
		}

		_ddmFormValues = FieldsToDDMFormValuesConverterUtil.convert(
			ddmStructure, fields);

		return _ddmFormValues;
	}

	public String getDDMStructureKey() {
		if (_ddmStructureKey != null) {
			return _ddmStructureKey;
		}

		_ddmStructureKey = ParamUtil.getString(_request, "ddmStructureKey");

		return _ddmStructureKey;
	}

	public String getDdmStructureName() throws PortalException {
		if (_ddmStructureName != null) {
			return _ddmStructureName;
		}

		_ddmStructureName = LanguageUtil.get(_request, "basic-web-content");

		if (Validator.isNull(getDDMStructureKey())) {
			return _ddmStructureName;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay) _request.getAttribute(
			WebKeys.THEME_DISPLAY);

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
			themeDisplay.getSiteGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			getDDMStructureKey(), true);

		if (ddmStructure != null) {
			_ddmStructureName = ddmStructure.getName(themeDisplay.getLocale());
		}

		return _ddmStructureName;
	}

	public String getDDMTemplateKey() {
		if (_ddmTemplateKey != null) {
			return _ddmTemplateKey;
		}

		_ddmTemplateKey = ParamUtil.getString(_request, "ddmTemplateKey");

		return _ddmTemplateKey;
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = getDisplayStyle(_request, getDisplayViews());
		}

		return _displayStyle;
	}

	public String[] getDisplayViews() {
		if (_displayViews == null) {
			JournalWebConfiguration journalWebConfiguration =
				(JournalWebConfiguration)_request.getAttribute(
					JournalWebConfiguration.class.getName());

			_displayViews = StringUtil.split(
				PrefsParamUtil.getString(
					_portletPreferences, _request, "displayViews",
					StringUtil.merge(journalWebConfiguration.displayViews())));
		}

		return _displayViews;
	}

	public JournalFolder getFolder() throws PortalException {
		if (_folder != null) {
			return _folder;
		}

		_folder = (JournalFolder)_request.getAttribute(WebKeys.JOURNAL_FOLDER);

		if (_folder != null) {
			return _folder;
		}

		_folder = ActionUtil.getFolder(_request);

		return _folder;
	}

	public long getFolderId() throws PortalException {
		if (_folderId != null) {
			return _folderId;
		}

		JournalFolder folder = getFolder();

		_folderId = BeanParamUtil.getLong(
			folder, _request, "folderId",
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return _folderId;
	}

	public JournalConverter getJournalConverter() {
		return (JournalConverter)_request.getAttribute(
			JournalWebKeys.JOURNAL_CONVERTER);
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_request, "keywords");

		return _keywords;
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(_request, "navigation", "home");

		return _navigation;
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_request, "orderByCol");

		if (Validator.isNull(_orderByCol)) {
			_orderByCol = _portalPreferences.getValue(
				JournalPortletKeys.JOURNAL, "order-by-col", "modified-date");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(_request, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
					JournalPortletKeys.JOURNAL, "order-by-col", _orderByCol);
			}
		}

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType");

		if (Validator.isNull(_orderByCol)) {
			_orderByType = _portalPreferences.getValue(
				JournalPortletKeys.JOURNAL, "order-by-type", "asc");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(_request, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
					JournalPortletKeys.JOURNAL, "order-by-type", _orderByType);
			}
		}

		return _orderByType;
	}

	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		String navigation = ParamUtil.getString(_request, "navigation");

		if (Validator.isNotNull(navigation)) {
			portletURL.setParameter(
				"navigation", HtmlUtil.escapeJS(getNavigation()));
		}

		portletURL.setParameter("folderId", String.valueOf(getFolderId()));

		String ddmStructureKey = getDDMStructureKey();

		if (!ddmStructureKey.equals("0")) {
			portletURL.setParameter("ddmStructureKey", ddmStructureKey);
		}

		String deltaEntry = ParamUtil.getString(_request, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String displayStyle = ParamUtil.getString(_request, "displayStyle");

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", getDisplayStyle());
		}

		if (!isShowEditActions()) {
			portletURL.setParameter(
				"showEditActions", String.valueOf(isShowEditActions()));
		}

		return portletURL;
	}

	public ArticleSearch getSearchContainer() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		ArticleSearch articleSearchContainer = new ArticleSearch(
			_liferayPortletRequest, getPortletURL());

		OrderByComparator<JournalArticle> orderByComparator =
			JournalPortletUtil.getArticleOrderByComparator(
				getOrderByCol(), getOrderByType());

		articleSearchContainer.setOrderByCol(getOrderByCol());
		articleSearchContainer.setOrderByComparator(orderByComparator);
		articleSearchContainer.setOrderByType(getOrderByType());

		EntriesChecker entriesChecker = new EntriesChecker(
			_liferayPortletRequest, _liferayPortletResponse);

		entriesChecker.setCssClass("entry-selector");

		articleSearchContainer.setRowChecker(entriesChecker);

		EntriesMover entriesMover = new EntriesMover(
			themeDisplay.getScopeGroupId());

		articleSearchContainer.setRowMover(entriesMover);

		if (isNavigationMine() || isNavigationRecent()) {
			boolean includeOwner = true;

			if (isNavigationMine()) {
				includeOwner = false;
			}

			if (isNavigationRecent()) {
				articleSearchContainer.setOrderByCol("create-date");
				articleSearchContainer.setOrderByType(getOrderByType());
			}

			int total = JournalArticleServiceUtil.getGroupArticlesCount(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				getFolderId(), getStatus(), includeOwner);

			articleSearchContainer.setTotal(total);

			List results = JournalArticleServiceUtil.getGroupArticles(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				getFolderId(), getStatus(), includeOwner,
				articleSearchContainer.getStart(),
				articleSearchContainer.getEnd(),
				articleSearchContainer.getOrderByComparator());

			articleSearchContainer.setResults(results);
		}
		else if (Validator.isNotNull(getDDMStructureKey())) {
			int total = JournalArticleServiceUtil.getArticlesCountByStructureId(
				themeDisplay.getScopeGroupId(), getDDMStructureKey());

			articleSearchContainer.setTotal(total);

			List results = JournalArticleServiceUtil.getArticlesByStructureId(
				themeDisplay.getScopeGroupId(), getDDMStructureKey(),
				articleSearchContainer.getStart(),
				articleSearchContainer.getEnd(),
				articleSearchContainer.getOrderByComparator());

			articleSearchContainer.setResults(results);
		}
		else if (Validator.isNotNull(getDDMTemplateKey())) {
			List<Long> folderIds = new ArrayList<>(1);

			if (getFolderId() !=
					JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				folderIds.add(getFolderId());
			}

			int total = JournalArticleServiceUtil.searchCount(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
				folderIds, JournalArticleConstants.CLASSNAME_ID_DEFAULT,
				getKeywords(), -1.0, getDDMStructureKey(), getDDMTemplateKey(),
				null, null, getStatus(), null);

			articleSearchContainer.setTotal(total);

			List results = JournalArticleServiceUtil.search(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
				folderIds, JournalArticleConstants.CLASSNAME_ID_DEFAULT,
				getKeywords(), -1.0, getDDMStructureKey(), getDDMTemplateKey(),
				null, null, getStatus(), null,
				articleSearchContainer.getStart(),
				articleSearchContainer.getEnd(),
				articleSearchContainer.getOrderByComparator());

			articleSearchContainer.setResults(results);
		}
		else {
			int total = JournalFolderServiceUtil.getFoldersAndArticlesCount(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				getFolderId(), getStatus());

			articleSearchContainer.setTotal(total);

			OrderByComparator<Object> folderOrderByComparator = null;

			boolean orderByAsc = false;

			if (getOrderByType().equals("asc")) {
				orderByAsc = true;
			}

			if (getOrderByCol().equals("display-date")) {
				folderOrderByComparator =
					new FolderArticleDisplayDateComparator(orderByAsc);
			}
			else if (getOrderByCol().equals("modified-date")) {
				folderOrderByComparator =
					new FolderArticleModifiedDateComparator(orderByAsc);
			}

			List results = JournalFolderServiceUtil.getFoldersAndArticles(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				getFolderId(), getStatus(), articleSearchContainer.getStart(),
				articleSearchContainer.getEnd(), folderOrderByComparator);

			articleSearchContainer.setResults(results);
		}

		return articleSearchContainer;
	}

	public int getStatus() {
		if (_status != null) {
			return _status;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		int defaultStatus = WorkflowConstants.STATUS_APPROVED;

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (permissionChecker.isContentReviewer(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId()) ||
			isNavigationMine()) {

			defaultStatus = WorkflowConstants.STATUS_ANY;
		}

		_status = ParamUtil.getInteger(_request, "status", defaultStatus);

		return _status;
	}

	public boolean isNavigationHome() {
		if (getNavigation().equals("home")) {
			return true;
		}

		return false;
	}

	public boolean isNavigationMine() {
		if (getNavigation().equals("mine")) {
			return true;
		}

		return false;
	}

	public boolean isNavigationRecent() {
		if (getNavigation().equals("recent")) {
			return true;
		}

		return false;
	}

	public boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	public boolean isShowBreadcrumb() {
		String browseBy = ParamUtil.getString(_request, "browseBy");

		if (Validator.isNotNull(browseBy)) {
			return false;
		}

		if (isNavigationRecent()) {
			return false;
		}

		if (isNavigationMine()) {
			return false;
		}

		if (isSearch()) {
			return false;
		}

		return true;
	}

	public boolean isShowEditActions() {
		if (_showEditActions != null) {
			return _showEditActions;
		}

		_showEditActions = ParamUtil.getBoolean(
			_request, "showEditActions", true);

		return _showEditActions;
	}

	public boolean isShowInfoPanel() {
		if (Validator.isNotNull(getDDMStructureKey())) {
			return false;
		}

		if (isNavigationMine()) {
			return false;
		}

		if (isNavigationRecent()) {
			return false;
		}

		if (isSearch()) {
			return false;
		}

		return true;
	}

	protected String getDisplayStyle(
		HttpServletRequest request, String[] displayViews) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(request);

		String displayStyle = ParamUtil.getString(request, "displayStyle");

		if (Validator.isNull(displayStyle)) {
			JournalWebConfiguration journalWebConfiguration =
				(JournalWebConfiguration)_request.getAttribute(
					JournalWebConfiguration.class.getName());

			displayStyle = portalPreferences.getValue(
				JournalPortletKeys.JOURNAL, "display-style",
				journalWebConfiguration.defaultDisplayView());
		}
		else {
			if (ArrayUtil.contains(displayViews, displayStyle)) {
				portalPreferences.setValue(
					JournalPortletKeys.JOURNAL, "display-style", displayStyle);

				request.setAttribute(
					WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
			}
		}

		if (!ArrayUtil.contains(displayViews, displayStyle)) {
			displayStyle = displayViews[0];
		}

		return displayStyle;
	}

	private JournalArticle _article;
	private DDMFormValues _ddmFormValues;
	private String _ddmStructureKey;
	private String _ddmStructureName;
	private String _ddmTemplateKey;
	private String _displayStyle;
	private String[] _displayViews;
	private JournalFolder _folder;
	private Long _folderId;
	private String _keywords;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private final PortalPreferences _portalPreferences;
	private final PortletPreferences _portletPreferences;
	private final HttpServletRequest _request;
	private Boolean _showEditActions;
	private Integer _status;

}