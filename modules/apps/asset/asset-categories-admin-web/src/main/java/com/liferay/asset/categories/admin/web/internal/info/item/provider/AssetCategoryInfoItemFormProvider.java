/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.admin.web.internal.info.item.provider;

import com.liferay.asset.categories.admin.web.internal.info.item.AssetCategoryInfoItemFields;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.localized.bundle.ModelResourceLocalizedValue;
import com.liferay.layout.page.template.info.item.provider.DisplayPageInfoItemFieldSetProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = InfoItemFormProvider.class)
public class AssetCategoryInfoItemFormProvider
	implements InfoItemFormProvider<AssetCategory> {

	@Override
	public InfoForm getInfoForm() {
		return getInfoForm(StringPool.BLANK, 0);
	}

	@Override
	public InfoForm getInfoForm(AssetCategory assetCategory) {
		return getInfoForm(StringPool.BLANK, 0);
	}

	@Override
	public InfoForm getInfoForm(String formVariationKey, long groupId) {
		return InfoForm.builder(
		).infoFieldSetEntry(
			_getBasicInformationInfoFieldSet()
		).infoFieldSetEntry(
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				AssetCategory.class.getName())
		).infoFieldSetEntry(
			unsafeConsumer -> {
				if (!FeatureFlagManagerUtil.isEnabled("LPS-195205")) {
					unsafeConsumer.accept(_getDisplayPageInfoFieldSet());
				}
			}
		).infoFieldSetEntry(
			unsafeConsumer -> {
				if (FeatureFlagManagerUtil.isEnabled("LPS-195205")) {
					unsafeConsumer.accept(
						_displayPageInfoItemFieldSetProvider.getInfoFieldSet(
							AssetCategory.class.getName(), StringPool.BLANK,
							AssetCategory.class.getSimpleName(), groupId));
				}
			}
		).infoFieldSetEntry(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
				AssetCategory.class.getName())
		).labelInfoLocalizedValue(
			new ModelResourceLocalizedValue(AssetCategory.class.getName())
		).name(
			AssetCategory.class.getName()
		).build();
	}

	private InfoFieldSet _getBasicInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			AssetCategoryInfoItemFields.nameInfoField
		).infoFieldSetEntry(
			AssetCategoryInfoItemFields.descriptionInfoField
		).infoFieldSetEntry(
			AssetCategoryInfoItemFields.vocabularyInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "basic-information")
		).name(
			"basic-information"
		).build();
	}

	private InfoFieldSet _getDisplayPageInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			AssetCategoryInfoItemFields.displayPageURLInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "display-page")
		).name(
			"display-page"
		).build();
	}

	@Reference
	private DisplayPageInfoItemFieldSetProvider
		_displayPageInfoItemFieldSetProvider;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

}