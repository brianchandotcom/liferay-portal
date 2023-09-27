/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.search.spi.model.result.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	property = "indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = ModelSummaryContributor.class
)
public class DLFileEntryModelSummaryContributor
	implements ModelSummaryContributor {

	@Override
	public Summary getSummary(
		Document document, Locale locale, String snippet) {

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			document.get(Field.DEFAULT_LANGUAGE_ID));

		String prefix = Field.SNIPPET + StringPool.UNDERLINE;

		String content = document.get(
			locale, prefix + Field.CONTENT, Field.CONTENT);

		if (Validator.isNull(content)) {
			content = document.get(
				locale, prefix + Field.DESCRIPTION, Field.DESCRIPTION);

			if (Validator.isNull(content) && !locale.equals(defaultLocale)) {
				content = document.get(
					defaultLocale, prefix + Field.DESCRIPTION,
					Field.DESCRIPTION);
			}
		}

		String title = document.get(locale, prefix + Field.TITLE, Field.TITLE);

		if (Validator.isNull(title) && !locale.equals(defaultLocale)) {
			title = document.get(
				defaultLocale, prefix + Field.TITLE, Field.TITLE);
		}

		Summary summary = new Summary(title, content);

		summary.setMaxContentLength(200);

		return summary;
	}

}