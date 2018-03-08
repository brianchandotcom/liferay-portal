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

<portlet:actionURL name="editCommerceOrder" var="editCommerceOrderURL" />

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

			<aui:form action="<%= searchURL %>" method="get" name="searchFm">
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
	<aui:form action="<%= editCommerceOrderURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCommerceOrderIds" type="hidden" />

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
				boolean hasUpdatePermission = CommerceOrderPermission.contains(permissionChecker, commerceOrder, ActionKeys.UPDATE);

				PortletURL rowURL = renderResponse.createRenderURL();

				if (hasUpdatePermission) {
					rowURL.setParameter("mvcRenderCommandName", "editCommerceOrder");
				}
				else {
					rowURL.setParameter("mvcRenderCommandName", "viewCommerceOrderDetail");
				}

				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("commerceOrderId", String.valueOf(commerceOrder.getCommerceOrderId()));
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-list-title"
					href="<%= rowURL %>"
					name="order-date"
					value="<%= HtmlUtil.escape(commerceOrderListDisplayContext.getCommerceOrderDate(commerceOrder)) %>"
				/>

				<liferay-ui:search-container-column-status
					name="status"
					status="<%= commerceOrder.getStatus() %>"
					statusByUserId="<%= commerceOrder.getStatusByUserId() %>"
					statusDate="<%= commerceOrder.getStatusDate() %>"
				/>

				<liferay-ui:search-container-column-text
					name="customer-name"
					value="<%= HtmlUtil.escape(commerceOrderListDisplayContext.getCommerceOrderCustomerName(commerceOrder)) %>"
				/>

				<liferay-ui:search-container-column-text
					name="customer-id"
					value="<%= commerceOrderListDisplayContext.getCommerceOrderCustomerId(commerceOrder) %>"
				/>

				<liferay-ui:search-container-column-text
					name="order-id"
					property="commerceOrderId"
				/>

				<liferay-ui:search-container-column-text
					name="order-value"
					value="<%= commerceOrderListDisplayContext.getCommerceOrderValue(commerceOrder) %>"
				/>

				<liferay-ui:search-container-column-text
					align="center"
					name="notes"
				>

					<%
					if (hasUpdatePermission) {
						rowURL.setParameter("screenNavigationCategoryKey", CommerceOrderScreenNavigationConstants.CATEGORY_KEY_COMMERCE_ORDER_NOTES);
					}

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
					cssClass="transition-column"
					path="/order_transition.jsp"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					name="actions"
					path="/order_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator markupView="lexicon" />
		</liferay-ui:search-container>
	</aui:form>
</div>

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