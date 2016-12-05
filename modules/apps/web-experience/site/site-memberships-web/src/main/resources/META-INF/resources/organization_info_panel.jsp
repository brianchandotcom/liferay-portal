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
List<Organization> organizations = (List<Organization>)request.getAttribute(SiteMembershipWebKeys.ORGANIZATIONS);
%>

<c:choose>
	<c:when test="<%= ListUtil.isEmpty(organizations) %>">
		<div class="sidebar-header">
			<h4 class="sidebar-title"><liferay-ui:message key="organizations" /></h4>
		</div>

		<aui:nav-bar cssClass="navbar-no-collapse" markupView="lexicon">
			<aui:nav collapsible="<%= false %>" cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<dl class="sidebar-block">
				<dt class="h5"><liferay-ui:message key="num-of-organizations" /></dt>

				<%
				LinkedHashMap<String, Object> organizationParams = new LinkedHashMap<String, Object>();

				organizationParams.put("groupOrganization", Long.valueOf(siteMembershipsDisplayContext.getGroupId()));
				organizationParams.put("organizationsGroups", Long.valueOf(siteMembershipsDisplayContext.getGroupId()));

				int organizationsCount = OrganizationLocalServiceUtil.searchCount(company.getCompanyId(), OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, StringPool.BLANK, StringPool.BLANK, null, null, organizationParams);
				%>

				<dd class="h6 sidebar-caption">
					<%= organizationsCount %>
				</dd>
		</div>
	</c:when>
	<c:when test="<%= ListUtil.isNotEmpty(organizations) && (organizations.size() == 1) %>">

		<%
		Organization organization = organizations.get(0);
		%>

		<div class="sidebar-header">
			<h4 class="sidebar-title">
				<%= organization.getName() %>
			</h4>

			<h5>
				<%= LanguageUtil.get(request, organization.getType()) %>
			</h5>

			<%
			Group group = siteMembershipsDisplayContext.getGroup();
			%>

			<c:if test="<%= group.getOrganizationId() == organization.getOrganizationId() %>">
				<h6 class="sidebar-caption">
					<liferay-ui:message arguments="<%= new String[] {organization.getName(), LanguageUtil.get(request, organization.getType())} %>" key="this-site-belongs-to-x-which-is-an-organization-of-type-x" translateArguments="<%= false %>" />
				</h6>

				<h6 class="sidebar-caption">
					<liferay-ui:message arguments="<%= organization.getName() %>" key="all-users-of-x-are-automatically-members-of-the-site" translateArguments="<%= false %>" />
				</h6>
			</c:if>
		</div>

		<aui:nav-bar cssClass="navbar-no-collapse" markupView="lexicon">
			<aui:nav collapsible="<%= false %>" cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<dl>
				<dt class="h5"><liferay-ui:message key="num-of-users" /></dt>

				<dd class="h6 sidebar-caption">
					<%= UserLocalServiceUtil.getOrganizationUsersCount(organization.getOrganizationId(), WorkflowConstants.STATUS_APPROVED) %>
				</dd>

				<%
				String city = organization.getAddress().getCity();
				%>

				<c:if test="<%= Validator.isNotNull(city) %>">
					<dt class="h5"><liferay-ui:message key="city" /></dt>

					<dd class="h6 sidebar-caption">
						<%= HtmlUtil.escape(city) %>
					</dd>
				</c:if>

				<%
				String region = UsersAdmin.ORGANIZATION_REGION_NAME_ACCESSOR.get(organization);
				%>

				<c:if test="<%= Validator.isNotNull(region) %>">
					<dt class="h5"><liferay-ui:message key="region" /></dt>

					<dd class="h6 sidebar-caption">
						<%= region %>
					</dd>
				</c:if>

				<%
				String country = UsersAdmin.ORGANIZATION_COUNTRY_NAME_ACCESSOR.get(organization);
				%>

				<c:if test="<%= Validator.isNotNull(country) %>">
					<dt class="h5"><liferay-ui:message key="country" /></dt>

					<dd class="h6 sidebar-caption">
						<%= country %>
					</dd>
				</c:if>
			</dl>
		</div>
	</c:when>
	<c:when test="<%= ListUtil.isNotEmpty(organizations) && (organizations.size() > 1) %>">
		<div class="sidebar-header">
			<h4 class="sidebar-title"><liferay-ui:message arguments="<%= organizations.size() %>" key="x-items-are-selected" /></h4>
		</div>

		<aui:nav-bar cssClass="navbar-no-collapse" markupView="lexicon">
			<aui:nav collapsible="<%= false %>" cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<h5><liferay-ui:message arguments="<%= organizations.size() %>" key="x-items-are-selected" /></h5>
		</div>
	</c:when>
</c:choose>