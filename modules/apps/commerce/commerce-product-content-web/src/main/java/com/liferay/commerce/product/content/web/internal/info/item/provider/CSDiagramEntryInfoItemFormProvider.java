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

import com.liferay.commerce.product.content.web.internal.info.CSDiagramEntryInfoItemFields;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
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
 * @author Mahmoud Azzam
 */
@Component(
	property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFormProvider.class
)
public class CSDiagramEntryInfoItemFormProvider
	implements InfoItemFormProvider<CSDiagramEntry> {

	@Override
	public InfoForm getInfoForm() {
		return _getInfoForm();
	}

	@Override
	public InfoForm getInfoForm(CSDiagramEntry csDiagramEntry) {
		return _getInfoForm();
	}

	@Override
	public InfoForm getInfoForm(String formVariationKey, long groupId) {
		return _getInfoForm();
	}

	private InfoFieldSet _getBasicInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			CSDiagramEntryInfoItemFields.cProductIdInfoField
		).infoFieldSetEntry(
			CSDiagramEntryInfoItemFields.diagramInfoField
		).infoFieldSetEntry(
			CSDiagramEntryInfoItemFields.quantityInfoField
		).infoFieldSetEntry(
			CSDiagramEntryInfoItemFields.sequenceInfoField
		).infoFieldSetEntry(
			CSDiagramEntryInfoItemFields.skuInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "basic-information")
		).name(
			"basic-information"
		).build();
	}

	private InfoFieldSet _getDetailedInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			CSDiagramEntryInfoItemFields.createDateInfoField
		).infoFieldSetEntry(
			CSDiagramEntryInfoItemFields.modifiedDateInfoField
		).infoFieldSetEntry(
			CSDiagramEntryInfoItemFields.userNameInfoField
		).infoFieldSetEntry(
			CSDiagramEntryInfoItemFields.cpInstanceIdInfoField
		).infoFieldSetEntry(
			CSDiagramEntryInfoItemFields.cpDefinitionIdInfoField
		).infoFieldSetEntry(
			CSDiagramEntryInfoItemFields.companyIdInfoField
		).infoFieldSetEntry(
			CSDiagramEntryInfoItemFields.csDiagramEntryIdInfoField
		).infoFieldSetEntry(
			CSDiagramEntryInfoItemFields.userIdInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "detailed-information")
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
					locale, CSDiagramEntry.class.getName()));
		}

		return InfoForm.builder(
		).infoFieldSetEntry(
			_expandoInfoItemFieldSetProvider.getInfoFieldSet(
				CSDiagramEntry.class.getName())
		).infoFieldSetEntry(
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				CSDiagramEntry.class.getName())
		).infoFieldSetEntry(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
				CSDiagramEntry.class.getName())
		).infoFieldSetEntry(
			_getBasicInformationInfoFieldSet()
		).infoFieldSetEntry(
			_getDetailedInformationInfoFieldSet()
		).labelInfoLocalizedValue(
			infoLocalizedValueBuilder.build()
		).name(
			CSDiagramEntry.class.getName()
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