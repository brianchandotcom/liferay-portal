/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.data.integration.internal.upgrade.base;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Ethan Bustad
 */
public abstract class BaseCommerceDataIntegrationServiceUpgradeProcess
	extends UpgradeProcess {

	protected void renameColumn(
			Class<?> tableClass, String tableName, String oldColumnName,
			String newColumnName)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info(
				String.format(
					"Renaming column %s to %s in table %s", oldColumnName,
					newColumnName, tableName));
		}

		String newColumnSimpleName = StringUtil.extractFirst(
			newColumnName, StringPool.SPACE);

		if (!hasColumn(tableName, newColumnSimpleName)) {
			alter(
				tableClass, new AlterColumnName(oldColumnName, newColumnName));
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info(
					String.format(
						"Column %s already exists on table %s", newColumnName,
						tableName));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseCommerceDataIntegrationServiceUpgradeProcess.class);

}