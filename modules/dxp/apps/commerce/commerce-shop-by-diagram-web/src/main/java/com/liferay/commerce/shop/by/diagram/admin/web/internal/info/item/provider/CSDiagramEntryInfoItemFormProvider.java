/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.admin.web.internal.info.item.provider;

import com.liferay.commerce.shop.by.diagram.admin.web.internal.info.CSDiagramEntryInfoItemFields;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
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
 * @author Mahmoud Azzam
 * @author Alessio Antonio Rendina
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
			new ModelResourceLocalizedValue(CSDiagramEntry.class.getName())
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
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

}