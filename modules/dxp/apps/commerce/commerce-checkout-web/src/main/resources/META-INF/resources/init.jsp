<%--
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
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.commerce.checkout.web.constants.CommerceCheckoutWebKeys" %><%@
page import="com.liferay.commerce.checkout.web.internal.display.context.BaseAddressCheckoutStepDisplayContext" %><%@
page import="com.liferay.commerce.checkout.web.internal.display.context.CheckoutDisplayContext" %><%@
page import="com.liferay.commerce.checkout.web.internal.display.context.OrderConfirmationCheckoutStepDisplayContext" %><%@
page import="com.liferay.commerce.checkout.web.internal.display.context.OrderSummaryCheckoutStepDisplayContext" %><%@
page import="com.liferay.commerce.checkout.web.internal.display.context.PaymentMethodCheckoutStepDisplayContext" %><%@
page import="com.liferay.commerce.checkout.web.internal.display.context.ShippingMethodCheckoutStepDisplayContext" %><%@
page import="com.liferay.commerce.checkout.web.util.CommerceCheckoutStep" %><%@
page import="com.liferay.commerce.constants.CommerceOrderPaymentConstants" %><%@
page import="com.liferay.commerce.exception.CommerceAddressCityException" %><%@
page import="com.liferay.commerce.exception.CommerceAddressCountryException" %><%@
page import="com.liferay.commerce.exception.CommerceAddressNameException" %><%@
page import="com.liferay.commerce.exception.CommerceAddressStreetException" %><%@
page import="com.liferay.commerce.exception.CommerceOrderBillingAddressException" %><%@
page import="com.liferay.commerce.exception.CommerceOrderPaymentMethodException" %><%@
page import="com.liferay.commerce.exception.CommerceOrderShippingAddressException" %><%@
page import="com.liferay.commerce.exception.CommerceOrderShippingMethodException" %><%@
page import="com.liferay.commerce.model.CommerceAddress" %><%@
page import="com.liferay.commerce.model.CommerceCountry" %><%@
page import="com.liferay.commerce.model.CommerceOrder" %><%@
page import="com.liferay.commerce.model.CommerceOrderPayment" %><%@
page import="com.liferay.commerce.model.CommercePaymentMethod" %><%@
page import="com.liferay.commerce.model.CommerceShippingMethod" %><%@
page import="com.liferay.commerce.model.CommerceShippingOption" %><%@
page import="com.liferay.commerce.order.CommerceOrderValidatorResult" %><%@
page import="com.liferay.commerce.product.model.CPDefinition" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePair" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<%@ page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.StringJoiner" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />