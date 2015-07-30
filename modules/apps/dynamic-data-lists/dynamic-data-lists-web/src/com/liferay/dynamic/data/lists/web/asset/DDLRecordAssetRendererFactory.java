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

import com.liferay.dynamic.data.lists.constants.DDLActionKeys;
import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalServiceUtil;
import com.liferay.dynamic.data.lists.service.permission.DDLRecordSetPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;
import com.liferay.portlet.asset.model.ClassTypeReader;

import javax.portlet.PortletURL;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDLPortletKeys.DYNAMIC_DATA_LISTS,
		"search.asset.type=com.liferay.dynamic.data.lists.model.DDLRecord"
	},
	service = AssetRendererFactory.class
)
public class DDLRecordAssetRendererFactory
	extends BaseAssetRendererFactory<DDLRecord> {

	public static final String TYPE = "record";

	public DDLRecordAssetRendererFactory() {
		setCategorizable(false);
		setClassName(DDLRecord.class.getName());
		setPortletId(DDLPortletKeys.DYNAMIC_DATA_LISTS);
		setSelectable(true);
	}

	@Override
	public AssetRenderer<DDLRecord> getAssetRenderer(long classPK, int type)
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
		ddlRecordAssetRenderer.setServletContext(_servletContext);

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
			DDLPortletKeys.DYNAMIC_DATA_LISTS);

		portletURL.setParameter("mvcPath", "/edit_record.jsp");

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
			permissionChecker, classTypeId, DDLActionKeys.ADD_RECORD);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		DDLRecord record = DDLRecordLocalServiceUtil.getRecord(classPK);

		return DDLRecordSetPermission.contains(
			permissionChecker, record.getRecordSet(), actionId);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.dynamic.data.lists.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/history.png";
	}

	private ServletContext _servletContext;

}