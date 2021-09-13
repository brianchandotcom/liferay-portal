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

package com.liferay.commerce.product.content.web.internal.info.item.provider;

import com.liferay.commerce.product.content.web.internal.info.CPDefinitionInfoItemFields;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import java.util.Locale;
import java.util.Set;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true,
	property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFormProvider.class
)
public class CPDefinitionInfoItemFormProvider
	implements InfoItemFormProvider<CPDefinition> {

	@Override
	public InfoForm getInfoForm() {
		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();

		InfoLocalizedValue.Builder<String> infoLocalizedValueBuilder =
			InfoLocalizedValue.builder();

		for (Locale locale : availableLocales) {
			infoLocalizedValueBuilder.value(
				locale,
				ResourceActionsUtil.getModelResource(
					locale, CPDefinition.class.getName()));
		}

		return InfoForm.builder(
		).infoFieldSetEntry(
			_getBasicInformationInfoFieldSet()
		).infoFieldSetEntry(
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				CPDefinition.class.getName())
		).infoFieldSetEntry(
			_getDisplayPageInfoFieldSet()
		).labelInfoLocalizedValue(
			infoLocalizedValueBuilder.build()
		).name(
			CPDefinition.class.getName()
		).build();
	}

	private InfoFieldSet _getBasicInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.titleInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.descriptionInfoField
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.defaultImage
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				"com.liferay.commerce.lang", "basic-information")
		).name(
			"basic-information"
		).build();
	}

	private InfoFieldSet _getDisplayPageInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			CPDefinitionInfoItemFields.displayPageUrlInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "configuration")
		).name(
			"configuration"
		).build();
	}

	@Reference
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

}