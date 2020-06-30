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

<commerce-ui:side-panel-content
	title='<%= (cpDefinitionOptionRel == null) ? LanguageUtil.get(request, "add-option") : LanguageUtil.format(request, "edit-x", cpDefinitionOptionRel.getName(languageId), false) %>'
>
	<aui:form action="<%= editProductDefinitionOptionRelActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="cpDefinitionId" type="hidden" value="<%= String.valueOf(cpDefinitionOptionRel.getCPDefinitionId()) %>" />
		<aui:input name="cpDefinitionOptionRelId" type="hidden" value="<%= String.valueOf(cpDefinitionOptionRelId) %>" />
		<aui:input name="cpOptionId" type="hidden" value="<%= cpDefinitionOptionRel.getCPOptionId() %>" />

		<liferay-ui:error exception="<%= CPDefinitionOptionSKUContributorException.class %>" message="sku-contributor-cannot-be-set-as-true-for-the-selected-field-type" />

		<aui:model-context bean="<%= cpDefinitionOptionRel %>" model="<%= CPDefinitionOptionRel.class %>" />

		<commerce-ui:panel
			title='<%= LanguageUtil.get(request, "details") %>'
		>
			<div class="row">
				<div class="col-12">
					<aui:input name="name" />
				</div>

				<div class="col-6">
					<aui:input name="description" />
				</div>

				<div class="col-6">
					<aui:input name="priority" />
				</div>

				<div class="col-4">
					<aui:input checked="<%= (cpDefinitionOptionRel == null) ? false : cpDefinitionOptionRel.isFacetable() %>" inlineField="<%= true %>" label="use-in-faceted-navigation" name="facetable" type="toggle-switch" />
				</div>

				<div class="col-4">
					<aui:input checked="<%= (cpDefinitionOptionRel == null) ? false : cpDefinitionOptionRel.getRequired() %>" inlineField="<%= true %>" name="required" type="toggle-switch" />
				</div>

				<div class="col-4">
					<aui:input checked="<%= (cpDefinitionOptionRel == null) ? false : cpDefinitionOptionRel.isSkuContributor() %>" inlineField="<%= true %>" name="skuContributor" type="toggle-switch" />
				</div>

				<div class="col-12">
					<aui:select label="field-type" name="DDMFormFieldTypeName" showEmptyOption="<%= true %>">

						<%
						for (DDMFormFieldType ddmFormFieldType : ddmFormFieldTypes) {
						%>

							<aui:option label="<%= cpDefinitionOptionRelDisplayContext.getDDMFormFieldTypeLabel(ddmFormFieldType, locale) %>" selected="<%= (cpDefinitionOptionRel != null) && cpDefinitionOptionRel.getDDMFormFieldTypeName().equals(ddmFormFieldType.getName()) %>" value="<%= ddmFormFieldType.getName() %>" />

						<%
						}
						%>

					</aui:select>
				</div>

				<div class="col-12">

					<%
					String priceType = BeanParamUtil.getString(cpDefinitionOptionRel, request, "priceType", CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC);
					%>

					<aui:select name="priceType" showEmptyOption="<%= false %>">
						<aui:option label="static" selected="<%= priceType.equals(CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC) %>" value="<%= CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC %>" />
						<aui:option label="dynamic" selected="<%= priceType.equals(CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC) %>" value="<%= CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC %>" />
					</aui:select>
				</div>
			</div>
		</commerce-ui:panel>

		<c:if test="<%= cpDefinitionOptionRelDisplayContext.hasCustomAttributesAvailable() %>">
			<commerce-ui:panel
				title='<%= LanguageUtil.get(request, "custom-attribute") %>'
			>
				<liferay-expando:custom-attribute-list
					className="<%= CPDefinitionOptionRel.class.getName() %>"
					classPK="<%= (cpDefinitionOptionRel != null) ? cpDefinitionOptionRel.getCPDefinitionOptionRelId() : 0 %>"
					editable="<%= true %>"
					label="<%= true %>"
				/>
			</commerce-ui:panel>
		</c:if>

		<div class="d-none" id="values-container">
			<commerce-ui:panel
				bodyClasses="p-0"
				title='<%= LanguageUtil.get(request, "values") %>'
			>

				<%
				Map<String, String> contextParams = new HashMap<>();

				contextParams.put("cpDefinitionOptionRelId", String.valueOf(cpDefinitionOptionRelId));
				%>

				<commerce-ui:dataset-display
					clayCreationMenu="<%= cpDefinitionOptionRelDisplayContext.getClayCreationMenu() %>"
					contextParams="<%= contextParams %>"
					dataProviderKey="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_OPTION_VALUES %>"
					id="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_OPTION_VALUES %>"
					itemsPerPage="<%= 10 %>"
					namespace="<%= renderResponse.getNamespace() %>"
					pageNumber="<%= 1 %>"
					portletURL="<%= currentURLObj %>"
				/>
			</commerce-ui:panel>
		</div>

		<aui:script>
			var valuesContainer = document.querySelector('#values-container');
			var optionTypeSelect = document.querySelector(
				'#<portlet:namespace />DDMFormFieldTypeName'
			);
			var sectionsTypeWithMultipleValues = ['select', 'radio', 'checkbox_multiple'];

			function handleTypeSelectChanges() {
				if (sectionsTypeWithMultipleValues.includes(optionTypeSelect.value)) {
					valuesContainer.classList.remove('d-none');
				} else {
					valuesContainer.classList.add('d-none');
				}
			}

			optionTypeSelect.addEventListener('change', handleTypeSelectChanges);

			handleTypeSelectChanges();
		</aui:script>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" value="save" />

			<aui:button cssClass="btn-lg" type="cancel" />
		</aui:button-row>
	</aui:form>
</commerce-ui:side-panel-content>