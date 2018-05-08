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
%>

<portlet:actionURL name="editCommerceOrder" var="editCommerceOrderURL" />

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= commerceOrderListDisplayContext.getNavigationItems() %>"
/>

<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>" />

<aui:form action="<%= editCommerceOrderURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="deleteCommerceOrderIds" type="hidden" />

	<liferay-ui:search-container
		cssClass="table-nowrap"
		id="commerceOrders"
		searchContainer="<%= commerceOrderListDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.model.CommerceOrder"
			modelVar="commerceOrder"
		>

			<%
			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("mvcRenderCommandName", "editCommerceOrder");
			rowURL.setParameter("commerceOrderId", String.valueOf(commerceOrder.getCommerceOrderId()));
			%>

			<liferay-ui:search-container-column-text
				cssClass="important table-cell-expand table-list-title"
				href="<%= rowURL %>"
				name="order-date"
				value="<%= HtmlUtil.escape(commerceOrderListDisplayContext.getCommerceOrderDateTime(commerceOrder)) %>"
			/>

			<liferay-ui:search-container-column-text
				name="order-status"
				value="<%= commerceOrderListDisplayContext.getCommerceOrderStatus(commerceOrder) %>"
			/>

			<liferay-ui:search-container-column-text
				name="payment-status"
				value="<%= commerceOrderListDisplayContext.getCommerceOrderPaymentStatus(commerceOrder) %>"
			/>

			<liferay-ui:search-container-column-status
				name="buyer-workflow-status"
				status="<%= commerceOrder.getStatus() %>"
				statusByUserId="<%= commerceOrder.getStatusByUserId() %>"
				statusDate="<%= commerceOrder.getStatusDate() %>"
			/>

			<liferay-ui:search-container-column-text
				name="customer-name"
				property="customerName"
			/>

			<liferay-ui:search-container-column-text
				name="customer-id"
				property="customerId"
			/>

			<liferay-ui:search-container-column-text
				name="order-id"
				property="commerceOrderId"
			/>

			<liferay-ui:search-container-column-text
				name="order-value"
				value="<%= commerceOrderListDisplayContext.getCommerceOrderValue(commerceOrder) %>"
			/>

			<liferay-ui:search-container-column-jsp
				align="center"
				name="notes"
				path="/order_notes.jsp"
			/>

			<liferay-ui:search-container-column-text
				cssClass="transition-column"
			>
				<liferay-commerce:order-transitions
					commerceOrderId="<%= commerceOrder.getCommerceOrderId() %>"
					cssClass="btn btn-secondary btn-sm"
				/>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				align="center"
				cssClass="entry-action-column"
				name="actions"
				path="/order_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<div class="hide" id="<portlet:namespace />transitionComments">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="transition" />

	<aui:input cols="55" name="comment" placeholder="comment" rows="1" type="textarea" />
</div>

<aui:script>
	function <portlet:namespace />deleteCommerceOrders() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-orders" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCommerceOrderIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
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

			var workflowTaskId = parseInt(link.getData('workflowTaskId'), 10);

			var form = A.Node.create('<form />');

			var url = '<%= editCommerceOrderURL %>';

			url += '&<portlet:namespace />commerceOrderId=' + link.getData('commerceOrderId');
			url += '&<portlet:namespace />workflowTaskId=' + workflowTaskId;
			url += '&<portlet:namespace />transitionName=' + link.getData('transitionName');

			form.setAttribute('action', url);
			form.setAttribute('method', 'POST');

			form.append(transitionComments);

			if (workflowTaskId <= 0) {
				submitForm(form);

				return;
			}

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
									label: '<liferay-ui:message key="done" />',
									on: {
										click: function() {
											submitForm(form);
										}
									}
								},
								{
									cssClass: 'btn-cancel',
									label: '<liferay-ui:message key="cancel" />',
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