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
CommerceWishListContentDisplayContext commerceWishListContentDisplayContext = (CommerceWishListContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceCart commerceCart = commerceWishListContentDisplayContext.getCommerceCart();

long commerceCartId = commerceWishListContentDisplayContext.getCommerceCartId();

SearchContainer<CommerceCartItem> commerceCartItemSearchContainer = commerceWishListContentDisplayContext.getSearchContainer();

PortletURL portletURL = commerceWishListContentDisplayContext.getPortletURL();
%>

<c:if test="<%= commerceCart != null %>">
	<div class="container-fluid-1280" id="<portlet:namespace />wishListItemsContainer">
		<h3><%= commerceCart.getName() %></h3>

		<aui:button name="editWishListButton" value="edit" />

		<div class="commerce-wish-list-items-container" id="<portlet:namespace />entriesContainer">
			<liferay-ui:search-container
				id="commerceCartItems"
				iteratorURL="<%= portletURL %>"
				searchContainer="<%= commerceCartItemSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.commerce.model.CommerceCartItem"
					cssClass="entry-display-style"
					keyProperty="CommerceCartItemId"
					modelVar="commerceCartItem"
				>

					<%
						CPDefinition cpDefinition = commerceCartItem.getCPDefinition();

						String thumbnailSrc = cpDefinition.getDefaultImageThumbnailSrc(themeDisplay);

					List<KeyValuePair> keyValuePairs = commerceWishListContentDisplayContext.getKeyValuePairs(commerceCartItem.getJson(), locale);

						StringJoiner stringJoiner = new StringJoiner(StringPool.COMMA);

						for (KeyValuePair keyValuePair : keyValuePairs) {
							stringJoiner.add(keyValuePair.getValue());
						}
					%>

					<liferay-ui:search-container-column-image
						name="product"
						src="<%= thumbnailSrc %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="description"
					>
						<a href="<%= commerceWishListContentDisplayContext.getCPDefinitionURL(cpDefinition.getCPDefinitionId(), themeDisplay) %>">
							<%= HtmlUtil.escape(cpDefinition.getTitle(languageId)) %>
						</a>

						<h6 class="text-default">
							<%= HtmlUtil.escape(stringJoiner.toString()) %>
						</h6>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="price"
						value="<%= commerceWishListContentDisplayContext.getFormattedPrice(commerceCartItem) %>"
					/>

					<liferay-ui:search-container-column-text>
						<portlet:actionURL name="editCommerceCartItem" var="deleteURL">
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="commerceCartItemId" value="<%= String.valueOf(commerceCartItem.getCommerceCartItemId()) %>" />
							<portlet:param name="type" value="<%= String.valueOf(CommerceCartConstants.TYPE_CART) %>" />
						</portlet:actionURL>

						<liferay-ui:icon-delete
							label="<%= true %>"
							url="<%= deleteURL %>"
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						colspan="<%= 2 %>"
						path="/wish_list/wish_list_item_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" searchContainer="<%= commerceCartItemSearchContainer %>" />
			</liferay-ui:search-container>
		</div>
	</div>

	<liferay-portlet:renderURL var="editWishListURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcRenderCommandName" value="editCommerceWishList" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="commerceCartId" value="<%= String.valueOf(commerceCartId) %>" />
	</liferay-portlet:renderURL>

	<aui:script use="aui-base">
		A.one('#<portlet:namespace />editWishListButton').on(
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
						title: '<liferay-ui:message key="edit-wish-list" />',
						uri: '<%= editWishListURL.toString() %>'
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
</c:if>