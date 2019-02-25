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

package com.liferay.document.library.internal.versioning;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Luiz Marins
 */
@RunWith(PowerMockRunner.class)
public class AssetVersioningPolicyTest {

	@Before
	public void setUp() {
		Mockito.when(_newVersion.getFileEntryId()).thenReturn(_FILE_ENTRY_ID);

		_mockGetEntryTags();
	}

	@Test
	public void testIncludeNewTagIncreaseNumber() {
		_mockGetEntryTags(_TAG_1, _TAG_2);

		Optional<DLVersionNumberIncrease> numberIncrease =
			_assetVersioningPolicy.computeDLVersionNumberIncrease(
				_previousVersion, _newVersion, new String[] {_TAG_1});

		_assertVersionIncreased(numberIncrease);
	}

	@Test
	public void testSetSameTagsDoesNotIncreaseNumber() {
		_mockGetEntryTags(_TAG_1);

		Optional<DLVersionNumberIncrease> numberIncrease =
			_assetVersioningPolicy.computeDLVersionNumberIncrease(
				_previousVersion, _newVersion, new String[] {_TAG_1});

		_assertVersionNotIncreased(numberIncrease);
	}

	@Test
	public void testSetSameTagsOutOfOrderDoesNotIncreaseNumber() {
		_mockGetEntryTags(_TAG_2, _TAG_1);

		Optional<DLVersionNumberIncrease> numberIncrease =
			_assetVersioningPolicy.computeDLVersionNumberIncrease(
				_previousVersion, _newVersion, new String[] {_TAG_1, _TAG_2});

		_assertVersionNotIncreased(numberIncrease);
	}

	@Test
	public void testSetTagIncreaseNumber() {
		Optional<DLVersionNumberIncrease> numberIncrease =
			_assetVersioningPolicy.computeDLVersionNumberIncrease(
				_previousVersion, _newVersion, new String[] {_TAG_1});

		_assertVersionIncreased(numberIncrease);
	}

	@Test
	public void testWithoutAnyTagsDoesNotIncreaseNumber() {
		Optional<DLVersionNumberIncrease> numberIncrease =
			_assetVersioningPolicy.computeDLVersionNumberIncrease(
				_previousVersion, _newVersion, null);

		_assertVersionNotIncreased(numberIncrease);
	}

	private void _assertVersionIncreased(
		Optional<DLVersionNumberIncrease> numberIncrease) {

		Assert.assertTrue(
			"It must increase the number with no asset tags update",
			numberIncrease.isPresent());

		Assert.assertEquals(
			"It must must increase the number with no asset tags update",
			DLVersionNumberIncrease.AUTOMATIC, numberIncrease.get());
	}

	private void _assertVersionNotIncreased(
		Optional<DLVersionNumberIncrease> numberIncrease) {

		Assert.assertFalse(
			"It mustn't increase the number with no asset tags update",
			numberIncrease.isPresent());
	}

	private List<AssetTag> _mockAssetTags(String... tags) {
		List<AssetTag> assetTags = new ArrayList<>();

		for (String tag : tags) {
			AssetTag assetTag = Mockito.mock(AssetTag.class);

			Mockito.when(assetTag.getName()).thenReturn(tag);
			assetTags.add(assetTag);
		}

		return assetTags;
	}

	private void _mockGetEntryTags(String... tag) {
		List<AssetTag> tags = _mockAssetTags(tag);

		Mockito.when(
			_assetTagLocalService.getEntryTags(_FILE_ENTRY_ID)
		).thenReturn(
			tags
		);
	}

	private static final long _FILE_ENTRY_ID = 1;

	private static final String _TAG_1 = "tag1";

	private static final String _TAG_2 = "tag2";

	@Mock
	private AssetTagLocalService _assetTagLocalService;

	@InjectMocks
	private final AssetVersioningPolicy _assetVersioningPolicy =
		new AssetVersioningPolicy();

	@Mock
	private DLFileVersion _newVersion;

	@Mock
	private DLFileVersion _previousVersion;

}