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

<%@ include file="/wiki/init.jsp" %>

<div class="sidebar-header">
	<h4><%= LanguageUtil.get(request, "wikis") %></h4>
</div>

<aui:nav-bar>
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="details" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<div class="sidebar-body">
	<h5><liferay-ui:message key="nodes" /></h5>

	<p>
		<%= WikiNodeServiceUtil.getNodesCount(scopeGroupId) %>
	</p>
</div>