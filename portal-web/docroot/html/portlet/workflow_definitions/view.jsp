<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/workflow_definitions/init.jsp" %>

<c:choose>
	<c:when test="<%= WorkflowEngineManagerUtil.isDeployed() %>">

		<%
		String tabs1 = ParamUtil.getString(request, "tabs1", "definitions");

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("tabs1", tabs1);
		%>

		<liferay-ui:tabs
			names="definitions,default-configuration,submissions"
			portletURL="<%= portletURL %>"
		/>

		<c:choose>
			<c:when test='<%= tabs1.equals("default-configuration") %>'>
				<liferay-util:include page="/html/portlet/workflow_definition_links/view.jsp" />
			</c:when>
			<c:when test='<%= tabs1.equals("submissions") %>'>
				<liferay-util:include page="/html/portlet/workflow_instances/view.jsp" />
			</c:when>
			<c:otherwise>
				<%@ include file="/html/portlet/workflow_definitions/view_definitions.jspf" %>
			</c:otherwise>
		</c:choose>

		<%
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, tabs1), currentURL);
		%>

	</c:when>
	<c:otherwise>
		<div class="alert alert-info">
			<liferay-ui:message key="no-workflow-engine-is-deployed" />
		</div>
	</c:otherwise>
</c:choose>