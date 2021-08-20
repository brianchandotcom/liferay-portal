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

package com.liferay.commerce.internal.upgrade.v7_2_0;

import com.liferay.commerce.internal.upgrade.base.BaseCommerceServiceUpgradeProcess;
import com.liferay.commerce.model.impl.CommerceOrderTypeModelImpl;
import com.liferay.commerce.model.impl.CommerceOrderTypeRelModelImpl;

/**
 * @author Riccardo Alberti
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderTypeUpgradeProcess
	extends BaseCommerceServiceUpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		if (!hasTable(CommerceOrderTypeModelImpl.TABLE_NAME)) {
			runSQL(CommerceOrderTypeModelImpl.TABLE_SQL_CREATE);
		}

		if (!hasTable(CommerceOrderTypeRelModelImpl.TABLE_NAME)) {
			runSQL(CommerceOrderTypeRelModelImpl.TABLE_SQL_CREATE);
		}
	}

}