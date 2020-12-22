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

package com.liferay.app.builder.web.internal.asset.model;

import com.liferay.app.builder.constants.AppBuilderWebKeys;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.portlet.tab.AppBuilderAppPortletTab;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.data.engine.constants.DataActionKeys;
import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rafael Praxedes
 */
public class AppBuilderAppAssetRenderer
	extends BaseJSPAssetRenderer<DDLRecord> {

	public AppBuilderAppAssetRenderer(
		AppBuilderApp appBuilderApp,
		AppBuilderAppPortletTab appBuilderAppPortletTab,
		DataDefinitionContentType appBuilderDataDefinitionContentType,
		DDLRecord ddlRecord, DDLRecordVersion ddlRecordVersion) {

		_appBuilderApp = appBuilderApp;
		_appBuilderAppPortletTab = appBuilderAppPortletTab;
		_appBuilderDataDefinitionContentType =
			appBuilderDataDefinitionContentType;
		_ddlRecord = ddlRecord;
		_ddlRecordVersion = ddlRecordVersion;
	}

	@Override
	public DDLRecord getAssetObject() {
		return _ddlRecord;
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _appBuilderApp.getAvailableLanguageIds();
	}

	@Override
	public String getClassName() {
		return ResourceActionsUtil.getCompositeModelName(
			AppBuilderApp.class.getName(), DDLRecord.class.getName());
	}

	@Override
	public long getClassPK() {
		return _ddlRecord.getRecordId();
	}

	@Override
	public long getGroupId() {
		return _ddlRecord.getGroupId();
	}

	@Override
	public String getJspPath(
		HttpServletRequest httpServletRequest, String template) {

		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			return "/asset/full_content.jsp";
		}

		return null;
	}

	@Override
	public int getStatus() {
		return _ddlRecordVersion.getStatus();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		return _appBuilderApp.getName(locale);
	}

	@Override
	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect)
		throws Exception {

		return noSuchEntryRedirect;
	}

	@Override
	public long getUserId() {
		return _ddlRecord.getUserId();
	}

	@Override
	public String getUserName() {
		return _ddlRecord.getUserName();
	}

	@Override
	public String getUuid() {
		return _ddlRecord.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return _hasPermission(
			permissionChecker, _ddlRecord.getRecordSet(),
			DataActionKeys.UPDATE_DATA_RECORD);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		try {
			return _hasPermission(
				permissionChecker, _ddlRecord.getRecordSet(),
				DataActionKeys.VIEW_DATA_RECORD);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return false;
	}

	@Override
	public boolean include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String template)
		throws Exception {

		httpServletRequest.setAttribute(
			AppBuilderWebKeys.APP_TAB_CONTEXT,
			_appBuilderAppPortletTab.getAppBuilderAppPortletTabContext(
				_appBuilderApp, _ddlRecord.getRecordId()));
		httpServletRequest.setAttribute(
			AppBuilderWebKeys.DATA_RECORD_ID, _ddlRecord.getRecordId());

		return super.include(httpServletRequest, httpServletResponse, template);
	}

	private boolean _hasPermission(
			PermissionChecker permissionChecker, DDLRecordSet ddlRecordSet,
			String actionId)
		throws PortalException {

		return _appBuilderDataDefinitionContentType.hasPermission(
			permissionChecker, ddlRecordSet.getCompanyId(),
			ddlRecordSet.getGroupId(),
			ResourceActionsUtil.getCompositeModelName(
				AppBuilderApp.class.getName(), DDLRecordSet.class.getName()),
			ddlRecordSet.getRecordSetId(), ddlRecordSet.getUserId(), actionId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AppBuilderAppAssetRenderer.class);

	private final AppBuilderApp _appBuilderApp;
	private final AppBuilderAppPortletTab _appBuilderAppPortletTab;
	private final DataDefinitionContentType
		_appBuilderDataDefinitionContentType;
	private final DDLRecord _ddlRecord;
	private final DDLRecordVersion _ddlRecordVersion;

}