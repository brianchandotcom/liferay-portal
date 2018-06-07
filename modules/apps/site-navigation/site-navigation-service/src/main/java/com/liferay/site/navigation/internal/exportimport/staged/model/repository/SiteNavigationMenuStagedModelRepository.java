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
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.site.navigation.model.SiteNavigationMenu",
	service = StagedModelRepository.class
)
public class SiteNavigationMenuStagedModelRepository
	implements StagedModelRepository<SiteNavigationMenu> {

	@Override
	public SiteNavigationMenu addStagedModel(
			PortletDataContext portletDataContext,
			SiteNavigationMenu siteNavigationMenu)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			siteNavigationMenu.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			siteNavigationMenu);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(siteNavigationMenu.getUuid());
		}

		return _siteNavigationMenuLocalService.addSiteNavigationMenu(
			userId, siteNavigationMenu.getGroupId(),
			siteNavigationMenu.getName(), siteNavigationMenu.getType(),
			siteNavigationMenu.isAuto(), serviceContext);
	}

	@Override
	public void deleteStagedModel(SiteNavigationMenu siteNavigationMenu)
		throws PortalException {

		_siteNavigationMenuLocalService.deleteSiteNavigationMenu(
			siteNavigationMenu);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		SiteNavigationMenu siteNavigationMenu =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (siteNavigationMenu != null) {
			deleteStagedModel(siteNavigationMenu);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_siteNavigationMenuLocalService.deleteSiteNavigationMenus(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public SiteNavigationMenu fetchMissingReference(String uuid, long groupId) {
		return _stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public SiteNavigationMenu fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _siteNavigationMenuLocalService.
			fetchSiteNavigationMenuByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<SiteNavigationMenu> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _siteNavigationMenuLocalService.
			getSiteNavigationMenusByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _siteNavigationMenuLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public SiteNavigationMenu getStagedModel(long siteNavigationMenuId)
		throws PortalException {

		return _siteNavigationMenuLocalService.getSiteNavigationMenu(
			siteNavigationMenuId);
	}

	@Override
	public SiteNavigationMenu saveStagedModel(
		SiteNavigationMenu siteNavigationMenu) {

		return _siteNavigationMenuLocalService.updateSiteNavigationMenu(
			siteNavigationMenu);
	}

	@Override
	public SiteNavigationMenu updateStagedModel(
			PortletDataContext portletDataContext,
			SiteNavigationMenu siteNavigationMenu)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			siteNavigationMenu.getUserUuid());

		return _siteNavigationMenuLocalService.updateSiteNavigationMenu(
			userId, siteNavigationMenu.getSiteNavigationMenuId(),
			siteNavigationMenu.getGroupId(), siteNavigationMenu.getName(),
			siteNavigationMenu.getType(), siteNavigationMenu.isAuto());
	}

	@Reference
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}