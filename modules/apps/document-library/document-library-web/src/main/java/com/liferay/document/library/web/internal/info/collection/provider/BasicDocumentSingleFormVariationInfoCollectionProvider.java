/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.info.collection.provider;

import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.ConfigurableInfoCollectionProvider;
import com.liferay.info.collection.provider.FilteredInfoCollectionProvider;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.MultiselectInfoFieldType;
import com.liferay.info.field.type.OptionInfoFieldType;
import com.liferay.info.filter.CategoriesInfoFilter;
import com.liferay.info.filter.InfoFilter;
import com.liferay.info.form.InfoForm;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.localized.SingleValueInfoLocalizedValue;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.asset.util.comparator.AssetTagNameComparator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(enabled = false, service = InfoCollectionProvider.class)
public class BasicDocumentSingleFormVariationInfoCollectionProvider
	implements ConfigurableInfoCollectionProvider<FileEntry>,
			   FilteredInfoCollectionProvider<FileEntry>,
			   SingleFormVariationInfoCollectionProvider<FileEntry> {

	@Override
	public InfoPage<FileEntry> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		return _getFileEntryInfoPage(collectionQuery);
	}

	@Override
	public InfoForm getConfigurationInfoForm() {
		return InfoForm.builder(
		).infoFieldSetEntry(
			_getAssetTagsInfoField()
		).build();
	}

	@Override
	public String getFormVariationKey() {
		return String.valueOf(0);
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "basic-document");
	}

	@Override
	public List<InfoFilter> getSupportedInfoFilters() {
		return Arrays.asList(new CategoriesInfoFilter());
	}

	private SearchContext _buildSearchContext(CollectionQuery collectionQuery) {
		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(true);
		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.STATUS, WorkflowConstants.STATUS_APPROVED
			).put(
				"fileEntryTypeId", getFormVariationKey()
			).put(
				"head", true
			).put(
				"latest", true
			).build());

		CategoriesInfoFilter categoriesInfoFilter =
			collectionQuery.getInfoFilter(CategoriesInfoFilter.class);

		if (categoriesInfoFilter != null) {
			long[] categoryIds = ArrayUtil.append(
				categoriesInfoFilter.getCategoryIds());

			categoryIds = ArrayUtil.unique(categoryIds);

			searchContext.setAssetCategoryIds(categoryIds);
		}

		Map<String, String[]> configuration =
			collectionQuery.getConfiguration();

		if (configuration == null) {
			configuration = Collections.emptyMap();
		}

		String[] assetTagNames = configuration.get(Field.ASSET_TAG_NAMES);

		if (ArrayUtil.isNotEmpty(assetTagNames) &&
			Validator.isNotNull(assetTagNames[0])) {

			searchContext.setAssetTagNames(assetTagNames);
		}

		searchContext.setClassTypeIds(
			new long[] {GetterUtil.getLong(getFormVariationKey())});

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		searchContext.setCompanyId(serviceContext.getCompanyId());

		Pagination pagination = collectionQuery.getPagination();

		searchContext.setEnd(pagination.getEnd());

		searchContext.setEntryClassNames(
			new String[] {DLFileEntryConstants.getClassName()});
		searchContext.setGroupIds(
			new long[] {serviceContext.getScopeGroupId()});

		Sort sort = collectionQuery.getSort();

		if (sort != null) {
			searchContext.setSorts(
				new com.liferay.portal.kernel.search.Sort(
					sort.getFieldName(),
					com.liferay.portal.kernel.search.Sort.LONG_TYPE,
					sort.isReverse()));
		}
		else {
			searchContext.setSorts(
				new com.liferay.portal.kernel.search.Sort(
					Field.MODIFIED_DATE,
					com.liferay.portal.kernel.search.Sort.LONG_TYPE, true));
		}

		searchContext.setStart(pagination.getStart());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	private InfoField<?> _getAssetTagsInfoField() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		return InfoField.builder(
		).infoFieldType(
			MultiselectInfoFieldType.INSTANCE
		).namespace(
			StringPool.BLANK
		).name(
			Field.ASSET_TAG_NAMES
		).attribute(
			MultiselectInfoFieldType.OPTIONS,
			TransformUtil.transform(
				_assetTagLocalService.getGroupTags(
					serviceContext.getScopeGroupId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, new AssetTagNameComparator(true)),
				assetTag -> new OptionInfoFieldType(
					new SingleValueInfoLocalizedValue<>(assetTag.getName()),
					assetTag.getName()))
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "tag")
		).localizable(
			true
		).build();
	}

	private InfoPage<FileEntry> _getFileEntryInfoPage(
		CollectionQuery collectionQuery) {

		try {
			Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
				DLFileEntryConstants.getClassName());

			SearchContext searchContext = _buildSearchContext(collectionQuery);

			Hits hits = indexer.search(searchContext);

			List<FileEntry> fileEntries = new ArrayList<>();

			for (Document document : hits.getDocs()) {
				long classPK = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				fileEntries.add(_dlAppLocalService.getFileEntry(classPK));
			}

			return InfoPage.of(
				fileEntries, collectionQuery.getPagination(), hits.getLength());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BasicDocumentSingleFormVariationInfoCollectionProvider.class);

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private Language _language;

}