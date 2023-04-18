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

package com.liferay.headless.delivery.dto.v1_0.util;

import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidator;
import com.liferay.journal.util.JournalConverter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;

/**
 * @author Luis Miguel Barcos
 */
public class StructuredContentUtil {

	public static String getJournalArticleContent(
			DDM ddm, DDMFormValues ddmFormValues,
			DDMFormValuesSerializer ddmFormValuesSerializer,
			DDMFormValuesValidator ddmFormValuesValidator,
			DDMStructure ddmStructure, JournalConverter journalConverter)
		throws Exception {

		ddmFormValuesValidator.validate(ddmFormValues);

		Locale originalSiteDefaultLocale =
			LocaleThreadLocal.getSiteDefaultLocale();

		try {
			LocaleThreadLocal.setSiteDefaultLocale(
				LocaleUtil.fromLanguageId(ddmStructure.getDefaultLanguageId()));

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAttribute(
				"ddmFormValues",
				DDMFormValuesUtil.getContent(
					ddmFormValuesSerializer, ddmStructure.getDDMForm(),
					ddmFormValues.getDDMFormFieldValues()));

			return journalConverter.getContent(
				ddmStructure,
				ddm.getFields(ddmStructure.getStructureId(), serviceContext),
				ddmStructure.getGroupId());
		}
		finally {
			LocaleThreadLocal.setSiteDefaultLocale(originalSiteDefaultLocale);
		}
	}

}