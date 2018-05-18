<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceWishListDisplayContext commerceWishListDisplayContext = (CommerceWishListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceWishList commerceWishList = commerceWishListDisplayContext.getCommerceWishList();
long commerceWishListId = commerceWishListDisplayContext.getCommerceWishListId();
SearchContainer<CommerceWishListItem> commerceWishListItemsSearchContainer = commerceWishListDisplayContext.getCommerceWishListItemsSearchContainer();
PortletURL portletURL = commerceWishListDisplayContext.getPortletURL();
%>

<c:if test="<%= !commerceWishList.isGuestWishList() %>">
	<h3><%= HtmlUtil.escape(commerceWishList.getName()) %></h3>

	<portlet:renderURL var="editCommerceWishListURL">
		<portlet:param name="mvcRenderCommandName" value="editCommerceWishList" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="commerceWishListId" value="<%= String.valueOf(commerceWishListId) %>" />
	</portlet:renderURL>

	<aui:button href="<%= editCommerceWishListURL %>" name="editWishListButton" value="edit" />
</c:if>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceWishListItems"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceWishListItemsSearchContainer.getOrderByCol() %>"
			orderByType="<%= commerceWishListItemsSearchContainer.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceWishListItems();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="editCommerceWishListItem" var="editCommerceWishListItemActionURL" />

<aui:form action="<%= editCommerceWishListItemActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="deleteCommerceWishListItemIds" type="hidden" />

	<liferay-ui:search-container
		searchContainer="<%= commerceWishListItemsSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.wish.list.model.CommerceWishListItem"
			keyProperty="CommerceWishListItemId"
			modelVar="commerceWishListItem"
		>

			<%
			CPDefinition cpDefinition = commerceWishListItem.getCPDefinition();
			%>

			<liferay-ui:search-container-column-image
				name="product"
				src="<%= cpDefinition.getDefaultImageThumbnailSrc(themeDisplay) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="description"
			>
				<a href="<%= commerceWishListDisplayContext.getCPDefinitionURL(cpDefinition.getCPDefinitionId(), themeDisplay) %>">
					<%= HtmlUtil.escape(cpDefinition.getName(themeDisplay.getLanguageId())) %>
				</a>

				<h6 class="text-default">
					<%= HtmlUtil.escape(commerceWishListDisplayContext.getCommerceWishListItemDescription(commerceWishListItem)) %>
				</h6>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="price"
				value="<%= commerceWishListDisplayContext.getCommerceWishListItemPrice(commerceWishListItem) %>"
			/>

			<liferay-ui:search-container-column-text>
				<portlet:actionURL name="editCommerceWishListItem" var="deleteURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="commerceWishListItemId" value="<%= String.valueOf(commerceWishListItem.getCommerceWishListItemId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon-delete
					label="<%= true %>"
					url="<%= deleteURL %>"
				/>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				colspan="<%= 2 %>"
				path="/wish_list_item_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	function <portlet:namespace />deleteCommerceWishListItems() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-items" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('deleteCommerceWishListItemIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}
</aui:script>