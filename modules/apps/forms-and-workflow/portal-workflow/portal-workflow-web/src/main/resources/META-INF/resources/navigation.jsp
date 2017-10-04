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
String searchPage = ParamUtil.getString(request, "searchPage");

String searchURL = ParamUtil.getString(request, "searchURL");
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		for (String tabName : tabNames) {
		%>

			<portlet:renderURL var="renderURL">
				<portlet:param name="tab" value="<%= tabName %>" />
			</portlet:renderURL>

			<aui:nav-item
				href="<%= renderURL.toString() %>"
				label="<%= tabName %>"
				selected="<%= tabName.equals(tab) %>"
			/>

		<%
		}
		%>

	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= searchURL %>" method="post" name="fm1">
			<liferay-util:include page="<%= searchPage %>" servletContext="<%= application %>" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>