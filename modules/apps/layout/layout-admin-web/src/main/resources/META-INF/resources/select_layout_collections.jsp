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
String backURL = layoutsAdminDisplayContext.getRedirect();

if (Validator.isNull(backURL)) {
	PortletURL portletURL = layoutsAdminDisplayContext.getPortletURL();

	backURL = portletURL.toString();
}

SelectLayoutCollectionDisplayContext selectLayoutCollectionDisplayContext = new SelectLayoutCollectionDisplayContext(liferayPortletRequest, liferayPortletResponse);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.get(request, "select-collection"));
%>

<clay:navigation-bar
	cssClass="border-bottom"
	inverted="<%= false %>"
	navigationItems="<%= selectLayoutCollectionDisplayContext.getNavigationItems() %>"
/>

<clay:container-fluid
	cssClass="container-view"
	id='<%= renderResponse.getNamespace() + "collections" %>'
>
	<c:choose>
		<c:when test="<%= selectLayoutCollectionDisplayContext.isCollections() %>">
			<liferay-util:include page="/select_collections.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:otherwise>
			<liferay-util:include page="/select_collection_providers.jsp" servletContext="<%= application %>" />
		</c:otherwise>
	</c:choose>
</clay:container-fluid>