/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.model.listener;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.util.AssetHelper;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.search.experiences.internal.info.collection.provider.AssetEntrySXPBlueprintInfoCollectionProvider;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import org.osgi.framework.BundleContext;

/**
 * @author Shuyang Zhou
 */
public class
	AssetEntrySXPBlueprintInfoCollectionProviderSXPBlueprintModelListener
		extends InfoCollectionProviderSXPBlueprintModelListener {

	public AssetEntrySXPBlueprintInfoCollectionProviderSXPBlueprintModelListener(
		BundleContext bundleContext, CompanyLocalService companyLocalService,
		SXPBlueprintLocalService sxpBlueprintLocalService,
		AssetHelper assetHelper, Searcher searcher,
		SearchRequestBuilderFactory searchRequestBuilderFactory) {

		super(bundleContext, companyLocalService, sxpBlueprintLocalService);

		_assetHelper = assetHelper;
		_searcher = searcher;
		_searchRequestBuilderFactory = searchRequestBuilderFactory;
	}

	@Override
	protected InfoCollectionProvider<?> createInfoCollectionProvider(
		SXPBlueprint sxpBlueprint) {

		return new AssetEntrySXPBlueprintInfoCollectionProvider(
			_assetHelper, _searcher, _searchRequestBuilderFactory,
			sxpBlueprint);
	}

	@Override
	protected String getItemClassName() {
		return AssetEntry.class.getName();
	}

	private final AssetHelper _assetHelper;
	private final Searcher _searcher;
	private final SearchRequestBuilderFactory _searchRequestBuilderFactory;

}