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

package com.liferay.social.networking.web.internal.upgrade;

import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.release.BaseUpgradeWebModuleRelease;
import com.liferay.social.networking.constants.SocialNetworkingPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class SocialNetworkingWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		BaseUpgradeWebModuleRelease baseUpgradeWebModuleRelease =
			new BaseUpgradeWebModuleRelease() {

				@Override
				protected String getBundleSymbolicName() {
					return "com.liferay.social.networking.web";
				}

				@Override
				protected String[] getPortletIds() {
					return new String[] {
						"1_WAR_socialnetworkingportlet",
						"2_WAR_socialnetworkingportlet",
						"3_WAR_socialnetworkingportlet",
						"4_WAR_socialnetworkingportlet",
						"5_WAR_socialnetworkingportlet",
						"6_WAR_socialnetworkingportlet",
						"7_WAR_socialnetworkingportlet",
						"8_WAR_socialnetworkingportlet",
						SocialNetworkingPortletKeys.FRIENDS,
						SocialNetworkingPortletKeys.FRIENDS_ACTIVITIES,
						SocialNetworkingPortletKeys.MAP,
						SocialNetworkingPortletKeys.MEMBERS,
						SocialNetworkingPortletKeys.MEMBERS_ACTIVITIES,
						SocialNetworkingPortletKeys.MEETUPS,
						SocialNetworkingPortletKeys.SUMMARY,
						SocialNetworkingPortletKeys.WALL
					};
				}

			};

		try {
			baseUpgradeWebModuleRelease.upgrade();
		}
		catch (UpgradeException ue) {
			throw new RuntimeException(ue);
		}

		registry.register(
			"com.liferay.social.networking.web", "0.0.1", "1.0.4",
			new DummyUpgradeStep());
	}

}