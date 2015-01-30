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

package com.liferay.portal.test;

import com.liferay.portal.kernel.test.BaseTestRule;
import com.liferay.portal.test.util.InitPersistenceTest;
import com.liferay.portal.test.util.InitPersistenceTestImpl;

import org.junit.runner.Description;

/**
 * @author Shuyang Zhou
 */
public class PersistenceTestRule extends BaseTestRule<Object, Object> {

	public static final PersistenceTestRule INSTANCE =
		new PersistenceTestRule();

	@Override
	protected void afterMethod(Description description, Object modelListeners) {
		_initPersistenceTest.release(modelListeners);
	}

	@Override
	protected Object beforeMethod(Description description) {
		return _initPersistenceTest.init();
	}

	private PersistenceTestRule() {
	}

	private static final InitPersistenceTest _initPersistenceTest =
		new InitPersistenceTestImpl();

}