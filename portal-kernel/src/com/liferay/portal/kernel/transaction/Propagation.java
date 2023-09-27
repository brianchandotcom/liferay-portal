/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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