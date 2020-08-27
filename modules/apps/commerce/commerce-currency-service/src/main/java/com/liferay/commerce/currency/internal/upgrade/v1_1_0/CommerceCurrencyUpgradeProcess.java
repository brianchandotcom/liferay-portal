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

package com.liferay.commerce.currency.internal.upgrade.v1_1_0;

import com.liferay.commerce.currency.internal.upgrade.base.BaseCommerceCurrencyUpgradeProcess;
import com.liferay.commerce.currency.model.impl.CommerceCurrencyModelImpl;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceCurrencyUpgradeProcess
	extends BaseCommerceCurrencyUpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		dropColumn(CommerceCurrencyModelImpl.TABLE_NAME, "groupId");
	}

}