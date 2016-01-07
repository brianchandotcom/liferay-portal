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
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view.jsp");
portletURL.setParameter("groupId", String.valueOf(themeDisplay.getScopeGroupId()));
%>

<aui:nav-bar cssClass="collapse-basic-search" id="toolbar" markupView="lexicon">
	<aui:nav-bar-search>
		<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1">
			<liferay-util:include page="/workflow_definition_search.jsp" servletContext="<%= application %>" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>