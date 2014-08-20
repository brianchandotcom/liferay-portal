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
public enum Propagation {

	MANDATORY(TransactionDefinition.PROPAGATION_MANDATORY),
	NEVER(TransactionDefinition.PROPAGATION_NEVER),
	NESTED(TransactionDefinition.PROPAGATION_NESTED),
	NOT_SUPPORTED(TransactionDefinition.PROPAGATION_NOT_SUPPORTED),
	REQUIRED(TransactionDefinition.PROPAGATION_REQUIRED),
	REQUIRES_NEW(TransactionDefinition.PROPAGATION_REQUIRES_NEW),
	SUPPORTS(TransactionDefinition.PROPAGATION_SUPPORTS);

	public static Propagation getPropagation(int value) {
		return _propagations.get(value);
	}

	Propagation(int value) {
		_value = value;
	}

	public int value() {
		return _value;
	}

	private static final Map<Integer, Propagation> _propagations =
		new HashMap<Integer, Propagation>();

	static {
		for (Propagation propagation : EnumSet.allOf(Propagation.class)) {
			_propagations.put(propagation._value, propagation);
		}
	}

	private final int _value;

}