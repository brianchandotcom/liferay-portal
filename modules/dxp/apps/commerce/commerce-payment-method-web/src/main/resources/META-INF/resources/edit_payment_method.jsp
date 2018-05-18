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

<%@ include file="/init.jsp" %>

<%
ServletContext commerceAdminServletContext = (ServletContext)request.getAttribute(CommerceAdminWebKeys.COMMERCE_ADMIN_SERVLET_CONTEXT);

CommercePaymentMethodsDisplayContext commercePaymentMethodsDisplayContext = (CommercePaymentMethodsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePaymentMethod commercePaymentMethod = commercePaymentMethodsDisplayContext.getCommercePaymentMethod();

long commercePaymentMethodId = commercePaymentMethod.getCommercePaymentMethodId();

String title = LanguageUtil.format(request, "edit-x", commercePaymentMethod.getName(locale), false);

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, commerceAdminModuleKey), redirect, data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);

renderResponse.setTitle(LanguageUtil.get(request, "settings"));
%>

<liferay-util:include page="/navbar.jsp" servletContext="<%= commerceAdminServletContext %>">
	<liferay-util:param name="commerceAdminModuleKey" value="<%= commerceAdminModuleKey %>" />
</liferay-util:include>

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