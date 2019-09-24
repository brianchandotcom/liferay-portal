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
import com.liferay.document.library.test.util.DLAppTestUtil;
import com.liferay.headless.delivery.client.dto.v1_0.DocumentFolder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

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
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"creatorId"};
	}

	@Override
	protected DocumentFolder testGetDocumentFolder_addDocumentFolder()
		throws Exception {

		DocumentFolder postDocumentFolder =
			documentFolderResource.postSiteDocumentFolder(
				testGroup.getGroupId(), randomDocumentFolder());

		Assert.assertEquals(
			Integer.valueOf(0), postDocumentFolder.getNumberOfDocuments());

		DLAppTestUtil.addFileEntryWithWorkflow(
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			testGroup.getGroupId(), postDocumentFolder.getId(),
			StringPool.BLANK, RandomTestUtil.randomString(10), true,
			new ServiceContext());

		DocumentFolder getDocumentFolder =
			documentFolderResource.getDocumentFolder(
				postDocumentFolder.getId());

		Assert.assertEquals(
			Integer.valueOf(1), getDocumentFolder.getNumberOfDocuments());

		return postDocumentFolder;
	}

	@Override
	protected Long
			testGetDocumentFolderDocumentFoldersPage_getParentDocumentFolderId()
		throws Exception {

		DocumentFolder documentFolder =
			documentFolderResource.postSiteDocumentFolder(
				testGroup.getGroupId(), randomDocumentFolder());

		return documentFolder.getId();
	}

}