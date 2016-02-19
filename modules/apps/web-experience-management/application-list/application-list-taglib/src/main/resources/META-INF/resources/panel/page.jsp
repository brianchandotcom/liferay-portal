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

<%@ include file="/panel/init.jsp" %>

<%
List<PanelCategory> childPanelCategories = (List<PanelCategory>)request.getAttribute("liferay-application-list:panel:childPanelCategories");
%>

<c:if test="<%= !childPanelCategories.isEmpty() %>">
	<div class="list-group">

		<%
		for (PanelCategory childPanelCategory : childPanelCategories) {
		%>

				<liferay-application-list:panel-category-content panelCategory="<%= childPanelCategory %>" showOpen="<%= childPanelCategories.size() == 1 %>" />

		<%
		}
		%>

	</div>
</c:if>