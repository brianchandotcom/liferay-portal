/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.internal.upgrade.v2_4_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBTypeToSQLMap;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author José Abelenda
 */
public class RemoteAppEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_addExternalReferenceCodeColumn();

		_updateExternalReferenceCode();
	}

	private void _addExternalReferenceCodeColumn() throws Exception {
		alterTableAddColumn(
			"RemoteAppEntry", "externalReferenceCode", "VARCHAR(75)");
	}

	private void _updateExternalReferenceCode() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("update RemoteAppEntry set externalReferenceCode = ");
		sb.append("CAST_TEXT(remoteAppEntryId) where externalReferenceCode ");
		sb.append("is null");

		DBTypeToSQLMap dbTypeToSQLMap = new DBTypeToSQLMap(sb.toString());

		runSQL(dbTypeToSQLMap);
	}

}