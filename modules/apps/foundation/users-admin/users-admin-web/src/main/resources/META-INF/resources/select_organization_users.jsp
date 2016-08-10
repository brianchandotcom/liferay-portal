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
String tabs1 = ParamUtil.getString(request, "tabs1");

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

String redirect = ParamUtil.getString(request, "redirect");

long organizationId = ParamUtil.getLong(request, "organizationId");

Organization organization = OrganizationServiceUtil.fetchOrganization(organizationId);

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/users_admin/edit_organization_assignments");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("organizationId", String.valueOf(organization.getOrganizationId()));

UsersAdminUtil.addPortletBreadcrumbEntries(organization, request, renderResponse);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "assign-members"), currentURL);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(organization.getName());

SearchContainer userSearchContainer = new UserSearch(renderRequest, portletURL);
%>

<liferay-ui:membership-policy-error />

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="users" selected="<%= true %>" />
	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= portletURL.toString() %>" name="searchFm">
			<liferay-ui:input-search markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="users"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= userSearchContainer.getOrderByCol() %>"
			orderByType="<%= userSearchContainer.getOrderByType() %>"
			orderColumns='<%= new String[] {"first-name", "last-name", "screen-name"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="organizationId" type="hidden" value="<%= organization.getOrganizationId() %>" />

	<aui:input name="addUserIds" type="hidden" />
	<aui:input name="removeUserIds" type="hidden" />

	<liferay-ui:search-container
		id="users"
		rowChecker="<%= new UserOrganizationChecker(renderResponse, organization) %>"
		searchContainer="<%= userSearchContainer %>"
	>
		<%
		UserSearchTerms searchTerms = (UserSearchTerms)userSearchContainer.getSearchTerms();

		LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

		userParams.put("usersOrgsTree", user.getOrganizations(true));
		%>

		<liferay-ui:user-search-container-results userParams="<%= userParams %>" />

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.User"
			escapedModel="<%= true %>"
			keyProperty="userId"
			modelVar="user2"
			rowIdProperty="screenName"
		>
			<liferay-ui:search-container-column-text
				name="name"
				property="fullName"
				truncate="<%= true %>"
			/>

			<liferay-ui:search-container-column-text
				name="screen-name"
				property="screenName"
				truncate="<%= true %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	function <portlet:namespace />updateOrganizationUsers(assignmentsRedirect) {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('assignmentsRedirect').val(assignmentsRedirect);
		form.fm('addUserIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		form.fm('removeUserIds').val(Liferay.Util.listUncheckedExcept(form, '<portlet:namespace />allRowIds'));

		submitForm(form, '<portlet:actionURL name="/users_admin/edit_organization_assignments" />');
	}
</aui:script>