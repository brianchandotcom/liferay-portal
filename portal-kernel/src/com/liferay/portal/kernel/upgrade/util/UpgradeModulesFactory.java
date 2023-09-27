/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.util;

/**
 * @author Arthur Chan
 */
public class UpgradeModulesFactory {

	public static UpgradeModules create(
		String[] bundleSymbolicNames, String[][] convertedLegacyModules) {

		return create(bundleSymbolicNames, convertedLegacyModules, null);
	}

	public static UpgradeModules create(
		String[] bundleSymbolicNames, String[][] convertedLegacyModules,
		String[][] legacyServiceModules) {

		return new UpgradeModules() {

			@Override
			public String[] getBundleSymbolicNames() {
				if (bundleSymbolicNames == null) {
					return new String[0];
				}

				return bundleSymbolicNames;
			}

			@Override
			public String[][] getConvertedLegacyModules() {
				if ((convertedLegacyModules == null) ||
					(convertedLegacyModules[0] == null)) {

					return new String[0][0];
				}

				return convertedLegacyModules;
			}

			@Override
			public String[][] getLegacyServiceModules() {
				if ((legacyServiceModules == null) ||
					(legacyServiceModules[0] == null)) {

					return new String[0][0];
				}

				return legacyServiceModules;
			}

		};
	}

}