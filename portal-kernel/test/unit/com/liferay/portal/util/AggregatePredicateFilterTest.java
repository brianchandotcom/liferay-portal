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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.AggregatePredicateFilter;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class AggregatePredicateFilterTest {

	@Test
	public void testAndAggregatePredicateFilter() {
		List<String> list = Arrays.asList("Bob", "Clair", "James", "Joe");

		AggregatePredicateFilter<String> aggregatePredicateFilter =
			new AggregatePredicateFilter<>(new StartsWithPredicateFilter("B"));

		aggregatePredicateFilter = aggregatePredicateFilter.or(
			new EndsWithPredicateFilter("B"));

		Assert.assertEquals(
			Arrays.asList("Bob"),
			ListUtil.filter(list, aggregatePredicateFilter));
	}

	@Test
	public void testIdentityAggregatePredicateFilter() {
		List<String> list = Arrays.asList("Bob", "Clair", "James", "Joe");

		AggregatePredicateFilter<String> aggregatePredicateFilter =
			new AggregatePredicateFilter<>(new StartsWithPredicateFilter("J"));

		Assert.assertEquals(
			Arrays.asList("James", "Joe"),
			ListUtil.filter(list, aggregatePredicateFilter));
	}

	@Test
	public void testMultipleAggregatePredicateFilter() {
		List<String> list = Arrays.asList(
			"Bob", "Carl", "Clair", "James", "Joe");

		AggregatePredicateFilter<String> aggregatePredicateFilter =
			new AggregatePredicateFilter<>(new StartsWithPredicateFilter("C"));

		aggregatePredicateFilter = aggregatePredicateFilter.and(
			new EndsWithPredicateFilter("L"));
		aggregatePredicateFilter = aggregatePredicateFilter.or(
			new EndsWithPredicateFilter("E"));

		Assert.assertEquals(
			Arrays.asList("Carl", "Joe"),
			ListUtil.filter(list, aggregatePredicateFilter));

		aggregatePredicateFilter = aggregatePredicateFilter.negate();

		Assert.assertEquals(
			Arrays.asList("Bob", "Clair", "James"),
			ListUtil.filter(list, aggregatePredicateFilter));
	}

	@Test
	public void testNegateAggregatePredicateFilter() {
		List<String> list = Arrays.asList("Bob", "Clair", "James", "Joe");

		AggregatePredicateFilter<String> aggregatePredicateFilter =
			new AggregatePredicateFilter<>(new StartsWithPredicateFilter("J"));

		aggregatePredicateFilter = aggregatePredicateFilter.negate();

		Assert.assertEquals(
			Arrays.asList("Bob", "Clair"),
			ListUtil.filter(list, aggregatePredicateFilter));
	}

	@Test
	public void testOrAggregatePredicateFilter() {
		List<String> list = Arrays.asList("Bob", "Clair", "James", "Joe");

		AggregatePredicateFilter<String> aggregatePredicateFilter =
			new AggregatePredicateFilter<>(new StartsWithPredicateFilter("J"));

		aggregatePredicateFilter = aggregatePredicateFilter.or(
			new EndsWithPredicateFilter("R"));

		Assert.assertEquals(
			Arrays.asList("Clair", "James", "Joe"),
			ListUtil.filter(list, aggregatePredicateFilter));
	}

	private static class EndsWithPredicateFilter
		implements PredicateFilter<String> {

		public EndsWithPredicateFilter(String lastLetter) {
			_lastLetter = lastLetter;
		}

		@Override
		public boolean filter(String string) {
			String lastLetter = string.substring(string.length() - 1);

			if (StringUtil.equalsIgnoreCase(lastLetter, _lastLetter)) {
				return true;
			}

			return false;
		}

		private final String _lastLetter;

	}

	private static class StartsWithPredicateFilter
		implements PredicateFilter<String> {

		public StartsWithPredicateFilter(String firstLetter) {
			_firstLetter = firstLetter;
		}

		@Override
		public boolean filter(String string) {
			String firstLetter = string.substring(0, 1);

			if (StringUtil.equalsIgnoreCase(firstLetter, _firstLetter)) {
				return true;
			}

			return false;
		}

		private final String _firstLetter;

	}

}