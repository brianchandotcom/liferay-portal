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

package com.liferay.petra.url.pattern.mapper.internal;

import com.liferay.petra.url.pattern.mapper.URLPatternMapper;

import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Arthur Chan
 */
public abstract class BaseURLPatternMapperPerformanceTestCase
	extends BaseURLPatternMapperTestCase {

	@Test
	public void testConsumeValues() {
		Consumer<Integer> indexConsumer = new Consumer<Integer>() {

			@Override
			public void accept(Integer i) {
			}

		};

		URLPatternMapper<Integer> urlPatternMapper = createURLPatternMapper(
			createValues());

		long start = System.currentTimeMillis();

		for (int i = 0; i < 100000; i++) {
			for (String urlPath : expectedURLPatternMatches.keySet()) {
				urlPatternMapper.consumeValues(urlPath, indexConsumer);
			}
		}

		long end = System.currentTimeMillis();

		long delta = end - start;

		System.out.println("Iterated 100 thousand times in " + delta + " ms");

		Assert.assertTrue(delta < 6000);
	}

	/**
	 * Performance benchmark simulation of utilizing URLPatternMapper by Filter
	 * Chain
	 */
	@Test
	public void testConsumeValuesOrdered() {

		// Current url-patterns for Filters set in liferay-web.xml is no larger
		// then 128, thus 2 longs are sufficient big enough.

		long[] indexBuckets = new long[2];

		Consumer<Integer> indexConsumer = new Consumer<Integer>() {

			@Override
			public void accept(Integer i) {
				if (i > 63) {
					indexBuckets[1] |= 1L << (i - 64);
				}
				else {
					indexBuckets[0] |= 1L << i;
				}
			}

		};

		URLPatternMapper<Integer> urlPatternMapper = createURLPatternMapper(
			createValues());

		long start = System.currentTimeMillis();

		for (int i = 0; i < 100000; i++) {
			for (String urlPath : expectedURLPatternMatches.keySet()) {
				indexBuckets[0] = 0;
				indexBuckets[1] = 0;

				urlPatternMapper.consumeValues(urlPath, indexConsumer);

				while (indexBuckets[0] > 0) {
					indexBuckets[0] &= 1L;
					indexBuckets[0] >>= 1L;
				}

				while (indexBuckets[1] > 0) {
					indexBuckets[1] &= 1L;
					indexBuckets[1] >>= 1L;
				}
			}
		}

		long end = System.currentTimeMillis();

		long delta = end - start;

		System.out.println("Iterated 100 thousand times in " + delta + " ms");

		Assert.assertTrue(delta < 6000);
	}

	@Test
	public void testGetValue() {
		URLPatternMapper<Integer> urlPatternMapper = createURLPatternMapper(
			createValues());

		long start = System.currentTimeMillis();

		for (int i = 0; i < 100000; i++) {
			for (String urlPath : expectedURLPatternMatches.keySet()) {
				urlPatternMapper.getValue(urlPath);
			}
		}

		long end = System.currentTimeMillis();

		long delta = end - start;

		System.out.println("Iterated 100 thousand times in " + delta + " ms");

		Assert.assertTrue(delta < 2000);
	}

	@Test
	public void testGetValues() {
		URLPatternMapper<Integer> urlPatternMapper = createURLPatternMapper(
			createValues());

		long start = System.currentTimeMillis();

		for (int i = 0; i < 100000; i++) {
			for (String urlPath : expectedURLPatternMatches.keySet()) {
				urlPatternMapper.getValues(urlPath);
			}
		}

		long end = System.currentTimeMillis();

		long delta = end - start;

		System.out.println("Iterated 100 thousand times in " + delta + " ms");

		Assert.assertTrue(delta < 6000);
	}

}