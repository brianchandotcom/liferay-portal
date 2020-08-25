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
Map<String, Object> contextObjects = new HashMap<>();

CommerceOrderContentDisplayContext commerceOrderContentDisplayContext = (CommerceOrderContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

contextObjects.put("commerceOrderContentDisplayContext", commerceOrderContentDisplayContext);

CommerceAccount commerceAccount = commerceOrderContentDisplayContext.getCommerceAccount();
%>

<liferay-ddm:template-renderer
	className="<%= CommerceOpenOrderContentPortlet.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= commerceOrderContentDisplayContext.getDisplayStyle(CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT) %>"
	displayStyleGroupId="<%= commerceOrderContentDisplayContext.getDisplayStyleGroupId(CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT) %>"
	entries="<%= commerceOrderContentDisplayContext.getCommerceOrders() %>"
>
	<commerce-ui:dataset-display
		dataProviderKey="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PENDING_ORDERS %>"
		id="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PENDING_ORDERS %>"
		itemsPerPage="<%= 10 %>"
		namespace="<%= renderResponse.getNamespace() %>"
		pageNumber="<%= 1 %>"
		portletURL="<%= commerceOrderContentDisplayContext.getPortletURL() %>"
		style="stacked"
	/>

	<portlet:actionURL name="editCommerceOrder" var="editCommerceOrderURL" />

	<div class="commerce-cta is-visible">
		<c:if test="<%= commerceOrderContentDisplayContext.hasPermission(CommerceOrderActionKeys.ADD_COMMERCE_ORDER) %>">
			<aui:form action="<%= editCommerceOrderURL %>" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="deleteCommerceOrderIds" type="hidden" />

				<clay:button
					disabled="<%= commerceAccount == null %>"
					elementClasses="btn-fixed btn-primary"
					label='<%= LanguageUtil.get(request, "add-order") %>'
					size="lg"
					style="primary"
					type="submit"
				/>
			</aui:form>
		</c:if>
	</div>
</liferay-ddm:template-renderer>