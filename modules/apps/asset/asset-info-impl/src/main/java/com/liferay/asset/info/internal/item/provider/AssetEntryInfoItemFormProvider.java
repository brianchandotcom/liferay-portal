/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.info.internal.item.provider;

import com.liferay.asset.info.internal.item.AssetEntryInfoItemFields;
import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.localized.bundle.ModelResourceLocalizedValue;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(service = InfoItemFormProvider.class)
public class AssetEntryInfoItemFormProvider
	implements InfoItemFormProvider<AssetEntry> {

	@Override
	public InfoForm getInfoForm() {
		return InfoForm.builder(
		).infoFieldSetEntry(
			InfoFieldSet.builder(
			).infoFieldSetEntry(
				AssetEntryInfoItemFields.titleInfoField
			).infoFieldSetEntry(
				AssetEntryInfoItemFields.descriptionInfoField
			).infoFieldSetEntry(
				AssetEntryInfoItemFields.summaryInfoField
			).infoFieldSetEntry(
				AssetEntryInfoItemFields.userNameInfoField
			).infoFieldSetEntry(
				AssetEntryInfoItemFields.createDateInfoField
			).infoFieldSetEntry(
				AssetEntryInfoItemFields.modifiedDateInfoField
			).infoFieldSetEntry(
				AssetEntryInfoItemFields.expirationDateInfoField
			).infoFieldSetEntry(
				AssetEntryInfoItemFields.viewCountInfoField
			).infoFieldSetEntry(
				AssetEntryInfoItemFields.displayPageURLInfoField
			).infoFieldSetEntry(
				AssetEntryInfoItemFields.urlInfoField
			).infoFieldSetEntry(
				AssetEntryInfoItemFields.userProfileImageInfoField
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(getClass(), "basic-information")
			).name(
				"basic-information"
			).build()
		).infoFieldSetEntry(
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				AssetEntry.class.getName())
		).infoFieldSetEntry(
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
				AssetEntry.class.getName())
		).labelInfoLocalizedValue(
			new ModelResourceLocalizedValue(AssetEntry.class.getName())
		).name(
			AssetEntry.class.getName()
		).build();
	}

	@Reference
	private AssetEntryInfoItemFieldSetProvider
		_assetEntryInfoItemFieldSetProvider;

	@Reference
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

}