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

package com.liferay.commerce.frontend.js.internal.servlet.taglib;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Fabio Mastrorilli
 */
@Component(
	property = "service.ranking:Integer=" + Integer.MAX_VALUE,
	service = DynamicInclude.class
)
public class CommerceFrontendJsDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		StringBundler sb = new StringBundler(5);

		sb.append("<script data-senna-track=\"temporary\">");
		sb.append("var Liferay = window.Liferay || {};");

		String[] accountEntryAllowedTypes = null;
		long[] commerceAccountGroupIds = null;
		long commerceChannelId = 0;
		int commerceSiteType = 0;

		JSONObject accountJSONObject = null;
		JSONObject currencyJSONObject = null;
		JSONObject orderJSONObject = null;

		try {
			CommerceContext commerceContext =
				(CommerceContext)httpServletRequest.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT);

			accountEntryAllowedTypes =
				commerceContext.getAccountEntryAllowedTypes();
			commerceAccountGroupIds =
				commerceContext.getCommerceAccountGroupIds();
			commerceChannelId = commerceContext.getCommerceChannelId();
			commerceSiteType = commerceContext.getCommerceSiteType();

			CommerceAccount commerceAccount =
				commerceContext.getCommerceAccount();

			if (commerceAccount != null) {
				accountJSONObject = _jsonFactory.createJSONObject(
				).put(
					"accountId", commerceAccount.getCommerceAccountId()
				).put(
					"accountName", commerceAccount.getName()
				);
			}

			CommerceCurrency commerceCurrency =
				commerceContext.getCommerceCurrency();

			if (commerceCurrency != null) {
				currencyJSONObject = _jsonFactory.createJSONObject(
				).put(
					"currencyCode", commerceCurrency.getCode()
				).put(
					"currencyId", commerceCurrency.getCommerceCurrencyId()
				);
			}

			CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

			if (commerceOrder != null) {
				orderJSONObject = _jsonFactory.createJSONObject(
				).put(
					"orderId", commerceOrder.getCommerceOrderId()
				).put(
					"orderType", commerceOrder.getCommerceOrderTypeId()
				);
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		sb.append(
			StringBundler.concat(
				"Liferay.CommerceContext = ",
				_jsonFactory.createJSONObject(
				).put(
					"account", accountJSONObject
				).put(
					"accountEntryAllowedTypes", accountEntryAllowedTypes
				).put(
					"commerceAccountGroupIds", commerceAccountGroupIds
				).put(
					"commerceChannelId", commerceChannelId
				).put(
					"commerceSiteType", commerceSiteType
				).put(
					"currency", currencyJSONObject
				).put(
					"order", orderJSONObject
				).toString(),
				";"));

		sb.append("</script>");

		sb.append(
			StringBundler.concat(
				"<link href=\"",
				_portal.getPathProxy() + httpServletRequest.getContextPath(),
				"/o/commerce-frontend-js/styles/main.css\" rel=\"stylesheet\" ",
				"type=\"text/css\" />"));

		printWriter.println(sb);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_head.jsp#post");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceFrontendJsDynamicInclude.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}