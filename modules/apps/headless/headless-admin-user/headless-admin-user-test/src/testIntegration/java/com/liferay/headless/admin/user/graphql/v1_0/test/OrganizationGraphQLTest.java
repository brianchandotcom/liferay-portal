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

package com.liferay.headless.admin.user.graphql.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.Organization;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.service.ListTypeLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class OrganizationGraphQLTest extends BaseOrganizationGraphQLTestCase {

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"comment", "name"};
	}

	@Override
	protected Organization testOrganization_addOrganization() throws Exception {
		Organization organization = randomOrganization();

		List<ListType> listTypes = ListTypeLocalServiceUtil.getListTypes(
			ListTypeConstants.ORGANIZATION_STATUS);

		ListType listType = listTypes.get(0);

		com.liferay.portal.kernel.model.Organization
			serviceBuilderOrganization =
				OrganizationLocalServiceUtil.addOrganization(
					UserLocalServiceUtil.getDefaultUserId(
						testGroup.getCompanyId()),
					0, organization.getName(),
					OrganizationConstants.TYPE_ORGANIZATION, 0, 0,
					listType.getListTypeId(), organization.getComment(), false,
					new ServiceContext());

		_organizations.add(serviceBuilderOrganization);

		return _toOrganization(serviceBuilderOrganization);
	}

	private Organization _toOrganization(
		com.liferay.portal.kernel.model.Organization organization) {

		return new Organization() {
			{
				comment = organization.getComments();
				id = organization.getOrganizationId();
				dateCreated = organization.getCreateDate();
				dateModified = organization.getModifiedDate();
				name = organization.getName();
			}
		};
	}

	@DeleteAfterTestRun
	private final List<com.liferay.portal.kernel.model.Organization>
		_organizations = new ArrayList<>();

}