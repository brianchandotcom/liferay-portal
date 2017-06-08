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

package com.liferay.dynamic.data.lists.internal.exportimport.staged.model.repository;

import com.liferay.dynamic.data.lists.exportimport.staged.model.repository.api.DDLRecordStagedModelRepository;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.dynamic.data.lists.model.DDLRecord"
	},
	service = {
		DDLRecordStagedModelRepository.class, StagedModelRepository.class
	}
)
public class DDLRecordStagedModelRepositoryImpl
	implements DDLRecordStagedModelRepository {

	@Override
	public DDLRecord addStagedModel(
			PortletDataContext portletDataContext, DDLRecord stagedModel)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public DDLRecord addStagedModel(
			PortletDataContext portletDataContext, DDLRecord ddlRecord,
			DDMFormValues ddmFormValues)
		throws PortalException {

		long userId = portletDataContext.getUserId(ddlRecord.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			ddlRecord);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(ddlRecord.getUuid());
		}

		return _ddlRecordLocalService.addRecord(
			userId, ddlRecord.getGroupId(), ddlRecord.getRecordSetId(),
			ddlRecord.getDisplayIndex(), ddmFormValues, serviceContext);
	}

	@Override
	public void deleteStagedModel(DDLRecord ddlRecord) throws PortalException {
		_ddlRecordLocalService.deleteRecord(ddlRecord);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		DDLRecord ddlRrecord = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (ddlRrecord != null) {
			deleteStagedModel(ddlRrecord);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public DDLRecord fetchMissingReference(String uuid, long groupId) {
		return (DDLRecord)_stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public DDLRecord fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _ddlRecordLocalService.fetchDDLRecordByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<DDLRecord> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _ddlRecordLocalService.getDDLRecordsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<DDLRecord>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return getExportActionableDynamicQuery(
			portletDataContext, DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS);
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext, int scope) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_ddlRecordLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		final ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			exportActionableDynamicQuery.getAddCriteriaMethod();

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					addCriteriaMethod.addCriteria(dynamicQuery);

					Property recordIdProperty = PropertyFactoryUtil.forName(
						"recordId");

					DynamicQuery recordVersionDynamicQuery =
						_getRecordVersionDynamicQuery();

					dynamicQuery.add(
						recordIdProperty.in(recordVersionDynamicQuery));

					Property recordSetIdProperty = PropertyFactoryUtil.forName(
						"recordSetId");

					DynamicQuery recordSetDynamicQuery =
						_getRecordSetDynamicQuery(scope);

					dynamicQuery.add(
						recordSetIdProperty.in(recordSetDynamicQuery));
				}

			});

		return exportActionableDynamicQuery;
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, DDLRecord stagedModel)
		throws PortletDataException {
	}

	@Override
	public DDLRecord saveStagedModel(DDLRecord ddlRecord)
		throws PortalException {

		return _ddlRecordLocalService.updateDDLRecord(ddlRecord);
	}

	@Override
	public DDLRecord updateStagedModel(
			PortletDataContext portletDataContext, DDLRecord stagedModel)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public DDLRecord updateStagedModel(
			PortletDataContext portletDataContext, DDLRecord ddlRecord,
			DDMFormValues ddmFormValues)
		throws PortalException {

		long userId = portletDataContext.getUserId(ddlRecord.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			ddlRecord);

		return _ddlRecordLocalService.updateRecord(
			userId, ddlRecord.getRecordId(), false, ddlRecord.getDisplayIndex(),
			ddmFormValues, serviceContext);
	}

	private DynamicQuery _getRecordSetDynamicQuery(int scope) {
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

		recordSetDynamicQuery.add(scopeProperty.eq(scope));

		return recordSetDynamicQuery;
	}

	private DynamicQuery _getRecordVersionDynamicQuery() {
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

	@Reference
	private DDLRecordLocalService _ddlRecordLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}