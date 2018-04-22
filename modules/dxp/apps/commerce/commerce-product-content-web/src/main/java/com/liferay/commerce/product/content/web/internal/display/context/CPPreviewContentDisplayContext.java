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

package com.liferay.commerce.product.content.web.internal.display.context;

import com.liferay.commerce.price.CommercePriceFormatter;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.search.CPInstanceIndexer;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.product.util.comparator.CPInstanceSkuComparator;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CPPreviewContentDisplayContext {

	public CPPreviewContentDisplayContext(
		CommercePriceFormatter commercePriceFormatter,
		CPDefinitionOptionRelService cpDefinitionOptionRelService,
		CPDefinitionService cpDefinitionService,
		CPInstanceHelper cpInstanceHelper, CPInstanceService cpInstanceService,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_commercePriceFormatter = commercePriceFormatter;
		_cpDefinitionOptionRelService = cpDefinitionOptionRelService;
		_cpDefinitionService = cpDefinitionService;
		_cpInstanceHelper = cpInstanceHelper;
		_cpInstanceService = cpInstanceService;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public CPDefinition getCPDefinition() throws PortalException {
		return _cpDefinitionService.fetchCPDefinition(getCPDefinitionId());
	}

	public long getCPDefinitionId() {
		return ParamUtil.getLong(_renderRequest, "cpDefinitionId");
	}

	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels()
		throws PortalException {

		return _cpDefinitionOptionRelService.getCPDefinitionOptionRels(
			getCPDefinitionId(), true);
	}

	public String getCPInstanceThumbnailSrc(
			CPInstance cpInstance, ThemeDisplay themeDisplay)
		throws Exception {

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			_cpInstanceHelper.getCPAttachmentFileEntries(
				cpInstance.getCPDefinitionId(), cpInstance.getDDMContent(),
				CPAttachmentFileEntryConstants.TYPE_IMAGE);

		if (cpAttachmentFileEntries.isEmpty()) {
			CPDefinition cpDefinition = cpInstance.getCPDefinition();

			return cpDefinition.getDefaultImageThumbnailSrc(themeDisplay);
		}

		CPAttachmentFileEntry cpAttachmentFileEntry =
			cpAttachmentFileEntries.get(0);

		FileEntry fileEntry = cpAttachmentFileEntry.getFileEntry();

		if (fileEntry == null) {
			return null;
		}

		return DLUtil.getThumbnailSrc(fileEntry, themeDisplay);
	}

	public String getFormattedPrice(BigDecimal price) throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _commercePriceFormatter.format(
			themeDisplay.getScopeGroupId(), price);
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		String redirect = ParamUtil.getString(_renderRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		if (getCPDefinitionId() > 0) {
			portletURL.setParameter(
				"cpDefinitionId", String.valueOf(getCPDefinitionId()));
		}

		String delta = ParamUtil.getString(_renderRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(_renderRequest, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		return portletURL;
	}

	public SearchContainer<CPInstance> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_searchContainer = new SearchContainer<>(
			_renderRequest, getPortletURL(), null, null);

		_searchContainer.setEmptyResultsMessage("there-are-no-skus");

		OrderByComparator<CPInstance> orderByComparator =
			new CPInstanceSkuComparator(true);

		_searchContainer.setOrderByComparator(orderByComparator);

		SearchContext searchContext = _buildSearchContext(
			themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
			WorkflowConstants.STATUS_APPROVED, _searchContainer.getStart(),
			_searchContainer.getEnd());

		BaseModelSearchResult<CPInstance> results =
			_cpInstanceService.searchCPInstances(searchContext);

		_searchContainer.setTotal(results.getLength());
		_searchContainer.setResults(results.getBaseModels());

		return _searchContainer;
	}

	public Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			parseCPInstanceDDMContent(long cpInstanceId)
		throws PortalException {

		if (cpInstanceId <= 0) {
			return Collections.emptyMap();
		}

		CPInstance cpInstance = _cpInstanceService.getCPInstance(cpInstanceId);

		return _cpInstanceHelper.getCPDefinitionOptionRelsMap(
			cpInstance.getDDMContent());
	}

	private SearchContext _buildSearchContext(
		long companyId, long groupId, int status, int start, int end) {

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(Field.STATUS, status);
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("purchasable", true);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setStart(start);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		Sort sort = SortFactoryUtil.create(
			CPInstanceIndexer.FIELD_SKU, Sort.STRING_TYPE, false);

		searchContext.setSorts(new Sort[] {sort});

		return searchContext;
	}

	private final CommercePriceFormatter _commercePriceFormatter;
	private final CPDefinitionOptionRelService _cpDefinitionOptionRelService;
	private final CPDefinitionService _cpDefinitionService;
	private final CPInstanceHelper _cpInstanceHelper;
	private final CPInstanceService _cpInstanceService;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<CPInstance> _searchContainer;

}