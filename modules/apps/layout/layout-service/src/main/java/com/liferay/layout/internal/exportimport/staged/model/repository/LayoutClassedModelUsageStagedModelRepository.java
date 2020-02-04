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
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.layout.model.LayoutClassedModelUsage",
	service = StagedModelRepository.class
)
public class LayoutClassedModelUsageStagedModelRepository
	implements StagedModelRepository<LayoutClassedModelUsage> {

	@Override
	public LayoutClassedModelUsage addStagedModel(
			PortletDataContext portletDataContext,
			LayoutClassedModelUsage layoutClassedModelUsage)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			layoutClassedModelUsage);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(layoutClassedModelUsage.getUuid());
		}

		return _layoutClassedModelUsageLocalService.addLayoutClassedModelUsage(
			layoutClassedModelUsage.getGroupId(),
			layoutClassedModelUsage.getClassNameId(),
			layoutClassedModelUsage.getClassPK(),
			layoutClassedModelUsage.getContainerKey(),
			layoutClassedModelUsage.getContainerType(),
			layoutClassedModelUsage.getPlid(), serviceContext);
	}

	@Override
	public void deleteStagedModel(
			LayoutClassedModelUsage layoutClassedModelUsage)
		throws PortalException {

		_layoutClassedModelUsageLocalService.deleteLayoutClassedModelUsage(
			layoutClassedModelUsage);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		LayoutClassedModelUsage layoutClassedModelUsage =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (layoutClassedModelUsage != null) {
			deleteStagedModel(layoutClassedModelUsage);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public LayoutClassedModelUsage fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _layoutClassedModelUsageLocalService.
			fetchLayoutClassedModelUsageByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<LayoutClassedModelUsage> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _layoutClassedModelUsageLocalService.
			getLayoutClassedModelUsagesByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _layoutClassedModelUsageLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public LayoutClassedModelUsage saveStagedModel(
			LayoutClassedModelUsage layoutClassedModelUsage)
		throws PortalException {

		return _layoutClassedModelUsageLocalService.
			updateLayoutClassedModelUsage(layoutClassedModelUsage);
	}

	@Override
	public LayoutClassedModelUsage updateStagedModel(
			PortletDataContext portletDataContext,
			LayoutClassedModelUsage layoutClassedModelUsage)
		throws PortalException {

		return _layoutClassedModelUsageLocalService.
			updateLayoutClassedModelUsage(layoutClassedModelUsage);
	}

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

}