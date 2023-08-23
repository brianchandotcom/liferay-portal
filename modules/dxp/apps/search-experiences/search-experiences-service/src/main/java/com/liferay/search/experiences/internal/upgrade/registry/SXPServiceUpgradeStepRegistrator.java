/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.upgrade.registry;

import com.liferay.portal.kernel.upgrade.BaseExternalReferenceCodeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(enabled = true, service = UpgradeStepRegistrator.class)
public class SXPServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "1.1.0",
			UpgradeProcessFactory.addColumns(
				"SXPElement", "key_ VARCHAR(75) null",
				"version VARCHAR(75) null"),
			UpgradeProcessFactory.addColumns(
				"SXPBlueprint", "key_ VARCHAR(75) null",
				"version VARCHAR(75) null"));

		registry.register(
			"1.1.0", "1.2.0",
			new BaseExternalReferenceCodeUpgradeProcess() {

				@Override
				protected String[][] getTableAndPrimaryKeyColumnNames() {
					return new String[][] {
						{"SXPBlueprint", "sxpBlueprintId"},
						{"SXPElement", "sxpElementId"}
					};
				}

			});

		registry.register(
			"1.2.0", "1.3.0",
			new com.liferay.search.experiences.internal.upgrade.v1_3_0.
				SXPBlueprintUpgradeProcess());

		registry.register(
			"1.3.0", "1.3.1",
			new com.liferay.search.experiences.internal.upgrade.v1_3_1.
				DummyUpgradeProcess());

		registry.register(
			"1.3.1", "1.3.2",
			new com.liferay.search.experiences.internal.upgrade.v1_3_2.
				DummyUpgradeProcess());

		registry.register(
			"1.3.2", "1.3.3",
			new com.liferay.search.experiences.internal.upgrade.v1_3_3.
				DummyUpgradeProcess());

		registry.register(
			"1.3.3", "2.0.0",
			new com.liferay.search.experiences.internal.upgrade.v2_0_0.
				DummyUpgradeProcess());

		registry.register(
			"2.0.0", "2.0.1",
			new com.liferay.search.experiences.internal.upgrade.v2_0_1.
				SXPBlueprintUpgradeProcess());

		registry.register(
			"2.0.1", "2.0.2",
			new com.liferay.search.experiences.internal.upgrade.v2_0_2.
				SXPBlueprintUpgradeProcess());

		registry.register(
			"2.0.2", "2.0.3",
			new com.liferay.search.experiences.internal.upgrade.v2_0_3.
				SXPBlueprintUpgradeProcess());

		registry.register(
			"2.0.3", "3.0.0",
			new com.liferay.search.experiences.internal.upgrade.v3_0_0.
				SXPBlueprintUpgradeProcess());
	}

}