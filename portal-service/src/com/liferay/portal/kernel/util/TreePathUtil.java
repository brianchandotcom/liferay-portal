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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.TreeModel;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Shinn Lok
 */
public class TreePathUtil {

	public static void rebuildTree(
			long companyId, long defaultParentId,
			TreeModelFinder<?> treeModelFinder)
		throws SystemException {

		int size = GetterUtil.getInteger(
			PropsUtil.get(
				PropsKeys.MODEL_TREE_REBUILD_QUERY_RESULTS_BATCH_SIZE));

		Deque<Object[]> traces = new LinkedList<Object[]>();

		traces.push(new Object[] {defaultParentId, StringPool.SLASH, 0L});

		Object[] trace = null;

		while ((trace = traces.poll()) != null) {
			Long parentId = (Long)trace[0];
			String parentPath = (String)trace[1];
			Long previousId = (Long)trace[2];

			List<? extends TreeModel> treeModels =
				treeModelFinder.findTreeModels(
					previousId, companyId, parentId, size);

			if (treeModels.isEmpty()) {
				continue;
			}

			if (treeModels.size() == size) {
				trace[2] = treeModels.get(treeModels.size() - 1);

				traces.push(trace);
			}

			for (TreeModel treeModel : treeModels) {
				String treePath = parentPath.concat(
					String.valueOf(treeModel.getPrimaryKeyObj())).concat(
						StringPool.SLASH);

				treeModel.updateTreePath(treePath);

				traces.push(
					new Object[] {treeModel.getPrimaryKeyObj(), treePath, 0L});
			}
		}
	}

	public static void rebuildTree(
		Session session, long companyId, String modelName,
		String parentModelName, boolean statusColumn) {

		rebuildTree(
			session, companyId, modelName, parentModelName, statusColumn,
			false);

		rebuildTree(
			session, companyId, modelName, parentModelName, statusColumn, true);
	}

	protected static void rebuildTree(
		Session session, long companyId, String tableName,
		String parentTableName, boolean statusColumn, boolean rootFolder) {

		StringBundler sb = new StringBundler(18);

		sb.append("update ");
		sb.append(tableName);
		sb.append(" set ");

		if (rootFolder) {
			sb.append("treePath = \"/0/\" ");
		}
		else {
			sb.append("treePath = (select ");
			sb.append(parentTableName);
			sb.append(".treePath from ");
			sb.append(parentTableName);
			sb.append(" where ");
			sb.append(parentTableName);
			sb.append(".folderId = ");
			sb.append(tableName);
			sb.append(".folderId)");
		}

		sb.append("where (");

		if (rootFolder) {
			sb.append(tableName);
			sb.append(".folderId = 0) AND (");
		}

		sb.append(tableName);
		sb.append(".companyId = ?)");

		if (statusColumn) {
			sb.append(" and (");
			sb.append(tableName);
			sb.append(".status != ?)");
		}

		SQLQuery sqlQuery = session.createSQLQuery(sb.toString());

		QueryPos qPos = QueryPos.getInstance(sqlQuery);

		qPos.add(companyId);

		if (statusColumn) {
			qPos.add(WorkflowConstants.STATUS_IN_TRASH);
		}

		sqlQuery.executeUpdate();
	}

}