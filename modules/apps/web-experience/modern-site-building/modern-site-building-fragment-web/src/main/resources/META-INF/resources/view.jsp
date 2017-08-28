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

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<portlet:renderURL var="mainURL" />

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item href="<%= mainURL.toString() %>" label="collections" selected="<%= true %>" />
	</aui:nav>

	<c:if test="<%= msbFragmentDisplayContext.isShowMSBFragmentCollectionsSearch() %>">
		<portlet:renderURL var="portletURL">
			<portlet:param name="mvcPath" value="/view.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="displayStyle" value="<%= msbFragmentDisplayContext.getDisplayStyle() %>" />
		</portlet:renderURL>

		<aui:nav-bar-search>
			<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>

<liferay-frontend:management-bar
	disabled="<%= msbFragmentDisplayContext.isDisabledMSBFragmentCollectionsManagementBar() %>"
	includeCheckBox="<%= true %>"
	searchContainerId="msbFragmentCollections"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon"} %>'
			portletURL="<%= currentURLObj %>"
			selectedDisplayStyle="<%= msbFragmentDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= currentURLObj %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= msbFragmentDisplayContext.getOrderByCol() %>"
			orderByType="<%= msbFragmentDisplayContext.getOrderByType() %>"
			orderColumns="<%= msbFragmentDisplayContext.getOrderColumns() %>"
			portletURL="<%= currentURLObj %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteSelectedMSBFragmentCollections" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="deleteMSBFragmentCollection" var="deleteMSBFragmentCollectionURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteMSBFragmentCollectionURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="msbFragmentCollections"
		searchContainer="<%= msbFragmentDisplayContext.getMSBFragmentCollectionsSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.modern.site.building.fragment.model.MSBFragmentCollection"
			keyProperty="msbFragmentCollectionId"
			modelVar="msbFragmentCollection"
		>
			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcPath" value="/view_msb_fragment_entries.jsp" />
				<portlet:param name="msbFragmentCollectionId" value="<%= String.valueOf(msbFragmentCollection.getMsbFragmentCollectionId()) %>" />
			</portlet:renderURL>

			<%
			row.setCssClass("entry-card lfr-asset-folder");
			%>

			<liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text colspan="<%= 2 %>">
					<liferay-frontend:horizontal-card
						actionJsp="/msb_fragment_collection_action.jsp"
						actionJspServletContext="<%= application %>"
						resultRow="<%= row %>"
						rowChecker="<%= searchContainer.getRowChecker() %>"
						text="<%= HtmlUtil.escape(msbFragmentCollection.getName()) %>"
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

		<liferay-ui:search-iterator displayStyle="<%= msbFragmentDisplayContext.getDisplayStyle() %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= msbFragmentDisplayContext.isShowAddButton(MSBFragmentActionKeys.ADD_MSB_FRAGMENT_COLLECTION) %>">
	<portlet:renderURL var="addFragmentCollectionURL">
		<portlet:param name="mvcPath" value="/edit_msb_fragment_collection.jsp" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-collection") %>' url="<%= addFragmentCollectionURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />deleteSelectedMSBFragmentCollections').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm($(document.<portlet:namespace />fm));
			}
		}
	);
</aui:script>