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

package com.liferay.commerce.internal.upgrade.v4_2_1;

import com.liferay.commerce.model.impl.CommerceOrderItemModelImpl;
import com.liferay.commerce.model.impl.CommerceOrderModelImpl;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Marco Leo
 */
public class PrintedNoteUpgradeProcess extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		alter(
			CommerceOrderModelImpl.class,
			new AlterColumnType("printedNote", "STRING null"));
		alter(
			CommerceOrderItemModelImpl.class,
			new AlterColumnType("printedNote", "STRING null"));
	}

}