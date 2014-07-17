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

package com.liferay.portal.test.persistence.rule;

import com.liferay.portal.util.PropsValues;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Miguel Pastor
 * @author Cristina González
 */
public class NonDelegatedHibernateSessionTestRule implements TestRule {

	@Override
	public Statement apply(final Statement statement, Description description) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				PropsValues.SPRING_HIBERNATE_SESSION_DELEGATED = false;

				try {
					statement.evaluate();
				}
				finally {
					PropsValues.SPRING_HIBERNATE_SESSION_DELEGATED = true;
				}
			}
		};
	}

}