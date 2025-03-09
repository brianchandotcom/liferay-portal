/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.item.provider;

import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.localized.bundle.ModelResourceLocalizedValue;
import com.liferay.layout.page.template.info.item.provider.DisplayPageInfoItemFieldSetProvider;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.petra.string.StringPool;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "item.class.name=com.liferay.object.model.ObjectEntryFolder",
	service = InfoItemFormProvider.class
)
public class ObjectEntryFolderInfoItemFormProvider
	implements InfoItemFormProvider<ObjectEntryFolder> {

	@Override
	public InfoForm getInfoForm() {
		return _getInfoForm(0);
	}

	@Override
	public InfoForm getInfoForm(ObjectEntryFolder objectEntryFolder) {
		return _getInfoForm(objectEntryFolder.getGroupId());
	}

	@Override
	public InfoForm getInfoForm(String formVariationKey, long groupId) {
		return _getInfoForm(groupId);
	}

	private InfoFieldSet _getBasicInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			ObjectEntryFolderInfoItemFields.createDateInfoField
		).infoFieldSetEntry(
			ObjectEntryFolderInfoItemFields.modifiedDateInfoField
		).infoFieldSetEntry(
			ObjectEntryFolderInfoItemFields.nameInfoField
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
			new ModelResourceLocalizedValue(ObjectEntryFolder.class.getName())
		).name(
			ObjectEntryFolder.class.getName()
		).build();
	}

	private InfoForm _getInfoForm(long groupId) {
		return _getInfoForm(
			_displayPageInfoItemFieldSetProvider.getInfoFieldSet(
				ObjectEntryFolder.class.getName(), StringPool.BLANK,
				ObjectEntryFolder.class.getSimpleName(), groupId));
	}

	@Reference
	private DisplayPageInfoItemFieldSetProvider
		_displayPageInfoItemFieldSetProvider;

}