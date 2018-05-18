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
CheckoutDisplayContext checkoutDisplayContext = (CheckoutDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String currentCheckoutStepName = checkoutDisplayContext.getCurrentCheckoutStepName();
%>

<div class="row">
	<div class="container-fluid container-fluid-max-xl">
		<c:choose>
			<c:when test="<%= checkoutDisplayContext.isEmptyCommerceOrder() %>">
				<div class="alert alert-info mx-auto">
					<liferay-ui:message key="cart-is-empty" />
					<liferay-ui:message key="please-add-products-to-proceed-with-the-checkout" />
				</div>
			</c:when>
			<c:otherwise>
				<ul class="commerce-multi-step-nav multi-step-indicator-label-top multi-step-nav multi-step-nav-collapse-sm">

					<%
					boolean complete = true;
					int step = 1;

					for (CommerceCheckoutStep commerceCheckoutStep : checkoutDisplayContext.getCommerceCheckoutSteps()) {
						String name = commerceCheckoutStep.getName();

						if (!currentCheckoutStepName.equals(name) && !commerceCheckoutStep.isVisible(request, response)) {
							continue;
						}

						String cssClass = "multi-step-item";

						if (checkoutDisplayContext.getCommerceCheckoutSteps().size() != step) {
							cssClass += " multi-step-item-expand";
						}

						if (currentCheckoutStepName.equals(name)) {
							cssClass += " active";
							complete = false;
						}

						if (complete) {
							cssClass += " complete";
						}
					%>

						<li class="<%= cssClass %>">
							<div class="multi-step-divider"></div>
							<div class="multi-step-indicator">
								<div class="multi-step-indicator-label">
									<liferay-ui:message key="<%= commerceCheckoutStep.getLabel(locale) %>" />
								</div>

								<span class="multi-step-icon" data-multi-step-icon="<%= step %>"></span>
							</div>
						</li>

					<%
						step++;
					}
					%>

				</ul>

				<portlet:actionURL name="saveStep" var="saveStepURL" />

				<aui:form action="<%= saveStepURL %>" data-senna-off="<%= checkoutDisplayContext.isSennaDisabled() %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCheckoutStep();" %>'>
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
	</div>
</div>