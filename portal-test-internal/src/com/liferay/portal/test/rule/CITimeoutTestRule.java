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

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.test.rule.BaseTestRule;
import com.liferay.portal.kernel.test.rule.callback.BaseTestCallback;
import com.liferay.portal.test.rule.callback.CITimeoutTestCallback;

/**
 * @author Shuyang Zhou
 */
public class CITimeoutTestRule extends BaseTestRule<Long, Object> {

	public static final CITimeoutTestRule INSTANCE = new CITimeoutTestRule();

	public CITimeoutTestRule() {
		super(_getTestCallback());
	}

	private static BaseTestCallback<Long, Object> _getTestCallback() {
		if (System.getenv("JENKINS_HOME") != null) {
			return CITimeoutTestCallback.INSTANCE;
		}

		return new BaseTestCallback<>();
	}

}