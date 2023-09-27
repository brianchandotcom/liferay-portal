/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.fields;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Ivica Cardic
 */
public class NestedFieldsContextThreadLocal {

	public static NestedFieldsContext getAndSetNestedFieldsContext(
		NestedFieldsContext nestedFieldsContext) {

		NestedFieldsContext oldNestedFieldsContext = getNestedFieldsContext();

		setNestedFieldsContext(nestedFieldsContext);

		return oldNestedFieldsContext;
	}

	public static NestedFieldsContext getNestedFieldsContext() {
		return _nestedContextThreadLocal.get();
	}

	public static void setNestedFieldsContext(
		NestedFieldsContext nestedFieldsContext) {

		_nestedContextThreadLocal.set(nestedFieldsContext);
	}

	private static final ThreadLocal<NestedFieldsContext>
		_nestedContextThreadLocal = new CentralizedThreadLocal<>(
			NestedFieldsContextThreadLocal.class +
				"._nestedFieldsContextThreadLocal");

}