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
CommerceOrganizationOrderDisplayContext commerceOrganizationOrderDisplayContext = (CommerceOrganizationOrderDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<CommerceAddress> commerceAddresses = commerceOrganizationOrderDisplayContext.getAvailableCommerceOrderAddresses();
Organization organization = commerceOrganizationOrderDisplayContext.getOrganization();
%>

<portlet:actionURL name="editCommerceOrder" var="editCommerceOrderActionURL" />

<aui:form action="<%= editCommerceOrderActionURL %>" method="post" name="addFm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="organizationId" type="hidden" value="<%= organization.getOrganizationId() %>" />

	<aui:model-context model="<%= CommerceOrder.class %>" />

	<c:if test="<%= !commerceAddresses.isEmpty() %>">
		<h6><liferay-ui:message key="shipping-address" /></h6>

		<ul class="list-group list-group-flush order-shipping-address-list">

			<%
			for (int i = 0; i < commerceAddresses.size(); i++) {
				CommerceAddress commerceAddress = commerceAddresses.get(i);

				commerceAddress = commerceAddress.toEscapedModel();
			%>

				<li class="list-group-item list-group-item-flex">
					<div class="autofit-col">
						<div class="custom-control custom-radio">
							<label>
								<aui:input checked="<%= i == 0 %>" cssClass="custom-control-input" id='<%= "shippingAddressId" + i %>' label="" name="shippingAddressId" type="radio" value="<%= commerceAddress.getCommerceAddressId() %>" />

								<span class="custom-control-label"></span>
							</label>
						</div>
					</div>

					<div class="autofit-col autofit-col-expand">
						<div class="autofit-section">
							<label class="order-shipping-address-label" for="<portlet:namespace /><%= "shippingAddressId" + i %>">
								<span class="commerce-name list-group-title"><%= commerceAddress.getName() %></span>
								<span class="commerce-street1 list-group-text"><%= commerceAddress.getStreet1() %></span>

								<c:if test="<%= Validator.isNotNull(commerceAddress.getStreet2()) %>">
									<span class="commerce-street2 list-group-text"><%= commerceAddress.getStreet2() %></span>
								</c:if>

								<c:if test="<%= Validator.isNotNull(commerceAddress.getStreet3()) %>">
									<span class="commerce-street3 list-group-text"><%= commerceAddress.getStreet3() %></span>
								</c:if>

								<span class="commerce-city list-group-text"><%= commerceAddress.getCity() %></span>

								<%
								CommerceCountry commerceCountry = commerceAddress.getCommerceCountry();
								%>

								<c:if test="<%= commerceCountry != null %>">
									<span class="commerce-country list-group-text"><%= HtmlUtil.escape(commerceCountry.getName(locale)) %></span>
								</c:if>
							</label>
						</div>
					</div>
				</li>

			<%
			}
			%>

		</ul>
	</c:if>

	<aui:input name="purchaseOrderNumber" />
</aui:form>