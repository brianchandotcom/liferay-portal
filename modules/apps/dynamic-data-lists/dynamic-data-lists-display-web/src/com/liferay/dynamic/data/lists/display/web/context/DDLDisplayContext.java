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

import com.liferay.dynamic.data.lists.display.web.util.DDLDisplayRendererHelper;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.permission.DDLRecordSetPermission;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;

/**
 * @author Marcellus Tavares
 */
public class DDLDisplayContext {

	public DDLDisplayContext(RenderRequest renderRequest) {
		_renderRequest = renderRequest;
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

	public Map<String, Object> getContextObjects(
		SearchContainer<?> searchContainer) {

		Map<String, Object> contextObjects = new HashMap<>();

		contextObjects.put(
			"displayRendererHelper", getDDLDisplayRendererHelper());
		contextObjects.put("recordSet", _recordSet);
		contextObjects.put("total", searchContainer.getTotal());

		return contextObjects;
	}

	public String getDisplayStyle() {
		return PrefsParamUtil.getString(
			_portletPreferences, _renderRequest, "displayStyle");
	}

	public long getDisplayStyleGroupId() {
		return PrefsParamUtil.getLong(
			_portletPreferences, _renderRequest, "displayStyleGroupId");
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

	protected DDLDisplayRendererHelper getDDLDisplayRendererHelper() {
		DDLDisplayRendererHelper ddlDisplayRendererHelper =
			(DDLDisplayRendererHelper)_renderRequest.getAttribute(
				"ddlDisplayRendererHelper");

		return ddlDisplayRendererHelper;
	}

	protected Layout getLayout() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		return themeDisplay.getLayout();
	}

	protected String getPortletId() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getId();
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

	private Boolean _hasViewPermission;
	private final PortletPreferences _portletPreferences;
	private DDLRecordSet _recordSet;
	private final RenderRequest _renderRequest;
	private Boolean _showConfigurationIcon;

}