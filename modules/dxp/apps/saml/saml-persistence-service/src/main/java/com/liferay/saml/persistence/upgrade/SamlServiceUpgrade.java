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

package com.liferay.saml.persistence.upgrade;

import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.saml.persistence.upgrade.v1_0_0.UpgradeSamlSpAuthRequest;
import com.liferay.saml.persistence.upgrade.v1_0_0.UpgradeSamlSpMessage;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class SamlServiceUpgrade implements UpgradeStepRegistrator {

	public void register(Registry registry) {
		registry.register(
			"com.liferay.saml.persistence.service", "0.0.1", "1.0.0",
			new DummyUpgradeStep());

		registry.register(
			"com.liferay.saml.persistence.service", "1.0.0", "1.1.0",
			new com.liferay.saml.persistence.upgrade.v1_0_0.
				UpgradeSamlIdpSpSession(),
			new UpgradeSamlSpAuthRequest(), new UpgradeSamlSpMessage(),
			new com.liferay.saml.persistence.upgrade.v1_0_0.
				UpgradeSamlSpSession());

		registry.register(
			"com.liferay.saml.persistence.service", "1.1.0", "1.1.1",
			new com.liferay.saml.persistence.upgrade.v1_1_0.
				UpgradeSamlSpSession());

		registry.register(
			"com.liferay.saml.persistence.service", "1.1.1", "1.1.2",
			new com.liferay.saml.persistence.upgrade.v1_1_1.
				UpgradeSamlSpSession());

		registry.register(
			"com.liferay.saml.persistence.service", "1.1.2", "1.1.3",
			new com.liferay.saml.persistence.upgrade.v1_1_2.
				UpgradeSamlSpIdpConnection());
	}

}