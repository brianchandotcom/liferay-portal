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
String searchContainerId = ParamUtil.getString(request, "searchContainerId", "commercePriceLists");

CommercePriceListDisplayContext commercePriceListDisplayContext = (CommercePriceListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="<%= searchContainerId %>"
>
	<liferay-frontend:management-bar-buttons>
		<c:if test="<%= commercePriceListDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commercePriceListDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>

		<c:if test="<%= commercePriceListDisplayContext.hasPermission(CommercePriceListActionKeys.ADD_COMMERCE_PRICE_LIST) %>">
			<liferay-portlet:renderURL var="addProductDefinitionURL">
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
				<portlet:param name="mvcRenderCommandName" value="editCommercePriceList" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</liferay-portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "add-price-list") %>'
					url="<%= addProductDefinitionURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= commercePriceListDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commercePriceListDisplayContext.getOrderByCol() %>"
			orderByType="<%= commercePriceListDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date", "display-date", "priority"} %>'
			portletURL="<%= commercePriceListDisplayContext.getPortletURL() %>"
		/>

		<li>
			<liferay-commerce:search-input
				actionURL="<%= commercePriceListDisplayContext.getPortletURL() %>"
				formName="searchFm"
			/>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<c:if test="<%= commercePriceListDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + liferayPortletResponse.getNamespace() + "deleteCommercePriceLists();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:script>
	function <portlet:namespace />deleteCommercePriceLists() {
		if (
			confirm(
				'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-price-lists" />'
			)
		) {
			var form = window.document['<portlet:namespace />fm'];

			form.setAttribute('method', 'post');
			form['<portlet:namespace /><%= Constants.CMD %>'].value =
				'<%= Constants.DELETE %>';
			form[
				'<portlet:namespace />deleteCommercePriceListIds'
			].value = Liferay.Util.listCheckedExcept(
				form,
				'<portlet:namespace />allRowIds'
			);

			submitForm(form, '<portlet:actionURL name="editCommercePriceList" />');
		}
	}
</aui:script>