/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0.CompanyTestEntity;

import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class CompanyTestEntityResourceTest
	extends BaseCompanyTestEntityResourceTestCase {

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description"};
	}

	@Override
	protected CompanyTestEntity
			testGetCompanyTestEntitiesPage_addCompanyTestEntity(
				CompanyTestEntity companyTestEntity)
		throws Exception {

		return companyTestEntityResource.postCompanyTestEntity(
			companyTestEntity);
	}

	@Override
	protected CompanyTestEntity testGetCompanyTestEntity_addCompanyTestEntity()
		throws Exception {

		return companyTestEntityResource.postCompanyTestEntity(
			randomCompanyTestEntity());
	}

	@Override
	protected CompanyTestEntity
			testGetCompanyTestEntityByExternalReferenceCode_addCompanyTestEntity()
		throws Exception {

		return companyTestEntityResource.postCompanyTestEntity(
			randomCompanyTestEntity());
	}

	@Override
	protected CompanyTestEntity
			testGetCompanyTestEntityPermissionsPage_addCompanyTestEntity()
		throws Exception {

		return companyTestEntityResource.postCompanyTestEntity(
			randomCompanyTestEntity());
	}

	@Override
	protected CompanyTestEntity
			testGraphQLCompanyTestEntity_addCompanyTestEntity()
		throws Exception {

		return companyTestEntityResource.postCompanyTestEntity(
			randomCompanyTestEntity());
	}

	@Override
	protected CompanyTestEntity testPostCompanyTestEntity_addCompanyTestEntity(
			CompanyTestEntity companyTestEntity)
		throws Exception {

		return companyTestEntityResource.postCompanyTestEntity(
			companyTestEntity);
	}

	@Override
	protected CompanyTestEntity
			testPostCompanyTestEntity_addPermissionsCompanyTestEntity(
				CompanyTestEntity companyTestEntity)
		throws Exception {

		return permissionsCompanyTestEntityResource.postCompanyTestEntity(
			companyTestEntity);
	}

	@Override
	protected CompanyTestEntity testPutCompanyTestEntity_addCompanyTestEntity()
		throws Exception {

		return companyTestEntityResource.postCompanyTestEntity(
			randomCompanyTestEntity());
	}

	@Override
	protected CompanyTestEntity
			testPutCompanyTestEntityByExternalReferenceCode_addCompanyTestEntity()
		throws Exception {

		return companyTestEntityResource.postCompanyTestEntity(
			randomCompanyTestEntity());
	}

	@Override
	protected CompanyTestEntity
			testPutCompanyTestEntityPermissionsPage_addCompanyTestEntity()
		throws Exception {

		return companyTestEntityResource.postCompanyTestEntity(
			randomCompanyTestEntity());
	}

}