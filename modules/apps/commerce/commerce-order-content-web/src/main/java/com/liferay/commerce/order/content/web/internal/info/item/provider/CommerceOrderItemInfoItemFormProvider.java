/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.info.item.provider;

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.content.web.internal.info.CommerceOrderItemInfoItemFields;
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
 * @author Alessio Antonio Rendina
 */
@Component(
	property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFormProvider.class
)
public class CommerceOrderItemInfoItemFormProvider
	implements InfoItemFormProvider<CommerceOrderItem> {

	@Override
	public InfoForm getInfoForm() {
		return _getInfoForm();
	}

	@Override
	public InfoForm getInfoForm(CommerceOrderItem commerceOrderItem) {
		return _getInfoForm();
	}

	@Override
	public InfoForm getInfoForm(String formVariationKey, long groupId) {
		return _getInfoForm();
	}

	private InfoFieldSet _getBasicInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.nameInfoField
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.quantityInfoField
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.skuInfoField
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.thumbnailURLInfoField
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.totalPriceInfoField
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.unitPriceInfoField
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.URLInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "basic-information")
		).name(
			"basic-information"
		).build();
	}

	private InfoFieldSet _getDetailedInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.orderIdInfoField
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.orderItemIdInfoField
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.companyIdInfoField
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.createDateInfoField
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.defaultLanguageIdInfoField
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.discountAmountInfoField
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.groupIdInfoField
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.modifiedDateInfoField
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.optionsInfoField
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.promoPriceInfoField
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.stagedModelTypeInfoField
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.userIdInfoField
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.userNameInfoField
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.userUuidInfoField
		).infoFieldSetEntry(
			CommerceOrderItemInfoItemFields.uuidInfoField
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
				CommerceOrderItem.class.getName())
		).infoFieldSetEntry(
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				CommerceOrderItem.class.getName())
		).infoFieldSetEntry(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
				CommerceOrderItem.class.getName())
		).infoFieldSetEntry(
			_getBasicInformationInfoFieldSet()
		).infoFieldSetEntry(
			_getDetailedInformationInfoFieldSet()
		).labelInfoLocalizedValue(
			new ModelResourceLocalizedValue(CommerceOrderItem.class.getName())
		).name(
			CommerceOrderItem.class.getName()
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