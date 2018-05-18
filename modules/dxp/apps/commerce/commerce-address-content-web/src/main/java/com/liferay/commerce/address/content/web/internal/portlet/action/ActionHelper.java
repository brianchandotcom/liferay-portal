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

package com.liferay.commerce.address.content.web.internal.portlet.action;

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.RenderRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = ActionHelper.class)
public class ActionHelper {

	public CommerceAddress getCommerceAddress(RenderRequest renderRequest)
		throws PortalException {

		CommerceAddress commerceAddress =
			(CommerceAddress)renderRequest.getAttribute(
				CommerceWebKeys.COMMERCE_ADDRESS);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		long commerceAddressId = ParamUtil.getLong(
			renderRequest, "commerceAddressId");

		if (commerceAddressId > 0) {
			commerceAddress = _commerceAddressService.fetchCommerceAddress(
				commerceAddressId);
		}

		if (commerceAddress != null) {
			renderRequest.setAttribute(
				CommerceWebKeys.COMMERCE_ADDRESS, commerceAddress);
		}

		return commerceAddress;
	}

	@Reference
	private CommerceAddressService _commerceAddressService;

}