<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>
<!-- test custom_fields.jsp -->

<%
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = commerceOrderEditDisplayContext.getCommerceOrder();
CommerceOrderItem commerceOrderItem = commerceOrderEditDisplayContext.getCommerceOrderItem();
%>

<liferay-portlet:actionURL name="/commerce_order/edit_commerce_order_item" var="editCommerceOrderItemActionURL" />

<commerce-ui:panel
	title='<%= LanguageUtil.get(request, "custom-fields") %>'
>
	<aui:form action="<%= commerceOrderEditDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) ? editCommerceOrderItemActionURL : null %>" method="post" name="fm">
		<aui:fieldset>
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="customFields" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrderItem.getCommerceOrderId() %>" />
			<aui:input name="commerceOrderItemId" type="hidden" value="<%= commerceOrderItem.getCommerceOrderItemId() %>" />
			<aui:input bean="<%= commerceOrderItem %>" name="quantity" type="hidden" />

			<liferay-ui:error-marker
				key="<%= WebKeys.ERROR_SECTION %>"
				value="custom-fields"
			/>

			<aui:model-context bean="<%= commerceOrderItem %>" model="<%= CommerceOrderItem.class %>" />

			<liferay-expando:custom-attribute-list
				className="<%= CommerceOrderItem.class.getName() %>"
				classPK="<%= (commerceOrderItem != null) ? commerceOrderItem.getCommerceOrderItemId() : 0 %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>

			<c:if test="<%= commerceOrderEditDisplayContext.hasModelPermission(commerceOrder, ActionKeys.UPDATE) %>">
				<aui:button-row>
					<aui:button type="submit" />
				</aui:button-row>
			</c:if>
		</aui:fieldset>
	</aui:form>
</commerce-ui:panel>