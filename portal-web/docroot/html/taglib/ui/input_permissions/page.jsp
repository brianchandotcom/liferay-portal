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
	<c:when test="<%= modelName != null %>">

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

				<span <%= inputPermissionsShowOptions ? "class=\"hide\"" : "" %> id="<%= uniqueNamespace %>inputPermissionsShowOptionsLink">
					<a href="javascript:<%= uniqueNamespace %>inputPermissionsShowOptions();"><liferay-ui:message key="more-options" /></a> <liferay-ui:icon-help message="input-permissions-more-options-help" />
				</span>

				<a <%= inputPermissionsShowOptions ? "" : "class=\"hide\"" %> href="javascript:<%= uniqueNamespace %>inputPermissionsHideOptions();" id="<%= uniqueNamespace %>inputPermissionsHideOptionsLink"><liferay-ui:message key="hide-options" /></a>
			</p>
		</c:if>

		<div class="permissions-table-container table-responsive <%= (inputPermissionsShowOptions || !supportedActions.contains(ActionKeys.VIEW)) ? "" : "hide" %>" id="<%= uniqueNamespace %>inputPermissionsTable">
			<table class="table table-list">
				<thead>
					<tr>
						<th>
							<liferay-ui:message key="roles" />
						</th>

						<%
						for (int i = 0; i < supportedActions.size(); i++) {
							String action = (String)supportedActions.get(i);
						%>

							<th <%= action.equals(ActionKeys.VIEW) ? "class=\"hide-accessible\"" : "" %>>
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

							<td <%= action.equals(ActionKeys.VIEW) ? "class=\"hide-accessible\"" : "" %>>
								<label class="sr-only" for="<%= checkboxFieldId %>"><liferay-ui:message arguments="<%= new Object[] {ResourceActionsUtil.getAction(request, action), role.getTitle(themeDisplay.getLocale())} %>" key="give-x-permission-to-users-with-role-x" translateArguments="<%= false %>" /></label>

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

		<aui:script>
			function <%= uniqueNamespace %>inputPermissionsHideOptions() {
				<%= uniqueNamespace %>togglePermissionsOptions(false);
			}

			function <%= uniqueNamespace %>inputPermissionsShowOptions() {
				<%= uniqueNamespace %>togglePermissionsOptions(true);
			}

			function <%= uniqueNamespace %>togglePermissionsOptions(force) {
				var $ = AUI.$;

				$('#<%= uniqueNamespace %>inputPermissionsHideOptionsLink').toggleClass('hide', !force);
				$('#<%= uniqueNamespace %>inputPermissionsTable').toggleClass('hide', !force);

				$('#<%= uniqueNamespace %>inputPermissionsShowOptionsLink').toggleClass('hide', force);
				$('#<%= uniqueNamespace %>inputPermissionsShowOptions').val(force);
			}

			function <%= uniqueNamespace %>updatePermissionsView() {
				var $ = AUI.$;

				var viewableBy = $('#<%= uniqueNamespace %>inputPermissionsViewRole').val();

				var checkGroupViewPermissions = false;
				var checkGuestViewPermissions = false;

				if (viewableBy == '<%= RoleConstants.GUEST %>') {
					checkGuestViewPermissions = true;
				}
				else if (viewableBy == '<%= defaultGroupRole.getName() %>') {
					checkGroupViewPermissions = true;
				}

				$('#<%= uniqueNamespace %>guestPermissions_VIEW').prop('checked', checkGuestViewPermissions);
				$('#<%= uniqueNamespace %>groupPermissions_VIEW').prop('checked', checkGroupViewPermissions);
			}
		</aui:script>
	</c:when>
	<c:otherwise>

		<%
		boolean addGroupPermissions = ParamUtil.getBoolean(request, "addGroupPermissions", true);
		boolean addGuestPermissions = ParamUtil.getBoolean(request, "addGuestPermissions", true);
		%>

		<input name="<%= namespace %>addGroupPermissions" type="hidden" value="<%= addGroupPermissions %>" />
		<input name="<%= namespace %>addGuestPermissions" type="hidden" value="<%= addGuestPermissions %>" />

		<div class="checkbox">
			<label>
				<input <%= addGroupPermissions ? "checked" : "" %> name="<%= namespace %>addGroupPermissionsBox" onClick="<%= namespace %>checkGroupAndGuestPermissions();" type="checkbox" /> <liferay-ui:message key="assign-default-permissions-to-site" />
			</label>
		</div>

		<div class="checkbox">
			<label>
				<input <%= addGuestPermissions ? "checked" : "" %> name="<%= namespace %>addGuestPermissionsBox" onClick="<%= namespace %>checkGroupAndGuestPermissions();" type="checkbox" /> <liferay-ui:message key="assign-default-permissions-to-guest" />
			</label>
		</div>

		<div class="checkbox">
			<label>
				<input <%= (!addGroupPermissions && !addGuestPermissions) ? "checked" : "" %> name="<%= namespace %>addUserPermissionsBox" onClick="<%= namespace %>checkUserPermissions();" type="checkbox" /> <liferay-ui:message key="only-assign-permissions-to-me" />
			</label>
		</div>

		<aui:script>
			function <%= namespace %>checkGroupAndGuestPermissions() {
				var form = AUI.$(document.<%= formName %>);

				var groupPermissionsChecked = form.fm('addGroupPermissionsBox').prop('checked');
				var guestPermissionsChecked = form.fm('addGuestPermissionsBox').prop('checked');

				var checkUserPermissions = true;

				if (groupPermissionsChecked || guestPermissionsChecked) {
					checkUserPermissions = false;
				}

				form.fm('addUserPermissionsBox').prop('checked', checkUserPermissions);

				form.fm('addGroupPermissions').val(groupPermissionsChecked);
				form.fm('addGuestPermissions').val(guestPermissionsChecked);
			}

			function <%= namespace %>checkUserPermissions() {
				var form = AUI.$(document.<%= formName %>);

				var checkGroupAndGuestPermissions = true;

				if (form.fm('addUserPermissionsBox').prop('checked')) {
					checkGroupAndGuestPermissions = false;
				}

				form.fm('addGroupPermissionsBox').prop('checked', checkGroupAndGuestPermissions);
				form.fm('addGuestPermissionsBox').prop('checked', checkGroupAndGuestPermissions);

				form.fm('addGroupPermissions').val(checkGroupAndGuestPermissions);
				form.fm('addGuestPermissions').val(checkGroupAndGuestPermissions);
			}
		</aui:script>
	</c:otherwise>
</c:choose>