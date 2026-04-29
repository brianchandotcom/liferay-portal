/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.internal.search.spi.model.result.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import java.util.Locale;

/**
 * @author Luis Ortiz
 */
public class StyleBookEntryModelSummaryContributor
	implements ModelSummaryContributor {

	@Override
	public Summary getSummary(
		Document document, Locale locale, String snippet) {

		Summary summary = new Summary(_getTitle(document), null);

		summary.setMaxContentLength(200);

		return summary;
	}

	private String _getTitle(Document document) {
		String title = document.get(
			Field.SNIPPET + StringPool.UNDERLINE + Field.TITLE, Field.TITLE);

		if (Validator.isBlank(title)) {
			title = document.get(Field.NAME);
		}

		return title;
	}

}