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

<portlet:actionURL name="/cp_options/edit_cp_option" var="editOptionActionURL" />

<liferay-portlet:renderURL var="editCPOptionExternalReferenceCodeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/cp_options/edit_cp_option_external_reference_code" />
	<portlet:param name="cpOptionId" value="<%= String.valueOf(cpOptionId) %>" />
</liferay-portlet:renderURL>

<commerce-ui:header
	actions="<%= cpOptionDisplayContext.getHeaderActionModels() %>"
	bean="<%= cpOption %>"
	beanIdLabel="id"
	externalReferenceCode="<%= cpOption.getExternalReferenceCode() %>"
	externalReferenceCodeEditUrl="<%= editCPOptionExternalReferenceCodeURL %>"
	model="<%= CPOption.class %>"
	title="<%= cpOption.getName(locale) %>"
	wrapperCssClasses="side-panel-top-anchor"
/>

<aui:form action="<%= editOptionActionURL %>" cssClass="col pt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpOption == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpOptionId" type="hidden" value="<%= String.valueOf(cpOptionId) %>" />

	<div class="container">
		<div class="row">
			<div class="col-12">
				<commerce-ui:panel
					title='<%= LanguageUtil.get(request, "details") %>'
				>

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

						<aui:input checked="<%= (cpOption == null) ? false : cpOption.isFacetable() %>" label="use-in-faceted-navigation" name="facetable" type="toggle-switch" />

						<aui:input checked="<%= (cpOption == null) ? false : cpOption.getRequired() %>" name="required" type="toggle-switch" />

						<aui:input checked="<%= (cpOption == null) ? false : cpOption.isSkuContributor() %>" name="skuContributor" type="toggle-switch" />

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
				</commerce-ui:panel>

				<c:if test="<%= cpOptionDisplayContext.hasValues(cpOption) %>">
					<commerce-ui:panel
						bodyClasses="p-0"
						title='<%= LanguageUtil.get(request, "values") %>'
					>
						<clay:headless-data-set-display
							apiURL='<%= "/o/headless-commerce-admin-catalog/v1.0/options/" + cpOptionId + "/optionValues" %>'
							clayDataSetActionDropdownItems="<%= cpOptionDisplayContext.getOptionValueClayDataSetActionDropdownItems() %>"
							creationMenu="<%= cpOptionDisplayContext.getOptionValueCreationMenu(cpOptionId) %>"
							id="<%= CommerceOptionDataSetConstants.COMMERCE_DATA_SET_KEY_OPTION_VALUES %>"
							itemsPerPage="<%= 10 %>"
							namespace="<%= liferayPortletResponse.getNamespace() %>"
							pageNumber="<%= 1 %>"
							portletURL="<%= renderResponse.createRenderURL() %>"
							style="stacked"
						/>
					</commerce-ui:panel>
				</c:if>
			</div>
		</div>
	</div>
</aui:form>

<liferay-frontend:component
	module="js/edit_cp_option_and_value"
/>