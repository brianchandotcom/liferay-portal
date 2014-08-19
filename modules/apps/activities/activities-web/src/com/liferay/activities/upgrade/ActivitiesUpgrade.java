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
package com.liferay.activities.upgrade;

import com.liferay.activities.portlet.ActivitiesPortlet;
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
 * @author Raymond Augé
 * @author Peter Fellwock
 */
@Component(immediate = true, service = ActivitiesUpgrade.class)
public class ActivitiesUpgrade {
	
	@Reference(target = "(original.bean=*)", unbind = "-")
	private void setServletContext(ServletContext servletContext) {
	}

	@Activate
	private void upgrade() throws PortalException {
		UpgradePortletId upgradePortletId = new UpgradePortletId() {

			@Override
			protected String[][] getRenamePortletIdsArray() {
				return new String[][] { 
					new String[] { 
						"116",
						"com_liferay_activities_portlet_ActivitiesPortlet" 
						}
					};
				}
			};

			_releaseLocalService.updateRelease(
				ActivitiesPortlet.class.getName(),
				Collections.<UpgradeProcess> singletonList(
					upgradePortletId), 1, 1, false);
	}

	@Reference(unbind = "-")
	private void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {
		
		_releaseLocalService = releaseLocalService;
	}

	private ReleaseLocalService _releaseLocalService;
}