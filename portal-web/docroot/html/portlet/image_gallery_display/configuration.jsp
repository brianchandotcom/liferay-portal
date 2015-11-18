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

<%@ include file="/html/portlet/image_gallery_display/init.jsp" %>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="true" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />
	<aui:input name="preferences--mimeTypes--" type="hidden" />
	<aui:input name="preferences--rootFolderId--" type="hidden" value="<%= rootFolderId %>" />

	<liferay-ui:error key="rootFolderIdInvalid" message="please-enter-a-valid-root-folder" />

	<liferay-ui:panel-container extended="<%= true %>" id="imageGalleryDisplaySettingsPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="imageGalleryDisplayDisplay" persistState="<%= true %>" title="display-settings">
			<aui:fieldset>
				<aui:input label="show-actions" name="preferences--showActions--" type="checkbox" value="<%= showActions %>" />

				<aui:input label="show-folder-menu" name="preferences--showFolderMenu--" type="checkbox" value="<%= showFolderMenu %>" />

				<aui:input label="show-navigation-links" name="preferences--showTabs--" type="checkbox" value="<%= showTabs %>" />

				<aui:input label="show-search" name="preferences--showFoldersSearch--" type="checkbox" value="<%= showFoldersSearch %>" />

				<aui:field-wrapper label="show-media-type">

					<%

					// Left list

					List leftList = new ArrayList();

					String[] mediaGalleryMimeTypes = DLUtil.getMediaGalleryMimeTypes(portletPreferences, renderRequest);

					for (String mimeType : mediaGalleryMimeTypes) {
						leftList.add(new KeyValuePair(mimeType, LanguageUtil.get(pageContext, mimeType)));
					}

					// Right list

					List rightList = new ArrayList();

					Set<String> allMediaGalleryMimeTypes = DLUtil.getAllMediaGalleryMimeTypes();

					for (String mimeType : allMediaGalleryMimeTypes) {
						if (Arrays.binarySearch(mediaGalleryMimeTypes, mimeType) < 0) {
							rightList.add(new KeyValuePair(mimeType, LanguageUtil.get(pageContext, mimeType)));
						}
					}
					%>

					<liferay-ui:input-move-boxes
						leftBoxName="currentMimeTypes"
						leftList="<%= leftList %>"
						leftReorder="true"
						leftTitle="current"
						rightBoxName="availableMimeTypes"
						rightList="<%= rightList %>"
						rightTitle="available"
					/>
				</aui:field-wrapper>

				<div class="display-template">

					<%
					TemplateHandler templateHandler = TemplateHandlerRegistryUtil.getTemplateHandler(FileEntry.class.getName());
					%>

					<liferay-ui:ddm-template-selector
						classNameId="<%= PortalUtil.getClassNameId(templateHandler.getClassName()) %>"
						displayStyle="<%= displayStyle %>"
						displayStyleGroupId="<%= displayStyleGroupId %>"
						refreshURL="<%= currentURL %>"
						showEmptyOption="<%= true %>"
					/>
				</div>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="imageGalleryDisplayFoldersListingPanel" persistState="<%= true %>" title="folders-listing">
			<aui:fieldset>
				<aui:field-wrapper>
					<div class="input-append">
						<aui:input label="root-folder" name="rootFolderName" type="resource" value="<%= rootFolderName %>" />

						<aui:button name="openFolderSelectorButton" value="select" />

						<%
						String taglibRemoveFolder = "Liferay.Util.removeFolderSelection('rootFolderId', 'rootFolderName', '" + renderResponse.getNamespace() + "');";
						%>

						<aui:button disabled="<%= rootFolderId <= 0 %>" name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
					</div>
				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="imageGalleryImagesRatingsPanel" persistState="<%= true %>" title="ratings">
			<aui:input name="preferences--enableRatings--" type="checkbox" value="<%= enableRatings %>" />
			<aui:input name="preferences--enableCommentRatings--" type="checkbox" value="<%= enableCommentRatings %>" />
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<liferay-portlet:renderURL portletName="<%= portletResource %>" var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="struts_action" value='<%= "/image_gallery_display/select_folder" %>' />
</liferay-portlet:renderURL>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />openFolderSelectorButton').on(
		'click',
		function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true,
						width: 680
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

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />saveConfiguration',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace />mimeTypes.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentMimeTypes);

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);
</aui:script>