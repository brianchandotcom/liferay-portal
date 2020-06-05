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

SelectCollectionDisplayContext selectCollectionDisplayContext = new SelectCollectionDisplayContext(liferayPortletRequest, liferayPortletResponse);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.get(request, "select-collection"));
%>

<clay:container-fluid
	cssClass="container-view"
	id='<%= renderResponse.getNamespace() + "collections" %>'
>
	<clay:row>
		<clay:col
			lg="3"
		>
			<nav class="menubar menubar-transparent menubar-vertical-expand-lg">
				<ul class="nav nav-nested">
					<li class="nav-item">
						<ul class="nav nav-stacked">
							<li class="nav-item">
								<a class='nav-link text-truncate <%= selectCollectionDisplayContext.isCollections() ? "active" : StringPool.BLANK %>' href='<%= layoutsAdminDisplayContext.getSelectCollectionsURL(layoutsAdminDisplayContext.getSelPlid(), "collections", layoutsAdminDisplayContext.isPrivateLayout()) %>'>
									<liferay-ui:message key="collections" />
								</a>
							</li>
							<li class="nav-item">
								<a class='nav-link text-truncate <%= selectCollectionDisplayContext.isCollectionProviders() ? "active" : StringPool.BLANK %>' href='<%= layoutsAdminDisplayContext.getSelectCollectionsURL(layoutsAdminDisplayContext.getSelPlid(), "collection-providers", layoutsAdminDisplayContext.isPrivateLayout()) %>'>
									<liferay-ui:message key="collection-providers" />
								</a>
							</li>
						</ul>
					</li>
				</ul>
			</nav>
		</clay:col>

		<clay:col
			lg="9"
		>
			<clay:sheet>
				<h2 class="sheet-title">
					<clay:content-row
						verticalAlign="center"
					>
						<clay:content-col
							expand="true"
						>
							<span class="text-uppercase">
								<c:choose>
									<c:when test="<%= selectCollectionDisplayContext.isCollections() %>">
										<liferay-ui:message key="collections" />
									</c:when>
									<c:when test="<%= selectCollectionDisplayContext.isCollectionProviders() %>">
										<liferay-ui:message key="collection-providers" />
									</c:when>
								</c:choose>
							</span>
						</clay:content-col>
					</clay:content-row>
				</h2>

				<c:choose>
					<c:when test="<%= selectCollectionDisplayContext.isCollections() %>">
						<liferay-util:include page="/select_collections.jsp" servletContext="<%= application %>" />
					</c:when>
					<c:otherwise>
						<liferay-util:include page="/select_collection_providers.jsp" servletContext="<%= application %>" />
					</c:otherwise>
				</c:choose>
			</clay:sheet>
		</clay:col>
	</clay:row>
</clay:container-fluid>