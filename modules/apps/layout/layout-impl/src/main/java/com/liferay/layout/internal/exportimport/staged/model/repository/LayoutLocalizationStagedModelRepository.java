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

package com.liferay.layout.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.layout.model.LayoutLocalization;
import com.liferay.layout.service.LayoutLocalizationLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "model.class.name=com.liferay.layout.model.LayoutLocalization",
	service = StagedModelRepository.class
)
public class LayoutLocalizationStagedModelRepository
	implements StagedModelRepository<LayoutLocalization> {

	@Override
	public LayoutLocalization addStagedModel(
			PortletDataContext portletDataContext,
			LayoutLocalization layoutLocalization)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			layoutLocalization);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(layoutLocalization.getUuid());
		}

		return _layoutLocalizationLocalService.addLayoutLocalization(
			serviceContext.getScopeGroupId(), layoutLocalization.getContent(),
			layoutLocalization.getLanguageId(), layoutLocalization.getPlid(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(LayoutLocalization layoutLocalization)
		throws PortalException {

		_layoutLocalizationLocalService.deleteLayoutLocalization(
			layoutLocalization);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		LayoutLocalization layoutLocalization =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (layoutLocalization != null) {
			deleteStagedModel(layoutLocalization);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public LayoutLocalization fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _layoutLocalizationLocalService.
			fetchLayoutLocalizationByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<LayoutLocalization> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _layoutLocalizationLocalService.
			getLayoutLocalizationsByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _layoutLocalizationLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public LayoutLocalization saveStagedModel(
			LayoutLocalization layoutLocalization)
		throws PortalException {

		return _layoutLocalizationLocalService.updateLayoutLocalization(
			layoutLocalization);
	}

	@Override
	public LayoutLocalization updateStagedModel(
			PortletDataContext portletDataContext,
			LayoutLocalization layoutLocalization)
		throws PortalException {

		return _layoutLocalizationLocalService.updateLayoutLocalization(
			layoutLocalization);
	}

	@Reference
	private LayoutLocalizationLocalService _layoutLocalizationLocalService;

}