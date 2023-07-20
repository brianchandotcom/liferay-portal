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