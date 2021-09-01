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
RoleTypeContributorProvider roleTypeContributorProvider = (RoleTypeContributorProvider)request.getAttribute(RolesAdminWebKeys.ROLE_TYPE_CONTRIBUTOR_PROVIDER);

PortletConfigurationPermissionsDisplayContext portletConfigurationPermissionsDisplayContext = new PortletConfigurationPermissionsDisplayContext(request, renderRequest, roleTypeContributorProvider);

Resource resource = portletConfigurationPermissionsDisplayContext.getResource();
SearchContainer<Role> roleSearchContainer = portletConfigurationPermissionsDisplayContext.getRoleSearchContainer();

if (Validator.isNotNull(portletConfigurationPermissionsDisplayContext.getModelResource())) {
	PortalUtil.addPortletBreadcrumbEntry(request, HtmlUtil.unescape(portletConfigurationPermissionsDisplayContext.getSelResourceDescription()), null);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "permissions"), currentURL);
}
%>

<div class="edit-permissions portlet-configuration-edit-permissions">
	<div class="portlet-configuration-body-content">
		<clay:management-toolbar
			clearResultsURL="<%= portletConfigurationPermissionsDisplayContext.getClearResultsURL() %>"
			itemsTotal="<%= roleSearchContainer.getTotal() %>"
			searchActionURL="<%= portletConfigurationPermissionsDisplayContext.getSearchActionURL() %>"
			searchFormName="searchFm"
			selectable="<%= false %>"
		/>

		<aui:form action="<%= portletConfigurationPermissionsDisplayContext.getUpdateRolePermissionsURL() %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
			<aui:input name="resourceId" type="hidden" value="<%= resource.getResourceId() %>" />

			<liferay-ui:search-container
				searchContainer="<%= roleSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.model.Role"
					cssClass="table-title"
					escapedModel="<%= true %>"
					keyProperty="roleId"
					modelVar="role"
				>

					<%
					String name = role.getName();
					%>

					<liferay-ui:search-container-column-text
						name="role"
					>

						<%
						RoleTypeContributor roleTypeContributor = roleTypeContributorProvider.getRoleTypeContributor(role.getType());
						%>

						<span class="text-truncate-inline">
							<span class="inline-item-before">
								<liferay-ui:icon
									icon='<%= (roleTypeContributor != null) ? roleTypeContributor.getIcon() : "users" %>'
									label="<%= false %>"
									markupView="lexicon"
									message='<%= LanguageUtil.get(request, (roleTypeContributor != null) ? roleTypeContributor.getTitle(locale) : "team") %>'
								/>
							</span>
							<span class="lfr-portal-tooltip text-truncate" title="<%= role.getTitle(locale) %>">
								<%= role.getTitle(locale) %>
							</span>

							<c:if test="<%= layout.isPrivateLayout() && name.equals(RoleConstants.GUEST) %>">
								<span class="inline-item-after">
									<liferay-ui:icon-help message="under-the-current-configuration-all-users-automatically-inherit-permissions-from-the-guest-role" />
								</span>
							</c:if>
						</span>
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

							dataMessage = HtmlUtil.escapeAttribute(LanguageUtil.format(request, preselectedMsg, new Object[] {role.getTitle(locale), _getActionLabel(request, resource.getName(), action), type, HtmlUtil.escape(portletConfigurationPermissionsDisplayContext.getGroupDescriptiveName())}, false));
						}

						String actionSeparator = Validator.isNotNull(preselectedMsg) ? ActionUtil.PRESELECTED : ActionUtil.ACTION;
					%>

						<liferay-ui:search-container-column-text
							cssClass="table-column-text-center"
							name="<%= _getActionLabel(request, resource.getName(), action) %>"
						>
							<c:if test="<%= disabled && checked %>">
								<input name="<%= liferayPortletResponse.getNamespace() + role.getRoleId() + actionSeparator + action %>" type="hidden" value="<%= true %>" />
							</c:if>

							<input <%= checked ? "checked" : StringPool.BLANK %> class="<%= Validator.isNotNull(preselectedMsg) ? "lfr-checkbox-preselected lfr-portal-tooltip" : StringPool.BLANK %>" title="<%= dataMessage %>" <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= FriendlyURLNormalizerUtil.normalize(role.getName()) + actionSeparator + action %>" name="<%= liferayPortletResponse.getNamespace() + role.getRoleId() + actionSeparator + action %>" onclick="<%= Validator.isNotNull(preselectedMsg) ? "return false;" : StringPool.BLANK %>" type="checkbox" />
						</liferay-ui:search-container-column-text>

					<%
					}
					%>

				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					fixedHeader="<%= true %>"
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

<aui:script>
	var <portlet:namespace />saveButton = document.getElementById(
		'<portlet:namespace />saveButton'
	);

	if (<portlet:namespace />saveButton) {
		<portlet:namespace />saveButton.addEventListener('click', (event) => {
			event.preventDefault();

			if (
				<%= portletConfigurationPermissionsDisplayContext.getRoleSearchContainer().getTotal() != 0 %>
			) {
				var form = document.getElementById('<portlet:namespace />fm');

				if (form) {
					submitForm(form);
				}
			}
		});
	}
</aui:script>