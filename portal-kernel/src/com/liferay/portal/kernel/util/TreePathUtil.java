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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.TreeModel;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Shinn Lok
 */
public class TreePathUtil {

	public static void rebuildTree(
			long companyId, long parentPrimaryKey, String parentTreePath,
			TreeModelTasks<?> treeModelTasks)
		throws PortalException {

		List<TreeModel> modifiedTreeModels = new ArrayList<>();

		Deque<Object[]> traces = new LinkedList<>();

		traces.push(new Object[] {parentPrimaryKey, parentTreePath, 0L});

		Object[] trace = null;

		while ((trace = traces.poll()) != null) {
			Long curParentPrimaryKey = (Long)trace[0];
			String curParentTreePath = (String)trace[1];
			Long previousPrimaryKey = (Long)trace[2];

			treeModelTasks.rebuildDependentModelsTreePaths(
				curParentPrimaryKey, curParentTreePath);

			List<? extends TreeModel> treeModels =
				treeModelTasks.findTreeModels(
					previousPrimaryKey, companyId, curParentPrimaryKey,
					_MODEL_TREE_REBUILD_QUERY_RESULTS_BATCH_SIZE);

			if (treeModels.isEmpty()) {
				continue;
			}

			if (treeModels.size() ==
					_MODEL_TREE_REBUILD_QUERY_RESULTS_BATCH_SIZE) {

				TreeModel treeModel = treeModels.get(treeModels.size() - 1);

				trace[2] = treeModel.getPrimaryKeyObj();

				traces.push(trace);
			}

			for (TreeModel treeModel : treeModels) {
				String treePath = curParentTreePath.concat(
					String.valueOf(treeModel.getPrimaryKeyObj())).concat(
						StringPool.SLASH);

				treeModel.updateTreePath(treePath);

				traces.push(
					new Object[] {treeModel.getPrimaryKeyObj(), treePath, 0L});

				modifiedTreeModels.add(treeModel);
			}
		}

		treeModelTasks.reindexTreeModels(modifiedTreeModels);
	}

	private static final int _MODEL_TREE_REBUILD_QUERY_RESULTS_BATCH_SIZE =
		GetterUtil.getInteger(
			PropsUtil.get(
				PropsKeys.MODEL_TREE_REBUILD_QUERY_RESULTS_BATCH_SIZE));

}