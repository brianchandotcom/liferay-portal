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
			<clay:row>
				<clay:col
					size="12"
				>
					<aui:alert type="info">
						<liferay-ui:message key="there-are-no-available-delivery-terms" />
					</aui:alert>
				</clay:col>
			</clay:row>

			<aui:script use="aui-base">
				var continueButton = A.one('#<portlet:namespace />continue');

				if (continueButton) {
					Liferay.Util.toggleDisabled(continueButton, true);
				}
			</aui:script>
		</c:when>
		<c:otherwise>
			<liferay-ui:error key="deliveryTermsInvalid" message="please-select-delivery-terms" />

			<ul class="list-group">

				<%
				for (CommerceTermEntry commerceTermEntry : deliveryCommerceTermEntries) {
					String deliveryTermsId = liferayPortletResponse.getNamespace() + "item_" + commerceTermEntry.getCommerceTermEntryId();
				%>

					<li class="commerce-delivery-types list-group-item list-group-item-flex">
						<div class="autofit-col autofit-col-expand p-2 pl-3">
							<aui:input checked="<%= deliveryCommerceTermEntryId == commerceTermEntry.getCommerceTermEntryId() %>" cssClass="mr-3" label="<%= commerceTermEntry.getLabel(LanguageUtil.getLanguageId(locale)) %>" labelCssClass="align-items-center d-inline-flex mb-0" name="deliveryCommerceTermEntryId" type="radio" value="<%= commerceTermEntry.getCommerceTermEntryId() %>" />
						</div>

						<div class="autofit-col p-2 pr-3">
							<a href="#" id="<%= deliveryTermsId %>"><liferay-ui:message key="more-info"></liferay-ui:message></a>

							<liferay-frontend:component
								context='<%=
									HashMapBuilder.<String, Object>put(
										"HTMLElementId", deliveryTermsId
									).put(
										"modalContent", commerceTermEntry.getDescription(LanguageUtil.getLanguageId(locale))
									).put(
										"modalTitle", commerceTermEntry.getLabel(LanguageUtil.getLanguageId(locale))
									).build()
								%>'
								module="js/attachModalToHTMLElement"
							/>
						</div>
					</li>

				<%
				}
				%>

			</ul>
		</c:otherwise>
	</c:choose>
</div>