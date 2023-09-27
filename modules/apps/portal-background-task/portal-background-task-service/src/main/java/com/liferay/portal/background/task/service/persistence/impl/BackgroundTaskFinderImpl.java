/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.background.task.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.model.impl.BackgroundTaskImpl;
import com.liferay.portal.background.task.service.persistence.BackgroundTaskFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltan Csaszi
 */
@Component(service = BackgroundTaskFinder.class)
public class BackgroundTaskFinderImpl
	extends BackgroundTaskFinderBaseImpl implements BackgroundTaskFinder {

	public static final String FIND_BY_G_T_C =
		BackgroundTaskFinder.class.getName() + ".findByG_T_C";

	@Override
	public List<BackgroundTask> findByG_T_C(
		long[] groupIds, String[] taskExecutorClassNames, Boolean completed,
		int start, int end, boolean orderByType) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_G_T_C);

			sql = _replaceWhereConditions(
				groupIds, taskExecutorClassNames, sql, completed);

			sql = StringUtil.replace(
				sql, "[$ORDER_BY_TYPE$]", orderByType ? "ASC" : "DESC");

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("BackgroundTask", BackgroundTaskImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			for (long groupId : groupIds) {
				queryPos.add(groupId);
			}

			for (String taskExecutorClassName : taskExecutorClassNames) {
				queryPos.add(taskExecutorClassName);
			}

			if (completed != null) {
				queryPos.add(completed.booleanValue());
			}

			return (List<BackgroundTask>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private String _getGroupCriteria(long[] groupIds) {
		String result = StringPool.BLANK;

		if (groupIds.length > 0) {
			StringBundler sb = new StringBundler(groupIds.length + 2);

			sb.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < groupIds.length; i++) {
				sb.append("(BackgroundTask.groupId = ?) OR ");
			}

			sb.append(StringPool.CLOSE_PARENTHESIS);

			result = StringUtil.replaceLast(
				sb.toString(), " OR ", StringPool.BLANK);
		}

		return result;
	}

	private String _getTaskExecutorClassNameCriteria(String[] classNames) {
		String result = StringPool.BLANK;

		if (classNames.length > 0) {
			StringBundler sb = new StringBundler(classNames.length + 2);

			sb.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < classNames.length; i++) {
				sb.append("(BackgroundTask.taskExecutorClassName = ?) OR ");
			}

			sb.append(StringPool.CLOSE_PARENTHESIS);

			result = StringUtil.replaceLast(
				sb.toString(), " OR ", StringPool.BLANK);
		}

		return result;
	}

	private String _replaceWhereConditions(
		long[] groupIds, String[] taskExecutorClassNames, String sql,
		Boolean completed) {

		StringBundler sb = new StringBundler(5);

		String groupCriteria = GetterUtil.getString(
			_getGroupCriteria(groupIds));
		String taskExecutorCriteria = GetterUtil.getString(
			_getTaskExecutorClassNameCriteria(taskExecutorClassNames));

		sb.append(groupCriteria);

		if (!groupCriteria.isEmpty() && !taskExecutorCriteria.isEmpty()) {
			sb.append(" AND ");
		}

		sb.append(taskExecutorCriteria);

		if (completed != null) {
			if (Validator.isNotNull(groupCriteria) &&
				Validator.isNotNull(taskExecutorCriteria)) {

				sb.append(" AND ");
			}

			sb.append("(BackgroundTask.completed = ?)");
		}

		if (Validator.isNotNull(sb.toString())) {
			sql = StringUtil.replace(
				sql, "[$WHERE_CONDITIONS$]", "WHERE " + sb.toString());
		}
		else {
			sql = StringUtil.removeSubstring(sql, "[$WHERE_CONDITIONS$]");
		}

		return sql;
	}

	@Reference
	private CustomSQL _customSQL;

}