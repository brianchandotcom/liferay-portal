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

package com.liferay.commerce.internal.upgrade.v4_1_1;

import com.liferay.commerce.model.impl.CommerceAddressModelImpl;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Marco Leo
 */
public class CommerceAddressUpgradeProcess extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		alter(
			CommerceAddressModelImpl.class,
			new AlterColumnType("name", "VARCHAR(255) null"));
		alter(
			CommerceAddressModelImpl.class,
			new AlterColumnType("street1", "VARCHAR(255) null"));
		alter(
			CommerceAddressModelImpl.class,
			new AlterColumnType("street2", "VARCHAR(255) null"));
		alter(
			CommerceAddressModelImpl.class,
			new AlterColumnType("street3", "VARCHAR(255) null"));
	}

}