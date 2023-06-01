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
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContentFolder;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.StructuredContentFolderResource;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class StructuredContentFolderResourceTest
	extends BaseStructuredContentFolderResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		testDepotEntry = DepotEntryLocalServiceUtil.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			null,
			ServiceContextTestUtil.getServiceContext(testGroup.getGroupId()));
	}

	@Override
	@Test
	public void testGetStructuredContentFolderStructuredContentFoldersPage()
		throws Exception {

		super.testGetStructuredContentFolderStructuredContentFoldersPage();

		_testGetStructuredContentFolderStructuredContentFoldersPageWithProfileURL();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "name"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"creatorId"};
	}

	@Override
	protected StructuredContentFolder
			testDeleteAssetLibraryStructuredContentFolderByExternalReferenceCode_addStructuredContentFolder()
		throws Exception {

		return testPostAssetLibraryStructuredContentFolder_addStructuredContentFolder(
			randomStructuredContentFolder());
	}

	@Override
	protected Long
			testDeleteAssetLibraryStructuredContentFolderByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	@Override
	protected StructuredContentFolder
			testGetAssetLibraryStructuredContentFolderByExternalReferenceCode_addStructuredContentFolder()
		throws Exception {

		return testPostAssetLibraryStructuredContentFolder_addStructuredContentFolder(
			randomStructuredContentFolder());
	}

	@Override
	protected Long
			testGetAssetLibraryStructuredContentFolderByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	@Override
	protected Long
			testGetStructuredContentFolderStructuredContentFoldersPage_getIrrelevantParentStructuredContentFolderId()
		throws Exception {

		JournalFolder journalFolder = JournalTestUtil.addFolder(
			irrelevantGroup.getGroupId(), RandomTestUtil.randomString());

		return journalFolder.getFolderId();
	}

	@Override
	protected Long
			testGetStructuredContentFolderStructuredContentFoldersPage_getParentStructuredContentFolderId()
		throws Exception {

		JournalFolder journalFolder = JournalTestUtil.addFolder(
			testGroup.getGroupId(), RandomTestUtil.randomString());

		return journalFolder.getFolderId();
	}

	@Override
	protected StructuredContentFolder
			testGraphQLGetAssetLibraryStructuredContentFolderByExternalReferenceCode_addStructuredContentFolder()
		throws Exception {

		return testGetAssetLibraryStructuredContentFolderByExternalReferenceCode_addStructuredContentFolder();
	}

	@Override
	protected Long
			testGraphQLGetAssetLibraryStructuredContentFolderByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	@Override
	protected StructuredContentFolder
			testPutAssetLibraryStructuredContentFolderByExternalReferenceCode_addStructuredContentFolder()
		throws Exception {

		return testPostAssetLibraryStructuredContentFolder_addStructuredContentFolder(
			randomStructuredContentFolder());
	}

	@Override
	protected StructuredContentFolder
			testPutAssetLibraryStructuredContentFolderByExternalReferenceCode_createStructuredContentFolder()
		throws Exception {

		return _randomStructuredContentFolder();
	}

	@Override
	protected Long
			testPutAssetLibraryStructuredContentFolderByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	@Override
	protected StructuredContentFolder
			testPutSiteStructuredContentFolderByExternalReferenceCode_createStructuredContentFolder()
		throws Exception {

		return _randomStructuredContentFolder();
	}

	private StructuredContentFolder _randomStructuredContentFolder()
		throws Exception {

		return new StructuredContentFolder() {
			{
				assetLibraryKey = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				numberOfStructuredContentFolders = RandomTestUtil.randomInt();
				numberOfStructuredContents = RandomTestUtil.randomInt();
				parentStructuredContentFolderId = 0L;
				siteId = testGroup.getGroupId();
				subscribed = RandomTestUtil.randomBoolean();
			}
		};
	}

	private void _testGetStructuredContentFolderStructuredContentFoldersPageWithProfileURL()
		throws Exception {

		StructuredContentFolder structuredContentFolder =
			_randomStructuredContentFolder();

		StructuredContentFolder postStructuredContentFolder =
			structuredContentFolderResource.
				postAssetLibraryStructuredContentFolder(
					testDepotEntry.getDepotEntryId(), structuredContentFolder);

		StructuredContentFolderResource.Builder builder =
			StructuredContentFolderResource.builder();

		structuredContentFolderResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).parameters(
			"nestedFields", "profileURL"
		).build();

		Page<StructuredContentFolder> page =
			structuredContentFolderResource.
				getAssetLibraryStructuredContentFoldersPage(
					testDepotEntry.getDepotEntryId(), null, null, null, null,
					Pagination.of(1, 10), null);

		Assert.assertEquals(1, page.getTotalCount());

		Assert.assertEquals(
			postStructuredContentFolder.getId(),
			page.fetchFirstItem(
			).getId());

		Assert.assertEquals(
			"/web/test",
			page.fetchFirstItem(
			).getCreator(
			).getProfileURL());

		assertValid(page);
	}

}