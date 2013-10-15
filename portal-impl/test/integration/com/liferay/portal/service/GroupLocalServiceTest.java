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
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.TestPropsValues;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.testng.Assert;

/**
 * @author Shuyang Zhou
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class GroupLocalServiceTest {

	@Before
	public void setUp() throws Exception {

		/**
		 * Tree 1
		 *
		 * /1--->/1--->/1
		 *  |     |--->/2
		 *  |     |--->/3
		 *  |     |--->/4
		 *  |
		 *  |--->/2--->/1
		 *  |     |--->/2
		 *  |     |--->/3
		 *  |
		 *  |--->/3--->/1
		 *        |--->/2
		 */
		Group p1Group = GroupTestUtil.addGroup(0, "p1");

		_groups.add(p1Group);

		Group p1P1Group = GroupTestUtil.addGroup(p1Group.getGroupId(), "p1p1");

		_groups.add(p1P1Group);

		Group p1P1P1Group = GroupTestUtil.addGroup(
			p1P1Group.getGroupId(), "p1p1p1");

		_groups.add(p1P1P1Group);

		Group p1P1P2Group = GroupTestUtil.addGroup(
			p1P1Group.getGroupId(), "p1p1p2");

		_groups.add(p1P1P2Group);

		Group p1P1P3Group = GroupTestUtil.addGroup(
			p1P1Group.getGroupId(), "p1p1p3");

		_groups.add(p1P1P3Group);

		Group p1P1P4Group = GroupTestUtil.addGroup(
			p1P1Group.getGroupId(), "p1p1p4");

		_groups.add(p1P1P4Group);

		Group p1P2Group = GroupTestUtil.addGroup(p1Group.getGroupId(), "p1p2");

		_groups.add(p1P2Group);

		Group p1P2P1Group = GroupTestUtil.addGroup(
			p1P2Group.getGroupId(), "p1p2p1");

		_groups.add(p1P2P1Group);

		Group p1P2P2Group = GroupTestUtil.addGroup(
			p1P2Group.getGroupId(), "p1p2p2");

		_groups.add(p1P2P2Group);

		Group p1P2P3Group = GroupTestUtil.addGroup(
			p1P2Group.getGroupId(), "p1p2p3");

		_groups.add(p1P2P3Group);

		Group p1P3Group = GroupTestUtil.addGroup(p1Group.getGroupId(), "p1p3");

		_groups.add(p1P3Group);

		Group p1P3P1Group = GroupTestUtil.addGroup(
			p1P3Group.getGroupId(), "p1p3p1");

		_groups.add(p1P3P1Group);

		Group p1P3P2Group = GroupTestUtil.addGroup(
			p1P3Group.getGroupId(), "p1p3p2");

		_groups.add(p1P3P2Group);

		/**
		 * Tree 2
		 *
		 * /2--->/1--->/1
		 *  |     |--->/2
		 *  |
		 *  |--->/2--->/1
		 *  |     |--->/2
		 *  |     |--->/3
		 *  |
		 *  |--->/3--->/1
		 *        |--->/2
		 *        |--->/3
		 *        |--->/4
		 */
		Group p2Group = GroupTestUtil.addGroup(0, "p2");

		_groups.add(p2Group);

		Group p2P1Group = GroupTestUtil.addGroup(p2Group.getGroupId(), "p2p1");

		_groups.add(p2P1Group);

		Group p2P1P1Group = GroupTestUtil.addGroup(
			p2P1Group.getGroupId(), "p2p1p1");

		_groups.add(p2P1P1Group);

		Group p2P1P2Group = GroupTestUtil.addGroup(
			p2P1Group.getGroupId(), "p2p1p2");

		_groups.add(p2P1P2Group);

		Group p2P2Group = GroupTestUtil.addGroup(p2Group.getGroupId(), "p2p2");

		_groups.add(p2P2Group);

		Group p2P2P1Group = GroupTestUtil.addGroup(
			p2P2Group.getGroupId(), "p2p2p1");

		_groups.add(p2P2P1Group);

		Group p2P2P2Group = GroupTestUtil.addGroup(
			p2P2Group.getGroupId(), "p2p2p2");

		_groups.add(p2P2P2Group);

		Group p2P2P3Group = GroupTestUtil.addGroup(
			p2P2Group.getGroupId(), "p2p2p3");

		_groups.add(p2P2P3Group);

		Group p2P3Group = GroupTestUtil.addGroup(p2Group.getGroupId(), "p2p3");

		_groups.add(p2P3Group);

		Group p2P3P1Group = GroupTestUtil.addGroup(
			p2P3Group.getGroupId(), "p2p3p1");

		_groups.add(p2P3P1Group);

		Group p2P3P2Group = GroupTestUtil.addGroup(
			p2P3Group.getGroupId(), "p2p3p2");

		_groups.add(p2P3P2Group);

		Group p2P3P3Group = GroupTestUtil.addGroup(
			p2P3Group.getGroupId(), "p2p3p3");

		_groups.add(p2P3P3Group);

		Group p2P3P4Group = GroupTestUtil.addGroup(
			p2P3Group.getGroupId(), "p2p3p4");

		_groups.add(p2P3P4Group);
	}

	@After
	public void tearDown() throws Exception {
		for (int i = _groups.size() - 1; i >= 0; i--) {
			GroupLocalServiceUtil.deleteGroup(_groups.get(i));
		}
	}

	@Test
	public void testRebuildTree() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "BULK_OPERATIONS_CHUNK_SIZE");

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

	private List<Group> _groups = new ArrayList<Group>();

}