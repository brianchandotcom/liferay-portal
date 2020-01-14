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

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
	onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
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
				<aui:input name="preferences--rootFolderId--" type="hidden" value="<%= rootFolderId %>" />
				<aui:input name="preferences--folderColumns--" type="hidden" />
				<aui:input name="preferences--entryColumns--" type="hidden" />

				<liferay-frontend:fieldset-group>
					<liferay-frontend:fieldset
						collapsible="<%= true %>"
						id="bookmarksFoldersListingPanel"
						label="folders-listing"
					>
						<div class="form-group">
							<aui:input label="root-folder" name="rootFolderName" type="resource" value="<%= rootFolderName %>" />

							<aui:button name="selectFolderButton" value="select" />

							<%
							String taglibRemoveFolder = "Liferay.Util.removeEntitySelection('rootFolderId', 'rootFolderName', this, '" + renderResponse.getNamespace() + "');";
							%>

							<aui:button disabled="<%= rootFolderId <= 0 %>" name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
						</div>

						<aui:input label="show-search" name="preferences--showFoldersSearch--" type="checkbox" value="<%= bookmarksGroupServiceOverriddenConfiguration.showFoldersSearch() %>" />

						<aui:input name="preferences--showSubfolders--" type="checkbox" value="<%= bookmarksGroupServiceOverriddenConfiguration.showSubfolders() %>" />

						<aui:field-wrapper label="show-columns">

							<%
							Set<String> availableFolderColumns = SetUtil.fromArray(StringUtil.split(allFolderColumns));

							// Left list

							List leftList = new ArrayList();

							for (String folderColumn : folderColumns) {
								leftList.add(new KeyValuePair(folderColumn, LanguageUtil.get(request, folderColumn)));
							}

							// Right list

							List rightList = new ArrayList();

							Arrays.sort(folderColumns);

							for (String folderColumn : availableFolderColumns) {
								if (Arrays.binarySearch(folderColumns, folderColumn) < 0) {
									rightList.add(new KeyValuePair(folderColumn, LanguageUtil.get(request, folderColumn)));
								}
							}

							rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
							%>

							<liferay-ui:input-move-boxes
								leftBoxName="currentFolderColumns"
								leftList="<%= leftList %>"
								leftReorder="<%= Boolean.TRUE.toString() %>"
								leftTitle="current"
								rightBoxName="availableFolderColumns"
								rightList="<%= rightList %>"
								rightTitle="available"
							/>
						</aui:field-wrapper>
					</liferay-frontend:fieldset>

					<liferay-frontend:fieldset
						collapsible="<%= true %>"
						id="bookmarksListingPanel"
						label="bookmarks-listing"
					>
						<aui:input label="show-related-assets" name="preferences--enableRelatedAssets--" type="checkbox" value="<%= bookmarksGroupServiceOverriddenConfiguration.enableRelatedAssets() %>" />

						<aui:field-wrapper label="show-columns">

							<%
							Set<String> availableEntryColumns = SetUtil.fromArray(StringUtil.split(allEntryColumns));

							// Left list

							List leftList = new ArrayList();

							for (int i = 0; i < entryColumns.length; i++) {
								String entryColumn = entryColumns[i];

								leftList.add(new KeyValuePair(entryColumn, LanguageUtil.get(request, entryColumn)));
							}

							// Right list

							List rightList = new ArrayList();

							Arrays.sort(entryColumns);

							for (String entryColumn : availableEntryColumns) {
								if (Arrays.binarySearch(entryColumns, entryColumn) < 0) {
									rightList.add(new KeyValuePair(entryColumn, LanguageUtil.get(request, entryColumn)));
								}
							}

							rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
							%>

							<liferay-ui:input-move-boxes
								leftBoxName="currentEntryColumns"
								leftList="<%= leftList %>"
								leftReorder="<%= Boolean.TRUE.toString() %>"
								leftTitle="current"
								rightBoxName="availableEntryColumns"
								rightList="<%= rightList %>"
								rightTitle="available"
							/>
						</aui:field-wrapper>
					</liferay-frontend:fieldset>
				</liferay-frontend:fieldset-group>

				<aui:script>
					var <portlet:namespace />selectFolderButton = document.getElementById(
						'<portlet:namespace />selectFolderButton'
					);

					if (<portlet:namespace />selectFolderButton) {
						<portlet:namespace />selectFolderButton.addEventListener('click', function(
							event
						) {
							Liferay.Util.selectEntity(
								{
									dialog: {
										constrain: true,
										destroyOnHide: true,
										modal: true,
										width: 830
									},
									id:
										'<%= HtmlUtil.escapeJS(PortalUtil.getPortletNamespace(portletResource)) %>selectFolder',
									title:
										'<liferay-ui:message arguments="folder" key="select-x" />',
									uri:
										'<liferay-portlet:renderURL portletName="<%= portletResource %>" windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcRenderCommandName" value="/bookmarks/select_folder" /></liferay-portlet:renderURL>'
								},
								function(event) {
									var folderData = {
										idString: 'rootFolderId',
										idValue: event.entityid,
										nameString: 'rootFolderName',
										nameValue: event.entityname
									};

									Liferay.Util.selectFolder(folderData, '<portlet:namespace />');
								}
							);
						});
					}
				</aui:script>
			</liferay-ui:section>

			<liferay-ui:section>
				<liferay-frontend:fieldset-group>
					<liferay-frontend:fieldset>
						<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= bookmarksGroupServiceOverriddenConfiguration.emailFromName() %>" />

						<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= bookmarksGroupServiceOverriddenConfiguration.emailFromAddress() %>" />
					</liferay-frontend:fieldset>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>

			<%
			Map<String, String> emailDefinitionTerms = BookmarksUtil.getEmailDefinitionTerms(renderRequest, bookmarksGroupServiceOverriddenConfiguration.emailFromAddress(), bookmarksGroupServiceOverriddenConfiguration.emailFromName());
			%>

			<liferay-ui:section>
				<liferay-frontend:fieldset-group>
					<liferay-frontend:email-notification-settings
						emailBodyLocalizedValuesMap="<%= bookmarksGroupServiceOverriddenConfiguration.emailEntryAddedBody() %>"
						emailDefinitionTerms="<%= emailDefinitionTerms %>"
						emailEnabled="<%= bookmarksGroupServiceOverriddenConfiguration.emailEntryAddedEnabled() %>"
						emailParam="emailEntryAdded"
						emailSubjectLocalizedValuesMap="<%= bookmarksGroupServiceOverriddenConfiguration.emailEntryAddedSubject() %>"
					/>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>

			<liferay-ui:section>
				<liferay-frontend:fieldset-group>
					<liferay-frontend:email-notification-settings
						emailBodyLocalizedValuesMap="<%= bookmarksGroupServiceOverriddenConfiguration.emailEntryUpdatedBody() %>"
						emailDefinitionTerms="<%= emailDefinitionTerms %>"
						emailEnabled="<%= bookmarksGroupServiceOverriddenConfiguration.emailEntryUpdatedEnabled() %>"
						emailParam="emailEntryUpdated"
						emailSubjectLocalizedValuesMap="<%= bookmarksGroupServiceOverriddenConfiguration.emailEntryUpdatedSubject() %>"
					/>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>
		</liferay-ui:tabs>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script>
	function <portlet:namespace />saveConfiguration() {
		var Util = Liferay.Util;

		var form = document.getElementById('<portlet:namespace />fm');

		if (form) {
			var currentFolderColumns = form.querySelector(
				'#<portlet:namespace />currentFolderColumns'
			);
			var folderColumns = form.querySelector(
				'#<portlet:namespace />folderColumns'
			);

			if (currentFolderColumns && folderColumns) {
				folderColumns.value = Util.listSelect(currentFolderColumns);
			}

			var currentEntryColumns = form.querySelector(
				'#<portlet:namespace />currentEntryColumns'
			);
			var entryColumns = form.querySelector(
				'#<portlet:namespace />entryColumns'
			);

			if (currentEntryColumns && entryColumns) {
				entryColumns.value = Util.listSelect(currentEntryColumns);
			}

			submitForm(form);
		}
	}
</aui:script>