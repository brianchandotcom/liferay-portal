/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.web.internal.info.item.provider;

import com.liferay.commerce.product.content.web.internal.info.CPDefinitionOptionValueRelInfoItemFields;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.localized.bundle.ModelResourceLocalizedValue;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(
	property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFormProvider.class
)
public class CPDefinitionOptionValueRelInfoItemFormProvider
	implements InfoItemFormProvider<CPDefinitionOptionValueRel> {

	@Override
	public InfoForm getInfoForm() {
		return _getInfoForm();
	}

	@Override
	public InfoForm getInfoForm(
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {

		return _getInfoForm();
	}

	@Override
	public InfoForm getInfoForm(String formVariationKey, long groupId) {
		return _getInfoForm();
	}

	private InfoFieldSet _getBasicInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			CPDefinitionOptionValueRelInfoItemFields.
				cpDefinitionOptionValueRelIdInfoField
		).infoFieldSetEntry(
			CPDefinitionOptionValueRelInfoItemFields.keyInfoField
		).infoFieldSetEntry(
			CPDefinitionOptionValueRelInfoItemFields.nameInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "basic-information")
		).name(
			"basic-information"
		).build();
	}

	private InfoForm _getInfoForm() {
		return InfoForm.builder(
		).infoFieldSetEntry(
			_expandoInfoItemFieldSetProvider.getInfoFieldSet(
				CPDefinitionOptionValueRel.class.getName())
		).infoFieldSetEntry(
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				CPDefinitionOptionValueRel.class.getName())
		).infoFieldSetEntry(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
				CPDefinitionOptionValueRel.class.getName())
		).infoFieldSetEntry(
			_getBasicInformationInfoFieldSet()
		).labelInfoLocalizedValue(
			new ModelResourceLocalizedValue(
				CPDefinitionOptionValueRel.class.getName())
		).name(
			CPDefinitionOptionValueRel.class.getName()
		).build();
	}

	@Reference
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

}