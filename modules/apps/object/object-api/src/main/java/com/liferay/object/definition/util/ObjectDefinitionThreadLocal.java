/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.definition.util;

import com.liferay.object.entry.util.ObjectEntryThreadLocal;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Shuyang Zhou
 */
public class ObjectDefinitionThreadLocal {

	public static boolean isDeleteObjectDefinitionId(long objectDefinitionId) {
		Long deleteObjectDefinitionId =
			_deleteObjectDefinitionIdThreadLocal.get();

		if ((deleteObjectDefinitionId != null) &&
			(deleteObjectDefinitionId == objectDefinitionId)) {

			return true;
		}

		return false;
	}

	public static SafeCloseable setDeleteObjectDefinitionIdWithSafeCloseable(
		long id) {

		return _deleteObjectDefinitionIdThreadLocal.setWithSafeCloseable(id);
	}

	private static final CentralizedThreadLocal<Long>
		_deleteObjectDefinitionIdThreadLocal = new CentralizedThreadLocal<>(
			ObjectEntryThreadLocal.class +
				"._deleteObjectDefinitionIdThreadLocal");

}