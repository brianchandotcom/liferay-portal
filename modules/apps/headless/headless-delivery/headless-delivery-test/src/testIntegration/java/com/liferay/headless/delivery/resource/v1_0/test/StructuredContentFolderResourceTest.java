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

package com.liferay.headless.web.experience.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContentFolder;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Objects;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class StructuredContentFolderResourceTest
	extends BaseStructuredContentFolderResourceTestCase {

	@Override
	protected void assertValid(
		StructuredContentFolder structuredContentFolder) {

		boolean valid = false;

		if (Objects.equals(
				structuredContentFolder.getContentSpaceId(),
				testGroup.getGroupId()) &&
			(structuredContentFolder.getDateCreated() != null) &&
			(structuredContentFolder.getDateModified() != null) &&
			(structuredContentFolder.getId() != null)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected boolean equals(
		StructuredContentFolder structuredContentFolder1,
		StructuredContentFolder structuredContentFolder2) {

		if (Objects.equals(
				structuredContentFolder1.getContentSpaceId(),
				structuredContentFolder1.getContentSpaceId()) &&
			Objects.equals(
				structuredContentFolder1.getDescription(),
				structuredContentFolder1.getDescription()) &&
			Objects.equals(
				structuredContentFolder1.getName(),
				structuredContentFolder1.getName())) {

			return true;
		}

		return false;
	}

	@Override
	protected StructuredContentFolder
			testDeleteStructuredContentFolder_addStructuredContentFolder()
		throws Exception {

		return invokePostContentSpaceStructuredContentFolder(
			testGroup.getGroupId(), randomStructuredContentFolder());
	}

	@Override
	protected StructuredContentFolder
			testGetContentSpaceStructuredContentFoldersPage_addStructuredContentFolder(
				Long contentSpaceId,
				StructuredContentFolder structuredContentFolder)
		throws Exception {

		return invokePostContentSpaceStructuredContentFolder(
			contentSpaceId, structuredContentFolder);
	}

	@Override
	protected StructuredContentFolder
			testGetStructuredContentFolder_addStructuredContentFolder()
		throws Exception {

		return invokePostContentSpaceStructuredContentFolder(
			testGroup.getGroupId(), randomStructuredContentFolder());
	}

	@Override
	protected StructuredContentFolder
			testGetStructuredContentFolderStructuredContentFoldersPage_addStructuredContentFolder(
				Long structuredContentFolderId,
				StructuredContentFolder structuredContentFolder)
		throws Exception {

		return invokePostStructuredContentFolderStructuredContentFolder(
			structuredContentFolderId, structuredContentFolder);
	}

	@Override
	protected Long
			testGetStructuredContentFolderStructuredContentFoldersPage_getIrrelevantStructuredContentFolderId()
		throws Exception {

		JournalFolder journalFolder = JournalTestUtil.addFolder(
			irrelevantGroup.getGroupId(), RandomTestUtil.randomString());

		return journalFolder.getFolderId();
	}

	@Override
	protected Long
			testGetStructuredContentFolderStructuredContentFoldersPage_getStructuredContentFolderId()
		throws Exception {

		JournalFolder journalFolder = JournalTestUtil.addFolder(
			testGroup.getGroupId(), RandomTestUtil.randomString());

		return journalFolder.getFolderId();
	}

	@Override
	protected StructuredContentFolder
			testPostContentSpaceStructuredContentFolder_addStructuredContentFolder(
				StructuredContentFolder structuredContentFolder)
		throws Exception {

		return invokePostContentSpaceStructuredContentFolder(
			testGroup.getGroupId(), structuredContentFolder);
	}

	@Override
	protected StructuredContentFolder
			testPostStructuredContentFolderStructuredContentFolder_addStructuredContentFolder(
				StructuredContentFolder structuredContentFolder)
		throws Exception {

		return invokePostContentSpaceStructuredContentFolder(
			testGroup.getGroupId(), structuredContentFolder);
	}

	@Override
	protected StructuredContentFolder
			testPutStructuredContentFolder_addStructuredContentFolder()
		throws Exception {

		return invokePostContentSpaceStructuredContentFolder(
			testGroup.getGroupId(), randomStructuredContentFolder());
	}

}