/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.delivery.client.dto.v1_0.ObjectEntryFolder;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;

import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@FeatureFlags("LPD-17564")
@RunWith(Arquillian.class)
public class ObjectEntryFolderResourceTest
	extends BaseObjectEntryFolderResourceTestCase {

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"label", "name"};
	}

	@Override
	protected ObjectEntryFolder randomObjectEntryFolder() throws Exception {
		return new ObjectEntryFolder() {
			{
				assetLibraryId = testDepotEntry.getDepotEntryId();
				assetLibraryKey = testGroup.getGroupKey();
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

		return objectEntryFolderResource.postAssetLibraryObjectEntryFolder(
			testDepotEntry.getDepotEntryId(), randomObjectEntryFolder());
	}

	@Override
	protected ObjectEntryFolder testGetObjectEntryFolder_addObjectEntryFolder()
		throws Exception {

		return objectEntryFolderResource.postAssetLibraryObjectEntryFolder(
			testDepotEntry.getDepotEntryId(), randomObjectEntryFolder());
	}

	@Override
	protected ObjectEntryFolder
			testGraphQLObjectEntryFolder_addObjectEntryFolder()
		throws Exception {

		return objectEntryFolderResource.postAssetLibraryObjectEntryFolder(
			testDepotEntry.getDepotEntryId(), randomObjectEntryFolder());
	}

	@Override
	protected ObjectEntryFolder
			testPatchObjectEntryFolder_addObjectEntryFolder()
		throws Exception {

		return objectEntryFolderResource.postAssetLibraryObjectEntryFolder(
			testDepotEntry.getDepotEntryId(), randomObjectEntryFolder());
	}

	@Override
	protected ObjectEntryFolder testPutObjectEntryFolder_addObjectEntryFolder()
		throws Exception {

		return objectEntryFolderResource.postAssetLibraryObjectEntryFolder(
			testDepotEntry.getDepotEntryId(), randomObjectEntryFolder());
	}

}