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

package com.liferay.adaptive.media.image.internal.finder;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.finder.AMImageQueryBuilder;
import com.liferay.adaptive.media.image.internal.configuration.AMImageConfigurationEntryImpl;
import com.liferay.adaptive.media.image.processor.AMImageAttribute;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class AMImageQueryBuilderImplTest {

	@Test
	public void testFileEntryQueryReturnsLatestFileVersion() throws Exception {
		FileEntry fileEntry = Mockito.mock(FileEntry.class);

		_amImageQueryBuilderImpl.forFileEntry(fileEntry);

		Assert.assertEquals(
			fileEntry.getFileVersion(),
			_amImageQueryBuilderImpl.getFileVersion());
	}

	@Test
	public void testFileEntryWithAttributesQueryReturnsLatestFileVersion()
		throws Exception {

		FileEntry fileEntry = Mockito.mock(FileEntry.class);

		_amImageQueryBuilderImpl.forFileEntry(
			fileEntry
		).done();

		Assert.assertEquals(
			fileEntry.getFileVersion(),
			_amImageQueryBuilderImpl.getFileVersion());
	}

	@Test
	public void testMatchingConfigurationAttributeQuery() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		_amImageQueryBuilderImpl.forFileVersion(
			fileVersion
		).forConfiguration(
			"small"
		);

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				"small", Collections.emptyMap(), true);

		Predicate<AMImageConfigurationEntry> filter =
			_amImageQueryBuilderImpl.getConfigurationEntryFilter();

		Assert.assertTrue(filter.test(amImageConfigurationEntry));
	}

	@Test
	public void testNonmatchingConfigurationAttributeQuery() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		_amImageQueryBuilderImpl.forFileVersion(
			fileVersion
		).forConfiguration(
			"small"
		);

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), Collections.emptyMap(), true);

		Predicate<AMImageConfigurationEntry> filter =
			_amImageQueryBuilderImpl.getConfigurationEntryFilter();

		Assert.assertFalse(filter.test(amImageConfigurationEntry));
	}

	@Test
	public void testNonnullOptionalAttributeQuery() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		_amImageQueryBuilderImpl.forFileVersion(
			fileVersion
		).with(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT, Optional.of(100)
		);

		Map<AMAttribute<AMImageProcessor, ?>, Object> amAttributes =
			_amImageQueryBuilderImpl.getAMAttributes();

		Assert.assertEquals(
			100, amAttributes.get(AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullAttributeFailsWhenOrderingByIt() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		_amImageQueryBuilderImpl.forFileVersion(
			fileVersion
		).orderBy(
			null, AMImageQueryBuilder.SortOrder.DESC
		);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullAttributeValueFailsWhenQueryingAttributes() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		_amImageQueryBuilderImpl.forFileVersion(
			fileVersion
		).with(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT, (Integer)null
		);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullConfigurationStatusFails() {
		_amImageQueryBuilderImpl.withConfigurationStatus(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullConfigurationUUIDFailsWhenQueryingAttributes() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		_amImageQueryBuilderImpl.forFileVersion(
			fileVersion
		).forConfiguration(
			null
		);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullFileEntryFailsWhenQueryingAll() {
		_amImageQueryBuilderImpl.forFileEntry(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullFileEntryFailsWhenQueryingAttributes() {
		_amImageQueryBuilderImpl.forFileEntry(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullFileVersionFailsWhenQueryingAll() {
		_amImageQueryBuilderImpl.forFileVersion(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullFileVersionFailsWhenQueryingAttributes() {
		_amImageQueryBuilderImpl.forFileVersion(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullOptionalAttributeValueFailsWhenQueryingAttributes() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		_amImageQueryBuilderImpl.forFileVersion(
			fileVersion
		).with(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT, (Optional<Integer>)null
		);
	}

	private final AMImageQueryBuilderImpl _amImageQueryBuilderImpl =
		new AMImageQueryBuilderImpl();

}