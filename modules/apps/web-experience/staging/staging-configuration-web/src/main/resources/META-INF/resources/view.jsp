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
GroupDisplayContextHelper groupDisplayContextHelper = new GroupDisplayContextHelper(request);

liveGroup = groupDisplayContextHelper.getLiveGroup();
liveGroupId = groupDisplayContextHelper.getLiveGroupId();
UnicodeProperties liveGroupTypeSettings = liveGroup.getTypeSettingsProperties();

LayoutSet privateLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(liveGroup.getGroupId(), true);
LayoutSet publicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(liveGroup.getGroupId(), false);

boolean liveGroupRemoteStaging = liveGroup.hasRemoteStagingGroup() && PropsValues.STAGING_LIVE_GROUP_REMOTE_STAGING_ENABLED;
boolean stagedLocally = liveGroup.isStaged() && !liveGroup.isStagedRemotely();
boolean stagedRemotely = liveGroup.isStaged() && !stagedLocally;

if (stagedLocally) {
	stagingGroup = liveGroup.getStagingGroup();
	stagingGroupId = stagingGroup.getGroupId();
}

BackgroundTask lastCompletedInitialPublicationBackgroundTask = BackgroundTaskManagerUtil.fetchFirstBackgroundTask(liveGroupId, BackgroundTaskExecutorNames.LAYOUT_STAGING_BACKGROUND_TASK_EXECUTOR, true, new BackgroundTaskCreateDateComparator(false));
%>

<c:choose>
	<c:when test="<%= GroupPermissionUtil.contains(permissionChecker, liveGroup, ActionKeys.MANAGE_STAGING) && GroupPermissionUtil.contains(permissionChecker, liveGroup, ActionKeys.VIEW_STAGING) %>">
		<%@ include file="/staging_configuration_exceptions.jspf" %>

		<div class="container-fluid-960">
			<liferay-ui:success key="stagingDisabled" message="staging-is-successfully-disabled" />

			<portlet:actionURL name="editStagingConfiguration" var="editStagingConfigurationURL">
				<portlet:param name="mvcPath" value="/view.jsp" />
			</portlet:actionURL>

			<portlet:renderURL var="redirectURL">
				<portlet:param name="mvcRenderCommandName" value="staging" />
			</portlet:renderURL>

			<aui:form action="<%= editStagingConfigurationURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveGroup();" %>'>
				<aui:input name="redirect" type="hidden" value="<%= redirectURL %>" />
				<aui:input name="groupId" type="hidden" value="<%= liveGroupId %>" />
				<aui:input name="liveGroupId" type="hidden" value="<%= liveGroupId %>" />
				<aui:input name="stagingGroupId" type="hidden" value="<%= stagingGroupId %>" />
				<aui:input name="forceDisable" type="hidden" value="<%= false %>" />

				<c:if test="<%= !privateLayoutSet.isLayoutSetPrototypeLinkActive() && !publicLayoutSet.isLayoutSetPrototypeLinkActive() %>">
					<aui:fieldset-group markupView="lexicon">
						<%@ include file="/staging_configuration_select_staging_type.jspf" %>

						<%@ include file="/staging_configuration_remote_options.jspf" %>

						<%@ include file="/staging_configuration_staged_portlets.jspf" %>

						<div class="staging-configuration-submit-button-holder">
							<button class="btn btn-primary"><span class="lfr-btn-label"><%= LanguageUtil.get(request, "save") %></span></button>
						</div>
					</aui:fieldset-group>

					<aui:script sandbox="<%= true %>">
						var remoteStagingOptions = $('#<portlet:namespace />remoteStagingOptions');
						var stagedPortlets = $('#<portlet:namespace />stagedPortlets');
						var trashWarning = $('#<portlet:namespace />trashWarning');

						var stagingTypes = $('#<portlet:namespace />stagingTypes');

						stagingTypes.on(
							'click',
							'input',
							function(event) {
								var value = $(event.currentTarget).val();

								stagedPortlets.toggleClass('hide', value == '<%= StagingConstants.TYPE_NOT_STAGED %>');

								remoteStagingOptions.toggleClass('hide', value != '<%= StagingConstants.TYPE_REMOTE_STAGING %>');

								trashWarning.toggleClass('hide', value != '<%= StagingConstants.TYPE_LOCAL_STAGING %>');
							}
						);
					</aui:script>
				</c:if>
			</aui:form>
		</div>
	</c:when>
	<c:otherwise>
		<liferay-staging:alert type="INFO">
			<liferay-ui:message key="you-do-not-have-permission-to-manage-settings-related-to-staging" />
		</liferay-staging:alert>
	</c:otherwise>
</c:choose>

<aui:script>
	function <portlet:namespace />saveGroup(forceDisable) {
		var $ = AUI.$;

		var form = $(document.<portlet:namespace />fm);

		var ok = true;

		<c:if test="<%= liveGroup != null %>">
			var stagingTypeEl = $('input[name=<portlet:namespace />stagingType]:checked');

			var oldValue;

			<c:choose>
				<c:when test="<%= liveGroup.isStaged() && !liveGroup.isStagedRemotely() %>">
					oldValue = <%= StagingConstants.TYPE_LOCAL_STAGING %>;
				</c:when>
				<c:when test="<%= liveGroup.isStaged() && liveGroup.isStagedRemotely() %>">
					oldValue = <%= StagingConstants.TYPE_REMOTE_STAGING %>;
				</c:when>
				<c:otherwise>
					oldValue = <%= StagingConstants.TYPE_NOT_STAGED %>;
				</c:otherwise>
			</c:choose>

			var currentValue = stagingTypeEl.val();

			if (stagingTypeEl.length && (currentValue != oldValue)) {
				ok = false;

				if (currentValue == <%= StagingConstants.TYPE_NOT_STAGED %>) {
					ok = confirm('<%= UnicodeLanguageUtil.format(request, "are-you-sure-you-want-to-deactivate-staging-for-x", liveGroup.getDescriptiveName(locale), false) %>');
				}
				else if (currentValue == <%= StagingConstants.TYPE_LOCAL_STAGING %>) {
					ok = confirm('<%= UnicodeLanguageUtil.format(request, "are-you-sure-you-want-to-activate-local-staging-for-x", liveGroup.getDescriptiveName(locale), false) %>');
				}
				else if (currentValue == <%= StagingConstants.TYPE_REMOTE_STAGING %>) {
					ok = confirm('<%= UnicodeLanguageUtil.format(request, "are-you-sure-you-want-to-activate-remote-staging-for-x", liveGroup.getDescriptiveName(locale), false) %>');
				}
			}
		</c:if>

		if (ok) {
			if (forceDisable) {
				form.fm('forceDisable').val(true);
				form.fm('local').prop('checked', false);
				form.fm('none').prop('checked', true);
				form.fm('redirect').val('<portlet:renderURL><portlet:param name="mvcPath" value="/view.jsp" /><portlet:param name="historyKey" value='<%= renderResponse.getNamespace() + "staging" %>' /></portlet:renderURL>');
				form.fm('remote').prop('checked', false);
			}

			submitForm(form);
		}
	}
</aui:script>

<aui:script sandbox="<%= true %>">
	var stagingConfigurationControls = $('#stagingConfigurationControls');

	var allCheckboxes = stagingConfigurationControls.find('input[type=checkbox]');

	$('#<portlet:namespace />selectAllCheckbox').on(
		'change',
		function() {
			allCheckboxes.prop('checked', this.checked);
		}
	);
</aui:script>