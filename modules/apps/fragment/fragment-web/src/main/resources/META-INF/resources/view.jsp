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
renderResponse.setTitle(LanguageUtil.get(request, "fragments"));
%>

<liferay-ui:error exception="<%= RequiredFragmentEntryException.class %>" message="the-collection-cannot-be-deleted-because-it-contains-a-fragment-required-by-one-or-more-templates" />

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= fragmentDisplayContext.getFragmentCollectionNavigationItems() %>"
/>

<clay:management-toolbar
	actionItems="<%= fragmentDisplayContext.getFragmentCollectionActionItemsDropdownItems() %>"
	clearResultsURL="<%= fragmentDisplayContext.getFragmentCollectionClearResultsURL() %>"
	componentId="fragmentCollectionsManagementToolbar"
	creationMenu="<%= fragmentDisplayContext.isShowAddButton(FragmentActionKeys.ADD_FRAGMENT_COLLECTION) ? fragmentDisplayContext.getFragmentCollectionCreationMenu() : null %>"
	disabled="<%= fragmentDisplayContext.isDisabledFragmentCollectionsManagementBar() %>"
	filterItems="<%= fragmentDisplayContext.getFragmentCollectionFilterItemsDropdownItems() %>"
	searchActionURL="<%= fragmentDisplayContext.getFragmentCollectionSearchActionURL() %>"
	searchContainerId="fragmentCollections"
	searchFormName="searchFm"
	sortingOrder="<%= fragmentDisplayContext.getOrderByType() %>"
	sortingURL="<%= fragmentDisplayContext.getFragmentCollectionSortingURL() %>"
	totalItems="<%= fragmentDisplayContext.getFragmentCollectionTotalItems() %>"
	viewTypes="<%= fragmentDisplayContext.getFragmentCollectionViewTypeItems() %>"
/>

<aui:form cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="fragmentCollections"
		searchContainer="<%= fragmentDisplayContext.getFragmentCollectionsSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.fragment.model.FragmentCollection"
			keyProperty="fragmentCollectionId"
			modelVar="fragmentCollection"
		>
			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcRenderCommandName" value="/fragment/view_fragment_entries" />
				<portlet:param name="fragmentCollectionId" value="<%= String.valueOf(fragmentCollection.getFragmentCollectionId()) %>" />
			</portlet:renderURL>

			<%
			row.setCssClass("entry-card lfr-asset-folder");
			%>

			<liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text
					colspan="<%= 2 %>"
				>
					<liferay-frontend:horizontal-card
						actionJsp="/fragment_collection_action.jsp"
						actionJspServletContext="<%= application %>"
						resultRow="<%= row %>"
						rowChecker="<%= searchContainer.getRowChecker() %>"
						text="<%= HtmlUtil.escape(fragmentCollection.getName()) %>"
						url="<%= rowURL.toString() %>"
					>
						<liferay-frontend:horizontal-card-col>
							<liferay-frontend:horizontal-card-icon
								icon="documents-and-media"
							/>
						</liferay-frontend:horizontal-card-col>
					</liferay-frontend:horizontal-card>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= fragmentDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	window.<portlet:namespace />deleteSelectedFragmentCollections = function() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			submitForm(document.querySelector('#<portlet:namespace />fm'), '<portlet:actionURL name="/fragment/delete_fragment_collection"><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
		}
	}

	window.<portlet:namespace />exportSelectedFragmentCollections = function() {
		submitForm(document.querySelector('#<portlet:namespace />fm'), '<portlet:resourceURL id="/fragment/export_fragment_collections" />');
	}
</aui:script>