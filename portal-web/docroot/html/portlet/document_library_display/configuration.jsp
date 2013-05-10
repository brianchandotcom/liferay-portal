<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
String strutsAction = "/document_library_display";

if (portletResource.equals(PortletKeys.DOCUMENT_LIBRARY)) {
	strutsAction = "/document_library";
}

String redirect = ParamUtil.getString(request, "redirect");

String portletNameSpace = PortalUtil.getPortletNamespace(portletResource);
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="preferences--rootFolderId--" type="hidden" value="<%= rootFolderId %>" />
	<aui:input name="preferences--folderColumns--" type="hidden" />
	<aui:input name="preferences--fileEntryColumns--" type="hidden" />

	<liferay-ui:error key="rootFolderId" message="please-enter-a-valid-root-folder" />

	<liferay-ui:panel-container extended="<%= true %>" id="documentLibrarySettingsPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryDisplay" persistState="<%= true %>" title="display-settings">
			<aui:input label="show-actions" name="preferences--showActions--" type="checkbox" value="<%= showActions %>" />

			<aui:input label="show-folder-menu" name="preferences--showFolderMenu--" type="checkbox" value="<%= showFolderMenu %>" />

			<aui:input label="show-navigation-links" name="preferences--showTabs--" type="checkbox" value="<%= showTabs %>" />

			<aui:input label="show-search" name="preferences--showFoldersSearch--" type="checkbox" value="<%= showFoldersSearch %>" />
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryFoldersListingPanel" persistState="<%= true %>" title="folders-listing">
			<aui:fieldset>
				<aui:field-wrapper label="root-folder">
					<portlet:renderURL var="viewFolderURL">
						<portlet:param name="struts_action" value='<%= strutsAction + "/view" %>' />
						<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
					</portlet:renderURL>

					<aui:a href="<%= viewFolderURL %>" id="rootFolderName"><%= rootFolderName %></aui:a>

					<aui:button name="openFolderSelectorButton" value="select" />

					<%
					String taglibRemoveFolder = "Liferay.Util.removeFolderSelection('rootFolderId', 'rootFolderName', '" + renderResponse.getNamespace() + "');";
					%>

					<aui:button disabled="<%= rootFolderId <= 0 %>" name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
				</aui:field-wrapper>

				<aui:input name="preferences--showSubfolders--" type="checkbox" value="<%= showSubfolders %>" />

				<aui:input name="preferences--foldersPerPage--" size="2" type="text" value="<%= foldersPerPage %>" />

				<aui:field-wrapper label="show-columns">

					<%
					Set<String> availableFolderColumns = SetUtil.fromArray(StringUtil.split(allFolderColumns));

					// Left list

					List leftList = new ArrayList();

					for (String folderColumn : folderColumns) {
						leftList.add(new KeyValuePair(folderColumn, LanguageUtil.get(pageContext, folderColumn)));
					}

					// Right list

					List rightList = new ArrayList();

					Arrays.sort(folderColumns);

					for (String folderColumn : availableFolderColumns) {
						if (Arrays.binarySearch(folderColumns, folderColumn) < 0) {
							rightList.add(new KeyValuePair(folderColumn, LanguageUtil.get(pageContext, folderColumn)));
						}
					}

					rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
					%>

					<liferay-ui:input-move-boxes
						leftBoxName="currentFolderColumns"
						leftList="<%= leftList %>"
						leftReorder="true"
						leftTitle="current"
						rightBoxName="availableFolderColumns"
						rightList="<%= rightList %>"
						rightTitle="available"
					/>
				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryDocumentsListingPanel" persistState="<%= true %>" title="documents-listing">
			<aui:fieldset>
				<aui:input label="documents-per-page" name="preferences--fileEntriesPerPage--" size="2" type="text" value="<%= fileEntriesPerPage %>" />

				<aui:field-wrapper label="show-columns">

					<%
					Set<String> availableFileEntryColumns = SetUtil.fromArray(StringUtil.split(allFileEntryColumns));

					// Left list

					List leftList = new ArrayList();

					for (String fileEntryColumn : fileEntryColumns) {
						leftList.add(new KeyValuePair(fileEntryColumn, LanguageUtil.get(pageContext, fileEntryColumn)));
					}

					// Right list

					List rightList = new ArrayList();

					Arrays.sort(fileEntryColumns);

					for (String fileEntryColumn : availableFileEntryColumns) {
						if (Arrays.binarySearch(fileEntryColumns, fileEntryColumn) < 0) {
							rightList.add(new KeyValuePair(fileEntryColumn, LanguageUtil.get(pageContext, fileEntryColumn)));
						}
					}

					rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
					%>

					<liferay-ui:input-move-boxes
						leftBoxName="currentFileEntryColumns"
						leftList="<%= leftList %>"
						leftReorder="true"
						leftTitle="current"
						rightBoxName="availableFileEntryColumns"
						rightList="<%= rightList %>"
						rightTitle="available"
					/>
				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryDocumentsRatingsPanel" persistState="<%= true %>" title="ratings">
			<aui:input name="preferences--enableRatings--" type="checkbox" value="<%= enableRatings %>" />
			<aui:input name="preferences--enableCommentRatings--" type="checkbox" value="<%= enableCommentRatings %>" />
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<liferay-portlet:renderURL portletName="<%= portletResource %>" var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="struts_action" value='<%= strutsAction + "/select_folder" %>' />
</liferay-portlet:renderURL>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />openFolderSelectorButton').on(
		'click',
		function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						align: Liferay.Util.Window.ALIGN_CENTER,
						constrain: true,
						modal: true,
						stack: true,
						width: 600
					},
					id: '_<%= HtmlUtil.escapeJS(portletResource) %>_selectFolder',
					title: '<%= UnicodeLanguageUtil.format(pageContext, "select-x", "folder") %>',
					uri: '<%= selectFolderURL.toString() %>'
				},
				function(event) {
					var folderData = {
						idString: 'rootFolderId',
						idValue: event.folderid,
						nameString: 'rootFolderName',
						nameValue: event.foldername
					};

					Liferay.Util.selectFolder(folderData, '<liferay-portlet:renderURL portletName="<%= portletResource %>"><portlet:param name="struts_action" value='<%= strutsAction + "/view" %>' /></liferay-portlet:renderURL>', '<portlet:namespace />');
				}
			);
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

		var portlet = Liferay.Util.getTop().AUI().one('#p_p_id<%= portletNameSpace %>');

		portlet.refreshURL = portlet.refreshURL.replace(
			/(cur\d{1}|delta[12])(=|%3D)[^%&]+/g,
			function(match, param, equals) {
				return param + equals + (valueMap[param] || 1);
			}
		);
	</aui:script>
</c:if>