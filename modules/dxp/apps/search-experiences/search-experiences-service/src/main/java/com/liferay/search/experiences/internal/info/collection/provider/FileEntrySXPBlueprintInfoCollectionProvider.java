/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.info.collection.provider;

import com.liferay.asset.util.AssetHelper;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.FilteredInfoCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.GeneralConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Joshua Cords
 */
public class FileEntrySXPBlueprintInfoCollectionProvider
	extends SXPBlueprintInfoCollectionProvider<FileEntry>
	implements FilteredInfoCollectionProvider<FileEntry>,
			   SingleFormVariationInfoCollectionProvider<FileEntry> {

	public FileEntrySXPBlueprintInfoCollectionProvider(
		AssetHelper assetHelper, DLAppLocalService dlAppLocalService,
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService,
		GroupService groupService, Searcher searcher,
		SearchRequestBuilderFactory searchRequestBuilderFactory,
		SXPBlueprint sxpBlueprint) {

		super(assetHelper, searcher, searchRequestBuilderFactory, sxpBlueprint);

		_dlAppLocalService = dlAppLocalService;
		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
		_groupService = groupService;
	}

	@Override
	public InfoPage<FileEntry> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		try {
			SearchResponse searchResponse = getCollectionQuerySearchResponse(
				collectionQuery);

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

		String[] searchableAssetTypes =
			generalConfiguration.getSearchableAssetTypes();

		if (searchableAssetTypes.length != 1) {
			return StringPool.BLANK;
		}

		String[] searchableAssetTypeWithSubtype = StringUtil.split(
			searchableAssetTypes[0], "&&");

		if (searchableAssetTypeWithSubtype.length != 3) {
			return StringPool.BLANK;
		}

		if (searchableAssetTypeWithSubtype[1].equals(StringPool.BLANK)) {
			return "0";
		}

		Group group;

		try {
			group = _groupService.fetchGroupByExternalReferenceCode(
				searchableAssetTypeWithSubtype[1], sxpBlueprint.getCompanyId());
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to get group with external reference code " +
					searchableAssetTypeWithSubtype[1],
				portalException);

			return StringPool.BLANK;
		}

		try {
			DLFileEntryType dlFileEntryType =
				_dlFileEntryTypeLocalService.
					getDLFileEntryTypeByExternalReferenceCode(
						searchableAssetTypeWithSubtype[2], group.getGroupId());

			return String.valueOf(dlFileEntryType.getFileEntryTypeId());
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to get file entry type with external reference code " +
					searchableAssetTypeWithSubtype[2],
				portalException);
		}

		return StringPool.BLANK;
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
	private final DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private final GroupService _groupService;

}