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
CommerceRegionsDisplayContext commerceRegionsDisplayContext = (CommerceRegionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceRegion> commerceRegionSearchContainer = commerceRegionsDisplayContext.getSearchContainer();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceRegions"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= commerceRegionsDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceRegionsDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceRegionsDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"name", "priority"} %>'
			portletURL="<%= commerceRegionsDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceRegionsDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceRegions();" %>' icon="trash" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />commerceRegionsContainer">
	<aui:form action="<%= commerceRegionsDisplayContext.getPortletURL() %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCommerceRegionIds" type="hidden" />

		<div class="commerce-regions-container" id="<portlet:namespace />entriesContainer">
			<liferay-ui:search-container
				id="commerceRegions"
				searchContainer="<%= commerceRegionSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.commerce.address.model.CommerceRegion"
					keyProperty="commerceRegionId"
				>
					<liferay-ui:search-container-column-text
						property="name"
					/>

					<liferay-ui:search-container-column-text
						property="abbreviation"
					/>

					<liferay-ui:search-container-column-text
						property="active"
					/>

					<liferay-ui:search-container-column-text
						property="priority"
					/>

					<liferay-ui:search-container-column-jsp
						path="/region_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" searchContainer="<%= commerceRegionSearchContainer %>" />
			</liferay-ui:search-container>
		</div>
	</aui:form>
</div>

<portlet:renderURL var="addCommerceRegionURL">
	<portlet:param name="mvcRenderCommandName" value="editCommerceRegion" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="commerceCountryId" value="<%= String.valueOf(commerceRegionsDisplayContext.getCommerceCountryId()) %>" />
</portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-region") %>' url="<%= addCommerceRegionURL.toString() %>" />
</liferay-frontend:add-menu>

<aui:script>
	function <portlet:namespace />deleteCommerceRegions() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-regions") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCommerceRegionIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editCommerceRegion" />');
		}
	}
</aui:script>