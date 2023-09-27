/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0;

/**
 * @author Marcellus Tavares
 */
public class UpgradeKernelPackage
	extends com.liferay.portal.upgrade.v7_0_0.UpgradeKernelPackage {

	@Override
	protected String[][] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String[][] getResourceNames() {
		return _RESOURCE_NAMES;
	}

	private static final String[][] _CLASS_NAMES = {
		{
			"com.liferay.portlet.dynamicdatamapping.model.",
			"com.liferay.dynamic.data.mapping.model."
		}
	};

	private static final String[][] _RESOURCE_NAMES = {
		{
			"com.liferay.portlet.dynamicdatamapping",
			"com.liferay.dynamic.data.mapping"
		}
	};

}