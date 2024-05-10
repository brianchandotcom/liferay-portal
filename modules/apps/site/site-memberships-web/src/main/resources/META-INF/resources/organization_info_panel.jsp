<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
List<Organization> organizations = (List<Organization>)request.getAttribute(SiteMembershipWebKeys.ORGANIZATIONS);
%>

<c:choose>
	<c:when test="<%= ListUtil.isEmpty(organizations) %>">
		<div class="sidebar-header">
			<div class="h4"><liferay-ui:message key="organizations" /></div>
		</div>

		<div class="sheet-row">
			<clay:tabs
				tabsItems="<%= siteMembershipsDisplayContext.getTabsItems() %>"
			>
				<clay:tabs-panel>
					<div class="h5"><liferay-ui:message key="num-of-organizations" /></div>

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
				</clay:tabs-panel>
			</clay:tabs>
		</div>
	</c:when>
	<c:when test="<%= ListUtil.isNotEmpty(organizations) && (organizations.size() == 1) %>">

		<%
		Organization organization = organizations.get(0);
		%>

		<div class="sidebar-header">
			<div class="h4">
				<%= organization.getName() %>
			</div>

			<div class="h6">
				<liferay-ui:message key="<%= organization.getType() %>" />
			</div>

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
				<clay:tabs-panel>
					<div class="h5"><liferay-ui:message key="num-of-users" /></div>

					<p>
						<%= UserLocalServiceUtil.getOrganizationUsersCount(organization.getOrganizationId(), WorkflowConstants.STATUS_APPROVED) %>
					</p>

					<%
					Address address = organization.getAddress();

					String city = address.getCity();
					%>

					<c:if test="<%= Validator.isNotNull(city) %>">
						<div class="h5"><liferay-ui:message key="city" /></div>

						<p>
							<%= HtmlUtil.escape(city) %>
						</p>
					</c:if>

					<%
					String region = UsersAdminUtil.ORGANIZATION_REGION_NAME_ACCESSOR.get(organization);
					%>

					<c:if test="<%= Validator.isNotNull(region) %>">
						<div class="h5"><liferay-ui:message key="region" /></div>

						<p>
							<%= region %>
						</p>
					</c:if>

					<%
					String country = UsersAdminUtil.ORGANIZATION_COUNTRY_NAME_ACCESSOR.get(organization);
					%>

					<c:if test="<%= Validator.isNotNull(country) %>">
						<div class="h5"><liferay-ui:message key="country" /></div>

						<p>
							<%= country %>
						</p>
					</c:if>
				</clay:tabs-panel>
			</clay:tabs>
		</div>
	</c:when>
	<c:when test="<%= ListUtil.isNotEmpty(organizations) && (organizations.size() > 1) %>">
		<div class="sidebar-header">
			<div class="h4"><liferay-ui:message arguments="<%= organizations.size() %>" key="x-items-are-selected" /></div>
		</div>

		<div class="sheet-row">
			<clay:tabs
				tabsItems="<%= siteMembershipsDisplayContext.getTabsItems() %>"
			>
				<clay:tabs-panel>
					<div class="h5"><liferay-ui:message arguments="<%= organizations.size() %>" key="x-items-are-selected" /></div>
				</clay:tabs-panel>
			</clay:tabs>
		</div>
	</c:when>
</c:choose>