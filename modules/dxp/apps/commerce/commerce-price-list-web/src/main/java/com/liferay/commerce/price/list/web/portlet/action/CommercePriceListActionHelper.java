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

package com.liferay.commerce.price.list.web.portlet.action;

import com.liferay.commerce.price.list.constants.CommercePriceListWebKeys;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryService;
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
@Component(service = CommercePriceListActionHelper.class)
public class CommercePriceListActionHelper {

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

		CommercePriceEntry commercePriceEntry =
			(CommercePriceEntry)renderRequest.getAttribute(
				CommercePriceListWebKeys.COMMERCE_PRICE_ENTRY);

		if (commercePriceEntry != null) {
			return commercePriceEntry;
		}

		long commercePriceEntryId = ParamUtil.getLong(
			renderRequest, "commercePriceEntryId");

		if (commercePriceEntryId > 0) {
			commercePriceEntry =
				_commercePriceEntryService.fetchCommercePriceEntry(
					commercePriceEntryId);
		}

		if (commercePriceEntry != null) {
			renderRequest.setAttribute(
				CommercePriceListWebKeys.COMMERCE_PRICE_ENTRY,
				commercePriceEntry);
		}

		return commercePriceEntry;
	}

	public CommercePriceList getCommercePriceList(PortletRequest portletRequest)
		throws PortalException {

		CommercePriceList commercePriceList =
			(CommercePriceList)portletRequest.getAttribute(
				CommercePriceListWebKeys.COMMERCE_PRICE_LIST);

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
				CommercePriceListWebKeys.COMMERCE_PRICE_LIST,
				commercePriceList);
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

	public List<CommerceTierPriceEntry> getCommerceTierPriceEntries(
			PortletRequest portletRequest)
		throws PortalException {

		List<CommerceTierPriceEntry> commerceTierPriceEntries =
			new ArrayList<>();

		long[] commerceTierPriceEntryIds = ParamUtil.getLongValues(
			portletRequest, "rowIds");

		for (long commerceTierPriceEntryId : commerceTierPriceEntryIds) {
			CommerceTierPriceEntry commerceTierPriceEntry =
				_commerceTierPriceEntryService.fetchCommerceTierPriceEntry(
					commerceTierPriceEntryId);

			if (commerceTierPriceEntry != null) {
				commerceTierPriceEntries.add(commerceTierPriceEntry);
			}
		}

		return commerceTierPriceEntries;
	}

	public CommerceTierPriceEntry getCommerceTierPriceEntry(
		RenderRequest renderRequest) {

		long commerceTierPriceEntryId = ParamUtil.getLong(
			renderRequest, "commerceTierPriceEntryId");

		return _commerceTierPriceEntryService.fetchCommerceTierPriceEntry(
			commerceTierPriceEntryId);
	}

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private CommerceTierPriceEntryService _commerceTierPriceEntryService;

}