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
%>

<c:choose>
	<c:when test="<%= ListUtil.isNotEmpty(groups) && (groups.size() == 1) %>">

		<%
		Group group = groups.get(0);
		%>

		<c:choose>
			<c:when test="<%= group == null %>">
				<div class="sidebar-header">
					<h4 class="sidebar-title"><liferay-ui:message key="sites" /></h4>
				</div>

				<aui:nav-bar cssClass="navbar-no-collapse" markupView="lexicon">
					<aui:nav collapsible="<%= false %>" cssClass="navbar-nav">
						<aui:nav-item label="details" selected="<%= true %>" />
					</aui:nav>
				</aui:nav-bar>

				<div class="sidebar-body">
					<dl class="sidebar-block">
						<dt class="h5">
							<liferay-ui:message key="num-of-items" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= GroupLocalServiceUtil.getGroupsCount(company.getCompanyId(), siteAdminDisplayContext.getGroupId(), true) %>
						</dd>
					</dl>
				</div>
			</c:when>
			<c:otherwise>

				<%
				request.setAttribute("info_panel.jsp-group", group);
				%>

				<div class="sidebar-header">
					<ul class="sidebar-actions">
						<li>
							<liferay-util:include page="/site_action.jsp" servletContext="<%= application %>" />
						</li>
					</ul>

					<h4 class="sidebar-title"><%= HtmlUtil.escape(group.getDescriptiveName()) %></h4>
				</div>

				<aui:nav-bar cssClass="navbar-no-collapse" markupView="lexicon">
					<aui:nav collapsible="<%= false %>" cssClass="navbar-nav">
						<portlet:renderURL var="mainURL" />

						<aui:nav-item href="<%= mainURL.toString() %>" label="details" selected="<%= true %>" />
					</aui:nav>
				</aui:nav-bar>

				<div class="sidebar-body">
					<div class="crop-img crop-img-center crop-img-middle sidebar-panel">
						<img alt="<%= HtmlUtil.escapeAttribute(group.getDescriptiveName()) %>" class="img-responsive" src="<%= group.getLogoURL(themeDisplay, true) %>" />
					</div>

					<c:if test="<%= group.isOrganization() %>">

						<%
						Organization groupOrganization = OrganizationLocalServiceUtil.getOrganization(group.getOrganizationId());
						%>

						<div>
							<liferay-ui:message arguments="<%= new String[] {groupOrganization.getName(), LanguageUtil.get(request, groupOrganization.getType())} %>" key="this-site-belongs-to-x-which-is-an-organization-of-type-x" translateArguments="<%= false %>" />
						</div>
					</c:if>

					<dl class="sidebar-block">
						<dt class="h5">
							<liferay-ui:message key="members" />
						</dt>

						<%
						String portletId = PortletProviderUtil.getPortletId(MembershipRequest.class.getName(), PortletProvider.Action.VIEW);

						PortletURL assignMembersURL = PortalUtil.getControlPanelPortletURL(request, portletId, PortletRequest.RENDER_PHASE);

						assignMembersURL.setParameter("redirect", currentURL);
						%>

						<c:choose>
							<c:when test="<%= (siteAdminDisplayContext.getUsersCount(group) == 0) && (siteAdminDisplayContext.getOrganizationsCount(group) == 0) && (siteAdminDisplayContext.getUserGroupsCount(group) == 0) %>">
								<dd class="h6 sidebar-caption">
									<liferay-ui:message key="none" />
								</dd>
							</c:when>
							<c:otherwise>
								<dd class="h6 sidebar-caption">
									<c:if test="<%= siteAdminDisplayContext.getUsersCount(group) > 0 %>">
										<div>
											<aui:a href='<%= HttpUtil.addParameter(assignMembersURL.toString(), "tabs1", "users") %>' label='<%= LanguageUtil.format(request, (siteAdminDisplayContext.getUsersCount(group) == 1) ? "x-user" : "x-users", siteAdminDisplayContext.getUsersCount(group), false) %>' />
										</div>
									</c:if>

									<c:if test="<%= siteAdminDisplayContext.getOrganizationsCount(group) > 0 %>">
										<div>
											<aui:a href='<%= HttpUtil.addParameter(assignMembersURL.toString(), "tabs1", "organizations") %>' label='<%= LanguageUtil.format(request, (siteAdminDisplayContext.getOrganizationsCount(group) == 1) ? "x-organization" : "x-organizations", siteAdminDisplayContext.getOrganizationsCount(group), false) %>' />
										</div>
									</c:if>

									<c:if test="<%= siteAdminDisplayContext.getUserGroupsCount(group) > 0 %>">
										<div>
											<aui:a href='<%= HttpUtil.addParameter(assignMembersURL.toString(), "tabs1", "user-groups") %>' label='<%= LanguageUtil.format(request, (siteAdminDisplayContext.getUserGroupsCount(group) == 1) ? "x-user-groups" : "x-user-groups", siteAdminDisplayContext.getUserGroupsCount(group), false) %>' />
										</div>
									</c:if>
								</dd>
							</c:otherwise>
						</c:choose>

						<c:if test="<%= siteAdminDisplayContext.getPendingRequestsCount(group) > 0 %>">
							<dt class="h5">
								<liferay-ui:message key="request-pending" />
							</dt>

							<liferay-portlet:renderURL portletName="<%= portletId %>" var="viewMembershipRequestsURL">
								<portlet:param name="mvcPath" value="/view_membership_requests.jsp" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
							</liferay-portlet:renderURL>

							<dd class="h6 sidebar-caption">
								<aui:a href="<%= viewMembershipRequestsURL %>" label='<%= LanguageUtil.format(request, (siteAdminDisplayContext.getPendingRequestsCount(group) == 1) ? "x-request-pending" : "x-requests-pending", siteAdminDisplayContext.getPendingRequestsCount(group), false) %>' />
							</dd>
						</c:if>

						<dt class="h5">
							<liferay-ui:message key="membership-type" />
						</dt>
						<dd class="h6 sidebar-caption">
							<liferay-ui:message key="<%= GroupConstants.getTypeLabel(group.getType()) %>" />
						</dd>

						<c:if test="<%= Validator.isNotNull(group.getDescription()) %>">
							<dt class="h5">
								<liferay-ui:message key="description" />
							</dt>
							<dd class="h6 sidebar-caption">
								<%= HtmlUtil.escape(group.getDescription()) %>
							</dd>
						</c:if>
					</dl>

					<liferay-ui:asset-categories-summary
						className="<%= Group.class.getName() %>"
						classPK="<%= group.getGroupId() %>"
					/>

					<liferay-ui:asset-tags-summary
						className="<%= Group.class.getName() %>"
						classPK="<%= group.getGroupId() %>"
					/>
				</div>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<h4 class="sidebar-title"><liferay-ui:message arguments="<%= groups.size() %>" key="x-items-are-selected" /></h4>
		</div>

		<aui:nav-bar cssClass="navbar-no-collapse" markupView="lexicon">
			<aui:nav collapsible="<%= false %>" cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<h5><liferay-ui:message arguments="<%= groups.size() %>" key="x-items-are-selected" /></h5>
		</div>
	</c:otherwise>
</c:choose>