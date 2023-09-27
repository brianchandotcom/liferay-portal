/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.util.Arrays;

/**
 * @author Shuyang Zhou
 */
public class ThreadLocalDistributorRegistry {

	public static ThreadLocalDistributor[] getThreadLocalDistributors() {
		return _threadLocalDistributors;
	}

	protected static int addThreadLocalDistributor(
		ThreadLocalDistributor threadLocalDistributor) {

		int newLength = _threadLocalDistributors.length + 1;

		ThreadLocalDistributor[] threadLocalDistributors = Arrays.copyOf(
			_threadLocalDistributors, newLength);

		threadLocalDistributors[newLength - 1] = threadLocalDistributor;

		_threadLocalDistributors = threadLocalDistributors;

		return newLength - 1;
	}

	protected static ThreadLocalDistributor getThreadLocalDistributor(
		int index) {

		return _threadLocalDistributors[index];
	}

	private static ThreadLocalDistributor[] _threadLocalDistributors =
		new ThreadLocalDistributor[0];

}