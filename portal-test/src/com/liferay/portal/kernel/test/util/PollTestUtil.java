/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test.util;

import com.liferay.petra.function.UnsafeSupplier;

/**
 * @author Rubén Pulido
 */
public class PollTestUtil {

	public static <T> T pollUntilNotNull(
			UnsafeSupplier<T, Exception> unsafeSupplier)
		throws Exception {

		long deadline = System.currentTimeMillis() + 30_000;

		while (System.currentTimeMillis() < deadline) {
			T value = unsafeSupplier.get();

			if (value != null) {
				return value;
			}

			Thread.sleep(500);
		}

		throw new AssertionError(
			"Supplied value did not become non-null within 30 seconds");
	}

}