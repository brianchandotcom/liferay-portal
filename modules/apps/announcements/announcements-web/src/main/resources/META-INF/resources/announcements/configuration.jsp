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

<%@ include file="/announcements/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "sites");

List<Group> groups = GroupLocalServiceUtil.getUserGroups(user.getUserId(), true);
List<Organization> organizations = OrganizationLocalServiceUtil.getUserOrganizations(user.getUserId());
List<Role> roles = RoleLocalServiceUtil.getRoles(PortalUtil.getCompanyId(renderRequest));
List<UserGroup> userGroups = UserGroupLocalServiceUtil.getUserGroups(themeDisplay.getCompanyId());

String tabs1Names = "sites";

if (!organizations.isEmpty()) {
	tabs1Names = tabs1Names.concat(",organizations");
}

if (!userGroups.isEmpty()) {
	tabs1Names = tabs1Names.concat(",user-groups");
}

if (!roles.isEmpty()) {
	tabs1Names = tabs1Names.concat(",roles");
}

announcementsPortletInstanceConfiguration = ParameterMapUtil.setParameterMap(AnnouncementsPortletInstanceConfiguration.class, announcementsPortletInstanceConfiguration, request.getParameterMap(), "preferences--", "--");
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL">
	<portlet:param name="tabs1" value="<%= tabs1 %>" />
</liferay-portlet:renderURL>

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
	onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfigurations();" %>'
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset
				collapsible="<%= true %>"
				id="displaySettingsPanel"
				label="display-settings"
			>
				<aui:select label="maximum-items-to-display" name="preferences--pageDelta--" value="<%= announcementsPortletInstanceConfiguration.pageDelta() %>">

					<%
					for (int pageDeltaValue : PropsValues.ANNOUNCEMENTS_ENTRY_PAGE_DELTA_VALUES) {
					%>

						<aui:option label="<%= pageDeltaValue %>" selected="<%= announcementsDisplayContext.getPageDelta() == pageDeltaValue %>" />

					<%
					}
					%>

				</aui:select>
			</liferay-frontend:fieldset>

			<liferay-frontend:fieldset
				collapsible="<%= true %>"
				id="announcementsDisplayedPanel"
				label="announcements-displayed"
			>
				<aui:input cssClass="customize-announcements-displayed" id="customizeAnnouncementsDisplayed" name="preferences--customizeAnnouncementsDisplayed--" title="customize-announcements-displayed" type="checkbox" value="<%= announcementsDisplayContext.isCustomizeAnnouncementsDisplayed() %>" />

				<div class="<%= announcementsDisplayContext.isCustomizeAnnouncementsDisplayed() ? "" : "hide" %>" id="<portlet:namespace />announcementsDisplayed">
					<div class="alert alert-info">
						<liferay-ui:message key="general-annnouncements-will-always-be-shown-select-any-other-distribution-scopes-you-would-like-to-display" />
					</div>

					<liferay-ui:tabs
						names="<%= tabs1Names %>"
						param="tabs1"
						refresh="<%= false %>"
					>
						<c:if test="<%= !groups.isEmpty() %>">
							<liferay-ui:section>

								<%
								List<KeyValuePair> leftList = new ArrayList<KeyValuePair>();

								for (Group curGroup : groups) {
									if (announcementsDisplayContext.isScopeGroupSelected(curGroup)) {
										leftList.add(new KeyValuePair(String.valueOf(curGroup.getGroupId()), curGroup.getDescriptiveName(locale)));
									}
								}

								List<KeyValuePair> rightList = new ArrayList<KeyValuePair>();

								for (Group curGroup : groups) {
									KeyValuePair tempKeyValuePair = new KeyValuePair(String.valueOf(curGroup.getGroupId()), curGroup.getDescriptiveName(locale));

									if (!leftList.contains(tempKeyValuePair)) {
										rightList.add(tempKeyValuePair);
									}
								}
								%>

								<aui:input name="preferences--selectedScopeGroupIds--" type="hidden" />

								<div id="<portlet:namespace />scopeGroupIdsBoxes">
									<liferay-ui:input-move-boxes
										leftBoxName="currentScopeGroupIds"
										leftList="<%= leftList %>"
										leftReorder="<%= Boolean.TRUE.toString() %>"
										leftTitle="current"
										rightBoxName="availableScopeGroupIds"
										rightList="<%= rightList %>"
										rightTitle="available"
									/>
								</div>
							</liferay-ui:section>
						</c:if>

						<c:if test="<%= !organizations.isEmpty() %>">
							<liferay-ui:section>

								<%
								List<KeyValuePair> leftList = new ArrayList<KeyValuePair>();

								for (Organization organization : organizations) {
									if (announcementsDisplayContext.isScopeOrganizationSelected(organization)) {
										leftList.add(new KeyValuePair(String.valueOf(organization.getOrganizationId()), organization.getName()));
									}
								}

								List<KeyValuePair> rightList = new ArrayList<KeyValuePair>();

								for (Organization organization : organizations) {
									KeyValuePair tempKeyValuePair = new KeyValuePair(String.valueOf(organization.getOrganizationId()), organization.getName());

									if (!leftList.contains(tempKeyValuePair)) {
										rightList.add(tempKeyValuePair);
									}
								}
								%>

								<aui:input name="preferences--selectedScopeOrganizationIds--" type="hidden" />

								<div id="<portlet:namespace />scopeOrganizationIdsBoxes">
									<liferay-ui:input-move-boxes
										leftBoxName="currentScopeOrganizationIds"
										leftList="<%= leftList %>"
										leftReorder="<%= Boolean.TRUE.toString() %>"
										leftTitle="current"
										rightBoxName="availableScopeOrganizationIds"
										rightList="<%= rightList %>"
										rightTitle="available"
									/>
								</div>
							</liferay-ui:section>
						</c:if>

						<c:if test="<%= !userGroups.isEmpty() %>">
							<liferay-ui:section>

								<%
								List<KeyValuePair> leftList = new ArrayList<KeyValuePair>();

								for (UserGroup userGroup : userGroups) {
									if (announcementsDisplayContext.isScopeUserGroupSelected(userGroup)) {
										leftList.add(new KeyValuePair(String.valueOf(userGroup.getUserGroupId()), userGroup.getName()));
									}
								}

								List<KeyValuePair> rightList = new ArrayList<KeyValuePair>();

								for (UserGroup userGroup : userGroups) {
									KeyValuePair tempKeyValuePair = new KeyValuePair(String.valueOf(userGroup.getUserGroupId()), userGroup.getName());

									if (!leftList.contains(tempKeyValuePair)) {
										rightList.add(tempKeyValuePair);
									}
								}
								%>

								<aui:input name="preferences--selectedScopeUserGroupIds--" type="hidden" />

								<div id="<portlet:namespace />scopeUserGroupIdsBoxes">
									<liferay-ui:input-move-boxes
										leftBoxName="currentScopeUserGroupIds"
										leftList="<%= leftList %>"
										leftReorder="<%= Boolean.TRUE.toString() %>"
										leftTitle="current"
										rightBoxName="availableScopeUserGroupIds"
										rightList="<%= rightList %>"
										rightTitle="available"
									/>
								</div>
							</liferay-ui:section>
						</c:if>

						<c:if test="<%= !roles.isEmpty() %>">
							<liferay-ui:section>

								<%
								List<KeyValuePair> leftList = new ArrayList<KeyValuePair>();

								for (Role role : roles) {
									if (announcementsDisplayContext.isScopeRoleSelected(role)) {
										leftList.add(new KeyValuePair(String.valueOf(role.getRoleId()), role.getTitle(locale)));
									}
								}

								List<KeyValuePair> rightList = new ArrayList<KeyValuePair>();

								for (Role role : roles) {
									KeyValuePair tempKeyValuePair = new KeyValuePair(String.valueOf(role.getRoleId()), role.getTitle(locale));

									if (!leftList.contains(tempKeyValuePair)) {
										rightList.add(tempKeyValuePair);
									}
								}
								%>

								<aui:input name="preferences--selectedScopeRoleIds--" type="hidden" />

								<div id="<portlet:namespace />scopeRoleIdsBoxes">
									<liferay-ui:input-move-boxes
										leftBoxName="currentScopeRoleIds"
										leftList="<%= leftList %>"
										leftReorder="<%= Boolean.TRUE.toString() %>"
										leftTitle="current"
										rightBoxName="availableScopeRoleIds"
										rightList="<%= rightList %>"
										rightTitle="available"
									/>
								</div>
							</liferay-ui:section>
						</c:if>
					</liferay-ui:tabs>
				</div>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script require="metal-dom/src/dom">
	let dom = metalDomSrcDom.default;

	var form = document.getElementById('<portlet:namespace />fm');

	if (form) {
		var <portlet:namespace />modified = function(panel) {
			var modifiedNotice = panel.querySelector(
				'.panel-heading .sheet-subtitle .modified-notice'
			);

			if (!modifiedNotice) {
				var displayTitle = panel.querySelector(
					'.panel-heading .sheet-subtitle'
				);

				dom.append(
					displayTitle,
					'<span class="modified-notice"> (<liferay-ui:message key="modified" />) </span>'
				);
			}
		};

		var customizeAnnouncementsDisplayedCheckbox = form.querySelector(
			'#<portlet:namespace />customizeAnnouncementsDisplayed'
		);

		if (customizeAnnouncementsDisplayedCheckbox) {
			customizeAnnouncementsDisplayedCheckbox.addEventListener(
				'change',
				function() {
					<portlet:namespace />modified(
						document.getElementById(
							'<portlet:namespace />announcementsDisplayedPanel'
						)
					);

					var announcementsDisplayed = form.querySelector(
						'#<portlet:namespace />announcementsDisplayed'
					);

					if (announcementsDisplayed) {
						dom.toggleClasses(announcementsDisplayed, 'hide');
					}
				}
			);
		}
	}
</aui:script>

<aui:script>
	var <portlet:namespace />form = document.getElementById(
		'<portlet:namespace />fm'
	);

	if (<portlet:namespace />form) {
		var selected = <portlet:namespace />form.querySelectorAll('.left-selector');

		var selectedHTML = '';

		for (var i = selected.length - 1; i >= 0; --i) {
			selectedHTML = selectedHTML.concat(selected[i].innerHTML);
		}

		Liferay.on('inputmoveboxes:moveItem', function(event) {
			var currSelectedHTML = '';

			for (var i = selected.length - 1; i >= 0; --i) {
				currSelectedHTML = currSelectedHTML.concat(selected[i].innerHTML);
			}

			if (selectedHTML != currSelectedHTML) {
				var announcementsDisplayedPanel = document.getElementById(
					'<portlet:namespace />announcementsDisplayedPanel'
				);

				if (announcementsDisplayedPanel) {
					modified(announcementsDisplayedPanel);
				}
			}
		});

		var pageDeltaInput = <portlet:namespace />form.querySelector(
			'select[name=<portlet:namespace />preferences--pageDelta--]'
		);

		if (pageDeltaInput) {
			pageDeltaInput.addEventListener('change', function(event) {
				var displaySettingsPanel = document.getElementById(
					'<portlet:namespace />displaySettingsPanel'
				);

				if (displaySettingsPanel) {
					modified(displaySettingsPanel);
				}
			});
		}

		function <portlet:namespace />saveConfigurations() {
			var currentScopeGroupIds = <portlet:namespace />form.querySelector(
				'#<portlet:namespace />currentScopeGroupIds'
			);
			var selectedScopeGroupIds = <portlet:namespace />form.querySelector(
				'#<portlet:namespace />selectedScopeGroupIds'
			);

			if (currentScopeGroupIds && selectedScopeGroupIds) {
				selectedScopeGroupIds.setAttribute(
					'value',
					Liferay.Util.listSelect(currentScopeGroupIds)
				);
			}

			var currentScopeOrganizationIds = <portlet:namespace />form.querySelector(
				'#<portlet:namespace />currentScopeOrganizationIds'
			);
			var selectedScopeOrganizationIds = <portlet:namespace />form.querySelector(
				'#<portlet:namespace />selectedScopeOrganizationIds'
			);

			if (currentScopeOrganizationIds && selectedScopeOrganizationIds) {
				selectedScopeOrganizationIds.setAttribute(
					'value',
					Liferay.Util.listSelect(currentScopeOrganizationIds)
				);
			}

			var currentScopeRoleIds = <portlet:namespace />form.querySelector(
				'#<portlet:namespace />currentScopeRoleIds'
			);
			var selectedScopeRoleIds = <portlet:namespace />form.querySelector(
				'#<portlet:namespace />selectedScopeRoleIds'
			);

			if (currentScopeRoleIds && selectedScopeRoleIds) {
				selectedScopeRoleIds.setAttribute(
					'value',
					Liferay.Util.listSelect(currentScopeRoleIds)
				);
			}

			var currentScopeUserGroupIds = <portlet:namespace />form.querySelector(
				'#<portlet:namespace />selectedScopeUserGroupIds'
			);
			var selectedScopeUserGroupIds = <portlet:namespace />form.querySelector(
				'#<portlet:namespace />currentScopeUserGroupIds'
			);

			if (currentScopeUserGroupIds && selectedScopeUserGroupIds) {
				selectedScopeUserGroupIds.setAttribute(
					'value',
					Liferay.Util.listSelect(currentScopeUserGroupIds)
				);
			}

			submitForm(<portlet:namespace />form);
		}
	}
</aui:script>