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

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDMBeanCopyUtil;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverterUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.util.JournalConverter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.BaseDDMFormValuesReader;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

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
			JournalConverter journalConverter = getJournalConverter();

			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.getStructure(
					PortalUtil.getSiteGroupId(_article.getGroupId()),
					PortalUtil.getClassNameId(JournalArticle.class),
					_article.getDDMStructureKey(), true);

			Fields fields = journalConverter.getDDMFields(
				ddmStructure, _article.getContent());

			return DDMBeanCopyUtil.copyDDMFormValues(
				FieldsToDDMFormValuesConverterUtil.convert(
					ddmStructure, fields));
		}
		catch (Exception e) {
			throw new PortalException(
				"Unable to read fields for article " + _article.getId(), e);
		}
	}

	protected JournalConverter getJournalConverter() {
		Registry registry = RegistryUtil.getRegistry();

		return registry.getService(JournalConverter.class);
	}

	private final JournalArticle _article;

}