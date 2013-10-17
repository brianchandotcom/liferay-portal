/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.TestPropsValues;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.testng.Assert;

/**
 * @author Shuyang Zhou
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class GroupLocalServiceTest {

	@After
	public void tearDown() throws Exception {
		for (int i = _groups.size() - 1; i >= 0; i--) {
			GroupLocalServiceUtil.deleteGroup(_groups.get(i));
		}
	}

	@Test
	public void testRebuildTree() throws Exception {
		createGroupTree();

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MODEL_TREE_REBUILD_QUERY_RESULTS_BATCH_SIZE");

		int oldSize = field.getInt(null);

		field.setInt(null, 3);

		try {
			GroupLocalServiceUtil.rebuildTree(TestPropsValues.getCompanyId());

			for (Group group : _groups) {
				Assert.assertEquals(group.buildTreePath(), group.getTreePath());
			}
		}
		finally {
			field.setInt(null, oldSize);
		}
	}

	protected void createGroupTree() throws Exception {

		/**
		 * Tree 1
		 *
		 * /A--->/A--->/A
		 *  |     |--->/B
		 *  |     |--->/C
		 *  |     |--->/D
		 *  |
		 *  |--->/B--->/A
		 *  |     |--->/B
		 *  |     |--->/C
		 *  |
		 *  |--->/C--->/A
		 *        |--->/B
		 */

		Group groupA = GroupTestUtil.addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, "Group A");

		_groups.add(groupA);

		Group groupAA = GroupTestUtil.addGroup(groupA.getGroupId(), "Group AA");

		_groups.add(groupAA);

		Group groupAAA = GroupTestUtil.addGroup(
			groupAA.getGroupId(), "Group AAA");

		_groups.add(groupAAA);

		Group groupAAB = GroupTestUtil.addGroup(
			groupAA.getGroupId(), "Group AAB");

		_groups.add(groupAAB);

		Group groupAAC = GroupTestUtil.addGroup(
				groupAA.getGroupId(), "Group AAC");

		_groups.add(groupAAC);

		Group groupAAD = GroupTestUtil.addGroup(
				groupAA.getGroupId(), "Group AAD");

		_groups.add(groupAAD);

		Group groupAB = GroupTestUtil.addGroup(groupA.getGroupId(), "Group AB");

		_groups.add(groupAB);

		Group groupABA = GroupTestUtil.addGroup(
			groupAB.getGroupId(), "Group ABA");

		_groups.add(groupABA);

		Group groupABB = GroupTestUtil.addGroup(
			groupAB.getGroupId(), "Group ABB");

		_groups.add(groupABB);

		Group groupABC = GroupTestUtil.addGroup(
			groupAB.getGroupId(), "Group ABC");

		_groups.add(groupABC);

		Group groupAC = GroupTestUtil.addGroup(groupA.getGroupId(), "Group AC");

		_groups.add(groupAC);

		Group groupACA = GroupTestUtil.addGroup(
			groupAC.getGroupId(), "Group ACA");

		_groups.add(groupACA);

		Group groupACB = GroupTestUtil.addGroup(
			groupAC.getGroupId(), "Group ACB");

		_groups.add(groupACB);

		/**
		 * Tree 2
		 *
		 * /B--->/A--->/A
		 *  |     |--->/B
		 *  |
		 *  |--->/B--->/A
		 *  |     |--->/B
		 *  |     |--->/C
		 *  |
		 *  |--->/C--->/A
		 *        |--->/B
		 *        |--->/C
		 *        |--->/D
		 */

		Group groupB = GroupTestUtil.addGroup(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, "Group B");

		_groups.add(groupB);

		Group groupBA = GroupTestUtil.addGroup(groupB.getGroupId(), "Group BA");

		_groups.add(groupBA);

		Group groupBAA = GroupTestUtil.addGroup(
			groupBA.getGroupId(), "Group BAA");

		_groups.add(groupBAA);

		Group groupBAB = GroupTestUtil.addGroup(
			groupBA.getGroupId(), "Group BAB");

		_groups.add(groupBAB);

		Group groupBB = GroupTestUtil.addGroup(groupB.getGroupId(), "Group BB");

		_groups.add(groupBB);

		Group groupBBA = GroupTestUtil.addGroup(
			groupBB.getGroupId(), "Group BBA");

		_groups.add(groupBBA);

		Group groupBBB = GroupTestUtil.addGroup(
			groupBB.getGroupId(), "Group BBB");

		_groups.add(groupBBB);

		Group groupBBC = GroupTestUtil.addGroup(
			groupBB.getGroupId(), "Group BBC");

		_groups.add(groupBBC);

		Group groupBC = GroupTestUtil.addGroup(groupB.getGroupId(), "Group BC");

		_groups.add(groupBC);

		Group groupBCA = GroupTestUtil.addGroup(
			groupBC.getGroupId(), "Group BCA");

		_groups.add(groupBCA);

		Group groupBCB = GroupTestUtil.addGroup(
			groupBC.getGroupId(), "Group BCB");

		_groups.add(groupBCB);

		Group groupBCC = GroupTestUtil.addGroup(
			groupBC.getGroupId(), "Group BCC");

		_groups.add(groupBCC);

		Group groupBCD = GroupTestUtil.addGroup(
			groupBC.getGroupId(), "Group BCD");

		_groups.add(groupBCD);
	}

	private List<Group> _groups = new ArrayList<Group>();

}