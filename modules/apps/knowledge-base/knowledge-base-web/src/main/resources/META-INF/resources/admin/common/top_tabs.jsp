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

<%@ include file="/admin/common/init.jsp" %>

<%
String mvcPath = ParamUtil.getString(request, "mvcPath");
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<c:if test="<%= PortletPermissionUtil.contains(permissionChecker, plid, portletDisplay.getId(), KBActionKeys.VIEW) %>">
			<portlet:renderURL var="viewKBObjectsURL">
				<portlet:param name="mvcPath" value="/admin/view.jsp" />
			</portlet:renderURL>

			<aui:nav-item
				href="<%= viewKBObjectsURL %>"
				label="articles"
				selected='<%= !mvcPath.equals("/admin/view_suggestions.jsp") %>'
			/>
		</c:if>

		<c:if test="<%= AdminPermission.contains(permissionChecker, scopeGroupId, KBActionKeys.VIEW_SUGGESTIONS) %>">
			<portlet:renderURL var="viewKBSuggestionsURL">
				<portlet:param name="mvcPath" value="/admin/view_suggestions.jsp" />
			</portlet:renderURL>

			<aui:nav-item
				href="<%= viewKBSuggestionsURL %>"
				label="suggestions"
				selected='<%= mvcPath.equals("/admin/view_suggestions.jsp") %>'
			/>
		</c:if>
	</aui:nav>

	<aui:nav-bar-search>
		<liferay-portlet:renderURL varImpl="searchURL">
			<portlet:param name="mvcPath" value="/admin/search.jsp" />
		</liferay-portlet:renderURL>

		<aui:form action="<%= searchURL %>" method="get" name="searchFm">
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

			<liferay-ui:input-search id="keywords" markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>