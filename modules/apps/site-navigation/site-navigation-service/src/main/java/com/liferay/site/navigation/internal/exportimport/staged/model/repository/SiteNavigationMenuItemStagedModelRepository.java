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

package com.liferay.site.navigation.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.site.navigation.model.SiteNavigationMenuItem",
	service = StagedModelRepository.class
)
public class SiteNavigationMenuItemStagedModelRepository
	implements StagedModelRepository<SiteNavigationMenuItem> {

	@Override
	public SiteNavigationMenuItem addStagedModel(
			PortletDataContext portletDataContext,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			siteNavigationMenuItem.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			siteNavigationMenuItem);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(siteNavigationMenuItem.getUuid());
		}

		return _siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
			userId, siteNavigationMenuItem.getGroupId(),
			siteNavigationMenuItem.getSiteNavigationMenuId(),
			siteNavigationMenuItem.getParentSiteNavigationMenuItemId(),
			siteNavigationMenuItem.getType(), siteNavigationMenuItem.getOrder(),
			siteNavigationMenuItem.getTypeSettings(), serviceContext);
	}

	@Override
	public void deleteStagedModel(SiteNavigationMenuItem siteNavigationMenuItem)
		throws PortalException {

		_siteNavigationMenuItemLocalService.deleteSiteNavigationMenuItem(
			siteNavigationMenuItem);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		SiteNavigationMenuItem siteNavigationMenuItem =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (siteNavigationMenuItem != null) {
			deleteStagedModel(siteNavigationMenuItem);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_siteNavigationMenuItemLocalService.
			deleteSiteNavigationMenuItemsByGroupId(
				portletDataContext.getScopeGroupId());
	}

	@Override
	public SiteNavigationMenuItem fetchMissingReference(
		String uuid, long groupId) {

		return _stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public SiteNavigationMenuItem fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _siteNavigationMenuItemLocalService.
			fetchSiteNavigationMenuItemByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<SiteNavigationMenuItem> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _siteNavigationMenuItemLocalService.
			getSiteNavigationMenuItemsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _siteNavigationMenuItemLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public SiteNavigationMenuItem getStagedModel(long siteNavigationMenuItemId)
		throws PortalException {

		return _siteNavigationMenuItemLocalService.getSiteNavigationMenuItem(
			siteNavigationMenuItemId);
	}

	@Override
	public SiteNavigationMenuItem saveStagedModel(
		SiteNavigationMenuItem siteNavigationMenuItem) {

		return _siteNavigationMenuItemLocalService.updateSiteNavigationMenuItem(
			siteNavigationMenuItem);
	}

	@Override
	public SiteNavigationMenuItem updateStagedModel(
			PortletDataContext portletDataContext,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			siteNavigationMenuItem.getUserUuid());

		return _siteNavigationMenuItemLocalService.updateSiteNavigationMenuItem(
			userId, siteNavigationMenuItem.getSiteNavigationMenuItemId(),
			siteNavigationMenuItem.getGroupId(),
			siteNavigationMenuItem.getSiteNavigationMenuId(),
			siteNavigationMenuItem.getParentSiteNavigationMenuItemId(),
			siteNavigationMenuItem.getType(), siteNavigationMenuItem.getOrder(),
			siteNavigationMenuItem.getTypeSettings());
	}

	@Reference
	private SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}