/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.increment;

/**
 * @author László Csontos
 */
public class IntegerOverrideIncrement extends OverrideIncrement<Integer> {

	public IntegerOverrideIncrement(Integer integerValue) {
		super(integerValue);
	}

	@Override
	protected IntegerOverrideIncrement createOverrideIncrement(
		Integer integerValue) {

		return new IntegerOverrideIncrement(integerValue);
	}

}