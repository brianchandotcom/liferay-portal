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

<%@ include file="/admin/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();
%>

<aui:nav-bar cssClass="collapse-basic-search" id="toolbar" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		portletURL.setParameter("currentTab", "forms");
		%>

		<aui:nav-item
			href="<%= portletURL.toString() %>"
			label="forms"
			selected="<%= false %>"
		/>

		<%
		portletURL.setParameter("currentTab", "field-library");
		%>

		<aui:nav-item
			href="<%= portletURL.toString() %>"
			label="field-library"
			selected="<%= true %>"
		/>
	</aui:nav>

	<c:if test="<%= ddlFormAdminFieldLibraryDisplayContext.isShowSearch() %>">
		<aui:nav-bar-search>
			<aui:form action="<%= ddlFormAdminFieldLibraryDisplayContext.getPortletURL() %>" method="post" name="fm1">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>