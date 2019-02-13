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

FragmentCollectionResourcesManagementToolbarDisplayContext fragmentCollectionResourcesManagementToolbarDisplayContext = new FragmentCollectionResourcesManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, fragmentCollectionResourcesDisplayContext);
%>

<clay:management-toolbar
	displayContext="<%= fragmentCollectionResourcesManagementToolbarDisplayContext %>"
/>

<portlet:actionURL name="/fragment/delete_fragment_collection_resources" var="deleteFragmentCollectionResourcesURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<portlet:actionURL name="/fragment/add_fragment_collection_resource" var="addFragmentCollectionResourceURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= addFragmentCollectionResourceURL %>" name="fragmentCollectionResourceFm">
	<aui:input name="fragmentCollectionId" type="hidden" value="<%= String.valueOf(fragmentDisplayContext.getFragmentCollectionId()) %>" />
	<aui:input name="fileEntryId" type="hidden" />
</aui:form>

<aui:form name="fm">
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
				<clay:vertical-card
					verticalCard="<%= new FragmentCollectionResourceVerticalCard(fileEntry, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= FragmentPermission.contains(permissionChecker, scopeGroupId, FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES) %>">
	<liferay-frontend:component
		componentId="<%= FragmentWebKeys.FRAGMENT_COLLECTION_RESOURCE_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
		module="js/FragmentCollectionResourceDropdownDefaultEventHandler.es"
	/>
</c:if>

<liferay-frontend:component
	componentId="<%= fragmentCollectionResourcesManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/FragmentCollectionResourcesManagementToolbarDefaultEventHandler.es"
/>