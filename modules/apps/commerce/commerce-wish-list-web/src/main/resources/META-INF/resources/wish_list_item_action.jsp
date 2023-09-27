<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceWishListDisplayContext commerceWishListDisplayContext = (CommerceWishListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceWishListItem commerceWishListItem = (CommerceWishListItem)row.getObject();

CPInstance cpInstance = commerceWishListItem.fetchCPInstance();
CProduct cProduct = commerceWishListItem.getCProduct();
%>

<c:choose>
	<c:when test="<%= cpInstance != null %>">
		<commerce-ui:add-to-cart
			alignment="left"
			CPInstanceId="<%= cpInstance.getCPInstanceId() %>"
			inline="<%= true %>"
			size="md"
			skuOptions="[]"
		/>
	</c:when>
	<c:otherwise>
		<a class="commerce-button commerce-button--outline w-100" href="<%= commerceWishListDisplayContext.getCPDefinitionURL(cProduct.getPublishedCPDefinitionId(), themeDisplay) %>"><liferay-ui:message key="view-all-variants" /></a>
	</c:otherwise>
</c:choose>