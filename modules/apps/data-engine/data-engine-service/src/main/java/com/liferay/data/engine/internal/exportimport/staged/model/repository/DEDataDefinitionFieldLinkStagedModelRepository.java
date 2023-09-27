/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.internal.exportimport.staged.model.repository;

import com.liferay.data.engine.model.DEDataDefinitionFieldLink;
import com.liferay.data.engine.service.DEDataDefinitionFieldLinkLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "model.class.name=com.liferay.data.engine.model.DEDataDefinitionFieldLink",
	service = StagedModelRepository.class
)
public class DEDataDefinitionFieldLinkStagedModelRepository
	implements StagedModelRepository<DEDataDefinitionFieldLink> {

	@Override
	public DEDataDefinitionFieldLink addStagedModel(
			PortletDataContext portletDataContext,
			DEDataDefinitionFieldLink deDataDefinitionFieldLink)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			deDataDefinitionFieldLink);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(deDataDefinitionFieldLink.getUuid());
		}

		return _deDataDefinitionFieldLinkLocalService.
			addDEDataDefinitionFieldLink(
				deDataDefinitionFieldLink.getGroupId(),
				deDataDefinitionFieldLink.getClassNameId(),
				deDataDefinitionFieldLink.getClassPK(),
				deDataDefinitionFieldLink.getDdmStructureId(),
				deDataDefinitionFieldLink.getFieldName(), serviceContext);
	}

	@Override
	public void deleteStagedModel(
			DEDataDefinitionFieldLink deDataDefinitionFieldLink)
		throws PortalException {

		_deDataDefinitionFieldLinkLocalService.deleteDEDataDefinitionFieldLink(
			deDataDefinitionFieldLink);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		DEDataDefinitionFieldLink deDataDefinitionFieldLink =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (deDataDefinitionFieldLink != null) {
			deleteStagedModel(deDataDefinitionFieldLink);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public DEDataDefinitionFieldLink fetchMissingReference(
		String uuid, long groupId) {

		return (DEDataDefinitionFieldLink)
			_stagedModelRepositoryHelper.fetchMissingReference(
				uuid, groupId, this);
	}

	@Override
	public DEDataDefinitionFieldLink fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _deDataDefinitionFieldLinkLocalService.
			fetchDEDataDefinitionFieldLinkByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<DEDataDefinitionFieldLink> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _deDataDefinitionFieldLinkLocalService.
			getDEDataDefinitionFieldLinksByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _deDataDefinitionFieldLinkLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public DEDataDefinitionFieldLink getStagedModel(long id)
		throws PortalException {

		return _deDataDefinitionFieldLinkLocalService.
			getDEDataDefinitionFieldLink(id);
	}

	@Override
	public DEDataDefinitionFieldLink saveStagedModel(
			DEDataDefinitionFieldLink deDataDefinitionFieldLink)
		throws PortalException {

		return _deDataDefinitionFieldLinkLocalService.
			updateDEDataDefinitionFieldLink(deDataDefinitionFieldLink);
	}

	@Override
	public DEDataDefinitionFieldLink updateStagedModel(
			PortletDataContext portletDataContext,
			DEDataDefinitionFieldLink deDataDefinitionFieldLink)
		throws PortalException {

		return saveStagedModel(deDataDefinitionFieldLink);
	}

	@Reference
	private DEDataDefinitionFieldLinkLocalService
		_deDataDefinitionFieldLinkLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}