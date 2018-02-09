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
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all-cart-items");

String cartToolbarItem = ParamUtil.getString(request, "cartToolbarItem", "view-all-carts");

String languageId = LanguageUtil.getLanguageId(locale);

CommerceCartItemDisplayContext commerceCartItemDisplayContext = (CommerceCartItemDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceCart commerceCart = commerceCartItemDisplayContext.getCommerceCart();

SearchContainer<CommerceCartItem> commerceCartItemSearchContainer = commerceCartItemDisplayContext.getSearchContainer();

String displayStyle = commerceCartItemDisplayContext.getDisplayStyle();

PortletURL portletURL = commerceCartItemDisplayContext.getPortletURL();

String lifecycle = (String)request.getAttribute(liferayPortletRequest.LIFECYCLE_PHASE);

PortletURL cartAdminURLObj = PortalUtil.getControlPanelPortletURL(request, CommercePortletKeys.COMMERCE_CART, lifecycle);

cartAdminURLObj.setParameter("toolbarItem", cartToolbarItem);

String title = commerceCart.getName();

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

String cartToolbarItemLabel = "carts";

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, cartToolbarItemLabel), cartAdminURLObj.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, title, portletURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "cart-items"), StringPool.BLANK, data);

portletURL.setParameter("toolbarItem", toolbarItem);
portletURL.setParameter("searchContainerId", "commerceCartItems");

request.setAttribute("view.jsp-portletURL", portletURL);

renderResponse.setTitle(LanguageUtil.get(request, "cart"));
%>

<%@ include file="/breadcrumb.jspf" %>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= commerceCartItemDisplayContext.getNavigationItems() %>"
/>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceCartItems"
>
	<liferay-frontend:management-bar-buttons>
		<c:if test="<%= commerceCartItemDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceCartItemDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="<%= commerceCartItemDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= commerceCartItemDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceCartItemDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceCartItemDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"modified-date"} %>'
			portletURL="<%= commerceCartItemDisplayContext.getPortletURL() %>"
		/>

		<li>
			<aui:form action="<%= portletURL.toString() %>" name="searchFm">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<c:if test="<%= commerceCartItemDisplayContext.isShowInfoPanel() %>">
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</c:if>

		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceCartItems();" %>' icon="times" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div id="<portlet:namespace />CartItemsContainer">
	<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<c:if test="<%= commerceCartItemDisplayContext.isShowInfoPanel() %>">
			<liferay-portlet:resourceURL
				copyCurrentRenderParameters="<%= false %>"
				id="commerceCartItemInfoPanel"
				var="sidebarPanelURL"
			/>

			<liferay-frontend:sidebar-panel
				resourceURL="<%= sidebarPanelURL %>"
				searchContainerId="commerceCartItems"
			>
				<liferay-util:include page="/cart_item_info_panel.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>
		</c:if>

		<div class="sidenav-content">
			<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="deleteCommerceCartItemIds" type="hidden" />

				<div class="cart-items-container" id="<portlet:namespace />entriesContainer">
					<liferay-ui:search-container
						id="commerceCartItems"
						iteratorURL="<%= portletURL %>"
						searchContainer="<%= commerceCartItemSearchContainer %>"
					>
						<liferay-ui:search-container-row
							className="com.liferay.commerce.model.CommerceCartItem"
							cssClass="entry-display-style"
							keyProperty="commerceCartItemId"
							modelVar="commerceCartItem"
						>

							<%
							CPDefinition cpDefinition = commerceCartItem.getCPDefinition();

							String thumbnailSrc = StringPool.BLANK;

							if (cpDefinition != null) {
								thumbnailSrc = cpDefinition.getDefaultImageThumbnailSrc(themeDisplay);
							}

							PortletURL rowURL = renderResponse.createRenderURL();

							rowURL.setParameter("mvcRenderCommandName", "editCommerceCartItem");
							rowURL.setParameter("redirect", currentURL);
							rowURL.setParameter("cartToolbarItem", cartToolbarItem);
							rowURL.setParameter("commerceCartId", String.valueOf(commerceCartItem.getCommerceCartId()));
							rowURL.setParameter("commerceCartItemId", String.valueOf(commerceCartItem.getCommerceCartItemId()));
							%>

							<liferay-ui:search-container-column-image
								cssClass="table-cell-content"
								name="product"
								src="<%= thumbnailSrc %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								href="<%= rowURL %>"
								name="title"
								value="<%= HtmlUtil.escape(cpDefinition.getTitle(languageId)) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="quantity"
								property="quantity"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="price"
								value="<%= commerceCartItemDisplayContext.getFormattedPrice(commerceCartItem) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-content"
								name="user"
								property="userName"
							/>

							<liferay-ui:search-container-column-jsp
								cssClass="entry-action-column"
								path="/cart_item_action.jsp"
							/>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" searchContainer="<%= commerceCartItemSearchContainer %>" />
					</liferay-ui:search-container>
				</div>
			</aui:form>
		</div>
	</div>
</div>

<portlet:actionURL name="editCommerceCartItem" var="addCommerceCartItemURL" />

<aui:form action="<%= addCommerceCartItemURL %>" cssClass="hide" name="addCommerceCartItemFm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD_MULTIPLE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceCartId" type="hidden" value="<%= commerceCart.getCommerceCartId() %>" />
	<aui:input name="cpInstanceIds" type="hidden" value="" />
</aui:form>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item id="addCommerceCartItem" title='<%= LanguageUtil.get(request, "add-item") %>' url="javascript:;" />
</liferay-frontend:add-menu>

<aui:script>
	function <portlet:namespace />deleteCommerceCartItems() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-items") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCommerceCartItemIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editCommerceCartItem" />');
		}
	}
</aui:script>

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />addCommerceCartItem').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'productInstancesSelectItem',
					on: {
						selectedItemChange: function(event) {
							var selectedItems = event.newVal;

							if (selectedItems) {
								$('#<portlet:namespace />cpInstanceIds').val(selectedItems);

								var addCommerceCartItemFm = $('#<portlet:namespace />addCommerceCartItemFm');

								submitForm(addCommerceCartItemFm);
							}
						}
					},
					title: '<liferay-ui:message arguments="<%= commerceCart.getName() %>" key="add-new-product-to-x" />',
					url: '<%= commerceCartItemDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>