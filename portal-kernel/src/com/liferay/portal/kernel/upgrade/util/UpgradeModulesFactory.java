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