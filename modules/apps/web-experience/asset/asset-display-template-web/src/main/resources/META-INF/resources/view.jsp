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

<liferay-portlet:renderURL varImpl="portletURL">
	<liferay-portlet:param name="keywords" value="<%= assetDisplayTemplateDisplayContext.getKeywords() %>" />
</liferay-portlet:renderURL>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<portlet:renderURL var="mainURL" />

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item href="<%= mainURL.toString() %>" label="asset-display-templates" selected="<%= true %>" />
	</aui:nav>

	<c:if test="<%= assetDisplayTemplateDisplayContext.isShowSearch() %>">
		<aui:nav-bar-search>
			<aui:form action="<%= portletURL %>" name="searchFm">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>