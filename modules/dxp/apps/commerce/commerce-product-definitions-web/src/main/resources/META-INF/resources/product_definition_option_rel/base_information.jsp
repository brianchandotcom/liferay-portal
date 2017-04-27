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
CPDefinitionOptionRel cpDefinitionOptionRel = (CPDefinitionOptionRel)request.getAttribute(CPWebKeys.COMMERCE_PRODUCT_DEFINITION_OPTION_REL);

CPDefinitionOptionRelDisplayContext cpDefinitionOptionRelDisplayContext = (CPDefinitionOptionRelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<DDMFormFieldType> ddmFormFieldTypes = cpDefinitionOptionRelDisplayContext.getDDMFormFieldTypes();
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="details" />

<aui:model-context bean="<%= cpDefinitionOptionRel %>" model="<%= CPDefinitionOptionRel.class %>" />

<aui:fieldset>
	<aui:select
		label="product-option-field-type"
		name="ddmFormFieldTypeName"
		showEmptyOption="<%= true %>"
	>

		<%
		for (DDMFormFieldType ddmFormFieldType : ddmFormFieldTypes) {
		%>

			<aui:option
				label="<%= ddmFormFieldType.getName() %>"
				selected="<%= (cpDefinitionOptionRel != null) && cpDefinitionOptionRel.getDDMFormFieldTypeName().equals(ddmFormFieldType.getName()) %>"
				value="<%= ddmFormFieldType.getName() %>"
			/>

		<%
		}
		%>

	</aui:select>

	<aui:input name="facetable" />
	<aui:input name="skuContributor" />
	<aui:input label="priority" name="priority" />
</aui:fieldset>