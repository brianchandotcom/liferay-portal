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

package com.liferay.layout.admin.web.upgrade;

import com.liferay.layout.admin.web.constants.LayoutAdminPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.service.ReleaseLocalService;
import com.liferay.portal.upgrade.util.UpgradePortletId;

import java.util.Collections;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = LayoutAdminWebUpgrade.class)
public class LayoutAdminWebUpgrade {

	@Reference(unbind = "-")
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	@Reference(target = "(original.bean=*)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
	}

	@Activate
	protected void upgrade() throws PortalException {
		UpgradePortletId upgradePortletId = new UpgradePortletId() {

			@Override
			protected String[][] getRenamePortletIdsArray() {
				return new String[][] {
					new String[] {
						"88", LayoutAdminPortletKeys.LAYOUT_ADMIN
					},
					new String[] {"140", LayoutAdminPortletKeys.MY_PAGES},
					new String[] {"156", LayoutAdminPortletKeys.GROUP_PAGES}
				};
			}

		};

		_releaseLocalService.updateRelease(
			"com.liferay.layout.admin.web",
			Collections.<UpgradeProcess>singletonList(upgradePortletId), 1, 1,
			false);
	}

	private ReleaseLocalService _releaseLocalService;

}