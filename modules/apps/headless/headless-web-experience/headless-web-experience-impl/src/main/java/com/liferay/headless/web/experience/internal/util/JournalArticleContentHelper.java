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

package com.liferay.headless.web.experience.internal.util;

import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.journal.util.JournalConverter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 * @author Cristina González
 */
@Component(service = JournalArticleContentHelper.class)
public class JournalArticleContentHelper {

	public String createJournalArticleContent(DDMStructure ddmStructure)
		throws Exception {

		Locale originalSiteDefaultLocale =
			LocaleThreadLocal.getSiteDefaultLocale();

		try {
			LocaleThreadLocal.setSiteDefaultLocale(
				LocaleUtil.fromLanguageId(ddmStructure.getDefaultLanguageId()));

			DDMForm ddmForm = ddmStructure.getDDMForm();

			DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

			ddmFormValues.setAvailableLocales(ddmForm.getAvailableLocales());
			ddmFormValues.setDefaultLocale(ddmForm.getDefaultLocale());

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAttribute(
				"ddmFormValues", serializeDDMFormValues(ddmFormValues));

			Fields fields = _ddm.getFields(
				ddmStructure.getStructureId(), serviceContext);

			return _journalConverter.getContent(ddmStructure, fields);
		}
		finally {
			LocaleThreadLocal.setSiteDefaultLocale(originalSiteDefaultLocale);
		}
	}

	protected String serializeDDMFormValues(DDMFormValues ddmFormValues) {
		DDMFormValuesSerializer ddmFormValuesSerializer =
			_ddmFormValuesSerializerTracker.getDDMFormValuesSerializer("json");

		DDMFormValuesSerializerSerializeResponse
			ddmFormValuesSerializerSerializeResponse =
				ddmFormValuesSerializer.serialize(
					DDMFormValuesSerializerSerializeRequest.Builder.newBuilder(
						ddmFormValues
					).build());

		return ddmFormValuesSerializerSerializeResponse.getContent();
	}

	@Reference
	private DDM _ddm;

	@Reference
	private DDMFormValuesSerializerTracker _ddmFormValuesSerializerTracker;

	@Reference
	private JournalConverter _journalConverter;

}