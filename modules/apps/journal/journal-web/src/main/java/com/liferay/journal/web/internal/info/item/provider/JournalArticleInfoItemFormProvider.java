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
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.dynamic.data.mapping.info.display.field.DDMFormValuesInfoDisplayFieldProvider;
import com.liferay.dynamic.data.mapping.info.item.provider.DDMStructureInfoItemFieldSetProvider;
import com.liferay.dynamic.data.mapping.info.item.provider.DDMTemplateInfoItemFieldSetProvider;
import com.liferay.dynamic.data.mapping.kernel.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.InfoForm;
import com.liferay.info.field.InfoFormValues;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.info.item.NoSuchClassTypeException;
import com.liferay.info.item.NoSuchInfoItemException;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.JournalContent;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.util.comparator.ArticleVersionComparator;
import com.liferay.journal.web.internal.asset.JournalArticleDDMFormValuesReader;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

import java.text.Format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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
		InfoForm infoForm = new InfoForm(JournalArticle.class.getName());

		infoForm.addAll(_getJournalArticleFields());

		infoForm.add(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
				JournalArticle.class.getName()));

		infoForm.add(
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
				AssetEntry.class.getName()));

		infoForm.add(
			_expandoInfoItemFieldSetProvider.getInfoFieldSet(
				JournalArticle.class.getName()));

		return infoForm;
	}

	@Override
	public InfoForm getInfoForm(JournalArticle article) {
		DDMStructure ddmStructure = article.getDDMStructure();

		long ddmStructureId = ddmStructure.getStructureId();

		try {
			return getInfoForm(ddmStructureId);
		}
		catch (NoSuchClassTypeException noSuchClassTypeException) {
			throw new RuntimeException(
				"Unable to find structure: " + ddmStructureId,
				noSuchClassTypeException);
		}
	}

	@Override
	public InfoForm getInfoForm(long ddmStructureId)
		throws NoSuchClassTypeException {

		InfoForm infoForm = getInfoForm();

		try {
			infoForm.add(
				_ddmStructureInfoItemFieldSetProvider.getInfoItemFieldSet(
					ddmStructureId));

			infoForm.add(
				_ddmTemplateInfoItemFieldSetProvider.getInfoItemFieldSet(
					ddmStructureId));
		}
		catch (NoSuchStructureException noSuchStructureException) {
			throw new NoSuchClassTypeException(
				ddmStructureId, noSuchStructureException);
		}

		return infoForm;
	}

	@Override
	public InfoFormValues getInfoFormValues(JournalArticle journalArticle) {
		InfoFormValues infoFormValues = new InfoFormValues();

		infoFormValues.addAll(_getJournalArticleFormValues(journalArticle));

		infoFormValues.setInfoItemClassPKReference(
			new InfoItemClassPKReference(
				JournalArticle.class.getName(),
				journalArticle.getResourcePrimKey()));

		try {
			infoFormValues.addAll(
				_assetEntryInfoItemFieldSetProvider.getInfoFieldValues(
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey()));
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			throw new RuntimeException(
				"Caught unexpected exception", noSuchInfoItemException);
		}

		infoFormValues.addAll(
			_expandoInfoItemFieldSetProvider.getInfoFieldValues(
				JournalArticle.class.getName(), journalArticle));

		infoFormValues.addAll(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldValues(
				JournalArticle.class.getName(), journalArticle));

		infoFormValues.addAll(_getDDMStructureInfoFieldValues(journalArticle));

		infoFormValues.addAll(_getDDMTemplateInfoFieldValues(journalArticle));

		return infoFormValues;
	}

	private String _getDateValue(Date date) {
		if (date == null) {
			return StringPool.BLANK;
		}

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
			locale);

		return dateFormatDateTime.format(date);
	}

	private List<InfoFieldValue<Object>> _getDDMStructureInfoFieldValues(
		JournalArticle article) {

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		JournalArticleDDMFormValuesReader journalArticleDDMFormValuesReader =
			new JournalArticleDDMFormValuesReader(article);

		journalArticleDDMFormValuesReader.setFieldsToDDMFormValuesConverter(
			_fieldsToDDMFormValuesConverter);
		journalArticleDDMFormValuesReader.setJournalConverter(
			_journalConverter);

		try {
			Map<String, Object> infoDisplayFieldsValues =
				_ddmFormValuesInfoDisplayFieldProvider.
					getInfoDisplayFieldsValues(
						article,
						journalArticleDDMFormValuesReader.getDDMFormValues(),
						locale);

			for (Map.Entry<String, Object> entry :
					infoDisplayFieldsValues.entrySet()) {

				String fieldName = entry.getKey();

				InfoField infoField = new InfoField(
					TextInfoFieldType.INSTANCE,
					InfoLocalizedValue.localize(getClass(), fieldName),
					fieldName);

				infoFieldValues.add(
					new InfoFieldValue<>(infoField, entry.getValue()));
			}
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}

		return infoFieldValues;
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

				InfoField infoField = new InfoField(
					TextInfoFieldType.INSTANCE,
					InfoLocalizedValue.localize(getClass(), fieldName),
					fieldName);

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
								"Error rendering template" +
									ddmTemplate.getTemplateId(),
								exception);
						}
					});

				infoFieldValues.add(infoFieldValue);
			});

		return infoFieldValues;
	}

	private String _getDisplayPageURL(JournalArticle journalArticle)
		throws PortalException {

		return _assetDisplayPageFriendlyURLProvider.getFriendlyURL(
			JournalArticle.class.getName(), journalArticle.getResourcePrimKey(),
			_getThemeDisplay());
	}

	private JSONObject _getImageJSONObject(String alt, String url) {
		JSONObject jsonObject = JSONUtil.put("url", url);

		if (alt != null) {
			jsonObject = jsonObject.put("alt", alt);
		}

		return jsonObject;
	}

	private Collection<InfoFieldSetEntry> _getJournalArticleFields() {
		return Arrays.asList(
			_titleInfoField, _descriptionInfoField, _smallImageInfoField,
			_authorNameInfoField, _authorProfileImageInfoField,
			_lastEditorNameInfoField, _lastEditorProfileImageInfoField,
			_publishDateInfoField, _displayPageUrlInfoField);
	}

	private List<InfoFieldValue<Object>> _getJournalArticleFormValues(
		JournalArticle journalArticle) {

		ThemeDisplay themeDisplay = _getThemeDisplay();

		try {
			List<InfoFieldValue<Object>> journalArticleFieldValues =
				new ArrayList<>();

			journalArticleFieldValues.add(
				new InfoFieldValue<>(
					_titleInfoField, journalArticle.getTitle()));

			journalArticleFieldValues.add(
				new InfoFieldValue<>(
					_descriptionInfoField, journalArticle.getDescription()));

			journalArticleFieldValues.add(
				new InfoFieldValue<>(
					_smallImageInfoField,
					_getImageJSONObject(
						null,
						journalArticle.getArticleImageURL(themeDisplay))));

			User user = _getLastVersionUser(journalArticle);

			if (user != null) {
				journalArticleFieldValues.add(
					new InfoFieldValue<>(
						_authorNameInfoField, user.getFullName()));

				journalArticleFieldValues.add(
					new InfoFieldValue<>(
						_authorProfileImageInfoField,
						_getImageJSONObject(
							user.getFullName(),
							user.getPortraitURL(themeDisplay))));
			}

			User lastEditorUser = _userLocalService.fetchUser(
				journalArticle.getUserId());

			if (lastEditorUser != null) {
				journalArticleFieldValues.add(
					new InfoFieldValue<>(
						_lastEditorNameInfoField,
						lastEditorUser.getFullName()));

				journalArticleFieldValues.add(
					new InfoFieldValue<>(
						_lastEditorProfileImageInfoField,
						_getImageJSONObject(
							lastEditorUser.getFullName(),
							lastEditorUser.getPortraitURL(themeDisplay))));
			}

			journalArticleFieldValues.add(
				new InfoFieldValue<>(
					_publishDateInfoField,
					_getDateValue(journalArticle.getDisplayDate())));

			journalArticleFieldValues.add(
				new InfoFieldValue<>(
					_displayPageUrlInfoField,
					_getDisplayPageURL(journalArticle)));

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

	private final InfoField _authorNameInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "author-name"), "authorName");
	private final InfoField _authorProfileImageInfoField = new InfoField(
		ImageInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			"com.liferay.journal.lang", "author-profile-image"),
		"authorProfileImage");

	@Reference
	private DDMFormValuesInfoDisplayFieldProvider
		_ddmFormValuesInfoDisplayFieldProvider;

	@Reference
	private DDMStructureInfoItemFieldSetProvider
		_ddmStructureInfoItemFieldSetProvider;

	@Reference
	private DDMTemplateInfoItemFieldSetProvider
		_ddmTemplateInfoItemFieldSetProvider;

	private final InfoField _descriptionInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "description"), "description");
	private final InfoField _displayPageUrlInfoField = new InfoField(
		URLInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			"com.liferay.asset.info.display.impl", "display-page-url"),
		"displayPageURL");

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

	private final InfoField _lastEditorNameInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			"com.liferay.journal.lang", "last-editor-name"),
		"lastEditorName");
	private final InfoField _lastEditorProfileImageInfoField = new InfoField(
		ImageInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "last-editor-profile-image"),
		"lastEditorProfileImage");
	private final InfoField _publishDateInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "publish-date"), "publishDate");
	private final InfoField _smallImageInfoField = new InfoField(
		ImageInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize("com.liferay.journal.lang", "small-image"),
		"smallImage");
	private final InfoField _titleInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "title"), "title");

	@Reference
	private UserLocalService _userLocalService;

}