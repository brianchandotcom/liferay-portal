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

boolean hasCustomAttributesAvailable = CustomAttributesUtil.hasCustomAttributes(company.getCompanyId(), CPOption.class.getName(), cpOptionId, null);
%>

<portlet:actionURL name="editProductOption" var="editProductOptionActionURL" />

<aui:form action="<%= editProductOptionActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
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

			<aui:input checked="<%= (cpOption == null) ? false : cpOption.getFacetable() %>" label="use-in-faceted-navigation" name="facetable" type="toggle-switch" />

			<aui:input checked="<%= (cpOption == null) ? false : cpOption.getRequired() %>" name="required" type="toggle-switch" />

			<aui:input checked="<%= (cpOption == null) ? false : cpOption.getSkuContributor() %>" name="skuContributor" type="toggle-switch" />

			<aui:input helpMessage="key-help" name="key" />
		</aui:fieldset>

		<c:if test="<%= hasCustomAttributesAvailable %>">
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
			<aui:script sandbox="<%= true %>">
				function slugify(string) {
					return string.toLowerCase().replace(/[^a-z1-9]+/g, '-');
				}

				var form = $(document.<portlet:namespace />fm);

				var keyInput = form.fm('key');
				var nameInput = form.fm('name');

				var onNameInput = _.debounce(
					function(event) {
						keyInput.val(slugify(nameInput.val()));
					},
					200
				);

				nameInput.on('input', onNameInput);
			</aui:script>
		</c:if>
	</div>
</aui:form>