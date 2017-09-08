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
import com.liferay.adaptive.media.image.test.util.DestinationReplacer;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
@Sync
public class AMImageProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_amImageConfigurationHelper = _getService(
			AMImageConfigurationHelper.class);
		_dlAppLocalService = _getService(DLAppLocalService.class);
		_amImageFinder = _getService(AMImageFinder.class);
		_amImageProcessor = _getService(AMImageProcessor.class);

		ServiceTestUtil.setUser(TestPropsValues.getUser());

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

		try (DestinationReplacer destinationReplacer = new DestinationReplacer(
				"liferay/am_processor")) {

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId());

			FileEntry fileEntry = _addNonImageFileEntry(serviceContext);

			Stream<AdaptiveMedia<AMImageProcessor>> adaptiveMediaStream =
				_amImageFinder.getAdaptiveMediaStream(
					amImageQueryBuilder -> amImageQueryBuilder.forFileEntry(
						fileEntry
					).done());

			Assert.assertEquals(0, adaptiveMediaStream.count());
		}
	}

	@Ignore
	@Test
	public void testAddingFileEntryWithImageCreatesMedia() throws Exception {
		try (DestinationReplacer destinationReplacer = new DestinationReplacer(
				"liferay/am_processor")) {

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId());

			final FileEntry fileEntry = _addImageFileEntry(serviceContext);

			Stream<AdaptiveMedia<AMImageProcessor>> adaptiveMediaStream =
				_amImageFinder.getAdaptiveMediaStream(
					amImageQueryBuilder -> amImageQueryBuilder.forFileEntry(
						fileEntry
					).done());

			Assert.assertEquals(
				_getVariantsCount(), adaptiveMediaStream.count());
		}
	}

	@Test
	public void testAddingFileEntryWithNoImageCreatesNoMedia()
		throws Exception {

		try (DestinationReplacer destinationReplacer = new DestinationReplacer(
				"liferay/am_processor")) {

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId());

			FileEntry fileEntry = _addNonImageFileEntry(serviceContext);

			Stream<AdaptiveMedia<AMImageProcessor>> adaptiveMediaStream =
				_amImageFinder.getAdaptiveMediaStream(
					amImageQueryBuilder -> amImageQueryBuilder.forFileEntry(
						fileEntry
					).done());

			Assert.assertEquals(0, adaptiveMediaStream.count());
		}
	}

	@Test
	public void testCleaningFileEntryWithImageRemovesMedia() throws Exception {
		try (DestinationReplacer destinationReplacer = new DestinationReplacer(
				"liferay/am_processor")) {

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
	}

	@Test
	public void testCleaningFileEntryWithNoImageDoesNothing() throws Exception {
		try (DestinationReplacer destinationReplacer = new DestinationReplacer(
				"liferay/am_processor")) {

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId());

			final FileEntry fileEntry = _addNonImageFileEntry(serviceContext);

			_amImageProcessor.cleanUp(fileEntry.getLatestFileVersion(true));

			Stream<AdaptiveMedia<AMImageProcessor>> adaptiveMediaStream =
				_amImageFinder.getAdaptiveMediaStream(
					amImageQueryBuilder -> amImageQueryBuilder.forFileEntry(
						fileEntry
					).done());

			Assert.assertEquals(0, adaptiveMediaStream.count());
		}
	}

	private FileEntry _addImageFileEntry(ServiceContext serviceContext)
		throws Exception {

		return _dlAppLocalService.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.IMAGE_JPEG,
			_getImageBytes(), serviceContext);
	}

	private FileEntry _addNonImageFileEntry(ServiceContext serviceContext)
		throws Exception {

		return _dlAppLocalService.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			_getNonImageBytes(), serviceContext);
	}

	private void _addTestVariant() throws Exception {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "small", StringPool.BLANK, "0",
			properties);
	}

	private byte[] _getImageBytes() throws Exception {
		return FileUtil.getBytes(
			AMImageProcessorTest.class,
			"/com/liferay/adaptive/media/image/processor/test/dependencies" +
				"/image.jpg");
	}

	private byte[] _getNonImageBytes() {
		String s = StringUtil.randomString();

		return s.getBytes();
	}

	private <T> T _getService(Class<T> clazz) {
		try {
			Registry registry = RegistryUtil.getRegistry();

			return registry.getService(clazz);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private int _getVariantsCount() throws Exception {
		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				TestPropsValues.getCompanyId());

		return amImageConfigurationEntries.size();
	}

	private AMImageConfigurationHelper _amImageConfigurationHelper;
	private AMImageFinder _amImageFinder;
	private AMImageProcessor _amImageProcessor;
	private DLAppLocalService _dlAppLocalService;

	@DeleteAfterTestRun
	private Group _group;

}