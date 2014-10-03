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

package com.liferay.portlet.dynamicdatalists.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;
import com.liferay.portlet.asset.model.BaseDDMStructureClassTypeReader;
import com.liferay.portlet.asset.model.ClassTypeReader;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordVersionLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.permission.DDLRecordSetPermission;

/**
 * @author Marcellus Tavares
 */
public class DDLRecordAssetRendererFactory extends BaseAssetRendererFactory {

	public static final String TYPE = "record";

	public DDLRecordAssetRendererFactory() {
		setCategorizable(false);
		setSelectable(false);
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
		return new BaseDDMStructureClassTypeReader(getClassName());
	}

	@Override
	public String getType() {
		return TYPE;
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