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
					"No dataBag found in test " + testClassName);
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