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

<%@ include file="/application_content/init.jsp" %>

<%
PanelAppContentHelper panelAppContentHelper = new PanelAppContentHelper(request, response);
%>

<c:choose>
	<c:when test="<%= panelAppContentHelper.isValidPortletSelected() %>">

		<%
		panelAppContentHelper.writeContent(pageContext.getOut());
		%>

	</c:when>
	<c:otherwise>
		<div class="portlet-msg-info">
			<liferay-ui:message key="please-select-a-tool-from-the-left-menu" />
		</div>
	</c:otherwise>
</c:choose>