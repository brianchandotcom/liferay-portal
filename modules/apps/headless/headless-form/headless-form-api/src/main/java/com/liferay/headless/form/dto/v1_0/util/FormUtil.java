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

package com.liferay.headless.form.dto.v1_0.util;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.headless.form.dto.v1_0.Form;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Locale;

/**
 * @author Gabriel Albuquerque
 */
public class FormUtil {

	public static Form toForm(
			Boolean acceptAllLanguages, DDMFormInstance ddmFormInstance,
			Portal portal, Locale preferredLocale,
			UserLocalService userLocalService)
		throws Exception {

		if (ddmFormInstance == null) {
			return null;
		}

		return new Form() {
			{
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					ddmFormInstance.getAvailableLanguageIds());
				creator = CreatorUtil.toCreator(
					portal,
					userLocalService.fetchUser(ddmFormInstance.getUserId()));
				dateCreated = ddmFormInstance.getCreateDate();
				dateModified = ddmFormInstance.getModifiedDate();
				datePublished = ddmFormInstance.getLastPublishDate();
				defaultLanguage = ddmFormInstance.getDefaultLanguageId();
				description = ddmFormInstance.getDescription(preferredLocale);
				description_i18n = LocalizedMapUtil.getI18nMap(
					acceptAllLanguages, ddmFormInstance.getDescriptionMap());
				id = ddmFormInstance.getFormInstanceId();
				name = ddmFormInstance.getName(preferredLocale);
				name_i18n = LocalizedMapUtil.getI18nMap(
					acceptAllLanguages, ddmFormInstance.getNameMap());
				structure = StructureUtil.toFormStructure(
					acceptAllLanguages, ddmFormInstance.getStructure(),
					preferredLocale, portal, userLocalService);
			}
		};
	}

}