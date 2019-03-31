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

package com.liferay.journal.web.internal.asset.display.contributor;

import com.liferay.asset.display.contributor.AssetDisplayContributor;
import com.liferay.asset.display.contributor.AssetDisplayField;
import com.liferay.asset.display.contributor.BaseAssetDisplayContributor;
import com.liferay.asset.display.contributor.util.ContentAccessor;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.Value;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
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
@Component(immediate = true, service = AssetDisplayContributor.class)
public class JournalArticleAssetDisplayContributor
	extends BaseAssetDisplayContributor<JournalArticle> {

	@Override
	public String getAssetURLSeparator() {
		return "/wc/";
	}

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	public List<AssetDisplayField> getClassTypeFields(
			long classTypeId, Locale locale)
		throws PortalException {

		List<AssetDisplayField> assetDisplayFields = super.getClassTypeFields(
			classTypeId, locale);

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchDDMStructure(
			classTypeId);

		List<DDMTemplate> ddmTemplates = ddmStructure.getTemplates();

		Stream<DDMTemplate> stream = ddmTemplates.stream();

		assetDisplayFields.addAll(
			stream.map(
				ddmTemplate -> new AssetDisplayField(
					_getTemplateKey(ddmTemplate),
					ddmTemplate.getName(locale) + StringPool.SPACE +
						StringPool.STAR,
					"text")
			).collect(
				Collectors.toList()
			));

		return assetDisplayFields;
	}

	@Override
	protected Map<String, Object> getClassTypeValues(
		JournalArticle article, Locale locale) {

		Map<String, Object> classTypeValues = new HashMap<>();

		JournalArticleDDMFormValuesReader journalArticleDDMFormValuesReader =
			new JournalArticleDDMFormValuesReader(article);

		journalArticleDDMFormValuesReader.setFieldsToDDMFormValuesConverter(
			_fieldsToDDMFormValuesConverter);
		journalArticleDDMFormValuesReader.setJournalConverter(
			_journalConverter);

		try {
			DDMFormValues ddmFormValues =
				journalArticleDDMFormValuesReader.getDDMFormValues();

			Map<String, List<DDMFormFieldValue>> ddmFormFieldsValuesMap =
				ddmFormValues.getDDMFormFieldValuesMap();

			for (Map.Entry<String, List<DDMFormFieldValue>> entry :
					ddmFormFieldsValuesMap.entrySet()) {

				List<DDMFormFieldValue> ddmFormFieldValues = entry.getValue();

				DDMFormFieldValue ddmFormFieldValue0 = ddmFormFieldValues.get(
					0);

				Value value = ddmFormFieldValue0.getValue();

				String fieldValue = value.getString(locale);

				if (Objects.equals(ddmFormFieldValue0.getType(), "ddm-image")) {
					fieldValue = _transformFileEntryURL(fieldValue);
				}
				else {
					fieldValue = SanitizerUtil.sanitize(
						article.getCompanyId(), article.getGroupId(),
						article.getUserId(), JournalArticle.class.getName(),
						article.getResourcePrimKey(), ContentTypes.TEXT_HTML,
						Sanitizer.MODE_ALL, fieldValue, null);
				}

				classTypeValues.put(entry.getKey(), fieldValue);
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

	private String _getTemplateKey(DDMTemplate ddmTemplate) {
		String templateKey = ddmTemplate.getTemplateKey();

		return _DDM_TEMPLATE + templateKey.replaceAll("\\W]", "_");
	}

	private String _transformFileEntryURL(String data) {
		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(data);

			String uuid = jsonObject.getString("uuid");
			long groupId = jsonObject.getLong("groupId");

			if (Validator.isNull(uuid) && (groupId == 0)) {
				return StringPool.BLANK;
			}

			FileEntry fileEntry = _dlAppService.getFileEntryByUuidAndGroupId(
				uuid, groupId);

			return _dlurlHelper.getDownloadURL(
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
		JournalArticleAssetDisplayContributor.class);

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlurlHelper;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private JournalContent _journalContent;

	@Reference
	private JournalConverter _journalConverter;

	private class DDMTemplateContentAccessor implements ContentAccessor {

		public DDMTemplateContentAccessor(
			JournalArticle article, DDMTemplate ddmTemplate,
			String languageId) {

			_article = article;
			_ddmTemplate = ddmTemplate;
			_languageId = languageId;
		}

		public String getContent() {
			return _journalContent.getContent(
				_article.getGroupId(), _article.getArticleId(),
				_ddmTemplate.getTemplateKey(), Constants.VIEW, _languageId,
				(ThemeDisplay)null);
		}

		private final JournalArticle _article;
		private final DDMTemplate _ddmTemplate;
		private final String _languageId;

	}

}