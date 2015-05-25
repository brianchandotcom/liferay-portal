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

package com.liferay.dynamic.data.lists.web.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;
import com.liferay.portlet.asset.model.ClassTypeReader;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordVersionLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.permission.DDLRecordSetPermission;

import javax.portlet.PortletURL;

/**
 * @author Marcellus Tavares
 */
public class DDLRecordAssetRendererFactory extends BaseAssetRendererFactory {

	public static final String TYPE = "record";

	public DDLRecordAssetRendererFactory() {
		setCategorizable(false);
		setSelectable(true);
	}

	@Override
	public AssetRenderer getAssetRenderer(long classPK, int type)
		throws PortalException {

		DDLRecord record = null;
		DDLRecordVersion recordVersion = null;

		if (type == TYPE_LATEST) {
			recordVersion = DDLRecordVersionLocalServiceUtil.getRecordVersion(
				classPK);

			record = recordVersion.getRecord();
		}
		else {
			record = DDLRecordLocalServiceUtil.getRecord(classPK);

			recordVersion = record.getRecordVersion();
		}

		DDLRecordAssetRenderer ddlRecordAssetRenderer =
			new DDLRecordAssetRenderer(record, recordVersion);

		ddlRecordAssetRenderer.setAssetRendererType(type);

		return ddlRecordAssetRenderer;
	}

	@Override
	public String getClassName() {
		return DDLRecord.class.getName();
	}

	@Override
	public ClassTypeReader getClassTypeReader() {
		return new DDLRecordSetClassTypeReader();
	}

	@Override
	public String getIconCssClass() {
		return "icon-file-2";
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLAdd(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, long classTypeId) {

		PortletURL portletURL = liferayPortletResponse.createRenderURL(
			PortletKeys.DYNAMIC_DATA_LISTS);

		portletURL.setParameter(
			"struts_action", "/dynamic_data_lists/edit_record");

		if (classTypeId > 0) {
			portletURL.setParameter("recordSetId", String.valueOf(classTypeId));
		}

		return portletURL;
	}

	@Override
	public boolean hasAddPermission(
			PermissionChecker permissionChecker, long groupId, long classTypeId)
		throws Exception {

		if (classTypeId == 0) {
			return false;
		}

		return DDLRecordSetPermission.contains(
			permissionChecker, classTypeId, ActionKeys.ADD_RECORD);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		DDLRecord record = DDLRecordLocalServiceUtil.getRecord(classPK);

		return DDLRecordSetPermission.contains(
			permissionChecker, record.getRecordSet(), actionId);
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/history.png";
	}

}