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
import com.liferay.commerce.product.catalog.CPMedia;
import com.liferay.commerce.product.catalog.CPSku;
import com.liferay.commerce.product.content.render.CPContentRenderer;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.type.CPType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alessio Antonio Rendina
 */
@ProviderType
public interface CPContentHelper {

	public String getAvailabilityEstimateLabel(
			HttpServletRequest httpServletRequest)
		throws Exception;

	public String getAvailabilityLabel(HttpServletRequest httpServletRequest)
		throws Exception;

	public List<CPDefinitionSpecificationOptionValue>
			getCategorizedCPDefinitionSpecificationOptionValues(
				long cpDefinitionId, long cpOptionCategoryId)
		throws PortalException;

	public List<CPMedia> getCPAttachmentFileEntries(
			long cpDefinitionId, ThemeDisplay themeDisplay)
		throws PortalException;

	public CPCatalogEntry getCPCatalogEntry(
			HttpServletRequest httpServletRequest)
		throws PortalException;

	public Object getCPContentContributorValue(
			String contributorKey, HttpServletRequest httpServletRequest)
		throws Exception;

	public String getCPContentRendererKey(
		String type, RenderRequest renderRequest);

	public List<CPContentRenderer> getCPContentRenderers(String cpType);

	public List<CPDefinitionSpecificationOptionValue>
			getCPDefinitionSpecificationOptionValues(long cpDefinitionId)
		throws PortalException;

	public List<CPOptionCategory> getCPOptionCategories(long companyId);

	public List<CPType> getCPTypes();

	public CPInstance getDefaultCPInstance(
			HttpServletRequest httpServletRequest)
		throws Exception;

	public CPSku getDefaultCPSku(CPCatalogEntry cpCatalogEntry)
		throws Exception;

	public String getDownloadFileEntryURL(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws PortalException;

	public String getFriendlyURL(
			CPCatalogEntry cpCatalogEntry, ThemeDisplay themeDisplay)
		throws PortalException;

	public List<CPMedia> getImages(
			long cpDefinitionId, ThemeDisplay themeDisplay)
		throws PortalException;

	public String getImageURL(FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception;

	public String getStockQuantity(HttpServletRequest httpServletRequest)
		throws Exception;

	public String getStockQuantityLabel(HttpServletRequest httpServletRequest)
		throws Exception;

	public String getSubscriptionInfoLabel(
			HttpServletRequest httpServletRequest)
		throws Exception;

	public ResourceURL getViewAttachmentURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException;

	public boolean hasChildCPDefinitions(long cpDefinitionId);

	public boolean hasCPDefinitionSpecificationOptionValues(long cpDefinitionId)
		throws PortalException;

	public boolean isInWishList(
			CPSku cpSku, CPCatalogEntry cpCatalogEntry,
			ThemeDisplay themeDisplay)
		throws Exception;

	public void renderCPType(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception;

	public String renderOptions(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException;

}