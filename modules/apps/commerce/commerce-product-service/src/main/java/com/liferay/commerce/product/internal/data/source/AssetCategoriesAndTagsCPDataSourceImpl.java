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

package com.liferay.commerce.product.internal.data.source;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.commerce.product.catalog.CPQuery;
import com.liferay.commerce.product.data.source.CPDataSource;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ethan Bustad
 */
@Component(
	enabled = false, immediate = true,
	property = "commerce.product.data.source.name=" + AssetCategoriesAndTagsCPDataSourceImpl.NAME,
	service = CPDataSource.class
)
public class AssetCategoriesAndTagsCPDataSourceImpl
	extends BaseAssetEntryCPDataSourceImpl {

	public static final String NAME = "assetCategoriesAndTagsDataSource";

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
			getResourceBundle(locale),
			"products-of-the-same-categories-and-tags");
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected CPQuery getCPQuery(long cpDefinitionId) throws PortalException {
		CPQuery cpQuery = new CPQuery();

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			CPDefinition.class.getName(), cpDefinitionId);

		cpQuery.setAnyCategoryIds(assetEntry.getCategoryIds());
		cpQuery.setAnyTagIds(_getTagIds(assetEntry));

		return cpQuery;
	}

	private long[] _getTagIds(AssetEntry assetEntry) throws PortalException {
		List<AssetTag> assetTags = assetEntry.getTags();

		long[] tagIds = new long[assetTags.size()];

		for (int i = 0; i < assetTags.size(); i++) {
			AssetTag assetTag = assetTags.get(i);

			tagIds[i] = assetTag.getTagId();
		}

		return tagIds;
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private Language _language;

}