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

package com.liferay.journal.internal.asset.auto.tagger.text.extractor;

import com.liferay.asset.auto.tagger.text.extractor.TextExtractor;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 * @author Alicia García
 * @author Alejandro Tardín
 */
@Component(service = TextExtractor.class)
public class JournalArticleTextExtractor
	implements TextExtractor<JournalArticle> {

	@Override
	public String extract(JournalArticle journalArticle, Locale locale) {
		DDMFormValues ddmFormValues = null;

		try {
			ddmFormValues = journalArticle.getDDMFormValues();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return StringPool.BLANK;
		}

		if (ddmFormValues == null) {
			return StringPool.BLANK;
		}

		return _ddmIndexer.extractIndexableAttributes(
			journalArticle.getDDMStructure(), ddmFormValues, locale);
	}

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleTextExtractor.class);

	@Reference
	private DDMIndexer _ddmIndexer;

}