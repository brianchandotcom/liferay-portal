/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.batch.engine.task;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Igor Beslic
 */
public class TaskItemUtil {

	public static String getInternalClassName(String internalClassNameKey) {
		int index = internalClassNameKey.indexOf(StringPool.POUND);

		if (index < 0) {
			return internalClassNameKey;
		}

		return internalClassNameKey.substring(0, index);
	}

	public static String getInternalClassNameKey(
		String internalClassName, String taskItemDelegateName) {

		if (Validator.isBlank(taskItemDelegateName) ||
			StringUtil.equals(taskItemDelegateName, "DEFAULT")) {

			return internalClassName;
		}

		return internalClassName + StringPool.POUND + taskItemDelegateName;
	}

	public static String getSimpleClassName(String internalClassNameKey) {
		int index = internalClassNameKey.indexOf(StringPool.POUND);

		if (index < 0) {
			return StringUtil.extractLast(
				internalClassNameKey, StringPool.PERIOD);
		}

		return getTaskItemDelegateName(internalClassNameKey);
	}

	public static String getTaskItemDelegateName(String internalClassNameKey) {
		int index = internalClassNameKey.indexOf(StringPool.POUND);

		if (index < 0) {
			return "DEFAULT";
		}

		return internalClassNameKey.substring(index + 1);
	}

}