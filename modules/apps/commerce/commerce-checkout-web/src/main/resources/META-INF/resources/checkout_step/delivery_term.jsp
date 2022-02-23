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
TermCommerceCheckoutStepDisplayContext termCommerceCheckoutStepDisplayContext = (TermCommerceCheckoutStepDisplayContext)request.getAttribute(CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT);

List<CommerceTermEntry> deliveryCommerceTermEntries = termCommerceCheckoutStepDisplayContext.getDeliveryCommerceTermEntries();

long deliveryCommerceTermEntryId = BeanParamUtil.getLong(termCommerceCheckoutStepDisplayContext.getCommerceOrder(), request, "deliveryCommerceTermEntryId");
%>

<div id="commerceDeliveryTermContainer">
	<c:choose>
		<c:when test="<%= deliveryCommerceTermEntries.isEmpty() %>">
			<aui:row>
				<aui:col widht="100">
					<aui:alert type="info">
						<liferay-ui:message key="there-are-no-available-delivery-terms" />
					</aui:alert>
				</aui:col>
			</aui:row>

			<aui:script use="aui-base">
				var continueButton = A.one('#<portlet:namespace />continue');

				if (continueButton) {
					Liferay.Util.toggleDisabled(continueButton, true);
				}
			</aui:script>
		</c:when>
		<c:otherwise>
			<ul class="list-group">

				<%
				for (CommerceTermEntry commerceTermEntry : deliveryCommerceTermEntries) {
				%>

					<li class="commerce-payment-types list-group-item list-group-item-flex">
						<div class="autofit-col autofit-col-expand">
							<aui:input checked="<%= deliveryCommerceTermEntryId == commerceTermEntry.getCommerceTermEntryId() %>" label="<%= commerceTermEntry.getLabel(LanguageUtil.getLanguageId(locale)) %>" name="commerceDeliveryTermId" type="radio" value="<%= commerceTermEntry.getCommerceTermEntryId() %>" />
						</div>
					</li>

				<%
				}
				%>

			</ul>
		</c:otherwise>
	</c:choose>
</div>