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

package com.liferay.portal.kernel.transaction;

import java.sql.Connection;

/**
 * @author Michael Young
 */
public interface TransactionDefinition {

	public static final int ISOLATION_COUNTER = -3;

	public static final int ISOLATION_DEFAULT = -1;

	public static final int ISOLATION_PORTAL = -2;

	public static final int ISOLATION_READ_COMMITTED =
		Connection.TRANSACTION_READ_COMMITTED;

	public static final int ISOLATION_READ_UNCOMMITTED =
		Connection.TRANSACTION_READ_UNCOMMITTED;

	public static final int ISOLATION_REPEATABLE_READ =
		Connection.TRANSACTION_REPEATABLE_READ;

	public static final int ISOLATION_SERIALIZABLE =
		Connection.TRANSACTION_SERIALIZABLE;

	public static final int PROPAGATION_MANDATORY = 2;

	public static final int PROPAGATION_NESTED = 6;

	public static final int PROPAGATION_NEVER = 5;

	public static final int PROPAGATION_NOT_SUPPORTED = 4;

	public static final int PROPAGATION_REQUIRED = 0;

	public static final int PROPAGATION_REQUIRES_NEW = 3;

	public static final int PROPAGATION_SUPPORTS = 1;

	public static final int TIMEOUT_DEFAULT = -1;

}