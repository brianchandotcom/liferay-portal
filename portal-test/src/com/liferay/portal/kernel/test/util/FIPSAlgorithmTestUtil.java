/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test.util;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.lang.SafeCloseable;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Lucas Miranda
 */
public class FIPSAlgorithmTestUtil {

	public static <T> void assertAlgorithmSwitch(
			UnsafeRunnable<Exception> action, String algorithm,
			UnsafeConsumer<String, Exception> algorithmCall,
			Class<T> classToMock, String fipsAlgorithm)
		throws Exception {

		try (MockedStatic<T> mockedStatic = Mockito.mockStatic(
				classToMock, Mockito.CALLS_REAL_METHODS)) {

			action.run();

			mockedStatic.verify(
				() -> algorithmCall.accept(algorithm), Mockito.atLeastOnce());
			mockedStatic.verify(
				() -> algorithmCall.accept(fipsAlgorithm), Mockito.never());
		}

		try (MockedStatic<T> mockedStatic = Mockito.mockStatic(
				classToMock, Mockito.CALLS_REAL_METHODS);
			SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"FIPS_ENABLED", true)) {

			action.run();

			mockedStatic.verify(
				() -> algorithmCall.accept(algorithm), Mockito.never());
			mockedStatic.verify(
				() -> algorithmCall.accept(fipsAlgorithm),
				Mockito.atLeastOnce());
		}
	}

}