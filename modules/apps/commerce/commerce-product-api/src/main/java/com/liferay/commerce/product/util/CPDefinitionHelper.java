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

package com.liferay.commerce.product.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPQuery;
import com.liferay.commerce.product.data.source.CPDataSourceResult;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Locale;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@ProviderType
public interface CPDefinitionHelper {

	public CPCatalogEntry getCPCatalogEntry(Document document, Locale locale);

	public CPCatalogEntry getCPCatalogEntry(
			long commerceAccountId, long groupId, long cpDefinitionId,
			Locale locale)
		throws PortalException;

	public String getDefaultImageFileURL(
			long commerceAccountId, long cpDefinitionId)
		throws PortalException;

	public String getFriendlyURL(long cpDefinitionId, ThemeDisplay themeDisplay)
		throws PortalException;

	public CPDataSourceResult search(
			long groupId, SearchContext searchContext, CPQuery cpQuery,
			int start, int end)
		throws PortalException;

	public long searchCount(
			long groupId, SearchContext searchContext, CPQuery cpQuery)
		throws PortalException;

}