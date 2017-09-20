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

package com.liferay.commerce.price.list.web.internal.portlet.action;

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.model.CommercePriceEntry;
import com.liferay.commerce.model.CommercePriceList;
import com.liferay.commerce.model.CommerceTirePriceEntry;
import com.liferay.commerce.service.CommercePriceEntryService;
import com.liferay.commerce.service.CommercePriceListService;
import com.liferay.commerce.service.CommerceTirePriceEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = ActionHelper.class)
public class ActionHelper {

	public List<CommercePriceEntry> getCommercePriceEntries(
			PortletRequest portletRequest)
		throws PortalException {

		List<CommercePriceEntry> commercePriceEntries = new ArrayList<>();

		long[] commercePriceEntryIds = ParamUtil.getLongValues(
			portletRequest, "rowIds");

		for (long commercePriceEntryId : commercePriceEntryIds) {
			CommercePriceEntry commercePriceEntry =
				_commercePriceEntryService.fetchCommercePriceEntry(
					commercePriceEntryId);

			if (commercePriceEntry != null) {
				commercePriceEntries.add(commercePriceEntry);
			}
		}

		return commercePriceEntries;
	}

	public CommercePriceEntry getCommercePriceEntry(
		RenderRequest renderRequest) {

		long commercePriceEntryId = ParamUtil.getLong(
			renderRequest, "commercePriceEntryId");

		return _commercePriceEntryService.fetchCommercePriceEntry(
			commercePriceEntryId);
	}

	public CommercePriceList getCommercePriceList(PortletRequest portletRequest)
		throws PortalException {

		CommercePriceList commercePriceList =
			(CommercePriceList)portletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_PRICE_LIST);

		if (commercePriceList != null) {
			return commercePriceList;
		}

		long commercePriceListId = ParamUtil.getLong(
			portletRequest, "commercePriceListId");

		if (commercePriceListId > 0) {
			commercePriceList =
				_commercePriceListService.fetchCommercePriceList(
					commercePriceListId);
		}

		if (commercePriceList != null) {
			portletRequest.setAttribute(
				CommerceWebKeys.COMMERCE_PRICE_LIST, commercePriceList);
		}

		return commercePriceList;
	}

	public List<CommercePriceList> getCommercePriceLists(
			PortletRequest portletRequest)
		throws PortalException {

		List<CommercePriceList> commercePriceLists = new ArrayList<>();

		long[] commercePriceListIds = ParamUtil.getLongValues(
			portletRequest, "rowIds");

		for (long commercePriceListId : commercePriceListIds) {
			CommercePriceList commercePriceList =
				_commercePriceListService.fetchCommercePriceList(
					commercePriceListId);

			if (commercePriceList != null) {
				commercePriceLists.add(commercePriceList);
			}
		}

		return commercePriceLists;
	}

	public List<CommerceTirePriceEntry> getCommerceTirePriceEntries(
			PortletRequest portletRequest)
		throws PortalException {

		List<CommerceTirePriceEntry> commerceTirePriceEntries =
			new ArrayList<>();

		long[] commerceTirePriceEntryIds = ParamUtil.getLongValues(
			portletRequest, "rowIds");

		for (long commerceTirePriceEntryId : commerceTirePriceEntryIds) {
			CommerceTirePriceEntry commerceTirePriceEntry =
				_commerceTirePriceEntryService.fetchCommerceTirePriceEntry(
					commerceTirePriceEntryId);

			if (commerceTirePriceEntry != null) {
				commerceTirePriceEntries.add(commerceTirePriceEntry);
			}
		}

		return commerceTirePriceEntries;
	}

	public CommerceTirePriceEntry getCommerceTirePriceEntry(
		RenderRequest renderRequest) {

		long commerceTirePriceEntryId = ParamUtil.getLong(
			renderRequest, "commerceTirePriceEntryId");

		return _commerceTirePriceEntryService.fetchCommerceTirePriceEntry(
			commerceTirePriceEntryId);
	}

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private CommerceTirePriceEntryService _commerceTirePriceEntryService;

}