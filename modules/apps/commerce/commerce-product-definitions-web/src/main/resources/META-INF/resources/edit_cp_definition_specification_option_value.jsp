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
CPDefinitionSpecificationOptionValueDisplayContext cpDefinitionSpecificationOptionValueDisplayContext = (CPDefinitionSpecificationOptionValueDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue = cpDefinitionSpecificationOptionValueDisplayContext.getCPDefinitionSpecificationOptionValue();
List<CPOptionCategory> cpOptionCategories = cpDefinitionSpecificationOptionValueDisplayContext.getCPOptionCategories();

CPSpecificationOption cpSpecificationOption = cpDefinitionSpecificationOptionValue.getCPSpecificationOption();

long cpOptionCategoryId = BeanParamUtil.getLong(cpDefinitionSpecificationOptionValue, request, "CPOptionCategoryId");

NumberFormat decimalFormat = NumberFormat.getNumberInstance(locale);

decimalFormat.setMinimumFractionDigits(0);
%>

<portlet:actionURL name="/cp_definitions/edit_cp_definition_specification_option_value" var="editProductDefinitionSpecificationOptionValueActionURL" />

<commerce-ui:side-panel-content
	title="<%= cpSpecificationOption.getTitle(locale) %>"
>
	<commerce-ui:panel
		title='<%= LanguageUtil.get(request, "detail") %>'
	>
		<aui:form action="<%= editProductDefinitionSpecificationOptionValueActionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="cpDefinitionSpecificationOptionValueId" type="hidden" value="<%= String.valueOf(cpDefinitionSpecificationOptionValue.getCPDefinitionSpecificationOptionValueId()) %>" />

			<aui:input defaultLanguageId="<%= cpDefinitionSpecificationOptionValueDisplayContext.getCatalogDefaultLanguageId() %>" name="value" value="<%= cpDefinitionSpecificationOptionValue.getValue(cpDefinitionSpecificationOptionValueDisplayContext.getCatalogDefaultLanguageId()) %>" />

			<aui:select label="group" name="CPOptionCategoryId" showEmptyOption="<%= true %>">

				<%
				for (CPOptionCategory cpOptionCategory : cpOptionCategories) {
				%>

					<aui:option label="<%= cpOptionCategory.getTitle(locale) %>" selected="<%= cpOptionCategoryId == cpOptionCategory.getCPOptionCategoryId() %>" value="<%= cpOptionCategory.getCPOptionCategoryId() %>" />

				<%
				}
				%>

			</aui:select>

			<aui:input label="position" name="priority" value="<%= decimalFormat.format(cpDefinitionSpecificationOptionValue.getPriority()) %>">
				<aui:validator name="min">[0]</aui:validator>
				<aui:validator name="number" />
			</aui:input>

			<c:if test="<%= cpDefinitionSpecificationOptionValueDisplayContext.hasCustomAttributesAvailable() %>">
				<liferay-expando:custom-attribute-list
					className="<%= CPDefinitionSpecificationOptionValue.class.getName() %>"
					classPK="<%= (cpDefinitionSpecificationOptionValue != null) ? cpDefinitionSpecificationOptionValue.getCPDefinitionSpecificationOptionValueId() : 0 %>"
					editable="<%= true %>"
					label="<%= true %>"
				/>
			</c:if>

			<aui:button-row>
				<aui:button cssClass="btn-lg" type="submit" value="save" />

				<aui:button cssClass="btn-lg" type="cancel" />
			</aui:button-row>
		</aui:form>
	</commerce-ui:panel>
</commerce-ui:side-panel-content>