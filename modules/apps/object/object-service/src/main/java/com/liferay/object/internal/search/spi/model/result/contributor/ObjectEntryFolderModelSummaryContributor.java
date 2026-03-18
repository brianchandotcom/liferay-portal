/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.result.contributor;

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
public class ObjectEntryFolderModelSummaryContributor
	implements ModelSummaryContributor {

	@Override
	public Summary getSummary(
		Document document, Locale locale, String snippet) {

		Summary summary = new Summary(_getTitle(document, locale), null);

		summary.setMaxContentLength(200);

		return summary;
	}

	private String _getTitle(Document document, Locale locale) {
		String title = document.get("snippet_" + Field.TITLE);

		if (Validator.isBlank(title)) {
			title = document.get(Field.TITLE);
		}

		if (Validator.isBlank(title)) {
			title = document.get(Field.NAME);
		}

		if (Validator.isBlank(title)) {
			String localizedFieldTitle = Field.getLocalizedName(
				locale, "localized_label");

			if (Validator.isNull(document.getField(localizedFieldTitle))) {
				title = document.get(
					LocaleUtil.fromLanguageId(
						document.get(Field.DEFAULT_LANGUAGE_ID)),
					"localized_label");
			}
			else {
				title = document.get(locale, "localized_label");
			}
		}

		return title;
	}

}