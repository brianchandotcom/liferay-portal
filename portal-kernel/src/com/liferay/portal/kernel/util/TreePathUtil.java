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

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author     Shinn Lok
 * @deprecated As of 7.0.0, moved to {@link
 *             com.liferay.portal.kernel.tree.TreePathUtil}
 */
@Deprecated
public class TreePathUtil {

	public static void rebuildTree(
			long companyId, long parentPrimaryKey, String parentTreePath,
			TreeModelTasks<?> treeModelTasks)
		throws PortalException {

		if (VerifyThreadLocal.isVerifyInProgress() &&
			_VERIFY_DATABASE_TRANSACTIONS_DISABLED) {

			ForkJoinPool forkJoinPool = new ForkJoinPool();

			try {
				forkJoinPool.invoke(
					new RecursiveRebuildTreeTask(
						treeModelTasks, companyId, parentPrimaryKey,
						parentTreePath, 0L));
			}
			finally {
				forkJoinPool.shutdown();
			}

			return;
		}

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

	private static final boolean _VERIFY_DATABASE_TRANSACTIONS_DISABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.VERIFY_DATABASE_TRANSACTIONS_DISABLED));

	private static class RecursiveRebuildTreeTask extends RecursiveAction {

		@Override
		protected void compute() {
			try {
				_treeModelTasks.rebuildDependentModelsTreePaths(
					_parentPrimaryKey, _parentTreePath);
			}
			catch (PortalException pe) {
				ReflectionUtil.throwException(pe);
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
				String treePath = _parentTreePath.concat(
					String.valueOf(treeModel.getPrimaryKeyObj())).concat(
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