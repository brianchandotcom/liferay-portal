/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test.util;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

/**
 * @author Adam Brandizzi
 * @author Alberto Chaparro
 */
public class UpgradeTestUtil {

	public static UpgradeProcess getUpgradeStep(
		UpgradeStepRegistrator upgradeStepRegistrator,
		String upgradeStepClassName) {

		SearchRegistry searchRegistry = new SearchRegistry(
			upgradeStepClassName);

		upgradeStepRegistrator.register(searchRegistry);

		return searchRegistry.getUpgradeStep();
	}

	private static class SearchRegistry
		implements UpgradeStepRegistrator.Registry {

		public SearchRegistry(String upgradeStepClassName) {
			_upgradeStepClassName = upgradeStepClassName;
		}

		public UpgradeProcess getUpgradeStep() {
			return _upgradeStep;
		}

		@Override
		public void register(
			String fromSchemaVersionString, String toSchemaVersionString,
			UpgradeStep... upgradeSteps) {

			for (UpgradeStep upgradeStep : upgradeSteps) {
				Class<?> clazz = upgradeStep.getClass();

				String className = clazz.getName();

				if (className.contains(_upgradeStepClassName)) {
					_upgradeStep = (UpgradeProcess)upgradeStep;
				}
			}
		}

		private UpgradeProcess _upgradeStep;
		private final String _upgradeStepClassName;

	}

}