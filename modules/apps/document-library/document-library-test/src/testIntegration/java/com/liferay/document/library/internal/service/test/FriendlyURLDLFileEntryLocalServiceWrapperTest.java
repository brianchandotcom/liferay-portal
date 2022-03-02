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

package com.liferay.document.library.internal.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.test.util.BaseDLAppTestCase;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class FriendlyURLDLFileEntryLocalServiceWrapperTest
	extends BaseDLAppTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddFileEntriesSameTitleAddsFriendlyURLEntryUniqueTitle()
		throws Exception {

		_testWithActiveFFFriendlyURLEntryFileEntryConfiguration(
			() -> {
				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext(
						group.getGroupId(), TestPropsValues.getUserId());

				byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

				InputStream inputStream = new ByteArrayInputStream(bytes);

				DLFileEntry dlFileEntry1 =
					_dlFileEntryLocalService.addFileEntry(
						null, TestPropsValues.getUserId(), group.getGroupId(),
						group.getGroupId(), parentFolder.getFolderId(),
						RandomTestUtil.randomString(),
						ContentTypes.APPLICATION_OCTET_STREAM, "title",
						"urltitle", StringPool.BLANK, StringPool.BLANK,
						DLFileEntryTypeConstants.
							FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
						null, null, inputStream, bytes.length, null, null,
						serviceContext);

				FriendlyURLEntry friendlyURLEntry1 =
					_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
						_portal.getClassNameId(FileEntry.class),
						dlFileEntry1.getFileEntryId());

				Assert.assertNotNull(friendlyURLEntry1);

				Assert.assertEquals(
					"urltitle", friendlyURLEntry1.getUrlTitle());

				Folder folder = _dlAppService.addFolder(
					group.getGroupId(), parentFolder.getFolderId(),
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(), serviceContext);

				DLFileEntry dlFileEntry2 =
					_dlFileEntryLocalService.addFileEntry(
						null, TestPropsValues.getUserId(), group.getGroupId(),
						group.getGroupId(), folder.getFolderId(),
						RandomTestUtil.randomString(),
						ContentTypes.APPLICATION_OCTET_STREAM, "title",
						"urltitle", StringPool.BLANK, StringPool.BLANK,
						DLFileEntryTypeConstants.
							FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
						null, null, inputStream, bytes.length, null, null,
						serviceContext);

				FriendlyURLEntry friendlyURLEntry2 =
					_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
						_portal.getClassNameId(FileEntry.class),
						dlFileEntry2.getFileEntryId());

				Assert.assertNotNull(friendlyURLEntry2);

				Assert.assertEquals(
					"urltitle-1", friendlyURLEntry2.getUrlTitle());
			},
			true);
	}

	@Test
	public void testAddFileEntryAddsFriendlyURLEntry() throws Exception {
		_testWithActiveFFFriendlyURLEntryFileEntryConfiguration(
			() -> {
				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext(
						group.getGroupId(), TestPropsValues.getUserId());

				byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

				InputStream inputStream = new ByteArrayInputStream(bytes);

				DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
					null, TestPropsValues.getUserId(), group.getGroupId(),
					group.getGroupId(), parentFolder.getFolderId(),
					RandomTestUtil.randomString(),
					ContentTypes.APPLICATION_OCTET_STREAM, "title", "urltitle",
					StringPool.BLANK, StringPool.BLANK,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
					null, null, inputStream, bytes.length, null, null,
					serviceContext);

				FriendlyURLEntry friendlyURLEntry =
					_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
						_portal.getClassNameId(FileEntry.class),
						dlFileEntry.getFileEntryId());

				Assert.assertNotNull(friendlyURLEntry);

				Assert.assertEquals("urltitle", friendlyURLEntry.getUrlTitle());
			},
			true);
	}

	@Test
	public void testAddFileEntryAddsFriendlyURLEntryBlankUrlTitle()
		throws Exception {

		_testWithActiveFFFriendlyURLEntryFileEntryConfiguration(
			() -> {
				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext(
						group.getGroupId(), TestPropsValues.getUserId());

				byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

				InputStream inputStream = new ByteArrayInputStream(bytes);

				DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
					null, TestPropsValues.getUserId(), group.getGroupId(),
					group.getGroupId(), parentFolder.getFolderId(),
					RandomTestUtil.randomString(),
					ContentTypes.APPLICATION_OCTET_STREAM, "title",
					StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
					null, null, inputStream, bytes.length, null, null,
					serviceContext);

				FriendlyURLEntry friendlyURLEntry =
					_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
						_portal.getClassNameId(FileEntry.class),
						dlFileEntry.getFileEntryId());

				Assert.assertNotNull(friendlyURLEntry);

				Assert.assertEquals("title", friendlyURLEntry.getUrlTitle());
			},
			true);
	}

	@Test
	public void testAddFileEntryAddsFriendlyURLEntryNullUrlTitle()
		throws Exception {

		_testWithActiveFFFriendlyURLEntryFileEntryConfiguration(
			() -> {
				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext(
						group.getGroupId(), TestPropsValues.getUserId());

				byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

				InputStream inputStream = new ByteArrayInputStream(bytes);

				DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
					null, TestPropsValues.getUserId(), group.getGroupId(),
					group.getGroupId(), parentFolder.getFolderId(),
					RandomTestUtil.randomString(),
					ContentTypes.APPLICATION_OCTET_STREAM, "title", null,
					StringPool.BLANK, StringPool.BLANK,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
					null, null, inputStream, bytes.length, null, null,
					serviceContext);

				FriendlyURLEntry friendlyURLEntry =
					_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
						_portal.getClassNameId(FileEntry.class),
						dlFileEntry.getFileEntryId());

				Assert.assertNotNull(friendlyURLEntry);

				Assert.assertEquals("title", friendlyURLEntry.getUrlTitle());
			},
			true);
	}

	@Test
	public void testDeleteFileEntryDeletesFriendlyURLEntry() throws Exception {
		_testWithActiveFFFriendlyURLEntryFileEntryConfiguration(
			() -> {
				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext(
						group.getGroupId(), TestPropsValues.getUserId());

				byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

				InputStream inputStream = new ByteArrayInputStream(bytes);

				DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
					null, TestPropsValues.getUserId(), group.getGroupId(),
					group.getGroupId(), parentFolder.getFolderId(),
					RandomTestUtil.randomString(),
					ContentTypes.APPLICATION_OCTET_STREAM,
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(), StringPool.BLANK,
					StringPool.BLANK,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
					null, null, inputStream, bytes.length, null, null,
					serviceContext);

				FriendlyURLEntry friendlyURLEntry =
					_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
						_portal.getClassNameId(FileEntry.class),
						dlFileEntry.getFileEntryId());

				Assert.assertNotNull(friendlyURLEntry);

				_dlFileEntryLocalService.deleteFileEntry(
					dlFileEntry.getFileEntryId());

				Assert.assertNull(
					_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
						friendlyURLEntry.getFriendlyURLEntryId()));
			},
			true);
	}

	@Test
	public void testUpdateFileEntryUpdatesCreateFriendlyURLEntryIfPreviousExisted()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		InputStream inputStream = new ByteArrayInputStream(bytes);

		AtomicLong fileEntryId = new AtomicLong();

		_testWithActiveFFFriendlyURLEntryFileEntryConfiguration(
			() -> {
				DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
					null, TestPropsValues.getUserId(), group.getGroupId(),
					group.getGroupId(), parentFolder.getFolderId(),
					RandomTestUtil.randomString(),
					ContentTypes.APPLICATION_OCTET_STREAM,
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(), StringPool.BLANK,
					StringPool.BLANK,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
					null, null, inputStream, bytes.length, null, null,
					serviceContext);

				fileEntryId.set(dlFileEntry.getFileEntryId());

				FriendlyURLEntry friendlyURLEntry =
					_friendlyURLEntryLocalService.fetchMainFriendlyURLEntry(
						_portal.getClassNameId(FileEntry.class),
						fileEntryId.get());

				Assert.assertNull(friendlyURLEntry);
			},
			false);

		_testWithActiveFFFriendlyURLEntryFileEntryConfiguration(
			() -> {
				_dlFileEntryLocalService.updateFileEntry(
					group.getCreatorUserId(), fileEntryId.get(),
					StringUtil.randomString(),
					ContentTypes.APPLICATION_OCTET_STREAM,
					StringUtil.randomString(), "urltitle", StringPool.BLANK,
					StringPool.BLANK, DLVersionNumberIncrease.MAJOR,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
					Collections.emptyMap(), null, inputStream, 0, null, null,
					serviceContext);

				FriendlyURLEntry friendlyURLEntry =
					_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
						_portal.getClassNameId(FileEntry.class),
						fileEntryId.get());

				Assert.assertNotNull(friendlyURLEntry);

				Assert.assertEquals("urltitle", friendlyURLEntry.getUrlTitle());
			},
			true);
	}

	@Test
	public void testUpdateFileEntryUpdatesFriendlyURLEntry() throws Exception {
		_testWithActiveFFFriendlyURLEntryFileEntryConfiguration(
			() -> {
				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext(
						group.getGroupId(), TestPropsValues.getUserId());

				byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

				InputStream inputStream = new ByteArrayInputStream(bytes);

				DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
					null, TestPropsValues.getUserId(), group.getGroupId(),
					group.getGroupId(), parentFolder.getFolderId(),
					RandomTestUtil.randomString(),
					ContentTypes.APPLICATION_OCTET_STREAM,
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(), StringPool.BLANK,
					StringPool.BLANK,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
					null, null, inputStream, bytes.length, null, null,
					serviceContext);

				dlFileEntry = _dlFileEntryLocalService.updateFileEntry(
					group.getCreatorUserId(), dlFileEntry.getFileEntryId(),
					StringUtil.randomString(),
					ContentTypes.APPLICATION_OCTET_STREAM,
					RandomTestUtil.randomString(), "urltitle", StringPool.BLANK,
					StringPool.BLANK, DLVersionNumberIncrease.MAJOR,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
					Collections.emptyMap(), null, inputStream, 0, null, null,
					serviceContext);

				FriendlyURLEntry friendlyURLEntry =
					_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
						_portal.getClassNameId(FileEntry.class),
						dlFileEntry.getFileEntryId());

				Assert.assertEquals("urltitle", friendlyURLEntry.getUrlTitle());
			},
			true);
	}

	@Test
	public void testUpdateFileEntryUpdatesFriendlyURLEntryBlankURlTitleNotModifyPreviousUrlTitle()
		throws Exception {

		_testWithActiveFFFriendlyURLEntryFileEntryConfiguration(
			() -> {
				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext(
						group.getGroupId(), TestPropsValues.getUserId());

				byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

				InputStream inputStream = new ByteArrayInputStream(bytes);

				DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
					null, TestPropsValues.getUserId(), group.getGroupId(),
					group.getGroupId(), parentFolder.getFolderId(),
					RandomTestUtil.randomString(),
					ContentTypes.APPLICATION_OCTET_STREAM,
					RandomTestUtil.randomString(), "urltitle", StringPool.BLANK,
					StringPool.BLANK,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
					null, null, inputStream, bytes.length, null, null,
					serviceContext);

				dlFileEntry = _dlFileEntryLocalService.updateFileEntry(
					group.getCreatorUserId(), dlFileEntry.getFileEntryId(),
					StringUtil.randomString(),
					ContentTypes.APPLICATION_OCTET_STREAM,
					RandomTestUtil.randomString(), StringPool.BLANK,
					StringPool.BLANK, StringPool.BLANK,
					DLVersionNumberIncrease.MAJOR,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
					Collections.emptyMap(), null, inputStream, 0, null, null,
					serviceContext);

				FriendlyURLEntry friendlyURLEntry =
					_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
						_portal.getClassNameId(FileEntry.class),
						dlFileEntry.getFileEntryId());

				Assert.assertEquals("urltitle", friendlyURLEntry.getUrlTitle());
			},
			true);
	}

	@Test
	public void testUpdateFileEntryUpdatesFriendlyURLEntryNullURlTitleNotModifyPreviousUrlTitle()
		throws Exception {

		_testWithActiveFFFriendlyURLEntryFileEntryConfiguration(
			() -> {
				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext(
						group.getGroupId(), TestPropsValues.getUserId());

				byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

				InputStream inputStream = new ByteArrayInputStream(bytes);

				DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
					null, TestPropsValues.getUserId(), group.getGroupId(),
					group.getGroupId(), parentFolder.getFolderId(),
					RandomTestUtil.randomString(),
					ContentTypes.APPLICATION_OCTET_STREAM,
					RandomTestUtil.randomString(), "urltitle", StringPool.BLANK,
					StringPool.BLANK,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
					null, null, inputStream, bytes.length, null, null,
					serviceContext);

				dlFileEntry = _dlFileEntryLocalService.updateFileEntry(
					group.getCreatorUserId(), dlFileEntry.getFileEntryId(),
					StringUtil.randomString(),
					ContentTypes.APPLICATION_OCTET_STREAM,
					RandomTestUtil.randomString(), null, StringPool.BLANK,
					StringPool.BLANK, DLVersionNumberIncrease.MAJOR,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
					Collections.emptyMap(), null, inputStream, 0, null, null,
					serviceContext);

				FriendlyURLEntry friendlyURLEntry =
					_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
						_portal.getClassNameId(FileEntry.class),
						dlFileEntry.getFileEntryId());

				Assert.assertEquals("urltitle", friendlyURLEntry.getUrlTitle());
			},
			true);
	}

	private void _testWithActiveFFFriendlyURLEntryFileEntryConfiguration(
			UnsafeRunnable<Exception> unsafeRunnable, boolean enabled)
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_FF_FRIENDLY_URL_ENTRY_FILE_ENTRY_CONFIGURATION_PID,
					HashMapDictionaryBuilder.<String, Object>put(
						"enabled", enabled
					).build())) {

			unsafeRunnable.run();
		}
	}

	private static final String
		_FF_FRIENDLY_URL_ENTRY_FILE_ENTRY_CONFIGURATION_PID =
			"com.liferay.document.library.configuration." +
				"FFFriendlyURLEntryFileEntryConfiguration";

	@Inject
	private static DLAppService _dlAppService;

	@Inject
	private static DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private static FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Inject
	private static Portal _portal;

}