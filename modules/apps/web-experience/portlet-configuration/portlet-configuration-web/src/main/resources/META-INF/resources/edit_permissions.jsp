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
PortletConfigurationPermissionsDisplayContext portletConfigurationPermissionsDisplayContext = new PortletConfigurationPermissionsDisplayContext(request, renderRequest);

if (Validator.isNotNull(portletConfigurationPermissionsDisplayContext.getModelResource())) {
	PortalUtil.addPortletBreadcrumbEntry(request, HtmlUtil.unescape(portletConfigurationPermissionsDisplayContext.getSelResourceDescription()), null);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "permissions"), currentURL);
}

Resource resource = portletConfigurationPermissionsDisplayContext.getResource();
%>

<div class="edit-permissions portlet-configuration-edit-permissions">
	<div class="portlet-configuration-body-content">
		<clay:navigation-bar
			items="<%= portletConfigurationPermissionsDisplayContext.getNavigationItems() %>"
		/>

		<liferay-frontend:management-bar>
			<liferay-frontend:management-bar-filters>
				<li>
					<aui:form action="<%= portletConfigurationPermissionsDisplayContext.getIteratorURL() %>" name="searchFm">
						<liferay-ui:input-search
							markupView="lexicon"
						/>
					</aui:form>
				</li>
			</liferay-frontend:management-bar-filters>
		</liferay-frontend:management-bar>

		<aui:form action="<%= portletConfigurationPermissionsDisplayContext.getUpdateRolePermissionsURL() %>" cssClass="container-fluid-1280" method="post" name="fm">
			<aui:input name="resourceId" type="hidden" value="<%= resource.getResourceId() %>" />

			<liferay-ui:search-container
				searchContainer="<%= portletConfigurationPermissionsDisplayContext.getRoleSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.model.Role"
					escapedModel="<%= true %>"
					keyProperty="roleId"
					modelVar="role"
				>

					<%
					String definePermissionsHREF = null;

					String name = role.getName();

					if (!name.equals(RoleConstants.ADMINISTRATOR) && !name.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) && !name.equals(RoleConstants.ORGANIZATION_OWNER) && !name.equals(RoleConstants.OWNER) && !name.equals(RoleConstants.SITE_ADMINISTRATOR) && !name.equals(RoleConstants.SITE_OWNER) && !role.isTeam() && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.DEFINE_PERMISSIONS)) {
						PortletURL definePermissionsURL = portletConfigurationPermissionsDisplayContext.getDefinePermissionsURL();

						definePermissionsURL.setParameter("roleId", String.valueOf(role.getRoleId()));

						definePermissionsHREF = definePermissionsURL.toString();
					}
					%>

					<liferay-ui:search-container-column-text
						href="<%= definePermissionsHREF %>"
						name="role"
					>
						<%= role.getTitle(locale) %>

						<c:if test="<%= layout.isPrivateLayout() && name.equals(RoleConstants.GUEST) %>">
							<liferay-ui:icon-help message="under-the-current-configuration-all-users-automatically-inherit-permissions-from-the-guest-role" />
						</c:if>
					</liferay-ui:search-container-column-text>

					<%

					// Actions

					List<String> currentIndividualActions = new ArrayList<String>();
					List<String> currentGroupActions = new ArrayList<String>();
					List<String> currentGroupTemplateActions = new ArrayList<String>();
					List<String> currentCompanyActions = new ArrayList<String>();

					ResourcePermissionUtil.populateResourcePermissionActionIds(portletConfigurationPermissionsDisplayContext.getGroupId(), role, resource, portletConfigurationPermissionsDisplayContext.getActions(), currentIndividualActions, currentGroupActions, currentGroupTemplateActions, currentCompanyActions);

					for (String action : portletConfigurationPermissionsDisplayContext.getActions()) {
						if (action.equals(ActionKeys.ACCESS_IN_CONTROL_PANEL)) {
							continue;
						}

						boolean checked = false;

						if (currentIndividualActions.contains(action) || currentGroupActions.contains(action) || currentGroupTemplateActions.contains(action) || currentCompanyActions.contains(action)) {
							checked = true;
						}

						String preselectedMsg = StringPool.BLANK;

						if (currentGroupActions.contains(action) || currentGroupTemplateActions.contains(action)) {
							preselectedMsg = "x-is-allowed-to-do-action-x-in-all-items-of-type-x-in-x";
						}
						else if (currentCompanyActions.contains(action)) {
							preselectedMsg = "x-is-allowed-to-do-action-x-in-all-items-of-type-x-in-this-portal-instance";
						}

						List<String> guestUnsupportedActions = portletConfigurationPermissionsDisplayContext.getGuestUnsupportedActions();

						boolean disabled = false;

						if (name.equals(RoleConstants.GUEST) && guestUnsupportedActions.contains(action)) {
							disabled = true;
						}

						String dataMessage = StringPool.BLANK;

						if (Validator.isNotNull(preselectedMsg)) {
							String type = portletConfigurationPermissionsDisplayContext.getSelResourceDescription();

							if (Validator.isNull(type)) {
								type = ResourceActionsUtil.getModelResource(locale, resource.getName());
							}

							dataMessage = HtmlUtil.escapeAttribute(LanguageUtil.format(request, preselectedMsg, new Object[] {role.getTitle(locale), ResourceActionsUtil.getAction(request, action), type, HtmlUtil.escape(portletConfigurationPermissionsDisplayContext.getGroupDescriptiveName())}, false));
						}

						String actionSeparator = Validator.isNotNull(preselectedMsg) ? ActionUtil.PRESELECTED : ActionUtil.ACTION;
					%>

						<liferay-ui:search-container-column-text
							name="<%= ResourceActionsUtil.getAction(request, action) %>"
						>
							<c:if test="<%= disabled && checked %>">
								<input name="<%= renderResponse.getNamespace() + role.getRoleId() + actionSeparator + action %>" type="hidden" value="<%= true %>" />
							</c:if>

							<input <%= checked ? "checked" : StringPool.BLANK %> class="<%= Validator.isNotNull(preselectedMsg) ? "lfr-checkbox-preselected" : StringPool.BLANK %>" data-message="<%= dataMessage %>" <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= FriendlyURLNormalizerUtil.normalize(role.getName()) + actionSeparator + action %>" name="<%= renderResponse.getNamespace() + role.getRoleId() + actionSeparator + action %>" onclick="<%= Validator.isNotNull(preselectedMsg) ? "return false;" : StringPool.BLANK %>" type="checkbox" />
						</liferay-ui:search-container-column-text>

					<%
					}
					%>

				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</div>

	<aui:button-row>
		<aui:button name="saveButton" type="submit" />

		<aui:button type="cancel" />
	</aui:button-row>
</div>

<aui:script sandbox="<%= true %>">
	var form = $(document.<portlet:namespace />fm);

	$('#<portlet:namespace />fm').on(
		'mouseover',
		'.lfr-checkbox-preselected',
		function(event) {
			var currentTarget = $(event.currentTarget);

			Liferay.Portal.ToolTip.show(currentTarget, currentTarget.data('message'));
		}
	);

	$('#<portlet:namespace />saveButton').on(
		'click',
		function(event) {
			event.preventDefault();

			if (<%= portletConfigurationPermissionsDisplayContext.getRoleSearchContainer().getTotal() != 0 %>) {
				submitForm(form);
			}
		}
	);
</aui:script>