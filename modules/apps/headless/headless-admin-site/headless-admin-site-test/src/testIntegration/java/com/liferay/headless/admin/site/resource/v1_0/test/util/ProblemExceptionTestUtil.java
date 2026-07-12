/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.petra.function.UnsafeRunnable;

import org.junit.Assert;

/**
 * @author Javier Moral
 */
public class ProblemExceptionTestUtil {

	public static void assertProblemException(
			String expectedStatus, String expectedTitle,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try {
			unsafeRunnable.run();

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals(expectedStatus, problem.getStatus());
			Assert.assertEquals(expectedTitle, problem.getTitle());
		}
	}

}