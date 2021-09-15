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
CommerceWishListDisplayContext commerceWishListDisplayContext = (CommerceWishListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="container-fluid container-fluid-max-xl py-3">
	<h3 class="align-middle d-inline"><liferay-ui:message key="wish-lists" /></h3>

	<div class="d-inline float-right">
		<portlet:actionURL name="/commerce_wish_list_content/edit_commerce_wish_list" var="addCommerceWishListActionURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SAVE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:actionURL>

		<liferay-frontend:add-menu
			inline="<%= true %>"
		>
			<liferay-frontend:add-menu-item
				title='<%= LanguageUtil.get(request, "add-wish-list") %>'
				url="<%= addCommerceWishListActionURL %>"
			/>
		</liferay-frontend:add-menu>
	</div>
</div>

<div class="container-fluid container-fluid-max-xl">
	<portlet:actionURL name="/commerce_wish_list_content/edit_commerce_wish_list" var="editCommerceWishListActionURL" />

	<aui:form action="<%= editCommerceWishListActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCommerceWishListIds" type="hidden" />

		<liferay-ui:search-container
			id="commerceWishLists"
			searchContainer="<%= commerceWishListDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.wish.list.model.CommerceWishList"
				keyProperty="commerceWishListId"
				modelVar="commerceWishList"
			>
				<liferay-ui:search-container-column-text
					cssClass="font-weight-bold table-cell-expand"
					href="<%= commerceWishListDisplayContext.getRowURL(commerceWishList.getCommerceWishListId()) %>"
					name="name"
					value="<%= HtmlUtil.escape(commerceWishList.getName()) %>"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/wish_list_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>