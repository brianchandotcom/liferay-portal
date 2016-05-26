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

package com.liferay.shopping.web.portlet.action;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;
import com.liferay.shopping.configuration.ShoppingGroupServiceOverriddenConfiguration;
import com.liferay.shopping.constants.ShoppingConstants;
import com.liferay.shopping.constants.ShoppingPortletKeys;
import com.liferay.shopping.exception.NoSuchOrderException;
import com.liferay.shopping.model.ShoppingOrder;
import com.liferay.shopping.service.ShoppingOrderLocalService;
import com.liferay.shopping.util.ShoppingUtil;

import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.URL;
import java.net.URLConnection;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Fellwock
 */
@Component(
	property = {
		"javax.portlet.name=" + ShoppingPortletKeys.SHOPPING,
		"javax.portlet.name=" + ShoppingPortletKeys.SHOPPING_ADMIN,
		"path=/shopping/notify"
	},
	service = StrutsAction.class
)
public class PayPalNotificationAction extends BaseStrutsAction {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String invoice = null;

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Receiving notification from PayPal");
			}

			String query = "cmd=_notify-validate";

			Enumeration<String> enu = request.getParameterNames();

			while (enu.hasMoreElements()) {
				String name = enu.nextElement();

				String value = request.getParameter(name);

				query = query + "&" + name + "=" + HttpUtil.encodeURL(value);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Sending response to PayPal " + query);
			}

			URL url = new URL("https://www.paypal.com/cgi-bin/webscr");

			URLConnection urlc = url.openConnection();

			urlc.setDoOutput(true);
			urlc.setRequestProperty(
				"Content-Type", "application/x-www-form-urlencoded");

			try (PrintWriter pw = UnsyncPrintWriterPool.borrow(
					urlc.getOutputStream())) {

				pw.println(query);
			}

			String payPalStatus = null;

			try (UnsyncBufferedReader unsyncBufferedReader =
					new UnsyncBufferedReader(
						new InputStreamReader(urlc.getInputStream()))) {

				payPalStatus = unsyncBufferedReader.readLine();
			}

			String itemName = ParamUtil.getString(request, "item_name");
			String itemNumber = ParamUtil.getString(request, "item_number");
			invoice = ParamUtil.getString(request, "invoice");
			String txnId = ParamUtil.getString(request, "txn_id");
			String paymentStatus = ParamUtil.getString(
				request, "payment_status");
			double paymentGross = ParamUtil.getDouble(request, "mc_gross");
			String receiverEmail = ParamUtil.getString(
				request, "receiver_email");
			String payerEmail = ParamUtil.getString(request, "payer_email");

			if (_log.isDebugEnabled()) {
				_log.debug("Receiving response from PayPal");
				_log.debug("Item name " + itemName);
				_log.debug("Item number " + itemNumber);
				_log.debug("Invoice " + invoice);
				_log.debug("Transaction ID " + txnId);
				_log.debug("Payment status " + paymentStatus);
				_log.debug("Payment gross " + paymentGross);
				_log.debug("Receiver email " + receiverEmail);
				_log.debug("Payer email " + payerEmail);
			}

			if (payPalStatus.equals("VERIFIED") && validate(request)) {
				ServiceContext serviceContext =
					ServiceContextFactory.getInstance(request);

				_shoppingOrderLocalService.completeOrder(
					invoice, txnId, paymentStatus, paymentGross, receiverEmail,
					payerEmail, true, serviceContext);
			}
			else if (payPalStatus.equals("INVALID")) {
			}

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	@Reference(unbind = "-")
	protected void setShoppingOrderLocalService(
		ShoppingOrderLocalService shoppingOrderLocalService) {

		_shoppingOrderLocalService = shoppingOrderLocalService;
	}

	protected boolean validate(HttpServletRequest request) throws Exception {

		// Invoice

		String ppInvoice = ParamUtil.getString(request, "invoice");

		ShoppingOrder order = _shoppingOrderLocalService.getOrder(ppInvoice);

		ShoppingGroupServiceOverriddenConfiguration
			shoppingGroupServiceOverriddenConfiguration =
				ConfigurationProviderUtil.getConfiguration(
					ShoppingGroupServiceOverriddenConfiguration.class,
					new GroupServiceSettingsLocator(
						order.getGroupId(), ShoppingConstants.SERVICE_NAME));

		// Receiver email address

		String ppReceiverEmail = ParamUtil.getString(request, "receiver_email");

		String payPalEmailAddress =
			shoppingGroupServiceOverriddenConfiguration.getPayPalEmailAddress();

		if (!payPalEmailAddress.equals(ppReceiverEmail)) {
			return false;
		}

		// Payment gross

		double ppGross = ParamUtil.getDouble(request, "mc_gross");

		double orderTotal = ShoppingUtil.calculateTotal(order);

		if (orderTotal != ppGross) {
			return false;
		}

		// Payment currency

		String ppCurrency = ParamUtil.getString(request, "mc_currency");

		String currencyId =
			shoppingGroupServiceOverriddenConfiguration.getCurrencyId();

		if (!currencyId.equals(ppCurrency)) {
			return false;
		}

		// Transaction ID

		String ppTxnId = ParamUtil.getString(request, "txn_id");

		try {
			_shoppingOrderLocalService.getPayPalTxnIdOrder(ppTxnId);

			return false;
		}
		catch (NoSuchOrderException nsoe) {
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PayPalNotificationAction.class);

	private ShoppingOrderLocalService _shoppingOrderLocalService;

}