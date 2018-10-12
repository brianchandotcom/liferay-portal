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

<%
SegmentsDisplayContext segmentsDisplayContext = (SegmentsDisplayContext)request.getAttribute(SegmentsWebKeys.SEGMENTS_DISPLAY_CONTEXT);
%>

<%@ include file="/init.jsp" %>

<clay:management-toolbar
	actionDropdownItems="<%= segmentsDisplayContext.getActionDropdownItems() %>"
	componentId="segmentsEntriesManagementToolbar"
	creationMenu="<%= segmentsDisplayContext.getCreationMenu() %>"
	disabled="<%= segmentsDisplayContext.getTotalItems() == 0 %>"
	itemsTotal="<%= segmentsDisplayContext.getTotalItems() %>"
	searchContainerId="segmentsEntries"
	selectable="<%= true %>"
	showSearch="<%= false %>"
/>

<portlet:actionURL name="deleteSegmentsEntry" var="deleteSegmentsEntryURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteSegmentsEntryURL %>" cssClass="container-fluid-1280" method="post" name="fmSegmentsEntries">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<liferay-ui:search-container
		id="segmentsEntries"
		searchContainer="<%= segmentsDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.segments.model.SegmentsEntry"
			keyProperty="segmentsEntryId"
			modelVar="segmentsEntry"
		>
			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcRenderCommandName" value="editSegmentsEntry" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="segmentsEntryId" value="<%= String.valueOf(segmentsEntry.getSegmentsEntryId()) %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				cssClass="important table-cell-content"
				href="<%= rowURL %>"
				name="name"
				value="<%= HtmlUtil.escape(segmentsEntry.getName(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="type"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="active"
				value='<%= LanguageUtil.get(request, segmentsEntry.isActive() ? "yes" : "no") %>'
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/segments_entry_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script sandbox="<%= true %>">
	var deleteSegmentsEntries = function() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			submitForm(document.querySelector('#<portlet:namespace />fmSegmentsEntries'));
		}
	};

	var ACTIONS = {
		'deleteSegmentsEntries': deleteSegmentsEntries
	};

	Liferay.componentReady('segmentsEntriesManagementToolbar').then(
		function(managementToolbar) {
			managementToolbar.on(
				'actionItemClicked',
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