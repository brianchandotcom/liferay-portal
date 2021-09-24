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

package com.liferay.translation.internal.info.item.updater;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.storage.constants.FieldConstants;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.updater.InfoItemFieldValuesUpdater;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	property = "item.class.name=com.liferay.journal.model.JournalArticle",
	service = InfoItemFieldValuesUpdater.class
)
public class JournalArticleInfoItemFieldValuesUpdaterImpl
	implements InfoItemFieldValuesUpdater<JournalArticle> {

	@Override
	public JournalArticle updateFromInfoItemFieldValues(
			JournalArticle journalArticle,
			InfoItemFieldValues infoItemFieldValues)
		throws Exception {

		Map<Locale, String> importedLocaleTitleMap = new HashMap<>();
		Map<Locale, String> importedLocaleDescriptionMap = new HashMap<>();
		Map<Locale, Map<String, String>> importedLocaleContentMap =
			new HashMap<>();
		Set<Locale> translatedLocales = new HashSet<>();
		Map<String, String> fieldNameContentMap = new HashMap<>();

		for (InfoFieldValue<Object> infoFieldValue :
				infoItemFieldValues.getInfoFieldValues()) {

			_getInfoLocalizedValueOptional(
				infoFieldValue
			).ifPresent(
				infoLocalizedValue -> {
					InfoField infoField = infoFieldValue.getInfoField();

					for (Locale locale :
							infoLocalizedValue.getAvailableLocales()) {

						if ((infoFieldValue.getValue(locale) != null) &&
							(infoFieldValue.getValue(locale) instanceof
								String)) {

							translatedLocales.add(locale);

							String fieldName = infoField.getName();

							String valueString = String.valueOf(
								infoFieldValue.getValue(locale));

							if (Objects.equals("description", fieldName)) {
								importedLocaleDescriptionMap.put(
									locale, valueString);
							}
							else if (Objects.equals("title", fieldName)) {
								importedLocaleTitleMap.put(locale, valueString);
							}
							else {
								fieldNameContentMap.put(fieldName, valueString);

								importedLocaleContentMap.put(
									locale, fieldNameContentMap);
							}
						}
					}
				}
			);
		}

		JournalArticle latestArticle =
			_journalArticleLocalService.getLatestArticle(
				journalArticle.getGroupId(), journalArticle.getArticleId(),
				WorkflowConstants.STATUS_ANY);

		Map<Locale, String> titleMap = latestArticle.getTitleMap();
		Map<Locale, String> descriptionMap = latestArticle.getDescriptionMap();
		String translatedContent = latestArticle.getContent();

		for (Locale targetLocale : translatedLocales) {
			titleMap.put(
				targetLocale,
				_getTranslatedString(
					latestArticle.getTitle(targetLocale),
					latestArticle.getTitle(),
					importedLocaleTitleMap.get(targetLocale)));
			descriptionMap.put(
				targetLocale,
				_getTranslatedString(
					latestArticle.getDescription(targetLocale),
					latestArticle.getDescription(),
					importedLocaleDescriptionMap.get(targetLocale)));
			translatedContent = _getTranslatedContent(
				translatedContent, latestArticle.getDDMStructure(),
				importedLocaleContentMap, targetLocale);
		}

		User user = _userLocalService.getUser(latestArticle.getUserId());

		int[] displayDateArray = _getDateArray(
			user, latestArticle.getDisplayDate());
		int[] expirationDateArray = _getDateArray(
			user, latestArticle.getExpirationDate());
		int[] reviewDateArray = _getDateArray(
			user, latestArticle.getReviewDate());

		ServiceContext serviceContext = _getServiceContext(latestArticle);

		return _journalArticleLocalService.updateArticle(
			latestArticle.getUserId(), latestArticle.getGroupId(),
			latestArticle.getFolderId(), latestArticle.getArticleId(),
			latestArticle.getVersion(), titleMap, descriptionMap,
			latestArticle.getFriendlyURLMap(), translatedContent,
			latestArticle.getDDMStructureKey(),
			latestArticle.getDDMTemplateKey(), latestArticle.getLayoutUuid(),
			displayDateArray[0], displayDateArray[1], displayDateArray[2],
			displayDateArray[3], displayDateArray[4], expirationDateArray[0],
			expirationDateArray[1], expirationDateArray[2],
			expirationDateArray[3], expirationDateArray[4],
			_isNeverExpire(latestArticle), reviewDateArray[0],
			reviewDateArray[1], reviewDateArray[2], reviewDateArray[3],
			reviewDateArray[4], _isNeverReview(latestArticle),
			latestArticle.isIndexable(), latestArticle.isSmallImage(),
			latestArticle.getSmallImageURL(), null, null, null, serviceContext);
	}

	private void _addNewTranslatedDDMField(
			DDMStructure ddmStructure, Locale targetLocale, String ddmFieldName,
			Fields ddmFields, String ddmFieldValue)
		throws Exception {

		Field ddmField = new Field(
			ddmStructure.getStructureId(), ddmFieldName,
			Collections.emptyList(), ddmFields.getDefaultLocale());

		ddmField.setValue(
			targetLocale,
			FieldConstants.getSerializable(
				targetLocale, targetLocale,
				ddmStructure.getFieldType(ddmFieldName), ddmFieldValue));

		ddmFields.put(ddmField);

		_updateFieldsDisplay(ddmFields, ddmFieldName);
	}

	private AssetEntry _getAssetLinkEntry(
		long assetEntryId, AssetLink assetLink) {

		try {
			if ((assetEntryId > 0) ||
				(assetLink.getEntryId1() == assetEntryId)) {

				return _assetEntryLocalService.getEntry(
					assetLink.getEntryId2());
			}

			return _assetEntryLocalService.getEntry(assetLink.getEntryId1());
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	private long _getAssetLinkEntryId(long assetEntryId, AssetLink assetLink) {
		AssetEntry assetEntry = _getAssetLinkEntry(assetEntryId, assetLink);

		return assetEntry.getEntryId();
	}

	private int[] _getDateArray(User user, Date date) {
		if (date == null) {
			return new int[] {0, 0, 0, 0, 0};
		}

		int[] dateArray = new int[5];

		Calendar calendar = CalendarFactoryUtil.getCalendar(user.getTimeZone());

		calendar.setTime(date);

		dateArray[0] = calendar.get(Calendar.MONTH);
		dateArray[1] = calendar.get(Calendar.DATE);
		dateArray[2] = calendar.get(Calendar.YEAR);
		dateArray[3] = calendar.get(Calendar.HOUR);
		dateArray[4] = calendar.get(Calendar.MINUTE);

		if (calendar.get(Calendar.AM_PM) == Calendar.PM) {
			dateArray[3] += 12;
		}

		return dateArray;
	}

	private Optional<InfoLocalizedValue<Object>> _getInfoLocalizedValueOptional(
		InfoFieldValue<Object> infoFieldValue) {

		Object value = infoFieldValue.getValue();

		if (value instanceof InfoLocalizedValue) {
			return Optional.of((InfoLocalizedValue)value);
		}

		return Optional.empty();
	}

	private ServiceContext _getServiceContext(JournalArticle journalArticle)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		serviceContext.setAssetCategoryIds(assetEntry.getCategoryIds());

		List<AssetLink> assetLinks = _assetLinkLocalService.getDirectLinks(
			assetEntry.getEntryId(), false);

		serviceContext.setAssetLinkEntryIds(
			ListUtil.toLongArray(
				assetLinks,
				assetLink -> _getAssetLinkEntryId(
					assetEntry.getEntryId(), assetLink)));

		serviceContext.setAssetPriority(assetEntry.getPriority());
		serviceContext.setAssetTagNames(assetEntry.getTagNames());

		ExpandoBridge expandoBridge = journalArticle.getExpandoBridge();

		serviceContext.setExpandoBridgeAttributes(
			expandoBridge.getAttributes());

		serviceContext.setFormDate(new Date());
		serviceContext.setScopeGroupId(journalArticle.getGroupId());

		if (journalArticle.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}
		else {
			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
		}

		return serviceContext;
	}

	private String _getTranslatedContent(
			String content, DDMStructure ddmStructure,
			Map<Locale, Map<String, String>> importedLocaleContentMap,
			Locale targetLocale)
		throws Exception {

		Map<String, String> contentFieldMap = importedLocaleContentMap.get(
			targetLocale);

		if ((contentFieldMap == null) || contentFieldMap.isEmpty()) {
			return content;
		}

		Fields ddmFields = _journalConverter.getDDMFields(
			ddmStructure, content);

		for (Map.Entry<String, String> entry : contentFieldMap.entrySet()) {
			Field field = ddmFields.get(entry.getKey());

			if (field != null) {
				field.setValue(
					targetLocale,
					FieldConstants.getSerializable(
						targetLocale, targetLocale, field.getDataType(),
						entry.getValue()));
			}
			else if (ddmStructure.hasField(entry.getKey())) {
				_addNewTranslatedDDMField(
					ddmStructure, targetLocale, entry.getKey(), ddmFields,
					entry.getValue());
			}
		}

		return _journalConverter.getContent(
			ddmStructure, ddmFields, ddmStructure.getGroupId());
	}

	private String _getTranslatedString(
		String currentString, String defaultString, String importedString) {

		if (importedString != null) {
			return importedString;
		}

		if (Validator.isNotNull(currentString)) {
			return currentString;
		}

		return defaultString;
	}

	private boolean _isNeverExpire(JournalArticle journalArticle) {
		if (journalArticle.getExpirationDate() == null) {
			return true;
		}

		return false;
	}

	private boolean _isNeverReview(JournalArticle journalArticle) {
		if (journalArticle.getReviewDate() == null) {
			return true;
		}

		return false;
	}

	private void _updateFieldsDisplay(Fields ddmFields, String fieldName) {
		String fieldsDisplayValue = StringBundler.concat(
			fieldName, DDM.INSTANCE_SEPARATOR, StringUtil.randomString());

		Field fieldsDisplayField = ddmFields.get(DDM.FIELDS_DISPLAY_NAME);

		String[] fieldsDisplayValues = StringUtil.split(
			(String)fieldsDisplayField.getValue());

		fieldsDisplayValues = ArrayUtil.append(
			fieldsDisplayValues, fieldsDisplayValue);

		fieldsDisplayField.setValue(StringUtil.merge(fieldsDisplayValues));
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetLinkLocalService _assetLinkLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private UserLocalService _userLocalService;

}