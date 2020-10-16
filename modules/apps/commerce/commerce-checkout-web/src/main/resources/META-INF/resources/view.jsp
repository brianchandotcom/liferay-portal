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
%>

<c:choose>
	<c:when test="<%= !checkoutDisplayContext.hasCommerceChannel() %>">
		<div class="alert alert-info mx-auto">
			<liferay-ui:message key="this-site-does-not-have-a-channel" />
		</div>
	</c:when>
	<c:otherwise>
		<div class="row">
			<div class="commerce-checkout container-fluid container-fluid-max-xl">
				<c:choose>
					<c:when test="<%= checkoutDisplayContext.isEmptyCommerceOrder() %>">
						<div class="alert alert-info mx-auto">
							<liferay-ui:message key="the-cart-is-empty" />
							<liferay-ui:message key="please-add-products-to-proceed-with-the-checkout" />
						</div>
					</c:when>
					<c:otherwise>
						<ul class="commerce-multi-step-nav multi-step-indicator-label-top multi-step-nav multi-step-nav-collapse-sm">

							<%
							boolean complete = true;
							String currentCheckoutStepName = checkoutDisplayContext.getCurrentCheckoutStepName();
							int step = 1;

							List<CommerceCheckoutStep> commerceCheckoutSteps = checkoutDisplayContext.getCommerceCheckoutSteps();

							Iterator<CommerceCheckoutStep> commerceCheckoutStepIterator = commerceCheckoutSteps.iterator();

							while (commerceCheckoutStepIterator.hasNext()) {
								CommerceCheckoutStep commerceCheckoutStep = commerceCheckoutStepIterator.next();

								String name = commerceCheckoutStep.getName();

								if (!currentCheckoutStepName.equals(name) && !commerceCheckoutStep.isVisible(request, response)) {
									continue;
								}

								String cssClass = "multi-step-item";

								if (commerceCheckoutStepIterator.hasNext()) {
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
											<liferay-ui:message key="<%= HtmlUtil.escape(commerceCheckoutStep.getLabel(locale)) %>" />
										</div>

										<span class="multi-step-icon" data-multi-step-icon="<%= step %>"></span>
									</div>
								</li>

							<%
								step++;
							}
							%>

						</ul>

						<portlet:actionURL name="/commerce_checkout/save_step" var="saveStepURL" />

						<aui:form action="<%= saveStepURL %>" data-senna-off="<%= checkoutDisplayContext.isSennaDisabled() %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveCheckoutStep();" %>'>
							<aui:input name="checkoutStepName" type="hidden" value="<%= currentCheckoutStepName %>" />
							<aui:input name="commerceOrderUuid" type="hidden" value="<%= checkoutDisplayContext.getCommerceOrderUuid() %>" />
							<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

							<%
							checkoutDisplayContext.renderCurrentCheckoutStep(pageContext);
							%>

							<c:if test="<%= checkoutDisplayContext.showControls() %>">
								<aui:button-row>
									<c:if test="<%= Validator.isNotNull(checkoutDisplayContext.getPreviousCheckoutStepName()) %>">
										<portlet:renderURL var="previousStepURL">
											<portlet:param name="commerceOrderUuid" value="<%= String.valueOf(checkoutDisplayContext.getCommerceOrderUuid()) %>" />
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
	</c:otherwise>
</c:choose>