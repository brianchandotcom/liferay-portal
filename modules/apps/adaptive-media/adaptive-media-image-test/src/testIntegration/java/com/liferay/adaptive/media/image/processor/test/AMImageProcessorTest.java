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

package com.liferay.adaptive.media.image.processor.test;

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.finder.AMImageFinder;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collection;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class AMImageProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		UserTestUtil.setUser(TestPropsValues.getUser());

		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry -> true);

		for (AMImageConfigurationEntry amImageConfigurationEntry :
				amImageConfigurationEntries) {

			_amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry.getUUID());
		}

		_addTestVariant();
	}

	@Test
	public void testAddingFileEntryWithDisabledConfigurationCreatesNoMedia()
		throws Exception {

		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry -> true);

		for (AMImageConfigurationEntry amImageConfigurationEntry :
				amImageConfigurationEntries) {

			_amImageConfigurationHelper.disableAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry.getUUID());
		}

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		FileEntry fileEntry = _addNonimageFileEntry(serviceContext);

		Stream<AdaptiveMedia<AMImageProcessor>> adaptiveMediaStream =
			_amImageFinder.getAdaptiveMediaStream(
				amImageQueryBuilder -> amImageQueryBuilder.forFileEntry(
					fileEntry
				).done());

		Assert.assertEquals(0, adaptiveMediaStream.count());
	}

	@Test
	public void testAddingFileEntryWithImageCreatesMedia() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		final FileEntry fileEntry = _addImageFileEntry(serviceContext);

		Stream<AdaptiveMedia<AMImageProcessor>> adaptiveMediaStream =
			_amImageFinder.getAdaptiveMediaStream(
				amImageQueryBuilder -> amImageQueryBuilder.forFileEntry(
					fileEntry
				).done());

		Assert.assertEquals(_getVariantsCount(), adaptiveMediaStream.count());
	}

	@Test
	public void testAddingFileEntryWithNoImageCreatesNoMedia()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		FileEntry fileEntry = _addNonimageFileEntry(serviceContext);

		Stream<AdaptiveMedia<AMImageProcessor>> adaptiveMediaStream =
			_amImageFinder.getAdaptiveMediaStream(
				amImageQueryBuilder -> amImageQueryBuilder.forFileEntry(
					fileEntry
				).done());

		Assert.assertEquals(0, adaptiveMediaStream.count());
	}

	@Test
	public void testCleaningFileEntryWithImageRemovesMedia() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		final FileEntry fileEntry = _addImageFileEntry(serviceContext);

		_amImageProcessor.cleanUp(fileEntry.getLatestFileVersion(true));

		Stream<AdaptiveMedia<AMImageProcessor>> adaptiveMediaStream =
			_amImageFinder.getAdaptiveMediaStream(
				amImageQueryBuilder -> amImageQueryBuilder.forFileEntry(
					fileEntry
				).done());

		Assert.assertEquals(0, adaptiveMediaStream.count());
	}

	@Test
	public void testCleaningFileEntryWithNoImageDoesNothing() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		final FileEntry fileEntry = _addNonimageFileEntry(serviceContext);

		_amImageProcessor.cleanUp(fileEntry.getLatestFileVersion(true));

		Stream<AdaptiveMedia<AMImageProcessor>> adaptiveMediaStream =
			_amImageFinder.getAdaptiveMediaStream(
				amImageQueryBuilder -> amImageQueryBuilder.forFileEntry(
					fileEntry
				).done());

		Assert.assertEquals(0, adaptiveMediaStream.count());
	}

	private FileEntry _addImageFileEntry(ServiceContext serviceContext)
		throws Exception {

		return _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), ContentTypes.IMAGE_JPEG,
			_getImageBytes(), null, null, serviceContext);
	}

	private FileEntry _addNonimageFileEntry(ServiceContext serviceContext)
		throws Exception {

		return _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM, _getNonimageBytes(), null,
			null, serviceContext);
	}

	private void _addTestVariant() throws Exception {
		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "small", StringPool.BLANK, "0",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());
	}

	private byte[] _getImageBytes() throws Exception {
		return FileUtil.getBytes(
			AMImageProcessorTest.class,
			"/com/liferay/adaptive/media/image/image.jpg");
	}

	private byte[] _getNonimageBytes() {
		String s = RandomTestUtil.randomString();

		return s.getBytes();
	}

	private int _getVariantsCount() throws Exception {
		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				TestPropsValues.getCompanyId());

		return amImageConfigurationEntries.size();
	}

	@Inject
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Inject
	private AMImageFinder _amImageFinder;

	@Inject
	private AMImageProcessor _amImageProcessor;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@DeleteAfterTestRun
	private Group _group;

}