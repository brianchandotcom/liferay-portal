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
	componentId="segmentsEntryOrganizationsManagementToolbar"
	disabled="<%= editSegmentsEntryDisplayContext.getOrganizationTotalItems() == 0 %>"
	itemsTotal="<%= editSegmentsEntryDisplayContext.getOrganizationTotalItems() %>"
	searchContainerId="segmentsEntryOrganizations"
	selectable="<%= true %>"
	showCreationMenu="<%= true %>"
	showSearch="<%= false %>"
/>

<portlet:actionURL name="deleteSegmentsEntryOrganization" var="deleteSegmentsEntryOrganizationURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<portlet:renderURL var="editSegmentsEntryOrganizationsRenderURL">
	<portlet:param name="mvcRenderCommandName" value="editSegmentsEntryOrganizations" />
	<portlet:param name="tabs1" value="organizations" />
	<portlet:param name="backURL" value="<%= backURL %>" />
	<portlet:param name="segmentsEntryId" value="<%= String.valueOf(segmentsEntryId) %>" />
</portlet:renderURL>

<aui:form action="<%= deleteSegmentsEntryOrganizationURL %>" cssClass="container-fluid-1280" method="post" name="fmSegmentsEntryOrganizations">
	<aui:input name="redirect" type="hidden" value="<%= editSegmentsEntryOrganizationsRenderURL %>" />
	<aui:input name="tabs1" type="hidden" value="organizations" />
	<aui:input name="segmentsEntryId" type="hidden" value="<%= segmentsEntryId %>" />

	<liferay-ui:search-container
		searchContainer="<%= editSegmentsEntryDisplayContext.getOrganizationSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Organization"
			escapedModel="<%= true %>"
			keyProperty="organizationId"
			modelVar="organization"
		>
			<liferay-ui:search-container-column-text
				name="name"
				property="name"
				truncate="<%= true %>"
			/>

			<liferay-ui:search-container-column-text
				name="parent-organization"
				truncate="<%= true %>"
				value="<%= HtmlUtil.escape(organization.getParentOrganizationName()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="type"
				orderable="<%= true %>"
				value="<%= LanguageUtil.get(request, organization.getType()) %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<portlet:actionURL name="addSegmentsEntryOrganization" var="addSegmentsEntryOrganizationURL" />

<aui:form action="<%= addSegmentsEntryOrganizationURL %>" cssClass="hide" method="post" name="addSegmentsEntryOrganizationFm">
	<aui:input name="redirect" type="hidden" value="<%= editSegmentsEntryOrganizationsRenderURL %>" />
	<aui:input name="tabs1" type="hidden" value="organizations" />
	<aui:input name="segmentsEntryId" type="hidden" value="<%= segmentsEntryId %>" />
</aui:form>

<aui:script use="liferay-item-selector-dialog,liferay-portlet-url">
	var addSegmentsEntryOrganizations = function(event) {
		event.preventDefault();

		var itemSelectorDialog = new A.LiferayItemSelectorDialog(
			{
				eventName: '<portlet:namespace />selectSegmentsEntryOrganizations',
				on: {
					selectedItemChange: function(event) {
						var selectedItem = event.newVal;

						if (selectedItem) {
							var addSegmentsEntryOrganizationFm = $(document.<portlet:namespace />addSegmentsEntryOrganizationFm);

							addSegmentsEntryOrganizationFm.append(selectedItem);

							submitForm(addSegmentsEntryOrganizationFm);
						}
					}
				},
				'strings.add': '<liferay-ui:message key="done" />',
				title: '<liferay-ui:message key="assign-organizations-to-this-segment" />',
				url: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcRenderCommandName" value="selectSegmentsEntryOrganizations" /><portlet:param name="segmentsEntryId" value="<%= String.valueOf(segmentsEntryId) %>" /></portlet:renderURL>'
			}
		);

		itemSelectorDialog.open();
	}

	var deleteSegmentsEntryOrganizations = function() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			submitForm(document.querySelector('#<portlet:namespace />fmSegmentsEntryOrganizations'));
		}
	};

	var ACTIONS = {
		'deleteSegmentsEntryOrganizations': deleteSegmentsEntryOrganizations
	};

	Liferay.componentReady('segmentsEntryOrganizationsManagementToolbar').then(
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
			managementToolbar.on('creationButtonClicked', addSegmentsEntryOrganizations);
		}
	);
</aui:script>