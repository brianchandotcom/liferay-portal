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

package com.liferay.portal.workflow.kaleo.demo.data.creator.internal.util;

import java.util.concurrent.Callable;

/**
 * @author Rafael Praxedes
 */
public class WorkflowDemoDataCreatorUtil {

	public static <T> T retry(Callable<T> callable) throws Exception {
		long deadline = System.currentTimeMillis() + 30000;

		while (true) {
			T returnedValue = callable.call();

			if (returnedValue != null) {
				return returnedValue;
			}

			if (System.currentTimeMillis() > deadline) {
				return null;
			}

			Thread.sleep(1000);
		}
	}

}