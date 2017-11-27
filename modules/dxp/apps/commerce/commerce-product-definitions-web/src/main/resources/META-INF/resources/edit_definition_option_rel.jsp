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
CPDefinitionOptionRelDisplayContext cpDefinitionOptionRelDisplayContext = (CPDefinitionOptionRelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinitionOptionRel cpDefinitionOptionRel = cpDefinitionOptionRelDisplayContext.getCPDefinitionOptionRel();

long cpDefinitionOptionRelId = cpDefinitionOptionRelDisplayContext.getCPDefinitionOptionRelId();

List<DDMFormFieldType> ddmFormFieldTypes = cpDefinitionOptionRelDisplayContext.getDDMFormFieldTypes();
%>

<portlet:actionURL name="editProductDefinitionOptionRel" var="editProductDefinitionOptionRelActionURL" />

<aui:form action="<%= editProductDefinitionOptionRelActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionOptionRel.getCPDefinitionId() %>" />
	<aui:input name="cpDefinitionOptionRelId" type="hidden" value="<%= String.valueOf(cpDefinitionOptionRelId) %>" />
	<aui:input name="cpOptionId" type="hidden" value="<%= cpDefinitionOptionRel.getCPOptionId() %>" />

	<div class="lfr-form-content">
		<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="details" />

		<aui:model-context bean="<%= cpDefinitionOptionRel %>" model="<%= CPDefinitionOptionRel.class %>" />

		<aui:fieldset>
			<aui:input name="title" />

			<aui:input name="description" />

			<aui:select
				label="field-type"
				name="DDMFormFieldTypeName"
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

			<aui:input name="required" />

			<aui:input name="skuContributor" />

			<aui:input name="priority" />

			<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="custom-fields" />

			<aui:model-context bean="<%= cpDefinitionOptionRel %>" model="<%= CPDefinitionOptionRel.class %>" />

			<c:if test="<%= cpDefinitionOptionRelDisplayContext.hasCustomAttributesAvailable() %>">
				<liferay-expando:custom-attribute-list
					className="<%= CPDefinitionOptionRel.class.getName() %>"
					classPK="<%= (cpDefinitionOptionRel != null) ? cpDefinitionOptionRel.getCPDefinitionOptionRelId() : 0 %>"
					editable="<%= true %>"
					label="<%= true %>"
				/>
			</c:if>
		</aui:fieldset>
	</div>
</aui:form>