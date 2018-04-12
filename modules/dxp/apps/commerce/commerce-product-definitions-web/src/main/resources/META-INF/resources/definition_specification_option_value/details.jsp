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
CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue = (CPDefinitionSpecificationOptionValue)request.getAttribute(CPWebKeys.CP_DEFINITION_SPECIFICATION_OPTION_VALUE);
CPDefinitionSpecificationOptionValueDisplayContext cpDefinitionSpecificationOptionValueDisplayContext = (CPDefinitionSpecificationOptionValueDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long cpOptionCategoryId = BeanParamUtil.getLong(cpDefinitionSpecificationOptionValue, request, "CPOptionCategoryId");

List<CPOptionCategory> cpOptionCategories = cpDefinitionSpecificationOptionValueDisplayContext.getCPOptionCategories();
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="details"
/>

<aui:model-context bean="<%= cpDefinitionSpecificationOptionValue %>" model="<%= CPDefinitionSpecificationOptionValue.class %>" />

<aui:fieldset>
	<aui:input name="value" />

	<aui:select label="category" name="CPOptionCategoryId" showEmptyOption="<%= true %>">

		<%
		for (CPOptionCategory cpOptionCategory : cpOptionCategories) {
		%>

			<aui:option label="<%= cpOptionCategory.getTitle(locale) %>" selected="<%= cpOptionCategoryId == cpOptionCategory.getCPOptionCategoryId() %>" value="<%= cpOptionCategory.getCPOptionCategoryId() %>" />

		<%
		}
		%>

	</aui:select>

	<aui:input name="priority" />
</aui:fieldset>