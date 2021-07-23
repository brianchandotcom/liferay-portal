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
String defaultLanguageId = cpDefinitionOptionRelDisplayContext.getCatalogDefaultLanguageId();
%>

<portlet:actionURL name="/cp_definitions/edit_cp_definition_option_rel" var="editProductDefinitionOptionRelActionURL" />

<liferay-frontend:side-panel-content
	title='<%= (cpDefinitionOptionRel == null) ? LanguageUtil.get(request, "add-option") : LanguageUtil.format(request, "edit-x", cpDefinitionOptionRel.getName(languageId), false) %>'
>
	<aui:form action="<%= editProductDefinitionOptionRelActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="cpDefinitionId" type="hidden" value="<%= String.valueOf(cpDefinitionOptionRel.getCPDefinitionId()) %>" />
		<aui:input name="cpDefinitionOptionRelId" type="hidden" value="<%= String.valueOf(cpDefinitionOptionRelId) %>" />
		<aui:input name="cpOptionId" type="hidden" value="<%= cpDefinitionOptionRel.getCPOptionId() %>" />

		<liferay-ui:error exception="<%= CPDefinitionOptionRelPriceTypeException.class %>" message="price-type-cannot-be-changed-for-the-current-option-value-setup" />
		<liferay-ui:error exception="<%= CPDefinitionOptionSKUContributorException.class %>" message="sku-contributor-cannot-be-set-as-true-for-the-selected-field-type" />

		<aui:model-context bean="<%= cpDefinitionOptionRel %>" model="<%= CPDefinitionOptionRel.class %>" />

		<commerce-ui:panel
			title='<%= LanguageUtil.get(request, "details") %>'
		>
			<div class="row">
				<div class="col-12">
					<aui:input defaultLanguageId="<%= defaultLanguageId %>" name="name" />
				</div>

				<div class="col-6">
					<aui:input defaultLanguageId="<%= defaultLanguageId %>" name="description" />
				</div>

				<div class="col-6">
					<aui:input label="position" name="priority">
						<aui:validator name="min">[0]</aui:validator>
						<aui:validator name="number" />
					</aui:input>
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
					<aui:select name="priceType" showEmptyOption="<%= true %>">
						<aui:option label="static" selected="<%= (cpDefinitionOptionRel != null) && cpDefinitionOptionRel.isPriceTypeStatic() %>" value="<%= CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC %>" />
						<aui:option label="dynamic" selected="<%= (cpDefinitionOptionRel != null) && cpDefinitionOptionRel.isPriceTypeDynamic() %>" value="<%= CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC %>" />
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
				String datasetDisplayId = CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_OPTION_VALUES;

				if (cpDefinitionOptionRel.isPriceTypeStatic()) {
					datasetDisplayId = CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_OPTION_VALUES_STATIC;
				}
				%>

				<clay:data-set-display
					contextParams='<%=
						HashMapBuilder.<String, String>put(
							"cpDefinitionOptionRelId", String.valueOf(cpDefinitionOptionRelId)
						).build()
					%>'
					creationMenu="<%= cpDefinitionOptionRelDisplayContext.getCreationMenu() %>"
					dataProviderKey="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_OPTION_VALUES %>"
					id="<%= datasetDisplayId %>"
					itemsPerPage="<%= 10 %>"
					namespace="<%= liferayPortletResponse.getNamespace() %>"
					pageNumber="<%= 1 %>"
					portletURL="<%= currentURLObj %>"
					selectedItemsKey="cpdefinitionOptionValueRelId"
				/>
			</commerce-ui:panel>
		</div>

		<aui:script>
			var allowedPriceContributorTypeNames =
				'<%= StringUtil.merge(CPConstants.PRODUCT_OPTION_PRICE_CONTRIBUTOR_FIELD_TYPES, StringPool.COMMA) %>';
			var allowedPriceContributorFieldTypeSelectOptions = allowedPriceContributorTypeNames.split(
				'<%= StringPool.COMMA %>'
			);
			var allowedSkuContributorTypeNames =
				'<%= StringUtil.merge(CPConstants.PRODUCT_OPTION_SKU_CONTRIBUTOR_FIELD_TYPES, StringPool.COMMA) %>';
			var allowedSkuContributorFieldTypeSelectOptions = allowedSkuContributorTypeNames.split(
				'<%= StringPool.COMMA %>'
			);
			var availableTypeNames =
				'<%= cpDefinitionOptionRelDisplayContext.getDDMFormFieldTypeNames() %>';
			var availableFieldTypeSelectOptions = availableTypeNames.split(
				'<%= StringPool.COMMA %>'
			);
			var multipleValuesTypeNames =
				'<%= StringUtil.merge(CPConstants.PRODUCT_OPTION_MULTIPLE_VALUES_FIELD_TYPES, StringPool.COMMA) %>';
			var multipleValuesFieldTypeSelectOptions = multipleValuesTypeNames.split(
				'<%= StringPool.COMMA %>'
			);

			var formFieldTypeSelect = document.getElementById(
				'<portlet:namespace />DDMFormFieldTypeName'
			);
			var priceTypeSelect = document.getElementById('<portlet:namespace />priceType');
			var skuContributorInput = document.getElementById(
				'<portlet:namespace />skuContributor'
			);
			var valuesContainer = document.getElementById('values-container');

			function checkDDMFormFieldType(event) {
				var priceTypeSelectValue =
					priceTypeSelect.options[priceTypeSelect.selectedIndex].value;
				var skuContributorInputChecked = skuContributorInput.checked;

				enableFormFieldTypeSelectOptionValues(availableFieldTypeSelectOptions);

				if (priceTypeSelectValue != '') {
					enableFormFieldTypeSelectOptionValues(
						allowedPriceContributorFieldTypeSelectOptions
					);
				}

				if (skuContributorInputChecked) {
					enableFormFieldTypeSelectOptionValues(
						allowedSkuContributorFieldTypeSelectOptions
					);
				}
			}

			function enableFormFieldTypeSelectOptionValues(array) {
				if (
					formFieldTypeSelect.value != '' &&
					!endsWith(formFieldTypeSelect.value, array)
				) {
					alert(
						'<liferay-ui:message key="selected-field-type-price-type-and-sku-contributor-combination-is-not-allowed" />'
					);

					return;
				}

				for (var i = 0; i < formFieldTypeSelect.options.length; i++) {
					var formFieldTypeSelectOption = formFieldTypeSelect.options[i];

					if (formFieldTypeSelectOption.value == '') {
						continue;
					}

					if (endsWith(formFieldTypeSelectOption.value, array)) {
						if (formFieldTypeSelectOption.getAttribute('disabled')) {
							formFieldTypeSelectOption.removeAttribute('disabled');
						}

						continue;
					}

					formFieldTypeSelectOption.setAttribute('disabled', true);
				}
			}

			function handleFormFieldTypeSelectChanges() {
				if (
					endsWith(
						formFieldTypeSelect.value,
						allowedPriceContributorFieldTypeSelectOptions
					)
				) {
					enable(priceTypeSelect);
				}
				else {
					if (priceTypeSelect.value == '') {
						disable(priceTypeSelect);
					}
					else {
						alert(
							'<liferay-ui:message key="selected-field-type-price-type-and-sku-contributor-combination-is-not-allowed" />'
						);

						return;
					}
				}

				if (
					endsWith(
						formFieldTypeSelect.value,
						allowedSkuContributorFieldTypeSelectOptions
					)
				) {
					enable(skuContributorInput);
				}
				else {
					if (!skuContributorInput.checked) {
						disable(skuContributorInput);
					}
					else {
						alert(
							'<liferay-ui:message key="selected-field-type-price-type-and-sku-contributor-combination-is-not-allowed" />'
						);

						return;
					}
				}

				if (
					endsWith(
						formFieldTypeSelect.value,
						multipleValuesFieldTypeSelectOptions
					)
				) {
					valuesContainer.classList.remove('d-none');
				}
				else {
					valuesContainer.classList.add('d-none');
				}
			}

			function disable(element) {
				if (!element.getAttribute('disabled')) {
					element.setAttribute('disabled', true);
				}
			}

			function enable(element) {
				if (element.getAttribute('disabled')) {
					element.removeAttribute('disabled');
				}
			}

			function endsWith(value, array) {
				value = value.toLowerCase();

				for (var i = 0; i < array.length; i++) {
					if (value.endsWith(array[i].toLowerCase())) {
						return true;
					}
				}

				return false;
			}

			formFieldTypeSelect.addEventListener(
				'change',
				handleFormFieldTypeSelectChanges
			);
			skuContributorInput.addEventListener('change', checkDDMFormFieldType);
			priceTypeSelect.addEventListener('change', checkDDMFormFieldType);
			handleFormFieldTypeSelectChanges();
		</aui:script>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" value="save" />

			<aui:button cssClass="btn-lg" type="cancel" />
		</aui:button-row>
	</aui:form>
</liferay-frontend:side-panel-content>