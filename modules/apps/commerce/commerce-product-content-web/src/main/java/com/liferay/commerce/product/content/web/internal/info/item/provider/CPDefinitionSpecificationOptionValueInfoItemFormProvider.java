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

import com.liferay.commerce.product.content.web.internal.info.CPDefinitionSpecificationOptionValueInfoItemFields;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import java.util.Locale;
import java.util.Set;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFormProvider.class
)
public class CPDefinitionSpecificationOptionValueInfoItemFormProvider
	implements InfoItemFormProvider<CPDefinitionSpecificationOptionValue> {

	@Override
	public InfoForm getInfoForm() {
		return _getInfoForm();
	}

	@Override
	public InfoForm getInfoForm(
		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue) {

		return _getInfoForm();
	}

	@Override
	public InfoForm getInfoForm(String formVariationKey, long groupId) {
		return _getInfoForm();
	}

	private InfoFieldSet _getBasicInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			CPDefinitionSpecificationOptionValueInfoItemFields.nameInfoField
		).infoFieldSetEntry(
			CPDefinitionSpecificationOptionValueInfoItemFields.valueInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				"com.liferay.commerce.lang", "basic-information")
		).name(
			"basic-information"
		).build();
	}

	private InfoFieldSet _getDetailedInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			CPDefinitionSpecificationOptionValueInfoItemFields.
				companyIdInfoField
		).infoFieldSetEntry(
			CPDefinitionSpecificationOptionValueInfoItemFields.
				cpDefinitionIdInfoField
		).infoFieldSetEntry(
			CPDefinitionSpecificationOptionValueInfoItemFields.
				cpDefinitionSpecificationOptionValueIdInfoField
		).infoFieldSetEntry(
			CPDefinitionSpecificationOptionValueInfoItemFields.
				cpOptionCategoryIdInfoField
		).infoFieldSetEntry(
			CPDefinitionSpecificationOptionValueInfoItemFields.
				cpSpecificationOptionIdInfoField
		).infoFieldSetEntry(
			CPDefinitionSpecificationOptionValueInfoItemFields.
				createDateInfoField
		).infoFieldSetEntry(
			CPDefinitionSpecificationOptionValueInfoItemFields.
				defaultLanguageIdInfoField
		).infoFieldSetEntry(
			CPDefinitionSpecificationOptionValueInfoItemFields.groupIdInfoField
		).infoFieldSetEntry(
			CPDefinitionSpecificationOptionValueInfoItemFields.
				lastPublishDateInfoField
		).infoFieldSetEntry(
			CPDefinitionSpecificationOptionValueInfoItemFields.
				modifiedDateInfoField
		).infoFieldSetEntry(
			CPDefinitionSpecificationOptionValueInfoItemFields.priorityInfoField
		).infoFieldSetEntry(
			CPDefinitionSpecificationOptionValueInfoItemFields.
				stagedModelTypeInfoField
		).infoFieldSetEntry(
			CPDefinitionSpecificationOptionValueInfoItemFields.userIdInfoField
		).infoFieldSetEntry(
			CPDefinitionSpecificationOptionValueInfoItemFields.userNameInfoField
		).infoFieldSetEntry(
			CPDefinitionSpecificationOptionValueInfoItemFields.userUuidInfoField
		).infoFieldSetEntry(
			CPDefinitionSpecificationOptionValueInfoItemFields.uuidInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				"com.liferay.commerce.lang", "detailed-information")
		).name(
			"detailed-information"
		).build();
	}

	private InfoForm _getInfoForm() {
		Set<Locale> availableLocales = _language.getAvailableLocales();

		InfoLocalizedValue.Builder<String> infoLocalizedValueBuilder =
			InfoLocalizedValue.builder();

		for (Locale locale : availableLocales) {
			infoLocalizedValueBuilder.value(
				locale,
				ResourceActionsUtil.getModelResource(
					locale,
					CPDefinitionSpecificationOptionValue.class.getName()));
		}

		return InfoForm.builder(
		).infoFieldSetEntry(
			_expandoInfoItemFieldSetProvider.getInfoFieldSet(
				CPDefinitionSpecificationOptionValue.class.getName())
		).infoFieldSetEntry(
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				CPDefinitionSpecificationOptionValue.class.getName())
		).infoFieldSetEntry(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
				CPDefinitionSpecificationOptionValue.class.getName())
		).infoFieldSetEntry(
			_getBasicInformationInfoFieldSet()
		).infoFieldSetEntry(
			_getDetailedInformationInfoFieldSet()
		).labelInfoLocalizedValue(
			infoLocalizedValueBuilder.build()
		).name(
			CPDefinitionSpecificationOptionValue.class.getName()
		).build();
	}

	@Reference
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private Language _language;

	@Reference
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

}