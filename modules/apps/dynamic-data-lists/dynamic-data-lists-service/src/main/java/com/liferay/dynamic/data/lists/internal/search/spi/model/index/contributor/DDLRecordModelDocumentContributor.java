/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.search.spi.model.index.contributor;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
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

			_addContent(ddlRecordVersion, ddmFormValues, document);

			ddmIndexer.addAttributes(document, ddmStructure, ddmFormValues);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected DDMIndexer ddmIndexer;

	private void _addContent(
			DDLRecordVersion ddlRecordVersion, DDMFormValues ddmFormValues,
			Document document)
		throws Exception {

		Set<Locale> locales = ddmFormValues.getAvailableLocales();

		for (Locale locale : locales) {
			document.addText(
				"ddmContent_" + LocaleUtil.toLanguageId(locale),
				_extractContent(ddlRecordVersion, locale));
		}
	}

	private String _extractContent(
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

	private static final Log _log = LogFactoryUtil.getLog(
		DDLRecordModelDocumentContributor.class);

}