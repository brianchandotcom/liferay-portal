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

package com.liferay.site.navigation.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.site.navigation.constants.SiteNavigationConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = ModelListener.class)
public class GroupModelListener extends BaseModelListener<Group> {

	@Override
	public void onBeforeRemove(Group group) throws ModelListenerException {
		try {
			List<SiteNavigationMenu> siteNavigationMenus =
				_siteNavigationMenuLocalService.getSiteNavigationMenus(
					group.getGroupId());

			for (SiteNavigationMenu siteNavigationMenu : siteNavigationMenus) {
				if (siteNavigationMenu.getType() ==
						SiteNavigationConstants.TYPE_PRIMARY) {

					continue;
				}

				_siteNavigationMenuLocalService.deleteSiteNavigationMenu(
					siteNavigationMenu);
			}

			SiteNavigationMenu primarySiteNavigationMenu =
				_siteNavigationMenuLocalService.fetchPrimarySiteNavigationMenu(
					group.getGroupId());

			if (primarySiteNavigationMenu != null) {
				_siteNavigationMenuLocalService.deleteSiteNavigationMenu(
					primarySiteNavigationMenu);
			}
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Reference
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

}