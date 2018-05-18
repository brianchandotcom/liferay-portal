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

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.List;

/**
 * @author Marco Leo
 */
@ProviderType
public interface CPDefinitionHelper {

	public BaseModelSearchResult<CPDefinition> getCPDefinitions(
			long companyId, long groupId, String keywords, String filterFields,
			String filterValues, int start, int end, Sort sort)
		throws PortalException;

	public List<Facet> getFacets(
		String filterFields, String filterValues, SearchContext searchContext);

	public String getFriendlyURL(long cpDefinitionId, ThemeDisplay themeDisplay)
		throws PortalException;

	public Layout getProductLayout(
			long groupId, boolean privateLayout, long cpDefinitionId)
		throws PortalException;

}