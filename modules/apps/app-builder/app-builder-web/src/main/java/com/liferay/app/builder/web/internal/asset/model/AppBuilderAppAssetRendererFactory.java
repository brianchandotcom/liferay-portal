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

import com.liferay.app.builder.constants.AppBuilderPortletKeys;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.model.AppBuilderAppDataRecordLink;
import com.liferay.app.builder.portlet.tab.AppBuilderAppPortletTab;
import com.liferay.app.builder.service.AppBuilderAppDataRecordLinkLocalService;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.data.engine.constants.DataActionKeys;
import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Objects;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + AppBuilderPortletKeys.APPS,
	service = AssetRendererFactory.class
)
public class AppBuilderAppAssetRendererFactory
	extends BaseAssetRendererFactory<DDLRecord> {

	public static final String TYPE = "appEntry";

	public AppBuilderAppAssetRendererFactory() {
		setCategorizable(false);
		setClassName(
			ResourceActionsUtil.getCompositeModelName(
				AppBuilderApp.class.getName(), DDLRecord.class.getName()));
		setPortletId(AppBuilderPortletKeys.APPS);
		setSearchable(false);
		setSelectable(false);
	}

	@Override
	public AssetEntry getAssetEntry(long assetEntryId) throws PortalException {
		return getAssetEntry(getClassName(), assetEntryId);
	}

	@Override
	public AssetRenderer<DDLRecord> getAssetRenderer(long classPK, int type)
		throws PortalException {

		DDLRecord ddlRecord = _ddlRecordLocalService.getDDLRecord(classPK);

		AppBuilderApp appBuilderApp = _getAppBuilderApp(ddlRecord);

		AppBuilderAppAssetRenderer appBuilderAppAssetRenderer =
			new AppBuilderAppAssetRenderer(
				appBuilderApp,
				_appBuilderAppPortletTabServiceTrackerMap.getService(
					appBuilderApp.getScope()),
				_appBuilderDataDefinitionContentType, ddlRecord,
				ddlRecord.getLatestRecordVersion());

		appBuilderAppAssetRenderer.setAssetRendererType(type);
		appBuilderAppAssetRenderer.setServletContext(_servletContext);

		return appBuilderAppAssetRenderer;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		if (Objects.equals(ActionKeys.DELETE, actionId)) {
			actionId = DataActionKeys.DELETE_DATA_RECORD;
		}
		else if (Objects.equals(ActionKeys.UPDATE, actionId)) {
			actionId = DataActionKeys.UPDATE_DATA_RECORD;
		}
		else if (Objects.equals(ActionKeys.VIEW, actionId)) {
			actionId = DataActionKeys.VIEW_DATA_RECORD;
		}

		DDLRecord ddlRecord = _ddlRecordLocalService.getDDLRecord(classPK);

		DDLRecordSet ddlRecordSet = ddlRecord.getRecordSet();

		return _appBuilderDataDefinitionContentType.hasPermission(
			permissionChecker, ddlRecordSet.getCompanyId(),
			ddlRecordSet.getGroupId(),
			ResourceActionsUtil.getCompositeModelName(
				AppBuilderApp.class.getName(), DDLRecordSet.class.getName()),
			ddlRecordSet.getRecordSetId(), ddlRecordSet.getUserId(), actionId);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.app.builder.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_appBuilderAppPortletTabServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, AppBuilderAppPortletTab.class,
				"app.builder.app.tab.name");
	}

	@Deactivate
	protected void deactivate() {
		_appBuilderAppPortletTabServiceTrackerMap.close();
	}

	private AppBuilderApp _getAppBuilderApp(DDLRecord ddlRecord)
		throws PortalException {

		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
			_appBuilderAppDataRecordLinkLocalService.
				getDDLRecordAppBuilderAppDataRecordLink(
					ddlRecord.getRecordId());

		return _appBuilderAppLocalService.getAppBuilderApp(
			appBuilderAppDataRecordLink.getAppBuilderAppId());
	}

	private static ServiceTrackerMap<String, AppBuilderAppPortletTab>
		_appBuilderAppPortletTabServiceTrackerMap;

	@Reference
	private AppBuilderAppDataRecordLinkLocalService
		_appBuilderAppDataRecordLinkLocalService;

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	@Reference(target = "(content.type=app-builder)")
	private DataDefinitionContentType _appBuilderDataDefinitionContentType;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private DDLRecordLocalService _ddlRecordLocalService;

	@Reference
	private Portal _portal;

	private ServletContext _servletContext;

}