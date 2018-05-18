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

package com.liferay.commerce.cart.content.web.internal.portlet.action;

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderService;
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

	public CommerceOrder getCommerceOrder(RenderRequest renderRequest)
		throws PortalException {

		CommerceOrder commerceOrder = (CommerceOrder)renderRequest.getAttribute(
			CommerceWebKeys.COMMERCE_ORDER);

		if (commerceOrder != null) {
			return commerceOrder;
		}

		long commerceOrderId = ParamUtil.getLong(
			renderRequest, "commerceOrderId");

		if (commerceOrderId > 0) {
			commerceOrder = _commerceOrderService.fetchCommerceOrder(
				commerceOrderId);
		}

		if (commerceOrder != null) {
			renderRequest.setAttribute(
				CommerceWebKeys.COMMERCE_ORDER, commerceOrder);
		}

		return commerceOrder;
	}

	@Reference
	private CommerceOrderService _commerceOrderService;

}