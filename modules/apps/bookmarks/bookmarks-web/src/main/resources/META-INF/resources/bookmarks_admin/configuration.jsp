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

<%@ include file="/bookmarks/init.jsp" %>

<%
try {
	BookmarksFolder rootFolder = BookmarksFolderLocalServiceUtil.getFolder(rootFolderId);

	rootFolderName = rootFolder.getName();

	if (rootFolder.getGroupId() != scopeGroupId) {
		rootFolderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		rootFolderName = StringPool.BLANK;
	}
}
catch (NoSuchFolderException nsfe) {
	rootFolderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;
}
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
	<portlet:param name="serviceName" value="<%= BookmarksConstants.SERVICE_NAME %>" />
	<portlet:param name="settingsScope" value="group" />
</liferay-portlet:actionURL>

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-ui:tabs
		names="display-settings,email-from,entry-added-email,entry-updated-email"
		refresh="<%= false %>"
	>
		<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
		<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
		<liferay-ui:error key="emailEntryAddedBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailEntryAddedSubject" message="please-enter-a-valid-subject" />
		<liferay-ui:error key="emailEntryUpdatedBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailEntryUpdatedSubject" message="please-enter-a-valid-subject" />
		<liferay-ui:error key="rootFolderId" message="please-enter-a-valid-root-folder" />

		<liferay-ui:section>
			<%@ include file="/bookmarks_admin/display_settings.jspf" %>
		</liferay-ui:section>

		<liferay-ui:section>
			<aui:fieldset-group>
				<aui:fieldset>
					<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= bookmarksGroupServiceOverriddenConfiguration.emailFromName() %>" />

					<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= bookmarksGroupServiceOverriddenConfiguration.emailFromAddress() %>" />
				</aui:fieldset>
			</aui:fieldset-group>
		</liferay-ui:section>

		<%
		Map<String, String> emailDefinitionTerms = BookmarksUtil.getEmailDefinitionTerms(renderRequest, bookmarksGroupServiceOverriddenConfiguration.emailFromAddress(), bookmarksGroupServiceOverriddenConfiguration.emailFromName());
		%>

		<liferay-ui:section>
			<liferay-frontend:email-notification-settings
				emailBody="<%= bookmarksGroupServiceOverriddenConfiguration.emailEntryAddedBodyXml() %>"
				emailDefinitionTerms="<%= emailDefinitionTerms %>"
				emailEnabled="<%= bookmarksGroupServiceOverriddenConfiguration.emailEntryAddedEnabled() %>"
				emailParam="emailEntryAdded"
				emailSubject="<%= bookmarksGroupServiceOverriddenConfiguration.emailEntryAddedSubjectXml() %>"
			/>
		</liferay-ui:section>

		<liferay-ui:section>
			<liferay-frontend:email-notification-settings
				emailBody="<%= bookmarksGroupServiceOverriddenConfiguration.emailEntryUpdatedBodyXml() %>"
				emailDefinitionTerms="<%= emailDefinitionTerms %>"
				emailEnabled="<%= bookmarksGroupServiceOverriddenConfiguration.emailEntryUpdatedEnabled() %>"
				emailParam="emailEntryUpdated"
				emailSubject="<%= bookmarksGroupServiceOverriddenConfiguration.emailEntryUpdatedSubjectXml() %>"
			/>
		</liferay-ui:section>
	</liferay-ui:tabs>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveConfiguration() {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('folderColumns').val(Util.listSelect(form.fm('currentFolderColumns')));
		form.fm('entryColumns').val(Util.listSelect(form.fm('currentEntryColumns')));

		submitForm(form);
	}
</aui:script>