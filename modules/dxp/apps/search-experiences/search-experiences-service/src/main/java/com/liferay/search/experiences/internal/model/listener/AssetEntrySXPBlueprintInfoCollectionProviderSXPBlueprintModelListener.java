/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.model.listener;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.util.AssetHelper;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.search.experiences.internal.info.collection.provider.AssetEntrySXPBlueprintInfoCollectionProvider;
import com.liferay.search.experiences.model.SXPBlueprint;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(enabled = false, service = ModelListener.class)
public class
	AssetEntrySXPBlueprintInfoCollectionProviderSXPBlueprintModelListener
		extends InfoCollectionProviderSXPBlueprintModelListener {

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

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}