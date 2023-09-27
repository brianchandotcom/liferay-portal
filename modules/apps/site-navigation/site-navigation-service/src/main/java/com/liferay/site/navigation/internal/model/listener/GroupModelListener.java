/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = ModelListener.class)
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
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Reference
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

}