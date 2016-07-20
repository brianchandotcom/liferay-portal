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

package com.liferay.dynamic.data.mapping.data.provider.web.internal.exportimport.data.handler;

import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
public class DDMDataProviderPortletDataHandler extends BasePortletDataHandler {

	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = addExportDataRootElement(portletDataContext);

		portletDataContext.addPortletPermissions(
			DDMDataProviderInstance.class.getName());

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_ddmDataProviderInstanceStagedModelRepository.
				getExportActionableDynamicQuery(portletDataContext);

		exportActionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Activate
	protected void activate() {
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(DDMDataProviderInstance.class));
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				DDMDataProviderPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		_ddmDataProviderInstanceStagedModelRepository.deleteStagedModels(
			portletDataContext);

		return portletPreferences;
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortletPermissions(
			DDMDataProviderInstance.class.getName());

		Element ddmDataProviderInstancesElement =
			portletDataContext.getImportDataGroupElement(
				DDMDataProviderInstance.class);

		List<Element> ddmDataProviderInstanceElements =
			ddmDataProviderInstancesElement.elements();

		for (Element ddmDataProviderInstanceElement :
				ddmDataProviderInstanceElements) {

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, ddmDataProviderInstanceElement);
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ActionableDynamicQuery entryExportActionableDynamicQuery =
			_ddmDataProviderInstanceStagedModelRepository.
				getExportActionableDynamicQuery(portletDataContext);

		entryExportActionableDynamicQuery.performCount();
	}

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance)"
	)
	private StagedModelRepository<DDMDataProviderInstance>
		_ddmDataProviderInstanceStagedModelRepository;

}