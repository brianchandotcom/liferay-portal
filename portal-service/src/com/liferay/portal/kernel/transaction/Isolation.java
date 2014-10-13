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
 * @author Michael Young
 * @author Shuyang Zhou
 */
public enum Isolation {

	COUNTER(TransactionDefinition.ISOLATION_COUNTER),
	DEFAULT(TransactionDefinition.ISOLATION_DEFAULT),
	PORTAL(TransactionDefinition.ISOLATION_PORTAL),
	READ_COMMITTED(TransactionDefinition.ISOLATION_READ_COMMITTED),
	READ_UNCOMMITTED(TransactionDefinition.ISOLATION_READ_UNCOMMITTED),
	REPEATABLE_READ(TransactionDefinition.ISOLATION_REPEATABLE_READ),
	SERIALIZABLE(TransactionDefinition.ISOLATION_SERIALIZABLE);

	public static Isolation getIsolation(int value) {
		return _isolations.get(value);
	}

	Isolation(int value) {
		_value = value;
	}

	public int value() {
		return _value;
	}

	private static final Map<Integer, Isolation> _isolations =
		new HashMap<Integer, Isolation>();

	static {
		for (Isolation isolation : EnumSet.allOf(Isolation.class)) {
			_isolations.put(isolation._value, isolation);
		}
	}

	private final int _value;

}