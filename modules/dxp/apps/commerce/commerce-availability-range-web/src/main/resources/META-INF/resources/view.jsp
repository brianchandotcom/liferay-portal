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
CommerceAvailabilityRangeDisplayContext commerceAvailabilityRangeDisplayContext = (CommerceAvailabilityRangeDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceAvailabilityRange> commerceAvailabilityRangeSearchContainer = commerceAvailabilityRangeDisplayContext.getSearchContainer();

boolean hasManageCommerceAvailabilityRangesPermission = CommercePermission.contains(permissionChecker, scopeGroupId, CommerceActionKeys.MANAGE_COMMERCE_AVAILABILITY_RANGES);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceAvailabilityRanges"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= commerceAvailabilityRangeDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceAvailabilityRangeDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceAvailabilityRangeDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"priority"} %>'
			portletURL="<%= commerceAvailabilityRangeDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceAvailabilityRangeDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>

		<c:if test="<%= hasManageCommerceAvailabilityRangesPermission %>">
			<portlet:renderURL var="addCommerceAvailabilityRangeURL">
				<portlet:param name="mvcRenderCommandName" value="editCommerceAvailabilityRange" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "add-availability-range") %>'
					url="<%= addCommerceAvailabilityRangeURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<c:if test="<%= hasManageCommerceAvailabilityRangesPermission %>">
		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceAvailabilityRanges();" %>'
				icon="trash"
				label="delete"
			/>
		</liferay-frontend:management-bar-action-buttons>
	</c:if>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<portlet:actionURL name="editCommerceAvailabilityRange" var="editCommerceAvailabilityRangeActionURL" />

	<aui:form action="<%= editCommerceAvailabilityRangeActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCommerceAvailabilityRangeIds" type="hidden" />

		<liferay-ui:search-container
			id="commerceAvailabilityRanges"
			searchContainer="<%= commerceAvailabilityRangeSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.model.CommerceAvailabilityRange"
				keyProperty="commerceAvailabilityRangeId"
				modelVar="commerceAvailabilityRange"
			>

				<%
				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcRenderCommandName", "editCommerceAvailabilityRange");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("commerceAvailabilityRangeId", String.valueOf(commerceAvailabilityRange.getCommerceAvailabilityRangeId()));
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					href="<%= rowURL %>"
					name="title"
					value="<%= HtmlUtil.escape(commerceAvailabilityRange.getTitle(languageId)) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					property="priority"
				/>

				<liferay-ui:search-container-column-date
					cssClass="table-cell-content"
					name="modified-date"
					property="modifiedDate"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/availability_range_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />deleteCommerceAvailabilityRanges() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-availability-ranges" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('deleteCommerceAvailabilityRangeIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}
</aui:script>