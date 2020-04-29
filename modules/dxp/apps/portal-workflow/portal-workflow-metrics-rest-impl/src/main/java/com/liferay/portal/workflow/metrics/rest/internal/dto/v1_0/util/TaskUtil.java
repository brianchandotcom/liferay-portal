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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.Field;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Task;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

		return Stream.of(
			document.getFields()
		).map(
			Map::entrySet
		).flatMap(
			Collection::stream
		).filter(
			entry ->
				StringUtil.startsWith(
					entry.getKey(), fieldName + StringPool.UNDERLINE) &&
				!StringUtil.endsWith(entry.getKey(), "_sortable")
		).collect(
			Collectors.toMap(
				entry -> _toLanguageTag(
					StringUtil.removeSubstring(
						entry.getKey(), fieldName + StringPool.UNDERLINE)),
				entry -> {
					Field field = entry.getValue();

					return String.valueOf(field.getValue());
				})
		);
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

	private static String _toLanguageTag(String languageId) {
		Locale locale = LocaleUtil.fromLanguageId(languageId);

		return locale.toLanguageTag();
	}

	private static final Log _log = LogFactoryUtil.getLog(TaskUtil.class);

}