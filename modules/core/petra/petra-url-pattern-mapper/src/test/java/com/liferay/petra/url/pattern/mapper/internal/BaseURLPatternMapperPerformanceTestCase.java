/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.url.pattern.mapper.internal;

import com.liferay.petra.url.pattern.mapper.URLPatternMapper;
import com.liferay.portal.kernel.test.performance.PerformanceTimer;

import java.util.BitSet;

import org.junit.Test;

/**
 * @author Arthur Chan
 */
public abstract class BaseURLPatternMapperPerformanceTestCase
	extends BaseURLPatternMapperTestCase {

	@Test
	public void testConsumeValues() {
		URLPatternMapper<Integer> urlPatternMapper = createURLPatternMapper(
			createValues());

		try (PerformanceTimer performanceTimer =
				new SystemOutLoggerPerformanceTimer(
					_CI_MULTIPLIER * testConsumeValuesExpectedTime(),
					"Iterated 100 thousand times")) {

			for (int i = 0; i < 100000; i++) {
				for (String urlPath : expectedURLPatternIndexesMap.keySet()) {
					urlPatternMapper.consumeValues(
						__ -> {
						},
						urlPath);
				}
			}
		}
	}

	@Test
	public void testConsumeValuesOrdered() {

		// Emulate filter chain in liferay-web.xml

		BitSet bitSet = new BitSet(128);

		URLPatternMapper<Integer> urlPatternMapper = createURLPatternMapper(
			createValues());

		try (PerformanceTimer performanceTimer =
				new SystemOutLoggerPerformanceTimer(
					_CI_MULTIPLIER * testConsumeValuesOrderedExpectedTime(),
					"Iterated 100 thousand times")) {

			for (int i = 0; i < 100000; i++) {
				for (String urlPath : expectedURLPatternIndexesMap.keySet()) {
					urlPatternMapper.consumeValues(bitSet::set, urlPath);

					for (int j = bitSet.nextSetBit(0); j >= 0;
						 j = bitSet.nextSetBit(j + 1)) {
					}
				}
			}
		}
	}

	@Test
	public void testGetValue() {
		URLPatternMapper<Integer> urlPatternMapper = createURLPatternMapper(
			createValues());

		try (PerformanceTimer performanceTimer =
				new SystemOutLoggerPerformanceTimer(
					_CI_MULTIPLIER * testGetValueExpectedTime(),
					"Iterated 100 thousand times")) {

			for (int i = 0; i < 100000; i++) {
				for (String urlPath : expectedURLPatternIndexesMap.keySet()) {
					urlPatternMapper.getValue(urlPath);
				}
			}
		}
	}

	@Test
	public void testGetValues() {
		URLPatternMapper<Integer> urlPatternMapper = createURLPatternMapper(
			createValues());

		try (PerformanceTimer performanceTimer =
				new SystemOutLoggerPerformanceTimer(
					_CI_MULTIPLIER * testGetValuesExpectedTime(),
					"Iterated 100 thousand times")) {

			for (int i = 0; i < 100000; i++) {
				for (String urlPath : expectedURLPatternIndexesMap.keySet()) {
					urlPatternMapper.getValues(urlPath);
				}
			}
		}
	}

	/**
	 * @return the expected running time of testConsumeValues on a local
	 * machine
	 */
	protected abstract int testConsumeValuesExpectedTime();

	/**
	 * @return the expected running time of testConsumeValuesOrdered on a local
	 * machine
	 */
	protected abstract int testConsumeValuesOrderedExpectedTime();

	/**
	 * @return the expected running time of testGetValue on a local machine
	 */
	protected abstract int testGetValueExpectedTime();

	/**
	 * @return the expected running time of testGetValues on a local machine
	 */
	protected abstract int testGetValuesExpectedTime();

	/**
	 * To avoid random heavy CI load
	 */
	private static final int _CI_MULTIPLIER = 3;

	private class SystemOutLoggerPerformanceTimer extends PerformanceTimer {

		public SystemOutLoggerPerformanceTimer(long maxTime, String name) {
			this(maxTime, name, System.currentTimeMillis());
		}

		protected SystemOutLoggerPerformanceTimer(
			long maxTime, String name, long startTime) {

			super(null, maxTime, name, startTime);
		}

		@Override
		protected void log(String message) {
			System.out.println(message);
		}

	}

}