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
		<liferay-ui:error-marker
			key="<%= WebKeys.ERROR_SECTION %>"
			value="details"
		/>

		<aui:model-context bean="<%= cpDefinitionOptionRel %>" model="<%= CPDefinitionOptionRel.class %>" />

		<aui:fieldset>
			<aui:input name="name" />

			<aui:input name="description" />

			<aui:select label="field-type" name="DDMFormFieldTypeName" showEmptyOption="<%= true %>">

				<%
				for (DDMFormFieldType ddmFormFieldType : ddmFormFieldTypes) {
				%>

					<aui:option label="<%= ddmFormFieldType.getName() %>" selected="<%= (cpDefinitionOptionRel != null) && cpDefinitionOptionRel.getDDMFormFieldTypeName().equals(ddmFormFieldType.getName()) %>" value="<%= ddmFormFieldType.getName() %>" />

				<%
				}
				%>

			</aui:select>

			<aui:input label="use-in-faceted-navigation" name="facetable" />

			<aui:input name="required" />

			<aui:input name="skuContributor" />

			<aui:input name="priority" />

			<liferay-ui:error-marker
				key="<%= WebKeys.ERROR_SECTION %>"
				value="custom-fields"
			/>

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