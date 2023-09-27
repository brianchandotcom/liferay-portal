/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		"create table SamlPeerBinding (samlPeerBindingId LONG not null primary key,companyId LONG,createDate DATE null,userId LONG,userName VARCHAR(75) null,deleted BOOLEAN,samlNameIdFormat VARCHAR(1024) null,samlNameIdNameQualifier VARCHAR(75) null,samlNameIdSpNameQualifier VARCHAR(75) null,samlNameIdSpProvidedId VARCHAR(75) null,samlNameIdValue VARCHAR(1024) null,samlPeerEntityId VARCHAR(1024) null)";
}