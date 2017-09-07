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
CPAvailabilityRangeDisplayContext cpAvailabilityRangeDisplayContext = (CPAvailabilityRangeDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CPAvailabilityRange> cpAvailabilityRangeSearchContainer = cpAvailabilityRangeDisplayContext.getSearchContainer();

boolean hasManageCPAvailabilityRangesPermission = CPAvailabilityRangePermission.contains(permissionChecker, scopeGroupId, CPActionKeys.MANAGE_COMMERCE_PRODUCT_AVAILABILITY_RANGES);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpAvailabilityRanges"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= cpAvailabilityRangeDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= cpAvailabilityRangeDisplayContext.getOrderByCol() %>"
			orderByType="<%= cpAvailabilityRangeDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"title"} %>'
			portletURL="<%= cpAvailabilityRangeDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= cpAvailabilityRangeDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>

	<c:if test="<%= hasManageCPAvailabilityRangesPermission %>">
		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCPAvailabilityRanges();" %>' icon="trash" label="delete" />
		</liferay-frontend:management-bar-action-buttons>
	</c:if>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<portlet:actionURL name="editCPAvailabilityRange" var="editCPAvailabilityRangeActionURL" />

	<aui:form action="<%= editCPAvailabilityRangeActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCPAvailabilityRangeIds" type="hidden" />

		<liferay-ui:search-container
			id="cpAvailabilityRanges"
			searchContainer="<%= cpAvailabilityRangeSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.product.model.CPAvailabilityRange"
				keyProperty="CPAvailabilityRangeId"
				modelVar="cpAvailabilityRange"
			>

				<%
				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcRenderCommandName", "editCPAvailabilityRange");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("cpAvailabilityRangeId", String.valueOf(cpAvailabilityRange.getCPAvailabilityRangeId()));
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					href="<%= rowURL %>"
					name="title"
					value="<%= HtmlUtil.escape(cpAvailabilityRange.getTitle(languageId)) %>"
				/>

				<liferay-ui:search-container-column-date
					cssClass="table-cell-content"
					name="create-date"
					property="createDate"
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

			<liferay-ui:search-iterator markupView="lexicon" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<c:if test="<%= hasManageCPAvailabilityRangesPermission %>">
	<portlet:renderURL var="addCPAvailabilityRangeURL">
		<portlet:param name="mvcRenderCommandName" value="editCPAvailabilityRange" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-availability-range") %>' url="<%= addCPAvailabilityRangeURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script>
	function <portlet:namespace />deleteCPAvailabilityRanges() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-availability-ranges") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('deleteCPAvailabilityRangeIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}
</aui:script>