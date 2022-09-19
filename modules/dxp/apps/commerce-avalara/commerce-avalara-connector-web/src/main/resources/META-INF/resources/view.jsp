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
CommerceAvalaraDisplayContext commerceAvalaraDisplayContext = (CommerceAvalaraDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<clay:navigation-bar
	inverted="<%= false %>"
	navigationItems="<%= commerceAvalaraDisplayContext.getNavigationItems() %>"
/>

<c:choose>
	<c:when test="<%= commerceAvalaraDisplayContext.getType() == CommerceAvalaraConstants.TYPE_CREDENTIALS %>">
		<%@ include file="/tabs/credentials.jspf" %>
	</c:when>
</c:choose>