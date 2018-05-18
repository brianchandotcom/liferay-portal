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

package com.liferay.commerce.organization.order.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.organization.service.CommerceOrganizationLocalService;
import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_CART_CONTENT_MINI,
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_ORGANIZATION_OPEN_ORDER,
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_ORGANIZATION_ORDER,
		"mvc.command.name=editCommerceOrder"
	},
	service = MVCActionCommand.class
)
public class EditCommerceOrderMVCActionCommand extends BaseMVCActionCommand {

	protected void addCommerceOrder(ActionRequest actionRequest)
		throws Exception {

		long organizationId = ParamUtil.getLong(
			actionRequest, "organizationId");
		long shippingAddressId = ParamUtil.getLong(
			actionRequest, "shippingAddressId");
		String purchaseOrderNumber = ParamUtil.getString(
			actionRequest, "purchaseOrderNumber");

		Organization organization =
			_commerceOrganizationService.getOrganization(organizationId);

		Organization accountOrganization =
			_commerceOrganizationLocalService.getAccountOrganization(
				organization.getOrganizationId());

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_commerceOrderService.addOrganizationCommerceOrder(
			organization.getGroupId(), themeDisplay.getSiteGroupId(),
			accountOrganization.getOrganizationId(), shippingAddressId,
			purchaseOrderNumber);
	}

	protected void approveCommerceOrder(long commerceOrderId) throws Exception {
		_commerceOrderService.approveCommerceOrder(commerceOrderId);
	}

	protected void checkoutCommerceOrder(
			ActionRequest actionRequest, long commerceOrderId)
		throws Exception {

		PortletURL portletURL =
			_commerceOrderHttpHelper.getCommerceCheckoutPortletURL(
				_portal.getHttpServletRequest(actionRequest));

		portletURL.setParameter(
			"commerceOrderId", String.valueOf(commerceOrderId));

		actionRequest.setAttribute(WebKeys.REDIRECT, portletURL.toString());
	}

	protected void checkoutOrSubmitCommerceOrder(
			ActionRequest actionRequest, CommerceOrder commerceOrder)
		throws Exception {

		if (commerceOrder.isOpen() && !commerceOrder.isPending()) {
			checkoutCommerceOrder(
				actionRequest, commerceOrder.getCommerceOrderId());

			return;
		}

		PortletURL portletURL = null;

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		long plid = _portal.getPlidFromPortletId(
			groupId, CommercePortletKeys.COMMERCE_ORGANIZATION_ORDER);

		if (plid > 0) {
			portletURL = _portletURLFactory.create(
				httpServletRequest,
				CommercePortletKeys.COMMERCE_ORGANIZATION_ORDER, plid,
				PortletRequest.RENDER_PHASE);
		}
		else {
			portletURL = _portletURLFactory.create(
				httpServletRequest,
				CommercePortletKeys.COMMERCE_ORGANIZATION_ORDER,
				PortletRequest.RENDER_PHASE);
		}

		actionRequest.setAttribute(WebKeys.REDIRECT, portletURL.toString());
	}

	protected void deleteCommerceOrders(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCommerceOrderIds = null;

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		if (commerceOrderId > 0) {
			deleteCommerceOrderIds = new long[] {commerceOrderId};
		}
		else {
			deleteCommerceOrderIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCommerceOrderIds"),
				0L);
		}

		for (long deleteCommerceOrderId : deleteCommerceOrderIds) {
			_commerceOrderService.deleteCommerceOrder(deleteCommerceOrderId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD)) {
				addCommerceOrder(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceOrders(actionRequest);
			}
			else if (cmd.equals("reorder")) {
				reorderCommerceOrder(actionRequest);
			}
			else if (cmd.equals("setCurrent")) {
				setCurrentCommerceOrder(actionRequest);
			}
			else if (cmd.equals("transition")) {
				executeTransition(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchOrderException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	protected void executeTransition(ActionRequest actionRequest)
		throws Exception {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		long workflowTaskId = ParamUtil.getLong(
			actionRequest, "workflowTaskId");
		String transitionName = ParamUtil.getString(
			actionRequest, "transitionName");

		if (workflowTaskId > 0) {
			executeWorkflowTransition(
				actionRequest, commerceOrderId, transitionName, workflowTaskId);
		}
		else if (transitionName.equals("approve") ||
				 transitionName.equals("force-approve")) {

			approveCommerceOrder(commerceOrderId);
		}
		else if (transitionName.equals("checkout")) {
			checkoutCommerceOrder(actionRequest, commerceOrderId);
		}
		else if (transitionName.equals("reorder")) {
			reorderCommerceOrder(actionRequest);
		}
		else if (transitionName.equals("submit")) {
			submitCommerceOrder(commerceOrderId);
		}

		hideDefaultSuccessMessage(actionRequest);
	}

	protected void executeWorkflowTransition(
			ActionRequest actionRequest, long commerceOrderId,
			String transitionName, long workflowTaskId)
		throws Exception {

		String comment = ParamUtil.getString(actionRequest, "comment");

		_commerceOrderService.executeWorkflowTransition(
			commerceOrderId, workflowTaskId, transitionName, comment);
	}

	protected void reorderCommerceOrder(ActionRequest actionRequest)
		throws Exception {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		reorderCommerceOrder(actionRequest, commerceOrderId);
	}

	protected void reorderCommerceOrder(
			ActionRequest actionRequest, long commerceOrderId)
		throws Exception {

		CommerceContext commerceContext =
			(CommerceContext)actionRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		CommerceOrder commerceOrder =
			_commerceOrderService.reorderCommerceOrder(
				commerceOrderId, commerceContext);

		checkoutOrSubmitCommerceOrder(actionRequest, commerceOrder);
	}

	protected void setCurrentCommerceOrder(ActionRequest actionRequest)
		throws Exception {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		_commerceOrderHttpHelper.setCurrentCommerceOrder(
			_portal.getHttpServletRequest(actionRequest), commerceOrder);
	}

	protected void submitCommerceOrder(long commerceOrderId) throws Exception {
		_commerceOrderService.submitCommerceOrder(commerceOrderId);
	}

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrganizationLocalService _commerceOrganizationLocalService;

	@Reference
	private CommerceOrganizationService _commerceOrganizationService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}