/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.search.spi.model.result.contributor;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = "indexer.class.name=com.liferay.journal.model.JournalFolder",
	service = ModelSummaryContributor.class
)
public class JournalFolderModelSummaryContributor
	implements ModelSummaryContributor {

	@Override
	public Summary getSummary(
		Document document, Locale locale, String snippet) {

		Summary summary = new Summary(
			document.get(
				locale,
				StringBundler.concat(
					Field.SNIPPET, StringPool.UNDERLINE, Field.TITLE),
				Field.TITLE),
			document.get(
				locale,
				StringBundler.concat(
					Field.SNIPPET, StringPool.UNDERLINE, Field.DESCRIPTION),
				Field.DESCRIPTION));

		summary.setMaxContentLength(200);

		return summary;
	}

}