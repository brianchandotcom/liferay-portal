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
List<Group> groups = (List<Group>)request.getAttribute(SiteAdminWebKeys.GROUP_ENTRIES);

if (ListUtil.isEmpty(groups)) {
	long groupId = GetterUtil.getLong((String)request.getAttribute("view.jsp-groupId"), ParamUtil.getLong(request, "groupId"));

	groups = new ArrayList<Group>();

	Group group = (Group)request.getAttribute("view.jsp-group");

	if (group != null) {
		groups.add(group);
	}
	else if (groupId != GroupConstants.DEFAULT_PARENT_GROUP_ID) {
		groups.add(GroupLocalServiceUtil.fetchGroup(groupId));
	}
	else {
		groups.add(siteAdminDisplayContext.getGroup());
	}
}

request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

List<NavigationItem> navigationItems = new ArrayList<>();

NavigationItem navigationItem = new NavigationItem();

navigationItem.setActive(true);
navigationItem.setHref(currentURL);
navigationItem.setLabel(LanguageUtil.get(request, "details"));

navigationItems.add(navigationItem);
%>

<c:choose>
	<c:when test="<%= ListUtil.isNotEmpty(groups) && (groups.size() == 1) %>">

		<%
		Group group = groups.get(0);
		%>

		<c:choose>
			<c:when test="<%= group == null %>">
				<div class="sidebar-header">
					<h4><liferay-ui:message key="sites" /></h4>
				</div>

				<clay:navigation-bar
					items="<%= navigationItems %>"
				/>

				<div class="sidebar-body">
					<h5><liferay-ui:message key="num-of-items" /></h5>

					<p>
						<%= GroupLocalServiceUtil.getGroupsCount(company.getCompanyId(), siteAdminDisplayContext.getGroupId(), true) %>
					</p>
				</div>
			</c:when>
			<c:otherwise>

				<%
				request.setAttribute("info_panel.jsp-group", group);
				%>

				<div class="sidebar-header">
					<ul class="sidebar-header-actions">
						<li>
							<liferay-util:include page="/site_action.jsp" servletContext="<%= application %>" />
						</li>
					</ul>

					<h4><%= HtmlUtil.escape(group.getDescriptiveName()) %></h4>
				</div>

				<clay:navigation-bar
					items="<%= navigationItems %>"
				/>

				<div class="sidebar-body">
					<p>
						<img alt="<%= HtmlUtil.escapeAttribute(group.getDescriptiveName()) %>" class="center-block img-responsive" src="<%= group.getLogoURL(themeDisplay, true) %>" />
					</p>

					<c:if test="<%= group.isOrganization() %>">

						<%
						Organization groupOrganization = OrganizationLocalServiceUtil.getOrganization(group.getOrganizationId());
						%>

						<p>
							<liferay-ui:message arguments="<%= new String[] {groupOrganization.getName(), LanguageUtil.get(request, groupOrganization.getType())} %>" key="this-site-belongs-to-x-which-is-an-organization-of-type-x" translateArguments="<%= false %>" />
						</p>
					</c:if>

					<h5><liferay-ui:message key="members" /></h5>

					<c:if test="<%= (siteAdminDisplayContext.getUsersCount(group) == 0) && (siteAdminDisplayContext.getOrganizationsCount(group) == 0) && (siteAdminDisplayContext.getUserGroupsCount(group) == 0) %>">
						<p>
							<liferay-ui:message key="none" />
						</p>
					</c:if>

					<%
					String portletId = PortletProviderUtil.getPortletId(MembershipRequest.class.getName(), PortletProvider.Action.VIEW);

					PortletURL assignMembersURL = PortalUtil.getControlPanelPortletURL(request, portletId, PortletRequest.RENDER_PHASE);

					assignMembersURL.setParameter("redirect", currentURL);
					assignMembersURL.setParameter("groupId", String.valueOf(group.getGroupId()));
					%>

					<c:if test="<%= siteAdminDisplayContext.getUsersCount(group) > 0 %>">
						<p>
							<aui:a href='<%= HttpUtil.addParameter(assignMembersURL.toString(), "tabs1", "users") %>' label='<%= LanguageUtil.format(request, (siteAdminDisplayContext.getUsersCount(group) == 1) ? "x-user" : "x-users", siteAdminDisplayContext.getUsersCount(group), false) %>' />
						</p>
					</c:if>

					<c:if test="<%= siteAdminDisplayContext.getOrganizationsCount(group) > 0 %>">
						<p>
							<aui:a href='<%= HttpUtil.addParameter(assignMembersURL.toString(), "tabs1", "organizations") %>' label='<%= LanguageUtil.format(request, (siteAdminDisplayContext.getOrganizationsCount(group) == 1) ? "x-organization" : "x-organizations", siteAdminDisplayContext.getOrganizationsCount(group), false) %>' />
						</p>
					</c:if>

					<c:if test="<%= siteAdminDisplayContext.getUserGroupsCount(group) > 0 %>">
						<p>
							<aui:a href='<%= HttpUtil.addParameter(assignMembersURL.toString(), "tabs1", "user-groups") %>' label='<%= LanguageUtil.format(request, (siteAdminDisplayContext.getUserGroupsCount(group) == 1) ? "x-user-groups" : "x-user-groups", siteAdminDisplayContext.getUserGroupsCount(group), false) %>' />
						</p>
					</c:if>

					<c:if test="<%= siteAdminDisplayContext.getPendingRequestsCount(group) > 0 %>">
						<h5><liferay-ui:message key="request-pending" /></h5>

						<liferay-portlet:renderURL portletName="<%= portletId %>" var="viewMembershipRequestsURL">
							<portlet:param name="mvcPath" value="/view_membership_requests.jsp" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
						</liferay-portlet:renderURL>

						<p>
							<aui:a href="<%= viewMembershipRequestsURL %>" label='<%= LanguageUtil.format(request, (siteAdminDisplayContext.getPendingRequestsCount(group) == 1) ? "x-request-pending" : "x-requests-pending", siteAdminDisplayContext.getPendingRequestsCount(group), false) %>' />
						</p>
					</c:if>

					<h5><liferay-ui:message key="membership-type" /></h5>

					<p>
						<liferay-ui:message key="<%= GroupConstants.getTypeLabel(group.getType()) %>" />
					</p>

					<c:if test="<%= Validator.isNotNull(group.getDescription()) %>">
						<h5><liferay-ui:message key="description" /></h5>

						<p>
							<%= HtmlUtil.escape(group.getDescription()) %>
						</p>
					</c:if>

					<liferay-asset:asset-categories-summary
						className="<%= Group.class.getName() %>"
						classPK="<%= group.getGroupId() %>"
					/>

					<liferay-asset:asset-tags-summary
						className="<%= Group.class.getName() %>"
						classPK="<%= group.getGroupId() %>"
					/>
				</div>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<h4><liferay-ui:message arguments="<%= groups.size() %>" key="x-items-are-selected" /></h4>
		</div>

		<clay:navigation-bar
			items="<%= navigationItems %>"
		/>

		<div class="sidebar-body">
			<h5><liferay-ui:message arguments="<%= groups.size() %>" key="x-items-are-selected" /></h5>
		</div>
	</c:otherwise>
</c:choose>