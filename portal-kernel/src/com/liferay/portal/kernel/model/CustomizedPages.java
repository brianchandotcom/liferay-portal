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

package com.liferay.portal.kernel.model;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-15626.
 * </p>
 *
 * @author Raymond Augé
 */
public class CustomizedPages {

	public static String namespaceColumnId(String columnId) {
		return columnId.concat(_CUSTOMIZABLE_SUFFIX);
	}

	public static String namespacePlid(long plid) {
		String className = CustomizedPages.class.getName();

		return className.concat(String.valueOf(plid));
	}

	private static final String _CUSTOMIZABLE_SUFFIX = "-customizable";

}