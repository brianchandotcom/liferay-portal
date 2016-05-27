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

package com.liferay.shopping.upgrade.v1_0_0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradeCompanyId;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeCompanyId extends BaseUpgradeCompanyId {

	@Override
	protected void doUpgrade() throws Exception {
		super.doUpgrade();

		updateShoppingOrderItem();
	}

	@Override
	protected TableUpdater[] getTableUpdaters() {
		return new TableUpdater[] {
			new TableUpdater("ShoppingItemField", "ShoppingItem", "itemId"),
			new TableUpdater("ShoppingItemPrice", "ShoppingItem", "itemId")
		};
	}

	protected void updateShoppingOrderItem() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL("alter table ShoppingOrderItem add companyId LONG");

			StringBundler sb = new StringBundler(6);

			sb.append("update ShoppingOrderItem set companyId = (select ");
			sb.append("max(ShoppingItem.companyId) from ShoppingItem where ");
			sb.append("SUBSTR(ShoppingOrderItem.itemId, 1, INSTR('|', ");
			sb.append("ShoppingOrderItem.itemId)) = ");
			sb.append("CAST_TEXT(ShoppingItem.itemId)) where ");
			sb.append("ShoppingOrderItem.itemId like '%|%' ");

			runSQL(sb.toString());

			sb = new StringBundler(5);

			sb.append("update ShoppingOrderItem set companyId = (select ");
			sb.append("max(ShoppingItem.companyId) from ShoppingItem where ");
			sb.append("ShoppingOrderItem.itemId = ");
			sb.append("CAST_TEXT(ShoppingItem.itemId)) where ");
			sb.append("ShoppingOrderItem.itemId not like '%|%' ");

			runSQL(sb.toString());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeCompanyId.class);

}