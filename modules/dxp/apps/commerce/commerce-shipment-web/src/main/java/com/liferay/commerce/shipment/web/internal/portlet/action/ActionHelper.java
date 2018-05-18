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

package com.liferay.commerce.shipment.web.internal.portlet.action;

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.CommerceShipmentItemLocalService;
import com.liferay.commerce.service.CommerceShipmentLocalService;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = ActionHelper.class)
public class ActionHelper {

	public CommerceShipment getCommerceShipment(RenderRequest renderRequest)
		throws PortalException {

		CommerceShipment commerceShipment =
			(CommerceShipment)renderRequest.getAttribute(
				CommerceWebKeys.COMMERCE_SHIPMENT);

		if (commerceShipment != null) {
			return commerceShipment;
		}

		long commerceShipmentId = ParamUtil.getLong(
			renderRequest, "commerceShipmentId");

		if (commerceShipmentId > 0) {
			commerceShipment =
				_commerceShipmentLocalService.fetchCommerceShipment(
					commerceShipmentId);
		}

		if (commerceShipment != null) {
			renderRequest.setAttribute(
				CommerceWebKeys.COMMERCE_SHIPMENT, commerceShipment);
		}

		return commerceShipment;
	}

	public CommerceShipmentItem getCommerceShipmentItem(
			RenderRequest renderRequest)
		throws PortalException {

		CommerceShipmentItem commerceShipmentItem =
			(CommerceShipmentItem)renderRequest.getAttribute(
				CommerceWebKeys.COMMERCE_SHIPMENT_ITEM);

		if (commerceShipmentItem != null) {
			return commerceShipmentItem;
		}

		long commerceShipmentItemId = ParamUtil.getLong(
			renderRequest, "commerceShipmentItemId");

		if (commerceShipmentItemId > 0) {
			commerceShipmentItem =
				_commerceShipmentItemLocalService.fetchCommerceShipmentItem(
					commerceShipmentItemId);
		}

		if (commerceShipmentItem != null) {
			renderRequest.setAttribute(
				CommerceWebKeys.COMMERCE_SHIPMENT_ITEM, commerceShipmentItem);
		}

		return commerceShipmentItem;
	}

	public List<CommerceShipmentItem> getCommerceShipmentItems(
			ResourceRequest resourceRequest)
		throws PortalException {

		List<CommerceShipmentItem> commerceShipmentItems = new ArrayList<>();

		long[] commerceShipmentItemIds = ParamUtil.getLongValues(
			resourceRequest, RowChecker.ROW_IDS);

		for (long commerceShipmentItemId : commerceShipmentItemIds) {
			CommerceShipmentItem commerceShipmentItem =
				_commerceShipmentItemLocalService.getCommerceShipmentItem(
					commerceShipmentItemId);

			commerceShipmentItems.add(commerceShipmentItem);
		}

		return commerceShipmentItems;
	}

	public List<CommerceShipment> getCommerceShipments(
			ResourceRequest resourceRequest)
		throws PortalException {

		List<CommerceShipment> commerceShipments = new ArrayList<>();

		long[] commerceShipmentIds = ParamUtil.getLongValues(
			resourceRequest, RowChecker.ROW_IDS);

		for (long commerceShipmentId : commerceShipmentIds) {
			CommerceShipment commerceShipment =
				_commerceShipmentLocalService.getCommerceShipment(
					commerceShipmentId);

			commerceShipments.add(commerceShipment);
		}

		return commerceShipments;
	}

	@Reference
	private CommerceShipmentItemLocalService _commerceShipmentItemLocalService;

	@Reference
	private CommerceShipmentLocalService _commerceShipmentLocalService;

}