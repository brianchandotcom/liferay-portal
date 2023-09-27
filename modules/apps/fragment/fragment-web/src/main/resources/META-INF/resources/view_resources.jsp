<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
FragmentCollectionResourcesDisplayContext fragmentCollectionResourcesDisplayContext = new FragmentCollectionResourcesDisplayContext(request, renderRequest, renderResponse, fragmentEntriesDisplayContext);
%>

<c:choose>
	<c:when test="<%= fragmentCollectionResourcesDisplayContext.isShowRepositoryBrowser() %>">
		<liferay-document-library:repository-browser
			folderId="<%= fragmentCollectionResourcesDisplayContext.getFolderId() %>"
			repositoryId="<%= fragmentCollectionResourcesDisplayContext.getRepositoryId() %>"
		/>
	</c:when>
	<c:otherwise>
		<clay:management-toolbar
			managementToolbarDisplayContext="<%= new FragmentCollectionResourcesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, fragmentCollectionResourcesDisplayContext) %>"
			propsTransformer="js/FragmentCollectionResourcesManagementToolbarPropsTransformer"
		/>

		<portlet:actionURL name="/fragment/delete_fragment_collection_resources" var="deleteFragmentCollectionResourcesURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:actionURL>

		<portlet:actionURL name="/fragment/add_fragment_collection_resource" var="addFragmentCollectionResourceURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:actionURL>

		<aui:form action="<%= addFragmentCollectionResourceURL %>" name="fragmentCollectionResourceFm">
			<aui:input name="fragmentCollectionId" type="hidden" value="<%= String.valueOf(fragmentEntriesDisplayContext.getFragmentCollectionId()) %>" />
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
					<liferay-ui:search-container-column-text>
						<clay:vertical-card
							propsTransformer="js/FragmentCollectionResourceDropdownPropsTransformer"
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
	</c:otherwise>
</c:choose>