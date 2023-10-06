/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.model.listener;

import com.liferay.asset.util.AssetHelper;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.search.experiences.internal.info.collection.provider.JournalArticleSXPBlueprintInfoCollectionProvider;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.GeneralConfiguration;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import org.osgi.framework.BundleContext;

/**
 * @author Shuyang Zhou
 */
public class
	JournalArticleSXPBlueprintInfoCollectionProviderSXPBlueprintModelListener
		extends InfoCollectionProviderSXPBlueprintModelListener {

	public JournalArticleSXPBlueprintInfoCollectionProviderSXPBlueprintModelListener(
		BundleContext bundleContext, CompanyLocalService companyLocalService,
		SXPBlueprintLocalService sxpBlueprintLocalService,
		AssetHelper assetHelper, JournalArticleService journalArticleService,
		Searcher searcher,
		SearchRequestBuilderFactory searchRequestBuilderFactory) {

		super(bundleContext, companyLocalService, sxpBlueprintLocalService);

		_assetHelper = assetHelper;
		_journalArticleService = journalArticleService;
		_searcher = searcher;
		_searchRequestBuilderFactory = searchRequestBuilderFactory;
	}

	@Override
	public void onAfterCreate(SXPBlueprint sxpBlueprint) {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-193551")) {
			return;
		}

		if (StringUtil.equals(
				_getSingleSearchableAssetType(sxpBlueprint),
				JournalArticle.class.getName())) {

			super.onAfterCreate(sxpBlueprint);
		}
	}

	@Override
	protected InfoCollectionProvider<?> createInfoCollectionProvider(
		SXPBlueprint sxpBlueprint) {

		return new JournalArticleSXPBlueprintInfoCollectionProvider(
			_assetHelper, _journalArticleService, _searcher,
			_searchRequestBuilderFactory, sxpBlueprint);
	}

	@Override
	protected String getItemClassName() {
		return JournalArticle.class.getName();
	}

	private String _getSingleSearchableAssetType(SXPBlueprint sxpBlueprint) {
		Configuration configuration = Configuration.unsafeToDTO(
			sxpBlueprint.getConfigurationJSON());

		GeneralConfiguration generalConfiguration =
			configuration.getGeneralConfiguration();

		if (generalConfiguration == null) {
			return null;
		}

		String[] searchableAssetTypes =
			generalConfiguration.getSearchableAssetTypes();

		if (ArrayUtil.isEmpty(searchableAssetTypes) ||
			(searchableAssetTypes.length > 1)) {

			return null;
		}

		return searchableAssetTypes[0];
	}

	private final AssetHelper _assetHelper;
	private final JournalArticleService _journalArticleService;
	private final Searcher _searcher;
	private final SearchRequestBuilderFactory _searchRequestBuilderFactory;

}