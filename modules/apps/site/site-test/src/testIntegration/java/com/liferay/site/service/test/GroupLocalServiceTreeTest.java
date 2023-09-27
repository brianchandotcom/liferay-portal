/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.TreeModel;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.local.service.tree.test.util.BaseLocalServiceTreeTestCase;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Shinn Lok
 */
@RunWith(Arquillian.class)
public class GroupLocalServiceTreeTest extends BaseLocalServiceTreeTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected TreeModel addTreeModel(TreeModel parentTreeModel)
		throws Exception {

		long parentGroupId = GroupConstants.DEFAULT_PARENT_GROUP_ID;

		if (parentTreeModel != null) {
			Group group = (Group)parentTreeModel;

			parentGroupId = group.getGroupId();
		}

		Group group = GroupTestUtil.addGroup(parentGroupId);

		group.setTreePath("/0/");

		return _groupLocalService.updateGroup(group);
	}

	@Override
	protected void deleteTreeModel(TreeModel treeModel) throws Exception {
		_groupLocalService.deleteGroup((Group)treeModel);
	}

	@Override
	protected TreeModel getTreeModel(long primaryKey) throws Exception {
		return _groupLocalService.getGroup(primaryKey);
	}

	@Override
	protected void rebuildTree() throws Exception {
		_groupLocalService.rebuildTree(TestPropsValues.getCompanyId());
	}

	@Inject
	private GroupLocalService _groupLocalService;

}