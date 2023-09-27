/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLTrashService;
import com.liferay.document.library.test.util.BaseDLAppTestCase;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class FriendlyURLDLAppServiceWrapperTest extends BaseDLAppTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddFileEntryBytesAddsFriendlyURLEntry() throws Exception {
		FileEntry fileEntry = _dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), "urltitle", StringUtil.randomString(),
			StringPool.BLANK, TestDataConstants.TEST_BYTE_ARRAY, null, null,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				fileEntry.getFileEntryId());

		Assert.assertNotNull(friendlyURLEntry);
		Assert.assertEquals("urltitle", friendlyURLEntry.getUrlTitle());
	}

	@Test
	public void testAddFileEntryBytesAddsFriendlyURLEntryOnTrashUniqueURLTitle()
		throws Exception {

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), "urltitle", StringUtil.randomString(),
			StringPool.BLANK, TestDataConstants.TEST_BYTE_ARRAY, null, null,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));

		FriendlyURLEntry friendlyURLEntry1 =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				fileEntry1.getFileEntryId());

		Assert.assertEquals("urltitle", friendlyURLEntry1.getUrlTitle());

		_dlTrashService.moveFileEntryToTrash(fileEntry1.getFileEntryId());

		FileEntry fileEntry2 = _dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), "urltitle", StringUtil.randomString(),
			StringPool.BLANK, TestDataConstants.TEST_BYTE_ARRAY, null, null,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));

		FriendlyURLEntry friendlyURLEntry2 =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				fileEntry2.getFileEntryId());

		Assert.assertEquals("urltitle-1", friendlyURLEntry2.getUrlTitle());
	}

	@Test
	public void testAddFileEntryFileAddsFriendlyURLEntry() throws Exception {
		FileEntry fileEntry = _dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), "urltitle", StringUtil.randomString(),
			StringPool.BLANK,
			FileUtil.createTempFile(
				new UnsyncByteArrayInputStream(
					TestDataConstants.TEST_BYTE_ARRAY)),
			null, null,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				fileEntry.getFileEntryId());

		Assert.assertNotNull(friendlyURLEntry);
		Assert.assertEquals("urltitle", friendlyURLEntry.getUrlTitle());
	}

	@Test
	public void testAddFileEntryInputStreamAddsFriendlyURLEntry()
		throws Exception {

		FileEntry fileEntry = _dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), "urltitle", StringUtil.randomString(),
			StringPool.BLANK,
			new UnsyncByteArrayInputStream(TestDataConstants.TEST_BYTE_ARRAY),
			0, null, null,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				fileEntry.getFileEntryId());

		Assert.assertNotNull(friendlyURLEntry);
		Assert.assertEquals("urltitle", friendlyURLEntry.getUrlTitle());
	}

	@Test
	public void testUpdateFileEntryAndCheckInFileUpdatesFriendlyURLEntry()
		throws Exception {

		File file = FileUtil.createTempFile(
			new UnsyncByteArrayInputStream(TestDataConstants.TEST_BYTE_ARRAY));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = _dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), RandomTestUtil.randomString(),
			StringUtil.randomString(), StringPool.BLANK, file, null, null,
			serviceContext);

		fileEntry = _dlAppService.updateFileEntryAndCheckIn(
			fileEntry.getFileEntryId(), StringUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), "urltitle", StringPool.BLANK,
			StringPool.BLANK, DLVersionNumberIncrease.MAJOR, file, null, null,
			serviceContext);

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				fileEntry.getFileEntryId());

		Assert.assertEquals("urltitle", friendlyURLEntry.getUrlTitle());
	}

	@Test
	public void testUpdateFileEntryAndCheckInInputStreamUpdatesFriendlyURLEntry()
		throws Exception {

		InputStream inputStream = new UnsyncByteArrayInputStream(
			TestDataConstants.TEST_BYTE_ARRAY);
		long size = 0;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = _dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), "urltitle", StringUtil.randomString(),
			StringPool.BLANK, inputStream, size, null, null, serviceContext);

		fileEntry = _dlAppService.updateFileEntryAndCheckIn(
			fileEntry.getFileEntryId(), StringUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), "urltitle", StringPool.BLANK,
			StringPool.BLANK, DLVersionNumberIncrease.MAJOR, inputStream, size,
			null, null, serviceContext);

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				fileEntry.getFileEntryId());

		Assert.assertEquals("urltitle", friendlyURLEntry.getUrlTitle());
	}

	@Test
	public void testUpdateFileEntryBytesUpdatesFriendlyURLEntry()
		throws Exception {

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = _dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), RandomTestUtil.randomString(),
			StringUtil.randomString(), StringPool.BLANK, bytes, null, null,
			serviceContext);

		fileEntry = _dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), StringUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), "urltitle", StringPool.BLANK,
			StringPool.BLANK, DLVersionNumberIncrease.MAJOR, bytes, null, null,
			serviceContext);

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				fileEntry.getFileEntryId());

		Assert.assertEquals("urltitle", friendlyURLEntry.getUrlTitle());
	}

	@Test
	public void testUpdateFileEntryFileUpdatesFriendlyURLEntry()
		throws Exception {

		File file = FileUtil.createTempFile(
			new UnsyncByteArrayInputStream(TestDataConstants.TEST_BYTE_ARRAY));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = _dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), RandomTestUtil.randomString(),
			StringUtil.randomString(), StringPool.BLANK, file, null, null,
			serviceContext);

		fileEntry = _dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), StringUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), "urltitle", StringPool.BLANK,
			StringPool.BLANK, DLVersionNumberIncrease.MAJOR, file, null, null,
			serviceContext);

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				fileEntry.getFileEntryId());

		Assert.assertEquals("urltitle", friendlyURLEntry.getUrlTitle());
	}

	@Test
	public void testUpdateFileEntryInputStreamUpdatesFriendlyURLEntry()
		throws Exception {

		InputStream inputStream = new UnsyncByteArrayInputStream(
			TestDataConstants.TEST_BYTE_ARRAY);
		long size = 0;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = _dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), "urltitle", StringUtil.randomString(),
			StringPool.BLANK, inputStream, size, null, null, serviceContext);

		fileEntry = _dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), StringUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), "urltitle", StringPool.BLANK,
			StringPool.BLANK, DLVersionNumberIncrease.MAJOR, inputStream, size,
			null, null, serviceContext);

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				fileEntry.getFileEntryId());

		Assert.assertEquals("urltitle", friendlyURLEntry.getUrlTitle());
	}

	@Inject
	private static DLAppService _dlAppService;

	@Inject
	private static FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Inject
	private static Portal _portal;

	@Inject
	private DLTrashService _dlTrashService;

}