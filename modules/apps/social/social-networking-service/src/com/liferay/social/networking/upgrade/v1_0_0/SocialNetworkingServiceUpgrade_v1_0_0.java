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

package com.liferay.social.networking.upgrade.v1_0_0;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.OlderVersionException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.service.ReleaseLocalService;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.springframework.context.ApplicationContext;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true, service = SocialNetworkingServiceUpgrade_v1_0_0.class
)
public class SocialNetworkingServiceUpgrade_v1_0_0 {

	@Reference(
		target =
			"(org.springframework.context.service.name=" +
				"com.liferay.social.networking.service)",
		unbind = "-"
	)
	protected void setApplicationContext(
		ApplicationContext applicationContext) {
	}

	@Reference(unbind = "-")
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	@Activate
	protected void upgrade() throws PortalException {
		List<UpgradeProcess> upgradeProcesses =
			Collections.<UpgradeProcess>singletonList(new UpgradeNamespace());

		try {
			_releaseLocalService.updateRelease(
				"social-networking-portlet", upgradeProcesses, 100, 0, false);
		}
		catch (OlderVersionException ovs) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No need to execute " +
						SocialNetworkingServiceUpgrade_v1_0_0.class.getName());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SocialNetworkingServiceUpgrade_v1_0_0.class);

	private ReleaseLocalService _releaseLocalService;

}