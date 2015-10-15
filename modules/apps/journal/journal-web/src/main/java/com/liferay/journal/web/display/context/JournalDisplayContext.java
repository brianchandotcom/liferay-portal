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
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverterUtil;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.constants.JournalWebKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.web.configuration.JournalWebConfiguration;
import com.liferay.journal.web.portlet.action.ActionUtil;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortalPreferences;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalDisplayContext {

	public JournalDisplayContext(
		HttpServletRequest request,
		LiferayPortletResponse liferayPortletResponse,
		PortletPreferences portletPreferences) {

		_request = request;
		_liferayPortletResponse = liferayPortletResponse;
		_portletPreferences = portletPreferences;
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

	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		String navigation = ParamUtil.getString(_request, "navigation");

		if (Validator.isNotNull(navigation)) {
			portletURL.setParameter(
				"navigation", HtmlUtil.escapeJS(getNavigation()));
		}

		portletURL.setParameter("folderId", String.valueOf(getFolderId()));

		String ddmStructureKey = ParamUtil.getString(
			_request, "ddmStructureKey");

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

	public boolean isShowEditActions() {
		if (_showEditActions != null) {
			return _showEditActions;
		}

		_showEditActions = ParamUtil.getBoolean(
			_request, "showEditActions", true);

		return _showEditActions;
	}

	public boolean isShowInfoPanel() {
		String ddmStructureKey = ParamUtil.getString(
			_request, "ddmStructureKey");

		if (Validator.isNotNull(ddmStructureKey)) {
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
	private String _displayStyle;
	private String[] _displayViews;
	private JournalFolder _folder;
	private Long _folderId;
	private String _keywords;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _navigation;
	private final PortletPreferences _portletPreferences;
	private final HttpServletRequest _request;
	private Boolean _showEditActions;
	private Integer _status;

}