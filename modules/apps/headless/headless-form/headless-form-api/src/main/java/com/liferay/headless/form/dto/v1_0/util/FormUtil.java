/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				setAvailableLanguages(
					() -> LocaleUtil.toW3cLanguageIds(
						ddmFormInstance.getAvailableLanguageIds()));
				setCreator(
					() -> CreatorUtil.toCreator(
						portal,
						userLocalService.fetchUser(
							ddmFormInstance.getUserId())));
				setDateCreated(ddmFormInstance::getCreateDate);
				setDateModified(ddmFormInstance::getModifiedDate);
				setDatePublished(ddmFormInstance::getLastPublishDate);
				setDefaultLanguage(ddmFormInstance::getDefaultLanguageId);
				setDescription(
					() -> ddmFormInstance.getDescription(preferredLocale));
				setDescription_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						acceptAllLanguages,
						ddmFormInstance.getDescriptionMap()));
				setId(ddmFormInstance::getFormInstanceId);
				setName(() -> ddmFormInstance.getName(preferredLocale));
				setName_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						acceptAllLanguages, ddmFormInstance.getNameMap()));
				setStructure(
					() -> StructureUtil.toFormStructure(
						acceptAllLanguages, ddmFormInstance.getStructure(),
						preferredLocale, portal, userLocalService));
			}
		};
	}

}