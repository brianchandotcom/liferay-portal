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

package com.liferay.saml.persistence.internal.upgrade.v2_4_0.util;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Brian Wing Shun Chan
 * @generated
 * @see com.liferay.portal.tools.upgrade.table.builder.UpgradeTableBuilder
 */
public class SamlPeerBindingTable {

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

	private static final String _TABLE_NAME = "SamlPeerBinding";

	private static final String _TABLE_SQL_CREATE =
		"create table SamlPeerBinding (samlPeerBindingId LONG not null primary key,companyId LONG,createDate DATE null,userId LONG,userName VARCHAR(75) null,deleted BOOLEAN,samlNameIdFormat VARCHAR(75) null,samlNameIdNameQualifier VARCHAR(75) null,samlNameIdSpNameQualifier VARCHAR(75) null,samlNameIdSpProvidedId VARCHAR(75) null,samlNameIdValue VARCHAR(75) null,samlPeerEntityId VARCHAR(75) null)";

}