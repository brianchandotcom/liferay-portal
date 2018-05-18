<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
					boolean checked = false;

					if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
						checked = commerceUserPermissionsDisplayContext.hasUserGroupRole(selUser.getUserId(), role.getRoleId());
					}
					else if (role.getType() == RoleConstants.TYPE_REGULAR) {
						checked = ArrayUtil.contains(userRoleIds, role.getRoleId());
					}
				%>

					<aui:input checked="<%= checked %>" label="<%= role.getTitle(locale) %>" multiple="<%= true %>" name="roleIds" type="checkbox" value="<%= role.getRoleId() %>" />

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