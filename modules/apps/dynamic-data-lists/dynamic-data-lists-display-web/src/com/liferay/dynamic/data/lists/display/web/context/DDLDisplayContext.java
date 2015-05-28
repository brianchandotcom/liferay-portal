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

package com.liferay.dynamic.data.lists.display.web.context;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.dynamic.data.lists.service.permission.DDLRecordSetPermission;
import com.liferay.dynamic.data.lists.util.DDLUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldType;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeRegistryUtil;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldValueRendererAccessor;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMPermission;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMTemplatePermission;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.util.DDMDisplay;
import com.liferay.portlet.dynamicdatamapping.util.DDMDisplayRegistryUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMPermissionHandler;

import java.util.List;
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

		String portletId = PortalUtil.getPortletId(renderRequest);

		if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
			return;
		}

		DDLRecordSet recordSet = getRecordSet();

		if ((recordSet == null) || !hasViewPermission()) {
			_renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}
	}

	public long getDisplayDDMTemplateId() {
		return PrefsParamUtil.getLong(
			_portletPreferences, _renderRequest, "displayDDMTemplateId");
	}

	public String getEditTemplateTitle() throws PortalException {
		DDMDisplay ddmDisplay = getDDMDisplay();

		return ddmDisplay.getEditTemplateTitle(
			_recordSet.getDDMStructure(), fetchDisplayDDMTemplate(),
			getLocale());
	}

	public DDLRecordSet getRecordSet() {
		if (_recordSet != null) {
			return _recordSet;
		}

		_recordSet = (DDLRecordSet)_renderRequest.getAttribute(
			WebKeys.DYNAMIC_DATA_LISTS_RECORD_SET);

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

	public String getTemplateContent() throws Exception {
		return DDLUtil.getTemplateContent(
			getDisplayDDMTemplateId(), _recordSet, getThemeDisplay(),
			_renderRequest, _renderResponse);
	}

	public boolean isEditable() {
		return PrefsParamUtil.getBoolean(
			_portletPreferences, _renderRequest, "editable");
	}

	public boolean isShowAddDisplayDDMTemplateIcon() {
		if (_hasAddDisplayDDMTemplatePermission != null) {
			return _hasAddDisplayDDMTemplatePermission;
		}

		_hasAddDisplayDDMTemplatePermission = Boolean.FALSE;

		DDLRecordSet recordSet = getRecordSet();

		if (recordSet == null) {
			return _hasAddDisplayDDMTemplatePermission;
		}

		DDMPermissionHandler ddmPermissionHandler = getDDMPermissionHandler();

		_hasAddDisplayDDMTemplatePermission = DDMPermission.contains(
			getPermissionChecker(), getScopeGroupId(),
			ddmPermissionHandler.getResourceName(getStructureTypeClassNameId()),
			ddmPermissionHandler.getAddTemplateActionId());

		return _hasAddDisplayDDMTemplatePermission;
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
			getDisplayDDMTemplateId(), PortletKeys.DYNAMIC_DATA_LISTS,
			ActionKeys.UPDATE);

		return _hasEditDisplayDDMTemplatePermission;
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

		if (isShowConfigurationIcon() || isShowAddDisplayDDMTemplateIcon() ||
			isShowEditDisplayDDMTemplateIcon()) {

			_hasShowIconsActionPermission = Boolean.TRUE;
		}

		return _hasShowIconsActionPermission;
	}

	public String renderRecordField(
			DDLRecord record, DDMFormField ddmFormField, Locale locale)
		throws PortalException {

		List<DDMFormFieldValue> ddmFormFieldValues =
			record.getDDMFormFieldValues(ddmFormField.getName());

		DDMFormFieldValueRendererAccessor
			ddmFormFieldValueRendererAccessor =
				getDDMFormFieldValueRendererAccessor(ddmFormField, locale);

		return ListUtil.toString(
			ddmFormFieldValues, ddmFormFieldValueRendererAccessor,
			StringPool.COMMA_AND_SPACE);
	}

	protected DDMTemplate fetchDisplayDDMTemplate() {
		if (_displayDDMTemplate != null) {
			return _displayDDMTemplate;
		}

		_displayDDMTemplate = DDMTemplateLocalServiceUtil.fetchDDMTemplate(
			getDisplayDDMTemplateId());

		return _displayDDMTemplate;
	}

	protected DDMDisplay getDDMDisplay() {
		return DDMDisplayRegistryUtil.getDDMDisplay(
			PortletKeys.DYNAMIC_DATA_LISTS);
	}

	protected DDMFormFieldValueRendererAccessor
		getDDMFormFieldValueRendererAccessor(
			DDMFormField ddmFormField, Locale locale) {

		DDMFormFieldType ddmFormFieldType =
			DDMFormFieldTypeRegistryUtil.getDDMFormFieldType(
				ddmFormField.getType());

		return ddmFormFieldType.getDDMFormFieldValueRendererAccessor(locale);
	}

	protected DDMPermissionHandler getDDMPermissionHandler() {
		DDMDisplay ddmDisplay = getDDMDisplay();

		return ddmDisplay.getDDMPermissionHandler();
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

	private DDMTemplate _displayDDMTemplate;
	private Boolean _hasAddDisplayDDMTemplatePermission;
	private Boolean _hasEditDisplayDDMTemplatePermission;
	private Boolean _hasShowIconsActionPermission;
	private Boolean _hasViewPermission;
	private final PortletPreferences _portletPreferences;
	private DDLRecordSet _recordSet;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private Boolean _showConfigurationIcon;

}