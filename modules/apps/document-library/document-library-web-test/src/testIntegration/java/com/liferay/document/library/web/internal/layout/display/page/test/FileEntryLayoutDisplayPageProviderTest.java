/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.layout.display.page.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
public class FileEntryLayoutDisplayPageProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), StringPool.BLANK,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new byte[0], null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test
	public void testGetLayoutDisplayPageObjectProviderFileEntryWithInvalidFileEntryId() {
		Assert.assertNull(
			_layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				_fileEntry.getGroupId(),
				String.valueOf(_fileEntry.getFileEntryId() + 1)));
	}

	@Test
	public void testGetLayoutDisplayPageObjectProviderFileEntryWithInvalidUrlTitle() {
		Assert.assertNull(
			_layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				_fileEntry.getGroupId(), RandomTestUtil.randomString()));
	}

	@Test
	public void testGetLayoutDisplayPageObjectProviderFileEntryWithValidFileEntryId() {
		Assert.assertNotNull(
			_layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				_fileEntry.getGroupId(),
				String.valueOf(_fileEntry.getFileEntryId())));
	}

	@Test
	public void testGetLayoutDisplayPageObjectProviderFileEntryWithValidUrlTitle()
		throws Exception {

		Assert.assertNotNull(
			_layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				_fileEntry.getGroupId(), _fileEntry.getTitle()));
	}

	@Inject
	private DLAppLocalService _dlAppLocalService;

	private FileEntry _fileEntry;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "component.name=com.liferay.document.library.web.internal.layout.display.page.FileEntryLayoutDisplayPageProvider"
	)
	private LayoutDisplayPageProvider<FileEntry> _layoutDisplayPageProvider;

}