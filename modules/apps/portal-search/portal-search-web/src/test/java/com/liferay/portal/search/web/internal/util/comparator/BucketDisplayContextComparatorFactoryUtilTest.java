/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2025-06
 */

package com.liferay.portal.search.web.internal.util.comparator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.web.internal.facet.display.context.BucketDisplayContext;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Rodrigo Guedes de Souza
 */
public class BucketDisplayContextComparatorFactoryUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetBucketDisplayContextComparatorByBucketTextAsc() {
		List<BucketDisplayContext> bucketDisplayContexts =
			_createUnsortedBucketDisplayContexts();
		Comparator<BucketDisplayContext> comparator =
			BucketDisplayContextComparatorFactoryUtil.
				getBucketDisplayContextComparator("key:asc");

		bucketDisplayContexts.sort(comparator);

		_assertOrder(
			bucketDisplayContexts,
			List.of(
				"1:1", "01:1", "2:3", "11:1", "albert:2", "Allen:1", "tom:2",
				"tom:1"));
	}

	@Test
	public void testGetBucketDisplayContextComparatorByBucketTextDesc() {
		List<BucketDisplayContext> bucketDisplayContexts =
			_createUnsortedBucketDisplayContexts();
		Comparator<BucketDisplayContext> comparator =
			BucketDisplayContextComparatorFactoryUtil.
				getBucketDisplayContextComparator("key:desc");

		bucketDisplayContexts.sort(comparator);

		_assertOrder(
			bucketDisplayContexts,
			List.of(
				"tom:2", "tom:1", "Allen:1", "albert:2", "11:1", "2:3", "01:1",
				"1:1"));
	}

	@Test
	public void testGetBucketDisplayContextComparatorByFrequencyAsc() {
		List<BucketDisplayContext> bucketDisplayContexts =
			_createUnsortedBucketDisplayContexts();
		Comparator<BucketDisplayContext> comparator =
			BucketDisplayContextComparatorFactoryUtil.
				getBucketDisplayContextComparator("count:asc");

		bucketDisplayContexts.sort(comparator);

		_assertOrder(
			bucketDisplayContexts,
			List.of(
				"1:1", "01:1", "11:1", "Allen:1", "tom:1", "albert:2", "tom:2",
				"2:3"));
	}

	@Test
	public void testGetBucketDisplayContextComparatorByFrequencyDesc() {
		List<BucketDisplayContext> bucketDisplayContexts =
			_createUnsortedBucketDisplayContexts();
		Comparator<BucketDisplayContext> comparator =
			BucketDisplayContextComparatorFactoryUtil.
				getBucketDisplayContextComparator("count:desc");

		bucketDisplayContexts.sort(comparator);

		_assertOrder(
			bucketDisplayContexts,
			List.of(
				"2:3", "albert:2", "tom:2", "1:1", "01:1", "11:1", "Allen:1",
				"tom:1"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetBucketDisplayContextComparatorShouldThrowException() {
		BucketDisplayContextComparatorFactoryUtil.
			getBucketDisplayContextComparator("invalid");
	}

	private void _assertOrder(
		List<BucketDisplayContext> bucketDisplayContexts,
		List<String> expected) {

		for (int i = 0; i < expected.size(); i++) {
			BucketDisplayContext bucketDisplayContext =
				bucketDisplayContexts.get(i);
			String[] parts = StringUtil.split(
				expected.get(i), StringPool.COLON);

			Assert.assertEquals(parts[0], bucketDisplayContext.getBucketText());
			Assert.assertEquals(
				parts[1], String.valueOf(bucketDisplayContext.getFrequency()));
		}
	}

	private BucketDisplayContext _createBucketDisplayContext(
		String bucketText, int frequency) {

		BucketDisplayContext bucketDisplayContext = new BucketDisplayContext();

		bucketDisplayContext.setBucketText(bucketText);
		bucketDisplayContext.setFrequency(frequency);
		bucketDisplayContext.setLocale(LocaleUtil.getDefault());

		return bucketDisplayContext;
	}

	private List<BucketDisplayContext> _createUnsortedBucketDisplayContexts() {
		return Arrays.asList(
			_createBucketDisplayContext("2", 3),
			_createBucketDisplayContext("tom", 1),
			_createBucketDisplayContext("01", 1),
			_createBucketDisplayContext("tom", 2),
			_createBucketDisplayContext("albert", 2),
			_createBucketDisplayContext("11", 1),
			_createBucketDisplayContext("1", 1),
			_createBucketDisplayContext("Allen", 1));
	}

}