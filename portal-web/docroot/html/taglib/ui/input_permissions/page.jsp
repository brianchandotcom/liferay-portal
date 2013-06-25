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

<%--

Do not update the logic in this JSP without also updating
ServiceContext#deriveDefaultPermissions(long, String).

--%>

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.taglib.ui.InputPermissionsParamsTag" %>

<%
String uniqueNamespace = namespace + PortalUtil.getUniqueElementId(request, namespace, StringPool.BLANK);

if (!uniqueNamespace.endsWith(StringPool.UNDERLINE)) {
	uniqueNamespace = uniqueNamespace.concat(StringPool.UNDERLINE);
}

String formName = namespace + request.getAttribute("liferay-ui:input-permissions:formName");
String modelName = (String)request.getAttribute("liferay-ui:input-permissions:modelName");
%>

<c:choose>
	<c:when test="<%= user.getDefaultUser() %>">
		<liferay-ui:message key="not-available" />
	</c:when>
	<c:when test="<%= modelName != null %>">

		<%
		Group siteGroup = GroupLocalServiceUtil.getGroup(themeDisplay.getSiteGroupId());

		Role defaultGroupRole = RoleLocalServiceUtil.getDefaultGroupRole(siteGroup.getGroupId());
		Role guestRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.GUEST);

		String[] roleNames = new String[] {RoleConstants.GUEST, defaultGroupRole.getName()};

		String guestPermissionsName = "guestPermissions";
		String groupPermissionsName = "groupPermissions";

		if (!uniqueNamespace.equals(namespace)) {
			guestPermissionsName = guestPermissionsName + StringPool.UNDERLINE + modelName;
			groupPermissionsName = groupPermissionsName + StringPool.UNDERLINE + modelName;
		}

		List groupPermissions = ListUtil.fromArray(request.getParameterValues(groupPermissionsName));
		List guestPermissions = ListUtil.fromArray(request.getParameterValues(guestPermissionsName));

		List supportedActions = (List)request.getAttribute("liferay-ui:input-permissions:supportedActions");
		List groupDefaultActions = (List)request.getAttribute("liferay-ui:input-permissions:groupDefaultActions");
		List guestDefaultActions = (List)request.getAttribute("liferay-ui:input-permissions:guestDefaultActions");
		List guestUnsupportedActions = (List)request.getAttribute("liferay-ui:input-permissions:guestUnsupportedActions");

		boolean submitted = (request.getParameter(groupPermissionsName) != null);

		boolean inputPermissionsShowOptions = ParamUtil.getBoolean(request, "inputPermissionsShowOptions");

		String inputPermissionsViewRole = ParamUtil.getString(request, "inputPermissionsViewRole", InputPermissionsParamsTag.getDefaultViewRole(modelName, themeDisplay));
		%>

		<input id="<%= uniqueNamespace %>inputPermissionsShowOptions" name="<%= namespace %>inputPermissionsShowOptions" type="hidden" value="<%= inputPermissionsShowOptions %>" />

		<p>
			<label class="inline-label" for="<%= namespace %>inputPermissionsViewRole">
				<liferay-ui:message key="viewable-by" />
			</label>

			<select id="<%= uniqueNamespace %>inputPermissionsViewRole" name="<%= namespace %>inputPermissionsViewRole" onChange="<%= uniqueNamespace + "updatePermissionsView();" %>">

				<%
				String guestRoleLabel = LanguageUtil.format(pageContext, "x-role", guestRole.getTitle(themeDisplay.getLocale()));

				if (PropsValues.PERMISSIONS_CHECK_GUEST_ENABLED) {
					guestRoleLabel = LanguageUtil.get(pageContext, "anyone") + StringPool.SPACE + StringPool.OPEN_PARENTHESIS + guestRoleLabel + StringPool.CLOSE_PARENTHESIS;
				}
				%>

				<option <%= (inputPermissionsViewRole.equals(RoleConstants.GUEST)) ? "selected=\"selected\"" : "" %> value="<%= RoleConstants.GUEST %>"><%= guestRoleLabel %></option>
				<option <%= (inputPermissionsViewRole.equals(defaultGroupRole.getName())) ? "selected=\"selected\"" : "" %> value="<%= defaultGroupRole.getName() %>">
					<c:choose>
						<c:when test="<%= defaultGroupRole.getName().equals(RoleConstants.ORGANIZATION_USER) %>">
							<liferay-ui:message key="organization-members" />
						</c:when>
						<c:when test="<%= defaultGroupRole.getName().equals(RoleConstants.POWER_USER) %>">
							<liferay-ui:message key="power-users" />
						</c:when>
						<c:when test="<%= defaultGroupRole.getName().equals(RoleConstants.SITE_MEMBER) %>">
							<liferay-ui:message key="site-members" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="user" />
						</c:otherwise>
					</c:choose>
				</option>
				<option <%= (inputPermissionsViewRole.equals(RoleConstants.OWNER)) ? "selected=\"selected\"" : "" %> value="<%= RoleConstants.OWNER %>"><liferay-ui:message key="owner" /></option>
			</select>

			<span <%= inputPermissionsShowOptions ? "class=\"hide\"" : "" %> id="<%= uniqueNamespace %>inputPermissionsShowOptionsLink">
				<a href="javascript:<%= uniqueNamespace %>inputPermissionsShowOptions();" style="margin-left: 10px;"><liferay-ui:message key="more-options" /> &raquo;</a> <liferay-ui:icon-help message="input-permissions-more-options-help" />
			</span>

			<a <%= inputPermissionsShowOptions ? "" : "class=\"hide\"" %> href="javascript:<%= uniqueNamespace %>inputPermissionsHideOptions();" id="<%= uniqueNamespace %>inputPermissionsHideOptionsLink" style="margin-left: 10px;">&laquo; <liferay-ui:message key="hide-options" /></a>
		</p>

		<table class="lfr-table <%= inputPermissionsShowOptions ? "" : "hide" %>" id="<%= uniqueNamespace %>inputPermissionsTable">
		<tr>
			<th>
				<liferay-ui:message key="roles" />
			</th>

			<%
			for (int i = 0; i < supportedActions.size(); i++) {
				String action = (String)supportedActions.get(i);
			%>

				<th <%= (action.equals(ActionKeys.VIEW)) ? "class=\"hide\"" : "" %> style="text-align: center;">
					<%= ResourceActionsUtil.getAction(pageContext, action) %>
				</th>

			<%
			}
			%>

		</tr>

		<%
		for (String roleName : roleNames) {
			Role role = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), roleName);
		%>

			<tr>
				<td>
					<%= role.getTitle(themeDisplay.getLocale()) %>
				</td>

				<%
				for (int i = 0; i < supportedActions.size(); i++) {
					String action = (String)supportedActions.get(i);

					boolean checked = false;
					boolean disabled = false;

					if (roleName.equals(RoleConstants.GUEST)) {
						disabled = guestUnsupportedActions.contains(action);

						if (disabled) {
							checked = false;
						}
						else if (submitted) {
							checked = guestPermissions.contains(action);
						}
						else {
							checked = guestDefaultActions.contains(action) && (inputPermissionsViewRole.equals(RoleConstants.GUEST));
						}
					}
					else if (roleName.equals(defaultGroupRole.getName())) {
						if (submitted) {
							checked = groupPermissions.contains(action);
						}
						else {
							checked = groupDefaultActions.contains(action);
						}
					}

					String checkboxFieldId = null;
					String checkboxFieldName = null;

					if (roleName.equals(RoleConstants.GUEST)) {
						checkboxFieldId = uniqueNamespace + "guestPermissions";
						checkboxFieldName = namespace + guestPermissionsName;
					}
					else {
						checkboxFieldId = uniqueNamespace + "groupPermissions";
						checkboxFieldName = namespace + groupPermissionsName;
					}

					checkboxFieldId = checkboxFieldId + StringPool.UNDERLINE + action;
				%>

					<td style="text-align: center;" <%= (action.equals(ActionKeys.VIEW)) ? "class=\"hide-accessible\"" : "" %>>
						<label class="hidden-label" for="<%= checkboxFieldId %>"><liferay-ui:message arguments="<%= new Object[] {ResourceActionsUtil.getAction(pageContext, action), role.getTitle(themeDisplay.getLocale())} %>" key="give-x-permission-to-users-with-role-x" /></label>

						<input <%= checked ? "checked" : "" %> <%= disabled ? "disabled" : "" %> id="<%= checkboxFieldId %>" name="<%= checkboxFieldName %>" title='<%= LanguageUtil.format(pageContext, "give-x-permission-to-users-with-role-x", new Object[] {ResourceActionsUtil.getAction(pageContext, action), role.getTitle(themeDisplay.getLocale())}) %>' type="checkbox" value="<%= action %>" />
					</td>

				<%
				}
				%>

			</tr>

		<%
		}
		%>

		</table>

		<aui:script>
			Liferay.provide(
				window,
				'<%= uniqueNamespace %>inputPermissionsShowOptions',
				function() {
					var A = AUI();

					A.one("#<%= uniqueNamespace %>inputPermissionsHideOptionsLink").show();
					A.one("#<%= uniqueNamespace %>inputPermissionsTable").show();

					A.one("#<%= uniqueNamespace %>inputPermissionsShowOptionsLink").hide();
					A.one("#<%= uniqueNamespace %>inputPermissionsShowOptions").val("true");
				},
				['aui-base']
			);

			Liferay.provide(
				window,
				'<%= uniqueNamespace %>inputPermissionsHideOptions',
				function() {
					var A = AUI();

					A.one("#<%= uniqueNamespace %>inputPermissionsShowOptionsLink").show();
					A.one("#<%= uniqueNamespace %>inputPermissionsTable").hide();

					A.one("#<%= uniqueNamespace %>inputPermissionsHideOptionsLink").hide();
					A.one("#<%= uniqueNamespace %>inputPermissionsShowOptions").val("false");
				},
				['aui-base']
			);

			Liferay.provide(
				window,
				'<%= uniqueNamespace %>updatePermissionsView',
				function() {
					var A = AUI();

					var viewableBySelect = A.one("#<%= uniqueNamespace %>inputPermissionsViewRole");
					var guestViewCheckbox = A.one("#<%= uniqueNamespace %>guestPermissions_VIEW");
					var groupViewCheckbox = A.one("#<%= uniqueNamespace %>groupPermissions_VIEW");

					if (viewableBySelect.val() == '<%= RoleConstants.GUEST %>') {
						guestViewCheckbox.set("checked", true);
						groupViewCheckbox.set("checked", false);
					}
					else if (viewableBySelect.val() == '<%= defaultGroupRole.getName() %>') {
						guestViewCheckbox.set("checked", false);
						groupViewCheckbox.set("checked", true);
					}
					else {
						guestViewCheckbox.set("checked", false);
						groupViewCheckbox.set("checked", false);
					}
				},
				['aui-base']
			);
		</aui:script>
	</c:when>
	<c:otherwise>

		<%
		boolean addGroupPermissions = ParamUtil.getBoolean(request, "addGroupPermissions", true);
		boolean addGuestPermissions = ParamUtil.getBoolean(request, "addGuestPermissions", true);
		%>

		<input name="<%= namespace %>addGroupPermissions" type="hidden" value="<%= addGroupPermissions %>" />
		<input name="<%= namespace %>addGuestPermissions" type="hidden" value="<%= addGuestPermissions %>" />

		<input <%= addGroupPermissions ? "checked" : "" %> name="<%= namespace %>addGroupPermissionsBox" type="checkbox" onClick="document.<%= formName %>.<%= namespace %>addGroupPermissions.value = this.checked; <%= namespace %>checkGroupAndGuestPermissions();"> <liferay-ui:message key="assign-default-permissions-to-site" /><br />
		<input <%= addGuestPermissions ? "checked" : "" %> name="<%= namespace %>addGuestPermissionsBox" type="checkbox" onClick="document.<%= formName %>.<%= namespace %>addGuestPermissions.value = this.checked; <%= namespace %>checkGroupAndGuestPermissions();"> <liferay-ui:message key="assign-default-permissions-to-guest" /><br />
		<input <%= !addGroupPermissions && !addGuestPermissions ? "checked" : "" %> name="<%= namespace %>addUserPermissionsBox" type="checkbox" onClick="document.<%= formName %>.<%= namespace %>addGroupPermissions.value = !this.checked; document.<%= formName %>.<%= namespace %>addGuestPermissions.value = !this.checked; <%= namespace %>checkUserPermissions();" /> <liferay-ui:message key="only-assign-permissions-to-me" />

		<aui:script>
			function <%= namespace %>checkGroupAndGuestPermissions() {
				if (document.<%= formName %>.<%= namespace %>addGroupPermissionsBox.checked ||
					document.<%= formName %>.<%= namespace %>addGuestPermissionsBox.checked) {

					document.<%= formName %>.<%= namespace %>addUserPermissionsBox.checked = false;
				}
				else if (!document.<%= formName %>.<%= namespace %>addGroupPermissionsBox.checked &&
						 !document.<%= formName %>.<%= namespace %>addGuestPermissionsBox.checked) {

					document.<%= formName %>.<%= namespace %>addUserPermissionsBox.checked = true;
				}
			}

			function <%= namespace %>checkUserPermissions() {
				if (document.<%= formName %>.<%= namespace %>addUserPermissionsBox.checked) {
					document.<%= formName %>.<%= namespace %>addGroupPermissionsBox.checked = false;
					document.<%= formName %>.<%= namespace %>addGuestPermissionsBox.checked = false;
				}
				else {
					document.<%= formName %>.<%= namespace %>addGroupPermissionsBox.checked = true;
					document.<%= formName %>.<%= namespace %>addGuestPermissionsBox.checked = true;
				}
			}
		</aui:script>
	</c:otherwise>
</c:choose>