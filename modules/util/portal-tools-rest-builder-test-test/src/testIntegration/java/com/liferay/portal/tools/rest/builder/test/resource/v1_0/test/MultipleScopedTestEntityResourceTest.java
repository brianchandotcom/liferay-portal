/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0.MultipleScopedTestEntity;

import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class MultipleScopedTestEntityResourceTest
	extends BaseMultipleScopedTestEntityResourceTestCase {

	@Override
	protected MultipleScopedTestEntity
			testDeleteAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		MultipleScopedTestEntity multipleScopedTestEntity =
			randomMultipleScopedTestEntity();

		return multipleScopedTestEntityResource.
			postAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
				Long.valueOf(multipleScopedTestEntity.getAssetLibraryKey()),
				multipleScopedTestEntity.getExternalReferenceCode(),
				multipleScopedTestEntity);
	}

	@Override
	protected Long
			testDeleteAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	@Override
	protected MultipleScopedTestEntity
			testDeleteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		MultipleScopedTestEntity multipleScopedTestEntity =
			randomMultipleScopedTestEntity();

		return multipleScopedTestEntityResource.
			postMultipleScopedTestEntityByExternalReferenceCode(
				multipleScopedTestEntity.getExternalReferenceCode(),
				multipleScopedTestEntity);
	}

	@Override
	protected MultipleScopedTestEntity
			testDeleteSiteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		MultipleScopedTestEntity multipleScopedTestEntity =
			randomMultipleScopedTestEntity();

		return multipleScopedTestEntityResource.
			postSiteMultipleScopedTestEntityByExternalReferenceCode(
				multipleScopedTestEntity.getSiteId(),
				multipleScopedTestEntity.getExternalReferenceCode(),
				multipleScopedTestEntity);
	}

	@Override
	protected MultipleScopedTestEntity
			testGetAssetLibraryMultipleScopedTestEntitiesPage_addMultipleScopedTestEntity(
				Long assetLibraryId,
				MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		return multipleScopedTestEntityResource.
			postAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
				assetLibraryId,
				multipleScopedTestEntity.getExternalReferenceCode(),
				multipleScopedTestEntity);
	}

	@Override
	protected MultipleScopedTestEntity
			testGetAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		return testDeleteAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity();
	}

	@Override
	protected Long
			testGetAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	@Override
	protected MultipleScopedTestEntity
			testGetMultipleScopedTestEntitiesPage_addMultipleScopedTestEntity(
				MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		return multipleScopedTestEntityResource.
			postMultipleScopedTestEntityByExternalReferenceCode(
				multipleScopedTestEntity.getExternalReferenceCode(),
				multipleScopedTestEntity);
	}

	@Override
	protected MultipleScopedTestEntity
			testGetMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		return testDeleteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity();
	}

	@Override
	protected MultipleScopedTestEntity
			testGetSiteMultipleScopedTestEntitiesPage_addMultipleScopedTestEntity(
				Long siteId, MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		return multipleScopedTestEntityResource.
			postSiteMultipleScopedTestEntityByExternalReferenceCode(
				siteId, multipleScopedTestEntity.getExternalReferenceCode(),
				multipleScopedTestEntity);
	}

	@Override
	protected MultipleScopedTestEntity
			testGetSiteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		return testDeleteSiteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity();
	}

	@Override
	protected MultipleScopedTestEntity
			testPatchAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		return testDeleteAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity();
	}

	@Override
	protected MultipleScopedTestEntity
			testPatchMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		return testDeleteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity();
	}

	@Override
	protected MultipleScopedTestEntity
			testPatchSiteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		return testDeleteSiteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity();
	}

	@Override
	protected MultipleScopedTestEntity
			testPostAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity(
				MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		return multipleScopedTestEntityResource.
			postAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
				Long.valueOf(multipleScopedTestEntity.getAssetLibraryKey()),
				multipleScopedTestEntity.getExternalReferenceCode(),
				multipleScopedTestEntity);
	}

	@Override
	protected MultipleScopedTestEntity
			testPostMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity(
				MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		return multipleScopedTestEntityResource.
			postMultipleScopedTestEntityByExternalReferenceCode(
				multipleScopedTestEntity.getExternalReferenceCode(),
				multipleScopedTestEntity);
	}

	@Override
	protected MultipleScopedTestEntity
			testPostSiteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity(
				MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		return multipleScopedTestEntityResource.
			postSiteMultipleScopedTestEntityByExternalReferenceCode(
				multipleScopedTestEntity.getSiteId(),
				multipleScopedTestEntity.getExternalReferenceCode(),
				multipleScopedTestEntity);
	}

	@Override
	protected MultipleScopedTestEntity
			testPutAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		return testDeleteAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity();
	}

	@Override
	protected Long
			testPutAssetLibraryMultipleScopedTestEntityByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	@Override
	protected MultipleScopedTestEntity
			testPutMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		return testDeleteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity();
	}

	@Override
	protected MultipleScopedTestEntity
			testPutSiteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity()
		throws Exception {

		return testDeleteSiteMultipleScopedTestEntityByExternalReferenceCode_addMultipleScopedTestEntity();
	}

}