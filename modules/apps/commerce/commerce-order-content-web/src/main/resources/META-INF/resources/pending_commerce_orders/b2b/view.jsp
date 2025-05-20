<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AccountEntry accountEntry = commerceOrderContentDisplayContext.getAccountEntry();
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

	<c:if test="<%= commerceOrderContentDisplayContext.hasPermission(accountEntry, CommerceOrderActionKeys.ADD_COMMERCE_ORDER) %>">
		<portlet:renderURL var="editCommerceOrderRenderURL">
			<portlet:param name="mvcRenderCommandName" value="/commerce_open_order_content/edit_commerce_order" />
		</portlet:renderURL>

		<div class="commerce-cta is-visible">
			<clay:button
				additionalProps='<%=
					HashMapBuilder.<String, Object>put(
						"orderDetailURL", editCommerceOrderRenderURL
					).build()
				%>'
				cssClass="btn-fixed btn-lg btn-primary"
				disabled="<%= accountEntry == null %>"
				displayType="primary"
				id="add-order"
				label='<%= LanguageUtil.get(request, "add-order") %>'
				propsTransformer="{AddOrderButtonPropsTransformer} from commerce-order-content-web"
				type="button"
			/>
		</div>
	</c:if>
</liferay-ddm:template-renderer>