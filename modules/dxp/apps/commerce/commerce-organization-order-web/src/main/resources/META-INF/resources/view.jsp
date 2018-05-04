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
CommerceOrganizationOrderDisplayContext commerceOrganizationOrderDisplayContext = (CommerceOrganizationOrderDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:choose>
	<c:when test="<%= commerceOrganizationOrderDisplayContext.getOrganization() != null %>">
		<liferay-portlet:renderURL var="addCommerceOrderURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
			<liferay-portlet:param name="mvcRenderCommandName" value="addCommerceOrder" />
			<liferay-portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:renderURL>

		<portlet:actionURL name="editCommerceOrder" var="editCommerceOrderURL" />

		<clay:navigation-bar
			items="<%= commerceOrganizationOrderDisplayContext.getNavigationItems() %>"
		/>

		<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>" />

		<aui:form action="<%= editCommerceOrderURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="deleteCommerceOrderIds" type="hidden" />

			<liferay-ui:search-container
				cssClass="table-nowrap table-responsive"
				id="commerceOrders"
				searchContainer="<%= commerceOrganizationOrderDisplayContext.getSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.commerce.model.CommerceOrder"
					keyProperty="commerceOrderId"
					modelVar="commerceOrder"
				>

					<%
					PortletURL rowURL = renderResponse.createRenderURL();

					rowURL.setParameter("redirect", currentURL);
					rowURL.setParameter("mvcRenderCommandName", "editCommerceOrder");
					rowURL.setParameter("commerceOrderId", String.valueOf(commerceOrder.getCommerceOrderId()));
					%>

					<liferay-ui:search-container-column-text
						cssClass="important table-list-title"
						href="<%= rowURL %>"
						name="order-date"
						value="<%= HtmlUtil.escape(commerceOrganizationOrderDisplayContext.getCommerceOrderDateTime(commerceOrder)) %>"
					/>

					<liferay-ui:search-container-column-status
						name="status"
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
						value="<%= commerceOrganizationOrderDisplayContext.getCommerceOrderValue(commerceOrder) %>"
					/>

					<liferay-ui:search-container-column-jsp
						align="center"
						name="notes"
						path="/order_notes.jsp"
					/>

					<liferay-ui:search-container-column-jsp
						cssClass="transition-column"
						path="/order_transition.jsp"
					/>

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

		<%@ include file="/transition.jspf" %>

		<aui:script>
			Liferay.provide(
				window,
				'<portlet:namespace />addCommerceOrder',
				function(A) {
					var A = AUI();

					var dialog = Liferay.Util.Window.getWindow(
						{
							dialog: {
								destroyOnClose: true,
								toolbars: {
									footer: [
										{
											cssClass: 'btn-cancel mr-2',
											label: '<liferay-ui:message key="cancel" />',
											on: {
												click: function() {
													dialog.hide();
												}
											}
										},
										{
											cssClass: 'btn-primary',
											label: '<liferay-ui:message key="add-order" />',
											on: {
												click: function() {
													submitForm(document.<portlet:namespace />addFm);
												}
											}
										}
									],
									header: [
										{
											cssClass: 'close',
											discardDefaultButtonCssClasses: true,
											labelHTML: '<clay:icon symbol="times" />',
											on: {
												click: function(event) {
													dialog.hide();
												}
											}
										}
									]
								},
								width: 600
							},
							title: '<liferay-ui:message key="add-order" />'
						}
					).plug(
						A.Plugin.IO,
						{
							uri: '<%= addCommerceOrderURL %>'
						}
					).render();
				},
				['aui-io-deprecated', 'liferay-util-window']
			);

			function <portlet:namespace />deleteCommerceOrders() {
				if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-orders" />')) {
					var form = AUI.$(document.<portlet:namespace />fm);

					form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
					form.fm('deleteCommerceOrderIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

					submitForm(form);
				}
			}
		</aui:script>

		<aui:script use="aui-base">
			var searchContainer = A.one('#<portlet:namespace />commerceOrdersSearchContainer');

			searchContainer.delegate(
				'click',
				function(event) {
					<portlet:namespace />transition(event);
				},
				'.transition-link'
			);
		</aui:script>
	</c:when>
	<c:otherwise>
		<div class="alert alert-warning text-center">
			<liferay-ui:message key="please-select-an-account" />
		</div>
	</c:otherwise>
</c:choose>