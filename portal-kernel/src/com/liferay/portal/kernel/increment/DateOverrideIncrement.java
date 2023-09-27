/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.increment;

import java.util.Date;

/**
 * @author László Csontos
 */
public class DateOverrideIncrement extends OverrideIncrement<Date> {

	public DateOverrideIncrement(Date dateValue) {
		super(dateValue);
	}

	@Override
	protected DateOverrideIncrement createOverrideIncrement(Date dateValue) {
		return new DateOverrideIncrement(dateValue);
	}

}