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
CPOptionDisplayContext cpOptionDisplayContext = (CPOptionDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPOption cpOption = cpOptionDisplayContext.getCPOption();

long cpOptionId = cpOptionDisplayContext.getCPOptionId();
%>

<portlet:actionURL name="editProductOption" var="editProductOptionActionURL" />

<aui:form action="<%= editProductOptionActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpOption == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpOptionId" type="hidden" value="<%= String.valueOf(cpOptionId) %>" />

	<div class="lfr-form-content">

		<%
		List<DDMFormFieldType> ddmFormFieldTypes = cpOptionDisplayContext.getDDMFormFieldTypes();
		%>

		<liferay-ui:error-marker
			key="<%= WebKeys.ERROR_SECTION %>"
			value="product-option-details"
		/>

		<aui:model-context bean="<%= cpOption %>" model="<%= CPOption.class %>" />

		<liferay-ui:error exception="<%= CPOptionKeyException.class %>" message="that-key-is-already-being-used" />

		<aui:fieldset>
			<aui:input autoFocus="<%= true %>" name="name" wrapperCssClass="commerce-product-option-title" />

			<aui:input name="description" wrapperCssClass="commerce-product-option-description" />

			<aui:select label="option-field-type" name="DDMFormFieldTypeName" showEmptyOption="<%= true %>">

				<%
				for (DDMFormFieldType ddmFormFieldType : ddmFormFieldTypes) {
				%>

					<aui:option label="<%= cpOptionDisplayContext.getDDMFormFieldTypeLabel(ddmFormFieldType, locale) %>" selected="<%= (cpOption != null) && cpOption.getDDMFormFieldTypeName().equals(ddmFormFieldType.getName()) %>" value="<%= ddmFormFieldType.getName() %>" />

				<%
				}
				%>

			</aui:select>

			<aui:input checked="<%= (cpOption == null) ? false : cpOption.isFacetable() %>" inlineLabel="right" label="use-in-faceted-navigation" labelCssClass="simple-toggle-switch" name="facetable" type="toggle-switch" />

			<aui:input checked="<%= (cpOption == null) ? false : cpOption.getRequired() %>" inlineLabel="right" labelCssClass="simple-toggle-switch" name="required" type="toggle-switch" />

			<aui:input checked="<%= (cpOption == null) ? false : cpOption.isSkuContributor() %>" inlineLabel="right" labelCssClass="simple-toggle-switch" name="skuContributor" type="toggle-switch" />

			<aui:input helpMessage="key-help" name="key" />
		</aui:fieldset>

		<c:if test="<%= CustomAttributesUtil.hasCustomAttributes(company.getCompanyId(), CPOption.class.getName(), cpOptionId, null) %>">
			<aui:fieldset>
				<liferay-expando:custom-attribute-list
					className="<%= CPOption.class.getName() %>"
					classPK="<%= (cpOption != null) ? cpOption.getCPOptionId() : 0 %>"
					editable="<%= true %>"
					label="<%= true %>"
				/>
			</aui:fieldset>
		</c:if>

		<c:if test="<%= cpOption == null %>">
			<aui:script require="commerce-frontend-js/utilities/debounce as debounce, commerce-frontend-js/utilities/slugify as slugify">
				var form = document.getElementById('<portlet:namespace />fm');

				var keyInput = form.querySelector('#<portlet:namespace />key');
				var nameInput = form.querySelector('#<portlet:namespace />name');

				var handleOnNameInput = function (event) {
					keyInput.value = slugify.default(nameInput.value);
				};

				nameInput.addEventListener('input', debounce.default(handleOnNameInput, 200));
			</aui:script>
		</c:if>
	</div>
</aui:form>