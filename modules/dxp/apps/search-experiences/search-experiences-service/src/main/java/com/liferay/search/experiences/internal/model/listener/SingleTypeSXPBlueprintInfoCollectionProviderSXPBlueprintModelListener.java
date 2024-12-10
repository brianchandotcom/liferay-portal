/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.model.listener;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.util.AssetHelper;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.search.experiences.internal.info.collection.provider.AssetEntrySXPBlueprintInfoCollectionProvider;
import com.liferay.search.experiences.internal.info.collection.provider.FileEntrySXPBlueprintInfoCollectionProvider;
import com.liferay.search.experiences.internal.info.collection.provider.JournalArticleSXPBlueprintInfoCollectionProvider;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.GeneralConfiguration;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import org.osgi.framework.BundleContext;

/**
 * @author Joshua Cords
 */
public class
	SingleTypeSXPBlueprintInfoCollectionProviderSXPBlueprintModelListener
		extends InfoCollectionProviderSXPBlueprintModelListener {

	public SingleTypeSXPBlueprintInfoCollectionProviderSXPBlueprintModelListener(
		BundleContext bundleContext, CompanyLocalService companyLocalService,
		SXPBlueprintLocalService sxpBlueprintLocalService,
		AssetHelper assetHelper, DLAppLocalService dlAppLocalService,
		JournalArticleService journalArticleService, Searcher searcher,
		SearchRequestBuilderFactory searchRequestBuilderFactory) {

		super(bundleContext, companyLocalService, sxpBlueprintLocalService);

		_assetHelper = assetHelper;
		_dlAppLocalService = dlAppLocalService;
		_journalArticleService = journalArticleService;
		_searcher = searcher;
		_searchRequestBuilderFactory = searchRequestBuilderFactory;
	}

	@Override
	public void onAfterCreate(SXPBlueprint sxpBlueprint) {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-193551")) {
			return;
		}

		if (_hasSingleSearchableAssetType(sxpBlueprint)) {
			super.onAfterCreate(sxpBlueprint);
		}
	}

	@Override
	protected InfoCollectionProvider<?> createInfoCollectionProvider(
		SXPBlueprint sxpBlueprint) {

		if (_className.equals(JournalArticle.class.getName())) {
			return new JournalArticleSXPBlueprintInfoCollectionProvider(
				_assetHelper, _journalArticleService, _searcher,
				_searchRequestBuilderFactory, sxpBlueprint);
		}
		else if (_className.equals(DLFileEntry.class.getName())) {
			return new FileEntrySXPBlueprintInfoCollectionProvider(
				_assetHelper, _dlAppLocalService, _searcher,
				_searchRequestBuilderFactory, sxpBlueprint);
		}

		return new AssetEntrySXPBlueprintInfoCollectionProvider(
			_assetHelper, _searcher, _searchRequestBuilderFactory,
			sxpBlueprint);
	}

	@Override
	protected String getItemClassName() {
		return _className;
	}

	private boolean _hasSingleSearchableAssetType(SXPBlueprint sxpBlueprint) {
		Configuration configuration = Configuration.unsafeToDTO(
			sxpBlueprint.getConfigurationJSON());

		GeneralConfiguration generalConfiguration =
			configuration.getGeneralConfiguration();

		if (generalConfiguration == null) {
			return false;
		}

		String[] searchableAssetTypes =
			generalConfiguration.getSearchableAssetTypes();

		if (ArrayUtil.isNotEmpty(searchableAssetTypes) &&
			(searchableAssetTypes.length == 1)) {

			_className = searchableAssetTypes[0];

			return true;
		}

		return false;
	}

	private final AssetHelper _assetHelper;
	private String _className = AssetEntry.class.getName();
	private final DLAppLocalService _dlAppLocalService;
	private final JournalArticleService _journalArticleService;
	private final Searcher _searcher;
	private final SearchRequestBuilderFactory _searchRequestBuilderFactory;

}