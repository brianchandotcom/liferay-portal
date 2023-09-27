/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.util.comparator;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.finder.AMImageQueryBuilder;
import com.liferay.adaptive.media.image.internal.configuration.AMImageAttributeMapping;
import com.liferay.adaptive.media.image.internal.processor.AMImage;
import com.liferay.adaptive.media.processor.AMProcessor;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class AMAttributeDistanceComparatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_multiAMAttributeDistanceComparator = new AMAttributeDistanceComparator(
			HashMapBuilder.
				<AMAttribute<AMProcessor<FileVersion>, ?>,
				 AMImageQueryBuilder.SortOrder>put(
					AMAttribute.getContentLengthAMAttribute(),
					AMImageQueryBuilder.SortOrder.ASC
				).put(
					AMAttribute.getFileNameAMAttribute(),
					AMImageQueryBuilder.SortOrder.DESC
				).build());
	}

	@Test
	public void testSortDifferentMediaByMultipleAttributes() {
		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia1 =
			_createAdaptiveMedia(
				AMAttribute.getContentLengthAMAttribute(), 10L,
				AMAttribute.getFileNameAMAttribute(), "zzz");
		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia2 =
			_createAdaptiveMedia(
				AMAttribute.getContentLengthAMAttribute(), 10L,
				AMAttribute.getFileNameAMAttribute(), "aaa");

		long result = _multiAMAttributeDistanceComparator.compare(
			adaptiveMedia1, adaptiveMedia2);

		Assert.assertEquals(-25, result);
	}

	@Test
	public void testSortDifferentMediaByMultipleAttributesInverse() {
		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia1 =
			_createAdaptiveMedia(
				AMAttribute.getContentLengthAMAttribute(), 10L,
				AMAttribute.getFileNameAMAttribute(), "zzz");
		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia2 =
			_createAdaptiveMedia(
				AMAttribute.getContentLengthAMAttribute(), 10L,
				AMAttribute.getFileNameAMAttribute(), "aaa");

		long result = _multiAMAttributeDistanceComparator.compare(
			adaptiveMedia2, adaptiveMedia1);

		Assert.assertEquals(25, result);
	}

	@Test
	public void testSortDifferentMediaByOneAttribute() {
		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia1 =
			_createAdaptiveMedia(
				AMAttribute.getContentLengthAMAttribute(), 10L);
		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia2 =
			_createAdaptiveMedia(
				AMAttribute.getContentLengthAMAttribute(), 20L);

		long result = _singleAMAttributeDistanceComparator.compare(
			adaptiveMedia1, adaptiveMedia2);

		Assert.assertEquals(-10, result);
	}

	@Test
	public void testSortDifferentMediaByOneAttributeInverse() {
		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia1 =
			_createAdaptiveMedia(
				AMAttribute.getContentLengthAMAttribute(), 10L);
		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia2 =
			_createAdaptiveMedia(
				AMAttribute.getContentLengthAMAttribute(), 20L);

		long result = _singleAMAttributeDistanceComparator.compare(
			adaptiveMedia2, adaptiveMedia1);

		Assert.assertEquals(10, result);
	}

	@Test
	public void testSortEqualMediaByMultipleAttributes() {
		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia1 =
			_createAdaptiveMedia(
				AMAttribute.getContentLengthAMAttribute(), 10L,
				AMAttribute.getFileNameAMAttribute(), "aaa");
		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia2 =
			_createAdaptiveMedia(
				AMAttribute.getContentLengthAMAttribute(), 10L,
				AMAttribute.getFileNameAMAttribute(), "aaa");

		long result = _singleAMAttributeDistanceComparator.compare(
			adaptiveMedia1, adaptiveMedia2);

		Assert.assertEquals(0, result);
	}

	@Test
	public void testSortEqualMediaByOneAttribute() {
		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia1 =
			_createAdaptiveMedia(
				AMAttribute.getContentLengthAMAttribute(), 10L);
		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia2 =
			_createAdaptiveMedia(
				AMAttribute.getContentLengthAMAttribute(), 10L);

		long result = _singleAMAttributeDistanceComparator.compare(
			adaptiveMedia1, adaptiveMedia2);

		Assert.assertEquals(0, result);
	}

	private <S, T> AdaptiveMedia<AMProcessor<FileVersion>> _createAdaptiveMedia(
		AMAttribute<AMProcessor<FileVersion>, S> amAttribute1, S value1,
		AMAttribute<AMProcessor<FileVersion>, T> amAttribute2, T value2) {

		AMImageAttributeMapping amImageAttributeMapping =
			AMImageAttributeMapping.fromProperties(
				HashMapBuilder.put(
					amAttribute1.getName(), String.valueOf(value1)
				).put(
					amAttribute2.getName(), String.valueOf(value2)
				).build());

		return new AMImage(() -> null, amImageAttributeMapping, null);
	}

	private <T> AdaptiveMedia<AMProcessor<FileVersion>> _createAdaptiveMedia(
		AMAttribute<AMProcessor<FileVersion>, T> amAttribute, T value) {

		AMImageAttributeMapping amImageAttributeMapping =
			AMImageAttributeMapping.fromProperties(
				Collections.singletonMap(
					amAttribute.getName(), String.valueOf(value)));

		return new AMImage(() -> null, amImageAttributeMapping, null);
	}

	private AMAttributeDistanceComparator _multiAMAttributeDistanceComparator;
	private final AMAttributeDistanceComparator
		_singleAMAttributeDistanceComparator =
			new AMAttributeDistanceComparator(
				AMAttribute.getContentLengthAMAttribute());

}