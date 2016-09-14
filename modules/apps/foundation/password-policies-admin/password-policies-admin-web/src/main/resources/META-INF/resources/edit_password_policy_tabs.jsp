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
String tabs1 = ParamUtil.getString(request, "tabs1", "details");

String redirect = ParamUtil.getString(request, "redirect");

long passwordPolicyId = ParamUtil.getLong(request, "passwordPolicyId");

PasswordPolicy passwordPolicy = PasswordPolicyLocalServiceUtil.fetchPasswordPolicy(passwordPolicyId);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("redirect", redirect);
portletURL.setParameter("passwordPolicyId", String.valueOf(passwordPolicyId));

boolean hasAssignMembersPermission = PasswordPolicyPermissionUtil.contains(permissionChecker, passwordPolicy.getPasswordPolicyId(), ActionKeys.ASSIGN_MEMBERS);
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		PortletURL detailsURL = PortletURLUtil.clone(portletURL, renderResponse);

		detailsURL.setParameter("mvcPath", "/edit_password_policy.jsp");
		detailsURL.setParameter("tabs1", "details");
		%>

		<aui:nav-item href="<%= detailsURL.toString() %>" label="details" selected='<%= tabs1.equals("details") %>' />

		<%
		PortletURL assigneesURL = PortletURLUtil.clone(portletURL, renderResponse);

		assigneesURL.setParameter("mvcPath", "/edit_password_policy_assignments.jsp");
		assigneesURL.setParameter("tabs1", "assignees");

		boolean isShowNav = false;

		if ((passwordPolicy != null) && hasAssignMembersPermission) {
			isShowNav = true;
		}
		%>

		<aui:nav-item cssClass='<%= isShowNav ? StringPool.BLANK : "disabled" %>' href="<%= isShowNav ? assigneesURL.toString() : null %>" label="assignees" selected='<%= tabs1.equals("assignees") %>' />
	</aui:nav>

	<c:if test='<%= hasAssignMembersPermission && tabs1.equals("assignees") %>'>

		<%
		PortletURL searchURL = (PortletURL)request.getAttribute("edit_password_policy_assignments.jsp-portletURL");
		%>

		<aui:nav-bar-search>
			<aui:form action="<%= searchURL.toString() %>" name="searchFm">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>