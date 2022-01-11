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

package com.liferay.commerce.product.type.grouped.web.internal.display.context;

import com.liferay.commerce.product.display.context.BaseCPDefinitionsSearchContainerDisplayContext;
import com.liferay.commerce.product.item.selector.criterion.CPDefinitionItemSelectorCriterion;
import com.liferay.commerce.product.portlet.action.ActionHelper;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryService;
import com.liferay.commerce.product.type.grouped.web.internal.util.GroupedCPTypeUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CPDefinitionGroupedEntriesDisplayContext
	extends BaseCPDefinitionsSearchContainerDisplayContext
		<CPDefinitionGroupedEntry> {

	public CPDefinitionGroupedEntriesDisplayContext(
		ActionHelper actionHelper, HttpServletRequest httpServletRequest,
		CPDefinitionGroupedEntryService cpDefinitionGroupedEntryService,
		ItemSelector itemSelector) {

		super(
			actionHelper, httpServletRequest,
			CPDefinitionGroupedEntry.class.getSimpleName());

		setDefaultOrderByCol("priority");
		setDefaultOrderByType("asc");

		_cpDefinitionGroupedEntryService = cpDefinitionGroupedEntryService;
		_itemSelector = itemSelector;
	}

	public CPDefinitionGroupedEntry getCPDefinitionGroupedEntry()
		throws PortalException {

		if (_cpDefinitionGroupedEntry != null) {
			return _cpDefinitionGroupedEntry;
		}

		long cpDefinitionGroupedEntryId = ParamUtil.getLong(
			cpRequestHelper.getRenderRequest(), "cpDefinitionGroupedEntryId");

		_cpDefinitionGroupedEntry =
			_cpDefinitionGroupedEntryService.getCPDefinitionGroupedEntry(
				cpDefinitionGroupedEntryId);

		return _cpDefinitionGroupedEntry;
	}

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				cpRequestHelper.getRenderRequest());

		CPDefinitionItemSelectorCriterion cpDefinitionItemSelectorCriterion =
			new CPDefinitionItemSelectorCriterion();

		cpDefinitionItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "productDefinitionsSelectItem",
			cpDefinitionItemSelectorCriterion);

		long cpDefinitionId = getCPDefinitionId();

		if (cpDefinitionId > 0) {
			itemSelectorURL.setParameter(
				"cpDefinitionId", String.valueOf(cpDefinitionId));

			String checkedCPDefinitionIds = StringUtil.merge(
				_getCheckedCPDefinitionIds(cpDefinitionId));

			String disabledCPDefinitionIds = StringUtil.merge(
				_getDisabledCPDefinitionIds(cpDefinitionId));

			itemSelectorURL.setParameter(
				"checkedCPDefinitionIds", checkedCPDefinitionIds);
			itemSelectorURL.setParameter(
				"disabledCPDefinitionIds", disabledCPDefinitionIds);
		}

		return itemSelectorURL.toString();
	}

	public String getLabel(Locale locale, String key) {
		return ResourceBundleUtil.getString(_getResourceBundle(locale), key);
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		return PortletURLBuilder.create(
			super.getPortletURL()
		).setMVCRenderCommandName(
			"/cp_definitions/edit_cp_definition"
		).setParameter(
			"screenNavigationCategoryKey", getScreenNavigationCategoryKey()
		).buildPortletURL();
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		CPType cpType = null;

		try {
			cpType = getCPType();
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		if (cpType != null) {
			return cpType.getName();
		}

		return super.getScreenNavigationCategoryKey();
	}

	@Override
	public SearchContainer<CPDefinitionGroupedEntry> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("no-grouped-entries-were-found");

		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator =
			GroupedCPTypeUtil.getCPDefinitionGroupedEntryOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		searchContainer.setResultsAndTotal(
			() ->
				_cpDefinitionGroupedEntryService.getCPDefinitionGroupedEntries(
					getCPDefinitionId(), searchContainer.getStart(),
					searchContainer.getEnd(), orderByComparator),
			_cpDefinitionGroupedEntryService.getCPDefinitionGroupedEntriesCount(
				getCPDefinitionId()));

		return searchContainer;
	}

	private long[] _getCheckedCPDefinitionIds(long cpDefinitionId)
		throws PortalException {

		List<Long> cpDefinitionIdsList = new ArrayList<>();

		List<CPDefinitionGroupedEntry> cpDefinitionGroupedEntries =
			_getCPDefinitionGroupedEntries(cpDefinitionId);

		for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry :
				cpDefinitionGroupedEntries) {

			cpDefinitionIdsList.add(
				cpDefinitionGroupedEntry.getEntryCPDefinitionId());
		}

		if (!cpDefinitionIdsList.isEmpty()) {
			return ArrayUtil.toLongArray(cpDefinitionIdsList);
		}

		return new long[0];
	}

	private List<CPDefinitionGroupedEntry> _getCPDefinitionGroupedEntries(
			long cpDefinitionId)
		throws PortalException {

		int total =
			_cpDefinitionGroupedEntryService.getCPDefinitionGroupedEntriesCount(
				cpDefinitionId);

		return _cpDefinitionGroupedEntryService.getCPDefinitionGroupedEntries(
			cpDefinitionId, 0, total, null);
	}

	private long[] _getDisabledCPDefinitionIds(long cpDefinitionId)
		throws PortalException {

		List<Long> cpDefinitionIdsList = new ArrayList<>();

		List<CPDefinitionGroupedEntry> cpDefinitionGroupedEntries =
			_getCPDefinitionGroupedEntries(cpDefinitionId);

		for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry :
				cpDefinitionGroupedEntries) {

			cpDefinitionIdsList.add(
				cpDefinitionGroupedEntry.getCPDefinitionId());
		}

		if (!cpDefinitionIdsList.isEmpty()) {
			return ArrayUtil.toLongArray(cpDefinitionIdsList);
		}

		return new long[0];
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionGroupedEntriesDisplayContext.class);

	private CPDefinitionGroupedEntry _cpDefinitionGroupedEntry;
	private final CPDefinitionGroupedEntryService
		_cpDefinitionGroupedEntryService;
	private final ItemSelector _itemSelector;

}