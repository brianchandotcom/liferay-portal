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
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

long roleId = ParamUtil.getLong(request, "roleId");

Role role = RoleServiceUtil.fetchRole(roleId);

String roleName = null;

if (role != null) {
	roleName = role.getName();
}

String subtype = BeanParamUtil.getString(role, request, "subtype");

RoleTypeContributor currentRoleTypeContributor = RoleTypeContributorRetrieverUtil.getCurrentRoleTypeContributor(request);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle((role == null) ? LanguageUtil.get(request, "new-role") : role.getTitle(locale));
%>

<liferay-util:include page="/edit_role_tabs.jsp" servletContext="<%= application %>" />

<c:if test="<%= role != null %>">
	<c:choose>
		<c:when test="<%= currentRoleTypeContributor.getType() == RoleConstants.TYPE_REGULAR %>">
			<liferay-ui:success key="roleCreated" message='<%= LanguageUtil.format(request, "x-was-created-successfully.-you-can-now-define-its-permissions-and-assign-users", HtmlUtil.escape(roleName)) %>' />
		</c:when>
		<c:otherwise>
			<liferay-ui:success key="roleCreated" message='<%= LanguageUtil.format(request, "x-was-created-successfully.-you-can-now-define-its-permissions", HtmlUtil.escape(roleName)) %>' />
		</c:otherwise>
	</c:choose>
</c:if>

<portlet:actionURL name="editRole" var="editRoleURL">
	<portlet:param name="mvcPath" value="/edit_role.jsp" />
	<portlet:param name="backURL" value="<%= backURL %>" />
</portlet:actionURL>

<portlet:renderURL var="editRoleRenderURL">
	<portlet:param name="mvcPath" value="/edit_role.jsp" />
	<portlet:param name="tabs1" value="details" />
	<portlet:param name="backURL" value="<%= backURL %>" />
	<portlet:param name="roleId" value="<%= String.valueOf(roleId) %>" />
	<portlet:param name="roleType" value="<%= String.valueOf(currentRoleTypeContributor.getType()) %>" />
</portlet:renderURL>

<aui:form action="<%= editRoleURL %>" cssClass="container-fluid container-fluid-max-xl container-form-view" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= editRoleRenderURL %>" />
	<aui:input name="roleId" type="hidden" value="<%= roleId %>" />

	<liferay-ui:error exception="<%= DuplicateRoleException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= RequiredRoleException.class %>" message="old-role-name-is-a-required-system-role" />

	<aui:model-context bean="<%= role %>" model="<%= Role.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<c:choose>
				<c:when test="<%= role == null %>">
					<aui:select label="type" name="roleType">

						<%
						List<RoleTypeContributor> roleTypeContributors = RoleTypeContributorRetrieverUtil.getRoleTypeContributors(request);

						for (RoleTypeContributor roleTypeContributor : roleTypeContributors) {
						%>

							<aui:option label="<%= roleTypeContributor.getName() %>" value="<%= roleTypeContributor.getType() %>" />

						<%
						}
						%>

					</aui:select>
				</c:when>
				<c:otherwise>
					<aui:input label="type" name="typeLabel" type="resource" value="<%= LanguageUtil.get(request, currentRoleTypeContributor.getName()) %>" />

					<c:if test="<%= role == null %>">
						<aui:input name="roleType" type="hidden" value="<%= String.valueOf(currentRoleTypeContributor.getType()) %>" />
					</c:if>
				</c:otherwise>
			</c:choose>

			<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="title" />
			<aui:input name="description" />

			<c:if test="<%= role != null %>">

				<%
				String[] subtypes = currentRoleTypeContributor.getSubtypes();
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

			<%
			String nameLabel = LanguageUtil.get(request, "role-key");
			%>

			<liferay-ui:error exception="<%= RoleNameException.class %>">
				<p>
					<liferay-ui:message arguments="<%= new String[] {nameLabel, RoleConstants.getNameGeneralRestrictions(locale, PropsValues.ROLES_NAME_ALLOW_NUMERIC), RoleConstants.NAME_RESERVED_WORDS} %>" key="the-x-cannot-be-x-or-a-reserved-word-such-as-x" />
				</p>

				<p>
					<liferay-ui:message arguments="<%= new String[] {nameLabel, RoleConstants.NAME_INVALID_CHARACTERS} %>" key="the-x-cannot-contain-the-following-invalid-characters-x" />
				</p>
			</liferay-ui:error>

			<c:choose>
				<c:when test="<%= (role != null) && role.isSystem() %>">
					<aui:input disabled="<%= true %>" helpMessage="key-field-help" label="key" name="name" value="<%= roleName %>" />
				</c:when>
				<c:otherwise>
					<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" helpMessage="key-field-help" label="key" name="name" />
				</c:otherwise>
			</c:choose>

			<c:if test="<%= (role != null) && roleName.equals(RoleConstants.SITE_ADMINISTRATOR) %>">
				<aui:input helpMessage="allow-subsite-management-help" label="allow-subsite-management" name="manageSubgroups" type="toggle-switch" value="<%= ResourcePermissionLocalServiceUtil.hasResourcePermission(company.getCompanyId(), Group.class.getName(), ResourceConstants.SCOPE_GROUP_TEMPLATE, String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID), roleId, ActionKeys.MANAGE_SUBGROUPS) %>" />
			</c:if>

			<%
			ExpandoBridge roleExpandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(company.getCompanyId(), Role.class.getName(), (role != null) ? role.getRoleId() : 0);

			Map<String, Serializable> roleCustomAttributes = roleExpandoBridge.getAttributes();
			%>

			<c:if test="<%= roleCustomAttributes.size() > 0 %>">
				<aui:fieldset-group markupView="lexicon">
					<aui:fieldset>
						<liferay-expando:custom-attribute-list
							className="<%= Role.class.getName() %>"
							classPK="<%= (role != null) ? role.getRoleId() : 0 %>"
							editable="<%= true %>"
							label="<%= true %>"
						/>
					</aui:fieldset>
				</aui:fieldset-group>
			</c:if>

			<aui:button-row>
				<aui:button type="submit" />

				<aui:button href="<%= backURL %>" type="cancel" />
			</aui:button-row>
		</aui:fieldset>
	</aui:fieldset-group>
</aui:form>

<c:if test="<%= role == null %>">
	<aui:script require="frontend-js-web/liferay/debounce/debounce.es as debounceModule">
		var form = document.getElementById('<portlet:namespace />fm');

		if (form) {
			var nameInput = form.querySelector('#<portlet:namespace />name');
			var titleInput = form.querySelector('#<portlet:namespace />title');

			if (nameInput && titleInput) {
				var debounce = debounceModule.default;

				var handleOnTitleInput = function(event) {
					var value = event.target.value;

					if (nameInput.hasAttribute('maxLength')) {
						value = value.substring(0, nameInput.getAttribute('maxLength'));
					}

					nameInput.value = value;
				};

				titleInput.addEventListener('input', debounce(handleOnTitleInput, 200));
			}
		}
	</aui:script>
</c:if>