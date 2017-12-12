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

CommerceShippingMethodsDisplayContext commerceShippingMethodsDisplayContext = (CommerceShippingMethodsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShippingMethod commerceShippingMethod = commerceShippingMethodsDisplayContext.getCommerceShippingMethod();

long commerceShippingMethodId = commerceShippingMethod.getCommerceShippingMethodId();

String title = LanguageUtil.format(request, "edit-x", commerceShippingMethod.getName(locale), false);

Map<String, Object> data = new HashMap<>();

data.put("direction-right", Boolean.TRUE.toString());

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, ShippingMethodsCommerceAdminModule.KEY), redirect, data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);

renderResponse.setTitle(LanguageUtil.get(request, "settings"));
%>

<%@ include file="/breadcrumb.jspf" %>

<portlet:actionURL name="editCommerceShippingMethod" var="editCommerceShippingMethodActionURL" />

<aui:form action="<%= editCommerceShippingMethodActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCommerceShippingMethod();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceShippingMethodId <= 0) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="commerceShippingMethodId" type="hidden" value="<%= commerceShippingMethodId %>" />
	<aui:input name="engineKey" type="hidden" value="<%= commerceShippingMethod.getEngineKey() %>" />

	<liferay-ui:form-navigator
		formModelBean="<%= commerceShippingMethod %>"
		id="<%= CommerceShippingFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_SHIPPING_METHOD %>"
		markupView="lexicon"
	/>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCommerceShippingMethod() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>