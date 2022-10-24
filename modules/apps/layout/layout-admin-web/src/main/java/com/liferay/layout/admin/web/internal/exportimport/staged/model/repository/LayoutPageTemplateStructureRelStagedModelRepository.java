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

package com.liferay.layout.admin.web.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel",
	service = StagedModelRepository.class
)
public class LayoutPageTemplateStructureRelStagedModelRepository
	implements StagedModelRepository<LayoutPageTemplateStructureRel> {

	@Override
	public LayoutPageTemplateStructureRel addStagedModel(
			PortletDataContext portletDataContext,
			LayoutPageTemplateStructureRel layoutPageTemplateStructureRel)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			layoutPageTemplateStructureRel.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			layoutPageTemplateStructureRel);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(layoutPageTemplateStructureRel.getUuid());
		}

		return _layoutPageTemplateStructureRelLocalService.
			addLayoutPageTemplateStructureRel(
				userId, serviceContext.getScopeGroupId(),
				layoutPageTemplateStructureRel.
					getLayoutPageTemplateStructureId(),
				layoutPageTemplateStructureRel.getSegmentsExperienceId(),
				layoutPageTemplateStructureRel.getData(), serviceContext);
	}

	@Override
	public void deleteStagedModel(
			LayoutPageTemplateStructureRel layoutPageTemplateStructureRel)
		throws PortalException {

		_layoutPageTemplateStructureRelLocalService.
			deleteLayoutPageTemplateStructureRel(
				layoutPageTemplateStructureRel);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (layoutPageTemplateStructureRel != null) {
			deleteStagedModel(layoutPageTemplateStructureRel);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public LayoutPageTemplateStructureRel fetchMissingReference(
		String uuid, long groupId) {

		return (LayoutPageTemplateStructureRel)
			_stagedModelRepositoryHelper.fetchMissingReference(
				uuid, groupId, this);
	}

	@Override
	public LayoutPageTemplateStructureRel fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _layoutPageTemplateStructureRelLocalService.
			fetchLayoutPageTemplateStructureRelByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<LayoutPageTemplateStructureRel>
		fetchStagedModelsByUuidAndCompanyId(String uuid, long companyId) {

		return _layoutPageTemplateStructureRelLocalService.
			getLayoutPageTemplateStructureRelsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _layoutPageTemplateStructureRelLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public LayoutPageTemplateStructureRel getStagedModel(long id)
		throws PortalException {

		return _layoutPageTemplateStructureRelLocalService.
			getLayoutPageTemplateStructureRel(id);
	}

	@Override
	public LayoutPageTemplateStructureRel saveStagedModel(
			LayoutPageTemplateStructureRel layoutPageTemplateStructureRel)
		throws PortalException {

		return _layoutPageTemplateStructureRelLocalService.
			updateLayoutPageTemplateStructureRel(
				layoutPageTemplateStructureRel);
	}

	@Override
	public LayoutPageTemplateStructureRel updateStagedModel(
			PortletDataContext portletDataContext,
			LayoutPageTemplateStructureRel layoutPageTemplateStructureRel)
		throws PortalException {

		return _layoutPageTemplateStructureRelLocalService.
			updateLayoutPageTemplateStructureRel(
				layoutPageTemplateStructureRel);
	}

	@Reference
	private LayoutPageTemplateStructureRelLocalService
		_layoutPageTemplateStructureRelLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}