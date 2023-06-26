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

package com.liferay.commerce.product.internal.upgrade.v5_5_0.util;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Brian Wing Shun Chan
 * @generated
 * @see com.liferay.portal.tools.upgrade.table.builder.UpgradeTableBuilder
 */
public class CPInstanceUnitOfMeasureTable {

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

	private static final String _TABLE_NAME = "CPInstanceUOM";

	private static final String _TABLE_SQL_CREATE =
		"create table CPInstanceUOM (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,CPInstanceUOMId LONG not null,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,CPInstanceId LONG,active_ BOOLEAN,incrementalOrderQuantity DECIMAL(30, 16) null,key_ VARCHAR(75) null,name STRING null,precision_ INTEGER,primary_ BOOLEAN,priority DOUBLE,rate DECIMAL(30, 16) null,sku VARCHAR(75) null,primary key (CPInstanceUOMId, ctCollectionId))";

}