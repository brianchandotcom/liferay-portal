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

package com.liferay.dynamic.data.lists.form.web.internal.exportimport.data.handler;

import com.liferay.dynamic.data.lists.exportimport.staged.model.repository.DDLRecordSetStagedModelRepository;
import com.liferay.dynamic.data.lists.exportimport.staged.model.repository.DDLRecordStagedModelRepository;
import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.lists.service.permission.DDLPermission;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
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
	property = {"javax.portlet.name=" + DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN},
	service = PortletDataHandler.class
)
public class DDLFormAdminPortletDataHandler extends BasePortletDataHandler {

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
			new StagedModelType(DDLRecord.class),
			new StagedModelType(DDLRecordSet.class));

		PortletDataHandlerControl[] formsPortletDataHandlerControlChildren =
			new PortletDataHandlerControl[] {
				new PortletDataHandlerBoolean(
					NAMESPACE, "ddm-data-provider", true, false, null,
					DDMDataProviderInstance.class.getName())
			};

		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "forms", true, false,
				formsPortletDataHandlerControlChildren,
				DDLRecordSet.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "form-entries", true, false, null,
				DDLRecord.class.getName()));
	}

	/**
	 * @deprecated As of 1.1.0
	 */
	@Deprecated
	protected DynamicQuery createRecordSetDynamicQuery() {
		return null;
	}

	/**
	 * @deprecated As of 1.1.0
	 */
	@Deprecated
	protected DynamicQuery createRecordVersionDynamicQuery() {
		return null;
	}

	/**
	 * @deprecated As of 1.1.0
	 */
	@Deprecated
	protected void deleteRecordSets(PortletDataContext portletDataContext)
		throws PortalException {

		_ddlRecordSetStagedModelRepository.deleteStagedModels(
			portletDataContext, DDLRecordSetConstants.SCOPE_FORMS);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				DDLFormAdminPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		_ddlRecordSetStagedModelRepository.deleteStagedModels(
			portletDataContext, DDLRecordSetConstants.SCOPE_FORMS);

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortletPermissions(DDLPermission.RESOURCE_NAME);

		Element rootElement = addExportDataRootElement(portletDataContext);

		if (portletDataContext.getBooleanParameter(NAMESPACE, "forms")) {
			ActionableDynamicQuery recordSetActionableDynamicQuery =
				_ddlRecordSetStagedModelRepository.
					getExportActionableDynamicQuery(
						portletDataContext, DDLRecordSetConstants.SCOPE_FORMS);

			recordSetActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "form-entries")) {
			ActionableDynamicQuery recordActionableDynamicQuery =
				_ddlRecordStagedModelRepository.getExportActionableDynamicQuery(
					portletDataContext, DDLRecordSetConstants.SCOPE_FORMS);

			recordActionableDynamicQuery.performActions();
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortletPermissions(
			DDLPermission.RESOURCE_NAME);

		if (portletDataContext.getBooleanParameter(NAMESPACE, "forms")) {
			Element recordSetsElement =
				portletDataContext.getImportDataGroupElement(
					DDLRecordSet.class);

			List<Element> recordSetElements = recordSetsElement.elements();

			for (Element recordSetElement : recordSetElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, recordSetElement);
			}

			Element ddmStructuresElement =
				portletDataContext.getImportDataGroupElement(
					DDMStructure.class);

			List<Element> ddmStructureElements =
				ddmStructuresElement.elements();

			for (Element ddmStructureElement : ddmStructureElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, ddmStructureElement);
			}

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
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "form-entries")) {
			Element recordsElement =
				portletDataContext.getImportDataGroupElement(DDLRecord.class);

			List<Element> recordElements = recordsElement.elements();

			for (Element recordElement : recordElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, recordElement);
			}
		}

		return portletPreferences;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ActionableDynamicQuery recordSetActionableDynamicQuery =
			_ddlRecordSetStagedModelRepository.getExportActionableDynamicQuery(
				portletDataContext, DDLRecordSetConstants.SCOPE_FORMS);

		recordSetActionableDynamicQuery.performCount();

		ActionableDynamicQuery recordActionableDynamicQuery =
			_ddlRecordStagedModelRepository.getExportActionableDynamicQuery(
				portletDataContext, DDLRecordSetConstants.SCOPE_FORMS);

		recordActionableDynamicQuery.performCount();
	}

	/**
	 * @deprecated As of 1.1.0
	 */
	@Deprecated
	protected ActionableDynamicQuery getRecordActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		return _ddlRecordStagedModelRepository.getExportActionableDynamicQuery(
			portletDataContext, DDLRecordSetConstants.SCOPE_FORMS);
	}

	/**
	 * @deprecated As of 1.1.0
	 */
	@Deprecated
	protected ActionableDynamicQuery getRecordSetActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		return _ddlRecordSetStagedModelRepository.
			getExportActionableDynamicQuery(
				portletDataContext, DDLRecordSetConstants.SCOPE_FORMS);
	}

	/**
	 * @deprecated As of 1.1.0
	 */
	@Deprecated
	protected void setDDLRecordLocalService(
		DDLRecordLocalService ddlRecordLocalService) {
	}

	/**
	 * @deprecated As of 1.1.0
	 */
	@Deprecated
	protected void setDDLRecordSetLocalService(
		DDLRecordSetLocalService ddlRecordSetLocalService) {
	}

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.lists.model.DDLRecordSet)",
		unbind = "-"
	)
	protected void setDDLRecordSetStagedModelRepository(
		DDLRecordSetStagedModelRepository ddlRecordSetStagedModelRepository) {

		_ddlRecordSetStagedModelRepository = ddlRecordSetStagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.lists.model.DDLRecord)",
		unbind = "-"
	)
	protected void setDDLRecordStagedModelRepository(
		DDLRecordStagedModelRepository ddlRecordStagedModelRepository) {

		_ddlRecordStagedModelRepository = ddlRecordStagedModelRepository;
	}

	/**
	 * @deprecated As of 1.1.0
	 */
	@Deprecated
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private DDLRecordSetStagedModelRepository
		_ddlRecordSetStagedModelRepository;
	private DDLRecordStagedModelRepository _ddlRecordStagedModelRepository;

}