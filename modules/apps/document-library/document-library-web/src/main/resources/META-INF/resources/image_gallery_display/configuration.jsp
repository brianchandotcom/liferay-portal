<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	<aui:input name="preferences--selectedRepositoryId--" type="hidden" value="<%= igConfigurationDisplayContext.getSelectedRepositoryId() %>" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:error key="rootFolderIdInvalid" message="please-enter-a-valid-root-folder" />

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

		<%
		String warningMessage = null;

		if (igConfigurationDisplayContext.isRootFolderInTrash()) {
			warningMessage = LanguageUtil.get(request, "the-selected-root-folder-is-in-the-recycle-bin-please-remove-it-or-select-another-one");
		}

		if (igConfigurationDisplayContext.isRootFolderNotFound()) {
			warningMessage = LanguageUtil.get(request, "the-selected-root-folder-cannot-be-found-please-select-another-one");
		}
		%>

		<liferay-frontend:fieldset
			collapsible="<%= true %>"
			id="imageGalleryDisplayFoldersListingPanel"
			label="folders-listing"
		>
			<aui:field-wrapper>
				<liferay-frontend:resource-selector
					inputLabel='<%= LanguageUtil.get(request, "root-folder") %>'
					inputName="preferences--rootFolderId--"
					modalTitle='<%= LanguageUtil.get(request, "select-folder") %>'
					resourceName="<%= igConfigurationDisplayContext.getRootFolderName() %>"
					resourceValue="<%= String.valueOf(igConfigurationDisplayContext.getRootFolderId()) %>"
					selectEventName="folderSelected"
					selectResourceURL="<%= igConfigurationDisplayContext.getSelectRootFolderURL() %>"
					showRemoveButton="<%= true %>"
					warningMessage="<%= warningMessage %>"
				/>
			</aui:field-wrapper>
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script>
	function <portlet:namespace />saveConfiguration() {
		var form = document.<portlet:namespace />fm;

		Liferay.Util.postForm(form, {
			data: {
				mimeTypes: Liferay.Util.getSelectedOptionValues(
					Liferay.Util.getFormElement(form, 'currentMimeTypes')
				),
			},
		});
	}
</aui:script>