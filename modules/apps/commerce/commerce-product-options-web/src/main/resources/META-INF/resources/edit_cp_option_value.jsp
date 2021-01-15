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
CPOptionValue cpOptionValue = (CPOptionValue)request.getAttribute(CPWebKeys.CP_OPTION_VALUE);

long cpOptionValueId = BeanParamUtil.getLong(cpOptionValue, request, "CPOptionValueId");

long cpOptionId = ParamUtil.getLong(request, "cpOptionId");

String defaultLanguageId = LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault());

Set<Locale> availableLocalesSet = new HashSet<>();

availableLocalesSet.add(LocaleUtil.fromLanguageId(defaultLanguageId));

if (cpOptionValue != null) {
	for (String languageId : cpOptionValue.getAvailableLanguageIds()) {
		availableLocalesSet.add(LocaleUtil.fromLanguageId(languageId));
	}
}
%>

<portlet:actionURL name="/commerce_product_options/edit_cp_option_value" var="editProductOptionValueActionURL" />

<commerce-ui:side-panel-content
	title='<%= LanguageUtil.format(request, "edit-x", cpOptionValue.getName(), false) %>'
>
	<aui:form action="<%= editProductOptionValueActionURL %>" method="post" name="optionValueFm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpOptionValue == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="cpOptionId" type="hidden" value="<%= cpOptionId %>" />
		<aui:input name="cpOptionValueId" type="hidden" value="<%= cpOptionValueId %>" />

		<liferay-ui:error exception="<%= CPOptionValueKeyException.class %>" message="price-type-cannot-be-changed-for-the-current-option-value-setup" />

		<commerce-ui:panel
			title='<%= LanguageUtil.get(request, "details") %>'
		>
			<liferay-ui:error-marker
				key="<%= WebKeys.ERROR_SECTION %>"
				value="product-option-value-details"
			/>

			<aui:model-context bean="<%= cpOptionValue %>" model="<%= CPOptionValue.class %>" />

			<liferay-ui:error exception="<%= CPOptionValueKeyException.class %>" focusField="key" message="that-key-is-already-being-used" />

			<aui:fieldset>
				<aui:input id="optionValueName" name="name" wrapperCssClass="commerce-product-option-value-title" />

				<aui:input name="priority" />

				<aui:input helpMessage="key-help" name="key" />
			</aui:fieldset>

			<c:if test="<%= CustomAttributesUtil.hasCustomAttributes(company.getCompanyId(), CPOptionValue.class.getName(), cpOptionValueId, null) %>">
				<aui:fieldset>
					<liferay-expando:custom-attribute-list
						className="<%= CPOptionValue.class.getName() %>"
						classPK="<%= (cpOptionValue != null) ? cpOptionValue.getCPOptionValueId() : 0 %>"
						editable="<%= true %>"
						label="<%= true %>"
					/>
				</aui:fieldset>
			</c:if>

			<c:if test="<%= cpOptionValue == null %>">
				<aui:script require="commerce-frontend-js/utilities/debounce as debounce, commerce-frontend-js/utilities/slugify as slugify">
					var form = document.getElementById('<portlet:namespace />optionValueFm');

					var keyInput = form.querySelector('#<portlet:namespace />key');
					var nameInput = form.querySelector('#<portlet:namespace />optionValueName');

					var handleOnNameInput = function () {
					keyInput.value = slugify.default(nameInput.value);
					};

					nameInput.addEventListener('input', debounce.default(handleOnNameInput, 200));
				</aui:script>
			</c:if>
		</commerce-ui:panel>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" value="save" />

			<aui:button cssClass="btn-lg" type="cancel" />
		</aui:button-row>
	</aui:form>
</commerce-ui:side-panel-content>