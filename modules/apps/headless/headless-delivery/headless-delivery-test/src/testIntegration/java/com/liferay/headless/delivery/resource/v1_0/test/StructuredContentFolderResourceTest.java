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
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContentFolder;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.problem.Problem;
import com.liferay.headless.delivery.client.resource.v1_0.StructuredContentFolderResource;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class StructuredContentFolderResourceTest
	extends BaseStructuredContentFolderResourceTestCase {

	@Override
	@Test
	public void testDeleteAssetLibraryStructuredContentFolderByExternalReferenceCode()
		throws Exception {

		super.
			testDeleteAssetLibraryStructuredContentFolderByExternalReferenceCode();

		String externalReferenceCode = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		try {
			structuredContentFolderResource.
				deleteAssetLibraryStructuredContentFolderByExternalReferenceCode(
					testDeleteAssetLibraryStructuredContentFolderByExternalReferenceCode_getAssetLibraryId(),
					externalReferenceCode);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertEquals(
				StringBundler.concat(
					"No JournalFolder exists with the key {",
					"externalReferenceCode=", externalReferenceCode,
					", groupId=", testDepotEntry.getGroupId(), "}"),
				problem.getTitle());
		}
	}

	@Override
	@Test
	public void testGetStructuredContentFolderStructuredContentFoldersPage()
		throws Exception {

		super.testGetStructuredContentFolderStructuredContentFoldersPage();

		StructuredContentFolder postStructuredContentFolder =
			structuredContentFolderResource.
				postAssetLibraryStructuredContentFolder(
					testDepotEntry.getDepotEntryId(),
					_randomStructuredContentFolder());

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

		Assert.assertNotNull(
			page.fetchFirstItem(
			).getCreator(
			).getProfileURL());

		assertValid(page);
	}

	@Override
	@Test
	public void testPostStructuredContentFolderStructuredContentFolder()
		throws Exception {

		super.testPostStructuredContentFolderStructuredContentFolder();

		StructuredContentFolder parentStructuredContentFolder =
			testPostAssetLibraryStructuredContentFolder_addStructuredContentFolder(
				_randomStructuredContentFolder());

		StructuredContentFolder randomStructuredContentFolder =
			_randomStructuredContentFolder();

		randomStructuredContentFolder.setExternalReferenceCode("");

		StructuredContentFolder postStructuredContentFolder =
			structuredContentFolderResource.
				postStructuredContentFolderStructuredContentFolder(
					parentStructuredContentFolder.getId(),
					randomStructuredContentFolder);

		JournalFolder journalFolder = JournalFolderLocalServiceUtil.getFolder(
			postStructuredContentFolder.getId());

		Assert.assertEquals(
			postStructuredContentFolder.getExternalReferenceCode(),
			journalFolder.getUuid());

		assertValid(postStructuredContentFolder);
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

}