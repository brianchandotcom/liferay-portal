<%--
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
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.commerce.cart.CommerceCartValidatorResult" %><%@
page import="com.liferay.commerce.checkout.web.constants.CommerceCheckoutWebKeys" %><%@
page import="com.liferay.commerce.checkout.web.internal.display.context.BaseAddressCheckoutStepDisplayContext" %><%@
page import="com.liferay.commerce.checkout.web.internal.display.context.CheckoutDisplayContext" %><%@
page import="com.liferay.commerce.checkout.web.internal.display.context.OrderConfirmationCheckoutStepDisplayContext" %><%@
page import="com.liferay.commerce.checkout.web.internal.display.context.OrderSummaryCheckoutStepDisplayContext" %><%@
page import="com.liferay.commerce.checkout.web.internal.display.context.PaymentMethodCheckoutStepDisplayContext" %><%@
page import="com.liferay.commerce.checkout.web.internal.display.context.ShippingMethodCheckoutStepDisplayContext" %><%@
page import="com.liferay.commerce.checkout.web.util.CommerceCheckoutStep" %><%@
page import="com.liferay.commerce.exception.CommerceAddressCityException" %><%@
page import="com.liferay.commerce.exception.CommerceAddressCountryException" %><%@
page import="com.liferay.commerce.exception.CommerceAddressNameException" %><%@
page import="com.liferay.commerce.exception.CommerceAddressStreetException" %><%@
page import="com.liferay.commerce.exception.CommerceCartBillingAddressException" %><%@
page import="com.liferay.commerce.exception.CommerceCartPaymentMethodException" %><%@
page import="com.liferay.commerce.exception.CommerceCartShippingAddressException" %><%@
page import="com.liferay.commerce.exception.CommerceCartShippingMethodException" %><%@
page import="com.liferay.commerce.model.CommerceAddress" %><%@
page import="com.liferay.commerce.model.CommerceCart" %><%@
page import="com.liferay.commerce.model.CommerceCountry" %><%@
page import="com.liferay.commerce.model.CommerceOrder" %><%@
page import="com.liferay.commerce.model.CommercePaymentMethod" %><%@
page import="com.liferay.commerce.model.CommerceShippingMethod" %><%@
page import="com.liferay.commerce.model.CommerceShippingOption" %><%@
page import="com.liferay.commerce.product.model.CPDefinition" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePair" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<%@ page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.StringJoiner" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />