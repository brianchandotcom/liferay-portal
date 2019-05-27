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

<liferay-ui:search-container
	searchContainer="<%= siteAdminDisplayContext.getSearchContainer() %>"
>
	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Group"
		escapedModel="<%= true %>"
		keyProperty="groupId"
		modelVar="curGroup"
		rowIdProperty="friendlyURL"
	>

		<%
		List<Group> childSites = curGroup.getChildren(true);

		String siteImageURL = curGroup.getLogoURL(themeDisplay, false);

		Map<String, Object> rowData = new HashMap<>();

		rowData.put("actions", siteAdminManagementToolbarDisplayContext.getAvailableActions(curGroup));

		row.setData(rowData);
		%>

		<portlet:renderURL var="viewSubsitesURL">
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(curGroup.getGroupId()) %>" />
		</portlet:renderURL>

		<c:choose>
			<c:when test='<%= Objects.equals(siteAdminDisplayContext.getDisplayStyle(), "descriptive") %>'>
				<c:choose>
					<c:when test="<%= Validator.isNotNull(siteImageURL) %>">
						<liferay-ui:search-container-column-image
							src="<%= siteImageURL %>"
						/>
					</c:when>
					<c:otherwise>
						<liferay-ui:search-container-column-icon
							icon="sites"
						/>
					</c:otherwise>
				</c:choose>

				<liferay-ui:search-container-column-text
					colspan="<%= 2 %>"
				>
					<h5>
						<aui:a href="<%= !curGroup.isCompany() ? viewSubsitesURL : StringPool.BLANK %>" label="<%= HtmlUtil.escape(curGroup.getDescriptiveName(locale)) %>" localizeLabel="<%= false %>" />
					</h5>

					<ul class="list-inline">
						<li class="h6 text-default">
							<c:choose>
								<c:when test="<%= curGroup.isActive() %>">
									<liferay-ui:message key="active" />
								</c:when>
								<c:otherwise>
									<liferay-ui:message key="not-active" />
								</c:otherwise>
							</c:choose>
						</li>
						<li class="h6">
							<c:choose>
								<c:when test="<%= !curGroup.isCompany() %>">
									<liferay-ui:message arguments="<%= String.valueOf(childSites.size()) %>" key="x-child-sites" />
								</c:when>
								<c:otherwise>
									-
								</c:otherwise>
							</c:choose>
						</li>
					</ul>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text>
					<clay:dropdown-actions
						defaultEventHandler="<%= SiteAdminWebKeys.SITE_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
						dropdownItems="<%= siteAdminDisplayContext.getActionDropdownItems(curGroup) %>"
					/>
				</liferay-ui:search-container-column-text>
			</c:when>
			<c:when test='<%= Objects.equals(siteAdminDisplayContext.getDisplayStyle(), "icon") %>'>

				<%
				row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
				%>

				<liferay-ui:search-container-column-text>
					<clay:vertical-card
						verticalCard="<%= new SiteVerticalCard(curGroup, liferayPortletRequest, liferayPortletResponse, searchContainer.getRowChecker(), siteAdminDisplayContext) %>"
					/>
				</liferay-ui:search-container-column-text>
			</c:when>
			<c:otherwise>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand table-cell-minw-200 table-title"
					name="name"
					orderable="<%= true %>"
				>
					<aui:a href="<%= !curGroup.isCompany() ? viewSubsitesURL : StringPool.BLANK %>" label="<%= HtmlUtil.escape(curGroup.getDescriptiveName(locale)) %>" localizeLabel="<%= false %>" />

					<c:if test="<%= curGroup.isOrganization() %>">

						<%
						Organization organization = OrganizationLocalServiceUtil.getOrganization(curGroup.getOrganizationId());
						%>

						<span class="organization-type">(<liferay-ui:message key="<%= organization.getType() %>" />)</span>
					</c:if>

					<%
					List<String> names = SitesUtil.getOrganizationNames(curGroup, user);

					names.addAll(SitesUtil.getUserGroupNames(curGroup, user));

					if (ListUtil.isNotEmpty(names)) {
						String message = StringPool.BLANK;

						if (names.size() == 1) {
							message = LanguageUtil.format(request, "you-are-a-member-of-x-because-you-belong-to-x", new Object[] {HtmlUtil.escape(curGroup.getDescriptiveName(locale)), HtmlUtil.escape(names.get(0))}, false);
						}
						else {
							message = LanguageUtil.format(request, "you-are-a-member-of-x-because-you-belong-to-x-and-x", new Object[] {HtmlUtil.escape(curGroup.getDescriptiveName(locale)), HtmlUtil.escape(StringUtil.merge(names.subList(0, names.size() - 1).toArray(new String[names.size() - 1]), ", ")), HtmlUtil.escape(names.get(names.size() - 1))}, false);
						}
					%>

						<liferay-ui:icon-help message="<%= message %>" />

					<%
					}
					%>

				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-smaller table-cell-ws-nowrap"
					name="child-sites"
				>
					<c:choose>
						<c:when test="<%= !curGroup.isCompany() %>">
							<liferay-ui:message arguments="<%= String.valueOf(childSites.size()) %>" key="x-child-sites" />
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
					name="membership-type"
					value="<%= LanguageUtil.get(request, curGroup.getTypeLabel()) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-smallest table-cell-minw-150"
					name="members"
				>
					<span onmouseover="Liferay.Portal.ToolTip.show(this, '<liferay-ui:message key="inherited-memberships-are-not-included-in-members-count" unicode="<%= true %>" />');">

						<%
						int usersCount = UserLocalServiceUtil.getGroupUsersCount(curGroup.getGroupId(), WorkflowConstants.STATUS_APPROVED);
						%>

						<c:if test="<%= usersCount > 0 %>">
							<div class="user-count">
								<%= LanguageUtil.format(request, usersCount > 1 ? "x-users" : "x-user", usersCount, false) %>
							</div>
						</c:if>

						<%
						int organizationsCount = OrganizationLocalServiceUtil.getGroupOrganizationsCount(curGroup.getGroupId());
						%>

						<c:if test="<%= organizationsCount > 0 %>">
							<div class="organization-count">
								<%= LanguageUtil.format(request, organizationsCount > 1 ? "x-organizations" : "x-organization", organizationsCount, false) %>
							</div>
						</c:if>

						<%
						int userGroupsCount = UserGroupLocalServiceUtil.getGroupUserGroupsCount(curGroup.getGroupId());
						%>

						<c:if test="<%= userGroupsCount > 0 %>">
							<div class="user-group-count">
								<%= LanguageUtil.format(request, userGroupsCount > 1 ? "x-user-groups" : "x-user-group", userGroupsCount, false) %>
							</div>
						</c:if>

						<c:if test="<%= (usersCount + organizationsCount + userGroupsCount) <= 0 %>">
							0
						</c:if>
					</span>
				</liferay-ui:search-container-column-text>

				<c:if test="<%= PropsValues.LIVE_USERS_ENABLED %>">
					<liferay-ui:search-container-column-text
						name="online-now"
						value="<%= String.valueOf(LiveUsers.getGroupUsersCount(company.getCompanyId(), curGroup.getGroupId())) %>"
					/>
				</c:if>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-smallest table-cell-ws-nowrap table-column-text-center"
					name="active"
					value='<%= LanguageUtil.get(request, (curGroup.isActive() ? "yes" : "no")) %>'
				/>

				<liferay-ui:search-container-column-text>
					<clay:dropdown-actions
						defaultEventHandler="<%= SiteAdminWebKeys.SITE_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
						dropdownItems="<%= siteAdminDisplayContext.getActionDropdownItems(curGroup) %>"
					/>
				</liferay-ui:search-container-column-text>
			</c:otherwise>
		</c:choose>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="<%= siteAdminDisplayContext.getDisplayStyle() %>"
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<liferay-frontend:component
	componentId="<%= SiteAdminWebKeys.SITE_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
	module="js/SiteDropdownDefaultEventHandler.es"
/>