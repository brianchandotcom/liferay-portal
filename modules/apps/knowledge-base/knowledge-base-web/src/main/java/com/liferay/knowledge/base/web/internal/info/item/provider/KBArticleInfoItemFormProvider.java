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

package com.liferay.knowledge.base.web.internal.info.item.provider;

import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.localized.bundle.ModelResourceLocalizedValue;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.web.internal.info.item.KBArticleInfoItemFields;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFormProvider.class
)
public class KBArticleInfoItemFormProvider
	implements InfoItemFormProvider<KBArticle> {

	@Override
	public InfoForm getInfoForm() {
		return _getInfoForm(
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
				KBArticle.class.getName()));
	}

	@Override
	public InfoForm getInfoForm(KBArticle kbArticle) {
		try {
			return _getInfoForm(
				_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
					_assetEntryLocalService.getEntry(
						KBArticle.class.getName(),
						kbArticle.getResourcePrimKey())));
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"Unable to get asset entry for knowledge base article entry " +
					kbArticle.getResourcePrimKey(),
				portalException);
		}
	}

	@Override
	public InfoForm getInfoForm(String formVariationKey, long groupId) {
		return _getInfoForm(
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
				KBArticle.class.getName(), 0, groupId));
	}

	private InfoFieldSet _getBasicInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			KBArticleInfoItemFields.titleInfoField
		).infoFieldSetEntry(
			KBArticleInfoItemFields.authorNameInfoField
		).infoFieldSetEntry(
			KBArticleInfoItemFields.authorProfileImageInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "basic-information")
		).name(
			"basic-information"
		).build();
	}

	private InfoFieldSet _getConfigurationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			KBArticleInfoItemFields.descriptionInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "configuration")
		).name(
			"configuration"
		).build();
	}

	private InfoFieldSet _getContentInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			KBArticleInfoItemFields.contentInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "content")
		).name(
			"content"
		).build();
	}

	private InfoForm _getInfoForm(InfoFieldSet assetEntryInfoFieldSet) {
		return InfoForm.builder(
		).infoFieldSetEntry(
			_getBasicInformationInfoFieldSet()
		).infoFieldSetEntry(
			_getContentInfoFieldSet()
		).infoFieldSetEntry(
			_expandoInfoItemFieldSetProvider.getInfoFieldSet(
				KBArticle.class.getName())
		).infoFieldSetEntry(
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				KBArticle.class.getName())
		).infoFieldSetEntry(
			_getConfigurationInfoFieldSet()
		).infoFieldSetEntry(
			assetEntryInfoFieldSet
		).infoFieldSetEntry(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
				KBArticle.class.getName())
		).labelInfoLocalizedValue(
			new ModelResourceLocalizedValue(KBArticle.class.getName())
		).name(
			KBArticle.class.getName()
		).build();
	}

	@Reference
	private AssetEntryInfoItemFieldSetProvider
		_assetEntryInfoItemFieldSetProvider;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

}