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
CommerceWishListDisplayContext commerceWishListDisplayContext = (CommerceWishListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceWishListItem commerceWishListItem = (CommerceWishListItem)row.getObject();
%>

<c:choose>
	<c:when test="<%= commerceWishListItem.isIgnoreSKUCombinations() %>">
		<aui:button cssClass="btn-lg" value="add-to-cart" />
	</c:when>
	<c:otherwise>
		<aui:button cssClass="btn-primary" href="<%= commerceWishListDisplayContext.getCPDefinitionURL(commerceWishListItem.getCPDefinitionId(), themeDisplay) %>" name="selectOptions" value="select-options" />
	</c:otherwise>
</c:choose>