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