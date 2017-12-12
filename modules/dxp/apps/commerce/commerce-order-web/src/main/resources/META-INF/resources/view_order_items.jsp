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
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all-order-items");

String languageId = LanguageUtil.getLanguageId(locale);

CommerceOrderItemDisplayContext commerceOrderItemDisplayContext = (CommerceOrderItemDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long commerceOrderId = commerceOrderItemDisplayContext.getCommerceOrderId();

SearchContainer<CommerceOrderItem> commerceOrderItemSearchContainer = commerceOrderItemDisplayContext.getSearchContainer();

String displayStyle = commerceOrderItemDisplayContext.getDisplayStyle();

PortletURL portletURL = commerceOrderItemDisplayContext.getPortletURL();

portletURL.setParameter("toolbarItem", toolbarItem);
portletURL.setParameter("searchContainerId", "commerceOrderItems");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<liferay-portlet:renderURL varImpl="viewCommerceOrderItemsURL">
			<portlet:param name="mvcRenderCommandName" value="viewCommerceOrderItems" />
			<portlet:param name="commerceOrderId" value="<%= String.valueOf(commerceOrderId) %>" />
			<portlet:param name="toolbarItem" value="view-all-order-items" />
		</liferay-portlet:renderURL>

		<aui:nav-item
			href="<%= viewCommerceOrderItemsURL.toString() %>"
			label="order-items"
			selected='<%= toolbarItem.equals("view-all-order-items") %>'
		/>
	</aui:nav>

	<aui:form action="<%= portletURL.toString() %>" name="searchFm">
		<aui:nav-bar-search>
			<liferay-ui:input-search markupView="lexicon" />
		</aui:nav-bar-search>
	</aui:form>
</aui:nav-bar>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceOrderItems"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceOrderItemDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="<%= commerceOrderItemDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= commerceOrderItemDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceOrderItemDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceOrderItemDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= commerceOrderItemDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceOrderItems();" %>' icon="times" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div id="<portlet:namespace />orderItemsContainer">
	<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCommerceOrderItemIds" type="hidden" />

		<div class="order-items-container" id="<portlet:namespace />entriesContainer">
			<liferay-ui:search-container
				id="commerceOrderItems"
				iteratorURL="<%= portletURL %>"
				searchContainer="<%= commerceOrderItemSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.commerce.model.CommerceOrderItem"
					cssClass="entry-display-style"
					keyProperty="commerceOrderItemId"
					modelVar="commerceOrderItem"
				>

					<%
					CPDefinition cpDefinition = commerceOrderItem.getCPDefinition();

					String cpDefinitionURL = StringPool.BLANK;
					String thumbnailSrc = StringPool.BLANK;

					if (cpDefinition != null) {
						cpDefinitionURL = commerceOrderItemDisplayContext.getCPDefinitionURL(cpDefinition.getCPDefinitionId(), themeDisplay);
						thumbnailSrc = cpDefinition.getDefaultImageThumbnailSrc(themeDisplay);
					}
					%>

					<liferay-ui:search-container-column-image
						cssClass="table-cell-content"
						name="product"
						src="<%= thumbnailSrc %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						href="<%= cpDefinitionURL %>"
						name="description"
						value="<%= HtmlUtil.escape(commerceOrderItem.getTitle(languageId)) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="quantity"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="price"
						value="<%= commerceOrderItemDisplayContext.getFormattedPrice(commerceOrderItem.getCommerceOrderItemId()) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="user"
						property="userName"
					/>

					<liferay-ui:search-container-column-jsp
						cssClass="entry-action-column"
						path="/order_item_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" searchContainer="<%= commerceOrderItemSearchContainer %>" />
			</liferay-ui:search-container>
		</div>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />deleteCommerceOrderItems() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-items") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCommerceOrderItemIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editCommerceOrderItem" />');
		}
	}
</aui:script>