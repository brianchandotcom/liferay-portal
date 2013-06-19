/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.action;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.shopping.NoSuchOrderException;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.shopping.service.ShoppingOrderLocalServiceUtil;
import com.liferay.portlet.shopping.util.ShoppingPreferences;
import com.liferay.portlet.shopping.util.ShoppingUtil;

import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.URL;
import java.net.URLConnection;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class PayPalNotificationAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, ActionForm actionForm,
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
				"Content-Type","application/x-www-form-urlencoded");

			PrintWriter pw = UnsyncPrintWriterPool.borrow(
				urlc.getOutputStream());

			pw.println(query);

			pw.close();

			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(
					new InputStreamReader(urlc.getInputStream()));

			String payPalStatus = unsyncBufferedReader.readLine();

			unsyncBufferedReader.close();

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

				ShoppingOrderLocalServiceUtil.completeOrder(
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

	protected boolean validate(HttpServletRequest request) throws Exception {

		// Invoice

		String ppInvoice = ParamUtil.getString(request, "invoice");

		ShoppingOrder order = ShoppingOrderLocalServiceUtil.getOrder(ppInvoice);

		ShoppingPreferences shoppingPrefs = ShoppingPreferences.getInstance(
			order.getCompanyId(), order.getGroupId());

		// Receiver email address

		String ppReceiverEmail = ParamUtil.getString(request, "receiver_email");

		String payPalEmailAddress = shoppingPrefs.getPayPalEmailAddress();

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

		String currencyId = shoppingPrefs.getCurrencyId();

		if (!currencyId.equals(ppCurrency)) {
			return false;
		}

		// Transaction ID

		String ppTxnId = ParamUtil.getString(request, "txn_id");

		try {
			ShoppingOrderLocalServiceUtil.getPayPalTxnIdOrder(ppTxnId);

			return false;
		}
		catch (NoSuchOrderException nsoe) {
		}

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(
		PayPalNotificationAction.class);

}