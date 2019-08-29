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
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.test.util.BaseDLAppTestCase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@RunWith(Arquillian.class)
public class DLFileVersionUpdateTest extends BaseDLAppTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testWithExtensionWithContent() throws Exception {
		testVersionUpdate(
			_FULL_FILE_NAME, _ZERO_BYTES, ContentTypes.TEXT_PLAIN,
			_FULL_FILE_NAME, CONTENT.getBytes(), ContentTypes.TEXT_PLAIN);
	}

	@Test
	public void testWithExtensionWithoutContent() throws Exception {
		testVersionUpdate(
			_FULL_FILE_NAME, _ZERO_BYTES, ContentTypes.TEXT_PLAIN,
			_FULL_FILE_NAME, _ZERO_BYTES, ContentTypes.TEXT_PLAIN);
	}

	@Test
	public void testWithoutExtensionWithContent() throws Exception {
		testVersionUpdate(
			_BASE_FILE_NAME, _ZERO_BYTES, ContentTypes.APPLICATION_OCTET_STREAM,
			_BASE_FILE_NAME, CONTENT.getBytes(), ContentTypes.TEXT_PLAIN);
	}

	@Test
	public void testWithoutExtensionWithoutContent() throws Exception {
		testVersionUpdate(
			_BASE_FILE_NAME, _ZERO_BYTES, ContentTypes.APPLICATION_OCTET_STREAM,
			_BASE_FILE_NAME, _ZERO_BYTES,
			ContentTypes.APPLICATION_OCTET_STREAM);
	}

	protected void testVersionUpdate(
			String addFileName, byte[] addBytes, String addMimeType,
			String updateFileName, byte[] updateBytes, String updateMimeType)
		throws PortalException {

		String description = StringPool.BLANK;
		String changeLog = StringPool.BLANK;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), addFileName,
			addMimeType, addFileName, description, changeLog, addBytes,
			serviceContext);

		fileEntry = DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), updateFileName, updateMimeType,
			updateFileName, description, changeLog,
			DLVersionNumberIncrease.MINOR, updateBytes, serviceContext);

		FileVersion fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals("1.1", fileVersion.getVersion());
		Assert.assertEquals(updateMimeType, fileVersion.getMimeType());
		Assert.assertEquals(updateBytes.length, fileVersion.getSize());
		Assert.assertEquals(
			fileVersion.getExtension(), fileEntry.getExtension());
		Assert.assertEquals(fileVersion.getMimeType(), fileEntry.getMimeType());
		Assert.assertEquals(fileVersion.getSize(), fileEntry.getSize());
	}

	private static final String _BASE_FILE_NAME = "Test";

	private static final String _FULL_FILE_NAME = "Test.txt";

	private static final byte[] _ZERO_BYTES = new byte[0];

}