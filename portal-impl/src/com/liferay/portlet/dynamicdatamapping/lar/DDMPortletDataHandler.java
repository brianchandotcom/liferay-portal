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

package com.liferay.portlet.dynamicdatamapping.lar;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureExportActionableDynamicQuery;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMTemplateExportActionableDynamicQuery;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Marcellus Tavares
 * @author Juan Fernández
 */
public class DDMPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "dynamic_data_mapping";

	public DDMPortletDataHandler() {
		setAlwaysExportable(true);
		setDataLocalized(true);
		setExportControls(
			new PortletDataHandlerBoolean(NAMESPACE, "structures", true, true),
			new PortletDataHandlerBoolean(NAMESPACE, "templates"));
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				DDMPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		DDMTemplateLocalServiceUtil.deleteTemplates(
			portletDataContext.getScopeGroupId());

		DDMStructureLocalServiceUtil.deleteStructures(
			portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.dynamicdatamapping",
			portletDataContext.getScopeGroupId());

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		ActionableDynamicQuery structureActionableDynamicQuery =
			new DDMStructureExportActionableDynamicQuery(portletDataContext);

		structureActionableDynamicQuery.performActions();

		ActionableDynamicQuery templateActionableDynamicQuery =
			new DDMTemplateExportActionableDynamicQuery(portletDataContext);

		templateActionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.dynamicdatamapping",
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Element structuresElement =
			portletDataContext.getImportDataGroupElement(DDMStructure.class);

		List<Element> structureElements = structuresElement.elements();

		for (Element structureElement : structureElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, structureElement);
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "templates")) {
			Element templatesElement =
				portletDataContext.getImportDataGroupElement(DDMTemplate.class);

			List<Element> templateElements = templatesElement.elements();

			for (Element templateElement : templateElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, templateElement);
			}
		}

		return portletPreferences;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext)
		throws Exception {

		ActionableDynamicQuery structureActionableDynamicQuery =
			new DDMStructureExportActionableDynamicQuery(portletDataContext);

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		manifestSummary.addModelCount(
			DDMStructure.class, structureActionableDynamicQuery.performCount());

		ActionableDynamicQuery templateActionableDynamicQuery =
			new DDMTemplateExportActionableDynamicQuery(portletDataContext);

		manifestSummary.addModelCount(
			DDMTemplate.class, templateActionableDynamicQuery.performCount());
	}

}