/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.exportimport.staged.model.repository;

import com.liferay.dynamic.data.lists.constants.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordSetNameComparator;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
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
	property = "model.class.name=com.liferay.dynamic.data.lists.model.DDLRecordSet",
	service = StagedModelRepository.class
)
public class DDLRecordSetStagedModelRepository
	implements StagedModelRepository<DDLRecordSet> {

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
			DDMStructure ddmStructure =
				_ddmStructureLocalService.fetchDDMStructure(
					recordSet.getDDMStructureId());

			if ((ddmStructure != null) &&
				(ddmStructure.getGroupId() == recordSet.getGroupId())) {

				recordSetDDMStructureIds.add(recordSet.getDDMStructureId());
			}

			_ddlRecordSetLocalService.deleteRecordSet(recordSet);
		}

		_deleteDDMStructures(recordSetDDMStructureIds);
	}

	@Override
	public DDLRecordSet fetchMissingReference(String uuid, long groupId) {
		return _stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
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
		PortletDataContext portletDataContext) {

		return getExportActionableDynamicQuery(
			portletDataContext, DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS);
	}

	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext, int scope) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_ddlRecordSetLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			exportActionableDynamicQuery.getAddCriteriaMethod();

		exportActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				addCriteriaMethod.addCriteria(dynamicQuery);

				Property scopeProperty = PropertyFactoryUtil.forName("scope");

				dynamicQuery.add(scopeProperty.eq(scope));
			});

		exportActionableDynamicQuery.setPerformActionMethod(
			(DDLRecordSet ddlRecordSet) -> {
				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, ddlRecordSet);

				DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, ddmStructure);

				if (scope == DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS) {
					for (DDMTemplate ddmTemplate :
							ddmStructure.getTemplates()) {

						StagedModelDataHandlerUtil.exportStagedModel(
							portletDataContext, ddmTemplate);
					}
				}
			});

		return exportActionableDynamicQuery;
	}

	@Override
	public DDLRecordSet getStagedModel(long recordSetId)
		throws PortalException {

		return _ddlRecordSetLocalService.getDDLRecordSet(recordSetId);
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

	private void _deleteDDMStructures(Set<Long> ddmStructureIds)
		throws PortalException {

		for (Long ddmStructureId : ddmStructureIds) {
			_ddmStructureLocalService.deleteStructure(ddmStructureId);
		}
	}

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}