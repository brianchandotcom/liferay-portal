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
dlPortletInstanceSettings = DLPortletInstanceSettings.getInstance(layout, portletId, request.getParameterMap());

DLConfigurationDisplayContext dlConfigurationDisplayContext = new DLConfigurationDisplayContext(request, dlPortletInstanceSettings);
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
	<liferay-portlet:param name="settingsScope" value="portletInstance" />
</liferay-portlet:actionURL>

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

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
						<div class="form-group">
							<aui:input label="root-folder" name="rootFolderName" type="resource" value="<%= rootFolderName %>" />

							<aui:button name="selectFolderButton" value="select" />

							<%
							String taglibRemoveFolder = "Liferay.Util.removeFolderSelection('rootFolderId', 'rootFolderName', '" + renderResponse.getNamespace() + "');";
							%>

							<aui:button disabled="<%= rootFolderId <= 0 %>" name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
						</div>

						<aui:input label="show-search" name="preferences--showFoldersSearch--" type="checkbox" value="<%= dlPortletInstanceSettings.isShowFoldersSearch() %>" />

						<aui:select label="maximum-entries-to-display" name="preferences--entriesPerPage--">

							<%
							for (int pageDeltaValue : PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) {
							%>

								<aui:option label="<%= pageDeltaValue %>" selected="<%= dlPortletInstanceSettings.getEntriesPerPage() == pageDeltaValue %>" />

							<%
							}
							%>

						</aui:select>

						<aui:input name="preferences--enableRelatedAssets--" type="checkbox" value="<%= dlPortletInstanceSettings.isEnableRelatedAssets() %>" />

						<aui:field-wrapper label="display-style-views">
							<liferay-ui:input-move-boxes
								leftBoxName="currentDisplayViews"
								leftList="<%= dlConfigurationDisplayContext.getCurrentDisplayViews() %>"
								leftReorder="true"
								leftTitle="current"
								rightBoxName="availableDisplayViews"
								rightList="<%= dlConfigurationDisplayContext.getAvailableDisplayViews() %>"
								rightTitle="available"
							/>
						</aui:field-wrapper>
					</aui:fieldset>
				</liferay-ui:panel>

				<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryEntriesListingPanel" persistState="<%= true %>" title="entries-listing-for-list-display-style">
					<aui:fieldset>
						<aui:field-wrapper label="show-columns">
							<liferay-ui:input-move-boxes
								leftBoxName="currentEntryColumns"
								leftList="<%= dlConfigurationDisplayContext.getCurrentEntryColumns() %>"
								leftReorder="true"
								leftTitle="current"
								rightBoxName="availableEntryColumns"
								rightList="<%= dlConfigurationDisplayContext.getAvailableEntryColumns() %>"
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

			<aui:script>
				AUI.$('#<portlet:namespace />selectFolderButton').on(
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
									<portlet:param name="struts_action" value="/document_library/select_folder" />
									<portlet:param name="ignoreRootFolder" value="<%= Boolean.TRUE.toString() %>" />
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