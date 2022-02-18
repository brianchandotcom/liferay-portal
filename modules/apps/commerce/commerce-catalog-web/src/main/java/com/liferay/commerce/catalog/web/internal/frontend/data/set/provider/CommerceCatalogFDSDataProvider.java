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

package com.liferay.commerce.catalog.web.internal.frontend.data.set.provider;

import com.liferay.commerce.catalog.web.internal.constants.CommerceCatalogFDSNames;
import com.liferay.commerce.catalog.web.internal.model.Catalog;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gianmarco Brunialti Masera
 */
@Component(
	enabled = false, immediate = true,
	property = "fds.data.provider.key=" + CommerceCatalogFDSNames.CATALOGS,
	service = FDSDataProvider.class
)
public class CommerceCatalogFDSDataProvider
	implements FDSDataProvider<Catalog> {

	@Override
	public List<Catalog> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		List<Catalog> catalogs = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<CommerceCatalog> commerceCatalogs = _commerceCatalogService.search(
			themeDisplay.getCompanyId(), fdsKeywords.getKeywords(),
			fdsPagination.getStartPosition(), fdsPagination.getEndPosition(),
			new Sort(Field.NAME, false));

		for (CommerceCatalog catalog : commerceCatalogs) {
			catalogs.add(
				new Catalog(
					catalog.getCommerceCatalogId(),
					catalog.getCommerceCurrencyCode(),
					catalog.getCatalogDefaultLanguageId(), catalog.getName(),
					catalog.isSystem()));
		}

		return catalogs;
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _commerceCatalogService.searchCommerceCatalogsCount(
			themeDisplay.getCompanyId(), fdsKeywords.getKeywords());
	}

	@Reference
	private CommerceCatalogService _commerceCatalogService;

}