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
long groupId = siteAdminDisplayContext.getGroupId();

Group group = siteAdminDisplayContext.getGroup();

String displayStyle = siteAdminDisplayContext.getDisplayStyle();

SearchContainer groupSearch = siteAdminDisplayContext.getSearchContainer();

PortletURL portletURL = siteAdminDisplayContext.getPortletURL();

PortletURL searchURL = siteAdminDisplayContext.getSearchURL();

pageContext.setAttribute("searchURL", searchURL);

request.setAttribute("view.jsp-displayStyle", displayStyle);

request.setAttribute("view.jsp-groupSearchContainer", groupSearch);

PortletURL mainURL = renderResponse.createRenderURL();

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "sites"), mainURL.toString());

if (group != null) {
	SitesUtil.addPortletBreadcrumbEntries(group, request, renderResponse);

	renderResponse.setTitle(HtmlUtil.escape(group.getDescriptiveName(locale)));
}
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item href="<%= mainURL.toString() %>" label="sites" selected="<%= true %>" />
	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= searchURL.toString() %>" name="searchFm">
			<liferay-portlet:renderURLParams varImpl="searchURL" />

			<liferay-ui:input-search markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchContainerId" value="sites" />
</liferay-util:include>

<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<div class="sidenav-menu-slider">
		<div class="sidebar sidebar-default sidenav-menu">
			<c:if test="<%= Validator.isNull(siteAdminDisplayContext.getKeywords()) && (group != null) %>">
				<liferay-util:include page="/info_panel.jsp" servletContext="<%= application %>" />
			</c:if>
		</div>
	</div>

	<div class="sidenav-content">
		<portlet:actionURL name="deleteGroups" var="deleteGroupsURL" />

		<aui:form action="<%= deleteGroupsURL %>" name="fm">
			<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

			<div id="breadcrumb">
				<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showPortletBreadcrumb="<%= true %>" />
			</div>

			<liferay-ui:error exception="<%= NoSuchLayoutSetException.class %>">

				<%
				NoSuchLayoutSetException nslse = (NoSuchLayoutSetException)errorException;

				PKParser pkParser = new PKParser(nslse.getMessage());

				Group curGroup = GroupLocalServiceUtil.getGroup(pkParser.getLong("groupId"));
				%>

				<liferay-ui:message arguments="<%= HtmlUtil.escape(curGroup.getDescriptiveName(locale)) %>" key="site-x-does-not-have-any-private-pages" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= RequiredGroupException.MustNotDeleteCurrentGroup.class %>" message="the-site-cannot-be-deleted-or-deactivated-because-you-are-accessing-the-site" />
			<liferay-ui:error exception="<%= RequiredGroupException.MustNotDeleteGroupThatHasChild.class %>" message="you-cannot-delete-sites-that-have-subsites" />
			<liferay-ui:error exception="<%= RequiredGroupException.MustNotDeleteSystemGroup.class %>" message="the-site-cannot-be-deleted-or-deactivated-because-it-is-a-required-system-site" />

			<liferay-util:include page="/view_entries.jsp" servletContext="<%= application %>">
				<liferay-util:param name="searchContainerId" value="sites" />
			</liferay-util:include>
		</aui:form>
	</div>
</div>
