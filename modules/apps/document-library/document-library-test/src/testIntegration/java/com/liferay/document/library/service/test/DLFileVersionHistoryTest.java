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

package com.liferay.document.library.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.exception.InvalidFileVersionException;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileVersionLocalServiceUtil;
import com.liferay.document.library.test.util.BaseDLAppTestCase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@RunWith(Arquillian.class)
public class DLFileVersionHistoryTest extends BaseDLAppTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testDeleteOneVersion() throws Exception {
		deleteVersion(false, false);
	}

	@Test
	public void testDeleteOneVersionOnePWC() throws Exception {
		deleteVersion(false, true);
	}

	@Test
	public void testDeleteTwoVersions() throws Exception {
		deleteVersion(true, false);
	}

	@Test
	public void testDeleteTwoVersionsOnePWC() throws Exception {
		deleteVersion(true, true);
	}

	@Test
	public void testRevertOneVersion() throws Exception {
		revertVersion(false, false);
	}

	@Test
	public void testRevertOneVersionOnePWC() throws Exception {
		revertVersion(false, true);
	}

	@Test
	public void testRevertTwoVersions() throws Exception {
		revertVersion(true, false);
	}

	@Test
	public void testRevertTwoVersionsOnePWC() throws Exception {
		revertVersion(true, true);
	}

	protected FileEntry addFileEntry(long folderId, String sourceFileName)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		return DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(), folderId,
			sourceFileName, ContentTypes.TEXT_PLAIN,
			TestDataConstants.TEST_BYTE_ARRAY, null, null, serviceContext);
	}

	protected void assertFileEntryTitle(String fileName)
		throws PortalException {

		FileEntry fileEntry = DLAppServiceUtil.getFileEntry(
			_fileEntry.getFileEntryId());

		Assert.assertEquals(fileName, fileEntry.getTitle());
	}

	protected void assertLatestFileVersionTitle(String fileName)
		throws PortalException {

		DLFileVersion latestDLFileVersion =
			DLFileVersionLocalServiceUtil.getLatestFileVersion(
				_fileEntry.getFileEntryId(), false);

		Assert.assertEquals(fileName, latestDLFileVersion.getTitle());
	}

	protected void deleteFileVersion(
			String version, String fileName, boolean pwc)
		throws PortalException {

		DLAppServiceUtil.deleteFileVersion(
			_fileEntry.getFileEntryId(), version);

		if (fileName != null) {
			if (pwc) {
				assertLatestFileVersionTitle(fileName);
			}
			else {
				assertFileEntryTitle(fileName);
			}
		}
	}

	protected void deleteVersion(boolean versioned, boolean leaveCheckedOut)
		throws Exception {

		_fileEntry = addFileEntry(parentFolder.getFolderId(), _VERSION_1_0);

		long fileEntryId = _fileEntry.getFileEntryId();

		if (versioned) {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					group.getGroupId(), TestPropsValues.getUserId());

			DLAppServiceUtil.updateFileEntry(
				fileEntryId, null, ContentTypes.TEXT_PLAIN, _VERSION_1_1,
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				DLVersionNumberIncrease.MINOR, (byte[])null,
				_fileEntry.getExpirationDate(), _fileEntry.getReviewDate(),
				serviceContext);
		}

		if (leaveCheckedOut) {
			DLAppServiceUtil.checkOutFileEntry(
				fileEntryId, new ServiceContext());

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					group.getGroupId(), TestPropsValues.getUserId());

			DLAppServiceUtil.updateFileEntry(
				fileEntryId, null, ContentTypes.TEXT_PLAIN, _VERSION_PWC,
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				DLVersionNumberIncrease.MINOR, (byte[])null,
				_fileEntry.getExpirationDate(), _fileEntry.getReviewDate(),
				serviceContext);
		}

		if (versioned && leaveCheckedOut) {
			Assert.assertEquals(3, getFileVersionsCount());

			failDeleteFileVersion("PWC");
			deleteFileVersion("1.1", _VERSION_PWC, true);
			failDeleteFileVersion("1.0");

			Assert.assertEquals(2, getFileVersionsCount());
		}
		else if (versioned) {
			Assert.assertEquals(2, getFileVersionsCount());

			deleteFileVersion("1.1", _VERSION_1_0, false);
			failDeleteFileVersion("1.0");

			Assert.assertEquals(1, getFileVersionsCount());
		}
		else if (leaveCheckedOut) {
			Assert.assertEquals(2, getFileVersionsCount());

			failDeleteFileVersion("PWC");
			failDeleteFileVersion("1.0");

			Assert.assertEquals(2, getFileVersionsCount());
		}
		else {
			Assert.assertEquals(1, getFileVersionsCount());

			failDeleteFileVersion("1.0");

			Assert.assertEquals(1, getFileVersionsCount());
		}
	}

	protected void failDeleteFileVersion(String version)
		throws PortalException {

		try {
			deleteFileVersion(version, null, true);

			Assert.fail();
		}
		catch (InvalidFileVersionException invalidFileVersionException) {
			if (_log.isDebugEnabled()) {
				_log.debug(invalidFileVersionException);
			}
		}
	}

	protected void failRevertFileVersion(String version)
		throws PortalException {

		try {
			revertFileVersion(version, null);

			Assert.fail();
		}
		catch (InvalidFileVersionException invalidFileVersionException) {
			if (_log.isDebugEnabled()) {
				_log.debug(invalidFileVersionException);
			}
		}
	}

	protected int getFileVersionsCount() {
		List<FileVersion> fileVersions = _fileEntry.getFileVersions(
			WorkflowConstants.STATUS_ANY);

		return fileVersions.size();
	}

	protected void revertFileVersion(String version, String fileName)
		throws PortalException {

		DLAppServiceUtil.revertFileEntry(
			_fileEntry.getFileEntryId(), version, new ServiceContext());

		if (fileName != null) {
			assertLatestFileVersionTitle(fileName);
		}
	}

	protected void revertVersion(boolean versioned, boolean leaveCheckedOut)
		throws Exception {

		_fileEntry = addFileEntry(parentFolder.getFolderId(), _VERSION_1_0);

		long fileEntryId = _fileEntry.getFileEntryId();

		if (versioned) {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					group.getGroupId(), TestPropsValues.getUserId());

			DLAppServiceUtil.updateFileEntry(
				fileEntryId, null, ContentTypes.TEXT_PLAIN, _VERSION_1_1,
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				DLVersionNumberIncrease.MINOR, (byte[])null,
				_fileEntry.getExpirationDate(), _fileEntry.getReviewDate(),
				serviceContext);
		}

		if (leaveCheckedOut) {
			DLAppServiceUtil.checkOutFileEntry(
				fileEntryId, new ServiceContext());

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					group.getGroupId(), TestPropsValues.getUserId());

			DLAppServiceUtil.updateFileEntry(
				fileEntryId, null, ContentTypes.TEXT_PLAIN, _VERSION_PWC,
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				DLVersionNumberIncrease.MINOR, (byte[])null,
				_fileEntry.getExpirationDate(), _fileEntry.getReviewDate(),
				serviceContext);
		}

		if (versioned && leaveCheckedOut) {
			Assert.assertEquals(3, getFileVersionsCount());

			failRevertFileVersion("PWC");
			revertFileVersion("1.1", _VERSION_1_1);
			revertFileVersion("1.0", _VERSION_1_0);

			Assert.assertEquals(3, getFileVersionsCount());
		}
		else if (versioned) {
			Assert.assertEquals(2, getFileVersionsCount());

			failRevertFileVersion("1.1");
			revertFileVersion("1.0", _VERSION_1_0);

			Assert.assertEquals(3, getFileVersionsCount());
		}
		else if (leaveCheckedOut) {
			Assert.assertEquals(2, getFileVersionsCount());

			failRevertFileVersion("PWC");
			revertFileVersion("1.0", _VERSION_1_0);

			Assert.assertEquals(2, getFileVersionsCount());
		}
		else {
			Assert.assertEquals(1, getFileVersionsCount());

			failRevertFileVersion("1.0");

			Assert.assertEquals(1, getFileVersionsCount());
		}
	}

	private static final String _VERSION_1_0 = "Test Version 1.0.txt";

	private static final String _VERSION_1_1 = "Test Version 1.1.txt";

	private static final String _VERSION_PWC = "Test Version PWC.txt";

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileVersionHistoryTest.class);

	private FileEntry _fileEntry;

}