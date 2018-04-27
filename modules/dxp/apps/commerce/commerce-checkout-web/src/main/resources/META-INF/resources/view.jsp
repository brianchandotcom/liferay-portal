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

String currentCheckoutStepName = checkoutDisplayContext.getCurrentCheckoutStepName();
%>

<c:choose>
	<c:when test="<%= checkoutDisplayContext.isEmptyCommerceOrder() %>">
		<div class="alert alert-info mx-auto">
			<liferay-ui:message key="cart-is-empty" />
			<liferay-ui:message key="please-add-products-to-proceed-with-the-checkout" />
		</div>
	</c:when>
	<c:otherwise>
		<ul class="multi-step-progress-bar multi-step-progress-bar-collapse my-4">

			<%
			boolean complete = true;
			int step = 1;

			for (CommerceCheckoutStep commerceCheckoutStep : checkoutDisplayContext.getCommerceCheckoutSteps()) {
				String name = commerceCheckoutStep.getName();

				if (!currentCheckoutStepName.equals(name) && !commerceCheckoutStep.isVisible(request, response)) {
					continue;
				}

				String cssClass = "";

				if (currentCheckoutStepName.equals(name)) {
					cssClass = "active";
					complete = false;
				}

				if (complete) {
					cssClass = "complete";
				}
			%>

				<li class="<%= cssClass %>">
					<div class="progress-bar-title">
						<liferay-ui:message key="<%= commerceCheckoutStep.getLabel(locale) %>" />
					</div>

					<div class="divider"></div>

					<div class="progress-bar-step">
						<%= step %>
					</div>
				</li>

			<%
				step++;
			}
			%>

		</ul>

		<portlet:actionURL name="saveStep" var="saveStepURL" />

		<aui:form action="<%= saveStepURL %>" cssClass="container-fluid-1280" data-senna-off="<%= checkoutDisplayContext.isSennaDisabled() %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCheckoutStep();" %>'>
			<aui:input name="checkoutStepName" type="hidden" value="<%= currentCheckoutStepName %>" />
			<aui:input name="commerceOrderId" type="hidden" value="<%= checkoutDisplayContext.getCommerceOrderId() %>" />
			<aui:input name="redirect" type="hidden" value="<%= checkoutDisplayContext.getRedirect() %>" />

			<%
			checkoutDisplayContext.renderCurrentCheckoutStep();
			%>

			<c:if test="<%= checkoutDisplayContext.showControls() %>">
				<aui:button-row>
					<c:if test="<%= Validator.isNotNull(checkoutDisplayContext.getPreviousCheckoutStepName()) %>">
						<portlet:renderURL var="previousStepURL">
							<portlet:param name="commerceOrderId" value="<%= String.valueOf(checkoutDisplayContext.getCommerceOrderId()) %>" />
							<portlet:param name="checkoutStepName" value="<%= checkoutDisplayContext.getPreviousCheckoutStepName() %>" />
						</portlet:renderURL>

						<aui:button cssClass="pull-left" href="<%= previousStepURL %>" value="previous" />
					</c:if>

					<aui:button cssClass="pull-right" name="continue" primary="<%= true %>" type="submit" value="continue" />
				</aui:button-row>
			</c:if>
		</aui:form>

		<aui:script>
			function <portlet:namespace />saveCheckoutStep() {
				submitForm(document.<portlet:namespace />fm);
			}
		</aui:script>
	</c:otherwise>
</c:choose>