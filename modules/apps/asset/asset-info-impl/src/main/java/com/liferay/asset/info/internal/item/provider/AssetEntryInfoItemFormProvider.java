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