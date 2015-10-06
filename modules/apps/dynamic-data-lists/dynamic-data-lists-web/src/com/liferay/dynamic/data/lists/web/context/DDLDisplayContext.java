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

package com.liferay.dynamic.data.lists.web.context;

import com.liferay.dynamic.data.lists.constants.DDLActionKeys;
import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.dynamic.data.lists.constants.DDLWebKeys;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.dynamic.data.lists.service.permission.DDLPermission;
import com.liferay.dynamic.data.lists.service.permission.DDLRecordSetPermission;
import com.liferay.dynamic.data.lists.web.configuration.DDLWebConfigurationValues;
import com.liferay.dynamic.data.lists.web.portlet.DDLPortletUtil;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.permission.DDMTemplatePermission;
import com.liferay.dynamic.data.mapping.util.DDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMDisplayRegistryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.util.Locale;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Marcellus Tavares
 */
public class DDLDisplayContext {

	public DDLDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_portletPreferences = renderRequest.getPreferences();

		if (Validator.isNotNull(getPortletResource())) {
			return;
		}

		DDLRecordSet recordSet = getRecordSet();

		if ((recordSet == null) || !hasViewPermission()) {
			_renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}
	}

	public String getAddDDMTemplateTitle() throws PortalException {
		DDMDisplay ddmDisplay = getDDMDisplay();

		return ddmDisplay.getEditTemplateTitle(
			_recordSet.getDDMStructure(), null, getLocale());
	}

	public String getDDLRecordSetDisplayStyle() {
		if (_ddlRecordDisplayStyle == null) {
			_ddlRecordDisplayStyle = DDLPortletUtil.getDDLRecordSetDisplayStyle(
				_renderRequest, getDDLRecordSetDisplayViews());
		}

		return _ddlRecordDisplayStyle;
	}

	public String[] getDDLRecordSetDisplayViews() {
		if (_ddlRecordDisplayViews == null) {
			_ddlRecordDisplayViews = StringUtil.split(
				PrefsParamUtil.getString(
					_portletPreferences, _renderRequest, "displayViews",
					StringUtil.merge(DDLWebConfigurationValues.DISPLAY_VIEWS)));
		}

		return _ddlRecordDisplayViews;
	}

	public long getDisplayDDMTemplateId() {
		return PrefsParamUtil.getLong(
			_portletPreferences, _renderRequest, "displayDDMTemplateId");
	}

	public String getEditDisplayDDMTemplateTitle() throws PortalException {
		DDLRecordSet recordSet = getRecordSet();

		if (recordSet == null) {
			return StringPool.BLANK;
		}

		DDMDisplay ddmDisplay = getDDMDisplay();

		return ddmDisplay.getEditTemplateTitle(
			recordSet.getDDMStructure(), fetchDisplayDDMTemplate(),
			getLocale());
	}

	public String getEditFormDDMTemplateTitle() throws PortalException {
		DDLRecordSet recordSet = getRecordSet();

		if (recordSet == null) {
			return LanguageUtil.get(_renderRequest.getLocale(), "add-list");
		}

		DDMDisplay ddmDisplay = getDDMDisplay();

		return ddmDisplay.getEditTemplateTitle(
			recordSet.getDDMStructure(), fetchFormDDMTemplate(), getLocale());
	}

	public long getFormDDMTemplateId() {
		return PrefsParamUtil.getLong(
			_portletPreferences, _renderRequest, "formDDMTemplateId");
	}

	public DDLRecordSet getRecordSet() {
		if (_recordSet != null) {
			return _recordSet;
		}

		_recordSet = (DDLRecordSet)_renderRequest.getAttribute(
			DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD_SET);

		if (_recordSet != null) {
			return _recordSet;
		}

		_recordSet = DDLRecordSetLocalServiceUtil.fetchDDLRecordSet(
			getRecordSetId());

		return _recordSet;
	}

	public long getRecordSetId() {
		return PrefsParamUtil.getLong(
			_portletPreferences, _renderRequest, "recordSetId");
	}

	public boolean isEditable() {
		return PrefsParamUtil.getBoolean(
			_portletPreferences, _renderRequest, "editable", true);
	}

	public boolean isShowAddDDMTemplateIcon() throws PortalException {
		if (_hasAddDDMTemplatePermission != null) {
			return _hasAddDDMTemplatePermission;
		}

		_hasAddDDMTemplatePermission = Boolean.FALSE;

		DDLRecordSet recordSet = getRecordSet();

		if (recordSet == null) {
			return _hasAddDDMTemplatePermission;
		}

		_hasAddDDMTemplatePermission =
			DDMTemplatePermission.containsAddTemplatePermission(
				getPermissionChecker(), getScopeGroupId(),
				getStructureTypeClassNameId(), getStructureTypeClassNameId());

		return _hasAddDDMTemplatePermission;
	}

	public boolean isShowAddRecordSetIcon() {
		if (_hasAddRecordSetPermission != null) {
			return _hasAddRecordSetPermission;
		}

		_hasAddRecordSetPermission = DDLPermission.contains(
			getPermissionChecker(), getScopeGroupId(), getPortletId(),
			DDLActionKeys.ADD_RECORD_SET);

		return _hasAddRecordSetPermission;
	}

	public boolean isShowConfigurationIcon() throws PortalException {
		if (_showConfigurationIcon != null) {
			return _showConfigurationIcon;
		}

		ThemeDisplay themeDisplay = getThemeDisplay();

		_showConfigurationIcon = PortletPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
			getPortletId(), ActionKeys.CONFIGURATION);

		return _showConfigurationIcon;
	}

	public boolean isShowEditDisplayDDMTemplateIcon() throws PortalException {
		if (_hasEditDisplayDDMTemplatePermission != null) {
			return _hasEditDisplayDDMTemplatePermission;
		}

		_hasEditDisplayDDMTemplatePermission = Boolean.FALSE;

		if (getDisplayDDMTemplateId() == 0) {
			return _hasEditDisplayDDMTemplatePermission;
		}

		_hasEditDisplayDDMTemplatePermission = DDMTemplatePermission.contains(
			getPermissionChecker(), getScopeGroupId(),
			getDisplayDDMTemplateId(), DDLPortletKeys.DYNAMIC_DATA_LISTS,
			ActionKeys.UPDATE);

		return _hasEditDisplayDDMTemplatePermission;
	}

	public boolean isShowEditFormDDMTemplateIcon() throws PortalException {
		if (_hasEditFormDDMTemplatePermission != null) {
			return _hasEditFormDDMTemplatePermission;
		}

		_hasEditFormDDMTemplatePermission = Boolean.FALSE;

		if (getFormDDMTemplateId() == 0) {
			return _hasEditFormDDMTemplatePermission;
		}

		_hasEditFormDDMTemplatePermission = DDMTemplatePermission.contains(
			getPermissionChecker(), getScopeGroupId(), getFormDDMTemplateId(),
			DDLPortletKeys.DYNAMIC_DATA_LISTS, ActionKeys.UPDATE);

		return _hasEditFormDDMTemplatePermission;
	}

	public boolean isShowEditRecordIcon() {
		return true;
	}

	public boolean isShowIconsActions() throws PortalException {
		if (_hasShowIconsActionPermission != null) {
			return _hasShowIconsActionPermission;
		}

		_hasShowIconsActionPermission = Boolean.FALSE;

		ThemeDisplay themeDisplay = getThemeDisplay();

		if (!themeDisplay.isSignedIn()) {
			return _hasShowIconsActionPermission;
		}

		Layout layout = themeDisplay.getLayout();

		if (layout.isLayoutPrototypeLinkActive()) {
			return _hasShowIconsActionPermission;
		}

		if (isShowConfigurationIcon() || isShowAddDDMTemplateIcon() ||
			isShowEditDisplayDDMTemplateIcon() ||
			isShowEditFormDDMTemplateIcon()) {

			_hasShowIconsActionPermission = Boolean.TRUE;
		}

		return _hasShowIconsActionPermission;
	}

	public boolean isSpreadsheet() {
		return PrefsParamUtil.getBoolean(
			_portletPreferences, _renderRequest, "spreadsheet");
	}

	protected DDMTemplate fetchDisplayDDMTemplate() {
		if (_displayDDMTemplate != null) {
			return _displayDDMTemplate;
		}

		_displayDDMTemplate = DDMTemplateLocalServiceUtil.fetchDDMTemplate(
			getDisplayDDMTemplateId());

		return _displayDDMTemplate;
	}

	protected DDMTemplate fetchFormDDMTemplate() {
		if (_formDDMTemplate != null) {
			return _formDDMTemplate;
		}

		_formDDMTemplate = DDMTemplateLocalServiceUtil.fetchDDMTemplate(
			getFormDDMTemplateId());

		return _formDDMTemplate;
	}

	protected DDMDisplay getDDMDisplay() {
		return DDMDisplayRegistryUtil.getDDMDisplay(
			DDLPortletKeys.DYNAMIC_DATA_LISTS);
	}

	protected Layout getLayout() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		return themeDisplay.getLayout();
	}

	protected Locale getLocale() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		return themeDisplay.getLocale();
	}

	protected PermissionChecker getPermissionChecker() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		return themeDisplay.getPermissionChecker();
	}

	protected String getPortletId() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getId();
	}

	protected String getPortletResource() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getPortletResource();
	}

	protected long getScopeGroupId() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		return themeDisplay.getScopeGroupId();
	}

	protected long getStructureTypeClassNameId() {
		DDMDisplay ddmDisplay = getDDMDisplay();

		return PortalUtil.getClassNameId(ddmDisplay.getStructureType());
	}

	protected ThemeDisplay getThemeDisplay() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay;
	}

	protected boolean hasViewPermission() {
		if (_hasViewPermission != null) {
			return _hasViewPermission;
		}

		_hasViewPermission = true;

		if (_recordSet != null) {
			ThemeDisplay themeDisplay = getThemeDisplay();

			_hasViewPermission = DDLRecordSetPermission.contains(
				themeDisplay.getPermissionChecker(), _recordSet,
				ActionKeys.VIEW);
		}

		return _hasViewPermission;
	}

	private String _ddlRecordDisplayStyle;
	private String[] _ddlRecordDisplayViews;
	private DDMTemplate _displayDDMTemplate;
	private DDMTemplate _formDDMTemplate;
	private Boolean _hasAddDDMTemplatePermission;
	private Boolean _hasAddRecordSetPermission;
	private Boolean _hasEditDisplayDDMTemplatePermission;
	private Boolean _hasEditFormDDMTemplatePermission;
	private Boolean _hasShowIconsActionPermission;
	private Boolean _hasViewPermission;
	private final PortletPreferences _portletPreferences;
	private DDLRecordSet _recordSet;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private Boolean _showConfigurationIcon;

}