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

package com.liferay.portal.service.test;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.TreeModel;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Shinn Lok
 * @deprecated As of Mueller (7.2.x), replaced by {@link
 *             com.liferay.portal.local.service.tree.test.util.BaseLocalServiceTreeTestCase}
 */
@Deprecated
public abstract class BaseLocalServiceTreeTestCase {

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();

		createTree();
	}

	@After
	public void tearDown() throws Exception {
		for (int i = _treeModels.size() - 1; i >= 0; i--) {
			deleteTreeModel(_treeModels.get(i));
		}
	}

	@Test
	public void testRebuildTree() throws Exception {
		rebuildTree();

		for (TreeModel treeModel : _treeModels) {
			long primaryKey = GetterUtil.getLong(treeModel.getPrimaryKeyObj());

			treeModel = getTreeModel(primaryKey);

			Assert.assertEquals(
				treeModel.buildTreePath(), treeModel.getTreePath());
		}
	}

	protected abstract TreeModel addTreeModel(TreeModel parentTreeModel)
		throws Exception;

	protected void createTree() throws Exception {

		// Tree 1

		// /A--->/A--->/A
		//  |     |--->/B
		//  |     |--->/C
		//  |     |--->/D
		//  |
		//  |--->/B--->/A
		//  |     |--->/B
		//  |     |--->/C
		//  |
		//  |--->/C--->/A
		//        |--->/B

		TreeModel treeModelA = addTreeModel(null);

		_treeModels.add(treeModelA);

		TreeModel treeModelAA = addTreeModel(treeModelA);

		_treeModels.add(treeModelAA);

		TreeModel treeModelAAA = addTreeModel(treeModelAA);

		_treeModels.add(treeModelAAA);

		TreeModel treeModelAAB = addTreeModel(treeModelAA);

		_treeModels.add(treeModelAAB);

		TreeModel treeModelAAC = addTreeModel(treeModelAA);

		_treeModels.add(treeModelAAC);

		TreeModel treeModelAAD = addTreeModel(treeModelAA);

		_treeModels.add(treeModelAAD);

		TreeModel treeModelAB = addTreeModel(treeModelA);

		_treeModels.add(treeModelAB);

		TreeModel treeModelABA = addTreeModel(treeModelAB);

		_treeModels.add(treeModelABA);

		TreeModel treeModelABB = addTreeModel(treeModelAB);

		_treeModels.add(treeModelABB);

		TreeModel treeModelABC = addTreeModel(treeModelAB);

		_treeModels.add(treeModelABC);

		TreeModel treeModelAC = addTreeModel(treeModelA);

		_treeModels.add(treeModelAC);

		TreeModel treeModelACA = addTreeModel(treeModelAC);

		_treeModels.add(treeModelACA);

		TreeModel treeModelACB = addTreeModel(treeModelAC);

		_treeModels.add(treeModelACB);

		// Tree 2

		// /B--->/A--->/A
		//  |     |--->/B
		//  |
		//  |--->/B--->/A
		//  |     |--->/B
		//  |     |--->/C
		//  |
		//  |--->/C--->/A
		//        |--->/B
		//        |--->/C
		//        |--->/D

		TreeModel treeModelB = addTreeModel(null);

		_treeModels.add(treeModelB);

		TreeModel treeModelBA = addTreeModel(treeModelB);

		_treeModels.add(treeModelBA);

		TreeModel treeModelBAA = addTreeModel(treeModelBA);

		_treeModels.add(treeModelBAA);

		TreeModel treeModelBAB = addTreeModel(treeModelBA);

		_treeModels.add(treeModelBAB);

		TreeModel treeModelBB = addTreeModel(treeModelB);

		_treeModels.add(treeModelBB);

		TreeModel treeModelBBA = addTreeModel(treeModelBB);

		_treeModels.add(treeModelBBA);

		TreeModel treeModelBBB = addTreeModel(treeModelBB);

		_treeModels.add(treeModelBBB);

		TreeModel treeModelBBC = addTreeModel(treeModelBB);

		_treeModels.add(treeModelBBC);

		TreeModel treeModelBC = addTreeModel(treeModelB);

		_treeModels.add(treeModelBC);

		TreeModel treeModelBCA = addTreeModel(treeModelBC);

		_treeModels.add(treeModelBCA);

		TreeModel treeModelBCB = addTreeModel(treeModelBC);

		_treeModels.add(treeModelBCB);

		TreeModel treeModelBCC = addTreeModel(treeModelBC);

		_treeModels.add(treeModelBCC);

		TreeModel treeModelBCD = addTreeModel(treeModelBC);

		_treeModels.add(treeModelBCD);
	}

	protected abstract void deleteTreeModel(TreeModel treeModel)
		throws Exception;

	protected abstract TreeModel getTreeModel(long primaryKey) throws Exception;

	protected abstract void rebuildTree() throws Exception;

	@DeleteAfterTestRun
	protected Group group;

	private final List<TreeModel> _treeModels = new ArrayList<>();

}