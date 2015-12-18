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
long groupId = ParamUtil.getLong(request, "groupId", GroupConstants.DEFAULT_PARENT_GROUP_ID);

Group group = null;

if (groupId > 0) {
	group = GroupServiceUtil.getGroup(groupId);
}

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

String keywords = ParamUtil.getString(request, "keywords");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("groupId", String.valueOf(groupId));

String portletURLString = portletURL.toString();

PortletURL searchURL = renderResponse.createRenderURL();

pageContext.setAttribute("searchURL", searchURL);

String searchURLString = searchURL.toString();

SearchContainer groupSearch = new GroupSearch(renderRequest, portletURL);

if (group != null) {
	SitesUtil.addPortletBreadcrumbEntries(group, request, renderResponse);
}
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="sites" selected="<%= true %>" />
	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= searchURLString %>" name="searchFm">
			<liferay-portlet:renderURLParams varImpl="searchURL" />

			<liferay-ui:input-search markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="sites"
>
	<liferay-frontend:management-bar-buttons>
		<c:if test="<%= Validator.isNull(keywords) && (group != null) %>">
			<liferay-frontend:management-bar-button cssClass="infoPanelToggler" href="javascript:;" icon="info-circle" label="info" />
		</c:if>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= groupSearch.getOrderByCol() %>"
			orderByType="<%= groupSearch.getOrderByType() %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteSites" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<div class="sidenav-menu-slider">
		<div class="sidebar sidebar-default sidenav-menu">
			<c:if test="<%= Validator.isNull(keywords) && (group != null) %>">
				<liferay-util:include page="/view_site_info.jsp" servletContext="<%= application %>" />
			</c:if>
		</div>
	</div>

	<div class="sidenav-content">
		<portlet:actionURL name="deleteGroups" var="deleteGroupsURL" />

		<aui:form action="<%= deleteGroupsURL %>" name="fm">
			<aui:input name="redirect" type="hidden" value="<%= portletURLString %>" />

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

			<c:choose>
				<c:when test="<%= Validator.isNotNull(keywords) %>">
					<%@ include file="/search_results.jspf" %>
				</c:when>
				<c:otherwise>
					<%@ include file="/view_entries.jspf" %>
				</c:otherwise>
			</c:choose>
		</aui:form>
	</div>
</div>

<aui:script>
	$('#<portlet:namespace />infoPanelId').sideNavigation(
		{
			gutter: 15,
			position: 'right',
			toggler: '.infoPanelToggler',
			type: 'relative',
			typeMobile: 'fixed',
			width: 320
		}
	);

	$('#<portlet:namespace />deleteSites').on(
		'click',
		function() {
			if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
				submitForm(AUI.$(document.<portlet:namespace />fm));
			}
		}
	);
</aui:script>