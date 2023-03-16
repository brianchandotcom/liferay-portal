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
LayoutUtilityPageEntryDisplayContext layoutUtilityPageEntryDisplayContext = new LayoutUtilityPageEntryDisplayContext(renderRequest, renderResponse);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new LayoutUtilityPageEntryManagementToolbarDisplayContext(request, layoutUtilityPageEntryDisplayContext, liferayPortletRequest, liferayPortletResponse) %>"
	propsTransformer="js/LayoutUtilityPageEntriesManagementToolbarPropsTransformer"
/>

<aui:form cssClass="container-fluid container-fluid-max-xl container-view" name="fm">
	<liferay-ui:success key="layoutPublished" message="the-utility-page-was-successfully-published" />

	<liferay-ui:success key="layoutUtilityPageDeleted" message='<%= GetterUtil.getString(MultiSessionMessages.get(renderRequest, "layoutUtilityPageDeleted")) %>' />

	<liferay-ui:search-container
		id="entries"
		searchContainer="<%= layoutUtilityPageEntryDisplayContext.getLayoutUtilityPageEntrySearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.layout.utility.page.model.LayoutUtilityPageEntry"
			keyProperty="layoutUtilityPageEntryId"
			modelVar="layoutUtilityPageEntry"
		>

			<%
			row.setData(
				HashMapBuilder.<String, Object>put(
					"actions", layoutUtilityPageEntryDisplayContext.getAvailableActions(layoutUtilityPageEntry)
				).build());
			%>

			<liferay-ui:search-container-column-text>
				<clay:vertical-card
					propsTransformer="js/LayoutUtilityPageEntryDropdownPropsTransformer"
					verticalCard="<%= new LayoutUtilityPageEntryVerticalCard(layoutUtilityPageEntry, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<portlet:actionURL name="/layout_admin/update_layout_utility_page_entry_preview" var="updateLayoutUtilityPageEntryPreviewURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= updateLayoutUtilityPageEntryPreviewURL %>" name="layoutUtilityPageEntryPreviewFm">
	<aui:input name="layoutUtilityPageEntryId" type="hidden" />
	<aui:input name="fileEntryId" type="hidden" />
</aui:form>