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

									<h4 class="order-id"><aui:a href="<%= commerceOrganizationOrderDisplayContext.getOrderDetailURL(commerceOrder) %>" label="<%= String.valueOf(commerceOrder.getCommerceOrderId()) %>" /></h4>

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