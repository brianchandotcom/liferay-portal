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

package com.liferay.organizations.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.TreeModel;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.local.service.tree.test.util.BaseLocalServiceTreeTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Shinn Lok
 */
@RunWith(Arquillian.class)
public class OrganizationLocalServiceTreeTest
	extends BaseLocalServiceTreeTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected TreeModel addTreeModel(TreeModel parentTreeModel)
		throws Exception {

		long parentOrganizationId =
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID;

		if (parentTreeModel != null) {
			Organization organization = (Organization)parentTreeModel;

			parentOrganizationId = organization.getOrganizationId();
		}

		Organization organization = OrganizationTestUtil.addOrganization(
			parentOrganizationId, RandomTestUtil.randomString(), false);

		organization.setTreePath(null);

		return OrganizationLocalServiceUtil.updateOrganization(organization);
	}

	@Override
	protected void deleteTreeModel(TreeModel treeModel) throws Exception {
		Organization organization = (Organization)treeModel;

		OrganizationLocalServiceUtil.deleteOrganization(organization);
	}

	@Override
	protected TreeModel getTreeModel(long primaryKey) throws Exception {
		return OrganizationLocalServiceUtil.getOrganization(primaryKey);
	}

	@Override
	protected void rebuildTree() throws Exception {
		OrganizationLocalServiceUtil.rebuildTree(
			TestPropsValues.getCompanyId());
	}

}