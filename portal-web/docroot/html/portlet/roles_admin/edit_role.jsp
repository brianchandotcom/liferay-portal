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

<%@ include file="/html/portlet/roles_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

Role role = (Role)request.getAttribute(WebKeys.ROLE);

long roleId = BeanParamUtil.getLong(role, request, "roleId");

int type = ParamUtil.getInteger(request, "type");
String subtype = BeanParamUtil.getString(role, request, "subtype");
%>

<liferay-util:include page="/html/portlet/roles_admin/toolbar.jsp">
	<liferay-util:param name="toolbarItem" value='<%= (role == null) ? "add" : "view-all" %>' />
</liferay-util:include>

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

<portlet:renderURL var="editRoleRenderURL">
	<portlet:param name="struts_action" value="/roles_admin/edit_role" />
</portlet:renderURL>

<portlet:actionURL var="editRoleActionURL">
	<portlet:param name="struts_action" value="/roles_admin/edit_role" />
</portlet:actionURL>

<aui:form action="<%= editRoleActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (role == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= editRoleRenderURL %>" />
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
				<aui:field-wrapper label="type">
					<%= LanguageUtil.get(pageContext, RoleConstants.getTypeLabel(type)) %>
				</aui:field-wrapper>

				<aui:input name="type" type="hidden" value="<%= String.valueOf(type) %>" />
			</c:when>
			<c:otherwise>
				<aui:field-wrapper label="type">
					<%= LanguageUtil.get(pageContext, role.getTypeLabel()) %>
				</aui:field-wrapper>
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="<%= (role != null) && PortalUtil.isSystemRole(role.getName()) %>">
				<aui:input name="name" type="hidden" value="<%= role.getName() %>" />
			</c:when>
			<c:otherwise>
				<aui:input label='<%= (role != null) ? "new-name" : "name" %>' name="name" />
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

<aui:script>
	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
	</c:if>
</aui:script>

<%
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, ((role == null) ? "add-role" : "edit")), currentURL);
%>