/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v1_6_0.util;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Brian Wing Shun Chan
 * @generated
 * @see com.liferay.portal.tools.upgrade.table.builder.UpgradeTableBuilder
 */
public class CommerceChannelTable {

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

	private static final String _TABLE_NAME = "CommerceChannel";

	private static final String _TABLE_SQL_CREATE =
		"create table CommerceChannel (mvccVersion LONG default 0 not null,externalReferenceCode VARCHAR(75) null,commerceChannelId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,siteGroupId LONG,name VARCHAR(75) null,type_ VARCHAR(75) null,typeSettings VARCHAR(75) null,commerceCurrencyCode VARCHAR(75) null,priceDisplayType VARCHAR(75) null,discountsTargetNetPrice BOOLEAN)";

}