/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v5_21_0.util;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Brian Wing Shun Chan
 * @generated
 * @see com.liferay.portal.tools.upgrade.table.builder.UpgradeTableBuilder
 */
public class CPConfigurationEntryTable {

	public static UpgradeProcess create() {
		return new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
				if (!hasTable(_TABLE_NAME)) {
					runSQL(_TABLE_SQL_CREATE);
				}
			}

		};
	}

	private static final String _TABLE_NAME = "CPConfigurationEntry";

	private static final String _TABLE_SQL_CREATE =
		"create table CPConfigurationEntry (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,externalReferenceCode VARCHAR(75) null,CPConfigurationEntryId LONG not null,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,CPConfigurationListId LONG,CPTaxCategoryId LONG,allowedOrderQuantities VARCHAR(75) null,backOrders BOOLEAN,CPDefinitionInventoryEngine VARCHAR(75) null,depth DOUBLE,displayAvailability BOOLEAN,displayStockQuantity BOOLEAN,freeShipping BOOLEAN,height DOUBLE,lowStockActivity VARCHAR(75) null,maxOrderQuantity BIGDECIMAL null,minOrderQuantity BIGDECIMAL null,minStockQuantity BIGDECIMAL null,multipleOrderQuantity BIGDECIMAL null,purchasable BOOLEAN,shippable BOOLEAN,shippingExtraPrice DOUBLE,shipSeparately BOOLEAN,taxExempt BOOLEAN,visible BOOLEAN,weight DOUBLE,width DOUBLE,primary key (CPConfigurationEntryId, ctCollectionId))";

}