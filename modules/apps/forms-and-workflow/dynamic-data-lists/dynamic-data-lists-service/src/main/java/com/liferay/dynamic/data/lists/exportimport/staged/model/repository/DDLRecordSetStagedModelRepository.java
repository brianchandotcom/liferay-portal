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

package com.liferay.dynamic.data.lists.exportimport.staged.model.repository;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordSetNameComparator;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tamas Molnar
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.dynamic.data.lists.model.DDLRecordSet"
	},
	service =
		{DDLRecordSetStagedModelRepository.class, StagedModelRepository.class}
)
public class DDLRecordSetStagedModelRepository
	extends BaseStagedModelRepository<DDLRecordSet> {

	@Override
	public DDLRecordSet addStagedModel(
			PortletDataContext portletDataContext, DDLRecordSet ddlRecordSet)
		throws PortalException {

		long userId = portletDataContext.getUserId(ddlRecordSet.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			ddlRecordSet);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(ddlRecordSet.getUuid());
		}
		else {
			ddlRecordSet.setRecordSetKey(null);
		}

		return _ddlRecordSetLocalService.addRecordSet(
			userId, ddlRecordSet.getGroupId(), ddlRecordSet.getDDMStructureId(),
			ddlRecordSet.getRecordSetKey(), ddlRecordSet.getNameMap(),
			ddlRecordSet.getDescriptionMap(), ddlRecordSet.getMinDisplayRows(),
			ddlRecordSet.getScope(), serviceContext);
	}

	@Override
	public void deleteStagedModel(DDLRecordSet ddlRecordSet)
		throws PortalException {

		_ddlRecordSetLocalService.deleteRecordSet(ddlRecordSet);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		DDLRecordSet ddlRecordSet = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (ddlRecordSet != null) {
			deleteStagedModel(ddlRecordSet);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		deleteStagedModels(
			portletDataContext, DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS);
	}

	public void deleteStagedModels(
			PortletDataContext portletDataContext, int scope)
		throws PortalException {

		Set<Long> recordSetDDMStructureIds = new HashSet<>();

		List<DDLRecordSet> recordSets = _ddlRecordSetLocalService.search(
			portletDataContext.getCompanyId(),
			portletDataContext.getScopeGroupId(), null, scope,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new DDLRecordSetNameComparator());

		for (DDLRecordSet recordSet : recordSets) {
			recordSetDDMStructureIds.add(recordSet.getDDMStructureId());

			_ddlRecordSetLocalService.deleteRecordSet(recordSet);
		}

		deleteDDMStructures(recordSetDDMStructureIds);
	}

	@Override
	public DDLRecordSet fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _ddlRecordSetLocalService.fetchDDLRecordSetByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<DDLRecordSet> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _ddlRecordSetLocalService.getDDLRecordSetsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<DDLRecordSet>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		return getExportActionableDynamicQuery(
			portletDataContext, DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS);
	}

	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext, final int scope) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_ddlRecordSetLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		final ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			exportActionableDynamicQuery.getAddCriteriaMethod();

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					addCriteriaMethod.addCriteria(dynamicQuery);

					Property scopeProperty = PropertyFactoryUtil.forName(
						"scope");

					dynamicQuery.add(scopeProperty.eq(scope));
				}

			});

		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<DDLRecordSet>() {

				@Override
				public void performAction(DDLRecordSet ddlRecordSet)
					throws PortalException {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, ddlRecordSet);

					DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, ddmStructure);

					if (scope ==
							DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS) {

						for (DDMTemplate ddmTemplate :
								ddmStructure.getTemplates()) {

							StagedModelDataHandlerUtil.exportStagedModel(
								portletDataContext, ddmTemplate);
						}
					}
				}

			});

		return exportActionableDynamicQuery;
	}

	@Override
	public DDLRecordSet saveStagedModel(DDLRecordSet ddlRecordSet)
		throws PortalException {

		return _ddlRecordSetLocalService.updateDDLRecordSet(ddlRecordSet);
	}

	@Override
	public DDLRecordSet updateStagedModel(
			PortletDataContext portletDataContext, DDLRecordSet ddlRecordSet)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			ddlRecordSet);

		return _ddlRecordSetLocalService.updateRecordSet(
			ddlRecordSet.getRecordSetId(), ddlRecordSet.getDDMStructureId(),
			ddlRecordSet.getNameMap(), ddlRecordSet.getDescriptionMap(),
			ddlRecordSet.getMinDisplayRows(), serviceContext);
	}

	protected void deleteDDMStructures(Set<Long> ddmStructureIds)
		throws PortalException {

		for (Long ddmStructureId : ddmStructureIds) {
			_ddmStructureLocalService.deleteStructure(ddmStructureId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDLRecordSetStagedModelRepository.class);

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

}