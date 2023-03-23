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
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.test.util.DLAppTestUtil;
import com.liferay.headless.delivery.client.dto.v1_0.Creator;
import com.liferay.headless.delivery.client.dto.v1_0.DocumentFolder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;

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
	protected DocumentFolder
			testGetAssetLibraryDocumentFoldersRatedByMePage_addDocumentFolder(
				Long assetLibraryId, DocumentFolder documentFolder)
		throws Exception {

		DocumentFolder addedDocumentFolder =
			super.
				testGetAssetLibraryDocumentFoldersRatedByMePage_addDocumentFolder(
					assetLibraryId, documentFolder);

		_addDocumentFolderRatingsEntry(addedDocumentFolder);

		return addedDocumentFolder;
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

	@Override
	protected DocumentFolder
			testGetSiteDocumentFoldersRatedByMePage_addDocumentFolder(
				Long siteId, DocumentFolder documentFolder)
		throws Exception {

		DocumentFolder addedDocumentFolder =
			super.testGetSiteDocumentFoldersRatedByMePage_addDocumentFolder(
				siteId, documentFolder);

		_addDocumentFolderRatingsEntry(addedDocumentFolder);

		return addedDocumentFolder;
	}

	private void _addDocumentFolderRatingsEntry(DocumentFolder documentFolder)
		throws Exception {

		Creator creator = documentFolder.getCreator();

		_ratingsEntryLocalService.updateEntry(
			creator.getId(), DLFolder.class.getName(), documentFolder.getId(),
			1.0,
			ServiceContextTestUtil.getServiceContext(testGroup.getGroupId()));
	}

	@Inject
	private RatingsEntryLocalService _ratingsEntryLocalService;

}