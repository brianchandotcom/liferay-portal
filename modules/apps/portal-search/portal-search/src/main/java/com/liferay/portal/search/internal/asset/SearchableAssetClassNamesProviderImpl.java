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

package com.liferay.portal.search.internal.asset;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.asset.SearchableAssetClassNamesProvider;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(immediate = true, service = SearchableAssetClassNamesProvider.class)
public class SearchableAssetClassNamesProviderImpl
	implements SearchableAssetClassNamesProvider {

	@Override
	public String[] getClassNames(long companyId) {
		List<AssetRendererFactory<?>> assetRendererFactories =
			assetRendererFactoryRegistry.getAssetRendererFactories(companyId);

		String[] searchEngineHelperEntryClassNames =
			searchEngineHelper.getEntryClassNames();

		List<String> classNames = new ArrayList<>();

		for (AssetRendererFactory<?> assetRendererFactory :
				assetRendererFactories) {

			if (assetRendererFactory.isSearchable()) {
				String className = assetRendererFactory.getClassName();

				if (ArrayUtil.contains(
						searchEngineHelperEntryClassNames, className, false)) {

					classNames.add(className);
				}
			}
		}

		for (String searchEngineHelperEntryClassName :
				searchEngineHelperEntryClassNames) {

			if (searchEngineHelperEntryClassName.startsWith(
					"com.liferay.object.model.ObjectDefinition#")) {

				classNames.add(searchEngineHelperEntryClassName);
			}
		}

		return classNames.toArray(new String[0]);
	}

	@Reference
	protected AssetRendererFactoryRegistry assetRendererFactoryRegistry;

	@Reference
	protected SearchEngineHelper searchEngineHelper;

}