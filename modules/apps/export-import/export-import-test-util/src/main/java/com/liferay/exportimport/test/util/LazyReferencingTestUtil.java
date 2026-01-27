/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.test.util;

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.lazy.referencing.LazyReferencingThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.util.function.Supplier;

/**
 * @author Carlos Correa
 */
public class LazyReferencingTestUtil {

	public static void executeWithLazyReferencingSafeCloseable(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (SafeCloseable safeCloseable = setLazyReferencingWithSafeCloseable(
				true)) {

			unsafeRunnable.run();
		}
	}

	public static SafeCloseable setLazyReferencingWithSafeCloseable(
		boolean lazyReferencing) {

		CentralizedThreadLocal<Boolean> originalCentralizedThreadLocal =
			ReflectionTestUtil.getFieldValue(
				LazyReferencingThreadLocal.class, "_enabled");

		Boolean originalValue = originalCentralizedThreadLocal.get();

		originalCentralizedThreadLocal.set(lazyReferencing);

		Supplier<Boolean> originalSupplier =
			ReflectionTestUtil.getAndSetFieldValue(
				originalCentralizedThreadLocal, "_supplier",
				() -> lazyReferencing);

		return () -> {
			originalCentralizedThreadLocal.set(originalValue);

			ReflectionTestUtil.setFieldValue(
				originalCentralizedThreadLocal, "_supplier", originalSupplier);
		};
	}

}