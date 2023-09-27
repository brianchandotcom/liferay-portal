/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.internal.upgrade.registry;

import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.style.book.internal.upgrade.v1_1_0.StyleBookEntryUpgradeProcess;
import com.liferay.style.book.internal.upgrade.v1_2_0.StyleBookEntryVersionUpgradeProcess;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = UpgradeStepRegistrator.class)
public class StyleBookServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.1", "1.0.0", new DummyUpgradeStep());

		registry.register("1.0.0", "1.1.0", new StyleBookEntryUpgradeProcess());

		registry.register(
			"1.1.0", "1.2.0", new StyleBookEntryVersionUpgradeProcess());

		registry.register(
			"1.2.0", "1.2.1",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getTableNames() {
					return new String[] {"StyleBookEntryVersion"};
				}

			});

		registry.register(
			"1.2.1", "1.3.0", new CTModelUpgradeProcess("StyleBookEntry"));

		registry.register(
			"1.3.0", "1.4.0",
			new CTModelUpgradeProcess("StyleBookEntryVersion"));

		registry.register(
			"1.4.0", "1.4.1",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getTableNames() {
					return new String[] {"StyleBookEntryVersion"};
				}

			});
	}

}