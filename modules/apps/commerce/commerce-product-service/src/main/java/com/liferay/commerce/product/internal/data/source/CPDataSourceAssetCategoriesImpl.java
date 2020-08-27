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
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.commerce.product.catalog.CPQuery;
import com.liferay.commerce.product.data.source.CPDataSource;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ethan Bustad
 */
@Component(
	immediate = true,
	property = "commerce.product.data.source.name=" + CPDataSourceAssetCategoriesImpl.NAME,
	service = CPDataSource.class
)
public class CPDataSourceAssetCategoriesImpl
	extends BaseCPDataSourceAssetEntryImpl {

	public static final String NAME = "assetCategoriesDataSource";

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			getResourceBundle(locale), "products-of-the-same-categories");
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

		return cpQuery;
	}

	@Reference(unbind = "-")
	private void _setCPDefinitionHelper(CPDefinitionHelper cpDefinitionHelper) {
		this.cpDefinitionHelper = cpDefinitionHelper;
	}

	@Reference(unbind = "-")
	private void _setPortal(Portal portal) {
		this.portal = portal;
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

}