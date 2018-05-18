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
CommerceOrderItemContentDisplayContext commerceOrderItemContentDisplayContext = (CommerceOrderItemContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = commerceOrderItemContentDisplayContext.getCommerceOrder();

SearchContainer<CommerceOrderItem> commerceOrderItemSearchContainer = commerceOrderItemContentDisplayContext.getSearchContainer();

PortletURL portletURL = commerceOrderItemContentDisplayContext.getPortletURL();

portletURL.setParameter("searchContainerId", "commerceOrderItems");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<div class="commerce-orders-container container-fluid-1280" id="<portlet:namespace />orderItemsContainer">
	<liferay-ui:header
		backURL="<%= renderResponse.createRenderURL() %>"
		title="back"
	/>

	<div class="col-md-8" id="<portlet:namespace />entriesContainer">
		<liferay-ui:search-container
			id="commerceOrderItems"
			iteratorURL="<%= portletURL %>"
			searchContainer="<%= commerceOrderItemSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.model.CommerceOrderItem"
				cssClass="entry-display-style"
				keyProperty="CommerceOrderItemId"
				modelVar="commerceOrderItem"
			>

				<%
				CPDefinition cpDefinition = commerceOrderItem.getCPDefinition();

				String cpDefinitionURL = StringPool.BLANK;
				String thumbnailSrc = StringPool.BLANK;

				if (cpDefinition != null) {
					cpDefinitionURL = commerceOrderItemContentDisplayContext.getCPDefinitionURL(cpDefinition.getCPDefinitionId(), themeDisplay);
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
					value="<%= HtmlUtil.escape(commerceOrderItem.getName(languageId)) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="quantity"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="price"
					value="<%= commerceOrderItemContentDisplayContext.getFormattedPrice(commerceOrderItem.getCommerceOrderItemId()) %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="list"
				markupView="lexicon"
				searchContainer="<%= commerceOrderItemSearchContainer %>"
			/>
		</liferay-ui:search-container>
	</div>

	<div class="col-md-4">
		<h4><liferay-ui:message key="order-number" /> <%= HtmlUtil.escape(String.valueOf(commerceOrder.getCommerceOrderId())) %></h4>

		<h5>
			<liferay-ui:message key="order-status" /> <aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= commerceOrder.getStatus() %>" /></h5>
		</h5>

		<h5>
			<liferay-ui:message key="order-date" /> <%= HtmlUtil.escape(String.valueOf(commerceOrder.getCreateDate())) %>
		</h5>
	</div>
</div>