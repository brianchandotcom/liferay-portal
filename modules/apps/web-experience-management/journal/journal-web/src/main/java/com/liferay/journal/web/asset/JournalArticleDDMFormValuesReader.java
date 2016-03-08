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

package com.liferay.journal.web.asset;

import com.liferay.asset.kernel.model.BaseDDMFormValuesReader;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslatorUtil;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.util.JournalConverter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Adolfo Pérez
 */
final class JournalArticleDDMFormValuesReader extends BaseDDMFormValuesReader {

	public JournalArticleDDMFormValuesReader(JournalArticle article) {
		_article = article;
	}

	@Override
	public DDMFormValues getDDMFormValues() throws PortalException {
		try {
			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.getStructure(
					PortalUtil.getSiteGroupId(_article.getGroupId()),
					PortalUtil.getClassNameId(JournalArticle.class),
					_article.getDDMStructureKey(), true);

			Fields fields = _journalConverter.getDDMFields(
				ddmStructure, _article.getContent());

			return DDMBeanTranslatorUtil.translate(
				_fieldsToDDMFormValuesConverter.convert(ddmStructure, fields));
		}
		catch (Exception e) {
			throw new PortalException(
				"Unable to read fields for article " + _article.getId(), e);
		}
	}

	public void setJournalConverter(JournalConverter journalConverter) {
		_journalConverter = journalConverter;
	}

	public void setFieldsToDDMFormValuesConverter(
		FieldsToDDMFormValuesConverter fieldsToDDMFormValuesConverter) {

		_fieldsToDDMFormValuesConverter = fieldsToDDMFormValuesConverter;
	}

	private final JournalArticle _article;
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;
	private JournalConverter _journalConverter;
}