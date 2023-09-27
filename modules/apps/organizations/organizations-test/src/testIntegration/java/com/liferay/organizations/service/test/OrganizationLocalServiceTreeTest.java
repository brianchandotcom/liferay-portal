/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		organization.setTreePath("/0/");

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