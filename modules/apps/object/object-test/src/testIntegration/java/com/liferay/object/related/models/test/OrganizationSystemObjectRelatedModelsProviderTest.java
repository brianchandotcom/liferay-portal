/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.related.models.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.exception.NoSuchOrganizationException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;

import org.junit.runner.RunWith;

/**
 * @author Pedro Leite
 */
@RunWith(Arquillian.class)
public class OrganizationSystemObjectRelatedModelsProviderTest
	extends BaseSystemObjectRelatedModelsProviderTestCase {

	@Override
	protected long[] addBaseModels(int count) throws Exception {
		long[] organizationIds = new long[count];

		for (int i = 0; i < count; i++) {
			Organization organization = OrganizationTestUtil.addOrganization(
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
				RandomTestUtil.randomString(), false);

			organizationIds[i] = organization.getOrganizationId();
		}

		return organizationIds;
	}

	@Override
	protected void assertFailure(long primaryKey) {
		AssertUtils.assertFailure(
			NoSuchOrganizationException.class,
			"No Organization exists with the primary key " + primaryKey,
			() -> _organizationLocalService.getOrganization(primaryKey));
	}

	@Override
	protected void deleteBaseModel(long primaryKey) throws Exception {
		_organizationLocalService.deleteOrganization(primaryKey);
	}

	@Override
	protected Object fetchBaseModel(long primaryKey) {
		return _organizationLocalService.fetchOrganization(primaryKey);
	}

	@Override
	protected String getName(long primaryKey) throws Exception {
		Organization organization = _organizationLocalService.getOrganization(
			primaryKey);

		return organization.getName();
	}

	@Override
	protected ObjectDefinition getSystemObjectDefinition() throws Exception {
		return _objectDefinitionLocalService.fetchObjectDefinitionByClassName(
			TestPropsValues.getCompanyId(), Organization.class.getName());
	}

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private OrganizationLocalService _organizationLocalService;

}