<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long commerceReturnId = ParamUtil.getLong(request, "commerceReturnId");
%>

<c:choose>
	<c:when test="<%= commerceReturnId > 0 %>">
		<commerce-ui:modal-content
			title='<%= LanguageUtil.get(request, "select-returnable-items") %>'
		>
			<aui:form method="post" name="fm">
				<aui:input name="redirect" type="hidden" value="" />

				<frontend-data-set:headless-display
					additionalProps="<%= commerceOrderContentDisplayContext.getReturnableOrderItemsContextParams() %>"
					apiURL="<%= commerceOrderContentDisplayContext.getCommerceReturnableItemsAPIURL() %>"
					bulkActionDropdownItems="<%= commerceOrderContentDisplayContext.getCommerceReturnableItemsBulkActionDropdownItems() %>"
					id="<%= CommerceOrderFDSNames.RETURNABLE_ORDER_ITEMS %>"
					propsTransformer="js/returnableOrderItemsPropsTransformer"
					selectedItems="<%= commerceOrderContentDisplayContext.getReturnableSelectedItems() %>"
					selectedItemsKey="id"
					selectionType="multiple"
					style="fluid"
				/>
			</aui:form>
		</commerce-ui:modal-content>
	</c:when>
	<c:otherwise>
		<aui:form method="post" name="fm">
			<aui:input name="redirect" type="hidden" value="" />

			<frontend-data-set:headless-display
				additionalProps="<%= commerceOrderContentDisplayContext.getReturnableOrderItemsContextParams() %>"
				apiURL="<%= commerceOrderContentDisplayContext.getCommerceReturnableItemsAPIURL() %>"
				bulkActionDropdownItems="<%= commerceOrderContentDisplayContext.getCommerceReturnableItemsBulkActionDropdownItems() %>"
				id="<%= CommerceOrderFDSNames.RETURNABLE_ORDER_ITEMS %>"
				propsTransformer="js/returnableOrderItemsPropsTransformer"
				selectedItems="<%= commerceOrderContentDisplayContext.getReturnableSelectedItems() %>"
				selectedItemsKey="id"
				selectionType="multiple"
				style="fluid"
			/>
		</aui:form>
	</c:otherwise>
</c:choose>