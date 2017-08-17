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
String jspPage = (String)request.getAttribute("view_checkout_step.jsp-jspPage");
String mvcActionCommandName = (String)request.getAttribute("view_checkout_step.jsp-mvcActionCommandName");
String submitButtonValue = GetterUtil.getString(request.getAttribute("view_checkout_step.jsp-submitButtonValue"), "next");
%>

<portlet:actionURL name="<%= mvcActionCommandName %>" var="editCheckoutStepActionURL" />

<aui:form action="<%= editCheckoutStepActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCheckoutStep();" %>'>
	<c:if test="<%= Validator.isNotNull(redirect) %>">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	</c:if>

	<aui:input name="commerceCartId" type="hidden" value="<%= commerceCart.getCommerceCartId() %>" />

	<liferay-util:include page="<%= jspPage %>" servletContext="<%= application %>" />

	<aui:button-row>
		<c:if test="<%= Validator.isNotNull(backURL) %>">
			<aui:button cssClass="btn-lg" href="<%= backURL %>" type="cancel" value="previous" />
		</c:if>

		<aui:button cssClass="btn-lg" primary="<%= false %>" type="submit" value="<%= submitButtonValue %>" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCheckoutStep() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>