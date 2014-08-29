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

package com.liferay.polls.upgrade;

import com.liferay.polls.configurator.PollServicesConfigurator;
import com.liferay.polls.util.PollsWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.service.ReleaseLocalService;
import com.liferay.portal.upgrade.util.UpgradePortletId;

import java.util.Collections;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Miguel Pastor
 */
@Component(
	immediate = true, service = PollsPortletsUpgrade.class
)
public class PollsPortletsUpgrade {

	@Reference(unbind = "-")
	protected void setPollServicesConfigurator(
		PollServicesConfigurator pollServicesConfigurator) {
	}

	@Reference(unbind = "-")
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	@Activate
	protected void upgrade() throws PortalException {
		UpgradePortletId upgradePortletId = new UpgradePortletId() {

			@Override
			protected String[][] getRenamePortletIdsArray() {
				return new String[][] {
					new String[] {
						"25", "25_WAR_pollsweb"
					},
					new String[] {
						"59", "59_WAR_pollsweb"
					}
				};
			}

		};

		_releaseLocalService.updateRelease(
			PollsWebKeys.POLLS_PORTLET,
			Collections.<UpgradeProcess>singletonList(upgradePortletId), 1, 0,
			false);
	}

	private ReleaseLocalService _releaseLocalService;

}