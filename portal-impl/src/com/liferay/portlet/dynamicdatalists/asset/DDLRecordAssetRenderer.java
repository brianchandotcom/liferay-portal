/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.asset;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.service.permission.DDLRecordSetPermission;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Marcellus Tavares
 * @author Sergio González
 */
public class DDLRecordAssetRenderer extends BaseAssetRenderer {

	public DDLRecordAssetRenderer(
		DDLRecord record, DDLRecordVersion recordVersion) {

		_record = record;
		_recordVersion = recordVersion;

		try {
			_recordSet = record.getRecordSet();

			_ddmStructure = _recordSet.getDDMStructure();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	@Override
	public String getClassName() {
		return DDLRecord.class.getName();
	}

	@Override
	public long getClassPK() {
		return _record.getRecordId();
	}

	@Override
	public long getGroupId() {
		return _record.getGroupId();
	}

	@Override
	public String getSummary(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		String ddmStructureName = _ddmStructure.getName(locale);

		String recordSetName = _recordSet.getName(locale);

		return LanguageUtil.format(
			locale, "new-x-for-list-x",
			new Object[] {ddmStructureName, recordSetName}, false);
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			getControlPanelPlid(liferayPortletRequest),
			PortletKeys.DYNAMIC_DATA_LISTS, PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"struts_action", "/dynamic_data_lists/edit_record");
		portletURL.setParameter(
			"recordId", String.valueOf(_record.getRecordId()));

		return portletURL;
	}

	@Override
	public long getUserId() {
		return _record.getUserId();
	}

	@Override
	public String getUserName() {
		return _record.getUserName();
	}

	@Override
	public String getUuid() {
		return _record.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker) {
		return DDLRecordSetPermission.contains(
			permissionChecker, _recordSet, ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		return DDLRecordSetPermission.contains(
			permissionChecker, _recordSet, ActionKeys.VIEW);
	}

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			renderRequest.setAttribute(
				WebKeys.DYNAMIC_DATA_LISTS_RECORD, _record);
			renderRequest.setAttribute(
				WebKeys.DYNAMIC_DATA_LISTS_RECORD_VERSION, _recordVersion);

			String path =
				"/html/portlet/dynamic_data_lists/asset/full_content.jsp";

			return path;
		}
		else {
			return null;
		}
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/history.png";
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDLRecordAssetRenderer.class);

	private DDMStructure _ddmStructure;
	private DDLRecord _record;
	private DDLRecordSet _recordSet;
	private DDLRecordVersion _recordVersion;

}