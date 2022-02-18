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
CommerceAccount commerceAccount = commerceOrderContentDisplayContext.getCommerceAccount();
%>

<liferay-ui:error exception="<%= CommerceOrderAccountLimitException.class %>" message="unable-to-create-a-new-order-as-the-open-order-limit-has-been-reached" />

<liferay-ddm:template-renderer
	className="<%= CommerceOpenOrderContentPortlet.class.getName() %>"
	contextObjects='<%=
		HashMapBuilder.<String, Object>put(
			"commerceOrderContentDisplayContext", commerceOrderContentDisplayContext
		).build()
	%>'
	displayStyle="<%= commerceOrderContentDisplayContext.getDisplayStyle(CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT) %>"
	displayStyleGroupId="<%= commerceOrderContentDisplayContext.getDisplayStyleGroupId(CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT) %>"
	entries="<%= commerceOrderSearchContainer.getResults() %>"
>
	<frontend-data-set:classic-display
		dataProviderKey="<%= CommerceOrderFDSNames.PENDING_ORDERS %>"
		id="<%= CommerceOrderFDSNames.PENDING_ORDERS %>"
		itemsPerPage="<%= 10 %>"
		style="stacked"
	/>

	<portlet:actionURL name="/commerce_open_order_content/edit_commerce_order" var="editCommerceOrderURL" />

	<div class="commerce-cta is-visible">
		<c:if test="<%= commerceOrderContentDisplayContext.hasPermission(CommerceOrderActionKeys.ADD_COMMERCE_ORDER) %>">
			<aui:form action="<%= editCommerceOrderURL %>" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="deleteCommerceOrderIds" type="hidden" />

				<clay:button
					cssClass="btn-fixed btn-lg btn-primary"
					disabled="<%= commerceAccount == null %>"
					displayType="primary"
					id="add-order"
					label='<%= LanguageUtil.get(request, "add-order") %>'
					small="<%= false %>"
					type="submit"
				/>
			</aui:form>

			<c:if test="<%= commerceOrderContentDisplayContext.getCommerceOrderTypesCount() > 1 %>">
				<portlet:renderURL var="viewCommerceOrderOrderTypeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcRenderCommandName" value="/commerce_order_content/view_commerce_order_order_type_modal" />
				</portlet:renderURL>

				<aui:script require="commerce-frontend-js/utilities/eventsDefinitions as events">
					document.querySelector('#add-order').addEventListener('click', (e) => {
						e.preventDefault();
						Liferay.fire(events.OPEN_MODAL, {
							id: 'add-order-modal',
						});
					});
				</aui:script>

				<commerce-ui:modal
					id="add-order-modal"
					refreshPageOnClose="<%= true %>"
					url="<%= viewCommerceOrderOrderTypeURL %>"
				/>
			</c:if>
		</c:if>
	</div>
</liferay-ddm:template-renderer>