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
CommerceWishListsDisplayContext commerceWishListsDisplayContext = (CommerceWishListsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceCart> commerceCartSearchContainer = commerceWishListsDisplayContext.getSearchContainer();

PortletURL portletURL = commerceWishListsDisplayContext.getPortletURL();
%>

<div class="container-fluid-1280" id="<portlet:namespace />wishListsContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCommerceWishListIds" type="hidden" />

		<div class="commerce-wish-lists-container" id="<portlet:namespace />entriesContainer">
			<liferay-ui:search-container
				id="commerceWishLists"
				iteratorURL="<%= portletURL %>"
				searchContainer="<%= commerceCartSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.commerce.model.CommerceCart"
					cssClass="entry-display-style"
					keyProperty="CommerceCartId"
					modelVar="commerceCart"
				>
					<portlet:actionURL name="editCommerceWishList" var="rowURL">
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.VIEW %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="commerceCartId" value="<%= String.valueOf(commerceCart.getCommerceCartId()) %>" />
					</portlet:actionURL>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						href="<%= rowURL %>"
						property="name"
					/>

					<liferay-ui:search-container-column-text>
						<portlet:actionURL name="editCommerceWishList" var="deleteURL">
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="commerceCartId" value="<%= String.valueOf(commerceCart.getCommerceCartId()) %>" />
						</portlet:actionURL>

						<liferay-ui:icon-delete
							label="<%= true %>"
							url="<%= deleteURL %>"
						/>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" searchContainer="<%= commerceCartSearchContainer %>" />
			</liferay-ui:search-container>
		</div>

		<aui:button name="addWishListButton" value="add" />
	</aui:form>
</div>

<liferay-portlet:renderURL var="addWishListURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="editCommerceWishList" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</liferay-portlet:renderURL>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />addWishListButton').on(
		'click',
		function(event) {
			Liferay.Util.openWindow(
				{
					dialog: {
						centered: true,
						destroyOnClose: true,
						height: 300,
						modal: true,
						width: 300
					},
					dialogIframe: {
						bodyCssClass: 'dialog-with-footer'
					},
					id: 'editWishListDialog',
					title: '<liferay-ui:message key="add-wish-list" />',
					uri: '<%= addWishListURL.toString() %>'
				}
			);
		}
	);

	Liferay.provide(
		window,
		'refreshPortlet',
		function() {
			var curPortlet = '#p_p_id<portlet:namespace/>';

			Liferay.Portlet.refresh(curPortlet);
		},
		['aui-dialog','aui-dialog-iframe']
	);

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