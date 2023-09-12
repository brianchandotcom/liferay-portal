/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.db.partition;

import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.PropsUtil;

/**
 * @author Luis Ortiz
 */
public class DBPartition {

	public static boolean isPartitionedModel(Class<?> clazz) {
		if (isPartitionEnabled() &&
			(ClassName.class.isAssignableFrom(clazz) ||
			 ShardedModel.class.isAssignableFrom(clazz))) {

			return true;
		}

		return false;
	}

	public static boolean isPartitionEnabled() {
		if (PortalRunMode.isTestMode()) {
			return GetterUtil.getBoolean(
				PropsUtil.get("database.partition.enabled"));
		}

		return _DATABASE_PARTITION_ENABLED;
	}

	private static final boolean _DATABASE_PARTITION_ENABLED =
		GetterUtil.getBoolean(PropsUtil.get("database.partition.enabled"));

}