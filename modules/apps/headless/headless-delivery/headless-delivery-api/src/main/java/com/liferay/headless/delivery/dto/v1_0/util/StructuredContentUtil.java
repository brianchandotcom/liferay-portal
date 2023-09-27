/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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