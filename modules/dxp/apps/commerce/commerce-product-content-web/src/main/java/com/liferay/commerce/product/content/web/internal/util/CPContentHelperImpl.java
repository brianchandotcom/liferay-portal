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

package com.liferay.commerce.product.content.web.internal.util;

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPSku;
import com.liferay.commerce.product.constants.CPContentContributorConstants;
import com.liferay.commerce.product.constants.CPOptionCategoryConstants;
import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.content.render.CPContentRenderer;
import com.liferay.commerce.product.content.render.CPContentRendererRegistry;
import com.liferay.commerce.product.content.util.CPContentHelper;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueService;
import com.liferay.commerce.product.service.CPOptionCategoryLocalService;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.commerce.product.util.CPContentContributor;
import com.liferay.commerce.product.util.CPContentContributorRegistry;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = CPContentHelper.class)
public class CPContentHelperImpl implements CPContentHelper {

	@Override
	public String getAvailabilityEstimateLabel(
			HttpServletRequest httpServletRequest)
		throws Exception {

		JSONObject availabilityEstimate =
			(JSONObject)getCPContentContributorValue(
				CPContentContributorConstants.AVAILABILITY_ESTIMATE_NAME,
				httpServletRequest);

		if (availabilityEstimate == null) {
			return StringPool.BLANK;
		}

		return availabilityEstimate.getString(
			CPContentContributorConstants.AVAILABILITY_ESTIMATE_NAME);
	}

	@Override
	public String getAvailabilityLabel(HttpServletRequest httpServletRequest)
		throws Exception {

		JSONObject availability = (JSONObject)getCPContentContributorValue(
			CPContentContributorConstants.AVAILABILITY_NAME,
			httpServletRequest);

		if (availability == null) {
			return StringPool.BLANK;
		}

		return availability.getString(
			CPContentContributorConstants.AVAILABILITY_NAME);
	}

	@Override
	public List<CPDefinitionSpecificationOptionValue>
			getCategorizedCPDefinitionSpecificationOptionValues(
				long cpDefinitionId, long cpOptionCategoryId)
		throws PortalException {

		return _cpCatalogEntrySpecificationOptionValueService.
			getCPDefinitionSpecificationOptionValues(
				cpDefinitionId, cpOptionCategoryId);
	}

	@Override
	public List<CPAttachmentFileEntry> getCPAttachmentFileEntries(
			long cpDefinitionId)
		throws PortalException {

		long classNameId = _portal.getClassNameId(CPDefinition.class);

		return _cpAttachmentFileEntryService.getCPAttachmentFileEntries(
			classNameId, cpDefinitionId,
			CPAttachmentFileEntryConstants.TYPE_OTHER,
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	@Override
	public CPCatalogEntry getCPCatalogEntry(
		HttpServletRequest httpServletRequest) {

		return (CPCatalogEntry)httpServletRequest.getAttribute(
			CPWebKeys.CP_CATALOG_ENTRY);
	}

	@Override
	public Object getCPContentContributorValue(
			String contributorKey, HttpServletRequest httpServletRequest)
		throws Exception {

		CPContentContributor cpContentContributor =
			_cpContentContributorRegistry.getCPContentContributor(
				contributorKey);

		if (cpContentContributor == null) {
			return null;
		}

		return cpContentContributor.getValue(
			getDefaultCPInstance(httpServletRequest), httpServletRequest);
	}

	@Override
	public String getCPContentRendererKey(
		String type, RenderRequest renderRequest) {

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		String value = portletPreferences.getValue(
			type + "--cpTypeRendererKey", null);

		if (Validator.isNotNull(value)) {
			return value;
		}

		List<CPContentRenderer> cpContentRenderers = getCPContentRenderers(
			type);

		if (cpContentRenderers.isEmpty()) {
			return StringPool.BLANK;
		}

		CPContentRenderer cpContentRenderer = cpContentRenderers.get(0);

		if (cpContentRenderer == null) {
			return StringPool.BLANK;
		}

		return cpContentRenderer.getKey();
	}

	@Override
	public List<CPContentRenderer> getCPContentRenderers(String cpType) {
		return _cpContentRendererRegistry.getCPContentRenderers(cpType);
	}

	@Override
	public List<CPDefinitionSpecificationOptionValue>
			getCPDefinitionSpecificationOptionValues(long cpDefinitionId)
		throws PortalException {

		return _cpCatalogEntrySpecificationOptionValueService.
			getCPDefinitionSpecificationOptionValues(
				cpDefinitionId,
				CPOptionCategoryConstants.DEFAULT_CP_OPTION_CATEGORY_ID);
	}

	@Override
	public List<CPOptionCategory> getCPOptionCategories(long groupId) {
		return _cpOptionCategoryLocalService.getCPOptionCategories(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<CPType> getCPTypes() {
		return _cpTypeServicesTracker.getCPTypes();
	}

	@Override
	public CPInstance getDefaultCPInstance(
			HttpServletRequest httpServletRequest)
		throws Exception {

		CPCatalogEntry cpCatalogEntry = getCPCatalogEntry(httpServletRequest);

		if (cpCatalogEntry == null) {
			return null;
		}

		if (!cpCatalogEntry.isIgnoreSKUCombinations()) {
			return null;
		}

		return _cpInstanceHelper.getCPInstance(
			cpCatalogEntry.getCPDefinitionId(), null);
	}

	@Override
	public CPSku getDefaultCPSku(CPCatalogEntry cpCatalogEntry)
		throws Exception {

		return _cpInstanceHelper.getDefaultCPSku(cpCatalogEntry);
	}

	@Override
	public String getDownloadFileEntryURL(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws PortalException {

		String downloadUrl = DLUtil.getDownloadURL(
			fileEntry, fileEntry.getLatestFileVersion(), themeDisplay,
			StringPool.BLANK, true, true);

		return downloadUrl;
	}

	@Override
	public String getFriendlyURL(
			CPCatalogEntry cpCatalogEntry, ThemeDisplay themeDisplay)
		throws PortalException {

		return _cpDefinitionHelper.getFriendlyURL(
			cpCatalogEntry.getCPDefinitionId(), themeDisplay);
	}

	@Override
	public List<CPAttachmentFileEntry> getImages(long cpDefinitionId)
		throws PortalException {

		long classNameId = _portal.getClassNameId(CPDefinition.class);

		return _cpAttachmentFileEntryService.getCPAttachmentFileEntries(
			classNameId, cpDefinitionId,
			CPAttachmentFileEntryConstants.TYPE_IMAGE,
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	@Override
	public String getImageURL(FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws Exception {

		return DLUtil.getDownloadURL(
			fileEntry, fileEntry.getFileVersion(), themeDisplay,
			StringPool.BLANK);
	}

	@Override
	public String getStockQuantityLabel(HttpServletRequest httpServletRequest)
		throws Exception {

		JSONObject stockQuantity = (JSONObject)getCPContentContributorValue(
			CPContentContributorConstants.STOCK_QUANTITY_NAME,
			httpServletRequest);

		if (stockQuantity == null) {
			return StringPool.BLANK;
		}

		return stockQuantity.getString(
			CPContentContributorConstants.STOCK_QUANTITY_NAME);
	}

	@Override
	public ResourceURL getViewAttachmentURL(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		ResourceURL resourceURL = liferayPortletResponse.createResourceURL();

		CPCatalogEntry cpCatalogEntry = getCPCatalogEntry(
			_portal.getHttpServletRequest(liferayPortletRequest));

		if (cpCatalogEntry != null) {
			resourceURL.setParameter(
				"cpDefinitionId",
				String.valueOf(cpCatalogEntry.getCPDefinitionId()));
		}

		resourceURL.setResourceID("viewCPAttachments");

		return resourceURL;
	}

	@Override
	public boolean hasCPDefinitionSpecificationOptionValues(long cpDefinitionId)
		throws PortalException {

		List<CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues =
				_cpCatalogEntrySpecificationOptionValueService.
					getCPDefinitionSpecificationOptionValues(cpDefinitionId);

		return !cpDefinitionSpecificationOptionValues.isEmpty();
	}

	@Override
	public void renderCPType(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		CPCatalogEntry cpCatalogEntry = getCPCatalogEntry(httpServletRequest);

		if (cpCatalogEntry == null) {
			return;
		}

		RenderRequest renderRequest =
			(RenderRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		CPContentRenderer cpContentRenderer =
			_cpContentRendererRegistry.getCPContentRenderer(
				getCPContentRendererKey(
					cpCatalogEntry.getProductTypeName(), renderRequest));

		if (cpContentRenderer != null) {
			cpContentRenderer.render(
				cpCatalogEntry, httpServletRequest, httpServletResponse);
		}
	}

	@Override
	public String renderOptions(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		CPCatalogEntry cpCatalogEntry = getCPCatalogEntry(
			_portal.getHttpServletRequest(renderRequest));

		if (cpCatalogEntry == null) {
			return StringPool.BLANK;
		}

		return _cpInstanceHelper.renderPublicStoreOptions(
			cpCatalogEntry.getCPDefinitionId(), null,
			cpCatalogEntry.isIgnoreSKUCombinations(), false, renderRequest,
			renderResponse);
	}

	@Reference
	private CPAttachmentFileEntryService _cpAttachmentFileEntryService;

	@Reference
	private CPDefinitionSpecificationOptionValueService
		_cpCatalogEntrySpecificationOptionValueService;

	@Reference
	private CPContentContributorRegistry _cpContentContributorRegistry;

	@Reference
	private CPContentRendererRegistry _cpContentRendererRegistry;

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private CPOptionCategoryLocalService _cpOptionCategoryLocalService;

	@Reference
	private CPTypeServicesTracker _cpTypeServicesTracker;

	@Reference
	private Portal _portal;

}