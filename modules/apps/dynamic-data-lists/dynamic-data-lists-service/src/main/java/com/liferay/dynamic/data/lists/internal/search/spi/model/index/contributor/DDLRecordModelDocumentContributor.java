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

package com.liferay.dynamic.data.lists.internal.search.spi.model.index.contributor;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.dynamic.data.lists.model.DDLRecord",
	service = ModelDocumentContributor.class
)
public class DDLRecordModelDocumentContributor
	implements ModelDocumentContributor<DDLRecord> {

	@Override
	public void contribute(Document document, DDLRecord ddlRecord) {
		try {
			DDLRecordVersion ddlRecordVersion = ddlRecord.getRecordVersion();

			DDLRecordSet ddlRecordSet = ddlRecordVersion.getRecordSet();

			document.addKeyword(
				Field.CLASS_NAME_ID,
				classNameLocalService.getClassNameId(DDLRecordSet.class));
			document.addKeyword(Field.CLASS_PK, ddlRecordSet.getRecordSetId());
			document.addKeyword(
				Field.CLASS_TYPE_ID, ddlRecordVersion.getRecordSetId());
			document.addKeyword(Field.RELATED_ENTRY, true);
			document.addKeyword(Field.STATUS, ddlRecordVersion.getStatus());
			document.addKeyword(Field.VERSION, ddlRecordVersion.getVersion());
			document.addKeyword("recordSetId", ddlRecordSet.getRecordSetId());
			document.addKeyword("recordSetScope", ddlRecordSet.getScope());

			DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

			DDMFormValues ddmFormValues = ddlRecordVersion.getDDMFormValues();

			addContent(ddlRecordVersion, ddmFormValues, document);

			ddmIndexer.addAttributes(document, ddmStructure, ddmFormValues);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	protected void addContent(
			DDLRecordVersion ddlRecordVersion, DDMFormValues ddmFormValues,
			Document document)
		throws Exception {

		Set<Locale> locales = ddmFormValues.getAvailableLocales();

		for (Locale locale : locales) {
			StringBundler sb = new StringBundler(3);

			sb.append("ddmContent");
			sb.append(StringPool.UNDERLINE);
			sb.append(LocaleUtil.toLanguageId(locale));

			document.addText(
				sb.toString(), extractContent(ddlRecordVersion, locale));
		}
	}

	protected String extractContent(
			DDLRecordVersion ddlRecordVersion, Locale locale)
		throws Exception {

		DDMFormValues ddmFormValues = ddlRecordVersion.getDDMFormValues();

		if (ddmFormValues == null) {
			return StringPool.BLANK;
		}

		DDLRecordSet ddlRecordSet = ddlRecordVersion.getRecordSet();

		return ddmIndexer.extractIndexableAttributes(
			ddlRecordSet.getDDMStructure(), ddmFormValues, locale);
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected DDMIndexer ddmIndexer;

	private static final Log _log = LogFactoryUtil.getLog(
		DDLRecordModelDocumentContributor.class);

}