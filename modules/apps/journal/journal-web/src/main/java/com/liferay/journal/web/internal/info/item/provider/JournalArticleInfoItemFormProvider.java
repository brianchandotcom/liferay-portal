/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.info.item.provider;

import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
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
import com.liferay.info.localized.bundle.ModelResourceLocalizedValue;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.info.item.JournalArticleInfoItemFields;
import com.liferay.layout.page.template.info.item.provider.DisplayPageInfoItemFieldSetProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

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
	property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFormProvider.class
)
public class JournalArticleInfoItemFormProvider
	implements InfoItemFormProvider<JournalArticle> {

	@Override
	public InfoForm getInfoForm() {
		try {
			return _getInfoForm(
				StringPool.BLANK,
				_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
					JournalArticle.class.getName()),
				_displayPageInfoItemFieldSetProvider.getInfoFieldSet(
					JournalArticle.class.getName(), StringPool.BLANK,
					JournalArticle.class.getSimpleName(), 0));
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
				String.valueOf(ddmStructureId),
				_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
					_assetEntryLocalService.getEntry(
						JournalArticle.class.getName(),
						article.getResourcePrimKey())),
				_displayPageInfoItemFieldSetProvider.getInfoFieldSet(
					JournalArticle.class.getName(),
					String.valueOf(ddmStructureId),
					JournalArticle.class.getSimpleName(), 0));
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
	public InfoForm getInfoForm(String formVariationKey, long groupId)
		throws NoSuchFormVariationException {

		return _getInfoForm(
			formVariationKey,
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
				JournalArticle.class.getName(),
				GetterUtil.getLong(formVariationKey), groupId),
			_displayPageInfoItemFieldSetProvider.getInfoFieldSet(
				JournalArticle.class.getName(), formVariationKey,
				JournalArticle.class.getSimpleName(), groupId));
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
			JournalArticleInfoItemFields.displayPageURLInfoField
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
			String formVariationKey, InfoFieldSet assetEntryInfoFieldSet,
			InfoFieldSet displayPageInfoFieldSet)
		throws NoSuchFormVariationException {

		try {
			return InfoForm.builder(
			).infoFieldSetEntry(
				_getBasicInformationInfoFieldSet()
			).<NoSuchStructureException>infoFieldSetEntry(
				unsafeConsumer -> {
					long ddmStructureId = GetterUtil.getLong(formVariationKey);

					if (ddmStructureId != 0) {
						unsafeConsumer.accept(
							_ddmStructureInfoItemFieldSetProvider.
								getInfoItemFieldSet(
									ddmStructureId,
									_getStructureFieldSetNameInfoLocalizedValue(
										ddmStructureId)));

						unsafeConsumer.accept(
							_ddmTemplateInfoItemFieldSetProvider.
								getInfoItemFieldSet(ddmStructureId));
					}
				}
			).infoFieldSetEntry(
				_templateInfoItemFieldSetProvider.getInfoFieldSet(
					JournalArticle.class.getName(), formVariationKey)
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
				_getFeaturedImageInfoFieldSet()
			).infoFieldSetEntry(
				_expandoInfoItemFieldSetProvider.getInfoFieldSet(
					JournalArticle.class.getName())
			).infoFieldSetEntry(
				assetEntryInfoFieldSet
			).infoFieldSetEntry(
				_getScheduleInfoFieldSet()
			).infoFieldSetEntry(
				_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
					JournalArticle.class.getName())
			).labelInfoLocalizedValue(
				new ModelResourceLocalizedValue(JournalArticle.class.getName())
			).name(
				JournalArticle.class.getName()
			).build();
		}
		catch (NoSuchStructureException noSuchStructureException) {
			throw new NoSuchFormVariationException(
				formVariationKey, noSuchStructureException);
		}
	}

	private InfoFieldSet _getScheduleInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			JournalArticleInfoItemFields.displayDateInfoField
		).infoFieldSetEntry(
			JournalArticleInfoItemFields.expirationDateInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "schedule")
		).name(
			"schedule"
		).build();
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
					_language.get(locale, "content"), StringPool.SPACE,
					StringPool.OPEN_PARENTHESIS, name,
					StringPool.CLOSE_PARENTHESIS));

			return InfoLocalizedValue.<String>builder(
			).defaultLocale(
				LocaleUtil.fromLanguageId(ddmStructure.getDefaultLanguageId())
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
	private DisplayPageInfoItemFieldSetProvider
		_displayPageInfoItemFieldSetProvider;

	@Reference
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private Language _language;

	@Reference
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

}