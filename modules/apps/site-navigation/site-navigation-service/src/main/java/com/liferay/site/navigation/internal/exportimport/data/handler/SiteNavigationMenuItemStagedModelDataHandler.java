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

package com.liferay.site.navigation.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class SiteNavigationMenuItemStagedModelDataHandler
	extends BaseStagedModelDataHandler<SiteNavigationMenuItem> {

	public static final String[] CLASS_NAMES = {
		SiteNavigationMenuItem.class.getName()
	};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(
		SiteNavigationMenuItem siteNavigationMenuItem) {

		return siteNavigationMenuItem.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws Exception {

		Element siteNavigationMenuItemElement =
			portletDataContext.getExportDataElement(siteNavigationMenuItem);

		SiteNavigationMenuItemType siteNavigationMenuItemType =
			_siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
				siteNavigationMenuItem.getType());

		if (!siteNavigationMenuItemType.exportData(
				portletDataContext, siteNavigationMenuItemElement,
				siteNavigationMenuItem)) {

			return;
		}

		if (siteNavigationMenuItem.getSiteNavigationMenuId() > 0) {
			SiteNavigationMenu siteNavigationMenu =
				_siteNavigationMenuLocalService.getSiteNavigationMenu(
					siteNavigationMenuItem.getSiteNavigationMenuId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, siteNavigationMenuItem, siteNavigationMenu,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		if (siteNavigationMenuItem.getParentSiteNavigationMenuItemId() > 0) {
			SiteNavigationMenuItem parentSiteNavigationMenuItem =
				_siteNavigationMenuItemLocalService.getSiteNavigationMenuItem(
					siteNavigationMenuItem.getParentSiteNavigationMenuItemId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, siteNavigationMenuItem,
				parentSiteNavigationMenuItem,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		portletDataContext.addClassedModel(
			siteNavigationMenuItemElement,
			ExportImportPathUtil.getModelPath(siteNavigationMenuItem),
			siteNavigationMenuItem);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long siteNavigationMenuItemId)
		throws Exception {

		SiteNavigationMenuItem existingSiteNavigationMenuItem =
			fetchMissingReference(uuid, groupId);

		if (existingSiteNavigationMenuItem == null) {
			return;
		}

		Map<Long, Long> siteNavigationMenuItemIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				SiteNavigationMenuItem.class);

		siteNavigationMenuItemIds.put(
			siteNavigationMenuItemId,
			existingSiteNavigationMenuItem.getSiteNavigationMenuItemId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws Exception {

		Map<Long, Long> siteNavigationMenuIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				SiteNavigationMenu.class);

		long siteNavigationMenuId = MapUtil.getLong(
			siteNavigationMenuIds,
			siteNavigationMenuItem.getSiteNavigationMenuId(),
			siteNavigationMenuItem.getSiteNavigationMenuId());

		Map<Long, Long> siteNavigationMenuItemIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				SiteNavigationMenuItem.class);

		long parentSiteNavigationMenuItemId = MapUtil.getLong(
			siteNavigationMenuItemIds,
			siteNavigationMenuItem.getParentSiteNavigationMenuItemId(),
			siteNavigationMenuItem.getParentSiteNavigationMenuItemId());

		SiteNavigationMenuItem importedSiteNavigationMenuItem =
			(SiteNavigationMenuItem)siteNavigationMenuItem.clone();

		importedSiteNavigationMenuItem.setGroupId(
			portletDataContext.getScopeGroupId());
		importedSiteNavigationMenuItem.setSiteNavigationMenuId(
			siteNavigationMenuId);
		importedSiteNavigationMenuItem.setParentSiteNavigationMenuItemId(
			parentSiteNavigationMenuItemId);

		SiteNavigationMenuItem existingSiteNavigationMenuItem =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				siteNavigationMenuItem.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingSiteNavigationMenuItem == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedSiteNavigationMenuItem =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedSiteNavigationMenuItem);
		}
		else {
			importedSiteNavigationMenuItem.setMvccVersion(
				existingSiteNavigationMenuItem.getMvccVersion());
			importedSiteNavigationMenuItem.setSiteNavigationMenuItemId(
				existingSiteNavigationMenuItem.getSiteNavigationMenuItemId());

			importedSiteNavigationMenuItem =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedSiteNavigationMenuItem);
		}

		SiteNavigationMenuItemType siteNavigationMenuItemType =
			_siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
				siteNavigationMenuItem.getType());

		if (!siteNavigationMenuItemType.importData(
				portletDataContext, siteNavigationMenuItem,
				importedSiteNavigationMenuItem)) {

			_stagedModelRepository.deleteStagedModel(
				importedSiteNavigationMenuItem);

			return;
		}

		_siteNavigationMenuItemLocalService.updateSiteNavigationMenuItem(
			importedSiteNavigationMenuItem);

		portletDataContext.importClassedModel(
			siteNavigationMenuItem, importedSiteNavigationMenuItem);
	}

	@Override
	protected StagedModelRepository<SiteNavigationMenuItem>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference
	private SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;

	@Reference
	private SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;

	@Reference
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.site.navigation.model.SiteNavigationMenuItem)",
		unbind = "-"
	)
	private StagedModelRepository<SiteNavigationMenuItem>
		_stagedModelRepository;

}