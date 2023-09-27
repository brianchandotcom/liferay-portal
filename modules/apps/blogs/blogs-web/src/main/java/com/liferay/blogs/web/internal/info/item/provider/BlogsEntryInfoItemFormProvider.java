/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.info.localized.bundle.ModelResourceLocalizedValue;
import com.liferay.layout.page.template.info.item.provider.DisplayPageInfoItemFieldSetProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 * @author Jorge Ferrer
 */
@Component(
	property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFormProvider.class
)
public class BlogsEntryInfoItemFormProvider
	implements InfoItemFormProvider<BlogsEntry> {

	@Override
	public InfoForm getInfoForm() {
		return _getInfoForm(
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
				BlogsEntry.class.getName()),
			_displayPageInfoItemFieldSetProvider.getInfoFieldSet(
				BlogsEntry.class.getName(), StringPool.BLANK,
				BlogsEntry.class.getSimpleName(), 0));
	}

	@Override
	public InfoForm getInfoForm(BlogsEntry blogsEntry) {
		try {
			return _getInfoForm(
				_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
					_assetEntryLocalService.getEntry(
						BlogsEntry.class.getName(), blogsEntry.getEntryId())),
				_displayPageInfoItemFieldSetProvider.getInfoFieldSet(
					BlogsEntry.class.getName(), StringPool.BLANK,
					BlogsEntry.class.getSimpleName(), 0));
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
				BlogsEntry.class.getName(), 0, groupId),
			_displayPageInfoItemFieldSetProvider.getInfoFieldSet(
				BlogsEntry.class.getName(), StringPool.BLANK,
				BlogsEntry.class.getSimpleName(), groupId));
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

	private InfoForm _getInfoForm(
		InfoFieldSet assetEntryInfoFieldSet,
		InfoFieldSet displayPageInfoFieldSet) {

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
			unsafeConsumer -> {
				if (!FeatureFlagManagerUtil.isEnabled("LPS-195205")) {
					unsafeConsumer.accept(_getDisplayPageInfoFieldSet());
				}
			}
		).infoFieldSetEntry(
			unsafeConsumer -> {
				if (FeatureFlagManagerUtil.isEnabled("LPS-195205")) {
					unsafeConsumer.accept(displayPageInfoFieldSet);
				}
			}
		).infoFieldSetEntry(
			_getConfigurationInfoFieldSet()
		).infoFieldSetEntry(
			assetEntryInfoFieldSet
		).infoFieldSetEntry(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
				BlogsEntry.class.getName())
		).labelInfoLocalizedValue(
			new ModelResourceLocalizedValue(BlogsEntry.class.getName())
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
	private DisplayPageInfoItemFieldSetProvider
		_displayPageInfoItemFieldSetProvider;

	@Reference
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

}