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

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class DocumentFolderResourceTest
	extends BaseDocumentFolderResourceTestCase {

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "name"};
	}

	@Override
	protected DocumentFolder randomDocumentFolder() {
		return new DocumentFolder() {
			{
				description = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
				siteId = testGroup.getGroupId();
			}
		};
	}

	protected DocumentFolder randomPatchDocumentFolder() {
		return new DocumentFolder() {
			{
				description = RandomTestUtil.randomString();
				siteId = testGroup.getGroupId();
			}
		};
	}

	@Override
	protected DocumentFolder testGetDocumentFolder_addDocumentFolder()
		throws Exception {

		DocumentFolder postDocumentFolder = invokePostSiteDocumentFolder(
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
	protected Long
			testGetDocumentFolderDocumentFoldersPage_getParentDocumentFolderId()
		throws Exception {

		DocumentFolder documentFolder = invokePostSiteDocumentFolder(
			testGroup.getGroupId(), randomDocumentFolder());

		return documentFolder.getId();
	}

}