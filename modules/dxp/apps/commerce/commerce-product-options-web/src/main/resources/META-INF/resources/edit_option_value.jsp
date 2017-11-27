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

Locale[] availableLocales = availableLocalesSet.toArray(new Locale[availableLocalesSet.size()]);

boolean hasCustomAttributesAvailable = CustomAttributesUtil.hasCustomAttributes(company.getCompanyId(), CPOptionValue.class.getName(), cpOptionValueId, null);
%>

<portlet:actionURL name="editProductOptionValue" var="editProductOptionValueActionURL" />

<aui:form action="<%= editProductOptionValueActionURL %>" cssClass="container-fluid-1280" method="post" name="optionValueFm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpOptionValue == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="cpOptionId" type="hidden" value="<%= cpOptionId %>" />
	<aui:input name="cpOptionValueId" type="hidden" value="<%= cpOptionValueId %>" />

	<liferay-frontend:translation-manager
		availableLocales="<%= availableLocales %>"
		changeableDefaultLanguage="<%= true %>"
		componentId='<%= renderResponse.getNamespace() + "optionValuesTranslationManager" %>'
		defaultLanguageId="<%= defaultLanguageId %>"
		id="optionValuesTranslationManager"
	/>

	<div class="lfr-form-content">
		<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="product-option-value-details" />

		<aui:model-context bean="<%= cpOptionValue %>" model="<%= CPOptionValue.class %>" />

		<liferay-ui:error exception="<%= CPOptionValueKeyException.class %>" message="please-enter-a-unique-key" />

		<aui:fieldset>
			<aui:input id="optionValueTitle" name="title" wrapperCssClass="commerce-product-option-value-title" />

			<aui:input name="priority" />

			<aui:input helpMessage="key-help" name="key" />
		</aui:fieldset>

		<c:if test="<%= hasCustomAttributesAvailable %>">
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
			<aui:script sandbox="<%= true %>">
				var form = $(document.<portlet:namespace />optionValueFm);

				var keyInput = form.fm('key');
				var titleInput = form.fm('optionValueTitle');

				var onTitleInput = _.debounce(
					function(event) {
						keyInput.val(titleInput.val());
					},
					200
				);

				titleInput.on('input', onTitleInput);
			</aui:script>
		</c:if>
	</div>
</aui:form>

<aui:script use="aui-base">
	function afterDeletingAvailableLocale(event) {
		var titleInputLocalized = Liferay.component('<portlet:namespace />optionValueTitle');

		var locale = event.locale;

		titleInputLocalized.removeInputLanguage(locale);
	}

	function afterEditingLocaleChange(event) {
		var titleInputLocalized = Liferay.component('<portlet:namespace />optionValueTitle');

		var editingLocale = event.newVal;
		var items = titleInputLocalized.get('items');
		var selectedIndex = items.indexOf(editingLocale);

		titleInputLocalized.set('selected', selectedIndex);
		titleInputLocalized.selectFlag(editingLocale);
	}

	var translationManager = Liferay.component('<portlet:namespace />optionValuesTranslationManager');

	if (translationManager) {
		translationManager.on('deleteAvailableLocale', afterDeletingAvailableLocale);
		translationManager.on('editingLocaleChange', afterEditingLocaleChange);
	}
</aui:script>