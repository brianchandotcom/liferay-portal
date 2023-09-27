/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v4_1_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Julián Vela
 */
public class ObjectViewUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateObjectFieldName("ObjectViewColumn", "createDate", "dateCreated");
		_updateObjectFieldName(
			"ObjectViewColumn", "modifiedDate", "dateModified");
		_updateObjectFieldName(
			"ObjectViewFilterColumn", "createDate", "dateCreated");
		_updateObjectFieldName(
			"ObjectViewFilterColumn", "modifiedDate", "dateModified");
		_updateObjectFieldName(
			"ObjectViewSortColumn", "createDate", "dateCreated");
		_updateObjectFieldName(
			"ObjectViewSortColumn", "modifiedDate", "dateModified");
	}

	private void _updateObjectFieldName(
			String dbTableName, String newObjectFieldName,
			String oldObjectFieldName)
		throws Exception {

		runSQL(
			StringBundler.concat(
				"update ", dbTableName, " set objectFieldName = '",
				newObjectFieldName, "' where objectFieldName = '",
				oldObjectFieldName, "'"));
	}

}