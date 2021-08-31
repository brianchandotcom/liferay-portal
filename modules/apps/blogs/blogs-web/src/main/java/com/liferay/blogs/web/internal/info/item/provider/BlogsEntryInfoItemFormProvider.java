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

package com.liferay.blogs.web.internal.info.item.provider;

import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.web.internal.info.item.BlogsEntryInfoItemFields;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import java.util.Locale;
import java.util.Set;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 * @author Jorge Ferrer
 */
@Component(
	immediate = true, property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFormProvider.class
)
public class BlogsEntryInfoItemFormProvider
	implements InfoItemFormProvider<BlogsEntry> {

	@Override
	public InfoForm getInfoForm() {
		return _getInfoForm(
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
				BlogsEntry.class.getName()));
	}

	@Override
	public InfoForm getInfoForm(BlogsEntry blogsEntry) {
		try {
			return _getInfoForm(
				_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
					_assetEntryLocalService.getEntry(
						BlogsEntry.class.getName(), blogsEntry.getEntryId())));
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"Unable to get asset entry for blogs entry " +
					blogsEntry.getEntryId(),
				portalException);
		}
	}

	@Override
	public InfoForm getInfoForm(String formVariationKey, long groupId) {
		return _getInfoForm(
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
				BlogsEntry.class.getName(), 0, groupId));
	}

	private InfoFieldSet _getBasicInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			BlogsEntryInfoItemFields.titleInfoField
		).infoFieldSetEntry(
			BlogsEntryInfoItemFields.authorNameInfoField
		).infoFieldSetEntry(
			BlogsEntryInfoItemFields.authorProfileImageInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				"com.liferay.journal.lang", "basic-information")
		).name(
			"basic-information"
		).build();
	}

	private InfoFieldSet _getConfigurationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			BlogsEntryInfoItemFields.descriptionInfoField
		).infoFieldSetEntry(
			BlogsEntryInfoItemFields.smallImageInfoField
		).infoFieldSetEntry(
			BlogsEntryInfoItemFields.displayDateInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "configuration")
		).name(
			"configuration"
		).build();
	}

	private InfoFieldSet _getContentInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			BlogsEntryInfoItemFields.subtitleInfoField
		).infoFieldSetEntry(
			BlogsEntryInfoItemFields.coverImageInfoField
		).infoFieldSetEntry(
			BlogsEntryInfoItemFields.coverImageCaptionInfoField
		).infoFieldSetEntry(
			BlogsEntryInfoItemFields.contentInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "content")
		).name(
			"content"
		).build();
	}

	private InfoFieldSet _getDisplayPageInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			BlogsEntryInfoItemFields.displayPageURLInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "configuration")
		).name(
			"configuration"
		).build();
	}

	private InfoForm _getInfoForm(InfoFieldSet assetEntryInfoFieldSet) {
		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();

		InfoLocalizedValue.Builder infoLocalizedValueBuilder =
			InfoLocalizedValue.builder();

		for (Locale locale : availableLocales) {
			infoLocalizedValueBuilder.value(
				locale,
				ResourceActionsUtil.getModelResource(
					locale, BlogsEntry.class.getName()));
		}

		return InfoForm.builder(
		).infoFieldSetEntry(
			_getBasicInformationInfoFieldSet()
		).infoFieldSetEntry(
			_getContentInfoFieldSet()
		).infoFieldSetEntry(
			_expandoInfoItemFieldSetProvider.getInfoFieldSet(
				BlogsEntry.class.getName())
		).infoFieldSetEntry(
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				BlogsEntry.class.getName())
		).infoFieldSetEntry(
			_getDisplayPageInfoFieldSet()
		).infoFieldSetEntry(
			_getConfigurationInfoFieldSet()
		).infoFieldSetEntry(
			assetEntryInfoFieldSet
		).infoFieldSetEntry(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
				BlogsEntry.class.getName())
		).labelInfoLocalizedValue(
			infoLocalizedValueBuilder.build()
		).name(
			BlogsEntry.class.getName()
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