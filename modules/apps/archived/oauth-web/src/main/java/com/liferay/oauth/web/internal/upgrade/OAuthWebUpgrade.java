/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.oauth.web.internal.upgrade;

import com.liferay.oauth.web.internal.upgrade.v1_0_0.UpgradePortletId;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.release.BaseUpgradeWebModuleRelease;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class OAuthWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		try {
			BaseUpgradeWebModuleRelease baseUpgradeWebModuleRelease =
				new BaseUpgradeWebModuleRelease() {

					@Override
					protected String getBundleSymbolicName() {
						return "com.liferay.oauth.web";
					}

					@Override
					protected String[] getPortletIds() {
						return new String[] {
							"1_WAR_oauthportlet", "2_WAR_oauthportlet",
							"3_WAR_oauthportlet"
						};
					}

				};

			baseUpgradeWebModuleRelease.upgrade();
		}
		catch (UpgradeException upgradeException) {
			throw new RuntimeException(upgradeException);
		}

		registry.register("0.0.0", "1.0.0", new DummyUpgradeStep());

		registry.register("0.0.1", "1.0.0", new UpgradePortletId());
	}

}