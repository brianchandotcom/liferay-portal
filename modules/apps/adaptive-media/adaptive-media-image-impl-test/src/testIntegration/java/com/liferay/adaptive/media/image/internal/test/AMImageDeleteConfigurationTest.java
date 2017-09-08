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

package com.liferay.adaptive.media.image.internal.test;

import com.liferay.adaptive.media.exception.AMImageConfigurationException.InvalidStateAMImageConfigurationException;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.internal.test.util.DestinationReplacer;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalServiceUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
@Sync
public class AMImageDeleteConfigurationTest
	extends BaseAMImageConfigurationTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testDeleteAllConfigurationEntries() throws Exception {
		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2", properties);

		Optional<AMImageConfigurationEntry>
			firstAMImageConfigurationEntryOptional =
				amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstAMImageConfigurationEntryOptional);

		Optional<AMImageConfigurationEntry>
			secondAMImageConfigurationEntryOptional =
				amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondAMImageConfigurationEntryOptional);

		amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		amImageConfigurationHelper.deleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		amImageConfigurationHelper.deleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		firstAMImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(firstAMImageConfigurationEntryOptional.isPresent());

		secondAMImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(secondAMImageConfigurationEntryOptional.isPresent());
	}

	@Test
	public void testDeleteConfigurationEntryWithExistingDisabledConfiguration()
		throws Exception {

		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2", properties);

		amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		amImageConfigurationHelper.deleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertDisabled(amImageConfigurationEntryOptional);

		amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(amImageConfigurationEntryOptional.isPresent());
	}

	@Test
	public void testDeleteConfigurationEntryWithImages() throws Exception {
		try (DestinationReplacer destinationReplacer = new DestinationReplacer(
				"liferay/am_processor")) {

			AMImageConfigurationHelper amImageConfigurationHelper =
				serviceTracker.getService();

			Map<String, String> properties = new HashMap<>();

			properties.put("max-height", "100");
			properties.put("max-width", "100");

			AMImageConfigurationEntry amImageConfigurationEntry =
				amImageConfigurationHelper.addAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "one", "desc", "1",
					properties);

			FileEntry fileEntry = _addFileEntry();

			FileVersion fileVersion = fileEntry.getFileVersion();

			Assert.assertNotNull(
				AMImageEntryLocalServiceUtil.fetchAMImageEntry(
					amImageConfigurationEntry.getUUID(),
					fileVersion.getFileVersionId()));

			amImageConfigurationHelper.disableAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry.getUUID());

			amImageConfigurationHelper.deleteAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry.getUUID());

			Assert.assertNull(
				AMImageEntryLocalServiceUtil.fetchAMImageEntry(
					amImageConfigurationEntry.getUUID(),
					fileVersion.getFileVersionId()));
		}
	}

	@Test
	public void testDeleteDeletedConfigurationEntry() throws Exception {
		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertEnabled(amImageConfigurationEntryOptional);

		amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		amImageConfigurationHelper.deleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(amImageConfigurationEntryOptional.isPresent());

		amImageConfigurationHelper.deleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(amImageConfigurationEntryOptional.isPresent());
	}

	@Test(expected = InvalidStateAMImageConfigurationException.class)
	public void testDeleteEnabledConfigurationEntry() throws Exception {
		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertEnabled(amImageConfigurationEntryOptional);

		amImageConfigurationHelper.deleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");
	}

	@Test
	public void testDeleteFirstConfigurationEntry() throws Exception {
		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2", properties);

		Optional<AMImageConfigurationEntry>
			firstAMImageConfigurationEntryOptional =
				amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstAMImageConfigurationEntryOptional);

		Optional<AMImageConfigurationEntry>
			secondAMImageConfigurationEntryOptional =
				amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondAMImageConfigurationEntryOptional);

		amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		amImageConfigurationHelper.deleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		firstAMImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(firstAMImageConfigurationEntryOptional.isPresent());

		secondAMImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondAMImageConfigurationEntryOptional);
	}

	@Test
	public void testDeleteSecondConfigurationEntry() throws Exception {
		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2", properties);

		Optional<AMImageConfigurationEntry>
			firstAMImageConfigurationEntryOptional =
				amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstAMImageConfigurationEntryOptional);

		Optional<AMImageConfigurationEntry>
			secondAMImageConfigurationEntryOptional =
				amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondAMImageConfigurationEntryOptional);

		amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		amImageConfigurationHelper.deleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		firstAMImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstAMImageConfigurationEntryOptional);

		secondAMImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(secondAMImageConfigurationEntryOptional.isPresent());
	}

	@Test
	public void testDeleteUniqueConfigurationEntry() throws Exception {
		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertEnabled(amImageConfigurationEntryOptional);

		amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		amImageConfigurationHelper.deleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(amImageConfigurationEntryOptional.isPresent());
	}

	@Test
	public void testForceDeleteAllConfigurationEntries() throws Exception {
		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2", properties);

		Optional<AMImageConfigurationEntry>
			firstAMImageConfigurationEntryOptional =
				amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstAMImageConfigurationEntryOptional);

		Optional<AMImageConfigurationEntry>
			secondAMImageConfigurationEntryOptional =
				amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondAMImageConfigurationEntryOptional);

		amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		firstAMImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(firstAMImageConfigurationEntryOptional.isPresent());

		secondAMImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(secondAMImageConfigurationEntryOptional.isPresent());
	}

	@Test
	public void testForceDeleteConfigurationEntryWithExistingDisabledConfiguration()
		throws Exception {

		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2", properties);

		amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertDisabled(amImageConfigurationEntryOptional);

		amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(amImageConfigurationEntryOptional.isPresent());
	}

	@Test
	public void testForceDeleteConfigurationEntryWithImages() throws Exception {
		try (DestinationReplacer destinationReplacer = new DestinationReplacer(
				"liferay/am_processor")) {

			AMImageConfigurationHelper amImageConfigurationHelper =
				serviceTracker.getService();

			Map<String, String> properties = new HashMap<>();

			properties.put("max-height", "100");
			properties.put("max-width", "100");

			AMImageConfigurationEntry amImageConfigurationEntry =
				amImageConfigurationHelper.addAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "one", "desc", "1",
					properties);

			FileEntry fileEntry = _addFileEntry();

			FileVersion fileVersion = fileEntry.getFileVersion();

			Assert.assertNotNull(
				AMImageEntryLocalServiceUtil.fetchAMImageEntry(
					amImageConfigurationEntry.getUUID(),
					fileVersion.getFileVersionId()));

			amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry.getUUID());

			Assert.assertNull(
				AMImageEntryLocalServiceUtil.fetchAMImageEntry(
					amImageConfigurationEntry.getUUID(),
					fileVersion.getFileVersionId()));
		}
	}

	@Test
	public void testForceDeleteDeletedConfigurationEntry() throws Exception {
		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertEnabled(amImageConfigurationEntryOptional);

		amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(amImageConfigurationEntryOptional.isPresent());

		amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(amImageConfigurationEntryOptional.isPresent());
	}

	@Test
	public void testForceDeleteEnabledConfigurationEntry() throws Exception {
		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertEnabled(amImageConfigurationEntryOptional);

		amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		amImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(amImageConfigurationEntryOptional.isPresent());
	}

	@Test
	public void testForceDeleteFirstConfigurationEntry() throws Exception {
		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2", properties);

		Optional<AMImageConfigurationEntry>
			firstAMImageConfigurationEntryOptional =
				amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstAMImageConfigurationEntryOptional);

		Optional<AMImageConfigurationEntry>
			secondAMImageConfigurationEntryOptional =
				amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondAMImageConfigurationEntryOptional);

		amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		firstAMImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(firstAMImageConfigurationEntryOptional.isPresent());

		secondAMImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondAMImageConfigurationEntryOptional);
	}

	@Test
	public void testForceDeleteSecondConfigurationEntry() throws Exception {
		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2", properties);

		Optional<AMImageConfigurationEntry>
			firstAMImageConfigurationEntryOptional =
				amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstAMImageConfigurationEntryOptional);

		Optional<AMImageConfigurationEntry>
			secondAMImageConfigurationEntryOptional =
				amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondAMImageConfigurationEntryOptional);

		amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		firstAMImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstAMImageConfigurationEntryOptional);

		secondAMImageConfigurationEntryOptional =
			amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(secondAMImageConfigurationEntryOptional.isPresent());
	}

	@Test
	public void testSendsAMessageToTheMessageBus() throws Exception {
		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "onedesc", "1",
				properties);

		amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		List<Message> messages = collectConfigurationMessages(
			() -> amImageConfigurationHelper.deleteAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1"));

		Assert.assertEquals(messages.toString(), 1, messages.size());

		Message message = messages.get(0);

		AMImageConfigurationEntry deletedAMImageConfigurationEntry =
			(AMImageConfigurationEntry)message.getPayload();

		Assert.assertEquals(
			amImageConfigurationEntry.getName(),
			deletedAMImageConfigurationEntry.getName());
		Assert.assertEquals(
			amImageConfigurationEntry.getDescription(),
			deletedAMImageConfigurationEntry.getDescription());
		Assert.assertEquals(
			amImageConfigurationEntry.getUUID(),
			deletedAMImageConfigurationEntry.getUUID());
		Assert.assertEquals(
			amImageConfigurationEntry.getProperties(),
			deletedAMImageConfigurationEntry.getProperties());
	}

	private FileEntry _addFileEntry() throws Exception {
		return DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			FileUtil.getBytes(
				AMImageDeleteConfigurationTest.class, _PNG_IMAGE_FILE_PATH),
			new ServiceContext());
	}

	private static final String _PNG_IMAGE_FILE_PATH =
		"/com/liferay/adaptive/media/image/internal/test/dependencies" +
			"/image.jpg";

	@DeleteAfterTestRun
	private Group _group;

}