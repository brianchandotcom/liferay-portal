/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.internal.search.spi.model.result.contributor;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import java.util.Locale;

/**
 * @author Mikel Lorza
 */
public class SharingEntryModelSummaryContributor
	implements ModelSummaryContributor {

	@Override
	public Summary getSummary(
		Document document, Locale locale, String snippet) {

		Summary summary = new Summary(_getTitle(document, locale), null);

		summary.setMaxContentLength(200);

		return summary;
	}

	private String _getTitle(Document document, Locale locale) {
		String localizedFieldTitle = Field.getLocalizedName(
			locale, "localized_title");

		if (Validator.isNull(document.getField(localizedFieldTitle))) {
			return document.get(
				LocaleUtil.fromLanguageId(
					document.get(Field.DEFAULT_LANGUAGE_ID)),
				"localized_title");
		}

		return document.get(locale, "localized_title");
	}

}