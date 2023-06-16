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