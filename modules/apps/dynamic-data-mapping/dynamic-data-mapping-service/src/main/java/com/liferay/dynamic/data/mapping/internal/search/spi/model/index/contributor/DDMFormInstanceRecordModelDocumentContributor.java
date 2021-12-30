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

package com.liferay.dynamic.data.mapping.internal.search.spi.model.index.contributor;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
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
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord",
	service = ModelDocumentContributor.class
)
public class DDMFormInstanceRecordModelDocumentContributor
	implements ModelDocumentContributor<DDMFormInstanceRecord> {

	@Override
	public void contribute(
		Document document, DDMFormInstanceRecord ddmFormInstanceRecord) {

		try {
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
				ddmFormInstanceRecord.getFormInstanceRecordVersion();

			DDMFormInstance ddmFormInstance =
				ddmFormInstanceRecordVersion.getFormInstance();

			document.addKeyword(
				Field.CLASS_NAME_ID,
				classNameLocalService.getClassNameId(DDMFormInstance.class));
			document.addKeyword(
				Field.CLASS_PK, ddmFormInstance.getFormInstanceId());
			document.addKeyword(
				Field.CLASS_TYPE_ID,
				ddmFormInstanceRecordVersion.getFormInstanceId());
			document.addKeyword(Field.RELATED_ENTRY, true);
			document.addKeyword(
				Field.STATUS, ddmFormInstanceRecordVersion.getStatus());
			document.addKeyword(
				Field.VERSION, ddmFormInstanceRecordVersion.getVersion());
			document.addKeyword(
				"formInstanceId", ddmFormInstance.getFormInstanceId());

			DDMStructure ddmStructure = ddmFormInstance.getStructure();

			DDMFormValues ddmFormValues =
				ddmFormInstanceRecordVersion.getDDMFormValues();

			_addContent(ddmFormInstanceRecordVersion, ddmFormValues, document);

			ddmIndexer.addAttributes(document, ddmStructure, ddmFormValues);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected DDMIndexer ddmIndexer;

	private void _addContent(
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion,
			DDMFormValues ddmFormValues, Document document)
		throws Exception {

		Set<Locale> locales = ddmFormValues.getAvailableLocales();

		for (Locale locale : locales) {
			document.addText(
				"ddmContent_" + LocaleUtil.toLanguageId(locale),
				_extractContent(ddmFormInstanceRecordVersion, locale));
		}
	}

	private String _extractContent(
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion,
			Locale locale)
		throws Exception {

		DDMFormValues ddmFormValues =
			ddmFormInstanceRecordVersion.getDDMFormValues();

		if (ddmFormValues == null) {
			return StringPool.BLANK;
		}

		DDMFormInstance ddmFormInstance =
			ddmFormInstanceRecordVersion.getFormInstance();

		return ddmIndexer.extractIndexableAttributes(
			ddmFormInstance.getStructure(), ddmFormValues, locale);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordModelDocumentContributor.class);

}