/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test.util;

import com.liferay.petra.string.StringBundler;

import org.junit.Assert;

/**
 * @author Vendel Toreki
 */
public class ItemCountPerformanceTestTimer extends PerformanceTestTimer {

	public ItemCountPerformanceTestTimer(
		int count, Class<?> clazz, String name, long maxTime) {

		this(
			count, getInvokerName(clazz, name), System.currentTimeMillis(),
			maxTime);
	}

	public ItemCountPerformanceTestTimer(int count, long maxTime) {
		this(
			count, getInvokerName(null, null), System.currentTimeMillis(),
			maxTime);
	}

	public ItemCountPerformanceTestTimer(int count, String name, int maxTime) {
		this(
			count, getInvokerName(null, name), System.currentTimeMillis(),
			maxTime);
	}

	@Override
	public void close() {
		long delta = System.currentTimeMillis() - startTime;

		double speed =
			(delta > 0) ? (double)(_count * 1000) / (double)delta : Double.NaN;

		System.out.println(
			StringBundler.concat(
				"Completed ", name, " in ", delta, " ms, speed: ",
				String.format("%.2f", speed), " items/s"));

		Assert.assertTrue(
			StringBundler.concat(
				"Completed in ", delta,
				"ms, but the expected completion time should be less than ",
				maxTime, "ms"),
			delta < maxTime);
	}

	protected ItemCountPerformanceTestTimer(
		int count, String name, long startTime, long maxTime) {

		super(name, startTime, maxTime);

		_count = count;
	}

	private int _count;

}