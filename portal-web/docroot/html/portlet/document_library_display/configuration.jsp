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

<%@ include file="/html/portlet/document_library_display/init.jsp" %>

<%
dlPortletInstanceSettings = DLPortletInstanceSettings.getInstance(layout, portletId, request.getParameterMap());

DLDisplayConfigurationDisplayContext dlDisplayConfigurationDisplayContext = new DLDisplayConfigurationDisplayContext(request, dlPortletInstanceSettings);
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL">
	<liferay-portlet:param name="settingsScope" value="portletInstance" />
</liferay-portlet:actionURL>

<liferay-portlet:renderURL portletConfiguration="true" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />
	<aui:input name="preferences--rootFolderId--" type="hidden" value="<%= rootFolderId %>" />
	<aui:input name="preferences--folderColumns--" type="hidden" />
	<aui:input name="preferences--fileEntryColumns--" type="hidden" />

	<liferay-ui:error key="rootFolderIdInvalid" message="please-enter-a-valid-root-folder" />

	<liferay-ui:panel-container extended="<%= true %>" id="documentLibrarySettingsPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryDisplay" persistState="<%= true %>" title="display-settings">
			<aui:input id="showActions" label="show-actions" name="preferences--showActions--" type="checkbox" value="<%= dlPortletInstanceSettings.isShowActions() %>" />

			<aui:input label="show-folder-menu" name="preferences--showFolderMenu--" type="checkbox" value="<%= dlPortletInstanceSettings.isShowFolderMenu() %>" />

			<aui:input label="show-navigation-links" name="preferences--showTabs--" type="checkbox" value="<%= dlPortletInstanceSettings.isShowTabs() %>" />

			<aui:input label="show-search" name="preferences--showFoldersSearch--" type="checkbox" value="<%= dlPortletInstanceSettings.isShowFoldersSearch() %>" />
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryFoldersListingPanel" persistState="<%= true %>" title="folders-listing">
			<aui:fieldset>
				<div class="control-group">
					<aui:input label="root-folder" name="rootFolderName" type="resource" value="<%= rootFolderName %>" />

					<aui:button name="openFolderSelectorButton" value="select" />

					<%
					String taglibRemoveFolder = "Liferay.Util.removeFolderSelection('rootFolderId', 'rootFolderName', '" + renderResponse.getNamespace() + "');";
					%>

					<aui:button disabled="<%= rootFolderId <= 0 %>" name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
				</div>

				<aui:input name="preferences--showSubfolders--" type="checkbox" value="<%= dlPortletInstanceSettings.isShowSubfolders() %>" />

				<aui:input name="preferences--foldersPerPage--" size="2" type="text" value="<%= dlPortletInstanceSettings.getFoldersPerPage() %>" />

				<aui:field-wrapper label="show-columns">
					<liferay-ui:input-move-boxes
						leftBoxName="currentFolderColumns"
						leftList="<%= dlDisplayConfigurationDisplayContext.getCurrentFolderColumns() %>"
						leftReorder="true"
						leftTitle="current"
						rightBoxName="availableFolderColumns"
						rightList="<%= dlDisplayConfigurationDisplayContext.getAvailableFolderColumns() %>"
						rightTitle="available"
					/>
				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryDocumentsListingPanel" persistState="<%= true %>" title="documents-listing">
			<aui:fieldset>
				<aui:input label="documents-per-page" name="preferences--fileEntriesPerPage--" size="2" type="text" value="<%= dlPortletInstanceSettings.getFileEntriesPerPage() %>" />

				<aui:field-wrapper label="show-columns">
					<liferay-ui:input-move-boxes
						leftBoxName="currentFileEntryColumns"
						leftList="<%= dlDisplayConfigurationDisplayContext.getCurrentFileEntryColumns() %>"
						leftReorder="true"
						leftTitle="current"
						rightBoxName="availableFileEntryColumns"
						rightList="<%= dlDisplayConfigurationDisplayContext.getAvailableFileEntryColumns() %>"
						rightTitle="available"
					/>
				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryDocumentsRatingsPanel" persistState="<%= true %>" title="ratings">
			<aui:input name="preferences--enableRatings--" type="checkbox" value="<%= dlPortletInstanceSettings.isEnableRatings() %>" />
			<aui:input name="preferences--enableCommentRatings--" type="checkbox" value="<%= dlPortletInstanceSettings.isEnableCommentRatings() %>" />
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />openFolderSelectorButton').on(
		'click',
		function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true,
						width: 600
					},
					id: '_<%= HtmlUtil.escapeJS(portletResource) %>_selectFolder',
					title: '<liferay-ui:message arguments="folder" key="select-x" />',

					<liferay-portlet:renderURL portletName="<%= portletResource %>" var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
						<portlet:param name="struts_action" value="/document_library_display/select_folder" />
					</liferay-portlet:renderURL>

					uri: '<%= selectFolderURL.toString() %>'
				},
				function(event) {
					var folderData = {
						idString: 'rootFolderId',
						idValue: event.folderid,
						nameString: 'rootFolderName',
						nameValue: event.foldername
					};

					Liferay.Util.selectFolder(folderData, '<portlet:namespace />');
				}
			);
		}
	);

	A.one('#<portlet:namespace />showActionsCheckbox').after(
		'change',
		function(event) {
			var currentFileEntryColumns = A.one('#<portlet:namespace />currentFileEntryColumns');
			var currentFolderColumns = A.one('#<portlet:namespace />currentFolderColumns');
			var showActionsInput = A.one('#<portlet:namespace />showActions');

			if (showActionsInput.val() === 'false') {
				var actionHTML = '<option value="action"><%= UnicodeLanguageUtil.get(pageContext, "action") %></option>';

				currentFileEntryColumns.append(actionHTML);
				currentFolderColumns.append(actionHTML);
			}
			else {
				var availableFileEntryColumns = A.one('#<portlet:namespace />availableFileEntryColumns');
				var availableFolderColumns = A.one('#<portlet:namespace />availableFolderColumns');

				A.Array.each(
					[currentFolderColumns, currentFileEntryColumns, availableFileEntryColumns, availableFolderColumns],
					function(item, index) {
						var actionsNode = item.one('option[value="action"]');

						if (actionsNode) {
							actionsNode.remove();
						}
					}
				);
			}
		}
	);

</aui:script>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />saveConfiguration',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace />folderColumns.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentFolderColumns);
			document.<portlet:namespace />fm.<portlet:namespace />fileEntryColumns.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentFileEntryColumns);

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);
</aui:script>

<c:if test="<%= SessionMessages.contains(renderRequest, portletDisplay.getId() + SessionMessages.KEY_SUFFIX_UPDATED_CONFIGURATION) %>">
	<aui:script position="inline" use="aui-base">
		var valueMap = {};

		var foldersPerPageInput = A.one('#<portlet:namespace />foldersPerPage');

		if (foldersPerPageInput) {
			valueMap.delta1 = foldersPerPageInput.val();
		}

		var fileEntriesPerPageInput = A.one('#<portlet:namespace />fileEntriesPerPage');

		if (fileEntriesPerPageInput) {
			valueMap.delta2 = fileEntriesPerPageInput.val();
		}

		var portlet = Liferay.Util.getTop().AUI().one('#p_p_id<%= HtmlUtil.escapeJS(PortalUtil.getPortletNamespace(portletResource)) %>');

		portlet.refreshURL = portlet.refreshURL.replace(
			/(cur\d{1}|delta[12])(=|%3D)[^%&]+/g,
			function(match, param, equals) {
				return param + equals + (valueMap[param] || 1);
			}
		);
	</aui:script>
</c:if>