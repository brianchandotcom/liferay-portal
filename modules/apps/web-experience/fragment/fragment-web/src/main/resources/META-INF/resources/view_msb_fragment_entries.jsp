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
portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(msbFragmentDisplayContext.getMSBFragmentCollectionsRedirect());

renderResponse.setTitle(msbFragmentDisplayContext.getMSBFragmentCollectionTitle());
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<portlet:renderURL var="mainURL" />

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item href="<%= currentURL %>" label="fragments" selected="<%= true %>" />
	</aui:nav>

	<c:if test="<%= msbFragmentDisplayContext.isShowMSBFragmentEntriesSearch() %>">
		<portlet:renderURL var="portletURL">
			<portlet:param name="mvcPath" value="/view_fragment_entries.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="msbFragmentCollectionId" value="<%= String.valueOf(msbFragmentDisplayContext.getMSBFragmentCollectionId()) %>" />
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
	disabled="<%= msbFragmentDisplayContext.isDisabledMSBFragmentEntriesManagementBar() %>"
	includeCheckBox="<%= true %>"
	searchContainerId="msbFragmentEntries"
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
		<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteSelectedMSBFragmentEntries" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="deleteMSBFragmentEntries" var="deleteMSBFragmentEntriesURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteMSBFragmentEntriesURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="msbFragmentEntries"
		searchContainer="<%= msbFragmentDisplayContext.getMSBFragmentEntriesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.modern.site.building.fragment.model.MSBFragmentEntry"
			keyProperty="msbFragmentEntryId"
			modelVar="msbFragmentEntry"
		>

			<%
			row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
			%>

			<liferay-ui:search-container-column-text>
				<liferay-frontend:icon-vertical-card
					actionJsp="/msb_fragment_entry_action.jsp"
					actionJspServletContext="<%= application %>"
					cssClass="entry-display-style"
					icon="page"
					resultRow="<%= row %>"
					rowChecker="<%= searchContainer.getRowChecker() %>"
					title="<%= msbFragmentEntry.getName() %>"
				>
					<liferay-frontend:vertical-card-header>
						<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - msbFragmentEntry.getCreateDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
					</liferay-frontend:vertical-card-header>
				</liferay-frontend:icon-vertical-card>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= msbFragmentDisplayContext.getDisplayStyle() %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= msbFragmentDisplayContext.isShowAddButton(MSBFragmentActionKeys.ADD_MSB_FRAGMENT_ENTRY) %>">
	<portlet:actionURL name="addMSBFragmentEntry" var="addMSBFragmentEntryURL">
		<portlet:param name="mvcPath" value="/edit_msb_fragment_entry.jsp" />
		<portlet:param name="msbFragmentCollectionId" value="<%= String.valueOf(msbFragmentDisplayContext.getMSBFragmentCollectionId()) %>" />
	</portlet:actionURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item id="addMSBFragmentEntryMenuItem" title='<%= LanguageUtil.get(request, "add-fragment") %>' url="<%= addMSBFragmentEntryURL.toString() %>" />
	</liferay-frontend:add-menu>

	<aui:script require="modern-site-building-fragment-web/js/MSBFragmentNameEditor">
		var addMSBFragmentEntryMenuItem = document.getElementById('<portlet:namespace />addMSBFragmentEntryMenuItem');

		addMSBFragmentEntryMenuItem.addEventListener(
			'click',
			function(event) {
				event.preventDefault();

				var msbFragmentNameEditor = new modernSiteBuildingFragmentWebJsMSBFragmentNameEditor.default(
					{
						addMSBFragmentEntryURL: '<%= addMSBFragmentEntryURL.toString() %>',
						editMSBFragmentEntryURL: '<portlet:renderURL><portlet:param name="mvcPath" value="/edit_msb_fragment_entry.jsp" /><portlet:param name="msbFragmentCollectionId" value="<%= String.valueOf(msbFragmentDisplayContext.getMSBFragmentCollectionId()) %>" /></portlet:renderURL>',
						events: {
							hide: function() {
								msbFragmentNameEditor.disposeInternal();
							}
						},
						namespace: '<portlet:namespace />',
						spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
					}
				);
			}
		);
	</aui:script>
</c:if>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />deleteSelectedMSBFragmentEntries').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm($(document.<portlet:namespace />fm));
			}
		}
	);
</aui:script>