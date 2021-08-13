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

package com.liferay.depot.web.internal.exportimport.data.handler;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.web.internal.constants.DepotPortletKeys;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.xml.Element;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "javax.portlet.name=" + DepotPortletKeys.DEPOT_ADMIN,
	service = PortletDataHandler.class
)
public class DepotAdminPortletDataHandler extends BasePortletDataHandler {

	public static final String[] CLASS_NAMES = {
		DepotEntryGroupRel.class.getName()
	};

	public static final String NAMESPACE = "depot";

	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public String getResourceName() {
		return DepotConstants.RESOURCE_NAME;
	}

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Override
	public String getServiceName() {
		return DepotConstants.SERVICE_NAME;
	}

	@Activate
	protected void activate() {
		setDataAlwaysStaged(true);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(DepotEntryGroupRel.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				getNamespace(), "site-connections", true, true, null,
				DepotEntryGroupRel.class.getName()));
		setPublishToLiveByDefault(true);
		setStagingControls(getExportControls());
	}

	@Override
	protected PortletPreferences doDeleteData(
		PortletDataContext portletDataContext, String portletId,
		PortletPreferences portletPreferences) {

		_depotEntryGroupRelLocalService.deleteToGroupDepotEntryGroupRels(
			portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = addExportDataRootElement(portletDataContext);

		portletDataContext.addPortletPermissions(DepotConstants.RESOURCE_NAME);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery actionableDynamicQuery =
			_depotEntryGroupRelLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortletPermissions(
			DepotConstants.RESOURCE_NAME);

		Element groupElement = portletDataContext.getImportDataGroupElement(
			DepotEntryGroupRel.class);

		for (Element element : groupElement.elements()) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, element);
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		if (ExportImportDateUtil.isRangeFromLastPublishDate(
				portletDataContext)) {

			_staging.populateLastPublishDateCounts(
				portletDataContext,
				new StagedModelType[] {
					new StagedModelType(DepotEntryGroupRel.class.getName())
				});

			return;
		}

		ActionableDynamicQuery actionableDynamicQuery =
			_depotEntryGroupRelLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.performCount();
	}

	@Reference
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Reference
	private Staging _staging;

}