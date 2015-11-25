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

package com.liferay.dynamic.data.lists.web.display.context;

import com.liferay.dynamic.data.lists.configuration.DDLServiceConfiguration;
import com.liferay.dynamic.data.lists.constants.DDLActionKeys;
import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.dynamic.data.lists.constants.DDLWebKeys;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.dynamic.data.lists.service.permission.DDLPermission;
import com.liferay.dynamic.data.lists.service.permission.DDLRecordSetPermission;
import com.liferay.dynamic.data.lists.web.display.context.util.DDLRequestHelper;
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
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.util.Locale;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marcellus Tavares
 */
public class DDLDisplayContext {

	public DDLDisplayContext(HttpServletRequest request) {
		_ddlRequestHelper = new DDLRequestHelper(request);

		if (Validator.isNotNull(getPortletResource())) {
			return;
		}

		DDLRecordSet recordSet = getRecordSet();

		if ((recordSet == null) || !hasViewPermission()) {
			RenderRequest renderRequest = _ddlRequestHelper.getRenderRequest();

			renderRequest.setAttribute(
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
				_ddlRequestHelper.getRenderRequest(),
				getDDLRecordSetDisplayViews());
		}

		return _ddlRecordDisplayStyle;
	}

	public String[] getDDLRecordSetDisplayViews() {
		if (_ddlRecordDisplayViews == null) {
			DDLServiceConfiguration ddlServiceConfiguration =
				_ddlRequestHelper.getDDLServiceConfiguration();

			_ddlRecordDisplayViews = StringUtil.split(
				PrefsParamUtil.getString(
					_ddlRequestHelper.getPortletPreferences(),
					_ddlRequestHelper.getRenderRequest(), "displayViews",
					StringUtil.merge(
						ddlServiceConfiguration.supportedDisplayView())));
		}

		return _ddlRecordDisplayViews;
	}

	public long getDisplayDDMTemplateId() {
		return PrefsParamUtil.getLong(
			_ddlRequestHelper.getPortletPreferences(),
			_ddlRequestHelper.getRenderRequest(), "displayDDMTemplateId");
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
			return LanguageUtil.get(getLocale(), "add-list");
		}

		DDMDisplay ddmDisplay = getDDMDisplay();

		return ddmDisplay.getEditTemplateTitle(
			recordSet.getDDMStructure(), fetchFormDDMTemplate(), getLocale());
	}

	public long getFormDDMTemplateId() {
		return PrefsParamUtil.getLong(
			_ddlRequestHelper.getPortletPreferences(),
			_ddlRequestHelper.getRenderRequest(), "formDDMTemplateId");
	}

	public DDLRecordSet getRecordSet() {
		if (_recordSet != null) {
			return _recordSet;
		}

		RenderRequest renderRequest = _ddlRequestHelper.getRenderRequest();

		_recordSet = (DDLRecordSet)renderRequest.getAttribute(
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
			_ddlRequestHelper.getPortletPreferences(),
			_ddlRequestHelper.getRenderRequest(), "recordSetId");
	}

	public boolean isAdminPortlet() {
		String portletName = getPortletName();

		return portletName.equals(DDLPortletKeys.DYNAMIC_DATA_LISTS);
	}

	public boolean isDisplayPortlet() {
		return !isAdminPortlet();
	}

	public boolean isEditable() {
		return PrefsParamUtil.getBoolean(
			_ddlRequestHelper.getPortletPreferences(),
			_ddlRequestHelper.getRenderRequest(), "editable", true);
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

		_showConfigurationIcon = PortletPermissionUtil.contains(
			getPermissionChecker(), getLayout(), getPortletId(),
			ActionKeys.CONFIGURATION);

		return _showConfigurationIcon;
	}

	public boolean isShowEditDisplayDDMTemplateIcon() throws PortalException {
		if (_hasEditDisplayDDMTemplatePermission != null) {
			return _hasEditDisplayDDMTemplatePermission;
		}

		_hasEditDisplayDDMTemplatePermission = Boolean.FALSE;

		DDMTemplate displayDDMTemplate = fetchDisplayDDMTemplate();

		if (displayDDMTemplate == null) {
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
			_ddlRequestHelper.getPortletPreferences(),
			_ddlRequestHelper.getRenderRequest(), "spreadsheet");
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
		return _ddlRequestHelper.getLayout();
	}

	protected Locale getLocale() {
		return _ddlRequestHelper.getLocale();
	}

	protected PermissionChecker getPermissionChecker() {
		return _ddlRequestHelper.getPermissionChecker();
	}

	protected String getPortletId() {
		return _ddlRequestHelper.getPortletId();
	}

	protected String getPortletName() {
		return _ddlRequestHelper.getPortletName();
	}

	protected String getPortletResource() {
		return _ddlRequestHelper.getPortletResource();
	}

	protected long getScopeGroupId() {
		return _ddlRequestHelper.getScopeGroupId();
	}

	protected long getStructureTypeClassNameId() {
		DDMDisplay ddmDisplay = getDDMDisplay();

		return PortalUtil.getClassNameId(ddmDisplay.getStructureType());
	}

	protected ThemeDisplay getThemeDisplay() {
		return _ddlRequestHelper.getThemeDisplay();
	}

	protected boolean hasViewPermission() {
		if (_hasViewPermission != null) {
			return _hasViewPermission;
		}

		_hasViewPermission = true;

		if (_recordSet != null) {
			_hasViewPermission = DDLRecordSetPermission.contains(
				getPermissionChecker(), _recordSet, ActionKeys.VIEW);
		}

		return _hasViewPermission;
	}

	private String _ddlRecordDisplayStyle;
	private String[] _ddlRecordDisplayViews;
	private final DDLRequestHelper _ddlRequestHelper;
	private DDMTemplate _displayDDMTemplate;
	private DDMTemplate _formDDMTemplate;
	private Boolean _hasAddDDMTemplatePermission;
	private Boolean _hasAddRecordSetPermission;
	private Boolean _hasEditDisplayDDMTemplatePermission;
	private Boolean _hasEditFormDDMTemplatePermission;
	private Boolean _hasShowIconsActionPermission;
	private Boolean _hasViewPermission;
	private DDLRecordSet _recordSet;
	private Boolean _showConfigurationIcon;

}