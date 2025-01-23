/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.test.util.DLTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.ByteArrayInputStream;

import java.util.Set;
import java.util.UUID;

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
public class DLTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());
	}

	@Test
	public void testGetAllMediaGalleryMimeTypesContainsSVG() {
		Set<String> allMediaGalleryMimeTypes =
			DLUtil.getAllMediaGalleryMimeTypes();

		Assert.assertTrue(
			allMediaGalleryMimeTypes.toString(),
			allMediaGalleryMimeTypes.contains(ContentTypes.IMAGE_SVG_XML));
	}

	@Test
	public void testGetUniqueFileName() throws Exception {
		String fileNamePrefix = RandomTestUtil.randomString();

		String fileName = fileNamePrefix + ".txt";

		Assert.assertEquals(
			fileName,
			DLUtil.getUniqueFileName(
				_group.getGroupId(), _dlFolder.getFolderId(), fileName, false));

		_addFileEntry(fileName, RandomTestUtil.randomString());

		Assert.assertEquals(
			fileNamePrefix + " (1).txt",
			DLUtil.getUniqueFileName(
				_group.getGroupId(), _dlFolder.getFolderId(), fileName, false));
	}

	@Test
	public void testGetUniqueTitle() throws Exception {
		String title = RandomTestUtil.randomString();

		Assert.assertEquals(
			title,
			DLUtil.getUniqueTitle(
				_group.getGroupId(), _dlFolder.getFolderId(), title));

		_addFileEntry(RandomTestUtil.randomString(), title);

		Assert.assertEquals(
			title + " (1)",
			DLUtil.getUniqueTitle(
				_group.getGroupId(), _dlFolder.getFolderId(), title));
	}

	@Test
	public void testIsValidVersion() {
		Assert.assertTrue(DLUtil.isValidVersion("1.1"));
		Assert.assertTrue(DLUtil.isValidVersion("1.1~" + UUID.randomUUID()));
		Assert.assertTrue(
			DLUtil.isValidVersion(
				DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION +
					StringPool.TILDE + UUID.randomUUID()));
		Assert.assertTrue(
			DLUtil.isValidVersion(
				DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION));
	}

	private void _addFileEntry(String sourceFileName, String title)
		throws Exception {

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		DLFileEntryLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_dlFolder.getRepositoryId(), _dlFolder.getFolderId(),
			sourceFileName, ContentTypes.TEXT_PLAIN, title, StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, new ByteArrayInputStream(bytes), bytes.length, null, null,
			null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private DLFolder _dlFolder;

	@DeleteAfterTestRun
	private Group _group;

}