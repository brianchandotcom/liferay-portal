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

package com.liferay.portlet.journal.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.BaseDDMFieldReader;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.util.JournalConverterUtil;

/**
 * @author Adolfo Pérez
 */
final class JournalArticleDDMFieldReader extends BaseDDMFieldReader {

	public JournalArticleDDMFieldReader(JournalArticle article) {
		_article = article;
	}

	@Override
	public Fields getFields() throws PortalException {
		try {
			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.getStructure(
					PortalUtil.getSiteGroupId(_article.getGroupId()),
					PortalUtil.getClassNameId(JournalArticle.class),
					_article.getStructureId(), true);

			String content = _article.getContent();

			return JournalConverterUtil.getDDMFields(ddmStructure, content);
		}
		catch (Exception e) {
			throw new PortalException(
				"Unable to read fields for article " + _article.getId(), e);
		}
	}

	private final JournalArticle _article;

}