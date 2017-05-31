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

package com.liferay.site.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.TreeModel;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.service.test.BaseLocalServiceTreeTestCase;
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

		group.setTreePath(null);

		return GroupLocalServiceUtil.updateGroup(group);
	}

	@Override
	protected void deleteTreeModel(TreeModel treeModel) throws Exception {
		Group group = (Group)treeModel;

		GroupLocalServiceUtil.deleteGroup(group);
	}

	@Override
	protected TreeModel getTreeModel(long primaryKey) throws Exception {
		return GroupLocalServiceUtil.getGroup(primaryKey);
	}

	@Override
	protected void rebuildTree() throws Exception {
		GroupLocalServiceUtil.rebuildTree(TestPropsValues.getCompanyId());
	}

}