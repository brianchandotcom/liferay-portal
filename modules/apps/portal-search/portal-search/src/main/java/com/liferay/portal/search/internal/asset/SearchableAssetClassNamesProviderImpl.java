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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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

		Stream<AssetRendererFactory<?>> stream1 =
			assetRendererFactories.stream();

		String[] searchEngineHelperEntryClassNames =
			searchEngineHelper.getEntryClassNames();

		String[] array1 = stream1.filter(
			AssetRendererFactory::isSearchable
		).map(
			AssetRendererFactory::getClassName
		).filter(
			className -> ArrayUtil.contains(
				searchEngineHelperEntryClassNames, className, false)
		).toArray(
			String[]::new
		);

		Stream<String> stream2 = Arrays.stream(
			searchEngineHelperEntryClassNames);

		String[] array2 = stream2.filter(
			className -> className.startsWith(
				"com.liferay.object.model.ObjectDefinition#")
		).toArray(
			String[]::new
		);

		String[] classNames = new String[array1.length + array2.length];

		ArrayUtil.combine(array1, array2, classNames);

		return classNames;
	}

	@Reference
	protected AssetRendererFactoryRegistry assetRendererFactoryRegistry;

	@Reference
	protected SearchEngineHelper searchEngineHelper;

}