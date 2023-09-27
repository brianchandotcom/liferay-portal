<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		<liferay-ui:icon
			icon="plus"
			label="<%= false %>"
			linkCssClass="btn btn-monospaced btn-primary"
			markupView="lexicon"
			message="add-wish-list"
			toolTip="<%= true %>"
			url="<%= addCommerceWishListActionURL %>"
		/>
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
					cssClass="font-weight-bold important table-cell-expand"
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