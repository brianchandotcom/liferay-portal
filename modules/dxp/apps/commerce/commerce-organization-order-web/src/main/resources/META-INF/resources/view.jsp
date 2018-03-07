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
			inverted="<%= true %>"
			items="<%= commerceOrganizationOrderDisplayContext.getNavigationItems() %>"
		/>

		<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>" />

		<aui:form action="<%= editCommerceOrderURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="deleteCommerceOrderIds" type="hidden" />

			<liferay-ui:search-container
				id="commerceOrders"
				searchContainer="<%= commerceOrganizationOrderDisplayContext.getSearchContainer() %>"
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
						cssClass="table-list-title"
						href="<%= rowURL %>"
						name="order-date"
						orderable="<%= true %>"
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
						value="<%= HtmlUtil.escape(commerceOrganizationOrderDisplayContext.getCommerceOrderCustomerName(commerceOrder)) %>"
					/>

					<liferay-ui:search-container-column-text
						name="customer-id"
						value="<%= commerceOrganizationOrderDisplayContext.getCommerceOrderCustomerId(commerceOrder) %>"
					/>

					<liferay-ui:search-container-column-text
						name="order-id"
						orderable="<%= true %>"
						property="commerceOrderId"
					/>

					<liferay-ui:search-container-column-text
						name="order-value"
						orderable="<%= true %>"
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
						cssClass="entry-action-column"
						name="actions"
						path="/order_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator markupView="lexicon" />
			</liferay-ui:search-container>
		</aui:form>

		<div class="hide" id="<portlet:namespace />transitionComments">
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="transition" />

			<aui:input cols="55" name="comment" placeholder="comment" rows="1" type="textarea" />
		</div>

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
											cssClass: 'btn-primary mr-2',
											label: '<%= UnicodeLanguageUtil.get(request, "add-order") %>',
											on: {
												click: function() {
													submitForm(document.<portlet:namespace />addFm);
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
				if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-orders") %>')) {
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
	</c:when>
	<c:otherwise>
		<div class="alert alert-warning text-center">
			<liferay-ui:message key="please-select-an-account" />
		</div>
	</c:otherwise>
</c:choose>