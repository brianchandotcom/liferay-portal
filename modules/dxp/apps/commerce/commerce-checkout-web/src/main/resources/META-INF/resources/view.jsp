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
CheckoutDisplayContext checkoutDisplayContext = (CheckoutDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<CommerceCheckoutStep> commerceCheckoutSteps = checkoutDisplayContext.getCommerceCheckoutSteps();

int step = 1;
%>

<ul class="multi-step-progress-bar multi-step-progress-bar-collapse">

	<%
	for (CommerceCheckoutStep commerceCheckoutStep : commerceCheckoutSteps) {
		String cssClass = "";

		if (checkoutDisplayContext.isCurrentCommerceCheckoutStep(commerceCheckoutStep)) {
			cssClass = "active";
		}
	%>

		<li class="<%= cssClass %>">
			<div class="progress-bar-title"><liferay-ui:message key="<%= commerceCheckoutStep.getLabel(locale) %>" /> </div>
			<div class="divider"></div>
			<div class="progress-bar-step"><%= step %></div>
		</li>

	<%
			step ++;
	}
	%>

	<li class="">
		<div class="progress-bar-title"><liferay-ui:message key="confirmation" /> </div>
		<div class="divider"></div>
		<div class="progress-bar-step"><%= step %></div>
	</li>
</ul>

<portlet:actionURL name="saveStep" var="saveStepURL">
	<portlet:param name="checkoutStepName" value="<%= checkoutDisplayContext.getCurrentCheckoutStepName() %>" />
</portlet:actionURL>

<aui:form action="<%= saveStepURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCheckoutStep();" %>'>
	<aui:input name="commerceCartId" type="hidden" value="<%= commerceCart.getCommerceCartId() %>" />

	<%= checkoutDisplayContext.renderCurrentCheckoutStep() %>

	<aui:button-row>
		<c:if test="<%= Validator.isNotNull(backURL) %>">
			<aui:button cssClass="btn-lg" href="<%= backURL %>" type="cancel" value="previous" />
		</c:if>

		<aui:button cssClass="btn-lg" primary="<%= false %>" type="submit" value="next" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCheckoutStep() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>