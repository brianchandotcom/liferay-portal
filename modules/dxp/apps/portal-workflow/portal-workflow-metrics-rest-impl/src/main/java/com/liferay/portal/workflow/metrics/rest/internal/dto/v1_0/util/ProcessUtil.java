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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.Field;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Process;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Rafael Praxedes
 */
public class ProcessUtil {

	public static Process toProcess(Document document, Locale locale) {
		Map<String, String> titleMap = _createTitleMap(document);

		return new Process() {
			{
				active = document.getBoolean("active");
				dateCreated = _parseDate(document.getDate("createDate"));
				dateModified = _parseDate(document.getDate("modifiedDate"));
				description = document.getString("description");
				id = document.getLong("processId");
				title_i18n = titleMap;
				version = document.getString("version");

				setTitle(
					() -> {
						String title = titleMap.get(locale.toLanguageTag());

						if (Validator.isNull(title)) {
							Locale defaultLocale =
								LocaleThreadLocal.getDefaultLocale();

							title = titleMap.get(defaultLocale.toLanguageTag());
						}

						return title;
					});
			}
		};
	}

	private static Map<String, String> _createTitleMap(Document document) {
		return Stream.of(
			document.getFields()
		).map(
			Map::entrySet
		).flatMap(
			Collection::stream
		).filter(
			entry ->
				StringUtil.startsWith(entry.getKey(), "title_") &&
				!StringUtil.endsWith(entry.getKey(), "_sortable")
		).collect(
			Collectors.toMap(
				entry -> _toLanguageTag(
					StringUtil.removeSubstring(entry.getKey(), "title_")),
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

	private static final Log _log = LogFactoryUtil.getLog(ProcessUtil.class);

}