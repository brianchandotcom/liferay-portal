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

<%@ include file="/html/portlet/roles_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

Role role = ActionUtil.getRole(request);

long roleId = BeanParamUtil.getLong(role, request, "roleId");

int type = ParamUtil.getInteger(request, "type");
String subtype = BeanParamUtil.getString(role, request, "subtype");
%>

<liferay-ui:header
	backURL="<%= backURL %>"
	localizeTitle="<%= (role == null) %>"
	title='<%= (role == null) ? "new-role" : role.getTitle(locale) %>'
/>

<c:if test="<%= role != null %>">
	<liferay-util:include page="/html/portlet/roles_admin/edit_role_tabs.jsp">
		<liferay-util:param name="tabs1" value="edit" />
		<liferay-util:param name="backURL" value="<%= backURL %>" />
	</liferay-util:include>
</c:if>

<portlet:actionURL var="editRoleActionURL">
	<portlet:param name="struts_action" value="/roles_admin/edit_role" />
	<portlet:param name="backURL" value="<%= backURL %>" />
</portlet:actionURL>

<aui:form action="<%= editRoleActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (role == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="roleId" type="hidden" value="<%= roleId %>" />

	<liferay-ui:error exception="<%= DuplicateRoleException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= RequiredRoleException.class %>" message="old-role-name-is-a-required-system-role" />
	<liferay-ui:error exception="<%= RoleNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= role %>" model="<%= Role.class %>" />

	<aui:fieldset>
		<c:choose>
			<c:when test="<%= ((role == null) && (type == 0)) %>">
				<aui:select name="type">
					<aui:option label="regular" value="<%= RoleConstants.TYPE_REGULAR %>" />
					<aui:option label="site" value="<%= RoleConstants.TYPE_SITE %>" />
					<aui:option label="organization" value="<%= RoleConstants.TYPE_ORGANIZATION %>" />
				</aui:select>
			</c:when>
			<c:when test="<%= (role == null) %>">
				<aui:input label="type" name="typeLabel" type="resource" value="<%= LanguageUtil.get(request, RoleConstants.getTypeLabel(type)) %>" />

				<aui:input name="type" type="hidden" value="<%= String.valueOf(type) %>" />
			</c:when>
			<c:otherwise>
				<aui:input label="type" name="typeLabel" type="resource" value="<%= LanguageUtil.get(request, role.getTypeLabel()) %>" />
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="<%= (role != null) && role.isSystem() %>">
				<aui:input name="name" type="hidden" value="<%= role.getName() %>" />
			</c:when>
			<c:otherwise>
				<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" label='<%= (role != null) ? "new-name" : "name" %>' name="name" />
			</c:otherwise>
		</c:choose>

		<aui:input name="title" />

		<aui:input name="description" />

		<c:if test="<%= role != null %>">

			<%
			String[] subtypes = null;

			if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
				subtypes = PropsValues.ROLES_ORGANIZATION_SUBTYPES;
			}
			else if (role.getType() == RoleConstants.TYPE_REGULAR) {
				subtypes = PropsValues.ROLES_REGULAR_SUBTYPES;
			}
			else if (role.getType() == RoleConstants.TYPE_SITE) {
				subtypes = PropsValues.ROLES_SITE_SUBTYPES;
			}
			else {
				subtypes = new String[0];
			}
			%>

			<c:if test="<%= subtypes.length > 0 %>">
				<aui:select name="subtype">
					<aui:option value="" />

					<%
					for (String curSubtype : subtypes) {
					%>

						<aui:option label="<%= curSubtype %>" selected="<%= subtype.equals(curSubtype) %>" />

					<%
					}
					%>

				</aui:select>
			</c:if>
		</c:if>

		<aui:fieldset>
			<liferay-ui:custom-attribute-list
				className="<%= Role.class.getName() %>"
				classPK="<%= (role != null) ? role.getRoleId() : 0 %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>
		</aui:fieldset>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<%
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, ((role == null) ? "add-role" : "edit")), currentURL);
%>