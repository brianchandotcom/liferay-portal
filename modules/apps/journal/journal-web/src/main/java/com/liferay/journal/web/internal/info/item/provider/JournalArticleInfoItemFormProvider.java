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

package com.liferay.journal.web.internal.info.item.provider;

import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.info.item.provider.DDMStructureInfoItemFieldSetProvider;
import com.liferay.dynamic.data.mapping.info.item.provider.DDMTemplateInfoItemFieldSetProvider;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.info.exception.NoSuchClassTypeException;
import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.info.item.JournalArticleInfoItemFields;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(
	immediate = true, property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFormProvider.class
)
public class JournalArticleInfoItemFormProvider
	implements InfoItemFormProvider<JournalArticle> {

	@Override
	public InfoForm getInfoForm() {
		try {
			return _getInfoForm(
				0,
				_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
					JournalArticle.class.getName()));
		}
		catch (NoSuchFormVariationException noSuchFormVariationException) {
			throw new RuntimeException(noSuchFormVariationException);
		}
	}

	@Override
	public InfoForm getInfoForm(JournalArticle article) {
		DDMStructure ddmStructure = article.getDDMStructure();

		long ddmStructureId = ddmStructure.getStructureId();

		try {
			return _getInfoForm(
				ddmStructureId,
				_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
					_assetEntryLocalService.getEntry(
						JournalArticle.class.getName(),
						article.getResourcePrimKey())));
		}
		catch (NoSuchClassTypeException noSuchClassTypeException) {
			throw new RuntimeException(
				"Unable to get dynamic data mapping structure " +
					ddmStructureId,
				noSuchClassTypeException);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"Unable to get asset entry for journal article " +
					article.getResourcePrimKey(),
				portalException);
		}
	}

	@Override
	public InfoForm getInfoForm(String formVariationKey)
		throws NoSuchFormVariationException {

		return _getInfoForm(
			GetterUtil.getLong(formVariationKey),
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
				AssetEntry.class.getName()));
	}

	@Override
	public InfoForm getInfoForm(String formVariationKey, long groupId)
		throws NoSuchFormVariationException {

		return _getInfoForm(
			GetterUtil.getLong(formVariationKey),
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
				JournalArticle.class.getName(),
				GetterUtil.getLong(formVariationKey), groupId));
	}

	private InfoFieldSet _getBasicInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			JournalArticleInfoItemFields.titleInfoField
		).infoFieldSetEntry(
			JournalArticleInfoItemFields.descriptionInfoField
		).infoFieldSetEntry(
			JournalArticleInfoItemFields.publishDateInfoField
		).infoFieldSetEntry(
			JournalArticleInfoItemFields.authorNameInfoField
		).infoFieldSetEntry(
			JournalArticleInfoItemFields.authorProfileImageInfoField
		).infoFieldSetEntry(
			JournalArticleInfoItemFields.lastEditorNameInfoField
		).infoFieldSetEntry(
			JournalArticleInfoItemFields.lastEditorProfileImageInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "basic-information")
		).name(
			"basic-information"
		).build();
	}

	private InfoFieldSet _getDisplayPageInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			JournalArticleInfoItemFields.displayPageUrlInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "display-page")
		).name(
			"display-page"
		).build();
	}

	private InfoFieldSet _getFeaturedImageInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			JournalArticleInfoItemFields.smallImageInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "featured-image")
		).name(
			"featured-image"
		).build();
	}

	private InfoForm _getInfoForm(
			long ddmStructureId, InfoFieldSet assetEntryInfoFieldSet)
		throws NoSuchFormVariationException {

		try {
			return InfoForm.builder(
			).infoFieldSetEntry(
				_getBasicInformationInfoFieldSet()
			).<NoSuchStructureException>infoFieldSetEntry(
				consumer -> {
					if (ddmStructureId != 0) {
						consumer.accept(
							_ddmStructureInfoItemFieldSetProvider.
								getInfoItemFieldSet(
									ddmStructureId,
									_getStructureFieldSetNameInfoLocalizedValue(
										ddmStructureId)));

						consumer.accept(
							_ddmTemplateInfoItemFieldSetProvider.
								getInfoItemFieldSet(ddmStructureId));
					}
				}
			).infoFieldSetEntry(
				_getDisplayPageInfoFieldSet()
			).infoFieldSetEntry(
				_getFeaturedImageInfoFieldSet()
			).infoFieldSetEntry(
				_expandoInfoItemFieldSetProvider.getInfoFieldSet(
					JournalArticle.class.getName())
			).infoFieldSetEntry(
				assetEntryInfoFieldSet
			).infoFieldSetEntry(
				_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
					JournalArticle.class.getName())
			).name(
				JournalArticle.class.getName()
			).build();
		}
		catch (NoSuchStructureException noSuchStructureException) {
			throw new NoSuchFormVariationException(
				String.valueOf(ddmStructureId), noSuchStructureException);
		}
	}

	private InfoLocalizedValue<String>
			_getStructureFieldSetNameInfoLocalizedValue(long ddmStructureId)
		throws NoSuchStructureException {

		try {
			DDMStructure ddmStructure =
				_ddmStructureLocalService.getDDMStructure(ddmStructureId);

			Map<Locale, String> nameMap = new HashMap<>(
				ddmStructure.getNameMap());

			nameMap.replaceAll(
				(locale, name) -> StringBundler.concat(
					LanguageUtil.get(locale, "content"), StringPool.SPACE,
					StringPool.OPEN_PARENTHESIS, name,
					StringPool.CLOSE_PARENTHESIS));

			return InfoLocalizedValue.<String>builder(
			).values(
				nameMap
			).build();
		}
		catch (NoSuchStructureException noSuchStructureException) {
			throw noSuchStructureException;
		}
		catch (PortalException portalException) {
			throw new RuntimeException("Unexpected exception", portalException);
		}
	}

	@Reference
	private AssetEntryInfoItemFieldSetProvider
		_assetEntryInfoItemFieldSetProvider;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private DDMStructureInfoItemFieldSetProvider
		_ddmStructureInfoItemFieldSetProvider;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMTemplateInfoItemFieldSetProvider
		_ddmTemplateInfoItemFieldSetProvider;

	@Reference
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

}