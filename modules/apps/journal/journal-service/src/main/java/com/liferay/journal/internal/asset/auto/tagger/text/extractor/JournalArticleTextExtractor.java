/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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