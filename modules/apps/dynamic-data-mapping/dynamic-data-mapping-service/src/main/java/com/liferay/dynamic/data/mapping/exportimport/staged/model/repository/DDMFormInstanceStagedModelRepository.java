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

package com.liferay.dynamic.data.mapping.exportimport.staged.model.repository;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.comparator.DDMFormInstanceNameComparator;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
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
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstance",
	service = {
		DDMFormInstanceStagedModelRepository.class, StagedModelRepository.class
	}
)
public class DDMFormInstanceStagedModelRepository
	implements StagedModelRepository<DDMFormInstance> {

	@Override
	public DDMFormInstance addStagedModel(
			PortletDataContext portletDataContext,
			DDMFormInstance ddmFormInstance)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			ddmFormInstance.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			ddmFormInstance);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(ddmFormInstance.getUuid());
		}

		return _ddmFormInstanceLocalService.addFormInstance(
			userId, ddmFormInstance.getGroupId(),
			ddmFormInstance.getStructureId(), ddmFormInstance.getNameMap(),
			ddmFormInstance.getDescriptionMap(), ddmFormInstance.getSettings(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(DDMFormInstance ddmFormInstance)
		throws PortalException {

		_ddmFormInstanceLocalService.deleteFormInstance(ddmFormInstance);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		DDMFormInstance ddmFormInstance = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (ddmFormInstance != null) {
			deleteStagedModel(ddmFormInstance);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		Set<Long> formInstanceDDMStructureIds = new HashSet<>();

		List<DDMFormInstance> formInstances =
			_ddmFormInstanceLocalService.search(
				portletDataContext.getCompanyId(),
				portletDataContext.getScopeGroupId(), null, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new DDMFormInstanceNameComparator());

		for (DDMFormInstance formInstance : formInstances) {
			formInstanceDDMStructureIds.add(formInstance.getStructureId());

			_ddmFormInstanceLocalService.deleteFormInstance(formInstance);
		}

		deleteDDMStructures(formInstanceDDMStructureIds);
	}

	@Override
	public DDMFormInstance fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _ddmFormInstanceLocalService.
			fetchDDMFormInstanceByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<DDMFormInstance> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _ddmFormInstanceLocalService.
			getDDMFormInstancesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<DDMFormInstance>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_ddmFormInstanceLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<DDMFormInstance>() {

				@Override
				public void performAction(DDMFormInstance ddmFormInstance)
					throws PortalException {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, ddmFormInstance);

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, ddmFormInstance.getStructure());
				}

			});

		return exportActionableDynamicQuery;
	}

	@Override
	public DDMFormInstance saveStagedModel(DDMFormInstance ddmFormInstance)
		throws PortalException {

		return _ddmFormInstanceLocalService.updateDDMFormInstance(
			ddmFormInstance);
	}

	@Override
	public DDMFormInstance updateStagedModel(
			PortletDataContext portletDataContext,
			DDMFormInstance ddmFormInstance)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			ddmFormInstance);

		return _ddmFormInstanceLocalService.updateFormInstance(
			ddmFormInstance.getFormInstanceId(),
			ddmFormInstance.getStructureId(), ddmFormInstance.getNameMap(),
			ddmFormInstance.getDescriptionMap(), ddmFormInstance.getSettings(),
			serviceContext);
	}

	protected void deleteDDMStructures(Set<Long> ddmStructureIds)
		throws PortalException {

		for (Long ddmStructureId : ddmStructureIds) {
			_ddmStructureLocalService.deleteStructure(ddmStructureId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceStagedModelRepository.class);

	@Reference
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

}