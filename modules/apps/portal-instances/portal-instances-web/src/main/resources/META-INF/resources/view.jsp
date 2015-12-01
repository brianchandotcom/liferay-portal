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

<c:choose>
	<c:when test="<%= permissionChecker.isOmniadmin() %>">

		<%
		int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

		String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/portal_instances/view");
		%>

		<aui:nav-bar markupView="lexicon">
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="instances" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<liferay-frontend:management-bar>
			<liferay-frontend:management-bar-filters>
				<liferay-frontend:management-bar-navigation
					navigationKeys='<%= new String[] {"all"} %>'
					portletURL="<%= renderResponse.createRenderURL() %>"
				/>
			</liferay-frontend:management-bar-filters>

			<liferay-frontend:management-bar-buttons>
				<liferay-frontend:management-bar-display-buttons
					displayViews='<%= new String[] {"list"} %>'
					portletURL="<%= renderResponse.createRenderURL() %>"
					selectedDisplayStyle="<%= displayStyle %>"
				/>
			</liferay-frontend:management-bar-buttons>
		</liferay-frontend:management-bar>

		<portlet:renderURL var="redirectURL">
			<portlet:param name="mvcRenderCommandName" value="/portal_instances/view" />
			<portlet:param name="cur" value="<%= String.valueOf(cur) %>" />
		</portlet:renderURL>

		<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="redirect" type="hidden" value="<%= redirectURL %>" />
			<aui:input name="className" type="hidden" />

			<%@ include file="/instances.jspf" %>
		</aui:form>
	</c:when>
	<c:otherwise>
		<div class="container-fluid-1280">
			<liferay-util:include page="/html/portal/portlet_access_denied.jsp" />
		</div>
	</c:otherwise>
</c:choose>