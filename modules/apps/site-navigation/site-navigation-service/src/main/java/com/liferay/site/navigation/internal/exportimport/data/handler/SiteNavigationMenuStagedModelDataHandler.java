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
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.site.navigation.model.SiteNavigationMenu;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class SiteNavigationMenuStagedModelDataHandler
	extends BaseStagedModelDataHandler<SiteNavigationMenu> {

	public static final String[] CLASS_NAMES = {
		SiteNavigationMenu.class.getName()
	};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(SiteNavigationMenu siteNavigationMenu) {
		return siteNavigationMenu.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			SiteNavigationMenu siteNavigationMenu)
		throws Exception {

		Element siteNavigationMenuElement =
			portletDataContext.getExportDataElement(siteNavigationMenu);

		portletDataContext.addClassedModel(
			siteNavigationMenuElement,
			ExportImportPathUtil.getModelPath(siteNavigationMenu),
			siteNavigationMenu);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			SiteNavigationMenu siteNavigationMenu)
		throws Exception {

		SiteNavigationMenu importedSiteNavigationMenu =
			(SiteNavigationMenu)siteNavigationMenu.clone();

		importedSiteNavigationMenu.setGroupId(
			portletDataContext.getScopeGroupId());

		SiteNavigationMenu existingSiteNavigationMenu =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				siteNavigationMenu.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingSiteNavigationMenu == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedSiteNavigationMenu = _stagedModelRepository.addStagedModel(
				portletDataContext, importedSiteNavigationMenu);
		}
		else {
			importedSiteNavigationMenu.setMvccVersion(
				existingSiteNavigationMenu.getMvccVersion());
			importedSiteNavigationMenu.setSiteNavigationMenuId(
				existingSiteNavigationMenu.getSiteNavigationMenuId());

			importedSiteNavigationMenu =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedSiteNavigationMenu);
		}

		portletDataContext.importClassedModel(
			siteNavigationMenu, importedSiteNavigationMenu);
	}

	@Override
	protected StagedModelRepository<SiteNavigationMenu>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.site.navigation.model.SiteNavigationMenu)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<SiteNavigationMenu> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<SiteNavigationMenu> _stagedModelRepository;

}