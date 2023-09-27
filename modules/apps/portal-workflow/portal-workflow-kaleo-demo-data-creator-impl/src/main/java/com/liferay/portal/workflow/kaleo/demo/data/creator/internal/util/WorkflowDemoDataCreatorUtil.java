/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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