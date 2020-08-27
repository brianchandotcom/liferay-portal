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

package com.liferay.commerce.internal.upgrade.v4_8_1;

import com.liferay.commerce.internal.upgrade.base.BaseCommerceServiceUpgradeProcess;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderStatusesUpgradeProcess
	extends BaseCommerceServiceUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"update CommerceOrder set orderStatus = 1 where orderStatus = 11");
		runSQL(
			"update CommerceOrder set orderStatus = 10 where orderStatus = 12");
	}

}