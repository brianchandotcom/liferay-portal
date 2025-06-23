/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0.ScopedTestEntity;

import java.util.Objects;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class ScopedTestEntityResourceTest
	extends BaseScopedTestEntityResourceTestCase {

	@Ignore
	@Override
	@Test
	public void testBatchEngineDeleteImportTask() throws Exception {
		super.testBatchEngineDeleteImportTask();
	}

	@Override
	protected void assertValid(ScopedTestEntity scopedTestEntity)
		throws Exception {

		boolean valid = true;

		if ((scopedTestEntity.getDateCreated() == null) ||
			(scopedTestEntity.getDateModified() == null) ||
			(scopedTestEntity.getExternalReferenceCode() == null)) {

			valid = false;
		}

		if ((scopedTestEntity.getAssetLibraryKey() != null) &&
			(!Objects.equals(
				scopedTestEntity.getAssetLibraryKey(),
				String.valueOf(testDepotEntry.getGroupId())) ||
			 !Objects.equals(scopedTestEntity.getSiteId(), 0L))) {

			valid = false;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected ScopedTestEntity
			testDeleteAssetLibraryScopedTestEntityByExternalReferenceCode_addScopedTestEntity()
		throws Exception {

		ScopedTestEntity scopedTestEntity = randomScopedTestEntity();

		return scopedTestEntityResource.
			postAssetLibraryScopedTestEntityByExternalReferenceCode(
				Long.valueOf(scopedTestEntity.getAssetLibraryKey()),
				scopedTestEntity.getExternalReferenceCode(), scopedTestEntity);
	}

	@Override
	protected Long
			testDeleteAssetLibraryScopedTestEntityByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	@Override
	protected ScopedTestEntity
			testDeleteScopedTestEntityByExternalReferenceCode_addScopedTestEntity()
		throws Exception {

		ScopedTestEntity scopedTestEntity = randomScopedTestEntity();

		return scopedTestEntityResource.
			postScopedTestEntityByExternalReferenceCode(
				scopedTestEntity.getExternalReferenceCode(), scopedTestEntity);
	}

	@Override
	protected ScopedTestEntity
			testDeleteSiteScopedTestEntityByExternalReferenceCode_addScopedTestEntity()
		throws Exception {

		ScopedTestEntity scopedTestEntity = randomScopedTestEntity();

		return scopedTestEntityResource.
			postSiteScopedTestEntityByExternalReferenceCode(
				scopedTestEntity.getSiteId(),
				scopedTestEntity.getExternalReferenceCode(), scopedTestEntity);
	}

	@Override
	protected ScopedTestEntity
			testGetAssetLibraryScopedTestEntitiesPage_addScopedTestEntity(
				Long assetLibraryId, ScopedTestEntity scopedTestEntity)
		throws Exception {

		return scopedTestEntityResource.
			postAssetLibraryScopedTestEntityByExternalReferenceCode(
				assetLibraryId, scopedTestEntity.getExternalReferenceCode(),
				scopedTestEntity);
	}

	@Override
	protected ScopedTestEntity
			testGetAssetLibraryScopedTestEntityByExternalReferenceCode_addScopedTestEntity()
		throws Exception {

		return testDeleteAssetLibraryScopedTestEntityByExternalReferenceCode_addScopedTestEntity();
	}

	@Override
	protected Long
			testGetAssetLibraryScopedTestEntityByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	@Override
	protected ScopedTestEntity
			testGetScopedTestEntitiesPage_addScopedTestEntity(
				ScopedTestEntity scopedTestEntity)
		throws Exception {

		return scopedTestEntityResource.
			postScopedTestEntityByExternalReferenceCode(
				scopedTestEntity.getExternalReferenceCode(), scopedTestEntity);
	}

	@Override
	protected ScopedTestEntity
			testGetScopedTestEntityByExternalReferenceCode_addScopedTestEntity()
		throws Exception {

		return testDeleteScopedTestEntityByExternalReferenceCode_addScopedTestEntity();
	}

	@Override
	protected ScopedTestEntity
			testGetSiteScopedTestEntitiesPage_addScopedTestEntity(
				Long siteId, ScopedTestEntity scopedTestEntity)
		throws Exception {

		return scopedTestEntityResource.
			postSiteScopedTestEntityByExternalReferenceCode(
				siteId, scopedTestEntity.getExternalReferenceCode(),
				scopedTestEntity);
	}

	@Override
	protected ScopedTestEntity
			testGetSiteScopedTestEntityByExternalReferenceCode_addScopedTestEntity()
		throws Exception {

		return testDeleteSiteScopedTestEntityByExternalReferenceCode_addScopedTestEntity();
	}

	@Override
	protected ScopedTestEntity
			testPatchAssetLibraryScopedTestEntityByExternalReferenceCode_addScopedTestEntity()
		throws Exception {

		return testDeleteAssetLibraryScopedTestEntityByExternalReferenceCode_addScopedTestEntity();
	}

	@Override
	protected ScopedTestEntity
			testPatchScopedTestEntityByExternalReferenceCode_addScopedTestEntity()
		throws Exception {

		return testDeleteScopedTestEntityByExternalReferenceCode_addScopedTestEntity();
	}

	@Override
	protected ScopedTestEntity
			testPatchSiteScopedTestEntityByExternalReferenceCode_addScopedTestEntity()
		throws Exception {

		return testDeleteSiteScopedTestEntityByExternalReferenceCode_addScopedTestEntity();
	}

	@Override
	protected ScopedTestEntity
			testPostAssetLibraryScopedTestEntityByExternalReferenceCode_addScopedTestEntity(
				ScopedTestEntity scopedTestEntity)
		throws Exception {

		return scopedTestEntityResource.
			postAssetLibraryScopedTestEntityByExternalReferenceCode(
				Long.valueOf(scopedTestEntity.getAssetLibraryKey()),
				scopedTestEntity.getExternalReferenceCode(), scopedTestEntity);
	}

	@Override
	protected ScopedTestEntity
			testPostScopedTestEntityByExternalReferenceCode_addScopedTestEntity(
				ScopedTestEntity scopedTestEntity)
		throws Exception {

		return scopedTestEntityResource.
			postScopedTestEntityByExternalReferenceCode(
				scopedTestEntity.getExternalReferenceCode(), scopedTestEntity);
	}

	@Override
	protected ScopedTestEntity
			testPostSiteScopedTestEntityByExternalReferenceCode_addScopedTestEntity(
				ScopedTestEntity scopedTestEntity)
		throws Exception {

		return scopedTestEntityResource.
			postSiteScopedTestEntityByExternalReferenceCode(
				scopedTestEntity.getSiteId(),
				scopedTestEntity.getExternalReferenceCode(), scopedTestEntity);
	}

	@Override
	protected ScopedTestEntity
			testPutAssetLibraryScopedTestEntityByExternalReferenceCode_addScopedTestEntity()
		throws Exception {

		return testDeleteAssetLibraryScopedTestEntityByExternalReferenceCode_addScopedTestEntity();
	}

	@Override
	protected Long
			testPutAssetLibraryScopedTestEntityByExternalReferenceCode_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	@Override
	protected ScopedTestEntity
			testPutScopedTestEntityByExternalReferenceCode_addScopedTestEntity()
		throws Exception {

		return testDeleteScopedTestEntityByExternalReferenceCode_addScopedTestEntity();
	}

	@Override
	protected ScopedTestEntity
			testPutSiteScopedTestEntityByExternalReferenceCode_addScopedTestEntity()
		throws Exception {

		return testDeleteSiteScopedTestEntityByExternalReferenceCode_addScopedTestEntity();
	}

}