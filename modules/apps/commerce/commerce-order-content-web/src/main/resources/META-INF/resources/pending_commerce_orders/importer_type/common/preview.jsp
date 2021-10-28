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
String commerceOrderImporterTypeKey = ParamUtil.getString(request, "commerceOrderImporterTypeKey");

CommerceOrderImporterType commerceOrderImporterType = commerceOrderContentDisplayContext.getCommerceOrderImporterType(commerceOrderImporterTypeKey);
%>

<c:if test="<%= commerceOrderImporterType != null %>">

	<%
	String commerceOrderImporterItemParamName = ParamUtil.getString(request, commerceOrderImporterType.getCommerceOrderImporterItemParamName());
	%>

	<portlet:actionURL name="/commerce_open_order_content/import_commerce_order_items" var="importCommerceOrderItemsActionURL">
		<portlet:param name="mvcRenderCommandName" value="/commerce_open_order_content/edit_commerce_order" />
	</portlet:actionURL>

	<aui:form action="<%= importCommerceOrderItemsActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.IMPORT %>" />
		<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrderContentDisplayContext.getCommerceOrderId() %>" />
		<aui:input name="commerceOrderImporterTypeKey" type="hidden" value="<%= commerceOrderImporterTypeKey %>" />
		<aui:input name="<%= commerceOrderImporterType.getCommerceOrderImporterItemParamName() %>" type="hidden" value="<%= commerceOrderImporterItemParamName %>" />

		<clay:data-set-display
			contextParams='<%=
				HashMapBuilder.<String, String>put(
					"commerceOrderId", String.valueOf(commerceOrderContentDisplayContext.getCommerceOrderId())
				).put(
					"commerceOrderImporterTypeKey", commerceOrderImporterTypeKey
				).put(
					commerceOrderImporterType.getCommerceOrderImporterItemParamName(), commerceOrderImporterItemParamName
				).build()
			%>'
			dataProviderKey="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PREVIEW_COMMERCE_ORDER_ITEMS %>"
			id="<%= CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PREVIEW_COMMERCE_ORDER_ITEMS %>"
			itemsPerPage="<%= 10 %>"
			namespace="<%= liferayPortletResponse.getNamespace() %>"
			pageNumber="<%= 1 %>"
			portletURL="<%= commerceOrderContentDisplayContext.getPortletURL() %>"
			showSearch="<%= false %>"
			style="fluid"
		/>

		<aui:button-row>
			<aui:button cssClass="btn-lg" name="importButton" primary="<%= true %>" type="submit" value='<%= LanguageUtil.get(request, "import") %>' />

			<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>

	<liferay-frontend:component
		module="js/preview"
	/>
</c:if>