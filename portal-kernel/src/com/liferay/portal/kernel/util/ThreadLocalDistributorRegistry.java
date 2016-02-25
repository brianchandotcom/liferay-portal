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