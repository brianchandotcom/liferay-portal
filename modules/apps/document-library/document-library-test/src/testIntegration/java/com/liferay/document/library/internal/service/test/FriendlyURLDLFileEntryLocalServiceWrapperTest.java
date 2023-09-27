/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.Collections;
import java.util.List;

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
	public void testAddFileEntriesSameTitleAddsFriendlyURLEntryDeleteFileEntryReuseURLTitle()
		throws Exception {

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		InputStream inputStream = new ByteArrayInputStream(bytes);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry1 = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), "urltitle", StringPool.BLANK,
			StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, inputStream, bytes.length, null, null, serviceContext);

		FriendlyURLEntry friendlyURLEntry1 =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry1.getFileEntryId());

		Assert.assertEquals("urltitle", friendlyURLEntry1.getUrlTitle());

		_dlFileEntryLocalService.deleteFileEntry(dlFileEntry1);

		DLFileEntry dlFileEntry2 = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), "urltitle", StringPool.BLANK,
			StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, inputStream, bytes.length, null, null, serviceContext);

		FriendlyURLEntry friendlyURLEntry2 =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry2.getFileEntryId());

		Assert.assertEquals("urltitle", friendlyURLEntry2.getUrlTitle());
	}

	@Test
	public void testAddFileEntriesSameTitleAddsFriendlyURLEntryUniqueTitle()
		throws Exception {

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		InputStream inputStream = new ByteArrayInputStream(bytes);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry1 = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM, "title", "urltitle",
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, inputStream, bytes.length, null, null, serviceContext);

		FriendlyURLEntry friendlyURLEntry1 =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry1.getFileEntryId());

		Assert.assertNotNull(friendlyURLEntry1);
		Assert.assertEquals("urltitle", friendlyURLEntry1.getUrlTitle());

		Folder folder = _dlAppService.addFolder(
			null, group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		DLFileEntry dlFileEntry2 = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			group.getGroupId(), folder.getFolderId(),
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM, "title", "urltitle",
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, inputStream, bytes.length, null, null, serviceContext);

		FriendlyURLEntry friendlyURLEntry2 =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry2.getFileEntryId());

		Assert.assertNotNull(friendlyURLEntry2);
		Assert.assertEquals("urltitle-1", friendlyURLEntry2.getUrlTitle());
	}

	@Test
	public void testAddFileEntryAddsFriendlyURLEntry() throws Exception {
		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM, "title", "urltitle",
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, new ByteArrayInputStream(bytes), bytes.length, null, null,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry.getFileEntryId());

		Assert.assertNotNull(friendlyURLEntry);
		Assert.assertEquals("urltitle", friendlyURLEntry.getUrlTitle());
	}

	@Test
	public void testAddFileEntryAddsFriendlyURLEntryBlankUrlTitle()
		throws Exception {

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM, "title", StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, new ByteArrayInputStream(bytes), bytes.length, null, null,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry.getFileEntryId());

		Assert.assertNotNull(friendlyURLEntry);
		Assert.assertEquals("title", friendlyURLEntry.getUrlTitle());
	}

	@Test
	public void testAddFileEntryAddsFriendlyURLEntryNullUrlTitle()
		throws Exception {

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM, "title", null,
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, new ByteArrayInputStream(bytes), bytes.length, null, null,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry.getFileEntryId());

		Assert.assertNotNull(friendlyURLEntry);
		Assert.assertEquals("title", friendlyURLEntry.getUrlTitle());
	}

	@Test
	public void testAddFileEntryAddsFriendlyURLEntryWithNormalizedUrlTitle()
		throws Exception {

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), "<script/urlTitle</script>",
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, new ByteArrayInputStream(bytes), bytes.length, null, null,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry.getFileEntryId());

		Assert.assertNotNull(friendlyURLEntry);
		Assert.assertEquals(
			"-script-urltitle-script-", friendlyURLEntry.getUrlTitle());
	}

	@Test
	public void testDeleteFileEntryDeletesFriendlyURLEntry() throws Exception {
		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, new ByteArrayInputStream(bytes), bytes.length, null, null,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry.getFileEntryId());

		Assert.assertNotNull(friendlyURLEntry);

		_dlFileEntryLocalService.deleteFileEntry(dlFileEntry.getFileEntryId());

		Assert.assertNull(
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				friendlyURLEntry.getFriendlyURLEntryId()));
	}

	@Test
	public void testRemoveOldFriendlyURLFromHistory() throws Exception {
		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		InputStream inputStream = new ByteArrayInputStream(bytes);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), "oldurltitle", StringPool.BLANK,
			StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, inputStream, bytes.length, null, null, serviceContext);

		dlFileEntry = _dlFileEntryLocalService.updateFileEntry(
			group.getCreatorUserId(), dlFileEntry.getFileEntryId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), "urltitle", StringPool.BLANK,
			StringPool.BLANK, DLVersionNumberIncrease.MAJOR,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			Collections.emptyMap(), null, inputStream, 0, null, null,
			serviceContext);

		FriendlyURLEntry mainFriendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry.getFileEntryId());

		Assert.assertEquals("urltitle", mainFriendlyURLEntry.getUrlTitle());

		List<FriendlyURLEntry> friendlyURLEntries =
			_friendlyURLEntryLocalService.getFriendlyURLEntries(
				dlFileEntry.getGroupId(),
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry.getFileEntryId());

		Assert.assertEquals(
			friendlyURLEntries.toString(), 2, friendlyURLEntries.size());

		FriendlyURLEntry oldFriendlyURLEntry =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				dlFileEntry.getGroupId(),
				_portal.getClassNameId(FileEntry.class), "oldurltitle");

		Assert.assertNotNull(oldFriendlyURLEntry);

		_friendlyURLEntryLocalService.deleteFriendlyURLEntry(
			oldFriendlyURLEntry);

		friendlyURLEntries =
			_friendlyURLEntryLocalService.getFriendlyURLEntries(
				dlFileEntry.getGroupId(),
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry.getFileEntryId());

		Assert.assertEquals(
			friendlyURLEntries.toString(), 1, friendlyURLEntries.size());

		mainFriendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry.getFileEntryId());

		Assert.assertEquals("urltitle", mainFriendlyURLEntry.getUrlTitle());
	}

	@Test
	public void testRestoreOldUrlTitleDoNotCreateANewFriendlyURL()
		throws Exception {

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		InputStream inputStream = new ByteArrayInputStream(bytes);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), "urltitle", StringPool.BLANK,
			StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, inputStream, bytes.length, null, null, serviceContext);

		dlFileEntry = _dlFileEntryLocalService.updateFileEntry(
			group.getCreatorUserId(), dlFileEntry.getFileEntryId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, DLVersionNumberIncrease.MAJOR,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			Collections.emptyMap(), null, inputStream, 0, null, null,
			serviceContext);

		FriendlyURLEntry mainFriendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry.getFileEntryId());

		Assert.assertNotEquals("urltitle", mainFriendlyURLEntry.getUrlTitle());

		List<FriendlyURLEntry> friendlyURLEntries =
			_friendlyURLEntryLocalService.getFriendlyURLEntries(
				dlFileEntry.getGroupId(),
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry.getFileEntryId());

		Assert.assertEquals(
			friendlyURLEntries.toString(), 2, friendlyURLEntries.size());

		FriendlyURLEntry restoredFriendlyURLEntry =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				dlFileEntry.getGroupId(),
				_portal.getClassNameId(FileEntry.class), "urltitle");

		Assert.assertNotNull(restoredFriendlyURLEntry);

		_friendlyURLEntryLocalService.setMainFriendlyURLEntry(
			restoredFriendlyURLEntry);

		friendlyURLEntries =
			_friendlyURLEntryLocalService.getFriendlyURLEntries(
				dlFileEntry.getGroupId(),
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry.getFileEntryId());

		Assert.assertEquals(
			friendlyURLEntries.toString(), 2, friendlyURLEntries.size());

		mainFriendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry.getFileEntryId());

		Assert.assertEquals("urltitle", mainFriendlyURLEntry.getUrlTitle());
	}

	@Test
	public void testUpdateFileEntryUpdatesFriendlyURLEntry() throws Exception {
		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		InputStream inputStream = new ByteArrayInputStream(bytes);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, inputStream, bytes.length, null, null, serviceContext);

		dlFileEntry = _dlFileEntryLocalService.updateFileEntry(
			group.getCreatorUserId(), dlFileEntry.getFileEntryId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
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
	}

	@Test
	public void testUpdateFileEntryUpdatesFriendlyURLEntryBlankURlTitleNotModifyPreviousUrlTitle()
		throws Exception {

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		InputStream inputStream = new ByteArrayInputStream(bytes);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), "urltitle", StringPool.BLANK,
			StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, inputStream, bytes.length, null, null, serviceContext);

		dlFileEntry = _dlFileEntryLocalService.updateFileEntry(
			group.getCreatorUserId(), dlFileEntry.getFileEntryId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, DLVersionNumberIncrease.MAJOR,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			Collections.emptyMap(), null, inputStream, bytes.length, null, null,
			serviceContext);

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry.getFileEntryId());

		Assert.assertEquals("urltitle", friendlyURLEntry.getUrlTitle());
	}

	@Test
	public void testUpdateFileEntryUpdatesFriendlyURLEntryDoNotDeletesPreviousFriendlyURL()
		throws Exception {

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		InputStream inputStream = new ByteArrayInputStream(bytes);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), "originalURLTitle", StringPool.BLANK,
			StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, inputStream, bytes.length, null, null, serviceContext);

		dlFileEntry = _dlFileEntryLocalService.updateFileEntry(
			group.getCreatorUserId(), dlFileEntry.getFileEntryId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), "urltitle", StringPool.BLANK,
			StringPool.BLANK, DLVersionNumberIncrease.MAJOR,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			Collections.emptyMap(), null, inputStream, bytes.length, null, null,
			serviceContext);

		FriendlyURLEntry mainFriendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry.getFileEntryId());

		Assert.assertEquals("urltitle", mainFriendlyURLEntry.getUrlTitle());

		List<FriendlyURLEntry> friendlyURLEntries =
			_friendlyURLEntryLocalService.getFriendlyURLEntries(
				dlFileEntry.getGroupId(),
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry.getFileEntryId());

		Assert.assertEquals(
			friendlyURLEntries.toString(), 2, friendlyURLEntries.size());
	}

	@Test
	public void testUpdateFileEntryUpdatesFriendlyURLEntryNullURlTitleNotModifyPreviousUrlTitle()
		throws Exception {

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		InputStream inputStream = new ByteArrayInputStream(bytes);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), "urltitle", StringPool.BLANK,
			StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, inputStream, bytes.length, null, null, serviceContext);

		dlFileEntry = _dlFileEntryLocalService.updateFileEntry(
			group.getCreatorUserId(), dlFileEntry.getFileEntryId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), null, StringPool.BLANK,
			StringPool.BLANK, DLVersionNumberIncrease.MAJOR,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			Collections.emptyMap(), null, inputStream, bytes.length, null, null,
			serviceContext);

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry.getFileEntryId());

		Assert.assertEquals("urltitle", friendlyURLEntry.getUrlTitle());
	}

	@Test
	public void testUpdateFileEntryUpdatesFriendlyURLEntryWithNormalizedUrlTitle()
		throws Exception {

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		InputStream inputStream = new ByteArrayInputStream(bytes);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM, "<script>title</script>",
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, inputStream, bytes.length, null, null, serviceContext);

		FriendlyURLEntry mainFriendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry.getFileEntryId());

		Assert.assertEquals(
			"-script-title-script-", mainFriendlyURLEntry.getUrlTitle());

		dlFileEntry = _dlFileEntryLocalService.updateFileEntry(
			group.getCreatorUserId(), dlFileEntry.getFileEntryId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), "url/title",
			RandomTestUtil.randomString(), StringPool.BLANK,
			DLVersionNumberIncrease.MAJOR,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			Collections.emptyMap(), null, inputStream, bytes.length, null, null,
			serviceContext);

		mainFriendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				dlFileEntry.getFileEntryId());

		Assert.assertEquals("url-title", mainFriendlyURLEntry.getUrlTitle());
	}

	@Inject
	private static DLAppService _dlAppService;

	@Inject
	private static DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private static FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Inject
	private static Portal _portal;

}