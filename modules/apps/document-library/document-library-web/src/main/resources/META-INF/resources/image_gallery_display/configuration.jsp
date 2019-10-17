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

<%@ include file="/image_gallery_display/init.jsp" %>

<%
DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(igRequestHelper);
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
	<aui:input name="preferences--mimeTypes--" type="hidden" />
	<aui:input name="preferences--rootFolderId--" type="hidden" value="<%= rootFolderId %>" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:error key="rootFolderIdInvalid" message="please-enter-a-valid-root-folder" />

		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset
				collapsible="<%= true %>"
				id="imageGalleryDisplayDisplay"
				label="display-settings"
			>
				<aui:input label="show-actions" name="preferences--showActions--" type="checkbox" value="<%= dlPortletInstanceSettings.isShowActions() %>" />

				<aui:field-wrapper label="show-media-type">
					<liferay-ui:input-move-boxes
						leftBoxName="currentMimeTypes"
						leftList="<%= dlPortletInstanceSettingsHelper.getCurrentMimeTypes() %>"
						leftReorder="<%= Boolean.TRUE.toString() %>"
						leftTitle="current"
						rightBoxName="availableMimeTypes"
						rightList="<%= dlPortletInstanceSettingsHelper.getAvailableMimeTypes() %>"
						rightTitle="available"
					/>
				</aui:field-wrapper>

				<div class="display-template">
					<liferay-ddm:template-selector
						className="<%= FileEntry.class.getName() %>"
						displayStyle="<%= displayStyle %>"
						displayStyleGroupId="<%= displayStyleGroupId %>"
						refreshURL="<%= configurationRenderURL %>"
						showEmptyOption="<%= true %>"
					/>
				</div>
			</liferay-frontend:fieldset>

			<liferay-frontend:fieldset
				collapsible="<%= true %>"
				id="imageGalleryDisplayFoldersListingPanel"
				label="folders-listing"
			>
				<aui:field-wrapper>
					<div class="form-group">
						<aui:input label="root-folder" name="rootFolderName" type="resource" value="<%= rootFolderName %>" />

						<aui:button name="openFolderSelectorButton" value="select" />

						<%
						String taglibRemoveFolder = "Liferay.Util.removeEntitySelection('rootFolderId', 'rootFolderName', this, '" + renderResponse.getNamespace() + "');";
						%>

						<aui:button disabled="<%= rootFolderId <= 0 %>" name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
					</div>
				</aui:field-wrapper>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<script>
	var openFolderSelectorButton = document.getElementById(
		'<portlet:namespace />openFolderSelectorButton'
	);

	if (openFolderSelectorButton) {
		openFolderSelectorButton.addEventListener('click', function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true,
						width: 680
					},
					id: '_<%= HtmlUtil.escapeJS(portletResource) %>_selectFolder',
					title:
						'<liferay-ui:message arguments="folder" key="select-x" />',

					<liferay-portlet:renderURL portletName="<%= portletResource %>" var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
						<portlet:param name="mvcRenderCommandName" value="/document_library/select_folder" />
						<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
						<portlet:param name="ignoreRootFolder" value="<%= Boolean.TRUE.toString() %>" />
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

	function <portlet:namespace />saveConfiguration() {
		var form = document.<portlet:namespace />fm;

		Liferay.Util.postForm(form, {
			data: {
				mimeTypes: Liferay.Util.listSelect(
					Liferay.Util.getFormElement(form, 'currentMimeTypes')
				)
			}
		});
	}
</script>