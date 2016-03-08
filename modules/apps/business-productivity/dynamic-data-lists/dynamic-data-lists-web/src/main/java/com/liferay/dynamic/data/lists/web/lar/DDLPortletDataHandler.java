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
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.lists.service.permission.DDLPermission;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordSetNameComparator;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.xml.Element;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "record-sets", true, false, null,
				DDLRecordSet.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "records", true, false, null,
				DDLRecord.class.getName()));
	}

	protected DynamicQuery createRecordSetDynamicQuery() {
		StagedModelDataHandler<?> stagedModelDataHandler =
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
				DDLRecord.class.getName());

		Class<?> clazz = stagedModelDataHandler.getClass();

		DynamicQuery recordSetDynamicQuery = DynamicQueryFactoryUtil.forClass(
			DDLRecordSet.class, "recordSet", clazz.getClassLoader());

		recordSetDynamicQuery.setProjection(
			ProjectionFactoryUtil.property("recordSetId"));

		recordSetDynamicQuery.add(
			RestrictionsFactoryUtil.eqProperty(
				"recordSet.recordSetId", "recordSetId"));

		Property scopeProperty = PropertyFactoryUtil.forName("scope");

		recordSetDynamicQuery.add(
			scopeProperty.eq(DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS));

		return recordSetDynamicQuery;
	}

	protected DynamicQuery createRecordVersionDynamicQuery() {
		StagedModelDataHandler<?> stagedModelDataHandler =
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
				DDLRecord.class.getName());

		Class<?> clazz = stagedModelDataHandler.getClass();

		DynamicQuery recordVersionDynamicQuery =
			DynamicQueryFactoryUtil.forClass(
				DDLRecordVersion.class, "recordVersion",
				clazz.getClassLoader());

		recordVersionDynamicQuery.setProjection(
			ProjectionFactoryUtil.property("recordId"));

		Property statusProperty = PropertyFactoryUtil.forName("status");

		recordVersionDynamicQuery.add(
			statusProperty.in(stagedModelDataHandler.getExportableStatuses()));

		recordVersionDynamicQuery.add(
			RestrictionsFactoryUtil.eqProperty(
				"recordVersion.version", "version"));

		recordVersionDynamicQuery.add(
			RestrictionsFactoryUtil.eqProperty(
				"recordVersion.recordId", "recordId"));

		return recordVersionDynamicQuery;
	}

	protected void deleteDDMStructures(Set<Long> ddmStructureIds)
		throws PortalException {

		for (Long ddmStructureId : ddmStructureIds) {
			_ddmStructureLocalService.deleteStructure(ddmStructureId);
		}
	}

	protected void deleteRecordSets(PortletDataContext portletDataContext)
		throws PortalException {

		Set<Long> recordSetDDMStructureIds = new HashSet<>();

		List<DDLRecordSet> recordSets = _ddlRecordSetLocalService.search(
			portletDataContext.getCompanyId(),
			portletDataContext.getScopeGroupId(), null,
			DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new DDLRecordSetNameComparator());

		for (DDLRecordSet recordSet : recordSets) {
			recordSetDDMStructureIds.add(recordSet.getDDMStructureId());

			_ddlRecordSetLocalService.deleteRecordSet(recordSet);
		}

		deleteDDMStructures(recordSetDDMStructureIds);
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

		deleteRecordSets(portletDataContext);

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortletPermissions(DDLPermission.RESOURCE_NAME);

		Element rootElement = addExportDataRootElement(portletDataContext);

		if (portletDataContext.getBooleanParameter(NAMESPACE, "record-sets")) {
			ActionableDynamicQuery recordSetActionableDynamicQuery =
				getRecordSetActionableDynamicQuery(portletDataContext);

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

		if (portletDataContext.getBooleanParameter(NAMESPACE, "record-sets")) {
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

			Element ddmTemplatesElement =
				portletDataContext.getImportDataGroupElement(DDMTemplate.class);

			List<Element> ddmTemplateElements = ddmTemplatesElement.elements();

			for (Element ddmTemplateElement : ddmTemplateElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, ddmTemplateElement);
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

		ActionableDynamicQuery recordSetActionableDynamicQuery =
			getRecordSetActionableDynamicQuery(portletDataContext);

		recordSetActionableDynamicQuery.performCount();

		ActionableDynamicQuery recordActionableDynamicQuery =
			getRecordActionableDynamicQuery(portletDataContext);

		recordActionableDynamicQuery.performCount();
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

					DynamicQuery recordVersionDynamicQuery =
						createRecordVersionDynamicQuery();

					dynamicQuery.add(
						recordIdProperty.in(recordVersionDynamicQuery));

					Property recordSetIdProperty = PropertyFactoryUtil.forName(
						"recordSetId");

					DynamicQuery recordSetDynamicQuery =
						createRecordSetDynamicQuery();

					dynamicQuery.add(
						recordSetIdProperty.in(recordSetDynamicQuery));
				}

			});

		return actionableDynamicQuery;
	}

	protected ActionableDynamicQuery getRecordSetActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		ActionableDynamicQuery actionableDynamicQuery =
			_ddlRecordSetLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		final ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			actionableDynamicQuery.getAddCriteriaMethod();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					addCriteriaMethod.addCriteria(dynamicQuery);

					Property scopeProperty = PropertyFactoryUtil.forName(
						"scope");

					dynamicQuery.add(
						scopeProperty.eq(
							DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS));
				}

			});
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<DDLRecordSet>() {

				@Override
				public void performAction(DDLRecordSet ddlRecordSet)
					throws PortalException {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, ddlRecordSet);

					DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, ddmStructure);

					for (DDMTemplate ddmTemplate :
							ddmStructure.getTemplates()) {

						StagedModelDataHandlerUtil.exportStagedModel(
							portletDataContext, ddmTemplate);
					}
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

	private DDLRecordLocalService _ddlRecordLocalService;
	private DDLRecordSetLocalService _ddlRecordSetLocalService;
	private DDMStructureLocalService _ddmStructureLocalService;

}