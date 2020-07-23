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

package com.liferay.commerce.product.content.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.data.source.CPDataSourceResult;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
@ProviderType
public interface CPCompareContentHelper {

	public Set<CPSpecificationOption> getCategorizedCPSpecificationOptions(
			CPDataSourceResult cpDataSourceResult)
		throws PortalException;

	public String getClearCompareProductsURL(
		RenderRequest renderRequest, RenderResponse renderResponse);

	public String getCompareProductsURL(ThemeDisplay themeDisplay)
		throws PortalException;

	public Set<String> getCPDefinitionOptionRelNames(
			CPDataSourceResult cpDataSourceResult, Locale locale)
		throws PortalException;

	public String getCPDefinitionOptionValueRels(
			CPCatalogEntry cpCatalogEntry, String cpDefinitionOptionRelName,
			Locale locale)
		throws PortalException;

	public String getCPDefinitionSpecificationOptionValue(
		long cpDefinitionId, long cpSpecificationOptionId, Locale locale);

	public List<CPOptionCategory> getCPOptionCategories(long groupId);

	public Set<CPSpecificationOption> getCPSpecificationOptions(
			CPDataSourceResult cpDataSourceResult)
		throws PortalException;

	public String getDeleteCompareProductURL(
		long cpDefinitionId, RenderRequest renderRequest,
		RenderResponse renderResponse);

	public String getDimensionCPMeasurementUnitName(
		long groupId, Locale locale);

	public int getProductsLimit(PortletDisplay portletDisplay)
		throws PortalException;

	public String getEditCompareProductActionURL(HttpServletRequest httpServletRequest);

	public String getCompareContentPortletNamespace();

	public boolean hasCategorizedCPDefinitionSpecificationOptionValues(
			CPDataSourceResult cpDataSourceResult, long cpOptionCategoryId)
		throws PortalException;

}