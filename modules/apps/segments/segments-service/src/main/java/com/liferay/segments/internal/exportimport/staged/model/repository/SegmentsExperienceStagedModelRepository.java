/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "model.class.name=com.liferay.segments.model.SegmentsExperience",
	service = StagedModelRepository.class
)
public class SegmentsExperienceStagedModelRepository
	implements StagedModelRepository<SegmentsExperience> {

	@Override
	public SegmentsExperience addStagedModel(
			PortletDataContext portletDataContext,
			SegmentsExperience segmentsExperience)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			segmentsExperience);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(segmentsExperience.getUuid());
		}

		return _segmentsExperienceLocalService.addSegmentsExperience(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			segmentsExperience.getSegmentsEntryId(),
			segmentsExperience.getSegmentsExperienceKey(),
			segmentsExperience.getPlid(), segmentsExperience.getNameMap(),
			segmentsExperience.getPriority(), segmentsExperience.isActive(),
			segmentsExperience.getTypeSettingsUnicodeProperties(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(SegmentsExperience segmentsExperience)
		throws PortalException {

		_segmentsExperienceLocalService.deleteSegmentsExperience(
			segmentsExperience);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (segmentsExperience != null) {
			deleteStagedModel(segmentsExperience);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public SegmentsExperience fetchMissingReference(String uuid, long groupId) {
		return (SegmentsExperience)
			_stagedModelRepositoryHelper.fetchMissingReference(
				uuid, groupId, this);
	}

	@Override
	public SegmentsExperience fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _segmentsExperienceLocalService.
			fetchSegmentsExperienceByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<SegmentsExperience> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _segmentsExperienceLocalService.
			getSegmentsExperiencesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _segmentsExperienceLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public SegmentsExperience getStagedModel(long id) throws PortalException {
		return _segmentsExperienceLocalService.getSegmentsExperience(id);
	}

	@Override
	public SegmentsExperience saveStagedModel(
			SegmentsExperience segmentsExperience)
		throws PortalException {

		return _segmentsExperienceLocalService.updateSegmentsExperience(
			segmentsExperience);
	}

	@Override
	public SegmentsExperience updateStagedModel(
			PortletDataContext portletDataContext,
			SegmentsExperience segmentsExperience)
		throws PortalException {

		return _segmentsExperienceLocalService.updateSegmentsExperience(
			segmentsExperience);
	}

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}