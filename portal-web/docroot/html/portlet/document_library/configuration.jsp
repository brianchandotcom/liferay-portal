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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
try {
	Folder rootFolder = DLAppLocalServiceUtil.getFolder(rootFolderId);

	rootFolderName = rootFolder.getName();

	if (rootFolder.getGroupId() != scopeGroupId) {
		rootFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		rootFolderName = StringPool.BLANK;
	}
}
catch (NoSuchFolderException nsfe) {
	rootFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
}
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL">
	<liferay-portlet:param name="settingsScope" value="portletInstance" />
</liferay-portlet:actionURL>

<liferay-portlet:renderURL portletConfiguration="true" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-ui:tabs
		names="display-settings"
		refresh="<%= false %>"
	>
		<liferay-ui:error key="displayViewsInvalid" message="display-style-views-cannot-be-empty" />
		<liferay-ui:error key="rootFolderIdInvalid" message="please-enter-a-valid-root-folder" />

		<liferay-ui:section>
			<aui:input name="preferences--rootFolderId--" type="hidden" value="<%= rootFolderId %>" />
			<aui:input name="preferences--displayViews--" type="hidden" />
			<aui:input name="preferences--entryColumns--" type="hidden" />

			<liferay-ui:panel-container extended="<%= true %>" id="documentLibrarySettingsPanelContainer" persistState="<%= true %>">
				<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryItemsListingPanel" persistState="<%= true %>" title="display-settings">
					<aui:fieldset>
						<aui:field-wrapper label="root-folder">
							<div class="input-append">
								<liferay-ui:input-resource id="rootFolderName" url="<%= rootFolderName %>" />

								<aui:button name="selectFolderButton" value="select" />

								<%
								String taglibRemoveFolder = "Liferay.Util.removeFolderSelection('rootFolderId', 'rootFolderName', '" + renderResponse.getNamespace() + "');";
								%>

								<aui:button disabled="<%= rootFolderId <= 0 %>" name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
							</div>
						</aui:field-wrapper>

						<aui:input label="show-search" name="preferences--showFoldersSearch--" type="checkbox" value="<%= showFoldersSearch %>" />

						<aui:select label="maximum-entries-to-display" name="preferences--entriesPerPage--">

							<%
							for (int pageDeltaValue : PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) {
							%>

								<aui:option label="<%= pageDeltaValue %>" selected="<%= entriesPerPage == pageDeltaValue %>" />

							<%
							}
							%>

						</aui:select>

						<aui:input name="preferences--enableRelatedAssets--" type="checkbox" value="<%= enableRelatedAssets %>" />

						<aui:field-wrapper label="display-style-views">

							<%
							Set<String> availableDisplayViews = SetUtil.fromArray(PropsValues.DL_DISPLAY_VIEWS);

							// Left list

							List leftList = new ArrayList();

							for (String displayView : displayViews) {
								leftList.add(new KeyValuePair(displayView, LanguageUtil.get(pageContext, displayView)));
							}

							// Right list

							List rightList = new ArrayList();

							Arrays.sort(displayViews);

							for (String displayView : availableDisplayViews) {
								if (Arrays.binarySearch(displayViews, displayView) < 0) {
									rightList.add(new KeyValuePair(displayView, LanguageUtil.get(pageContext, displayView)));
								}
							}

							rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
							%>

							<liferay-ui:input-move-boxes
								leftBoxName="currentDisplayViews"
								leftList="<%= leftList %>"
								leftReorder="true"
								leftTitle="current"
								rightBoxName="availableDisplayViews"
								rightList="<%= rightList %>"
								rightTitle="available"
							/>
						</aui:field-wrapper>
					</aui:fieldset>
				</liferay-ui:panel>

				<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryEntriesListingPanel" persistState="<%= true %>" title="entries-listing-for-list-display-style">
					<aui:fieldset>
						<aui:field-wrapper label="show-columns">

							<%
							Set<String> availableEntryColumns = SetUtil.fromArray(StringUtil.split(allEntryColumns));

							// Left list

							List leftList = new ArrayList();

							for (String entryColumn : entryColumns) {
								leftList.add(new KeyValuePair(entryColumn, LanguageUtil.get(pageContext, entryColumn)));
							}

							// Right list

							List rightList = new ArrayList();

							Arrays.sort(entryColumns);

							for (String entryColumn : availableEntryColumns) {
								if (Arrays.binarySearch(entryColumns, entryColumn) < 0) {
									rightList.add(new KeyValuePair(entryColumn, LanguageUtil.get(pageContext, entryColumn)));
								}
							}

							rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
							%>

							<liferay-ui:input-move-boxes
								leftBoxName="currentEntryColumns"
								leftList="<%= leftList %>"
								leftReorder="true"
								leftTitle="current"
								rightBoxName="availableEntryColumns"
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

			<liferay-portlet:renderURL portletName="<%= portletResource %>" var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="struts_action" value="/document_library/select_folder" />
			</liferay-portlet:renderURL>

			<aui:script use="aui-base">
				A.one('#<portlet:namespace />selectFolderButton').on(
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
			</aui:script>
		</liferay-ui:section>
	</liferay-ui:tabs>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />saveConfiguration',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace />displayViews.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentDisplayViews);
			document.<portlet:namespace />fm.<portlet:namespace />entryColumns.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentEntryColumns);

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);
</aui:script>