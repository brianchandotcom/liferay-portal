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

<%--

Do not update the logic in this JSP without also updating
ServiceContext#deriveDefaultPermissions(long, String).

--%>

<%@ include file="/html/taglib/ui/input_permissions/init.jsp" %>

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
	<c:otherwise>

		<%
		Group siteGroup = GroupLocalServiceUtil.getGroup(themeDisplay.getSiteGroupId());

		Role defaultGroupRole = RoleLocalServiceUtil.getDefaultGroupRole(siteGroup.getGroupId());

		boolean hasViewDefaultGroupRolePermission = RolePermissionUtil.contains(themeDisplay.getPermissionChecker(), siteGroup.getGroupId(), defaultGroupRole.getRoleId(), ActionKeys.VIEW);

		Role guestRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.GUEST);

		String[] roleNames = {RoleConstants.GUEST};

		if (hasViewDefaultGroupRolePermission) {
			roleNames = ArrayUtil.append(roleNames, defaultGroupRole.getName());
		}

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

		<c:if test="<%= supportedActions.contains(ActionKeys.VIEW) %>">
			<p>
				<label class="control-label" for="<%= namespace %>inputPermissionsViewRole">
					<liferay-ui:message key="viewable-by" />
				</label>

				<select class="form-control" id="<%= uniqueNamespace %>inputPermissionsViewRole" name="<%= namespace %>inputPermissionsViewRole" onChange="<%= uniqueNamespace + "updatePermissionsView();" %>">

					<%
					String guestRoleLabel = LanguageUtil.format(request, "x-role", guestRole.getTitle(themeDisplay.getLocale()), false);

					if (PropsValues.PERMISSIONS_CHECK_GUEST_ENABLED) {
						guestRoleLabel = LanguageUtil.get(resourceBundle, "anyone") + StringPool.SPACE + StringPool.OPEN_PARENTHESIS + guestRoleLabel + StringPool.CLOSE_PARENTHESIS;
					}
					%>

					<option <%= inputPermissionsViewRole.equals(RoleConstants.GUEST) ? "selected=\"selected\"" : "" %> value="<%= RoleConstants.GUEST %>"><%= guestRoleLabel %></option>

					<c:if test="<%= hasViewDefaultGroupRolePermission %>">
						<option <%= inputPermissionsViewRole.equals(defaultGroupRole.getName()) ? "selected=\"selected\"" : "" %> value="<%= defaultGroupRole.getName() %>">
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
					</c:if>

					<option <%= inputPermissionsViewRole.equals(RoleConstants.OWNER) ? "selected=\"selected\"" : "" %> value="<%= RoleConstants.OWNER %>"><liferay-ui:message key="owner" /></option>
				</select>

				<div class="mt-3 <%= inputPermissionsShowOptions ? "hide" : "" %>" id="<%= uniqueNamespace %>inputPermissionsShowOptionsLink">
					<a class="btn btn-secondary btn-sm" href="javascript:<%= uniqueNamespace %>inputPermissionsShowOptions();"><liferay-ui:message key="more-options" /></a> <liferay-ui:icon-help message="input-permissions-more-options-help" />
				</div>

				<a class="btn btn-secondary btn-sm mt-3 <%= inputPermissionsShowOptions ? "" : "hide" %>" href="javascript:<%= uniqueNamespace %>inputPermissionsHideOptions();" id="<%= uniqueNamespace %>inputPermissionsHideOptionsLink"><liferay-ui:message key="hide-options" /></a>
			</p>
		</c:if>

		<div class="permissions-table-container table-responsive <%= (inputPermissionsShowOptions || !supportedActions.contains(ActionKeys.VIEW)) ? "" : "hide" %>" id="<%= uniqueNamespace %>inputPermissionsTable">
			<table class="table table-list">
				<thead>
					<tr>
						<th>
							<liferay-ui:message key="role" />
						</th>

						<%
						for (int i = 0; i < supportedActions.size(); i++) {
							String action = (String)supportedActions.get(i);
						%>

							<th class="table-column-text-center">
								<%= ResourceActionsUtil.getAction(request, action) %>
							</th>

						<%
						}
						%>

					</tr>
				</thead>

				<%
				for (String roleName : roleNames) {
					Role role = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), roleName);
				%>

					<tr>
						<td class="table-title">
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

							<td class="table-column-text-center">
								<label class="sr-only" for="<%= checkboxFieldId %>"><liferay-ui:message arguments="<%= new Object[] {ResourceActionsUtil.getAction(request, action), role.getTitle(themeDisplay.getLocale())} %>" key="give-x-permission-to-users-with-role-x" translateArguments="<%= false %>" /></label>

								<c:if test="<%= action.equals(ActionKeys.VIEW) %>">
									<input <%= checked ? "checked" : "" %> class="hide-accessible" id="<%= checkboxFieldId %>" name="<%= checkboxFieldName %>" type="checkbox" value="<%= action %>" />

									<%
									disabled = true;

									checkboxFieldId = checkboxFieldId + StringPool.UNDERLINE + "display";
									checkboxFieldName = checkboxFieldName + StringPool.UNDERLINE + "display";
									%>

								</c:if>

								<input <%= checked ? "checked" : "" %> <%= disabled ? "disabled" : "" %> id="<%= checkboxFieldId %>" name="<%= checkboxFieldName %>" title='<%= LanguageUtil.format(request, "give-x-permission-to-users-with-role-x", new Object[] {ResourceActionsUtil.getAction(request, action), role.getTitle(themeDisplay.getLocale())}, false) %>' type="checkbox" value="<%= action %>" />
							</td>

						<%
						}
						%>

					</tr>

				<%
				}
				%>

			</table>
		</div>

		<script>
			function <%= uniqueNamespace %>inputPermissionsHideOptions() {
				<%= uniqueNamespace %>togglePermissionsOptions(false);
			}

			function <%= uniqueNamespace %>inputPermissionsShowOptions() {
				<%= uniqueNamespace %>togglePermissionsOptions(true);
			}

			function <%= uniqueNamespace %>togglePermissionsOptions(force) {
				var inputPermissionsHideOptionsLink = document.getElementById('<%= uniqueNamespace %>inputPermissionsHideOptionsLink');

				if (inputPermissionsHideOptionsLink) {
					if (force) {
						inputPermissionsHideOptionsLink.classList.remove('hide');
					}
					else {
						inputPermissionsHideOptionsLink.classList.add('hide');
					}
				}

				var inputPermissionsTable = document.getElementById('<%= uniqueNamespace %>inputPermissionsTable');

				if (inputPermissionsTable) {
					if (force) {
						inputPermissionsTable.classList.remove('hide');
					}
					else {
						inputPermissionsTable.classList.add('hide');
					}
				}

				var inputPermissionsShowOptionsLink = document.getElementById('<%= uniqueNamespace %>inputPermissionsShowOptionsLink');

				if (inputPermissionsShowOptionsLink) {
					if (force) {
						inputPermissionsShowOptionsLink.classList.add('hide');
					}
					else {
						inputPermissionsShowOptionsLink.classList.remove('hide');
					}
				}

				var inputPermissionsShowOptions = document.getElementById('<%= uniqueNamespace %>inputPermissionsShowOptions');

				if (inputPermissionsShowOptions) {
					inputPermissionsShowOptions.value = force;
				}
			}

			function <%= uniqueNamespace %>updatePermissionsView() {
				var permissionsViewRoleInput = document.getElementById('<%= uniqueNamespace %>inputPermissionsViewRole');

				if (permissionsViewRoleInput) {
					var viewableBy = permissionsViewRoleInput.value;

					var checkGroupViewPermissions = false;
					var checkGuestViewPermissions = false;

					if (viewableBy === '<%= RoleConstants.GUEST %>') {
						checkGuestViewPermissions = true;
						checkGroupViewPermissions = true;
					}
					else if (viewableBy === '<%= defaultGroupRole.getName() %>') {
						checkGroupViewPermissions = true;
					}

					<%= uniqueNamespace %>doUpdateViewValue('<%= uniqueNamespace %>guestPermissions_VIEW', checkGuestViewPermissions);
					<%= uniqueNamespace %>doUpdateViewValue('<%= uniqueNamespace %>groupPermissions_VIEW', checkGroupViewPermissions);
				}
			}

			function <%= uniqueNamespace %>doUpdateViewValue(id, checkPermission) {
				var element = document.getElementById(id);

				if (element) {
					element.checked = checkPermission;
				}

				var displayElement = document.getElementById(id + '_display');

				if (displayElement) {
					displayElement.checked = checkPermission;
				}
			}
		</script>
	</c:otherwise>
</c:choose>