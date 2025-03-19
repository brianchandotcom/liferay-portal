/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.headless.object.client.dto.v1_0.ObjectEntryFolder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import java.util.Collections;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@FeatureFlags("LPD-17564")
@RunWith(Arquillian.class)
public class ObjectEntryFolderResourceTest
	extends BaseObjectEntryFolderResourceTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_testDepotEntry = _depotEntryLocalService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			null,
			new ServiceContext() {
				{
					setCompanyId(testGroup.getCompanyId());
					setUserId(TestPropsValues.getUserId());
				}
			});
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"label", "name"};
	}

	@Override
	protected ObjectEntryFolder randomObjectEntryFolder() throws Exception {
		return new ObjectEntryFolder() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				label = StringUtil.toLowerCase(RandomTestUtil.randomString());
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				numberOfObjectEntries = RandomTestUtil.randomInt();
				numberOfObjectEntryFolders = RandomTestUtil.randomInt();
				parentObjectEntryFolderId = 0L;
			}
		};
	}

	@Override
	protected ObjectEntryFolder
			testDeleteObjectEntryFolder_addObjectEntryFolder()
		throws Exception {

		return objectEntryFolderResource.postScopeScopeKeyObjectEntryFolder(
			String.valueOf(_testDepotEntry.getGroupId()),
			randomObjectEntryFolder());
	}

	@Override
	protected ObjectEntryFolder
			testDeleteScopeScopeKeyObjectEntryFolderByExternalReferenceCode_addObjectEntryFolder()
		throws Exception {

		return objectEntryFolderResource.postScopeScopeKeyObjectEntryFolder(
			String.valueOf(_testDepotEntry.getGroupId()),
			randomObjectEntryFolder());
	}

	@Override
	protected ObjectEntryFolder testGetObjectEntryFolder_addObjectEntryFolder()
		throws Exception {

		return objectEntryFolderResource.postScopeScopeKeyObjectEntryFolder(
			String.valueOf(_testDepotEntry.getGroupId()),
			randomObjectEntryFolder());
	}

	@Override
	protected ObjectEntryFolder
			testGetScopeScopeKeyObjectEntryFolderByExternalReferenceCode_addObjectEntryFolder()
		throws Exception {

		return objectEntryFolderResource.postScopeScopeKeyObjectEntryFolder(
			String.valueOf(_testDepotEntry.getGroupId()),
			randomObjectEntryFolder());
	}

	@Override
	protected ObjectEntryFolder
			testGetScopeScopeKeyObjectEntryFoldersPage_addObjectEntryFolder(
				String scopeKey, ObjectEntryFolder objectEntryFolder)
		throws Exception {

		return objectEntryFolderResource.postScopeScopeKeyObjectEntryFolder(
			scopeKey, objectEntryFolder);
	}

	@Override
	protected String testGetScopeScopeKeyObjectEntryFoldersPage_getScopeKey() {
		return String.valueOf(_testDepotEntry.getGroupId());
	}

	@Override
	protected ObjectEntryFolder
			testGraphQLObjectEntryFolder_addObjectEntryFolder()
		throws Exception {

		return objectEntryFolderResource.postScopeScopeKeyObjectEntryFolder(
			String.valueOf(_testDepotEntry.getGroupId()),
			randomObjectEntryFolder());
	}

	@Override
	protected ObjectEntryFolder
			testPatchObjectEntryFolder_addObjectEntryFolder()
		throws Exception {

		return objectEntryFolderResource.postScopeScopeKeyObjectEntryFolder(
			String.valueOf(_testDepotEntry.getGroupId()),
			randomObjectEntryFolder());
	}

	protected ObjectEntryFolder
			testPatchScopeScopeKeyObjectEntryFolderByExternalReferenceCode_addObjectEntryFolder()
		throws Exception {

		return objectEntryFolderResource.postScopeScopeKeyObjectEntryFolder(
			String.valueOf(_testDepotEntry.getGroupId()),
			randomObjectEntryFolder());
	}

	protected ObjectEntryFolder
			testPostScopeScopeKeyObjectEntryFolder_addObjectEntryFolder(
				ObjectEntryFolder objectEntryFolder)
		throws Exception {

		return objectEntryFolderResource.postScopeScopeKeyObjectEntryFolder(
			String.valueOf(_testDepotEntry.getGroupId()), objectEntryFolder);
	}

	@Override
	protected ObjectEntryFolder
			testPutScopeScopeKeyObjectEntryFolderByExternalReferenceCode_addObjectEntryFolder()
		throws Exception {

		return objectEntryFolderResource.postScopeScopeKeyObjectEntryFolder(
			String.valueOf(_testDepotEntry.getGroupId()),
			randomObjectEntryFolder());
	}

	@Override
	protected ObjectEntryFolder
			testPutScopeScopeKeyObjectEntryFolderByExternalReferenceCode_createObjectEntryFolder()
		throws Exception {

		ObjectEntryFolder objectEntryFolder = randomObjectEntryFolder();

		objectEntryFolder.setScopeKey(
			String.valueOf(_testDepotEntry.getGroupId()));

		return objectEntryFolder;
	}

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private DepotEntry _testDepotEntry;

}