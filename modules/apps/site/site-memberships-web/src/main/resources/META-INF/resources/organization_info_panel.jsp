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
			<h4><liferay-ui:message key="organizations" /></h4>
		</div>

		<div class="sheet-row">
			<clay:tabs
				tabsItems="<%= siteMembershipsDisplayContext.getTabsItems() %>"
			>
				<div class="sidebar-body">
					<h5><liferay-ui:message key="num-of-organizations" /></h5>

					<p>
						<%=
						OrganizationLocalServiceUtil.searchCount(
							company.getCompanyId(), OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, StringPool.BLANK, StringPool.BLANK, null, null,
							LinkedHashMapBuilder.<String, Object>put(
								"groupOrganization", Long.valueOf(siteMembershipsDisplayContext.getGroupId())
							).put(
								"organizationsGroups", Long.valueOf(siteMembershipsDisplayContext.getGroupId())
							).build())
						%>

					</p>
				</div>
			</clay:tabs>
		</div>
	</c:when>
	<c:when test="<%= ListUtil.isNotEmpty(organizations) && (organizations.size() == 1) %>">

		<%
		Organization organization = organizations.get(0);
		%>

		<div class="sidebar-header">
			<h4>
				<%= organization.getName() %>
			</h4>

			<h6>
				<liferay-ui:message key="<%= organization.getType() %>" />
			</h6>

			<%
			Group group = siteMembershipsDisplayContext.getGroup();
			%>

			<c:if test="<%= group.getOrganizationId() == organization.getOrganizationId() %>">
				<p class="h6 text-muted">
					<liferay-ui:message arguments="<%= new String[] {organization.getName(), LanguageUtil.get(request, organization.getType())} %>" key="this-site-belongs-to-x-which-is-an-organization-of-type-x" translateArguments="<%= false %>" />
				</p>

				<p class="h6 text-muted">
					<liferay-ui:message arguments="<%= new String[] {organization.getName(), StringUtil.toLowerCase(GroupUtil.getGroupTypeLabel(group, locale)), GroupUtil.getGroupTypeLabel(group, locale)} %>" key="all-users-of-x-are-automatically-members-of-the-x" translateArguments="<%= false %>" />
				</p>
			</c:if>
		</div>

		<div class="sheet-row">
			<clay:tabs
				tabsItems="<%= siteMembershipsDisplayContext.getTabsItems() %>"
			>
				<div class="sidebar-body">
					<h5><liferay-ui:message key="num-of-users" /></h5>

					<p>
						<%= UserLocalServiceUtil.getOrganizationUsersCount(organization.getOrganizationId(), WorkflowConstants.STATUS_APPROVED) %>
					</p>

					<%
					Address address = organization.getAddress();

					String city = address.getCity();
					%>

					<c:if test="<%= Validator.isNotNull(city) %>">
						<h5><liferay-ui:message key="city" /></h5>

						<p>
							<%= HtmlUtil.escape(city) %>
						</p>
					</c:if>

					<%
					String region = UsersAdmin.ORGANIZATION_REGION_NAME_ACCESSOR.get(organization);
					%>

					<c:if test="<%= Validator.isNotNull(region) %>">
						<h5><liferay-ui:message key="region" /></h5>

						<p>
							<%= region %>
						</p>
					</c:if>

					<%
					String country = UsersAdmin.ORGANIZATION_COUNTRY_NAME_ACCESSOR.get(organization);
					%>

					<c:if test="<%= Validator.isNotNull(country) %>">
						<h5><liferay-ui:message key="country" /></h5>

						<p>
							<%= country %>
						</p>
					</c:if>
				</div>
			</clay:tabs>
		</div>
	</c:when>
	<c:when test="<%= ListUtil.isNotEmpty(organizations) && (organizations.size() > 1) %>">
		<div class="sidebar-header">
			<h4><liferay-ui:message arguments="<%= organizations.size() %>" key="x-items-are-selected" /></h4>
		</div>

		<div class="sheet-row">
			<clay:tabs
				tabsItems="<%= siteMembershipsDisplayContext.getTabsItems() %>"
			>
				<div class="sidebar-body">
					<h5><liferay-ui:message arguments="<%= organizations.size() %>" key="x-items-are-selected" /></h5>
				</div>
			</clay:tabs>
		</div>
	</c:when>
</c:choose>