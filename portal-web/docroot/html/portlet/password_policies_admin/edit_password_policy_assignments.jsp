<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/password_policies_admin/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1");
String tabs2 = ParamUtil.getString(request, "tabs2", "users");
String tabs3 = ParamUtil.getString(request, "tabs3", "current");

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

String redirect = ParamUtil.getString(request, "redirect");

PasswordPolicy passwordPolicy = (PasswordPolicy)request.getAttribute(WebKeys.PASSWORD_POLICY);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/password_policies_admin/edit_password_policy_assignments");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("passwordPolicyId", String.valueOf(passwordPolicy.getPasswordPolicyId()));

PortalUtil.addPortletBreadcrumbEntry(request, passwordPolicy.getName(), null);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "assign-members"), portletURL.toString());

portletURL.setParameter("tabs2", tabs2);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, tabs2), portletURL.toString());

portletURL.setParameter("tabs3", tabs3);
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= false %>"
	title="<%= passwordPolicy.getName() %>"
/>

<liferay-ui:tabs
	names="users,organizations"
	param="tabs2"
	url="<%= portletURL.toString() %>"
/>

<portlet:actionURL var="editAssignmentsURL">
	<portlet:param name="struts_action" value="/password_policies_admin/edit_password_policy_assignments" />
</portlet:actionURL>

<aui:form action="<%= editAssignmentsURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="tabs3" type="hidden" value="<%= tabs3 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="passwordPolicyId" type="hidden" value="<%= String.valueOf(passwordPolicy.getPasswordPolicyId()) %>" />

	<c:choose>
		<c:when test='<%= tabs2.equals("users") %>'>
			<aui:input name="addUserIds" type="hidden" />
			<aui:input name="removeUserIds" type="hidden" />

			<liferay-ui:tabs
				names="current,available"
				param="tabs3"
				url="<%= portletURL.toString() %>"
			/>

			<liferay-ui:search-container
				rowChecker="<%= new UserPasswordPolicyChecker(renderResponse, passwordPolicy) %>"
				searchContainer="<%= new UserSearch(renderRequest, portletURL) %>"
				var="userSearchContainer"
			>
				<liferay-ui:search-form
					page="/html/portlet/users_admin/user_search.jsp"
				/>

				<%
				UserSearchTerms searchTerms = (UserSearchTerms)userSearchContainer.getSearchTerms();

				LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

				if (tabs3.equals("current")) {
					userParams.put("usersPasswordPolicies", new Long(passwordPolicy.getPasswordPolicyId()));
				}
				%>

				<liferay-ui:search-container-results>
					<%@ include file="/html/portlet/users_admin/user_search_results.jspf" %>
				</liferay-ui:search-container-results>

				<liferay-ui:search-container-row
					className="com.liferay.portal.model.User"
					escapedModel="<%= true %>"
					keyProperty="userId"
					modelVar="user2"
					rowIdProperty="screenName"
				>
					<liferay-ui:search-container-column-text
						name="name"
					>

						<%= (user2.getFullName()) %>

						<%
						PasswordPolicyRel passwordPolicyRel = PasswordPolicyRelLocalServiceUtil.fetchPasswordPolicyRel(User.class.getName(), user.getUserId());
						%>

						<c:if test="<%= (passwordPolicyRel != null) && (passwordPolicyRel.getPasswordPolicyId() != passwordPolicy.getPasswordPolicyId()) %>">

							<%
							PasswordPolicy curPasswordPolicy = PasswordPolicyLocalServiceUtil.getPasswordPolicy(passwordPolicyRel.getPasswordPolicyId());
							%>

							<portlet:renderURL var="assignMembersURL">
								<portlet:param name="struts_action" value="/password_policies_admin/edit_password_policy_assignments" />
								<portlet:param name="tabs1" value="<%= tabs1 %>" />
								<portlet:param name="tabs2" value="users" />
								<portlet:param name="tabs3" value="current" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="passwordPolicyId" value="<%= String.valueOf(curPasswordPolicy.getPasswordPolicyId()) %>" />
							</portlet:renderURL>

							<liferay-ui:icon-help message='<%= LanguageUtil.format(pageContext, "this-user-is-already-assigned-to-password-policy-x", new Object[] {assignMembersURL, curPasswordPolicy.getName()}) %>' />
						</c:if>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="screen-name"
						value="<%= user2.getScreenName() %>"
					/>
				</liferay-ui:search-container-row>

				<div class="separator"><!-- --></div>

				<%
				String taglibOnClick = renderResponse.getNamespace() + "updatePasswordPolicyUsers('" + portletURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur + "');";
				%>

				<aui:button onClick="<%= taglibOnClick %>" value="update-associations" />

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>
		</c:when>
		<c:when test='<%= tabs2.equals("organizations") %>'>
			<aui:input name="addOrganizationIds" type="hidden" />
			<aui:input name="removeOrganizationIds" type="hidden" />

			<liferay-ui:tabs
				names="current,available"
				param="tabs3"
				url="<%= portletURL.toString() %>"
			/>

			<liferay-ui:search-container
				rowChecker="<%= new OrganizationPasswordPolicyChecker(renderResponse, passwordPolicy) %>"
				searchContainer="<%= new OrganizationSearch(renderRequest, portletURL) %>"
				var="organizationSearchContainer"
			>
				<liferay-ui:search-form
					page="/html/portlet/users_admin/organization_search.jsp"
				/>

				<%
				OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)organizationSearchContainer.getSearchTerms();

				long parentOrganizationId = OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;

				LinkedHashMap<String, Object> organizationParams = new LinkedHashMap<String, Object>();

				if (tabs3.equals("current")) {
					organizationParams.put("organizationsPasswordPolicies", new Long(passwordPolicy.getPasswordPolicyId()));
				}
				%>

				<liferay-ui:search-container-results>
					<%@ include file="/html/portlet/users_admin/organization_search_results.jspf" %>
				</liferay-ui:search-container-results>

				<liferay-ui:search-container-row
					className="com.liferay.portal.model.Organization"
					escapedModel="<%= true %>"
					keyProperty="organizationId"
					modelVar="organization"
				>
					<liferay-ui:search-container-column-text
						name="name"
						orderable="<%= true %>"
						property="name"
					/>

					<liferay-ui:search-container-column-text
						name="name"
						orderable="<%= true %>"
					>

						<%= HtmlUtil.escape(organization.getName()) %>

						<%
						PasswordPolicyRel passwordPolicyRel = PasswordPolicyRelLocalServiceUtil.fetchPasswordPolicyRel(Organization.class.getName(), organization.getOrganizationId());
						%>

						<c:if test="<%= (passwordPolicyRel != null) && (passwordPolicyRel.getPasswordPolicyId() != passwordPolicy.getPasswordPolicyId()) %>">

							<%
							PasswordPolicy curPasswordPolicy = PasswordPolicyLocalServiceUtil.getPasswordPolicy(passwordPolicyRel.getPasswordPolicyId());
							%>

							<portlet:renderURL var="assignMembersURL">
								<portlet:param name="struts_action" value="/password_policies_admin/edit_password_policy_assignments" />
								<portlet:param name="tabs1" value="<%= tabs1 %>" />
								<portlet:param name="tabs2" value="organizations" />
								<portlet:param name="tabs3" value="current" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="passwordPolicyId" value="<%= String.valueOf(curPasswordPolicy.getPasswordPolicyId()) %>" />
							</portlet:renderURL>

							<liferay-ui:icon-help message='<%= LanguageUtil.format(pageContext, "this-organization-is-already-assigned-to-password-policy-x", new Object[] {assignMembersURL, curPasswordPolicy.getName()}) %>' />
						</c:if>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						buffer="buffer"
						name="parent-organization"
					>

						<%
						if (organization.getParentOrganizationId() > 0) {
							try {
								Organization parentOrganization = OrganizationLocalServiceUtil.getOrganization(organization.getParentOrganizationId());

								buffer.append(HtmlUtil.escape(parentOrganization.getName()));
							}
							catch (Exception e) {
							}
						}
						%>

					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="type"
						orderable="<%= true %>"
						value="<%= LanguageUtil.get(pageContext, organization.getType()) %>"
					/>

					<liferay-ui:search-container-column-text
						name="city"
						value="<%= HtmlUtil.escape(organization.getAddress().getCity()) %>"
					/>

					<liferay-ui:search-container-column-text
						name="region"
					>
						<liferay-ui:write bean="<%= organization %>" property="region" />
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="country"
					>
						<liferay-ui:write bean="<%= organization %>" property="country" />
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<div class="separator"><!-- --></div>

				<%
				String taglibOnClick = renderResponse.getNamespace() + "updatePasswordPolicyOrganizations('" + portletURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur + "');";
				%>

				<aui:button onClick="<%= taglibOnClick %>" value="update-associations" />

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>
		</c:when>
	</c:choose>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />updatePasswordPolicyOrganizations',
		function(assignmentsRedirect) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "password_policy_organizations";
			document.<portlet:namespace />fm.<portlet:namespace />assignmentsRedirect.value = assignmentsRedirect;
			document.<portlet:namespace />fm.<portlet:namespace />addOrganizationIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');
			document.<portlet:namespace />fm.<portlet:namespace />removeOrganizationIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updatePasswordPolicyUsers',
		function(assignmentsRedirect) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "password_policy_users";
			document.<portlet:namespace />fm.<portlet:namespace />assignmentsRedirect.value = assignmentsRedirect;
			document.<portlet:namespace />fm.<portlet:namespace />addUserIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');
			document.<portlet:namespace />fm.<portlet:namespace />removeUserIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);
</aui:script>