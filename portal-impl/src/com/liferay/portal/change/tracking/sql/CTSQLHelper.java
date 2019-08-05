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

package com.liferay.portal.change.tracking.sql;

/**
 * @author Preston Crary
 */
public interface CTSQLHelper {

	public boolean visitExcludes(
		long ctCollectionId, String tableName, String primaryColumnName,
		long classNameId, ExcludeVisitor excludeVisitor);

	public enum ExcludeType {

		CONFLICT, DELETE, MODIFY

	}

	@FunctionalInterface
	public interface ExcludeVisitor {

		public void acceptExclude(long primaryKey, ExcludeType excludeType);

	}

}