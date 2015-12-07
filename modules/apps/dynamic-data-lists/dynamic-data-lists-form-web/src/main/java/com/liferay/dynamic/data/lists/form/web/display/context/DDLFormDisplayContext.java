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

package com.liferay.dynamic.data.lists.form.web.display.context;

import com.liferay.dynamic.data.lists.form.web.constants.DDLFormWebKeys;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.dynamic.data.lists.service.permission.DDLRecordSetPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;

import javax.portlet.RenderRequest;

/**
 * @author Marcellus Tavares
 */
public class DDLFormDisplayContext {

	public DDLFormDisplayContext(RenderRequest renderRequest)
		throws PortalException {

		_renderRequest = renderRequest;

		if (Validator.isNotNull(getPortletResource())) {
			return;
		}

		DDLRecordSet recordSet = getRecordSet();

		if ((recordSet == null) || !hasViewPermission()) {
			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}
	}

	public DDLRecordSet getRecordSet() {
		if (_recordSet != null) {
			return _recordSet;
		}

		_recordSet = (DDLRecordSet)_renderRequest.getAttribute(
			DDLFormWebKeys.DYNAMIC_DATA_LISTS_RECORD_SET);

		if (_recordSet != null) {
			return _recordSet;
		}

		_recordSet = DDLRecordSetLocalServiceUtil.fetchDDLRecordSet(
			getRecordSetId());

		return _recordSet;
	}

	public long getRecordSetId() {
		if (_recordSetId != 0) {
			return _recordSetId;
		}

		_recordSetId = PrefsParamUtil.getLong(
			_renderRequest.getPreferences(), _renderRequest, "recordSetId");

		return _recordSetId;
	}

	public boolean isFormAvailable() {
		if (isSharedURL()) {
			return isPublished() && isFormShared();
		}

		return getRecordSet() != null;
	}

	public boolean isShowConfigurationIcon() throws PortalException {
		if (_showConfigurationIcon != null) {
			return _showConfigurationIcon;
		}

		if (isSharedURL() && isPublished() && isFormShared()) {
			_showConfigurationIcon = false;

			return _showConfigurationIcon;
		}

		ThemeDisplay themeDisplay = getThemeDisplay();

		_showConfigurationIcon = PortletPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
			getPortletId(), ActionKeys.CONFIGURATION);

		return _showConfigurationIcon;
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

	protected ThemeDisplay getThemeDisplay() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay;
	}

	protected boolean hasViewPermission() throws PortalException {
		if (_hasViewPermission != null) {
			return _hasViewPermission;
		}

		_hasViewPermission = true;

		DDLRecordSet recordSet = getRecordSet();

		if (recordSet != null) {
			ThemeDisplay themeDisplay = getThemeDisplay();

			_hasViewPermission = DDLRecordSetPermission.contains(
				themeDisplay.getPermissionChecker(), recordSet,
				ActionKeys.VIEW);
		}

		return _hasViewPermission;
	}

	protected boolean isFormShared() {
		return ParamUtil.getBoolean(_renderRequest, "shared");
	}

	protected boolean isPublished() {
		DDLRecordSet ddlRecordSet = getRecordSet();

		if (ddlRecordSet == null) {
			return false;
		}

		return GetterUtil.getBoolean(
			ddlRecordSet.getSettingsProperty("published"));
	}

	protected boolean isSharedURL() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		String urlCurrent = themeDisplay.getURLCurrent();

		return urlCurrent.contains("/shared");
	}

	private Boolean _hasViewPermission;
	private DDLRecordSet _recordSet;
	private long _recordSetId;
	private final RenderRequest _renderRequest;
	private Boolean _showConfigurationIcon;

}