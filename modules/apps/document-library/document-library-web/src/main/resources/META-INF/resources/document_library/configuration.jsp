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

<%@ include file="/document_library/init.jsp" %>

<%
DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(dlRequestHelper);
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
	onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-ui:error key="displayViewsInvalid" message="display-style-views-cannot-be-empty" />
	<liferay-ui:error key="rootFolderIdInvalid" message="please-enter-a-valid-root-folder" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<aui:input name="preferences--rootFolderId--" type="hidden" value="<%= rootFolderId %>" />
			<aui:input name="preferences--displayViews--" type="hidden" />
			<aui:input name="preferences--entryColumns--" type="hidden" />

			<liferay-frontend:fieldset
				collapsible="<%= true %>"
				id="documentLibraryDisplay"
				label="display-settings"
			>
				<aui:input id="showActions" label="show-actions" name="preferences--showActions--" type="checkbox" value="<%= dlPortletInstanceSettings.isShowActions() %>" />

				<aui:input label="show-search" name="preferences--showFoldersSearch--" type="checkbox" value="<%= dlPortletInstanceSettings.isShowFoldersSearch() %>" />

				<aui:input label="show-related-assets" name="preferences--enableRelatedAssets--" type="checkbox" value="<%= dlPortletInstanceSettings.isEnableRelatedAssets() %>" />

				<aui:select label="maximum-entries-to-display" name="preferences--entriesPerPage--">

					<%
					for (int pageDeltaValue : PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) {
					%>

						<aui:option label="<%= pageDeltaValue %>" selected="<%= dlPortletInstanceSettings.getEntriesPerPage() == pageDeltaValue %>" />

					<%
					}
					%>

				</aui:select>

				<aui:field-wrapper label="display-style-views">
					<liferay-ui:input-move-boxes
						leftBoxName="currentDisplayViews"
						leftList="<%= dlPortletInstanceSettingsHelper.getCurrentDisplayViews() %>"
						leftReorder="<%= Boolean.TRUE.toString() %>"
						leftTitle="current"
						rightBoxName="availableDisplayViews"
						rightList="<%= dlPortletInstanceSettingsHelper.getAvailableDisplayViews() %>"
						rightTitle="available"
					/>
				</aui:field-wrapper>
			</liferay-frontend:fieldset>

			<liferay-frontend:fieldset
				collapsible="<%= true %>"
				id="documentLibraryItemsListingPanel"
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
			</liferay-frontend:fieldset>

			<liferay-frontend:fieldset
				collapsible="<%= true %>"
				id="documentLibraryEntriesListingPanel"
				label="entries-listing-for-table-display-style"
			>
				<liferay-frontend:fieldset>
					<aui:field-wrapper label="show-columns">
						<liferay-ui:input-move-boxes
							leftBoxName="currentEntryColumns"
							leftList="<%= dlPortletInstanceSettingsHelper.getCurrentEntryColumns() %>"
							leftReorder="<%= Boolean.TRUE.toString() %>"
							leftTitle="current"
							rightBoxName="availableEntryColumns"
							rightList="<%= dlPortletInstanceSettingsHelper.getAvailableEntryColumns() %>"
							rightTitle="available"
						/>
					</aui:field-wrapper>
				</liferay-frontend:fieldset>
			</liferay-frontend:fieldset>

			<liferay-frontend:fieldset
				collapsible="<%= true %>"
				id="documentLibraryDocumentsRatingsPanel"
				label="ratings"
			>
				<aui:input name="preferences--enableRatings--" type="checkbox" value="<%= dlPortletInstanceSettings.isEnableRatings() %>" />
				<aui:input label="enable-ratings-for-comments" name="preferences--enableCommentRatings--" type="checkbox" value="<%= dlPortletInstanceSettings.isEnableCommentRatings() %>" />
			</liferay-frontend:fieldset>

			<aui:script require="metal-dom/src/dom as dom">
				var selectFolderButton = document.getElementById(
					'<portlet:namespace />selectFolderButton'
				);

				if (selectFolderButton) {
					selectFolderButton.addEventListener('click', function(event) {
						Liferay.Util.selectEntity(
							{
								dialog: {
									constrain: true,
									destroyOnHide: true,
									modal: true,
									width: 600
								},
								id:
									'_<%= HtmlUtil.escapeJS(dlRequestHelper.getPortletResource()) %>_selectFolder',
								title:
									'<liferay-ui:message arguments="folder" key="select-x" />',

								<liferay-portlet:renderURL portletName="<%= dlRequestHelper.getPortletResource() %>" var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
									<portlet:param name="mvcRenderCommandName" value="/document_library/select_folder" />
									<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
									<portlet:param name="ignoreRootFolder" value="<%= Boolean.TRUE.toString() %>" />
									<portlet:param name="showMountFolder" value="<%= Boolean.FALSE.toString() %>" />
								</liferay-portlet:renderURL>

								uri: '<%= HtmlUtil.escapeJS(selectFolderURL.toString()) %>'
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
					});
				}

				var showActionsInput = document.getElementById(
					'<portlet:namespace />showActions'
				);

				if (showActionsInput) {
					showActionsInput.addEventListener('change', function(event) {
						var currentColumnsElement = document.getElementById(
							'<portlet:namespace />currentEntryColumns'
						);

						if (currentColumnsElement) {
							if (showActionsInput.checked) {
								dom.append(
									currentColumnsElement,
									'<option value="action"><%= UnicodeLanguageUtil.get(request, "action") %></option>'
								);
							}
							else {
								var options = document.querySelectorAll(
									'#<portlet:namespace />currentEntryColumns option[value="action"], #<portlet:namespace />availableEntryColumns option[value="action"]'
								);

								Array.prototype.forEach.call(options, function(option) {
									dom.exitDocument(option);
								});
							}
						}
					});
				}
			</aui:script>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<script>
	function <portlet:namespace />saveConfiguration() {
		var form = document.<portlet:namespace />fm;

		var Util = Liferay.Util;

		Util.postForm(form, {
			data: {
				displayViews: Util.listSelect(
					Util.getFormElement(form, 'currentDisplayViews')
				),
				entryColumns: Util.listSelect(
					Util.getFormElement(form, 'currentEntryColumns')
				)
			}
		});
	}
</script>