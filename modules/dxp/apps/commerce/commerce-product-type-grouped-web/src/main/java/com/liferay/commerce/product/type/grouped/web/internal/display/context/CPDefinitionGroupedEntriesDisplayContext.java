/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.type.grouped.web.internal.display.context;

import com.liferay.commerce.product.definitions.web.display.context.BaseCPDefinitionsSearchContainerDisplayContext;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.item.selector.criterion.CPDefinitionItemSelectorCriterion;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryService;
import com.liferay.commerce.product.type.grouped.web.internal.util.GroupedCPTypeUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
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
				getCheckedCPDefinitionIds(cpDefinitionId));

			String disabledCPDefinitionIds = StringUtil.merge(
				getDisabledCPDefinitionIds(cpDefinitionId));

			itemSelectorURL.setParameter(
				"checkedCPDefinitionIds", checkedCPDefinitionIds);
			itemSelectorURL.setParameter(
				"disabledCPDefinitionIds", disabledCPDefinitionIds);
		}

		return itemSelectorURL.toString();
	}

	public String getLabel(Locale locale, String key) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return ResourceBundleUtil.getString(resourceBundle, key);
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "editProductDefinition");
		portletURL.setParameter(
			"screenNavigationCategoryKey", getScreenNavigationCategoryKey());

		return portletURL;
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		CPType cpType = null;

		try {
			cpType = getCPType();
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
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

		int total =
			_cpDefinitionGroupedEntryService.getCPDefinitionGroupedEntriesCount(
				getCPDefinitionId());

		searchContainer.setTotal(total);

		List<CPDefinitionGroupedEntry> results =
			_cpDefinitionGroupedEntryService.getCPDefinitionGroupedEntries(
				getCPDefinitionId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	protected long[] getCheckedCPDefinitionIds(long cpDefinitionId)
		throws PortalException {

		List<Long> cpDefinitionIdsList = new ArrayList<>();

		List<CPDefinitionGroupedEntry> cpDefinitionGroupedEntries =
			getCPDefinitionGroupedEntries(cpDefinitionId);

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

	protected List<CPDefinitionGroupedEntry> getCPDefinitionGroupedEntries(
			long cpDefinitionId)
		throws PortalException {

		int total =
			_cpDefinitionGroupedEntryService.getCPDefinitionGroupedEntriesCount(
				cpDefinitionId);

		return _cpDefinitionGroupedEntryService.getCPDefinitionGroupedEntries(
			cpDefinitionId, 0, total, null);
	}

	protected long[] getDisabledCPDefinitionIds(long cpDefinitionId)
		throws PortalException {

		List<Long> cpDefinitionIdsList = new ArrayList<>();

		List<CPDefinitionGroupedEntry> cpDefinitionGroupedEntries =
			getCPDefinitionGroupedEntries(cpDefinitionId);

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