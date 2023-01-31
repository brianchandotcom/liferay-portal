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

package com.liferay.commerce.product.item.selector.web.internal.display.context;

import com.liferay.commerce.product.item.selector.web.internal.LayoutPageTemplateEntryItemSelectorView;
import com.liferay.commerce.product.item.selector.web.internal.util.CPItemSelectorViewUtil;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class LayoutPageTemplateEntryItemSelectorViewDisplayContext
	extends BaseCPItemSelectorViewDisplayContext<LayoutPageTemplateEntry> {

	public LayoutPageTemplateEntryItemSelectorViewDisplayContext(
		LayoutPageTemplateEntryService layoutPageTemplateEntryService,
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		String itemSelectedEventName) {

		super(
			httpServletRequest, portletURL, itemSelectedEventName,
			LayoutPageTemplateEntryItemSelectorView.class.getSimpleName());

		_layoutPageTemplateEntryService = layoutPageTemplateEntryService;

		setDefaultOrderByCol("name");
	}

	public long getLayoutPageTemplateEntryId() {
		return ParamUtil.getLong(
			httpServletRequest, "layoutPageTemplateEntryId");
	}

	@Override
	public PortletURL getPortletURL() {
		PortletURL portletURL = super.getPortletURL();

		long layoutPageTemplateEntryId = getLayoutPageTemplateEntryId();

		if (layoutPageTemplateEntryId > 0) {
			portletURL.setParameter(
				"layoutPageTemplateEntryId",
				String.valueOf(layoutPageTemplateEntryId));
		}

		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");

		portletURL.setParameter(
			"commerceChannelId", String.valueOf(commerceChannelId));

		long commerceChannelSiteGroupId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelSiteGroupId");

		portletURL.setParameter(
			"commerceChannelSiteGroupId",
			String.valueOf(commerceChannelSiteGroupId));

		return portletURL;
	}

	@Override
	public SearchContainer<LayoutPageTemplateEntry> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null,
			"there-are-no-display-page-templates");

		searchContainer.setOrderByCol(getOrderByCol());

		OrderByComparator<LayoutPageTemplateEntry>
			layoutPageTemplateEntryOrderByComparator =
				CPItemSelectorViewUtil.
					getLayoutPageTemplateEntryOrderByComparator(
						getOrderByCol(), getOrderByType());

		searchContainer.setOrderByComparator(
			layoutPageTemplateEntryOrderByComparator);

		searchContainer.setOrderByType(getOrderByType());

		long commerceChannelSiteGroupId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelSiteGroupId");

		List<LayoutPageTemplateEntry> layoutPageTemplateEntries =
			_layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
				commerceChannelSiteGroupId,
				ClassNameLocalServiceUtil.getClassNameId(CPDefinition.class), 0,
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE,
				WorkflowConstants.STATUS_APPROVED, searchContainer.getStart(),
				searchContainer.getEnd(),
				layoutPageTemplateEntryOrderByComparator);

		searchContainer.setResultsAndTotal(layoutPageTemplateEntries);

		return searchContainer;
	}

	public boolean isSingleSelection() {
		return true;
	}

	private final LayoutPageTemplateEntryService
		_layoutPageTemplateEntryService;

}