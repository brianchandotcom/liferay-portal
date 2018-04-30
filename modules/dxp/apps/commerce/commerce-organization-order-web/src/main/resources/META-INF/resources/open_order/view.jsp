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

Map<String, Object> contextObjects = new HashMap<>();

contextObjects.put("commerceOrganizationOrderDisplayContext", commerceOrganizationOrderDisplayContext);

SearchContainer searchContainer = commerceOrganizationOrderDisplayContext.getSearchContainer();

List<CommerceOrder> results = searchContainer.getResults();
%>

<liferay-ddm:template-renderer
	className="<%= CommerceOrganizationOpenOrderPortlet.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= commerceOrganizationOrderDisplayContext.getDisplayStyle() %>"
	displayStyleGroupId="<%= commerceOrganizationOrderDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= results %>"
>
	<c:choose>
		<c:when test="<%= (commerceOrganizationOrderDisplayContext.getOrganization() != null) && (results.size() > 0) %>">
			<div class="row" id="<portlet:namespace />openOrdersContainer">

				<%
				for (CommerceOrder commerceOrder : results) {
				%>

					<div class="col-md-3">
						<div class="order-card">
							<div class="row">
								<div class="col-md-8">
									<aui:workflow-status markupView="lexicon" showHelpMessage="<%= false %>" showLabel="<%= false %>" status="<%= commerceOrder.getStatus() %>" />

									<h4 class="order-id" ><aui:a href="#" label="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>" /></h4>

									<h4><%= commerceOrganizationOrderDisplayContext.getCommerceOrderValue(commerceOrder) %></h4>
								</div>
								<div class="col-md-4">
									<p class="text-muted"><%= commerceOrganizationOrderDisplayContext.getCommerceOrderDate(commerceOrder) + StringPool.SPACE + commerceOrganizationOrderDisplayContext.getCommerceOrderTime(commerceOrder) %></p>
									<%
										request.setAttribute("order_transition.jsp-commerceOrder", commerceOrder);
									%>

									<liferay-util:include page="/order_transition.jsp" servletContext="<%= application %>" />
								</div>
							</div>
						</div>
					</div>

				<%
				}
				%>

			</div>

			<portlet:actionURL name="editCommerceOrder" var="editCommerceOrderURL" />

			<%@ include file="/transition.jspf" %>

			<aui:script use="aui-base">
				var openOrdersContainer = A.one('#<portlet:namespace />openOrdersContainer');

				openOrdersContainer.delegate(
					'click',
					function(event) {
						<portlet:namespace />transition(event);
					},
					'.transition-link'
				);
			</aui:script>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
</liferay-ddm:template-renderer>