/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.test.AbstractIntegrationJUnitTestRunner;
import com.liferay.portal.util.InitUtil;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * @author Miguel Pastor
 * @author Carlos Sierra
 */
public class LiferayIntegrationJUnitTestRunner
	extends AbstractIntegrationJUnitTestRunner {

	public LiferayIntegrationJUnitTestRunner(Class<?> clazz)
		throws InitializationError {

		super(clazz);
	}

	@Override
	public void initApplicationContext() {
		System.setProperty("catalina.base", ".");

		InitUtil.initWithSpring();
	}

	@Override
	protected Statement classBlock(final RunNotifier notifier) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				Thread thread = new Thread() {

					@Override
					public void run() {
						try {
							Statement classBlock =
									LiferayIntegrationJUnitTestRunner.super.
									classBlock(notifier);

							classBlock.evaluate();
						}
						catch (Throwable e) {
							error = e;
						}
					}

				};

				thread.start();

				thread.join();

				if (error != null) {
					throw error;
				}
			}

			Throwable error = null;
		};
	}

}