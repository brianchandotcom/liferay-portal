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
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long commerceOrderId = commerceOrderEditDisplayContext.getCommerceOrderId();
%>

<liferay-portlet:actionURL name="/commerce_order/edit_commerce_order" var="editCommerceOrderURL" />

<aui:fieldset-group markupView="lexicon">
	<aui:form action="<%= editCommerceOrderURL %>" method="post" name="orderCustomFieldFm">
		<aui:fieldset>
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="customFields" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrderId %>" />

			<liferay-ui:error-marker
				key="<%= WebKeys.ERROR_SECTION %>"
				value="custom-fields"
			/>

			<aui:model-context bean="<%= commerceOrderEditDisplayContext.getCommerceOrder() %>" model="<%= CommerceOrder.class %>" />

			<liferay-expando:custom-attribute-list
				className="<%= CommerceOrder.class.getName() %>"
				classPK="<%= commerceOrderId %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>

			<aui:button-row>
				<aui:button type="submit" />
			</aui:button-row>
		</aui:fieldset>
	</aui:form>
</aui:fieldset-group>