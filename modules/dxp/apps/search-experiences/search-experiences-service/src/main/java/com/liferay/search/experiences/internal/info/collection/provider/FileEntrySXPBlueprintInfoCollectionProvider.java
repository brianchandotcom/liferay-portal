/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.info.collection.provider;

import com.liferay.asset.util.AssetHelper;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.FilteredInfoCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.GeneralConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author Joshua Cords
 */
public class FileEntrySXPBlueprintInfoCollectionProvider
	extends SXPBlueprintInfoCollectionProvider<FileEntry>
	implements FilteredInfoCollectionProvider<FileEntry>,
			   SingleFormVariationInfoCollectionProvider<FileEntry> {

	public FileEntrySXPBlueprintInfoCollectionProvider(
		AssetHelper assetHelper, DLAppLocalService dlAppLocalService,
		Searcher searcher,
		SearchRequestBuilderFactory searchRequestBuilderFactory,
		SXPBlueprint sxpBlueprint) {

		super(assetHelper, searcher, searchRequestBuilderFactory, sxpBlueprint);

		_dlAppLocalService = dlAppLocalService;
	}

	@Override
	public InfoPage<FileEntry> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		try {
			Pagination pagination = collectionQuery.getPagination();

			SearchRequestBuilder searchRequestBuilder = getSearchRequestBuilder(
				collectionQuery, pagination);

			SearchResponse searchResponse = searcher.search(
				searchRequestBuilder.build());

			return InfoPage.of(
				_getDLFileEntries(searchResponse.getSearchHits()),
				collectionQuery.getPagination(), searchResponse.getTotalHits());
		}
		catch (Exception exception) {
			_log.error("Unable to get document library file entry", exception);
		}

		return InfoPage.of(
			Collections.emptyList(), collectionQuery.getPagination(), 0);
	}

	@Override
	public String getFormVariationKey() {
		Configuration configuration = Configuration.unsafeToDTO(
			sxpBlueprint.getConfigurationJSON());

		GeneralConfiguration generalConfiguration =
			configuration.getGeneralConfiguration();

		if (generalConfiguration == null) {
			return "0";
		}

		return generalConfiguration.getSearchableAssetSubType();
	}

	@Override
	public String getKey() {
		return StringBundler.concat(
			FileEntrySXPBlueprintInfoCollectionProvider.class.getName(),
			StringPool.UNDERLINE, sxpBlueprint.getCompanyId(),
			StringPool.UNDERLINE, sxpBlueprint.getExternalReferenceCode(),
			StringPool.UNDERLINE, FileEntry.class.getName());
	}

	@Override
	public String getLabel(Locale locale) {
		return sxpBlueprint.getTitle(locale);
	}

	private List<FileEntry> _getDLFileEntries(SearchHits searchHits)
		throws PortalException {

		List<FileEntry> fileEntries = new ArrayList<>();

		for (SearchHit searchHit : searchHits.getSearchHits()) {
			Document document = searchHit.getDocument();

			long classPK = GetterUtil.getLong(
				document.getValue(Field.ENTRY_CLASS_PK));

			fileEntries.add(_dlAppLocalService.getFileEntry(classPK));
		}

		return fileEntries;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileEntrySXPBlueprintInfoCollectionProvider.class);

	private final DLAppLocalService _dlAppLocalService;

}