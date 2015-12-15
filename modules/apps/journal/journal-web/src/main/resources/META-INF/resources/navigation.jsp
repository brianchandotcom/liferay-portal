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
Map<String, Object> data = new HashMap<>();

data.put("qa-id", "navigation");
%>

<aui:nav-bar cssClass="collapse-basic-search" data="<%= data %>" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="web-content" selected="<%= true %>" />
	</aui:nav>

	<aui:nav-bar-search>

		<%
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("folderId", String.valueOf(journalDisplayContext.getFolderId()));
		portletURL.setParameter("showEditActions", String.valueOf(journalDisplayContext.isShowEditActions()));
		%>

		<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1">
			<liferay-ui:input-search markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>