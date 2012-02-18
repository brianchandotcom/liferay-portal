<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%
String redirect = ParamUtil.getString(request, "redirect");
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="preferences--mimeTypes--" type="hidden" />
	<aui:input name="preferences--rootFolderId--" type="hidden" value="<%= rootFolderId %>" />

	<liferay-ui:error key="rootFolderId" message="please-enter-a-valid-root-folder" />

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

					String[] mediaGalleryMimeTypes = DLUtil.getMediaGalleryMimeTypes(preferences, renderRequest);

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
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="imageGalleryDisplayFoldersListingPanel" persistState="<%= true %>" title="folders-listing">
			<aui:fieldset>
				<aui:field-wrapper label="root-folder">
					<portlet:renderURL var="viewFolderURL">
						<portlet:param name="struts_action" value='<%= "/image_gallery_display/view" %>' />
						<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
					</portlet:renderURL>

					<aui:a href="<%= viewFolderURL %>" id="rootFolderName"><%= rootFolderName %></aui:a>

					<aui:button name="openFolderSelectorButton" onClick='<%= renderResponse.getNamespace() + "openFolderSelector();" %>' value="select" />

					<%
					String taglibRemoveFolder = "Liferay.Util.removeFolderSelection('rootFolderId', 'rootFolderName', '" + renderResponse.getNamespace() + "');";
					%>

					<aui:button disabled="<%= rootFolderId <= 0 %>" name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />openFolderSelector() {
		var folderWindow = window.open('<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" portletName="<%= portletResource %>"><portlet:param name="struts_action" value='<%= "/image_gallery_display/select_folder" %>' /></liferay-portlet:renderURL>', 'folder', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=830');

		folderWindow.focus();
	}

	function <%= PortalUtil.getPortletNamespace(portletResource) %>selectFolder(rootFolderId, rootFolderName) {
		var folderData = {
			idString: 'rootFolderId',
			idValue: rootFolderId,
			nameString: 'rootFolderName',
			nameValue: rootFolderName
		};

		Liferay.Util.selectFolder(folderData, '<liferay-portlet:renderURL portletName="<%= portletResource %>"><portlet:param name="struts_action" value='<%= "/image_gallery_display/view" %>' /></liferay-portlet:renderURL>', '<portlet:namespace />');
	}

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