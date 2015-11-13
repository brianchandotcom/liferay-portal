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

package com.liferay.portal.kernel.test.rule.callback;

import org.junit.runner.Description;

/**
 * @author Shuyang Zhou
 */
public class BaseTestCallback<C, M> implements TestCallback<C, M> {

	@Override
	public void afterClass(Description description, C c) throws Throwable {
	}

	@Override
	public void afterMethod(Description description, M m, Object target)
		throws Throwable {
	}

	@Override
	public C beforeClass(Description description) throws Throwable {
		return null;
	}

	@Override
	public M beforeMethod(Description description, Object target)
		throws Throwable {

		return null;
	}

}