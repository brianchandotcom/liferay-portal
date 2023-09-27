/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.app.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLTrashServiceUtil;
import com.liferay.document.library.test.util.BaseDLAppTestCase;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@RunWith(Arquillian.class)
public class DLAppServiceWhenMovingAFileEntryToTrashTest
	extends BaseDLAppTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_fileEntry = DLAppServiceTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		DLAppServiceUtil.deleteFileEntry(_fileEntry.getFileEntryId());

		super.tearDown();
	}

	@Test
	public void testShouldCancelCheckout() throws Exception {
		DLAppServiceUtil.checkOutFileEntry(
			_fileEntry.getFileEntryId(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		Assert.assertTrue(_fileEntry.isCheckedOut());

		DLTrashServiceUtil.moveFileEntryToTrash(_fileEntry.getFileEntryId());

		_fileEntry = DLAppServiceUtil.getFileEntry(_fileEntry.getFileEntryId());

		Assert.assertFalse(_fileEntry.isCheckedOut());
	}

	@Test
	public void testShouldDeletePWCAssetEntry() throws Exception {
		DLAppServiceUtil.checkOutFileEntry(
			_fileEntry.getFileEntryId(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		FileVersion fileVersion = _fileEntry.getLatestFileVersion(false);

		Assert.assertNotNull(
			AssetEntryLocalServiceUtil.fetchEntry(
				DLFileEntryConstants.getClassName(),
				fileVersion.getFileVersionId()));

		DLTrashServiceUtil.moveFileEntryToTrash(_fileEntry.getFileEntryId());

		Assert.assertNull(
			AssetEntryLocalServiceUtil.fetchEntry(
				DLFileEntryConstants.getClassName(),
				fileVersion.getFileVersionId()));
	}

	private FileEntry _fileEntry;

}