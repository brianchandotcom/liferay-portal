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
CommerceOrganizationAddressesDisplayContext commerceOrganizationAddressesDisplayContext = (CommerceOrganizationAddressesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceAddresses"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= commerceOrganizationAddressesDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceOrganizationAddressesDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceOrganizationAddressesDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= commerceOrganizationAddressesDisplayContext.getPortletURL() %>"
		/>

		<li>
			<aui:form action="<%= String.valueOf(commerceOrganizationAddressesDisplayContext.getPortletURL()) %>" name="searchFm">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceOrganizationAddressesDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>

		<c:if test="<%= commerceOrganizationAddressesDisplayContext.hasManageCommerceAddressPermission() %>">
			<liferay-frontend:add-menu>
				<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-address") %>' url="<%= commerceOrganizationAddressesDisplayContext.getAddCommerceAddressHref() %>" />
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<c:if test="<%= commerceOrganizationAddressesDisplayContext.hasManageCommerceAddressPermission() %>">
		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceAddresses();" %>' icon="times" label="delete" />
		</liferay-frontend:management-bar-action-buttons>
	</c:if>
</liferay-frontend:management-bar>

<div class="commerce-addresses-container" id="<portlet:namespace />entriesContainer">
	<portlet:actionURL name="editCommerceAddress" var="editCommerceAddressActionURL" />

	<aui:form action="<%= editCommerceAddressActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCommerceAddressIds" type="hidden" />

		<liferay-ui:search-container
			id="commerceAddresses"
			searchContainer="<%= commerceOrganizationAddressesDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.model.CommerceAddress"
				cssClass="entry-display-style"
				keyProperty="commerceAddressId"
				modelVar="commerceAddress"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					href="<%= commerceOrganizationAddressesDisplayContext.getEditCommerceAddressHref(commerceAddress.getCommerceAddressId()) %>"
					property="name"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					property="street1"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					property="city"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					property="zip"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/address_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator markupView="lexicon" searchContainer="<%= commerceOrganizationAddressesDisplayContext.getSearchContainer() %>" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace/>editCommerceAddress(title, uri) {
		Liferay.Util.openWindow(
			{
				dialog: {
					centered: true,
					destroyOnClose: true,
					height: 800,
					modal: true,
					width: 900
				},
				dialogIframe: {
					bodyCssClass: 'dialog-with-footer'
				},
				id: 'editCommerceAddressDialog',
				title: title,
				uri: uri
			}
		);
	}

	function <portlet:namespace />deleteCommerceAddresses() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-addresses" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('deleteCommerceAddressIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}

	Liferay.provide(
		window,
		'closePopup',
		function(dialogId) {
			var dialog = Liferay.Util.Window.getById(dialogId);

			dialog.destroy();
		},
		['liferay-util-window']
	);
</aui:script>