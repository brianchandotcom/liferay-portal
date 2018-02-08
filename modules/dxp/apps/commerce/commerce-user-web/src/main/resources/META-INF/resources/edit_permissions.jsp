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
CommerceUserPermissionsDisplayContext commerceUserPermissionsDisplayContext = (CommerceUserPermissionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<Role> roles = commerceUserPermissionsDisplayContext.getRoles();

User selUser = commerceUserPermissionsDisplayContext.getSelectedUser();

long[] userRoleIds = selUser.getRoleIds();
%>

<portlet:actionURL name="assignUserRoles" var="assignUserRolesActionURL" />

<aui:form action="<%= assignUserRolesActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ASSIGN %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="userId" type="hidden" value="<%= selUser.getUserId() %>" />

	<c:choose>
		<c:when test="<%= roles.isEmpty() %>">
			<div class="alert alert-info">
				<liferay-ui:message key="there-are-no-roles" />
			</div>
		</c:when>
		<c:otherwise>
			<aui:fieldset>

				<%
				for (Role role : roles) {
				%>

					<c:choose>
						<c:when test="<%= role.getType() == RoleConstants.TYPE_ORGANIZATION %>">
							<aui:input
								checked="<%= commerceUserPermissionsDisplayContext.hasUserGroupRole(selUser.getUserId(), role.getRoleId()) %>"
								label="<%= role.getTitle(locale) %>"
								name="roleIds"
								type="checkbox"
								value="<%= role.getRoleId() %>"
							/>
						</c:when>
						<c:when test="<%= role.getType() == RoleConstants.TYPE_REGULAR %>">
							<aui:input
								checked="<%= ArrayUtil.contains(userRoleIds, role.getRoleId()) %>"
								label="<%= role.getTitle(locale) %>"
								name="roleIds"
								type="checkbox"
								value="<%= role.getRoleId() %>"
							/>
						</c:when>
					</c:choose>

				<%
				}
				%>

			</aui:fieldset>

			<aui:button-row>
				<aui:button type="submit" />
			</aui:button-row>
		</c:otherwise>
	</c:choose>
</aui:form>