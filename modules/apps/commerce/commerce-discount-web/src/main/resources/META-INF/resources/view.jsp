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
CommerceDiscountDisplayContext commerceDiscountDisplayContext = (CommerceDiscountDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:if test="<%= commerceDiscountDisplayContext.hasPermission(CommerceDiscountActionKeys.VIEW_COMMERCE_DISCOUNTS) %>">

	<%
	SearchContainer<CommerceDiscount> commerceDiscountSearchContainer = commerceDiscountDisplayContext.getSearchContainer();
	%>

	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="commerceDiscounts"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				disabled="<%= true %>"
				displayViews='<%= new String[] {"list"} %>'
				portletURL="<%= commerceDiscountDisplayContext.getPortletURL() %>"
				selectedDisplayStyle="list"
			/>

			<c:if test="<%= commerceDiscountDisplayContext.hasPermission(CommerceDiscountActionKeys.ADD_COMMERCE_DISCOUNT) %>">
				<portlet:renderURL var="addCommerceDiscountURL">
					<portlet:param name="mvcRenderCommandName" value="editCommerceDiscount" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</portlet:renderURL>

				<liferay-frontend:add-menu
					inline="<%= true %>"
				>
					<liferay-frontend:add-menu-item
						title='<%= LanguageUtil.get(request, "add-discount") %>'
						url="<%= addCommerceDiscountURL.toString() %>"
					/>
				</liferay-frontend:add-menu>
			</c:if>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-sort
				orderByCol="<%= commerceDiscountSearchContainer.getOrderByCol() %>"
				orderByType="<%= commerceDiscountSearchContainer.getOrderByType() %>"
				orderColumns='<%= new String[] {"create-date"} %>'
				portletURL="<%= commerceDiscountDisplayContext.getPortletURL() %>"
			/>

			<li>
				<liferay-commerce:search-input
					actionURL="<%= commerceDiscountDisplayContext.getPortletURL() %>"
					formName="searchFm"
				/>
			</li>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + liferayPortletResponse.getNamespace() + "deleteCommerceDiscounts();" %>'
				icon="times"
				label="delete"
			/>
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>

	<div class="container-fluid container-fluid-max-xl">
		<portlet:actionURL name="editCommerceDiscount" var="editCommerceDiscountActionURL" />

		<aui:form action="<%= editCommerceDiscountActionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="deleteCommerceDiscountIds" type="hidden" />

			<liferay-ui:search-container
				id="commerceDiscounts"
				searchContainer="<%= commerceDiscountSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.commerce.discount.model.CommerceDiscount"
					keyProperty="commerceDiscountId"
					modelVar="commerceDiscount"
				>
					<portlet:renderURL var="rowURL">
						<portlet:param name="mvcRenderCommandName" value="editCommerceDiscount" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="commerceDiscountId" value="<%= String.valueOf(commerceDiscount.getCommerceDiscountId()) %>" />
					</portlet:renderURL>

					<liferay-ui:search-container-column-text
						cssClass="important table-cell-expand"
						href="<%= rowURL %>"
						property="title"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="active"
						value='<%= LanguageUtil.get(request, commerceDiscount.isActive() ? "yes" : "no") %>'
					/>

					<liferay-ui:search-container-column-jsp
						cssClass="entry-action-column"
						path="/discount_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</div>

	<aui:script>
		function <portlet:namespace />deleteCommerceDiscounts() {
			if (
				confirm(
					'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-discounts" />'
				)
			) {
				var form = window.document['<portlet:namespace />fm'];

				form[
					'<portlet:namespace />deleteCommerceDiscountIds'
				].value = Liferay.Util.listCheckedExcept(
					form,
					'<portlet:namespace />allRowIds'
				);

				submitForm(form);
			}
		}
	</aui:script>
</c:if>