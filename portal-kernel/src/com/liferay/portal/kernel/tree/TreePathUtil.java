/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.tree;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.TreeModel;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * @author Shinn Lok
 */
public class TreePathUtil {

	public static void rebuildTree(
			long companyId, long parentPrimaryKey, String parentTreePath,
			TreeModelTasks<?> treeModelTasks)
		throws PortalException {

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
				String treePath = StringBundler.concat(
					curParentTreePath, treeModel.getPrimaryKeyObj(),
					StringPool.SLASH);

				if (!treePath.equals(treeModel.getTreePath())) {
					treeModel.updateTreePath(treePath);
				}

				traces.push(
					new Object[] {treeModel.getPrimaryKeyObj(), treePath, 0L});
			}
		}
	}

	private static final int _MODEL_TREE_REBUILD_QUERY_RESULTS_BATCH_SIZE =
		GetterUtil.getInteger(
			PropsUtil.get(
				PropsKeys.MODEL_TREE_REBUILD_QUERY_RESULTS_BATCH_SIZE));

	private static class RecursiveRebuildTreeTask extends RecursiveAction {

		@Override
		protected void compute() {
			try {
				_treeModelTasks.rebuildDependentModelsTreePaths(
					_parentPrimaryKey, _parentTreePath);
			}
			catch (PortalException portalException) {
				ReflectionUtil.throwException(portalException);
			}

			List<? extends TreeModel> treeModels =
				_treeModelTasks.findTreeModels(
					_previousPrimaryKey, _companyId, _parentPrimaryKey,
					_MODEL_TREE_REBUILD_QUERY_RESULTS_BATCH_SIZE);

			if (treeModels.isEmpty()) {
				return;
			}

			if (treeModels.size() ==
					_MODEL_TREE_REBUILD_QUERY_RESULTS_BATCH_SIZE) {

				TreeModel treeModel = treeModels.get(treeModels.size() - 1);

				RecursiveRebuildTreeTask recursiveRebuildTreeTask =
					new RecursiveRebuildTreeTask(
						_treeModelTasks, _companyId, _parentPrimaryKey,
						_parentTreePath, (long)treeModel.getPrimaryKeyObj());

				recursiveRebuildTreeTask.fork();
			}

			for (TreeModel treeModel : treeModels) {
				String treePath = StringBundler.concat(
					_parentTreePath, treeModel.getPrimaryKeyObj(),
					StringPool.SLASH);

				if (!treePath.equals(treeModel.getTreePath())) {
					treeModel.updateTreePath(treePath);
				}

				RecursiveRebuildTreeTask recursiveRebuildTreeTask =
					new RecursiveRebuildTreeTask(
						_treeModelTasks, _companyId,
						(long)treeModel.getPrimaryKeyObj(), treePath, 0L);

				recursiveRebuildTreeTask.fork();
			}
		}

		private RecursiveRebuildTreeTask(
			TreeModelTasks<?> treeModelTasks, long companyId,
			long parentPrimaryKey, String parentTreePath,
			long previousPrimaryKey) {

			_treeModelTasks = treeModelTasks;
			_companyId = companyId;
			_parentPrimaryKey = parentPrimaryKey;
			_parentTreePath = parentTreePath;
			_previousPrimaryKey = previousPrimaryKey;
		}

		private final long _companyId;
		private final long _parentPrimaryKey;
		private final String _parentTreePath;
		private final long _previousPrimaryKey;
		private final TreeModelTasks<?> _treeModelTasks;

	}

}