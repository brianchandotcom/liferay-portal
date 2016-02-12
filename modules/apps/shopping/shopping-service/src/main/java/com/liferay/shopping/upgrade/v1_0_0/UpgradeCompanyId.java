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
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeCompanyId
	extends com.liferay.portal.upgrade.util.UpgradeCompanyId {

	@Override
	protected void doUpgrade() throws Exception {
		super.doUpgrade();

		//Upgrade ShoppingOrderItem

		if (_log.isInfoEnabled()) {
			_log.info("Adding column companyId to table ShoppingOrderItem");
		}

		runSQL("alter table ShoppingOrderItem add companyId LONG");

		StringBundler sb = new StringBundler(7);

		sb.append("update ShoppingOrderItem set companyId = ");
		sb.append("(select  max(ShoppingItem.companyId) ");
		sb.append("from ShoppingItem ");
		sb.append("where SUBSTR(ShoppingOrderItem.itemId, 0, INSTR('|', ");
		sb.append("ShoppingOrderItem.itemId)) = ");
		sb.append("CAST_TEXT(ShoppingItem.itemId))");
		sb.append("where ShoppingOrderItem.itemId like '%|%' ");

		runSQL(sb.toString());

		sb = new StringBundler(6);

		sb.append("update ShoppingOrderItem set companyId = ");
		sb.append("(select max(ShoppingItem.companyId) ");
		sb.append("from ShoppingItem ");
		sb.append("where ShoppingOrderItem.itemId = ");
		sb.append("CAST_TEXT(ShoppingItem.itemId)) ");
		sb.append("where ShoppingOrderItem.itemId not like '%|%' ");

		runSQL(sb.toString());
	}

	@Override
	protected TableUpdater[] getTableUpdaters() {
		return new TableUpdater[] {
			new TableUpdater("ShoppingItemField", "ShoppingItem", "itemId"),
			new TableUpdater("ShoppingItemPrice", "ShoppingItem", "itemId")
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeCompanyId.class);

}