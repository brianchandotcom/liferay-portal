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

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Defines the level of visibility this transaction has for data changes made by
 * other concurrent transactions.
 *
 * <p>
 * <code>SERIALIZABLE</code> is the most restrictive.
 * </p>
 *
 * <p>
 * <code>READ_UNCOMMITTED</code> is the least restrictive.
 * </p>
 *
 * @author Michael Young
 * @author Shuyang Zhou
 * @see    Transactional
 */
public enum Isolation {

	/**
	 * Use the isolation level of the counter service, as defined by the portal
	 * properties.
	 */
	COUNTER(TransactionDefinition.ISOLATION_COUNTER),
	/**
	 * Use the default isolation level of the underlying data store.
	 */
	DEFAULT(TransactionDefinition.ISOLATION_DEFAULT),
	/**
	 * Use the isolation level of the portal, as defined by the portal
	 * properties.
	 */
	PORTAL(TransactionDefinition.ISOLATION_PORTAL),
	/**
	 * Prevent dirty reads; allow non-repeatable reads and phantom reads.
	 */
	READ_COMMITTED(TransactionDefinition.ISOLATION_READ_COMMITTED),
	/**
	 * Allow dirty reads, non-repeatable reads, and phantom reads.
	 */
	READ_UNCOMMITTED(TransactionDefinition.ISOLATION_READ_UNCOMMITTED),
	/**
	 * Prevent dirty reads and non-repeatable reads; allow phantom reads.
	 */
	REPEATABLE_READ(TransactionDefinition.ISOLATION_REPEATABLE_READ),
	/**
	 * Prevent dirty reads, non-repeatable reads, and phantom reads.
	 */
	SERIALIZABLE(TransactionDefinition.ISOLATION_SERIALIZABLE);

	public static Isolation getIsolation(int value) {
		return _isolations.get(value);
	}

	public int value() {
		return _value;
	}

	private Isolation(int value) {
		_value = value;
	}

	private static final Map<Integer, Isolation> _isolations =
		new HashMap<Integer, Isolation>() {
			{
				for (Isolation isolation : EnumSet.allOf(Isolation.class)) {
					put(isolation._value, isolation);
				}
			}
		};

	private final int _value;

}