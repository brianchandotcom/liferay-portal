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

	<aui:fieldset>
		<c:if test="<%= !commerceAddresses.isEmpty() %>">
			<aui:field-wrapper label="shipping-address">

				<%
				for (int i = 0; i < commerceAddresses.size(); i++) {
					CommerceAddress commerceAddress = commerceAddresses.get(i);

					commerceAddress = commerceAddress.toEscapedModel();
				%>

					<div class="col-md-4">
						<div class="radio radio-card radio-middle-left">
							<label>
								<aui:input checked="<%= i == 0 %>" label="" name="shippingAddressId" type="radio" value="<%= commerceAddress.getCommerceAddressId() %>" />

								<div class="card">
									<div class="card-body">
										<h5><%= commerceAddress.getName() %></h5>
										<p><%= commerceAddress.getStreet1() %></p>

										<c:if test="<%= Validator.isNotNull(commerceAddress.getStreet2()) %>">
											<p><%= commerceAddress.getStreet2() %></p>
										</c:if>

										<c:if test="<%= Validator.isNotNull(commerceAddress.getStreet3()) %>">
											<p><%= commerceAddress.getStreet3() %></p>
										</c:if>

										<p><%= commerceAddress.getCity() %></p>

										<%
										CommerceCountry commerceCountry = commerceAddress.getCommerceCountry();
										%>

										<c:if test="<%= commerceCountry != null %>">
											<p><%= HtmlUtil.escape(commerceCountry.getName(locale)) %></p>
										</c:if>
									</div>
								</div>
							</label>
						</div>
					</div>

				<%
				}
				%>

			</aui:field-wrapper>
		</c:if>

		<aui:input name="purchaseOrderNumber" />
	</aui:fieldset>
</aui:form>