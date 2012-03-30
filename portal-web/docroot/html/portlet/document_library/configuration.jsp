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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String strutsAction = "/document_library_display";

if (portletResource.equals(PortletKeys.DOCUMENT_LIBRARY)) {
	strutsAction = "/document_library";
}

String redirect = ParamUtil.getString(request, "redirect");
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="preferences--rootFolderId--" type="hidden" value="<%= rootFolderId %>" />
	<aui:input name="preferences--displayViews--" type="hidden" />
	<aui:input name="preferences--entryColumns--" type="hidden" />

	<liferay-ui:error key="displayViewsInvalid" message="display-style-views-cannot-be-empty" />
	<liferay-ui:error key="rootFolderIdInvalid" message="please-enter-a-valid-root-folder" />

	<liferay-ui:panel-container extended="<%= true %>" id="documentLibrarySettingsPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryItemsListingPanel" persistState="<%= true %>" title="display-settings">
			<aui:fieldset>
				<aui:field-wrapper label="root-folder">
					<portlet:renderURL var="viewFolderURL">
						<portlet:param name="struts_action" value='<%= strutsAction + "/view" %>' />
						<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
					</portlet:renderURL>

					<aui:a href="<%= viewFolderURL %>" id="rootFolderName"><%= rootFolderName %></aui:a>

					<aui:button name="openFolderSelectorButton" onClick='<%= renderResponse.getNamespace() + "openFolderSelector();" %>' value="select" />

					<%
					String taglibRemoveFolder = "Liferay.Util.removeFolderSelection('rootFolderId', 'rootFolderName', '" + renderResponse.getNamespace() + "');";
					%>

					<aui:button disabled="<%= rootFolderId <= 0 %>" name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
				</aui:field-wrapper>

				<aui:input label="show-search" name="preferences--showFoldersSearch--" type="checkbox" value="<%= showFoldersSearch %>" />

				<aui:select label="maximum-entries-to-display" name="preferences--entriesPerPage--">

					<%
					for (int deltaValue : PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) {
					%>

						<aui:option label="<%= deltaValue %>" selected="<%= entriesPerPage == deltaValue %>" />

					<%
					}
					%>

				</aui:select>

				<aui:field-wrapper label="display-style-views">

					<%
					Set<String> availableDisplayViews = SetUtil.fromArray(PropsValues.DL_DISPLAY_VIEWS);

					// Left list

					List leftList = new ArrayList();

					for (int i = 0; i < displayViews.length; i++) {
						String displayView = displayViews[i];

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

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryEntriesListingPanel" persistState="<%= true %>" title="entries-listing">
			<aui:fieldset>
				<aui:input name="preferences--enableRelatedAssets--" type="checkbox" value="<%= enableRelatedAssets %>" />

				<aui:field-wrapper label="show-columns">

					<%
					Set<String> availableEntryColumns = SetUtil.fromArray(StringUtil.split(allEntryColumns));

					// Left list

					List leftList = new ArrayList();

					for (int i = 0; i < entryColumns.length; i++) {
						String entryColumn = entryColumns[i];

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
			<aui:input name="preferences--enableCommentRatings--" type="checkbox" value="<%= enableCommentRatings %>" />
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />openFolderSelector() {
		var folderWindow = window.open('<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" portletName="<%= portletResource %>"><portlet:param name="struts_action" value='<%= strutsAction + "/select_folder" %>' /></liferay-portlet:renderURL>', 'folder', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=830');

		folderWindow.focus();
	}

	function <%= PortalUtil.getPortletNamespace(portletResource) %>selectFolder(rootFolderId, rootFolderName) {
		var folderData = {
			idString: 'rootFolderId',
			idValue: rootFolderId,
			nameString: 'rootFolderName',
			nameValue: rootFolderName
		};

		Liferay.Util.selectFolder(folderData, '<liferay-portlet:renderURL portletName="<%= portletResource %>"><portlet:param name="struts_action" value='<%= strutsAction + "/view" %>' /></liferay-portlet:renderURL>', '<portlet:namespace />');
	}

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