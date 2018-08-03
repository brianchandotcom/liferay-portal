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

package com.liferay.expando.exportimport.internal.data.handler;

import com.liferay.expando.constants.ExpandoPortletKeys;
import com.liferay.expando.kernel.model.adapter.StagedExpandoColumn;
import com.liferay.expando.kernel.model.adapter.StagedExpandoTable;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + ExpandoPortletKeys.EXPANDO,
	service = PortletDataHandler.class
)
public class ExpandoPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "expando";

	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Activate
	protected void activate() {
		setDataLevel(DataLevel.PORTAL);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(StagedExpandoTable.class),
			new StagedModelType(StagedExpandoColumn.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "expando-table", true, true, null,
				StagedExpandoTable.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "expando-column", true, true, null,
				StagedExpandoColumn.class.getName()));
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		_stagedExpandoTableStagedModelRepository.deleteStagedModels(
			portletDataContext);

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortalPermissions();

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ExportActionableDynamicQuery actionableDynamicQuery =
			_stagedExpandoTableStagedModelRepository.
				getExportActionableDynamicQuery(portletDataContext);

		actionableDynamicQuery.performActions();

		actionableDynamicQuery =
			_stagedExpandoColumnStagedModelRepository.
				getExportActionableDynamicQuery(portletDataContext);

		actionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortalPermissions();

		Element stagedExpandoTablesElement =
			portletDataContext.getImportDataGroupElement(
				StagedExpandoTable.class);

		List<Element> stagedExpandoTablesElements =
			stagedExpandoTablesElement.elements();

		for (Element stagedExpandoTableElement : stagedExpandoTablesElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, stagedExpandoTableElement);
		}

		Element stagedExpandoColumnsElement =
			portletDataContext.getImportDataGroupElement(
				StagedExpandoColumn.class);

		List<Element> stagedExpandoColumnsElements =
			stagedExpandoColumnsElement.elements();

		for (Element stagedExpandoColumnElement :
				stagedExpandoColumnsElements) {

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, stagedExpandoColumnElement);
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ActionableDynamicQuery exportActionableDynamicQuery =
			_stagedExpandoTableStagedModelRepository.
				getExportActionableDynamicQuery(portletDataContext);

		exportActionableDynamicQuery.performCount();

		exportActionableDynamicQuery =
			_stagedExpandoColumnStagedModelRepository.
				getExportActionableDynamicQuery(portletDataContext);

		exportActionableDynamicQuery.performCount();
	}

	@Reference(
		target = "(model.class.name=com.liferay.expando.kernel.model.adapter.StagedExpandoColumn)",
		unbind = "-"
	)
	protected void setStagedExpandoColumnStagedModelRepository(
		StagedModelRepository<StagedExpandoColumn>
			stagedExpandoColumnStagedModelRepository) {

		_stagedExpandoColumnStagedModelRepository =
			stagedExpandoColumnStagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.expando.kernel.model.adapter.StagedExpandoTable)",
		unbind = "-"
	)
	protected void setStagedExpandoTableStagedModelRepository(
		StagedModelRepository<StagedExpandoTable>
			stagedExpandoTableStagedModelRepository) {

		_stagedExpandoTableStagedModelRepository =
			stagedExpandoTableStagedModelRepository;
	}

	private StagedModelRepository<StagedExpandoColumn>
		_stagedExpandoColumnStagedModelRepository;
	private StagedModelRepository<StagedExpandoTable>
		_stagedExpandoTableStagedModelRepository;

}