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

package com.liferay.object.internal.search.spi.model.result.contributor;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import java.util.Locale;
import java.util.Map;

/**
 * @author Bryan Engler
 */
public class ObjectEntryModelSummaryContributor
	implements ModelSummaryContributor {

	@Override
	public Summary getSummary(
		Document document, Locale locale, String snippet) {

		return new Summary(_getTitle(document, locale), _getContent(document));
	}

	private String _getContent(Document document) {
		StringBundler sb = new StringBundler();

		Map<String, Field> fields = document.getFields();

		for (Map.Entry<String, Field> entry : fields.entrySet()) {
			String fieldName = entry.getKey();

			if (fieldName.startsWith("snippet_nestedFieldArray.value")) {
				Field field = entry.getValue();

				sb.append(
					StringUtil.merge(
						field.getValues(), StringPool.TRIPLE_PERIOD));

				sb.append(StringPool.TRIPLE_PERIOD);
			}
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		String content = sb.toString();

		if (Validator.isBlank(content)) {
			content = StringUtil.shorten(
				document.get("objectEntryContent"), 300,
				StringPool.TRIPLE_PERIOD);
		}

		return content;
	}

	private String _getTitle(Document document, Locale locale) {
		String title = document.get(
			"snippet_objectEntryTitle_" + LanguageUtil.getLanguageId(locale));

		if (Validator.isBlank(title)) {
			title = document.get(
				"objectEntryTitle_" + LanguageUtil.getLanguageId(locale));
		}

		if (Validator.isBlank(title)) {
			title = document.get("snippet_objectEntryTitle");
		}

		if (Validator.isBlank(title)) {
			title = document.get("objectEntryTitle");
		}

		if (Validator.isBlank(title)) {
			title = document.get("snippet_" + Field.ENTRY_CLASS_PK);
		}

		if (Validator.isBlank(title)) {
			title = document.get(Field.ENTRY_CLASS_PK);
		}

		return title;
	}

}