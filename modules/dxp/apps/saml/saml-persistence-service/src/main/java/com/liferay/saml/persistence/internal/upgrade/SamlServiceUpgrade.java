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

package com.liferay.saml.persistence.internal.upgrade;

import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.release.BaseUpgradeServiceModuleRelease;
import com.liferay.portal.upgrade.step.util.UpgradeStepFactory;
import com.liferay.saml.persistence.internal.upgrade.v1_1_0.SamlSpAuthRequestUpgradeProcess;
import com.liferay.saml.persistence.internal.upgrade.v1_1_0.SamlSpMessageUpgradeProcess;
import com.liferay.saml.persistence.internal.upgrade.v2_1_0.SamlIdpSpConnectionUpgradeProcess;
import com.liferay.saml.persistence.internal.upgrade.v3_0_0.util.SamlPeerBindingTable;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class SamlServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		try {
			BaseUpgradeServiceModuleRelease baseUpgradeServiceModuleRelease =
				new BaseUpgradeServiceModuleRelease() {

					@Override
					protected String getNamespace() {
						return "Saml";
					}

					@Override
					protected String getNewBundleSymbolicName() {
						return "com.liferay.saml.persistence.service";
					}

					@Override
					protected String getOldBundleSymbolicName() {
						return "saml-portlet";
					}

				};

			baseUpgradeServiceModuleRelease.upgrade();
		}
		catch (UpgradeException upgradeException) {
			throw new RuntimeException(upgradeException);
		}

		registry.register("0.0.1", "1.0.0", new DummyUpgradeStep());

		registry.register(
			"1.0.0", "1.1.0",
			new com.liferay.saml.persistence.internal.upgrade.v1_1_0.
				SamlIdpSpSessionUpgradeProcess(),
			new SamlSpAuthRequestUpgradeProcess(),
			new SamlSpMessageUpgradeProcess(),
			new com.liferay.saml.persistence.internal.upgrade.v1_1_0.
				SamlSpSessionUpgradeProcess());

		registry.register(
			"1.1.0", "1.1.1",
			new com.liferay.saml.persistence.internal.upgrade.v1_1_1.
				SamlSpSessionUpgradeProcess());

		registry.register(
			"1.1.1", "1.1.2",
			new com.liferay.saml.persistence.internal.upgrade.v1_1_2.
				SamlSpSessionUpgradeProcess());

		registry.register(
			"1.1.2", "1.1.3",
			new com.liferay.saml.persistence.internal.upgrade.v1_1_3.
				SamlSpIdpConnectionUpgradeProcess());

		registry.register(
			"1.1.3", "1.1.4",
			new com.liferay.saml.persistence.internal.upgrade.v1_1_4.
				UpgradeClassNames());

		registry.register(
			"1.1.4", "2.0.0",
			new com.liferay.saml.persistence.internal.upgrade.v2_0_0.
				SamlSpSessionUpgradeProcess(),
			new com.liferay.saml.persistence.internal.upgrade.v2_0_0.
				SamlSpSessionDataUpgradeProcess(_configurationAdmin));

		registry.register(
			"2.0.0", "2.1.0", new SamlIdpSpConnectionUpgradeProcess());

		registry.register(
			"2.1.0", "2.2.0",
			new com.liferay.saml.persistence.internal.upgrade.v2_2_0.
				SamlSpIdpConnectionUpgradeProcess());

		registry.register(
			"2.2.0", "2.3.0",
			new com.liferay.saml.persistence.internal.upgrade.v2_3_0.
				SamlSpIdpConnectionUpgradeProcess());

		registry.register("2.3.0", "2.4.0", SamlPeerBindingTable.create());

		registry.register(
			"2.4.0", "2.5.0",
			new com.liferay.saml.persistence.internal.upgrade.v3_0_0.
				SamlIdpSpSessionUpgradeProcess(),
			new com.liferay.saml.persistence.internal.upgrade.v3_0_0.
				SamlSpSessionUpgradeProcess());

		registry.register(
			"2.5.0", "3.0.0",
			UpgradeStepFactory.dropColumns("SamlIdpSpSession", "nameIdFormat"),
			UpgradeStepFactory.dropColumns("SamlIdpSpSession", "nameIdValue"),
			UpgradeStepFactory.dropColumns(
				"SamlIdpSpSession", "samlSpEntityId"),
			UpgradeStepFactory.dropColumns("SamlSpSession", "nameIdFormat"),
			UpgradeStepFactory.dropColumns(
				"SamlSpSession", "nameIdNameQualifier"),
			UpgradeStepFactory.dropColumns(
				"SamlSpSession", "nameIdSPNameQualifier"),
			UpgradeStepFactory.dropColumns("SamlSpSession", "nameIdValue"),
			UpgradeStepFactory.dropColumns("SamlSpSession", "samlIdpEntityId"));
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}