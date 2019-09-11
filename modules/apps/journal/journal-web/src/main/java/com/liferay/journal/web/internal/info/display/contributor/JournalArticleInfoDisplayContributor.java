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

import com.liferay.asset.info.display.contributor.util.ContentAccessor;
import com.liferay.asset.info.display.field.AssetEntryInfoDisplayFieldProvider;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseDDMStructureClassTypeReader;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.mapping.info.display.field.DDMFormValuesInfoDisplayFieldProvider;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.display.field.ClassTypesInfoDisplayFieldProvider;
import com.liferay.info.display.field.ExpandoInfoDisplayFieldProvider;
import com.liferay.info.display.field.InfoDisplayFieldProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.JournalContent;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.web.internal.asset.JournalArticleDDMFormValuesReader;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = InfoDisplayContributor.class)
public class JournalArticleInfoDisplayContributor
	implements InfoDisplayContributor<JournalArticle> {

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	public List<ClassType> getClassTypes(long groupId, Locale locale)
		throws PortalException {

		BaseDDMStructureClassTypeReader baseDDMStructureClassTypeReader =
			new BaseDDMStructureClassTypeReader(JournalArticle.class.getName());

		return classTypesInfoDisplayFieldProvider.getClassTypes(
			groupId, baseDDMStructureClassTypeReader, locale);
	}

	@Override
	public Set<InfoDisplayField> getInfoDisplayFields(
			long classTypeId, Locale locale)
		throws PortalException {

		Set<InfoDisplayField> infoDisplayFields =
			infoDisplayFieldProvider.getContributorInfoDisplayFields(
				locale, AssetEntry.class.getName(),
				JournalArticle.class.getName());

		BaseDDMStructureClassTypeReader baseDDMStructureClassTypeReader =
			new BaseDDMStructureClassTypeReader(JournalArticle.class.getName());

		ClassType classType = baseDDMStructureClassTypeReader.getClassType(
			classTypeId, locale);

		if (classType != null) {
			infoDisplayFields.addAll(
				classTypesInfoDisplayFieldProvider.
					getClassTypeInfoDisplayFields(classType, locale));
		}

		infoDisplayFields.addAll(
			expandoInfoDisplayFieldProvider.
				getContributorExpandoInfoDisplayFields(
					JournalArticle.class.getName(), locale));

		infoDisplayFields.addAll(
			_getDDMTemplateInfoDisplayFields(classTypeId, locale));

		return infoDisplayFields;
	}

	@Override
	public Map<String, Object> getInfoDisplayFieldsValues(
			JournalArticle article, Locale locale)
		throws PortalException {

		Map<String, Object> infoDisplayFieldValues = new HashMap<>();

		infoDisplayFieldValues.putAll(
			assetEntryInfoDisplayFieldProvider.
				getAssetEntryInfoDisplayFieldsValues(
					JournalArticle.class.getName(),
					article.getResourcePrimKey(), locale));
		infoDisplayFieldValues.putAll(
			infoDisplayFieldProvider.getContributorInfoDisplayFieldsValues(
				JournalArticle.class.getName(), article, locale));
		infoDisplayFieldValues.putAll(
			_getClassTypeInfoDisplayFieldsValues(article, locale));
		infoDisplayFieldValues.putAll(
			expandoInfoDisplayFieldProvider.
				getContributorExpandoInfoDisplayFieldsValues(
					getClassName(), article, locale));
		infoDisplayFieldValues.putAll(
			_getDDMTemplateInfoDisplayFieldsValues(article, locale));

		return infoDisplayFieldValues;
	}

	@Override
	public InfoDisplayObjectProvider getInfoDisplayObjectProvider(long classPK)
		throws PortalException {

		JournalArticle article = journalArticleLocalService.fetchLatestArticle(
			classPK);

		if ((article == null) || article.isInTrash()) {
			return null;
		}

		return new JournalArticleInfoDisplayObjectProvider(article);
	}

	@Override
	public InfoDisplayObjectProvider<JournalArticle>
			getInfoDisplayObjectProvider(long groupId, String urlTitle)
		throws PortalException {

		JournalArticle article =
			journalArticleLocalService.fetchArticleByUrlTitle(
				groupId, urlTitle);

		if ((article == null) || article.isInTrash()) {
			return null;
		}

		return new JournalArticleInfoDisplayObjectProvider(article);
	}

	@Override
	public String getInfoURLSeparator() {
		return "/w/";
	}

	@Override
	public InfoDisplayObjectProvider getPreviewInfoDisplayObjectProvider(
			long classPK, int type)
		throws PortalException {

		AssetEntry assetEntry = assetEntryLocalService.fetchAssetEntry(classPK);

		if (assetEntry == null) {
			return null;
		}

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				JournalArticle.class.getName());

		AssetRenderer<JournalArticle> assetRenderer =
			assetRendererFactory.getAssetRenderer(
				assetEntry.getClassPK(), type);

		JournalArticle article = assetRenderer.getAssetObject();

		if (article.isInTrash()) {
			return null;
		}

		return new JournalArticleInfoDisplayObjectProvider(article);
	}

	@Override
	public Map<String, Object> getVersionInfoDisplayFieldsValues(
			JournalArticle article, long versionClassPK, Locale locale)
		throws PortalException {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				JournalArticle.class.getName());

		AssetRenderer<JournalArticle> assetRenderer =
			assetRendererFactory.getAssetRenderer(versionClassPK);

		return getInfoDisplayFieldsValues(
			assetRenderer.getAssetObject(), locale);
	}

	@Reference
	protected AssetEntryInfoDisplayFieldProvider
		assetEntryInfoDisplayFieldProvider;

	@Reference
	protected AssetEntryLocalService assetEntryLocalService;

	@Reference
	protected ClassTypesInfoDisplayFieldProvider
		classTypesInfoDisplayFieldProvider;

	@Reference
	protected DDMFormValuesInfoDisplayFieldProvider
		ddmFormValuesInfoDisplayFieldProvider;

	@Reference
	protected DDMStructureLocalService ddmStructureLocalService;

	@Reference
	protected ExpandoInfoDisplayFieldProvider expandoInfoDisplayFieldProvider;

	@Reference
	protected FieldsToDDMFormValuesConverter fieldsToDDMFormValuesConverter;

	@Reference
	protected InfoDisplayFieldProvider infoDisplayFieldProvider;

	@Reference
	protected JournalArticleLocalService journalArticleLocalService;

	@Reference
	protected JournalContent journalContent;

	@Reference
	protected JournalConverter journalConverter;

	private Map<String, Object> _getClassTypeInfoDisplayFieldsValues(
		JournalArticle article, Locale locale) {

		Map<String, Object> classTypeValues = new HashMap<>();

		JournalArticleDDMFormValuesReader journalArticleDDMFormValuesReader =
			new JournalArticleDDMFormValuesReader(article);

		journalArticleDDMFormValuesReader.setFieldsToDDMFormValuesConverter(
			fieldsToDDMFormValuesConverter);
		journalArticleDDMFormValuesReader.setJournalConverter(journalConverter);

		try {
			classTypeValues.putAll(
				ddmFormValuesInfoDisplayFieldProvider.
					getInfoDisplayFieldsValues(
						article,
						journalArticleDDMFormValuesReader.getDDMFormValues(),
						locale));
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return classTypeValues;
	}

	private Set<InfoDisplayField> _getDDMTemplateInfoDisplayFields(
		long classTypeId, Locale locale) {

		Set<InfoDisplayField> infoDisplayFields = new HashSet<>();

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

	private Map<String, Object> _getDDMTemplateInfoDisplayFieldsValues(
		JournalArticle article, Locale locale) {

		Map<String, Object> ddmTemplateInfoDisplayFieldsValues =
			new HashMap<>();

		DDMStructure ddmStructure = article.getDDMStructure();

		List<DDMTemplate> ddmTemplates = ddmStructure.getTemplates();

		ddmTemplates.forEach(
			ddmTemplate -> ddmTemplateInfoDisplayFieldsValues.put(
				_getTemplateKey(ddmTemplate),
				new DDMTemplateContentAccessor(
					article, ddmTemplate, LocaleUtil.toLanguageId(locale))));

		return ddmTemplateInfoDisplayFieldsValues;
	}

	private String _getTemplateKey(DDMTemplate ddmTemplate) {
		String templateKey = ddmTemplate.getTemplateKey();

		return PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
			templateKey.replaceAll("\\W", "_");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleInfoDisplayContributor.class);

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