/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

								Map<String, String> map = new HashMap<>();

								for (Locale locale : availableLocales) {
									map.put(
										LocaleUtil.toBCP47LanguageId(locale),
										LanguageUtil.get(locale, statusLabel));
								}

								return map;
							});
					}
				};
			}
		};
	}

}