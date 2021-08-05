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

package com.liferay.journal.web.internal.info.collection.provider;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.ConfigurableInfoCollectionProvider;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.web.internal.search.JournalSearcher;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	enabled = false, immediate = true, service = InfoCollectionProvider.class
)
public class BasicWebContentSingleFormVariationInfoCollectionProvider
	implements ConfigurableInfoCollectionProvider<JournalArticle>,
			   SingleFormVariationInfoCollectionProvider<JournalArticle> {

	@Override
	public InfoPage<JournalArticle> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		Indexer<?> indexer = JournalSearcher.getInstance();

		SearchContext searchContext = _buildSearchContext(collectionQuery);

		try {
			Hits hits = indexer.search(searchContext);

			List<JournalArticle> articles = new ArrayList<>();

			for (Document document : hits.getDocs()) {
				String className = document.get(Field.ENTRY_CLASS_NAME);

				if (className.equals(JournalArticle.class.getName())) {
					long classPK = GetterUtil.getLong(
						document.get(Field.ENTRY_CLASS_PK));

					JournalArticle article =
						_journalArticleLocalService.fetchLatestArticle(
							classPK, WorkflowConstants.STATUS_ANY, false);

					articles.add(article);
				}
			}

			return InfoPage.of(
				articles, collectionQuery.getPagination(), hits.getLength());
		}
		catch (SearchException searchException) {
			if (_log.isWarnEnabled()) {
				_log.warn(searchException, searchException);
			}
		}

		return null;
	}

	@Override
	public InfoForm getConfigurationInfoForm() {
		return InfoForm.builder(
		).infoFieldSetEntry(
			InfoFieldSet.builder(
			).infoFieldSetEntry(
				InfoField.builder(
				).infoFieldType(
					TextInfoFieldType.INSTANCE
				).name(
					Field.TITLE
				).labelInfoLocalizedValue(
					InfoLocalizedValue.localize(getClass(), "title")
				).localizable(
					true
				).build()
			).infoFieldSetEntry(
				InfoField.builder(
				).infoFieldType(
					TextInfoFieldType.INSTANCE
				).name(
					Field.DESCRIPTION
				).labelInfoLocalizedValue(
					InfoLocalizedValue.localize(getClass(), "description")
				).localizable(
					true
				).build()
			).labelInfoLocalizedValue(
				InfoLocalizedValue.localize(getClass(), "configuration")
			).name(
				"basic-information"
			).build()
		).build();
	}

	@Override
	public String getFormVariationKey() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			serviceContext.getScopeGroupId(),
			_portal.getClassNameId(JournalArticle.class.getName()),
			"BASIC-WEB-CONTENT", true);

		return String.valueOf(ddmStructure.getStructureId());
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "basic-web-content");
	}

	private SearchContext _buildSearchContext(CollectionQuery collectionQuery) {
		Pagination pagination = collectionQuery.getPagination();

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(true);

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.STATUS, WorkflowConstants.STATUS_APPROVED
			).put(
				"ddmStructureKey", "BASIC-WEB-CONTENT"
			).put(
				"head", true
			).put(
				"latest", true
			).build());

		Optional<Map<String, String[]>> configurationOptional =
			collectionQuery.getConfigurationOptional();

		Map<String, String[]> configuration = configurationOptional.orElse(
			null);

		if (configuration != null) {
			for (Map.Entry<String, String[]> entry : configuration.entrySet()) {
				String value = entry.getValue()[0];

				if (Validator.isNotNull(value)) {
					String localizedName = Field.getLocalizedName(
						LocaleUtil.getSiteDefault(), entry.getKey());

					searchContext.setAttribute(localizedName, value);
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		searchContext.setCompanyId(serviceContext.getCompanyId());

		searchContext.setEnd(pagination.getEnd());
		searchContext.setGroupIds(
			new long[] {serviceContext.getScopeGroupId()});

		Optional<Sort> sortOptional = collectionQuery.getSortOptional();

		if (sortOptional.isPresent()) {
			Sort sort = sortOptional.get();

			searchContext.setSorts(
				new com.liferay.portal.kernel.search.Sort(
					sort.getFieldName(),
					com.liferay.portal.kernel.search.Sort.LONG_TYPE,
					sort.isReverse()));
		}

		searchContext.setStart(pagination.getStart());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BasicWebContentSingleFormVariationInfoCollectionProvider.class);

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private Portal _portal;

	@Reference(target = "(bundle.symbolic.name=com.liferay.journal.web)")
	private ResourceBundleLoader _resourceBundleLoader;

}