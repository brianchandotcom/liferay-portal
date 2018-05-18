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