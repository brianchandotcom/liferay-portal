/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.upload.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.opener.constants.DLOpenerMimeTypes;
import com.liferay.document.library.opener.upload.UniqueFileEntryTitleProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class UniqueFileEntryTitleProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_folder = _dlAppLocalService.addFolder(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test
	public void testProvideWithExistingFileName() throws PortalException {
		_dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_folder.getFolderId(), "someTitle.jpg",
			ContentTypes.APPLICATION_TEXT, StringUtil.randomString(),
			StringPool.BLANK, StringUtil.randomString(), StringPool.BLANK,
			"test".getBytes(), null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(
			"someTitle (1)",
			_uniqueFileEntryTitleProvider.provide(
				_group.getGroupId(), _folder.getFolderId(), ".jpg",
				"someTitle"));
	}

	@Test
	public void testProvideWithExistingName() throws PortalException {
		_dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_folder.getFolderId(), null, ContentTypes.APPLICATION_TEXT,
			"someTitle", StringPool.BLANK, StringUtil.randomString(),
			StringPool.BLANK, "test".getBytes(), null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(
			"someTitle (1)",
			_uniqueFileEntryTitleProvider.provide(
				_group.getGroupId(), _folder.getFolderId(), StringPool.BLANK,
				"someTitle"));
	}

	@Test
	public void testProvideWithExtensionAndLocale() throws PortalException {
		Assert.assertEquals(
			"Untitled",
			_uniqueFileEntryTitleProvider.provide(
				_group.getGroupId(), _folder.getFolderId(),
				DLOpenerMimeTypes.getMimeTypeExtension(
					DLOpenerMimeTypes.APPLICATION_VND_DOCX),
				LocaleUtil.US));
	}

	@Test
	public void testProvideWithExtensionAndLocaleWithExistingFileName()
		throws PortalException {

		String mimeTypeExtension = DLOpenerMimeTypes.getMimeTypeExtension(
			DLOpenerMimeTypes.APPLICATION_VND_DOCX);

		_dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_folder.getFolderId(), "Untitled" + mimeTypeExtension,
			ContentTypes.APPLICATION_TEXT, StringUtil.randomString(),
			StringPool.BLANK, StringUtil.randomString(), StringPool.BLANK,
			"test".getBytes(), null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(
			"Untitled (1)",
			_uniqueFileEntryTitleProvider.provide(
				_group.getGroupId(), _folder.getFolderId(), mimeTypeExtension,
				LocaleUtil.US));
	}

	@Test
	public void testProvideWithExtensionAndLocaleWithExistingTitle()
		throws PortalException {

		_dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_folder.getFolderId(), null, ContentTypes.APPLICATION_TEXT,
			"Untitled", StringPool.BLANK, StringUtil.randomString(),
			StringPool.BLANK, "test".getBytes(), null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(
			"Untitled (1)",
			_uniqueFileEntryTitleProvider.provide(
				_group.getGroupId(), _folder.getFolderId(),
				DLOpenerMimeTypes.getMimeTypeExtension(
					DLOpenerMimeTypes.APPLICATION_VND_DOCX),
				LocaleUtil.US));
	}

	@Test
	public void testProvideWithExtensionAndLocaleWithExistingTitleAndDifferentExtension()
		throws PortalException {

		_dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_folder.getFolderId(), "Untitled.ppt",
			ContentTypes.APPLICATION_TEXT, "Untitled", StringPool.BLANK,
			StringUtil.randomString(), StringPool.BLANK, "test".getBytes(),
			null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(
			"Untitled (1)",
			_uniqueFileEntryTitleProvider.provide(
				_group.getGroupId(), _folder.getFolderId(),
				DLOpenerMimeTypes.getMimeTypeExtension(
					DLOpenerMimeTypes.APPLICATION_VND_DOCX),
				LocaleUtil.US));
	}

	@Test
	public void testProvideWithLocale() throws PortalException {
		Assert.assertEquals(
			"Untitled",
			_uniqueFileEntryTitleProvider.provide(
				_group.getGroupId(), _folder.getFolderId(), LocaleUtil.US));
	}

	@Test
	public void testProvideWithLocaleWithExistingFileName()
		throws PortalException {

		_dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_folder.getFolderId(), "Untitled", ContentTypes.APPLICATION_TEXT,
			StringUtil.randomString(), StringPool.BLANK,
			StringUtil.randomString(), StringPool.BLANK, "test".getBytes(),
			null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(
			"Untitled (1)",
			_uniqueFileEntryTitleProvider.provide(
				_group.getGroupId(), _folder.getFolderId(), LocaleUtil.US));
	}

	@Test
	public void testProvideWithLocaleWithExistingTitle()
		throws PortalException {

		_dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_folder.getFolderId(), null, ContentTypes.APPLICATION_TEXT,
			"Untitled", StringPool.BLANK, StringUtil.randomString(),
			StringPool.BLANK, "test".getBytes(), null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(
			"Untitled (1)",
			_uniqueFileEntryTitleProvider.provide(
				_group.getGroupId(), _folder.getFolderId(), LocaleUtil.US));
	}

	@Test
	public void testProvideWithName() throws PortalException {
		Assert.assertEquals(
			"someTitle",
			_uniqueFileEntryTitleProvider.provide(
				_group.getGroupId(), _folder.getFolderId(), StringPool.BLANK,
				"someTitle"));
	}

	@Inject
	private DLAppLocalService _dlAppLocalService;

	private Folder _folder;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private UniqueFileEntryTitleProvider _uniqueFileEntryTitleProvider;

}