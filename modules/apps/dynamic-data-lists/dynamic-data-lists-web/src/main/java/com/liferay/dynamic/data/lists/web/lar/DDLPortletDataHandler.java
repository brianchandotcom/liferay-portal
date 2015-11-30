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

package com.liferay.dynamic.data.lists.web.lar;

import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.lists.service.permission.DDLPermission;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.exportimport.lar.BasePortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerBoolean;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = {"javax.portlet.name=" + DDLPortletKeys.DYNAMIC_DATA_LISTS},
	service = PortletDataHandler.class
)
public class DDLPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "dynamic_data_lists";

	@Activate
	protected void activate() {
		setDataLocalized(true);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(DDLRecord.class),
			new StagedModelType(DDLRecordSet.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "record-sets", true, false, null,
				DDLRecordSet.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "records", true, false, null,
				DDLRecord.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "data-definitions", true, false, null,
				DDMStructure.class.getName(), DDLRecordSet.class.getName()));
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				DDLPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		_ddlRecordSetLocalService.deleteRecordSets(
			portletDataContext.getScopeGroupId());

		_ddmStructureLocalService.deleteStructures(
			portletDataContext.getScopeGroupId(),
			PortalUtil.getClassNameId(DDLRecordSet.class));

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortletPermissions(DDLPermission.RESOURCE_NAME);

		Element rootElement = addExportDataRootElement(portletDataContext);

		if (portletDataContext.getBooleanParameter(
				NAMESPACE, "data-definitions")) {

			List<DDMTemplate> ddmTemplates = new ArrayList<>();

			ActionableDynamicQuery ddmStructureActionableDynamicQuery =
				getDDMStructureActionableDynamicQuery(
					portletDataContext, ddmTemplates);

			ddmStructureActionableDynamicQuery.performActions();

			for (DDMTemplate ddmTemplate : ddmTemplates) {
				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, ddmTemplate);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "record-sets")) {
			ActionableDynamicQuery recordSetActionableDynamicQuery =
				_ddlRecordSetLocalService.getExportActionableDynamicQuery(
					portletDataContext);

			recordSetActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "records")) {
			ActionableDynamicQuery recordActionableDynamicQuery =
				getRecordActionableDynamicQuery(portletDataContext);

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

		if (portletDataContext.getBooleanParameter(
				NAMESPACE, "data-definitions")) {

			Element ddmStructuresElement =
				portletDataContext.getImportDataGroupElement(
					DDMStructure.class);

			List<Element> ddmStructureElements =
				ddmStructuresElement.elements();

			for (Element ddmStructureElement : ddmStructureElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, ddmStructureElement);
			}

			Element ddmTemplatesElement =
				portletDataContext.getImportDataGroupElement(DDMTemplate.class);

			List<Element> ddmTemplateElements = ddmTemplatesElement.elements();

			for (Element ddmTemplateElement : ddmTemplateElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, ddmTemplateElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "record-sets")) {
			Element recordSetsElement =
				portletDataContext.getImportDataGroupElement(
					DDLRecordSet.class);

			List<Element> recordSetElements = recordSetsElement.elements();

			for (Element recordSetElement : recordSetElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, recordSetElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "records")) {
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

		List<DDMTemplate> ddmTemplates = new ArrayList<>();

		ActionableDynamicQuery ddmStructureActionableDynamicQuery =
			getDDMStructureActionableDynamicQuery(
				portletDataContext, ddmTemplates);

		ddmStructureActionableDynamicQuery.performCount();

		ActionableDynamicQuery recordSetActionableDynamicQuery =
			_ddlRecordSetLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		recordSetActionableDynamicQuery.performCount();

		ActionableDynamicQuery recordActionableDynamicQuery =
			getRecordActionableDynamicQuery(portletDataContext);

		recordActionableDynamicQuery.performCount();
	}

	protected ActionableDynamicQuery getDDMStructureActionableDynamicQuery(
		final PortletDataContext portletDataContext,
		final List<DDMTemplate> ddmTemplates) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_ddmStructureLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		final ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			exportActionableDynamicQuery.getAddCriteriaMethod();

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					addCriteriaMethod.addCriteria(dynamicQuery);

					Property classNameIdProperty = PropertyFactoryUtil.forName(
						"classNameId");

					long classNameId = PortalUtil.getClassNameId(
						DDLRecordSet.class);

					dynamicQuery.add(classNameIdProperty.eq(classNameId));
				}

			});
		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<DDMStructure>() {

				@Override
				public void performAction(DDMStructure ddmStructure)
					throws PortalException {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, ddmStructure);

					ddmTemplates.addAll(ddmStructure.getTemplates());
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				DDMStructure.class.getName(), DDLRecordSet.class.getName()));

		return exportActionableDynamicQuery;
	}

	protected ActionableDynamicQuery getRecordActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		ActionableDynamicQuery actionableDynamicQuery =
			_ddlRecordLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		final ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			actionableDynamicQuery.getAddCriteriaMethod();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					addCriteriaMethod.addCriteria(dynamicQuery);

					Property recordIdProperty = PropertyFactoryUtil.forName(
						"recordId");

					StagedModelDataHandler<?> stagedModelDataHandler =
						StagedModelDataHandlerRegistryUtil.
							getStagedModelDataHandler(
								DDLRecord.class.getName());

					Class<?> clazz = stagedModelDataHandler.getClass();

					DynamicQuery recordVersionDynamicQuery =
						DynamicQueryFactoryUtil.forClass(
							DDLRecordVersion.class, "recordVersion",
							clazz.getClassLoader());

					recordVersionDynamicQuery.setProjection(
						ProjectionFactoryUtil.property("recordId"));

					Property statusProperty = PropertyFactoryUtil.forName(
						"status");

					recordVersionDynamicQuery.add(
						statusProperty.in(
							stagedModelDataHandler.getExportableStatuses()));

					recordVersionDynamicQuery.add(
						RestrictionsFactoryUtil.eqProperty(
							"recordVersion.version", "version"));

					recordVersionDynamicQuery.add(
						RestrictionsFactoryUtil.eqProperty(
							"recordVersion.recordId", "recordId"));

					dynamicQuery.add(
						recordIdProperty.in(recordVersionDynamicQuery));
				}

			});

		return actionableDynamicQuery;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordLocalService(
		DDLRecordLocalService ddlRecordLocalService) {

		_ddlRecordLocalService = ddlRecordLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordSetLocalService(
		DDLRecordSetLocalService ddlRecordSetLocalService) {

		_ddlRecordSetLocalService = ddlRecordSetLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private volatile DDLRecordLocalService _ddlRecordLocalService;
	private volatile DDLRecordSetLocalService _ddlRecordSetLocalService;
	private volatile DDMStructureLocalService _ddmStructureLocalService;

}