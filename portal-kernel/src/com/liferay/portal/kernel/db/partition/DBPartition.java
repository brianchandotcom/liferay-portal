/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.db.partition;

import com.liferay.counter.kernel.model.Counter;
import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Luis Ortiz
 */
public class DBPartition {

	public static boolean isPartitionedModel(Class<?> clazz) {
		if (_DATABASE_PARTITION_ENABLED &&
			(ClassName.class.isAssignableFrom(clazz) ||
			 Counter.class.isAssignableFrom(clazz) ||
			 ResourceAction.class.isAssignableFrom(clazz) ||
			 ShardedModel.class.isAssignableFrom(clazz))) {

			return true;
		}

		return false;
	}

	public static boolean isPartitionedModel(String clazz) {
		if (_DATABASE_PARTITION_ENABLED &&
			(StringUtil.equals(clazz, _CLASS_NAME_IMPL_CLASS_NAME) ||
			 StringUtil.equals(clazz, _COUNTER_IMPL_CLASS_NAME) ||
			 StringUtil.equals(clazz, _RESOURCE_ACTION_IMPL_CLASS_NAME))) {

			return true;
		}

		return false;
	}

	public static boolean isPartitionEnabled() {
		return _DATABASE_PARTITION_ENABLED;
	}

	private static String _getImplClassName(Class<?> clazz) {
		ImplementationClassName implementationClassName = clazz.getAnnotation(
			ImplementationClassName.class);

		return implementationClassName.value();
	}

	private static final String _CLASS_NAME_IMPL_CLASS_NAME;

	private static final String _COUNTER_IMPL_CLASS_NAME;

	private static final boolean _DATABASE_PARTITION_ENABLED =
		GetterUtil.getBoolean(PropsUtil.get("database.partition.enabled"));

	private static final String _RESOURCE_ACTION_IMPL_CLASS_NAME;

	static {
		_CLASS_NAME_IMPL_CLASS_NAME = _getImplClassName(ClassName.class);
		_COUNTER_IMPL_CLASS_NAME = _getImplClassName(Counter.class);
		_RESOURCE_ACTION_IMPL_CLASS_NAME = _getImplClassName(
			ResourceAction.class);
	}

}