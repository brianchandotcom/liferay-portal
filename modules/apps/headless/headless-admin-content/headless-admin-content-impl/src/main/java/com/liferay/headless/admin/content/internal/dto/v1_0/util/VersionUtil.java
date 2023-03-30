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

package com.liferay.headless.admin.content.internal.dto.v1_0.util;

import com.liferay.headless.admin.content.dto.v1_0.Status;
import com.liferay.headless.admin.content.dto.v1_0.Version;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Luis Miguel Barcos
 */
public class VersionUtil {

	public static Version toVersion(
		AcceptLanguage acceptLanguage, JournalArticle journalArticle) {

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales(
			journalArticle.getGroupId());

		String statusLabel = WorkflowConstants.getStatusLabel(
			journalArticle.getStatus());

		return new Version() {
			{
				number = journalArticle.getVersion();
				status = new Status() {
					{
						code = journalArticle.getStatus();
						label = statusLabel;

						setLabel_i18n(
							() -> {
								if (!acceptLanguage.isAcceptAllLanguages()) {
									return null;
								}

								Map<String, String> stringMap = new HashMap<>();

								for (Locale locale : availableLocales) {
									stringMap.put(
										LocaleUtil.toBCP47LanguageId(locale),
										LanguageUtil.get(locale, statusLabel));
								}

								return stringMap;
							});
					}
				};
			}
		};
	}

}