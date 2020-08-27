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

package com.liferay.commerce.product.internal.upgrade.v1_3_0;

import com.liferay.commerce.product.internal.upgrade.base.BaseCommerceProductServiceUpgradeProcess;
import com.liferay.commerce.product.model.impl.CPAttachmentFileEntryModelImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Alec Sloan
 */
public class CPAttachmentFileEntryUpgradeProcess
	extends BaseCommerceProductServiceUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		addIndexes(CPAttachmentFileEntryModelImpl.TABLE_NAME);

		_dropIndex(CPAttachmentFileEntryModelImpl.TABLE_NAME, "IX_6FC3897D");
	}

	private void _dropIndex(String tableName, String indexName)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info(
				String.format(
					"Dropping index %s from table %s", indexName, tableName));
		}

		if (tableHasIndex(tableName, indexName)) {
			runSQL(
				StringBundler.concat(
					"drop index ", indexName, " on ", tableName));
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info(
					String.format(
						"Index %s already does not exist on table %s",
						indexName, tableName));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPAttachmentFileEntryUpgradeProcess.class);

}