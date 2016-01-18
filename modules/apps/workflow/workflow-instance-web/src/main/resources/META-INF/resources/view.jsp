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
DateSearchEntry dateSearchEntry = new DateSearchEntry();

String displayStyle = workflowInstanceViewDisplayContext.getDisplayStyle();

PortletURL portletURL = workflowInstanceViewDisplayContext.getViewPortletURL();
%>

<div class="container-fluid-1280">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
		<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="<%= workflowInstanceViewDisplayContext.getHeaderTitle() %>" selected="<%= true %>" />
			</aui:nav>

			<aui:nav-bar-search>
				<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1">
					<liferay-ui:input-search markupView="lexicon" />
				</aui:form>
			</aui:nav-bar-search>
		</aui:nav-bar>

		<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>" />

		<%@ include file="/workflow_instance.jspf" %>
	</aui:form>
</div>