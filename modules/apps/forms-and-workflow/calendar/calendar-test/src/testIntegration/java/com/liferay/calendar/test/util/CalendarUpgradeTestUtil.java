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

package com.liferay.calendar.test.util;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

/**
 * @author Adam Brandizzi
 */
public class CalendarUpgradeTestUtil {

	public static UpgradeProcess getUpgradeStep(String upgradeStepClassName) {
		Registry registry = RegistryUtil.getRegistry();

		UpgradeStepRegistrator upgradeStepRegistror = registry.getService(
			_UPGRADE_SERVICE_CLASS_NAME);

		SearchRegistry searchRegistry = new SearchRegistry(
			upgradeStepClassName);

		upgradeStepRegistror.register(searchRegistry);

		return searchRegistry.getUpgradeStep();
	}

	private static final String _UPGRADE_SERVICE_CLASS_NAME =
		"com.liferay.calendar.internal.upgrade.CalendarServiceUpgrade";

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
			String bundleSymbolicName, String fromSchemaVersionString,
			String toSchemaVersionString, UpgradeStep... upgradeSteps) {

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