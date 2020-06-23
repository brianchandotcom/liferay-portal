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
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.provider.InfoItemFormProvider;

import java.util.Arrays;
import java.util.List;

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
		InfoForm infoForm = new InfoForm(AssetEntry.class.getName());

		infoForm.addAll(_getAssetEntryFieldSetEntries());

		infoForm.add(
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
				AssetEntry.class.getName()));

		return infoForm;
	}

	private List<InfoFieldSetEntry> _getAssetEntryFieldSetEntries() {
		return Arrays.asList(
			AssetEntryInfoItemFields.titleInfoField,
			AssetEntryInfoItemFields.descriptionInfoField,
			AssetEntryInfoItemFields.summaryInfoField,
			AssetEntryInfoItemFields.userNameInfoField,
			AssetEntryInfoItemFields.createDateInfoField,
			AssetEntryInfoItemFields.modifiedDateInfoField,
			AssetEntryInfoItemFields.expirationDateInfoField,
			AssetEntryInfoItemFields.viewCountInfoField,
			AssetEntryInfoItemFields.urlInfoField,
			AssetEntryInfoItemFields.userProfileImage);
	}

	@Reference
	private AssetEntryInfoItemFieldSetProvider
		_assetEntryInfoItemFieldSetProvider;

}