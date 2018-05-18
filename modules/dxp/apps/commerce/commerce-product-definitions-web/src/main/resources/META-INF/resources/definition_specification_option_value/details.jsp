<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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

	<aui:select label="group" name="CPOptionCategoryId" showEmptyOption="<%= true %>">

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