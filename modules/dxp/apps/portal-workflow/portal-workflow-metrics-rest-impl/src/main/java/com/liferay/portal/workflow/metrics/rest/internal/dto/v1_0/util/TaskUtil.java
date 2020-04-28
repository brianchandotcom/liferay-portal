/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.rest.internal.dto.v1_0.util;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Task;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Function;

/**
 * @author Rafael Praxedes
 */
public class TaskUtil {

	public static Task toTask(
		Document document, Language language, Locale locale, Portal portal,
		ResourceBundle resourceBundle, Function<Long, User> userFunction) {

		return new Task() {
			{
				assetTitle = document.getString(
					_getLocalizedName(locale, "assetTitle"));
				assetType = document.getString(
					_getLocalizedName(locale, "assetType"));
				className = document.getString("className");
				classPK = document.getLong("classPK");
				completed = document.getBoolean("completed");
				completionUserId = document.getLong("completionUserId");
				dateCompletion = _parseDate(document.getDate("completionDate"));
				dateCreated = _parseDate(document.getDate("createDate"));
				dateModified = _parseDate(document.getDate("modifiedDate"));
				duration = document.getLong("duration");
				id = document.getLong("taskId");
				instanceId = document.getLong("instanceId");
				label = language.get(
					resourceBundle, document.getString("name"));
				name = document.getString("name");
				nodeId = document.getLong("nodeId");
				processId = document.getLong("processId");
				processVersion = document.getString("version");

				setAssignee(
					() -> {
						String assigneeType = document.getString(
							"assigneeType");

						if (Objects.deepEquals(
								assigneeType, User.class.getName())) {

							return AssigneeUtil.toAssignee(
								language, portal, resourceBundle,
								document.getLong("assigneeIds"), userFunction);
						}

						return null;
					});
			}
		};
	}

	public static Task toTask(
		Language language, Locale locale, Portal portal,
		ResourceBundle resourceBundle, Map<String, Object> sourcesMap,
		Function<Long, User> userFunction) {

		return new Task() {
			{
				assetTitle = GetterUtil.getString(
					sourcesMap.get(_getLocalizedName(locale, "assetTitle")));
				assetType = GetterUtil.getString(
					sourcesMap.get(_getLocalizedName(locale, "assetType")));
				className = GetterUtil.getString(sourcesMap.get("className"));
				classPK = GetterUtil.getLong(sourcesMap.get("classPK"));
				completed = GetterUtil.getBoolean(sourcesMap.get("completed"));
				completionUserId = GetterUtil.getLong(
					sourcesMap.get("completionUserId"));
				dateCompletion = _parseDate(
					GetterUtil.getString(sourcesMap.get("completionDate")));
				dateCreated = _parseDate(
					GetterUtil.getString(sourcesMap.get("createDate")));
				dateModified = _parseDate(
					GetterUtil.getString(sourcesMap.get("modifiedDate")));
				duration = GetterUtil.getLong(sourcesMap.get("duration"));
				id = GetterUtil.getLong(sourcesMap.get("taskId"));
				instanceId = GetterUtil.getLong(sourcesMap.get("instanceId"));
				label = language.get(
					resourceBundle,
					GetterUtil.getString(sourcesMap.get("name")));
				name = GetterUtil.getString(sourcesMap.get("name"));
				nodeId = GetterUtil.getLong(sourcesMap.get("nodeId"));
				processId = GetterUtil.getLong(sourcesMap.get("processId"));
				processVersion = GetterUtil.getString(
					sourcesMap.get("version"));

				setAssignee(
					() -> {
						String assigneeType = GetterUtil.getString(
							sourcesMap.get("assigneeType"));

						if (Objects.deepEquals(
								assigneeType, User.class.getName())) {

							return AssigneeUtil.toAssignee(
								language, portal, resourceBundle,
								GetterUtil.getLong(
									sourcesMap.get("assigneeIds")),
								userFunction);
						}

						return null;
					});
			}
		};
	}

	public static Task toTask(
		Language language, String taskName, ResourceBundle resourceBundle) {

		return new Task() {
			{
				label = language.get(resourceBundle, taskName);
				name = taskName;
			}
		};
	}

	private static String _getLocalizedName(Locale locale, String name) {
		return Field.getLocalizedName(locale, name);
	}

	private static Date _parseDate(String dateString) {
		try {
			return DateUtil.parseDate(
				"yyyyMMddHHmmss", dateString, LocaleUtil.getDefault());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(TaskUtil.class);

}