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

package com.liferay.portal.kernel.test.rule;

import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;

/**
 * @author     Shuyang Zhou
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Deprecated
public class ArquillianUtil {

	public static boolean isArquillianTest(Description description) {
		RunWith runWith = description.getAnnotation(RunWith.class);

		if (runWith == null) {
			return false;
		}

		Class<? extends Runner> runnerClass = runWith.value();

		String runnerClassName = runnerClass.getName();

		if (runnerClassName.equals(
				"com.liferay.arquillian.extension.junit.bridge.junit." +
					"Arquillian")) {

			return true;
		}

		return false;
	}

}