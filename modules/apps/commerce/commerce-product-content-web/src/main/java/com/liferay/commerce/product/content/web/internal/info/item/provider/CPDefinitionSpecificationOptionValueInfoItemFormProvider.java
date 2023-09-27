/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.info.localized.bundle.ModelResourceLocalizedValue;
import com.liferay.portal.kernel.language.Language;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
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
			InfoLocalizedValue.localize(getClass(), "basic-information")
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
			InfoLocalizedValue.localize(getClass(), "detailed-information")
		).name(
			"detailed-information"
		).build();
	}

	private InfoForm _getInfoForm() {
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
			new ModelResourceLocalizedValue(
				CPDefinitionSpecificationOptionValue.class.getName())
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