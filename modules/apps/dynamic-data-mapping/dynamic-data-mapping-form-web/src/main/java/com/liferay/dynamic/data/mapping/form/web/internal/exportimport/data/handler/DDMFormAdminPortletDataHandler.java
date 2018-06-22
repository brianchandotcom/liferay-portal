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

package com.liferay.dynamic.data.mapping.form.web.internal.exportimport.data.handler;

import com.liferay.dynamic.data.mapping.constants.DDMConstants;
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	property = "javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN,
	service = PortletDataHandler.class
)
public class DDMFormAdminPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "forms";

	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Activate
	protected void activate() {
		setDataLocalized(true);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(DDMFormInstanceRecord.class),
			new StagedModelType(DDMFormInstance.class));

		PortletDataHandlerControl[] formsPortletDataHandlerControlChildren = {
			new PortletDataHandlerBoolean(
				NAMESPACE, "ddm-data-provider", true, false, null,
				DDMDataProviderInstance.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "form-entries", true, false, null,
				DDMFormInstanceRecord.class.getName())
		};

		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "forms", true, false,
				formsPortletDataHandlerControlChildren,
				DDMFormInstance.class.getName()));

		setStagingControls(getExportControls());
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				DDMFormAdminPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		_formInstanceStagedModelRepository.deleteStagedModels(
			portletDataContext);

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortletPermissions(DDMConstants.RESOURCE_NAME);

		Element rootElement = addExportDataRootElement(portletDataContext);

		if (portletDataContext.getBooleanParameter(NAMESPACE, "forms")) {
			ActionableDynamicQuery formInstanceActionableDynamicQuery =
				_formInstanceStagedModelRepository.
					getExportActionableDynamicQuery(portletDataContext);

			formInstanceActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "form-entries")) {
			ActionableDynamicQuery recordActionableDynamicQuery =
				_formInstanceRecordStagedModelRepository.
					getExportActionableDynamicQuery(portletDataContext);

			recordActionableDynamicQuery.performActions();
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortletPermissions(DDMConstants.RESOURCE_NAME);

		if (portletDataContext.getBooleanParameter(NAMESPACE, "forms")) {
			Element formInstancesElement =
				portletDataContext.getImportDataGroupElement(
					DDMFormInstance.class);

			List<Element> formInstanceElements =
				formInstancesElement.elements();

			for (Element formInstanceElement : formInstanceElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, formInstanceElement);
			}

			Element structuresElement =
				portletDataContext.getImportDataGroupElement(
					DDMStructure.class);

			List<Element> structureElements = structuresElement.elements();

			for (Element structureElement : structureElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, structureElement);
			}

			Element dataProviderInstancesElement =
				portletDataContext.getImportDataGroupElement(
					DDMDataProviderInstance.class);

			List<Element> dataProviderInstanceElements =
				dataProviderInstancesElement.elements();

			for (Element dataProviderInstanceElement :
					dataProviderInstanceElements) {

				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, dataProviderInstanceElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "form-entries")) {
			Element formInstanceRecordsElement =
				portletDataContext.getImportDataGroupElement(
					DDMFormInstanceRecord.class);

			List<Element> formInstanceRecordElements =
				formInstanceRecordsElement.elements();

			for (Element formInstanceRecordElement :
					formInstanceRecordElements) {

				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, formInstanceRecordElement);
			}
		}

		return portletPreferences;
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
					new StagedModelType(DDMFormInstance.class.getName()),
					new StagedModelType(DDMFormInstanceRecord.class.getName())
				});

			return;
		}

		ActionableDynamicQuery formInstanceActionableDynamicQuery =
			_formInstanceStagedModelRepository.getExportActionableDynamicQuery(
				portletDataContext);

		formInstanceActionableDynamicQuery.performCount();

		ActionableDynamicQuery recordActionableDynamicQuery =
			_formInstanceRecordStagedModelRepository.
				getExportActionableDynamicQuery(portletDataContext);

		recordActionableDynamicQuery.performCount();
	}

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord)",
		unbind = "-"
	)
	protected void setDDMFormInstanceRecordStagedModelRepository(
		StagedModelRepository<DDMFormInstanceRecord>
			formInstanceRecordStagedModelRepository) {

		_formInstanceRecordStagedModelRepository =
			formInstanceRecordStagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstance)",
		unbind = "-"
	)
	protected void setDDMFormInstanceStagedModelRepository(
		StagedModelRepository<DDMFormInstance>
			formInstanceStagedModelRepository) {

		_formInstanceStagedModelRepository = formInstanceStagedModelRepository;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private StagedModelRepository<DDMFormInstanceRecord>
		_formInstanceRecordStagedModelRepository;
	private StagedModelRepository<DDMFormInstance>
		_formInstanceStagedModelRepository;

	@Reference
	private Staging _staging;

}