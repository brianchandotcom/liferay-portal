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

package com.liferay.headless.admin.workflow.internal.dto.v1_0.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowLog;

import java.util.Locale;
import java.util.function.Function;

/**
 * @author Rafael Praxedes
 */
public class WorkflowLogUtil {

	public static String getDescription(
			Language language, Locale locale, Portal portal,
			Function<Long, Role> roleFunction,
			Function<Long, User> userFunction, WorkflowLog workflowLog)
		throws PortalException {

		if (workflowLog.getType() == WorkflowLog.TASK_COMPLETION) {
			return language.format(
				locale, "x-completed-the-task-x",
				new Object[] {
					portal.getUserName(
						workflowLog.getAuditUserId(),
						String.valueOf(workflowLog.getAuditUserId())),
					language.get(locale, workflowLog.getState())
				},
				false);
		}
		else if (workflowLog.getType() == WorkflowLog.TASK_UPDATE) {
			return language.format(
				locale, "x-updated-the-due-date",
				new Object[] {
					portal.getUserName(
						workflowLog.getAuditUserId(),
						String.valueOf(workflowLog.getAuditUserId()))
				},
				false);
		}
		else if (workflowLog.getType() == WorkflowLog.TRANSITION) {
			return language.format(
				locale, "x-changed-the-state-from-x-to-x",
				new Object[] {
					portal.getUserName(
						workflowLog.getAuditUserId(),
						String.valueOf(workflowLog.getAuditUserId())),
					language.get(locale, workflowLog.getPreviousState()),
					language.get(locale, workflowLog.getState())
				},
				false);
		}
		else if (_isAuditUser(workflowLog)) {
			String pattern = "x-assigned-the-task-to-herself";

			User user = userFunction.apply(workflowLog.getUserId());

			if ((user == null) || user.isMale()) {
				pattern = "x-assigned-the-task-to-himself";
			}

			String userFullName;

			if (user == null) {
				userFullName = String.valueOf(workflowLog.getUserId());
			}
			else {
				userFullName = user.getFullName();
			}

			return language.format(locale, pattern, userFullName, false);
		}
		else if (workflowLog.getRoleId() == 0) {
			StringBundler sb = new StringBundler(3);

			sb.append(
				language.format(
					locale, "x-assigned-the-task-to-x",
					new Object[] {
						portal.getUserName(
							workflowLog.getAuditUserId(),
							String.valueOf(workflowLog.getAuditUserId())),
						_getActorName(
							language, locale, roleFunction, userFunction,
							workflowLog)
					},
					false));

			if (workflowLog.getPreviousUserId() != 0) {
				sb.append(StringPool.SPACE);
				sb.append(
					language.format(
						locale, "previous-assignee-was-x",
						portal.getUserName(
							workflowLog.getPreviousUserId(),
							String.valueOf(workflowLog.getPreviousUserId())),
						false));
			}

			return sb.toString();
		}

		return language.format(
			locale, "task-initially-assigned-to-the-x-role",
			_getActorName(
				language, locale, roleFunction, userFunction, workflowLog),
			false);
	}

	private static String _getActorName(
		Language language, Locale locale, Function<Long, Role> roleFunction,
		Function<Long, User> userFunction, WorkflowLog workflowLog) {

		if (workflowLog.getRoleId() != 0) {
			Role role = roleFunction.apply(workflowLog.getRoleId());

			if (role == null) {
				return String.valueOf(workflowLog.getRoleId());
			}

			return role.getTitle(language.getLanguageId(locale));
		}
		else if (workflowLog.getUserId() != 0) {
			User user = userFunction.apply(workflowLog.getUserId());

			if (user == null) {
				return String.valueOf(workflowLog.getUserId());
			}

			return user.getFullName();
		}

		return StringPool.BLANK;
	}

	private static boolean _isAuditUser(WorkflowLog workflowLog) {
		if (workflowLog.getUserId() == 0) {
			return false;
		}

		if (workflowLog.getAuditUserId() == workflowLog.getUserId()) {
			return true;
		}

		return false;
	}

}