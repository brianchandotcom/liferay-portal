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

package com.liferay.portal.kernel.util;

/**
 * @author José Manuel Navarro
 */
public class EntityNameOrderByComparator extends TableNameOrderByComparator {

	public EntityNameOrderByComparator(
		OrderByComparator orderByComparator, String entityName) {

		super(orderByComparator, entityName);
	}

	@Override
	protected String getWrappedColumnName(String columnName) {
		StringBundler sb = new StringBundler(4);

		sb.append(StringPool.OPEN_CURLY_BRACE);
		sb.append(getTableName());
		sb.append(StringUtil.trim(columnName));
		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}

}