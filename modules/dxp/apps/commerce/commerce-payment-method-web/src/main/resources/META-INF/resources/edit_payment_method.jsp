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

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

CommercePaymentMethodsDisplayContext commercePaymentMethodsDisplayContext = (CommercePaymentMethodsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePaymentMethod commercePaymentMethod = commercePaymentMethodsDisplayContext.getCommercePaymentMethod();

long commercePaymentMethodId = commercePaymentMethod.getCommercePaymentMethodId();

String title = LanguageUtil.format(request, "edit-x", commercePaymentMethod.getName(locale), false);

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, PaymentMethodsCommerceAdminModule.KEY), redirect, data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);

renderResponse.setTitle(LanguageUtil.get(request, "settings"));
%>

<%@ include file="/breadcrumb.jspf" %>

<portlet:actionURL name="editCommercePaymentMethod" var="editCommercePaymentMethodActionURL" />

<aui:form action="<%= editCommercePaymentMethodActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCommercePaymentMethod();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commercePaymentMethodId <= 0) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="commercePaymentMethodId" type="hidden" value="<%= commercePaymentMethodId %>" />
	<aui:input name="engineKey" type="hidden" value="<%= commercePaymentMethod.getEngineKey() %>" />

	<liferay-ui:form-navigator
		formModelBean="<%= commercePaymentMethod %>"
		id="<%= CommercePaymentMethodFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PAYMENT_METHOD %>"
		markupView="lexicon"
	/>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCommercePaymentMethod() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>