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
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL">
	<portlet:param name="tabs1" value="<%= tabs1 %>" />
</liferay-portlet:renderURL>

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfigurations();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<div class="portlet-configuration-body-content">
		<div class="container-fluid-1280">
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset collapsible="<%= true %>" id="displaySettingsPanel" label="display-settings">
					<aui:select label="maximum-items-to-display" name="preferences--pageDelta--">

						<%
						for (int pageDeltaValue : PropsValues.ANNOUNCEMENTS_ENTRY_PAGE_DELTA_VALUES) {
						%>

							<aui:option label="<%= pageDeltaValue %>" selected="<%= announcementsDisplayContext.getPageDelta() == pageDeltaValue %>" />

						<%
						}
						%>

					</aui:select>
				</aui:fieldset>

				<aui:fieldset collapsible="<%= true %>" id="announcementsDisplayedPanel" label="announcements-displayed">
					<aui:input cssClass="customize-announcements-displayed" id="customizeAnnouncementsDisplayed" name="preferences--customizeAnnouncementsDisplayed--" title="customize-announcements-displayed" type="checkbox" value="<%= announcementsDisplayContext.isCustomizeAnnouncementsDisplayed() %>" />

					<div class="<%= announcementsDisplayContext.isCustomizeAnnouncementsDisplayed() ? "" : "hide" %>" id="<portlet:namespace />announcementsDisplayed">
						<div class="alert alert-info">
							<liferay-ui:message key="general-annnouncements-will-always-be-shown-select-any-other-distribution-scopes-you-would-like-to-display" />
						</div>

						<liferay-ui:tabs
							names="<%= tabs1Names %>"
							param="tabs1"
							refresh="<%= false %>"
							type="tabs nav-tabs-default"
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
											leftTitle="displaying"
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
											leftTitle="displaying"
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
											leftTitle="displaying"
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
											leftTitle="displaying"
											rightBoxName="availableScopeRoleIds"
											rightList="<%= rightList %>"
											rightTitle="available"
										/>
									</div>
								</liferay-ui:section>
							</c:if>
						</liferay-ui:tabs>
					</div>
				</aui:fieldset>
			</aui:fieldset-group>
		</div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base">
	var form = A.one('#<portlet:namespace />fm');

	var modified = function(panel) {
		var modifiedNotice = panel.one('.panel-heading .panel-title a .modified-notice');

		if (modifiedNotice == null) {
			var displayTitle = panel.one('.panel-heading .panel-title a');

			displayTitle.append('<span class="modified-notice"> (<liferay-ui:message key="modified" />) </span>');
		}
	}

	var customizeAnnouncementsDisplayedCheckbox = form.one('#<portlet:namespace />customizeAnnouncementsDisplayed');

	customizeAnnouncementsDisplayedCheckbox.on(
		'change',
		function() {
			modified('#<portlet:namespace />announcementsDisplayedPanel');

			var announcementsDisplayed = form.one('#<portlet:namespace />announcementsDisplayed');

			if (announcementsDisplayed) {
				announcementsDisplayed.toggleClass('hide');
			}
		}
	);

	var selected = form.all('.left-selector');

	var selectedHTML = '';

	for (var i = selected._nodes.length - 1; i >= 0; --i) {
		selectedHTML = selectedHTML.concat(selected._nodes[i].innerHTML);
	}

	Liferay.on(
		'inputmoveboxes:moveItem',
		function(event) {
			var currSelectedHTML = '';

			for (var i = selected._nodes.length - 1; i >= 0; --i) {
				currSelectedHTML = currSelectedHTML.concat(selected._nodes[i].innerHTML);
			}

			if (selectedHTML != currSelectedHTML) {
				modified(A.one('#<portlet:namespace />announcementsDisplayedPanel'));
			}
		}
	);

	var pageDeltaInput = form.one('select[name=<portlet:namespace />preferences--pageDelta--]');

	pageDeltaInput.on(
		'change',
		function(event) {
			modified(A.one('#<portlet:namespace />displaySettingsPanel'));
		}
	);
</aui:script>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />saveConfigurations',
		function() {
			if (document.<portlet:namespace />fm.<portlet:namespace />selectedScopeGroupIds) {
				document.<portlet:namespace />fm.<portlet:namespace />selectedScopeGroupIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentScopeGroupIds);
			}

			if (document.<portlet:namespace />fm.<portlet:namespace />selectedScopeOrganizationIds) {
				document.<portlet:namespace />fm.<portlet:namespace />selectedScopeOrganizationIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentScopeOrganizationIds);
			}

			if (document.<portlet:namespace />fm.<portlet:namespace />selectedScopeRoleIds) {
				document.<portlet:namespace />fm.<portlet:namespace />selectedScopeRoleIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentScopeRoleIds);
			}

			if (document.<portlet:namespace />fm.<portlet:namespace />selectedScopeUserGroupIds) {
				document.<portlet:namespace />fm.<portlet:namespace />selectedScopeUserGroupIds.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentScopeUserGroupIds);
			}

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);
</aui:script>