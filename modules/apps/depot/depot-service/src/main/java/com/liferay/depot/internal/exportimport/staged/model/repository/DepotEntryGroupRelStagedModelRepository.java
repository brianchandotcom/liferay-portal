/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.exportimport.staged.model.repository;

import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "model.class.name=com.liferay.depot.model.DepotEntryGroupRel",
	service = StagedModelRepository.class
)
public class DepotEntryGroupRelStagedModelRepository
	implements StagedModelRepository<DepotEntryGroupRel> {

	@Override
	public DepotEntryGroupRel addStagedModel(
		PortletDataContext portletDataContext,
		DepotEntryGroupRel depotEntryGroupRel) {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			depotEntryGroupRel);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(depotEntryGroupRel.getUuid());
		}

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			return _depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				depotEntryGroupRel.isDdmStructuresAvailable(),
				depotEntryGroupRel.getDepotEntryId(),
				depotEntryGroupRel.getToGroupId(),
				depotEntryGroupRel.isSearchable());
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Override
	public void deleteStagedModel(DepotEntryGroupRel depotEntryGroupRel) {
		_depotEntryGroupRelLocalService.deleteDepotEntryGroupRel(
			depotEntryGroupRel);
	}

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData) {

		DepotEntryGroupRel depotEntryGroupRel =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (depotEntryGroupRel != null) {
			deleteStagedModel(depotEntryGroupRel);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DepotEntryGroupRel fetchMissingReference(String uuid, long groupId) {
		return _stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public DepotEntryGroupRel fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _depotEntryGroupRelLocalService.
			fetchDepotEntryGroupRelByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<DepotEntryGroupRel> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _depotEntryGroupRelLocalService.
			getDepotEntryGroupRelsByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _depotEntryGroupRelLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public DepotEntryGroupRel getStagedModel(long depotEntryGroupRelId)
		throws PortalException {

		return _depotEntryGroupRelLocalService.getDepotEntryGroupRel(
			depotEntryGroupRelId);
	}

	@Override
	public DepotEntryGroupRel saveStagedModel(
		DepotEntryGroupRel depotEntryGroupRel) {

		return _depotEntryGroupRelLocalService.updateDepotEntryGroupRel(
			depotEntryGroupRel);
	}

	@Override
	public DepotEntryGroupRel updateStagedModel(
		PortletDataContext portletDataContext,
		DepotEntryGroupRel depotEntryGroupRel) {

		return _depotEntryGroupRelLocalService.updateDepotEntryGroupRel(
			depotEntryGroupRel);
	}

	@Reference
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}