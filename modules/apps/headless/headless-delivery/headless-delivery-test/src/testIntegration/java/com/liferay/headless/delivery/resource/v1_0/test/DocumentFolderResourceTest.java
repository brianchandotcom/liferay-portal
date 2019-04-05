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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.delivery.dto.v1_0.DocumentFolder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;

import java.util.Objects;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@Ignore
@RunWith(Arquillian.class)
public class DocumentFolderResourceTest
	extends BaseDocumentFolderResourceTestCase {

	@Override
	protected void assertValid(DocumentFolder documentFolder) {
		boolean valid = false;

		if (Objects.equals(
				documentFolder.getContentSpaceId(), testGroup.getGroupId()) &&
			(documentFolder.getDateCreated() != null) &&
			(documentFolder.getDateModified() != null) &&
			(documentFolder.getId() != null)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected boolean equals(
		DocumentFolder documentFolder1, DocumentFolder documentFolder2) {

		if (Objects.equals(
				documentFolder1.getDescription(),
				documentFolder2.getDescription()) &&
			Objects.equals(
				documentFolder1.getName(), documentFolder2.getName())) {

			return true;
		}

		return false;
	}

	@Override
	protected DocumentFolder randomDocumentFolder() {
		return new DocumentFolder() {
			{
				description = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
			}
		};
	}

	protected DocumentFolder randomPatchDocumentFolder() {
		return new DocumentFolder() {
			{
				description = RandomTestUtil.randomString();
			}
		};
	}

	@Override
	protected DocumentFolder testDeleteDocumentFolder_addDocumentFolder()
		throws Exception {

		return invokePostContentSpaceDocumentFolder(
			testGroup.getGroupId(), randomDocumentFolder());
	}

	@Override
	protected DocumentFolder
			testGetContentSpaceDocumentFoldersPage_addDocumentFolder(
				Long contentSpaceId, DocumentFolder documentFolder)
		throws Exception {

		return invokePostContentSpaceDocumentFolder(
			contentSpaceId, documentFolder);
	}

	@Override
	protected DocumentFolder testGetDocumentFolder_addDocumentFolder()
		throws Exception {

		DocumentFolder postDocumentFolder =
			invokePostContentSpaceDocumentFolder(
				testGroup.getGroupId(), randomDocumentFolder());

		Assert.assertEquals(0, postDocumentFolder.getNumberOfDocuments());

		DLAppTestUtil.addFileEntryWithWorkflow(
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			testGroup.getGroupId(), postDocumentFolder.getId(),
			StringPool.BLANK, RandomTestUtil.randomString(10), true,
			new ServiceContext());

		DocumentFolder getDocumentFolder = invokeGetDocumentFolder(
			postDocumentFolder.getId());

		Assert.assertEquals(1, getDocumentFolder.getNumberOfDocuments());

		return postDocumentFolder;
	}

	@Override
	protected DocumentFolder
			testGetDocumentFolderDocumentFoldersPage_addDocumentFolder(
				Long documentFolderId, DocumentFolder documentFolder)
		throws Exception {

		return invokePostDocumentFolderDocumentFolder(
			documentFolderId, documentFolder);
	}

	@Override
	protected Long
			testGetDocumentFolderDocumentFoldersPage_getParentDocumentFolderId()
		throws Exception {

		DocumentFolder documentFolder = invokePostContentSpaceDocumentFolder(
			testGroup.getGroupId(), randomDocumentFolder());

		return documentFolder.getId();
	}

	@Override
	protected DocumentFolder testPatchDocumentFolder_addDocumentFolder()
		throws Exception {

		return invokePostContentSpaceDocumentFolder(
			testGroup.getGroupId(), randomDocumentFolder());
	}

	@Override
	protected DocumentFolder
			testPostContentSpaceDocumentFolder_addDocumentFolder(
				DocumentFolder documentFolder)
		throws Exception {

		DocumentFolder postDocumentFolder =
			invokePostContentSpaceDocumentFolder(
				testGroup.getGroupId(), documentFolder);

		Assert.assertEquals(0, postDocumentFolder.getNumberOfDocuments());

		return postDocumentFolder;
	}

	@Override
	protected DocumentFolder
			testPostDocumentFolderDocumentFolder_addDocumentFolder(
				DocumentFolder documentFolder)
		throws Exception {

		DocumentFolder parentDocumentFolder =
			invokePostContentSpaceDocumentFolder(
				testGroup.getGroupId(), randomDocumentFolder());

		return invokePostDocumentFolderDocumentFolder(
			parentDocumentFolder.getId(), documentFolder);
	}

	@Override
	protected DocumentFolder testPutDocumentFolder_addDocumentFolder()
		throws Exception {

		return invokePostContentSpaceDocumentFolder(
			testGroup.getGroupId(), randomDocumentFolder());
	}

}