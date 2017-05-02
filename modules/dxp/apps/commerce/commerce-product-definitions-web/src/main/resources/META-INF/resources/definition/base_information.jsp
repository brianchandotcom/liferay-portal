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
CPDefinition cpDefinition = (CPDefinition)request.getAttribute(CPWebKeys.COMMERCE_PRODUCT_DEFINITION);

CPDefinitionsDisplayContext cpDefinitionsDisplayContext = (CPDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<CPType> cpTypes = cpDefinitionsDisplayContext.getCPTypes();
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="base-information" />

<aui:model-context bean="<%= cpDefinition %>" model="<%= CPDefinition.class %>" />

<aui:fieldset cssClass="col-md-4">
	<aui:select label="product-type" name="productTypeName">

		<%
		for (CPType cpType : cpTypes) {
		%>

			<aui:option
				label="<%= cpType.getName() %>"
				selected="<%= (cpDefinition != null) && cpDefinition.getProductTypeName().equals(cpType.getName()) %>"
				value="<%= cpType.getName() %>"
			/>

		<%
		}
		%>

	</aui:select>

	<aui:input label="SKU" name="baseSKU" />
</aui:fieldset>