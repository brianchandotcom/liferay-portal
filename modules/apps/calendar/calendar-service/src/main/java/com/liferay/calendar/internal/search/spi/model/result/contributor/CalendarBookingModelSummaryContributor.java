/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.search.spi.model.result.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.HtmlParser;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.portal.search.summary.SummaryHelper;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = "indexer.class.name=com.liferay.calendar.model.CalendarBooking",
	service = ModelSummaryContributor.class
)
public class CalendarBookingModelSummaryContributor
	implements ModelSummaryContributor {

	@Override
	public Summary getSummary(
		Document document, Locale locale, String snippet) {

		Locale snippetLocale = summaryHelper.getSnippetLocale(document, locale);

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			document.get("defaultLanguageId"));

		String localizedTitleName = Field.getLocalizedName(locale, Field.TITLE);

		if ((snippetLocale == null) &&
			(document.getField(localizedTitleName) == null)) {

			snippetLocale = defaultLocale;
		}
		else {
			snippetLocale = locale;
		}

		String prefix = Field.SNIPPET + StringPool.UNDERLINE;

		String title = document.get(
			snippetLocale, prefix + Field.TITLE, Field.TITLE);

		if (Validator.isNull(title) && !snippetLocale.equals(defaultLocale)) {
			title = document.get(
				defaultLocale, prefix + Field.TITLE, Field.TITLE);
		}

		String description = document.get(
			snippetLocale, prefix + Field.DESCRIPTION, Field.DESCRIPTION);

		if (Validator.isNull(description) &&
			!snippetLocale.equals(defaultLocale)) {

			description = document.get(
				defaultLocale, prefix + Field.DESCRIPTION, Field.DESCRIPTION);
		}

		description = _htmlParser.extractText(description);

		Summary summary = new Summary(snippetLocale, title, description);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Reference
	protected SummaryHelper summaryHelper;

	@Reference
	private HtmlParser _htmlParser;

}