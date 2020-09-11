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

package com.liferay.commerce.product.type.virtual.internal.upgrade.v1_1_1;

import com.liferay.commerce.product.type.virtual.internal.upgrade.v1_1_1.util.CPDefinitionVirtualSettingTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Ivica Cardic
 */
public class CPDefinitionVirtualSettingUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumnType(
				getTableName(CPDefinitionVirtualSettingTable.class),
				"sampleUrl", "VARCHAR(75) null")) {

			alter(
				CPDefinitionVirtualSettingTable.class,
				new AlterColumnType("sampleUrl", "VARCHAR(255) null"));
		}

		if (hasColumnType(
				getTableName(CPDefinitionVirtualSettingTable.class), "url",
				"VARCHAR(75) null")) {

			alter(
				CPDefinitionVirtualSettingTable.class,
				new AlterColumnType("url", "VARCHAR(255) null"));
		}
	}

}