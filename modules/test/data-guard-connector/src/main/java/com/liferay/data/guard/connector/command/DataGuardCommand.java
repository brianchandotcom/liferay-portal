/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.guard.connector.command;

import com.liferay.portal.kernel.test.rule.DataGuardTestRuleUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Matthew Tambara
 */
public interface DataGuardCommand extends Serializable {

	public static DataGuardCommand endCapture(long id, String testClassName) {
		return dataBagMap -> {
			DataGuardTestRuleUtil.DataBag dataBag = dataBagMap.remove(id);

			if (dataBag == null) {
				throw new IllegalArgumentException(
					"No data bag found in test " + testClassName);
			}

			DataGuardTestRuleUtil.afterClass(dataBag, testClassName);

			return null;
		};
	}

	public static DataGuardCommand startCapture() {
		return dataBagMap -> DataGuardTestRuleUtil.beforeClass();
	}

	public DataGuardTestRuleUtil.DataBag execute(
			Map<Long, DataGuardTestRuleUtil.DataBag> dataBagMap)
		throws Throwable;

}