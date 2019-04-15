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

package com.liferay.journal.web.internal.info.display.contributor;

import com.liferay.asset.info.display.contributor.BaseAssetInfoDisplayContributor;
import com.liferay.asset.info.display.contributor.util.ContentAccessor;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.Value;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.util.JournalContent;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.web.asset.JournalArticleDDMFormValuesReader;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = InfoDisplayContributor.class)
public class JournalArticleAssetInfoDisplayContributor
	extends BaseAssetInfoDisplayContributor<JournalArticle> {

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	public List<InfoDisplayField> getClassTypeInfoDisplayFields(
			long classTypeId, Locale locale)
		throws PortalException {

		List<InfoDisplayField> infoDisplayFields =
			super.getClassTypeInfoDisplayFields(classTypeId, locale);

		DDMStructure ddmStructure = ddmStructureLocalService.fetchDDMStructure(
			classTypeId);

		List<DDMTemplate> ddmTemplates = ddmStructure.getTemplates();

		Stream<DDMTemplate> stream = ddmTemplates.stream();

		infoDisplayFields.addAll(
			stream.map(
				ddmTemplate -> new InfoDisplayField(
					_getTemplateKey(ddmTemplate),
					ddmTemplate.getName(locale) + StringPool.SPACE +
						StringPool.STAR,
					"text")
			).collect(
				Collectors.toList()
			));

		return infoDisplayFields;
	}

	@Override
	public String getInfoURLSeparator() {
		return "/w/";
	}

	@Override
	protected Map<String, Object> getClassTypeValues(
		JournalArticle article, Locale locale) {

		Map<String, Object> classTypeValues = new HashMap<>();

		JournalArticleDDMFormValuesReader journalArticleDDMFormValuesReader =
			new JournalArticleDDMFormValuesReader(article);

		journalArticleDDMFormValuesReader.setFieldsToDDMFormValuesConverter(
			fieldsToDDMFormValuesConverter);
		journalArticleDDMFormValuesReader.setJournalConverter(journalConverter);

		try {
			DDMFormValues ddmFormValues =
				journalArticleDDMFormValuesReader.getDDMFormValues();

			Map<String, List<DDMFormFieldValue>> ddmFormFieldsValuesMap =
				ddmFormValues.getDDMFormFieldValuesMap();

			for (Map.Entry<String, List<DDMFormFieldValue>> entry :
					ddmFormFieldsValuesMap.entrySet()) {

				List<DDMFormFieldValue> ddmFormFieldValues = entry.getValue();

				_addDDMFormFieldValues(
					article, entry.getKey(), ddmFormFieldValues,
					classTypeValues, locale);
			}

			DDMStructure ddmStructure = article.getDDMStructure();

			List<DDMTemplate> ddmTemplates = ddmStructure.getTemplates();

			ddmTemplates.forEach(
				ddmTemplate -> classTypeValues.put(
					_getTemplateKey(ddmTemplate),
					new DDMTemplateContentAccessor(
						article, ddmTemplate,
						LocaleUtil.toLanguageId(locale))));
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return classTypeValues;
	}

	@Reference
	protected DDMStructureLocalService ddmStructureLocalService;

	@Reference
	protected DLAppService dlAppService;

	@Reference
	protected DLURLHelper dlURLHelper;

	@Reference
	protected FieldsToDDMFormValuesConverter fieldsToDDMFormValuesConverter;

	@Reference
	protected JournalContent journalContent;

	@Reference
	protected JournalConverter journalConverter;

	private void _addDDMFormFieldValues(
			JournalArticle article, String key,
			List<DDMFormFieldValue> ddmFormFieldValues,
			Map<String, Object> classTypeValues, Locale locale)
		throws PortalException {

		Object fieldValue = null;

		if (ddmFormFieldValues.size() == 1) {
			DDMFormFieldValue ddmFormFieldValue0 = ddmFormFieldValues.get(0);

			Value value = ddmFormFieldValue0.getValue();

			_addNestedFields(
				article, ddmFormFieldValue0, classTypeValues, locale);

			fieldValue = _sanitizeFieldValue(
				article, ddmFormFieldValue0.getType(), value.getString(locale));
		}
		else {
			Stream<DDMFormFieldValue> stream = ddmFormFieldValues.stream();

			fieldValue = stream.map(
				ddmFormFieldValue -> {
					Value value = ddmFormFieldValue.getValue();

					try {
						_addNestedFields(
							article, ddmFormFieldValue, classTypeValues,
							locale);

						return _sanitizeFieldValue(
							article, ddmFormFieldValue.getType(),
							value.getString(locale));
					}
					catch (PortalException pe) {
						_log.error(
							"Unable to sanitize field " +
								ddmFormFieldValue.getName(),
							pe);

						return null;
					}
				}
			).filter(
				value -> value != null
			).collect(
				Collectors.toList()
			);
		}

		classTypeValues.put(key, fieldValue);
	}

	private void _addNestedFields(
			JournalArticle article, DDMFormFieldValue ddmFormFieldValue,
			Map<String, Object> classTypeValues, Locale locale)
		throws PortalException {

		Map<String, List<DDMFormFieldValue>> nestedDDMFormFieldsValuesMap =
			ddmFormFieldValue.getNestedDDMFormFieldValuesMap();

		for (Map.Entry<String, List<DDMFormFieldValue>> entry :
				nestedDDMFormFieldsValuesMap.entrySet()) {

			List<DDMFormFieldValue> ddmFormFieldValues = entry.getValue();

			_addDDMFormFieldValues(
				article, entry.getKey(), ddmFormFieldValues, classTypeValues,
				locale);
		}
	}

	private String _getTemplateKey(DDMTemplate ddmTemplate) {
		String templateKey = ddmTemplate.getTemplateKey();

		return _DDM_TEMPLATE + templateKey.replaceAll("\\W", "_");
	}

	private Object _sanitizeFieldValue(
			JournalArticle article, String type, String value)
		throws SanitizerException {

		if (Objects.equals(type, "ddm-image")) {
			return _transformFileEntryURL(value);
		}

		return SanitizerUtil.sanitize(
			article.getCompanyId(), article.getGroupId(), article.getUserId(),
			JournalArticle.class.getName(), article.getResourcePrimKey(),
			ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL, value, null);
	}

	private String _transformFileEntryURL(String data) {
		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(data);

			String uuid = jsonObject.getString("uuid");
			long groupId = jsonObject.getLong("groupId");

			if (Validator.isNull(uuid) && (groupId == 0)) {
				return StringPool.BLANK;
			}

			FileEntry fileEntry = dlAppService.getFileEntryByUuidAndGroupId(
				uuid, groupId);

			return dlURLHelper.getDownloadURL(
				fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return StringPool.BLANK;
	}

	private static final String _DDM_TEMPLATE = "ddmTemplate_";

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleAssetInfoDisplayContributor.class);

	private class DDMTemplateContentAccessor implements ContentAccessor {

		public DDMTemplateContentAccessor(
			JournalArticle article, DDMTemplate ddmTemplate,
			String languageId) {

			_article = article;
			_ddmTemplate = ddmTemplate;
			_languageId = languageId;
		}

		public String getContent() {
			return journalContent.getContent(
				_article.getGroupId(), _article.getArticleId(),
				_ddmTemplate.getTemplateKey(), Constants.VIEW, _languageId,
				(ThemeDisplay)null);
		}

		private final JournalArticle _article;
		private final DDMTemplate _ddmTemplate;
		private final String _languageId;

	}

}