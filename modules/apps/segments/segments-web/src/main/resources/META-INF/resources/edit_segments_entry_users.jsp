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
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

EditSegmentsEntryDisplayContext editSegmentsEntryDisplayContext = (EditSegmentsEntryDisplayContext)request.getAttribute(SegmentsWebKeys.EDIT_SEGMENTS_ENTRY_DISPLAY_CONTEXT);

long segmentsEntryId = editSegmentsEntryDisplayContext.getSegmentsEntryId();

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}

renderResponse.setTitle(editSegmentsEntryDisplayContext.getSegmentsEntryName(locale));
%>

<liferay-util:include page="/edit_segments_entry_tabs.jsp" servletContext="<%= application %>" />

<clay:management-toolbar
	actionDropdownItems="<%= editSegmentsEntryDisplayContext.getActionDropdownItems() %>"
	componentId="segmentsEntryUsersManagementToolbar"
	disabled="<%= editSegmentsEntryDisplayContext.getUserTotalItems() == 0 %>"
	itemsTotal="<%= editSegmentsEntryDisplayContext.getUserTotalItems() %>"
	searchContainerId="segmentsEntryUsers"
	selectable="<%= editSegmentsEntryDisplayContext.isSelectable() %>"
	showCreationMenu="<%= editSegmentsEntryDisplayContext.showCreationMenu() %>"
	showSearch="<%= false %>"
/>

<portlet:actionURL name="deleteSegmentsEntryUser" var="deleteSegmentsEntryUserURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<portlet:renderURL var="editSegmentsEntryUsersRenderURL">
	<portlet:param name="mvcRenderCommandName" value="editSegmentsEntryUsers" />
	<portlet:param name="tabs1" value="users" />
	<portlet:param name="backURL" value="<%= backURL %>" />
	<portlet:param name="segmentsEntryId" value="<%= String.valueOf(segmentsEntryId) %>" />
</portlet:renderURL>

<aui:form action="<%= deleteSegmentsEntryUserURL %>" cssClass="container-fluid-1280" method="post" name="fmSegmentsEntryUsers">
	<aui:input name="redirect" type="hidden" value="<%= editSegmentsEntryUsersRenderURL %>" />
	<aui:input name="tabs1" type="hidden" value="users" />
	<aui:input name="segmentsEntryId" type="hidden" value="<%= segmentsEntryId %>" />

	<liferay-ui:search-container
		searchContainer="<%= editSegmentsEntryDisplayContext.getUserSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.User"
			escapedModel="<%= true %>"
			keyProperty="userId"
			modelVar="user2"
			rowIdProperty="screenName"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200 table-title"
				name="name"
				value="<%= user2.getFullName() %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200"
				name="screen-name"
				orderable="<%= true %>"
				property="screenName"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<portlet:actionURL name="addSegmentsEntryUser" var="addSegmentsEntryUserURL" />

<aui:form action="<%= addSegmentsEntryUserURL %>" cssClass="hide" method="post" name="addSegmentsEntryUserFm">
	<aui:input name="redirect" type="hidden" value="<%= editSegmentsEntryUsersRenderURL %>" />
	<aui:input name="tabs1" type="hidden" value="users" />
	<aui:input name="segmentsEntryId" type="hidden" value="<%= segmentsEntryId %>" />
</aui:form>

<aui:script use="liferay-item-selector-dialog,liferay-portlet-url">
	var addSegmentsEntryUsers = function(event) {
		event.preventDefault();

		var itemSelectorDialog = new A.LiferayItemSelectorDialog(
			{
				eventName: '<portlet:namespace />selectSegmentsEntryUsers',
				on: {
					selectedItemChange: function(event) {
						var selectedItem = event.newVal;

						if (selectedItem) {
							var addSegmentsEntryUserFm = $(document.<portlet:namespace />addSegmentsEntryUserFm);

							addSegmentsEntryUserFm.append(selectedItem);

							submitForm(addSegmentsEntryUserFm);
						}
					}
				},
				'strings.add': '<liferay-ui:message key="done" />',
				title: '<liferay-ui:message key="assign-users-to-this-segment" />',
				url: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcRenderCommandName" value="selectSegmentsEntryUsers" /><portlet:param name="segmentsEntryId" value="<%= String.valueOf(segmentsEntryId) %>" /></portlet:renderURL>'
			}
		);

		itemSelectorDialog.open();
	}

	var deleteSegmentsEntryUsers = function() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			submitForm(document.querySelector('#<portlet:namespace />fmSegmentsEntryUsers'));
		}
	};

	var ACTIONS = {
		'deleteSegmentsEntryUsers': deleteSegmentsEntryUsers
	};

	Liferay.componentReady('segmentsEntryUsersManagementToolbar').then(
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
			managementToolbar.on('creationButtonClicked', addSegmentsEntryUsers);
		}
	);
</aui:script>