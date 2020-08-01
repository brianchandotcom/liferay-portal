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

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.type.WebImage;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.JournalContent;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.util.comparator.ArticleVersionComparator;
import com.liferay.journal.web.internal.asset.JournalArticleDDMFormValuesReader;
import com.liferay.journal.web.internal.info.item.JournalArticleInfoItemFields;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(
	immediate = true, property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFieldValuesProvider.class
)
public class JournalArticleInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<JournalArticle> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(
		JournalArticle journalArticle) {

		try {
			return InfoItemFieldValues.builder(
			).infoFieldValues(
				_getJournalArticleInfoFieldValues(journalArticle)
			).infoFieldValues(
				_assetEntryInfoItemFieldSetProvider.getInfoFieldValues(
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey())
			).infoFieldValues(
				_expandoInfoItemFieldSetProvider.getInfoFieldValues(
					JournalArticle.class.getName(), journalArticle)
			).infoFieldValues(
				_infoItemFieldReaderFieldSetProvider.getInfoFieldValues(
					JournalArticle.class.getName(), journalArticle)
			).infoFieldValues(
				_getDDMStructureInfoFieldValues(journalArticle)
			).infoFieldValues(
				_getDDMTemplateInfoFieldValues(journalArticle)
			).infoItemReference(
				new InfoItemReference(
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey())
			).build();
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			throw new RuntimeException(
				"Caught unexpected exception", noSuchInfoItemException);
		}
	}

	private List<InfoFieldValue<Object>> _getDDMStructureInfoFieldValues(
		JournalArticle article) {

		JournalArticleDDMFormValuesReader journalArticleDDMFormValuesReader =
			new JournalArticleDDMFormValuesReader(article);

		journalArticleDDMFormValuesReader.setFieldsToDDMFormValuesConverter(
			_fieldsToDDMFormValuesConverter);
		journalArticleDDMFormValuesReader.setJournalConverter(
			_journalConverter);

		try {
			return _ddmFormValuesInfoFieldValuesProvider.getInfoFieldValues(
				article, journalArticleDDMFormValuesReader.getDDMFormValues());
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private List<InfoFieldValue<Object>> _getDDMTemplateInfoFieldValues(
		JournalArticle journalArticle) {

		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		List<DDMTemplate> ddmTemplates = ddmStructure.getTemplates();

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		String languageId = LocaleUtil.toLanguageId(locale);

		ddmTemplates.forEach(
			ddmTemplate -> {
				String fieldName = _getTemplateKey(ddmTemplate);

				InfoField infoField = InfoField.builder(
				).infoFieldType(
					TextInfoFieldType.INSTANCE
				).name(
					fieldName
				).labelInfoLocalizedValue(
					InfoLocalizedValue.localize(getClass(), fieldName)
				).build();

				InfoFieldValue<Object> infoFieldValue = new InfoFieldValue<>(
					infoField,
					() -> {
						JournalArticleDisplay journalArticleDisplay =
							_journalContent.getDisplay(
								journalArticle, ddmTemplate.getTemplateKey(),
								com.liferay.portal.kernel.util.Constants.VIEW,
								languageId, 1, null, _getThemeDisplay());

						if (journalArticleDisplay != null) {
							return journalArticleDisplay.getContent();
						}

						try {
							journalArticleDisplay =
								_journalArticleLocalService.getArticleDisplay(
									journalArticle,
									ddmTemplate.getTemplateKey(), null,
									languageId, 1, null, _getThemeDisplay());

							return journalArticleDisplay.getContent();
						}
						catch (Exception exception) {
							throw new RuntimeException(
								"Unable to render dynamic data mapping " +
									"template" + ddmTemplate.getTemplateId(),
								exception);
						}
					});

				infoFieldValues.add(infoFieldValue);
			});

		return infoFieldValues;
	}

	private String _getDisplayPageURL(
			JournalArticle journalArticle, ThemeDisplay themeDisplay)
		throws PortalException {

		return _assetDisplayPageFriendlyURLProvider.getFriendlyURL(
			JournalArticle.class.getName(), journalArticle.getResourcePrimKey(),
			themeDisplay);
	}

	private List<InfoFieldValue<Object>> _getJournalArticleInfoFieldValues(
		JournalArticle journalArticle) {

		ThemeDisplay themeDisplay = _getThemeDisplay();

		try {
			List<InfoFieldValue<Object>> journalArticleFieldValues =
				new ArrayList<>();

			journalArticleFieldValues.add(
				new InfoFieldValue<>(
					JournalArticleInfoItemFields.titleInfoField,
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							journalArticle.getDefaultLanguageId())
					).values(
						journalArticle.getTitleMap()
					).build()));
			journalArticleFieldValues.add(
				new InfoFieldValue<>(
					JournalArticleInfoItemFields.descriptionInfoField,
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							journalArticle.getDefaultLanguageId())
					).values(
						journalArticle.getDescriptionMap()
					).build()));

			if (themeDisplay != null) {
				String articleImageURL = journalArticle.getArticleImageURL(
					themeDisplay);

				if (Validator.isNotNull(articleImageURL)) {
					journalArticleFieldValues.add(
						new InfoFieldValue<>(
							JournalArticleInfoItemFields.smallImageInfoField,
							new WebImage(articleImageURL)));
				}
			}

			User user = _getLastVersionUser(journalArticle);

			if (user != null) {
				journalArticleFieldValues.add(
					new InfoFieldValue<>(
						JournalArticleInfoItemFields.authorNameInfoField,
						user.getFullName()));

				if (themeDisplay != null) {
					WebImage webImage = new WebImage(
						user.getPortraitURL(themeDisplay));

					webImage.setAlt(user.getFullName());

					journalArticleFieldValues.add(
						new InfoFieldValue<>(
							JournalArticleInfoItemFields.
								authorProfileImageInfoField,
							webImage));
				}
			}

			User lastEditorUser = _userLocalService.fetchUser(
				journalArticle.getUserId());

			if (lastEditorUser != null) {
				journalArticleFieldValues.add(
					new InfoFieldValue<>(
						JournalArticleInfoItemFields.lastEditorNameInfoField,
						lastEditorUser.getFullName()));

				if (themeDisplay != null) {
					WebImage webImage = new WebImage(
						lastEditorUser.getPortraitURL(themeDisplay));

					webImage.setAlt(lastEditorUser.getFullName());

					journalArticleFieldValues.add(
						new InfoFieldValue<>(
							JournalArticleInfoItemFields.
								lastEditorProfileImageInfoField,
							webImage));
				}
			}

			journalArticleFieldValues.add(
				new InfoFieldValue<>(
					JournalArticleInfoItemFields.publishDateInfoField,
					journalArticle.getDisplayDate()));

			if (themeDisplay != null) {
				journalArticleFieldValues.add(
					new InfoFieldValue<>(
						JournalArticleInfoItemFields.displayPageUrlInfoField,
						_getDisplayPageURL(journalArticle, themeDisplay)));
			}

			return journalArticleFieldValues;
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private User _getLastVersionUser(JournalArticle journalArticle) {
		List<JournalArticle> articles = _journalArticleLocalService.getArticles(
			journalArticle.getGroupId(), journalArticle.getArticleId(), 0, 1,
			new ArticleVersionComparator(true));

		journalArticle = articles.get(0);

		return _userLocalService.fetchUser(journalArticle.getUserId());
	}

	private String _getTemplateKey(DDMTemplate ddmTemplate) {
		String templateKey = ddmTemplate.getTemplateKey();

		return PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
			templateKey.replaceAll("\\W", "_");
	}

	private ThemeDisplay _getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getThemeDisplay();
		}

		return null;
	}

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private AssetEntryInfoItemFieldSetProvider
		_assetEntryInfoItemFieldSetProvider;

	@Reference
	private DDMFormValuesInfoFieldValuesProvider
		_ddmFormValuesInfoFieldValuesProvider;

	@Reference
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalContent _journalContent;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private UserLocalService _userLocalService;

}