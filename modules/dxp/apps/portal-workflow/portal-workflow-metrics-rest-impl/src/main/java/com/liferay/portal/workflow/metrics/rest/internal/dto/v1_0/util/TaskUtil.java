/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.internal.dto.v1_0.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.Field;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Task;

import java.util.Date;
import java.util.HashMap;
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

		Map<String, String> assetTitleMap = _createMap(document, "assetTitle");
		Map<String, String> assetTypeMap = _createMap(document, "assetType");

		return new Task() {
			{
				assetTitle_i18n = assetTitleMap;
				assetType_i18n = assetTypeMap;
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

				setAssetTitle(
					() -> {
						String assetTitle = assetTitleMap.get(
							locale.toLanguageTag());

						if (Validator.isNull(assetTitle)) {
							Locale defaultLocale =
								LocaleThreadLocal.getDefaultLocale();

							assetTitle = assetTitleMap.get(
								defaultLocale.toLanguageTag());
						}

						return assetTitle;
					});
				setAssetType(
					() -> {
						String assetType = assetTypeMap.get(
							locale.toLanguageTag());

						if (Validator.isNull(assetType)) {
							Locale defaultLocale =
								LocaleThreadLocal.getDefaultLocale();

							assetType = assetTypeMap.get(
								defaultLocale.toLanguageTag());
						}

						return assetType;
					});
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

		Map<String, String> assetTitleMap = _createMap(
			"assetTitle", sourcesMap);
		Map<String, String> assetTypeMap = _createMap("assetType", sourcesMap);

		return new Task() {
			{
				assetTitle_i18n = assetTitleMap;
				assetType_i18n = assetTypeMap;
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

				setAssetTitle(
					() -> {
						String assetTitle = assetTitleMap.get(
							locale.toLanguageTag());

						if (Validator.isNull(assetTitle)) {
							Locale defaultLocale =
								LocaleThreadLocal.getDefaultLocale();

							assetTitle = assetTitleMap.get(
								defaultLocale.toLanguageTag());
						}

						return assetTitle;
					});
				setAssetType(
					() -> {
						String assetType = assetTypeMap.get(
							locale.toLanguageTag());

						if (Validator.isNull(assetType)) {
							Locale defaultLocale =
								LocaleThreadLocal.getDefaultLocale();

							assetType = assetTypeMap.get(
								defaultLocale.toLanguageTag());
						}

						return assetType;
					});
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

	private static Map<String, String> _createMap(
		Document document, String fieldName) {

		Map<String, String> map = new HashMap<>();

		Map<String, Field> fields = document.getFields();

		for (Map.Entry<String, Field> entry : fields.entrySet()) {
			String key = entry.getKey();

			if (StringUtil.startsWith(key, fieldName + StringPool.UNDERLINE) &&
				!StringUtil.endsWith(key, "_sortable")) {

				Field field = entry.getValue();

				map.put(
					StringUtil.removeSubstring(
						key, fieldName + StringPool.UNDERLINE),
					String.valueOf(field.getValue()));
			}
		}

		return map;
	}

	private static Map<String, String> _createMap(
		String fieldName, Map<String, Object> sourcesMap) {

		Map<String, String> map = new HashMap<>();

		for (Map.Entry<String, Object> entry : sourcesMap.entrySet()) {
			if (StringUtil.startsWith(
					entry.getKey(), fieldName + StringPool.UNDERLINE) &&
				!StringUtil.endsWith(entry.getKey(), "_sortable")) {

				map.put(
					_toLanguageTag(
						StringUtil.removeSubstring(
							entry.getKey(), fieldName + StringPool.UNDERLINE)),
					GetterUtil.getString(entry.getValue()));
			}
		}

		return map;
	}

	private static Date _parseDate(String dateString) {
		try {
			return DateUtil.parseDate(
				"yyyyMMddHHmmss", dateString, LocaleUtil.getDefault());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return null;
		}
	}

	private static String _toLanguageTag(String languageId) {
		Locale locale = LocaleUtil.fromLanguageId(languageId);

		return locale.toLanguageTag();
	}

	private static final Log _log = LogFactoryUtil.getLog(TaskUtil.class);

}