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

package com.liferay.portal.kernel.test;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Shuyang Zhou
 */
public class BaseTestRule<C, M> implements TestRule {

	@Override
	public final Statement apply(
		final Statement statement, final Description description) {

		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				String methodName = description.getMethodName();

				C c = null;
				M m = null;

				if (methodName == null) {
					c = beforeClass(description);
				}
				else {
					m = beforeMethod(description);
				}

				try {
					statement.evaluate();
				}
				finally {
					if (methodName == null) {
						afterClass(description, c);
					}
					else {
						afterMethod(description, m);
					}
				}
			}

		};
	}

	protected void afterClass(Description description, C c) throws Throwable {
	}

	protected void afterMethod(Description description, M m) throws Throwable {
	}

	protected C beforeClass(Description description) throws Throwable {
		return null;
	}

	protected M beforeMethod(Description description) throws Throwable {
		return null;
	}

}