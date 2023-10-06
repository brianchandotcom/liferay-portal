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
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.search.experiences.internal.info.collection.provider.JournalArticleSXPBlueprintInfoCollectionProvider;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.GeneralConfiguration;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(enabled = false, service = ModelListener.class)
public class
	JournalArticleSXPBlueprintInfoCollectionProviderSXPBlueprintModelListener
		extends InfoCollectionProviderSXPBlueprintModelListener {

	@Override
	public void onAfterCreate(SXPBlueprint sxpBlueprint) {
		String singleSearchableAssetType = _getSingleSearchableAssetType(
			sxpBlueprint);

		if (Validator.isBlank(singleSearchableAssetType)) {
			return;
		}

		if (FeatureFlagManagerUtil.isEnabled("LPS-193551") &&
			StringUtil.equals(
				singleSearchableAssetType, JournalArticle.class.getName())) {

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

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}