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
IGConfigurationDisplayContext igConfigurationDisplayContext = (IGConfigurationDisplayContext)request.getAttribute(IGConfigurationDisplayContext.class.getName());
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
	onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveConfiguration();" %>'
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />
	<aui:input name="preferences--mimeTypes--" type="hidden" />
	<aui:input name="preferences--rootFolderId--" type="hidden" value="<%= igConfigurationDisplayContext.getRootFolderId() %>" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:error key="rootFolderIdInvalid" message="please-enter-a-valid-root-folder" />

		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset
				collapsible="<%= true %>"
				id="imageGalleryDisplayDisplay"
				label="display-settings"
			>
				<aui:input label="show-actions" name="preferences--showActions--" type="checkbox" value="<%= igConfigurationDisplayContext.isShowActions() %>" />

				<aui:field-wrapper label="show-media-type">
					<liferay-ui:input-move-boxes
						leftBoxName="currentMimeTypes"
						leftList="<%= igConfigurationDisplayContext.getCurrentMimeTypes() %>"
						leftReorder="<%= Boolean.TRUE.toString() %>"
						leftTitle="current"
						rightBoxName="availableMimeTypes"
						rightList="<%= igConfigurationDisplayContext.getAvailableMimeTypes() %>"
						rightTitle="available"
					/>
				</aui:field-wrapper>

				<div class="display-template">
					<liferay-template:template-selector
						className="<%= FileEntry.class.getName() %>"
						displayStyle="<%= igConfigurationDisplayContext.getDisplayStyle() %>"
						displayStyleGroupId="<%= igConfigurationDisplayContext.getDisplayStyleGroupId() %>"
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
						<aui:input label="root-folder" name="rootFolderName" type="resource" value="<%= igConfigurationDisplayContext.getRootFolderName() %>" />

						<div class="alert alert-warning <%= igConfigurationDisplayContext.isRootFolderInTrash() ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />rootFolderInTrash">
							<liferay-ui:message key="the-selected-root-folder-is-in-the-recycle-bin-please-remove-it-or-select-another-one" />
						</div>

						<div class="alert alert-warning <%= igConfigurationDisplayContext.isRootFolderNotFound() ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />rootFolderNotFound">
							<liferay-ui:message key="the-selected-root-folder-cannot-be-found-please-select-another-one" />
						</div>

						<aui:button name="openFolderSelectorButton" value="select" />

						<%
						String taglibRemoveFolder = "Liferay.Util.removeEntitySelection('rootFolderId', 'rootFolderName', this, '" + liferayPortletResponse.getNamespace() + "');";
						%>

						<aui:button disabled="<%= igConfigurationDisplayContext.getRootFolderId() == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID %>" name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
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
		openFolderSelectorButton.addEventListener('click', (event) => {
			Liferay.Util.openSelectionModal({
				selectEventName:
					'<%= igConfigurationDisplayContext.getItemSelectedEventName() %>',
				multiple: false,
				onSelect: function (selectedItem) {
					if (!selectedItem) {
						return;
					}

					var folderData = {
						idString: 'rootFolderId',
						idValue: selectedItem.folderid,
						nameString: 'rootFolderName',
						nameValue: selectedItem.foldername,
					};

					Liferay.Util.selectFolder(folderData, '<portlet:namespace />');

					var rootFolderInTrashWarning = document.querySelector(
						'#<portlet:namespace />rootFolderInTrash'
					);

					rootFolderInTrashWarning.classList.add('hide');

					var rootFolderNotFoundWarning = document.querySelector(
						'#<portlet:namespace />rootFolderNotFound'
					);

					rootFolderNotFoundWarning.classList.add('hide');
				},
				title: '<liferay-ui:message arguments="folder" key="select-x" />',
				url:
					'<%= HtmlUtil.escapeJS(String.valueOf(igConfigurationDisplayContext.getSelectFolderURL())) %>',
			});
		});
	}

	function <portlet:namespace />saveConfiguration() {
		var form = document.<portlet:namespace />fm;

		Liferay.Util.postForm(form, {
			data: {
				mimeTypes: Liferay.Util.listSelect(
					Liferay.Util.getFormElement(form, 'currentMimeTypes')
				),
			},
		});
	}
</script>