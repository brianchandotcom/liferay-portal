<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrderListDisplayContext commerceOrderListDisplayContext = (CommerceOrderListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

request.setAttribute("view.jsp-portletURL", commerceOrderListDisplayContext.getPortletURL());
%>

<div id="<portlet:namespace />orderDefinitionsContainer">
	<aui:form action="<%= commerceOrderListDisplayContext.getPortletURL() %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
		<aui:input name="redirect" type="hidden" value="<%= String.valueOf(commerceOrderListDisplayContext.getPortletURL()) %>" />
		<aui:input name="deleteCPDefinitionIds" type="hidden" />

		<frontend-data-set:headless-display
			additionalProps='<%=
				HashMapBuilder.<String, Object>put(
					"namespace", liferayPortletResponse.getNamespace()
				).build()
			%>'
			apiURL="/o/headless-commerce-admin-order/v1.0/orders?nestedFields=account,channel"
			bulkActionDropdownItems="<%= commerceOrderListDisplayContext.getBulkActionDropdownItems() %>"
			fdsActionDropdownItems="<%= commerceOrderListDisplayContext.getFDSActionDropdownItems() %>"
			fdsSortItemList="<%= commerceOrderListDisplayContext.getFDSSortItemList() %>"
			formName="fm"
			id="<%= CommerceOrderFDSNames.ALL_ORDERS %>"
			propsTransformer="js/deleteCommerceOrdersPropsTransformer"
			selectedItemsKey="id"
			selectionType="multiple"
			style="fluid"
		/>
	</aui:form>
</div>