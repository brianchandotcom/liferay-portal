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

package com.liferay.commerce.order.content.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.exception.CommerceOrderImporterTypeException;
import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.importer.item.CommerceOrderImporterItem;
import com.liferay.commerce.order.importer.type.CommerceOrderImporterType;
import com.liferay.commerce.order.importer.type.CommerceOrderImporterTypeRegistry;
import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT,
		"mvc.command.name=/commerce_open_order_content/import_commerce_order_items"
	},
	service = MVCActionCommand.class
)
public class ImportCommerceOrderItemsMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		int importedRowsCount = 0;
		int notImportedRowsCount = 0;

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");
		String commerceOrderImporterTypeKey = ParamUtil.getString(
			actionRequest, "commerceOrderImporterTypeKey");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.IMPORT)) {
				CommerceOrderImporterType commerceOrderImporterType =
					_commerceOrderImporterTypeRegistry.
						getCommerceOrderImporterType(
							commerceOrderImporterTypeKey);

				commerceOrder.setManuallyAdjusted(true);

				commerceOrder = _commerceOrderService.updateCommerceOrder(
					commerceOrder);

				List<CommerceOrderImporterItem> commerceOrderImporterItems =
					commerceOrderImporterType.getCommerceOrderImporterItems(
						commerceOrder,
						commerceOrderImporterType.getCommerceOrderImporterItem(
							_portal.getHttpServletRequest(actionRequest)));

				for (CommerceOrderImporterItem commerceOrderImporterItem :
						commerceOrderImporterItems) {

					if (commerceOrderImporterItem.getQuantity() < 1) {
						notImportedRowsCount++;

						continue;
					}

					try {
						_commerceOrderItemService.addOrUpdateCommerceOrderItem(
							commerceOrderId,
							commerceOrderImporterItem.getCPInstanceId(),
							commerceOrderImporterItem.getJSON(),
							commerceOrderImporterItem.getQuantity(), 0,
							(CommerceContext)actionRequest.getAttribute(
								CommerceWebKeys.COMMERCE_CONTEXT),
							ServiceContextFactory.getInstance(
								CommerceOrderItem.class.getName(),
								actionRequest));

						importedRowsCount++;
					}
					catch (Exception exception) {
						if (exception instanceof
								CommerceOrderImporterTypeException ||
							exception instanceof NoSuchCPInstanceException ||
							exception instanceof PrincipalException) {

							notImportedRowsCount++;
						}
					}
				}
			}
		}
		catch (Exception exception) {
			if (exception instanceof CommerceOrderImporterTypeException ||
				exception instanceof NoSuchCPInstanceException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(
					actionRequest, CommerceOrderImporterTypeException.class,
					commerceOrderImporterTypeKey);

				sendRedirect(
					actionRequest, actionResponse,
					_getOrderDetailRedirect(commerceOrderId, actionRequest));
			}
			else if (exception instanceof NoSuchOrderException) {
				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw exception;
			}
		}
		finally {
			commerceOrder.setManuallyAdjusted(false);

			_commerceOrderService.updateCommerceOrder(commerceOrder);

			_commerceOrderService.recalculatePrice(
				commerceOrderId,
				(CommerceContext)actionRequest.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT));
		}

		hideDefaultErrorMessage(actionRequest);
		hideDefaultSuccessMessage(actionRequest);

		if (importedRowsCount > 0) {
			SessionMessages.add(
				actionRequest, "importedRowsCount", importedRowsCount);
		}

		if (notImportedRowsCount > 0) {
			SessionErrors.add(
				actionRequest, "notImportedRowsCount", notImportedRowsCount);
		}

		sendRedirect(
			actionRequest, actionResponse,
			_getOrderDetailRedirect(commerceOrderId, actionRequest));
	}

	private String _getOrderDetailRedirect(
			long commerceOrderId, ActionRequest actionRequest)
		throws Exception {

		return PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				actionRequest, CommerceOrder.class.getName(),
				PortletProvider.Action.EDIT)
		).setMVCRenderCommandName(
			"/commerce_open_order_content/edit_commerce_order"
		).setBackURL(
			ParamUtil.getString(actionRequest, "backURL")
		).setParameter(
			"commerceOrderId", commerceOrderId
		).buildString();
	}

	@Reference
	private CommerceOrderImporterTypeRegistry
		_commerceOrderImporterTypeRegistry;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private Portal _portal;

}