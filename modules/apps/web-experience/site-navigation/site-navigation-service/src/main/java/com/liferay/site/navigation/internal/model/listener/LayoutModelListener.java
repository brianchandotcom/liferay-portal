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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = ModelListener.class)
public class LayoutModelListener extends BaseModelListener<Layout> {

	@Override
	public void onAfterCreate(Layout layout) throws ModelListenerException {
		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuLocalService.fetchAutoSiteNavigationMenu(
				layout.getGroupId());

		if (siteNavigationMenu == null) {
			return;
		}

		UnicodeProperties unicodeProperties = new UnicodeProperties();

		unicodeProperties.setProperty(
			"groupId", String.valueOf(layout.getGroupId()));
		unicodeProperties.setProperty("layoutUuid", layout.getUuid());
		unicodeProperties.setProperty(
			"privateLayout", String.valueOf(layout.isPrivateLayout()));

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		try {
			long parentSiteNavigationMenuItemId = 0;

			if (layout.getParentPlid() > 0) {
				parentSiteNavigationMenuItemId =
					_getParentSiteNavigationMenuItemId(
						layout, siteNavigationMenu);
			}

			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				layout.getUserId(), layout.getGroupId(),
				siteNavigationMenu.getSiteNavigationMenuId(),
				parentSiteNavigationMenuItemId, "layout",
				unicodeProperties.toString(), serviceContext);
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	private long _getParentSiteNavigationMenuItemId(
			Layout layout, SiteNavigationMenu primarySiteNavigationMenu)
		throws PortalException {

		Layout parentLayout = _layoutLocalService.getParentLayout(layout);

		List<SiteNavigationMenuItem> siteNavigationMenuItems =
			_siteNavigationMenuItemLocalService.getSiteNavigationMenuItems(
				primarySiteNavigationMenu.getSiteNavigationMenuId());

		for (SiteNavigationMenuItem siteNavigationMenuItem :
				siteNavigationMenuItems) {

			UnicodeProperties unicodeProperties = new UnicodeProperties();

			unicodeProperties.fastLoad(
				siteNavigationMenuItem.getTypeSettings());

			String layoutUuid = unicodeProperties.getProperty("layoutUuid");

			if (StringUtil.equals(parentLayout.getUuid(), layoutUuid)) {
				return siteNavigationMenuItem.getSiteNavigationMenuItemId();
			}
		}

		return 0;
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;

	@Reference
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

}