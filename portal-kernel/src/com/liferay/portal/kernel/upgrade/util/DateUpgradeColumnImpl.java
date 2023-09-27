/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.util;

import com.liferay.petra.string.StringPool;

import java.sql.Types;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class DateUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public DateUpgradeColumnImpl(String name) {
		super(name, Types.TIMESTAMP);
	}

	@Override
	public Object getNewValue(Object oldValue) throws Exception {
		if (StringPool.NULL.equals(oldValue)) {
			return 0;
		}

		Date oldDate = (Date)oldValue;

		return oldDate.getTime();
	}

}