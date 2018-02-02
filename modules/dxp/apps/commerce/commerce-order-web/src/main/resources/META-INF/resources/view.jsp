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
CommerceOrderListDisplayContext commerceOrderListDisplayContext = (CommerceOrderListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceOrder> commerceOrderSearchContainer = commerceOrderListDisplayContext.getSearchContainer();
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= commerceOrderListDisplayContext.getNavigationItems() %>"
/>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceOrders"
>
	<liferay-frontend:management-bar-filters>
		<c:if test="<%= commerceOrderListDisplayContext.isShowManagementBarFilter() %>">
			<liferay-frontend:management-bar-filter
				managementBarFilterItems="<%= commerceOrderListDisplayContext.getManagementBarFilterItems() %>"
				value="<%= commerceOrderListDisplayContext.getManagementBarFilterValue() %>"
			/>
		</c:if>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceOrderSearchContainer.getOrderByCol() %>"
			orderByType="<%= commerceOrderSearchContainer.getOrderByType() %>"
			orderColumns="<%= commerceOrderSearchContainer.getOrderableHeaders() %>"
			portletURL="<%= commerceOrderListDisplayContext.getPortletURL() %>"
		/>

		<li>
			<liferay-portlet:renderURL varImpl="searchURL" />

			<aui:form action="<%= searchURL %>" method="get" name="fm">
				<liferay-portlet:renderURLParams varImpl="searchURL" />

				<liferay-ui:search-form
					page="/order_search.jsp"
					servletContext="<%= application %>"
				/>
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceOrders();" %>' icon="times" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		id="commerceOrders"
		searchContainer="<%= commerceOrderSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.model.CommerceOrder"
			escapedModel="<%= true %>"
			keyProperty="commerceOrderId"
			modelVar="commerceOrder"
		>

			<%
			User orderUser = commerceOrder.getOrderUser();

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("mvcRenderCommandName", "editCommerceOrder");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("commerceOrderId", String.valueOf(commerceOrder.getCommerceOrderId()));
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				href="<%= rowURL %>"
				name="order-date"
			>
				<div class="order-date">
					<%= HtmlUtil.escape(commerceOrderListDisplayContext.getCommerceOrderDate(commerceOrder)) %>
				</div>

				<div class="order-time">
					<%= HtmlUtil.escape(commerceOrderListDisplayContext.getCommerceOrderTime(commerceOrder)) %>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="order-status"
				value="<%= LanguageUtil.get(request, CommerceOrderConstants.getOrderStatusLabel(commerceOrder.getOrderStatus())) %>"
			>

			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="customer-name"
			>
				<div class="customer-name">
					<%= HtmlUtil.escape(orderUser.getFullName()) %>
				</div>

				<div class="customer-id">
					<%= commerceOrder.getOrderUserId() %>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="order-id"
				property="commerceOrderId"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="order-value"
				value="<%= commerceOrderListDisplayContext.getCommerceOrderValue(commerceOrder) %>"
			/>

			<liferay-ui:search-container-column-text
				align="center"
				cssClass="table-cell-content"
				name="notes"
			>

				<%
				rowURL.setParameter("screenNavigationCategoryKey", CommerceOrderScreenNavigationConstants.CATEGORY_KEY_COMMERCE_ORDER_NOTES);

				int commerceOrderNotesCount = commerceOrderListDisplayContext.getCommerceOrderNotesCount(commerceOrder);

				String taglibIconCssClass = "icon-file-text";

				if (commerceOrderNotesCount <= 0) {
					taglibIconCssClass += " no-notes";
				}

				String taglibMessage = null;

				if (commerceOrderNotesCount == 1) {
					taglibMessage = LanguageUtil.get(request, "1-note");
				}
				else {
					taglibMessage = LanguageUtil.format(request, "x-notes", commerceOrderNotesCount, false);
				}
				%>

				<liferay-ui:icon
					cssClass="notes-icon"
					iconCssClass="<%= taglibIconCssClass %>"
					message="<%= taglibMessage %>"
					method="get"
					url="<%= rowURL.toString() %>"
				/>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				cssClass="table-cell-content"
				path="/order_transition.jsp"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/order_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</div>

<div class="hide" id="<portlet:namespace />transitionComments">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="transition" />

	<aui:input cols="55" name="comment" placeholder="comment" rows="1" type="textarea" />
</div>

<aui:script>
	function <portlet:namespace />deleteCommerceOrders() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-orders") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCommerceOrderIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editCommerceOrder" />');
		}
	}
</aui:script>

<aui:script use="liferay-util-window">
	var searchContainer = A.one('#<portlet:namespace />commerceOrdersSearchContainer');
	var transitionComments = A.one('#<portlet:namespace />transitionComments');

	searchContainer.delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var form = A.Node.create('<form />');

			var url = '<portlet:actionURL name="editCommerceOrder" />';

			url += '&<portlet:namespace />commerceOrderId=' + link.getData('commerceOrderId');
			url += '&<portlet:namespace />workflowTaskId=' + link.getData('workflowTaskId');
			url += '&<portlet:namespace />transitionName=' + link.getData('transitionName');

			form.setAttribute('action', url);
			form.setAttribute('method', 'POST');

			form.append(transitionComments);

			transitionComments.show();

			var dialog = Liferay.Util.Window.getWindow(
				{
					dialog: {
						bodyContent: form,
						destroyOnHide: true,
						height: 400,
						resizable: false,
						toolbars: {
							footer: [
								{
									cssClass: 'btn-primary mr-2',
									label: '<%= UnicodeLanguageUtil.get(request, "done") %>',
									on: {
										click: function() {
											submitForm(form);
										}
									}
								},
								{
									cssClass: 'btn-cancel',
									label: '<%= UnicodeLanguageUtil.get(request, "cancel") %>',
									on: {
										click: function() {
											dialog.hide();
										}
									}
								}
							],
							header: [
								{
									cssClass: 'close',
									discardDefaultButtonCssClasses: true,
									labelHTML: '<span aria-hidden="true">&times;</span>',
									on: {
										click: function(event) {
											dialog.hide();
										}
									}
								}
							]
						},
						width: 720
					},
					title: link.text()
				}
			);
		},
		'.transition-link'
	);
</aui:script>