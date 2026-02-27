/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.internal.search.spi.model.result.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import java.util.Locale;

/**
 * @author Luan Maoski
 */
public class UserModelSummaryContributor implements ModelSummaryContributor {

	public UserModelSummaryContributor(Localization localization) {
		_localization = localization;
	}

	@Override
	public Summary getSummary(
		Document document, Locale locale, String snippet) {

		String languageId = LocaleUtil.toLanguageId(locale);

		String prefix = Field.SNIPPET + StringPool.UNDERLINE;

		String fullName = _localization.getLocalizedName(
			"fullName", languageId);

		String title = document.get(prefix + fullName, fullName);

		String content = document.get(prefix);

		return new Summary(title, content);
	}

	private final Localization _localization;

}