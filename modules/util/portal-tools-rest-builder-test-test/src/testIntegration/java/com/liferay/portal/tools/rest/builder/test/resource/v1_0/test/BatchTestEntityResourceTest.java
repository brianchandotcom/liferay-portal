/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0.BatchTestEntity;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class BatchTestEntityResourceTest
	extends BaseBatchTestEntityResourceTestCase {

	@Ignore
	@Override
	@Test
	public void testGraphQLGetBatchTestEntitiesPage() throws Exception {
		super.testGraphQLGetBatchTestEntitiesPage();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetBatchTestEntity() throws Exception {
		super.testGraphQLGetBatchTestEntity();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetBatchTestEntityByExternalReferenceCode()
		throws Exception {

		super.testGraphQLGetBatchTestEntityByExternalReferenceCode();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetBatchTestEntityByExternalReferenceCodeNotFound()
		throws Exception {

		super.testGraphQLGetBatchTestEntityByExternalReferenceCodeNotFound();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetBatchTestEntityNotFound() throws Exception {
		super.testGraphQLGetBatchTestEntityNotFound();
	}

	@Override
	protected BatchTestEntity
			testBatchEngineDeleteImportTask_addBatchTestEntity()
		throws Exception {

		return batchTestEntityResource.postBatchTestEntity(
			randomBatchTestEntity());
	}

	@Override
	protected BatchTestEntity
			testDeleteBatchTestEntityByExternalReferenceCode_addBatchTestEntity()
		throws Exception {

		return batchTestEntityResource.postBatchTestEntity(
			randomBatchTestEntity());
	}

	@Override
	protected BatchTestEntity testGetBatchTestEntitiesPage_addBatchTestEntity(
			BatchTestEntity batchTestEntity)
		throws Exception {

		return batchTestEntityResource.postBatchTestEntity(
			randomBatchTestEntity());
	}

	@Override
	protected BatchTestEntity testGetBatchTestEntity_addBatchTestEntity()
		throws Exception {

		return batchTestEntityResource.postBatchTestEntity(
			randomBatchTestEntity());
	}

	@Override
	protected BatchTestEntity
			testGetBatchTestEntityByExternalReferenceCode_addBatchTestEntity()
		throws Exception {

		return batchTestEntityResource.postBatchTestEntity(
			randomBatchTestEntity());
	}

	@Override
	protected BatchTestEntity testGraphQLBatchTestEntity_addBatchTestEntity()
		throws Exception {

		return batchTestEntityResource.postBatchTestEntity(
			randomBatchTestEntity());
	}

	@Override
	protected BatchTestEntity testPostBatchTestEntity_addBatchTestEntity(
			BatchTestEntity batchTestEntity)
		throws Exception {

		return batchTestEntityResource.postBatchTestEntity(
			randomBatchTestEntity());
	}

	@Override
	protected BatchTestEntity
			testPutBatchTestEntityByExternalReferenceCode_addBatchTestEntity()
		throws Exception {

		return batchTestEntityResource.postBatchTestEntity(
			randomBatchTestEntity());
	}

}