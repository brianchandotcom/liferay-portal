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

package com.liferay.adaptive.media.image.internal.configuration.test;

import com.liferay.adaptive.media.exception.AMImageConfigurationException.InvalidStateAMImageConfigurationException;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
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
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
public class AMImageDeleteConfigurationTest
	extends BaseAMImageConfigurationTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testDeleteAllConfigurationEntries() throws Exception {
		Map<String, String> properties = HashMapBuilder.put(
			"max-height", "100"
		).put(
			"max-width", "100"
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		properties = HashMapBuilder.put(
			"max-height", "200"
		).put(
			"max-width", "200"
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2", properties);

		Optional<AMImageConfigurationEntry>
			firstAMImageConfigurationEntryOptional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstAMImageConfigurationEntryOptional);

		Optional<AMImageConfigurationEntry>
			secondAMImageConfigurationEntryOptional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondAMImageConfigurationEntryOptional);

		_amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		_amImageConfigurationHelper.deleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		_amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		_amImageConfigurationHelper.deleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		firstAMImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(firstAMImageConfigurationEntryOptional.isPresent());

		secondAMImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(secondAMImageConfigurationEntryOptional.isPresent());
	}

	@Test
	public void testDeleteConfigurationEntryWithExistingDisabledConfiguration()
		throws Exception {

		Map<String, String> properties = HashMapBuilder.put(
			"max-height", "100"
		).put(
			"max-width", "100"
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		_amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		properties = HashMapBuilder.put(
			"max-height", "200"
		).put(
			"max-width", "200"
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2", properties);

		_amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		_amImageConfigurationHelper.deleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertDisabled(amImageConfigurationEntryOptional);

		amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(amImageConfigurationEntryOptional.isPresent());
	}

	@Test
	public void testDeleteConfigurationEntryWithImages() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1",
				HashMapBuilder.put(
					"max-height", "100"
				).put(
					"max-width", "100"
				).build());

		FileEntry fileEntry = _addFileEntry();

		FileVersion fileVersion = fileEntry.getFileVersion();

		Assert.assertNotNull(
			AMImageEntryLocalServiceUtil.fetchAMImageEntry(
				amImageConfigurationEntry.getUUID(),
				fileVersion.getFileVersionId()));

		_amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(),
			amImageConfigurationEntry.getUUID());

		_amImageConfigurationHelper.deleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(),
			amImageConfigurationEntry.getUUID());

		Assert.assertNull(
			AMImageEntryLocalServiceUtil.fetchAMImageEntry(
				amImageConfigurationEntry.getUUID(),
				fileVersion.getFileVersionId()));
	}

	@Test
	public void testDeleteDeletedConfigurationEntry() throws Exception {
		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertEnabled(amImageConfigurationEntryOptional);

		_amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		_amImageConfigurationHelper.deleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(amImageConfigurationEntryOptional.isPresent());

		_amImageConfigurationHelper.deleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(amImageConfigurationEntryOptional.isPresent());
	}

	@Test(expected = InvalidStateAMImageConfigurationException.class)
	public void testDeleteEnabledConfigurationEntry() throws Exception {
		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertEnabled(amImageConfigurationEntryOptional);

		_amImageConfigurationHelper.deleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");
	}

	@Test
	public void testDeleteFirstConfigurationEntry() throws Exception {
		Map<String, String> properties = HashMapBuilder.put(
			"max-height", "100"
		).put(
			"max-width", "100"
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		properties = HashMapBuilder.put(
			"max-height", "200"
		).put(
			"max-width", "200"
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2", properties);

		Optional<AMImageConfigurationEntry>
			firstAMImageConfigurationEntryOptional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstAMImageConfigurationEntryOptional);

		Optional<AMImageConfigurationEntry>
			secondAMImageConfigurationEntryOptional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondAMImageConfigurationEntryOptional);

		_amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		_amImageConfigurationHelper.deleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		firstAMImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(firstAMImageConfigurationEntryOptional.isPresent());

		secondAMImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondAMImageConfigurationEntryOptional);
	}

	@Test
	public void testDeleteSecondConfigurationEntry() throws Exception {
		Map<String, String> properties = HashMapBuilder.put(
			"max-height", "100"
		).put(
			"max-width", "100"
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		properties = HashMapBuilder.put(
			"max-height", "200"
		).put(
			"max-width", "200"
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2", properties);

		Optional<AMImageConfigurationEntry>
			firstAMImageConfigurationEntryOptional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstAMImageConfigurationEntryOptional);

		Optional<AMImageConfigurationEntry>
			secondAMImageConfigurationEntryOptional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondAMImageConfigurationEntryOptional);

		_amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		_amImageConfigurationHelper.deleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		firstAMImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstAMImageConfigurationEntryOptional);

		secondAMImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(secondAMImageConfigurationEntryOptional.isPresent());
	}

	@Test
	public void testDeleteUniqueConfigurationEntry() throws Exception {
		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertEnabled(amImageConfigurationEntryOptional);

		_amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		_amImageConfigurationHelper.deleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(amImageConfigurationEntryOptional.isPresent());
	}

	@Test
	public void testForceDeleteAllConfigurationEntries() throws Exception {
		Map<String, String> properties = HashMapBuilder.put(
			"max-height", "100"
		).put(
			"max-width", "100"
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		properties = HashMapBuilder.put(
			"max-height", "200"
		).put(
			"max-width", "200"
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2", properties);

		Optional<AMImageConfigurationEntry>
			firstAMImageConfigurationEntryOptional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstAMImageConfigurationEntryOptional);

		Optional<AMImageConfigurationEntry>
			secondAMImageConfigurationEntryOptional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondAMImageConfigurationEntryOptional);

		_amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		_amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		firstAMImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(firstAMImageConfigurationEntryOptional.isPresent());

		secondAMImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(secondAMImageConfigurationEntryOptional.isPresent());
	}

	@Test
	public void testForceDeleteConfigurationEntryWithExistingDisabledConfiguration()
		throws Exception {

		Map<String, String> properties = HashMapBuilder.put(
			"max-height", "100"
		).put(
			"max-width", "100"
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		_amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		properties = HashMapBuilder.put(
			"max-height", "200"
		).put(
			"max-width", "200"
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2", properties);

		_amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertDisabled(amImageConfigurationEntryOptional);

		amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(amImageConfigurationEntryOptional.isPresent());
	}

	@Test
	public void testForceDeleteConfigurationEntryWithImages() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "desc", "1",
				HashMapBuilder.put(
					"max-height", "100"
				).put(
					"max-width", "100"
				).build());

		FileEntry fileEntry = _addFileEntry();

		FileVersion fileVersion = fileEntry.getFileVersion();

		Assert.assertNotNull(
			AMImageEntryLocalServiceUtil.fetchAMImageEntry(
				amImageConfigurationEntry.getUUID(),
				fileVersion.getFileVersionId()));

		_amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(),
			amImageConfigurationEntry.getUUID());

		Assert.assertNull(
			AMImageEntryLocalServiceUtil.fetchAMImageEntry(
				amImageConfigurationEntry.getUUID(),
				fileVersion.getFileVersionId()));
	}

	@Test
	public void testForceDeleteDeletedConfigurationEntry() throws Exception {
		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertEnabled(amImageConfigurationEntryOptional);

		_amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(amImageConfigurationEntryOptional.isPresent());

		_amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(amImageConfigurationEntryOptional.isPresent());
	}

	@Test
	public void testForceDeleteEnabledConfigurationEntry() throws Exception {
		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1",
			HashMapBuilder.put(
				"max-height", "100"
			).put(
				"max-width", "100"
			).build());

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertEnabled(amImageConfigurationEntryOptional);

		_amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(amImageConfigurationEntryOptional.isPresent());
	}

	@Test
	public void testForceDeleteFirstConfigurationEntry() throws Exception {
		Map<String, String> properties = HashMapBuilder.put(
			"max-height", "100"
		).put(
			"max-width", "100"
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		properties = HashMapBuilder.put(
			"max-height", "200"
		).put(
			"max-width", "200"
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2", properties);

		Optional<AMImageConfigurationEntry>
			firstAMImageConfigurationEntryOptional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstAMImageConfigurationEntryOptional);

		Optional<AMImageConfigurationEntry>
			secondAMImageConfigurationEntryOptional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondAMImageConfigurationEntryOptional);

		_amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		firstAMImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertFalse(firstAMImageConfigurationEntryOptional.isPresent());

		secondAMImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondAMImageConfigurationEntryOptional);
	}

	@Test
	public void testForceDeleteSecondConfigurationEntry() throws Exception {
		Map<String, String> properties = HashMapBuilder.put(
			"max-height", "100"
		).put(
			"max-width", "100"
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		properties = HashMapBuilder.put(
			"max-height", "200"
		).put(
			"max-width", "200"
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2", properties);

		Optional<AMImageConfigurationEntry>
			firstAMImageConfigurationEntryOptional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstAMImageConfigurationEntryOptional);

		Optional<AMImageConfigurationEntry>
			secondAMImageConfigurationEntryOptional =
				_amImageConfigurationHelper.getAMImageConfigurationEntry(
					TestPropsValues.getCompanyId(), "2");

		assertEnabled(secondAMImageConfigurationEntryOptional);

		_amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "2");

		firstAMImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		assertEnabled(firstAMImageConfigurationEntryOptional);

		secondAMImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "2");

		Assert.assertFalse(secondAMImageConfigurationEntryOptional.isPresent());
	}

	@Test
	public void testSendsAMessageToTheMessageBus() throws Exception {
		AMImageConfigurationEntry amImageConfigurationEntry =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "onedesc", "1",
				HashMapBuilder.put(
					"max-height", "100"
				).put(
					"max-width", "100"
				).build());

		_amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		List<Message> messages = collectConfigurationMessages(
			() -> _amImageConfigurationHelper.deleteAMImageConfigurationEntry(
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

	@Override
	protected AMImageConfigurationHelper getAMImageConfigurationHelper() {
		return _amImageConfigurationHelper;
	}

	private FileEntry _addFileEntry() throws Exception {
		return DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			FileUtil.getBytes(
				AMImageDeleteConfigurationTest.class, "image.jpg"),
			null, null, new ServiceContext());
	}

	@Inject
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@DeleteAfterTestRun
	private Group _group;

}