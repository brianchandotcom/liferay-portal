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

package com.liferay.knowledge.base.internal.upgrade.v2_0_0;

import com.liferay.portal.upgrade.v7_0_0.UpgradeKernelPackage;

/**
 * @author Adolfo Pérez
 */
public class UpgradeClassNames extends UpgradeKernelPackage {

	@Override
	protected String[][] getClassNames() {
		return new String[][] {
			{"com.liferay.knowledgebase.", "com.liferay.knowledge.base."}
		};
	}

	@Override
	protected String[][] getResourceNames() {
		return new String[][] {
			{
				"com.liferay.knowledgebase.admin",
				"com.liferay.knowledge.base.admin"
			},
			{
				"com.liferay.knowledgebase.display",
				"com.liferay.knowledge.base.display"
			}
		};
	}

}