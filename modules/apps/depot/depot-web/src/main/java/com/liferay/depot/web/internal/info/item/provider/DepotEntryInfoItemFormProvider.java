/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.info.item.provider;

import com.liferay.depot.model.DepotEntry;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.localized.bundle.ModelResourceLocalizedValue;
import com.liferay.layout.page.template.info.item.provider.DisplayPageInfoItemFieldSetProvider;
import com.liferay.petra.string.StringPool;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "item.class.name=com.liferay.depot.model.DepotEntry",
	service = InfoItemFormProvider.class
)
public class DepotEntryInfoItemFormProvider
	implements InfoItemFormProvider<DepotEntry> {

	@Override
	public InfoForm getInfoForm() {
		return _getInfoForm(0);
	}

	@Override
	public InfoForm getInfoForm(DepotEntry depotEntry) {
		return _getInfoForm(depotEntry.getGroupId());
	}

	@Override
	public InfoForm getInfoForm(String formVariationKey, long groupId) {
		return _getInfoForm(groupId);
	}

	private InfoFieldSet _getBasicInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			DepotEntryInfoItemFields.createDateInfoField
		).infoFieldSetEntry(
			DepotEntryInfoItemFields.modifiedDateInfoField
		).infoFieldSetEntry(
			DepotEntryInfoItemFields.nameInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "basic-information")
		).name(
			"basic-information"
		).build();
	}

	private InfoForm _getInfoForm(InfoFieldSet displayPageInfoFieldSet) {
		return InfoForm.builder(
		).infoFieldSetEntry(
			_getBasicInformationInfoFieldSet()
		).infoFieldSetEntry(
			displayPageInfoFieldSet
		).labelInfoLocalizedValue(
			new ModelResourceLocalizedValue(DepotEntry.class.getName())
		).name(
			DepotEntry.class.getName()
		).build();
	}

	private InfoForm _getInfoForm(long groupId) {
		return _getInfoForm(
			_displayPageInfoItemFieldSetProvider.getInfoFieldSet(
				DepotEntry.class.getName(), StringPool.BLANK,
				DepotEntry.class.getSimpleName(), groupId));
	}

	@Reference
	private DisplayPageInfoItemFieldSetProvider
		_displayPageInfoItemFieldSetProvider;

}