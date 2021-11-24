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

package com.liferay.data.engine.internal.upgrade.v2_1_2;

import com.liferay.data.engine.internal.upgrade.v2_1_1.util.DEDataDefinitionFieldLinkTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Lino Alves
 */
public class DEDataDefinitionFieldLinkUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(
			DEDataDefinitionFieldLinkTable.class,
			new AlterColumnType("fieldName", "VARCHAR(255) null"));
	}

}