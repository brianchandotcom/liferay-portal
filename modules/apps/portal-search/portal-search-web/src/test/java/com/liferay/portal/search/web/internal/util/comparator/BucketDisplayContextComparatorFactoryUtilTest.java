/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2025-06
 */

package com.liferay.portal.search.web.internal.util.comparator;

import com.liferay.portal.kernel.util.LocaleUtil;
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
		Comparator<BucketDisplayContext> comparator =
			BucketDisplayContextComparatorFactoryUtil.
				getBucketDisplayContextComparator("key:asc");

		List<BucketDisplayContext> bucketDisplayContexts =
			_createBucketDisplayContextsWithBucketText();

		bucketDisplayContexts.sort(comparator);

		List<String> expectedOrder = List.of(
			"1", "01", "2", "11", "albert", "Allen", "Elizabeth", "Norman",
			"Taylor", "tom");

		for (int i = 0; i < expectedOrder.size(); i++) {
			Assert.assertEquals(
				expectedOrder.get(i),
				bucketDisplayContexts.get(
					i
				).getBucketText());
		}
	}

	@Test
	public void testGetBucketDisplayContextComparatorByBucketTextDesc() {
		Comparator<BucketDisplayContext> comparator =
			BucketDisplayContextComparatorFactoryUtil.
				getBucketDisplayContextComparator("key:desc");

		List<BucketDisplayContext> bucketDisplayContexts =
			_createBucketDisplayContextsWithBucketText();

		bucketDisplayContexts.sort(comparator);

		List<String> expectedOrder = List.of(
			"tom", "Taylor", "Norman", "Elizabeth", "Allen", "albert", "11",
			"2", "01", "1");

		for (int i = 0; i < expectedOrder.size(); i++) {
			Assert.assertEquals(
				expectedOrder.get(i),
				bucketDisplayContexts.get(
					i
				).getBucketText());
		}
	}

	@Test
	public void testGetBucketDisplayContextComparatorByFrequencyAsc() {
		Comparator<BucketDisplayContext> comparator =
			BucketDisplayContextComparatorFactoryUtil.
				getBucketDisplayContextComparator("count:asc");

		List<BucketDisplayContext> bucketDisplayContexts =
			_createBucketDisplayContextsWithFrequency();

		bucketDisplayContexts.sort(comparator);

		List<Integer> expectedOrder = List.of(1, 2, 11, 22);

		for (int i = 0; i < expectedOrder.size(); i++) {
			Assert.assertEquals(
				expectedOrder.get(
					i
				).intValue(),
				bucketDisplayContexts.get(
					i
				).getFrequency());
		}
	}

	@Test
	public void testGetBucketDisplayContextComparatorByFrequencyDesc() {
		Comparator<BucketDisplayContext> comparator =
			BucketDisplayContextComparatorFactoryUtil.
				getBucketDisplayContextComparator("count:desc");

		List<BucketDisplayContext> bucketDisplayContexts =
			_createBucketDisplayContextsWithFrequency();

		bucketDisplayContexts.sort(comparator);

		List<Integer> expectedOrder = List.of(22, 11, 2, 1);

		for (int i = 0; i < expectedOrder.size(); i++) {
			Assert.assertEquals(
				expectedOrder.get(
					i
				).intValue(),
				bucketDisplayContexts.get(
					i
				).getFrequency());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetBucketDisplayContextComparatorShouldThrowException() {
		BucketDisplayContextComparatorFactoryUtil.
			getBucketDisplayContextComparator("invalid");
	}

	private List<BucketDisplayContext>
		_createBucketDisplayContextsWithBucketText() {

		return Arrays.asList(
			_createBucketDisplayContextWithBucketText("2"),
			_createBucketDisplayContextWithBucketText("tom"),
			_createBucketDisplayContextWithBucketText("01"),
			_createBucketDisplayContextWithBucketText("Elizabeth"),
			_createBucketDisplayContextWithBucketText("albert"),
			_createBucketDisplayContextWithBucketText("11"),
			_createBucketDisplayContextWithBucketText("Taylor"),
			_createBucketDisplayContextWithBucketText("Norman"),
			_createBucketDisplayContextWithBucketText("1"),
			_createBucketDisplayContextWithBucketText("Allen"));
	}

	private List<BucketDisplayContext>
		_createBucketDisplayContextsWithFrequency() {

		return Arrays.asList(
			_createBucketDisplayContextWithFrequency(2),
			_createBucketDisplayContextWithFrequency(22),
			_createBucketDisplayContextWithFrequency(11),
			_createBucketDisplayContextWithFrequency(1));
	}

	private BucketDisplayContext _createBucketDisplayContextWithBucketText(
		String bucketText) {

		BucketDisplayContext bucketDisplayContext = new BucketDisplayContext();

		bucketDisplayContext.setBucketText(
			String.format("Bucket with bucketText %s", bucketText));
		bucketDisplayContext.setBucketText(bucketText);
		bucketDisplayContext.setLocale(LocaleUtil.getDefault());

		return bucketDisplayContext;
	}

	private BucketDisplayContext _createBucketDisplayContextWithFrequency(
		int frequency) {

		BucketDisplayContext bucketDisplayContext = new BucketDisplayContext();

		bucketDisplayContext.setBucketText(
			String.format("Bucket with frequency %s", frequency));
		bucketDisplayContext.setFrequency(frequency);
		bucketDisplayContext.setLocale(LocaleUtil.getDefault());

		return bucketDisplayContext;
	}

}