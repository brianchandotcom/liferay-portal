/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalService;
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
public class FriendlyURLDLAppLocalServiceWrapperTest extends BaseDLAppTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddFileEntryBytesAddsFriendlyURLEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, serviceContext.getUserId(), group.getGroupId(),
			parentFolder.getFolderId(), RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(), "urltitle",
			StringPool.BLANK, StringPool.BLANK,
			TestDataConstants.TEST_BYTE_ARRAY, null, null, serviceContext);

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				fileEntry.getFileEntryId());

		Assert.assertNotNull(friendlyURLEntry);
		Assert.assertEquals("urltitle", friendlyURLEntry.getUrlTitle());
	}

	@Test
	public void testAddFileEntryFileAddsFriendlyURLEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, serviceContext.getUserId(), group.getGroupId(),
			parentFolder.getFolderId(), RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(), "urltitle",
			StringPool.BLANK, StringPool.BLANK,
			FileUtil.createTempFile(
				new UnsyncByteArrayInputStream(
					TestDataConstants.TEST_BYTE_ARRAY)),
			null, null, serviceContext);

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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, serviceContext.getUserId(), group.getGroupId(),
			parentFolder.getFolderId(), RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(), "urltitle",
			StringPool.BLANK, StringPool.BLANK,
			new UnsyncByteArrayInputStream(TestDataConstants.TEST_BYTE_ARRAY),
			0, null, null, serviceContext);

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				fileEntry.getFileEntryId());

		Assert.assertNotNull(friendlyURLEntry);
		Assert.assertEquals("urltitle", friendlyURLEntry.getUrlTitle());
	}

	@Test
	public void testUpdateFileEntryBytesUpdatesFriendlyURLEntry()
		throws Exception {

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, serviceContext.getUserId(), group.getGroupId(),
			parentFolder.getFolderId(), RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			bytes, null, null, serviceContext);

		fileEntry = _dlAppLocalService.updateFileEntry(
			serviceContext.getUserId(), fileEntry.getFileEntryId(),
			StringPool.BLANK, ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomString(), "urltitle", StringPool.BLANK, null,
			DLVersionNumberIncrease.AUTOMATIC, bytes, null, null,
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

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, serviceContext.getUserId(), group.getGroupId(),
			parentFolder.getFolderId(), RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			file, null, null, serviceContext);

		fileEntry = _dlAppLocalService.updateFileEntry(
			serviceContext.getUserId(), fileEntry.getFileEntryId(),
			StringPool.BLANK, ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomString(), "urltitle", StringPool.BLANK, null,
			DLVersionNumberIncrease.AUTOMATIC, file, null, null,
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

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, serviceContext.getUserId(), group.getGroupId(),
			parentFolder.getFolderId(), RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(), "urltitle",
			StringPool.BLANK, StringPool.BLANK, inputStream, size, null, null,
			serviceContext);

		fileEntry = _dlAppLocalService.updateFileEntry(
			serviceContext.getUserId(), fileEntry.getFileEntryId(),
			StringPool.BLANK, ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomString(), "urltitle", StringPool.BLANK, null,
			DLVersionNumberIncrease.AUTOMATIC, inputStream, size, null, null,
			serviceContext);

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				_portal.getClassNameId(FileEntry.class),
				fileEntry.getFileEntryId());

		Assert.assertEquals("urltitle", friendlyURLEntry.getUrlTitle());
	}

	@Inject
	private static DLAppLocalService _dlAppLocalService;

	@Inject
	private static FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Inject
	private static Portal _portal;

}