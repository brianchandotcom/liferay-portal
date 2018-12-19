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

<%@ include file="/init.jsp" %>

<%
FragmentCollectionResourcesDisplayContext fragmentCollectionResourcesDisplayContext = new FragmentCollectionResourcesDisplayContext(renderRequest, renderResponse, request, fragmentDisplayContext);
%>

<clay:management-toolbar
	displayContext="<%= new FragmentCollectionResourcesManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, fragmentCollectionResourcesDisplayContext) %>"
/>

<portlet:actionURL name="/fragment/delete_fragment_collection_resources" var="deleteFragmentCollectionResourcesURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteFragmentCollectionResourcesURL %>" name="fm">
	<liferay-ui:search-container
		searchContainer="<%= fragmentCollectionResourcesDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.repository.model.FileEntry"
			keyProperty="fileEntryId"
			modelVar="fileEntry"
		>

			<%
			row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
			%>

			<liferay-ui:search-container-column-text>
				<liferay-frontend:vertical-card
					actionJsp="/fragment_collection_resources_action.jsp"
					actionJspServletContext="<%= application %>"
					cssClass="entry-display-style"
					imageUrl="<%= DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK, false, false) %>"
					resultRow="<%= row %>"
					rowChecker="<%= searchContainer.getRowChecker() %>"
					title="<%= fileEntry.getFileName() %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	var deleteSelectedFragmentCollectionResources = function() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			submitForm(document.querySelector('#<portlet:namespace />fm'));
		}
	}

	var ACTIONS = {
		'deleteSelectedFragmentCollectionResources': deleteSelectedFragmentCollectionResources
	};

	Liferay.componentReady('fragmentCollectionResourcesManagementToolbar').then(
		function(managementToolbar) {
			managementToolbar.on(
				['actionItemClicked'],
				function(event) {
					var itemData = event.data.item.data;

					if (itemData && itemData.action && ACTIONS[itemData.action]) {
						ACTIONS[itemData.action]();
					}
				}
			);
		}
	);
</aui:script>