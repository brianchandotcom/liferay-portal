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
 * Defines a transaction boundary in relation to a current transaction.
 *
 * @author Michael Young
 * @author Shuyang Zhou
 * @see    Transactional
 */
public enum Propagation {

	/**
	 * Support a current transaction, throw an exception if none exists.
	 */
	MANDATORY(TransactionDefinition.PROPAGATION_MANDATORY),
	/**
	 * Execute within a nested transaction if a current transaction exists,
	 * behaves like PROPAGATION_REQUIRED otherwise.
	 */
	NESTED(TransactionDefinition.PROPAGATION_NESTED),
	/**
	 * Execute non-transactionally, throw an exception if a transaction exists.
	 */
	NEVER(TransactionDefinition.PROPAGATION_NEVER),
	/**
	 * Execute non-transactionally, suspend the current transaction if one
	 * exists.
	 */
	NOT_SUPPORTED(TransactionDefinition.PROPAGATION_NOT_SUPPORTED),
	/**
	 * Support a current transaction, create a new one if none exists.
	 */
	REQUIRED(TransactionDefinition.PROPAGATION_REQUIRED),
	/**
	 * Create a new transaction, and suspend the current transaction if one
	 * exists.
	 */
	REQUIRES_NEW(TransactionDefinition.PROPAGATION_REQUIRES_NEW),
	/**
	 * Support a current transaction, execute non-transactionally if none
	 * exists.
	 */
	SUPPORTS(TransactionDefinition.PROPAGATION_SUPPORTS);

	public static Propagation getPropagation(int value) {
		return _propagations.get(value);
	}

	public int value() {
		return _value;
	}

	private Propagation(int value) {
		_value = value;
	}

	private static final Map<Integer, Propagation> _propagations =
		new HashMap<Integer, Propagation>() {
			{
				for (Propagation propagation :
						EnumSet.allOf(Propagation.class)) {

					put(propagation._value, propagation);
				}
			}
		};

	private final int _value;

}