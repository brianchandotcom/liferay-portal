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

package com.liferay.commerce.checkout.web.internal.portlet;

import com.liferay.account.constants.AccountConstants;
import com.liferay.commerce.checkout.web.internal.display.context.CheckoutDisplayContext;
import com.liferay.commerce.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.util.CommerceCheckoutStepRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cookies.CookiesManagerUtil;
import com.liferay.portal.kernel.cookies.constants.CookiesConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-commerce-checkout",
		"com.liferay.portlet.display-category=commerce",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Checkout",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_CHECKOUT,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class CommerceCheckoutPortlet extends MVCPortlet {

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		try {
			actionRequest.setAttribute(
				CommerceCheckoutWebKeys.COMMERCE_ORDER,
				_getCommerceOrder(actionRequest));
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}

		super.processAction(actionRequest, actionResponse);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			CommerceOrder commerceOrder = _getCommerceOrder(renderRequest);

			if (commerceOrder != null) {
				renderRequest.setAttribute(
					CommerceCheckoutWebKeys.COMMERCE_ORDER, commerceOrder);

				HttpServletRequest httpServletRequest =
					_portal.getHttpServletRequest(renderRequest);
				HttpServletResponse httpServletResponse =
					_portal.getHttpServletResponse(renderResponse);

				boolean continueAsGuest = GetterUtil.getBoolean(
					CookiesManagerUtil.getCookieValue(
						CookiesConstants.NAME_COMMERCE_CONTINUE_AS_GUEST,
						httpServletRequest));

				if ((commerceOrder.getCommerceAccountId() ==
						AccountConstants.ACCOUNT_ENTRY_ID_GUEST) &&
					!continueAsGuest) {

					httpServletResponse.sendRedirect(
						_getCheckoutURL(renderRequest));
				}
				else if ((commerceOrder.isOpen() &&
						  !_isOrderApproved(commerceOrder)) ||
						 !_commerceOrderValidatorRegistry.isValid(
							 LocaleUtil.getSiteDefault(), commerceOrder)) {

					httpServletResponse.sendRedirect(
						_getOrderDetailsURL(renderRequest, commerceOrder));
				}
				else if (!commerceOrder.isOpen() &&
						 (continueAsGuest || commerceOrder.isGuestOrder())) {

					CookiesManagerUtil.deleteCookies(
						CookiesManagerUtil.getDomain(httpServletRequest),
						httpServletRequest, httpServletResponse,
						CommerceOrder.class.getName() + StringPool.POUND +
							commerceOrder.getGroupId());

					CookiesManagerUtil.deleteCookies(
						CookiesManagerUtil.getDomain(httpServletRequest),
						httpServletRequest, httpServletResponse,
						CookiesConstants.NAME_COMMERCE_CONTINUE_AS_GUEST);
				}
			}

			CheckoutDisplayContext checkoutDisplayContext =
				new CheckoutDisplayContext(
					_commerceCheckoutStepRegistry,
					_portal.getLiferayPortletRequest(renderRequest),
					_portal.getLiferayPortletResponse(renderResponse), _portal);

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT, checkoutDisplayContext);

			super.render(renderRequest, renderResponse);
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	private String _getCheckoutURL(PortletRequest portletRequest)
		throws PortalException {

		PortletURL portletURL =
			_commerceOrderHttpHelper.getCommerceCheckoutPortletURL(
				_portal.getHttpServletRequest(portletRequest));

		if (portletURL == null) {
			return StringPool.BLANK;
		}

		return portletURL.toString();
	}

	private CommerceOrder _getCommerceOrder(PortletRequest portletRequest)
		throws PortalException {

		String commerceOrderUuid = ParamUtil.getString(
			portletRequest, "commerceOrderUuid");

		if (Validator.isNotNull(commerceOrderUuid)) {
			long groupId =
				_commerceChannelLocalService.
					getCommerceChannelGroupIdBySiteGroupId(
						_portal.getScopeGroupId(portletRequest));

			return _commerceOrderService.getCommerceOrderByUuidAndGroupId(
				commerceOrderUuid, groupId);
		}

		return _commerceOrderHttpHelper.getCurrentCommerceOrder(
			_portal.getHttpServletRequest(portletRequest));
	}

	private String _getOrderDetailsURL(
			PortletRequest portletRequest, CommerceOrder commerceOrder)
		throws PortalException {

		PortletURL portletURL =
			_commerceOrderHttpHelper.getCommerceCartPortletURL(
				_portal.getHttpServletRequest(portletRequest), commerceOrder);

		if (portletURL == null) {
			return StringPool.BLANK;
		}

		return portletURL.toString();
	}

	private boolean _isOrderApproved(CommerceOrder commerceOrder)
		throws PortalException {

		WorkflowInstanceLink workflowInstanceLink =
			_workflowInstanceLinkLocalService.fetchWorkflowInstanceLink(
				commerceOrder.getCompanyId(), commerceOrder.getGroupId(),
				CommerceOrder.class.getName(),
				commerceOrder.getCommerceOrderId());

		if ((workflowInstanceLink != null) &&
			(commerceOrder.getStatus() != WorkflowConstants.STATUS_APPROVED)) {

			return false;
		}

		WorkflowDefinitionLink workflowDefinitionLink =
			_workflowDefinitionLinkLocalService.fetchWorkflowDefinitionLink(
				commerceOrder.getCompanyId(), commerceOrder.getGroupId(),
				CommerceOrder.class.getName(), 0,
				CommerceOrderConstants.TYPE_PK_APPROVAL, true);

		if ((workflowDefinitionLink != null) &&
			(commerceOrder.getStatus() != WorkflowConstants.STATUS_APPROVED)) {

			return false;
		}

		return true;
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceCheckoutStepRegistry _commerceCheckoutStepRegistry;

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrderValidatorRegistry _commerceOrderValidatorRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}