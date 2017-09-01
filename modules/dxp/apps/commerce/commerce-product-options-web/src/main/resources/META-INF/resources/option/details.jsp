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
CPOption cpOption = (CPOption)request.getAttribute(CPWebKeys.CP_OPTION);

CPOptionDisplayContext cpOptionDisplayContext = (CPOptionDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<DDMFormFieldType> ddmFormFieldTypes = cpOptionDisplayContext.getDDMFormFieldTypes();
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="product-option-details" />

<aui:model-context bean="<%= cpOption %>" model="<%= CPOption.class %>" />

<liferay-ui:error exception="<%= CPOptionKeyException.class %>" message="please-enter-a-unique-key" />

<aui:fieldset>
	<aui:input autoFocus="<%= true %>" name="title" wrapperCssClass="commerce-product-option-title" />

	<aui:input name="description" wrapperCssClass="commerce-product-option-description" />

	<aui:select label="option-field-type" name="DDMFormFieldTypeName" showEmptyOption="<%= true %>">

		<%
		for (DDMFormFieldType ddmFormFieldType : ddmFormFieldTypes) {
		%>

			<aui:option
				label="<%= ddmFormFieldType.getName() %>"
				selected="<%= (cpOption != null) && cpOption.getDDMFormFieldTypeName().equals(ddmFormFieldType.getName()) %>"
				value="<%= ddmFormFieldType.getName() %>"
			/>

		<%
		}
		%>

	</aui:select>

	<aui:input name="facetable" />

	<aui:input name="required" />

	<aui:input name="skuContributor" />

	<aui:input helpMessage="key-help" name="key" />
</aui:fieldset>

<c:if test="<%= cpOption == null %>">
	<aui:script sandbox="<%= true %>">
		var form = $(document.<portlet:namespace />fm);

		var keyInput = form.fm('key');
		var titleInput = form.fm('title');

		var onTitleInput = _.debounce(
			function(event) {
				keyInput.val(titleInput.val());
			},
			200
		);

		titleInput.on('input', onTitleInput);
	</aui:script>
</c:if>

<aui:script use="aui-base">
	function afterDeletingAvailableLocale(event) {
		var descriptionInputLocalized = Liferay.component('<portlet:namespace />description');
		var titleInputLocalized = Liferay.component('<portlet:namespace />title');

		var locale = event.locale;

		descriptionInputLocalized.removeInputLanguage(locale);
		titleInputLocalized.removeInputLanguage(locale);
	}

	function afterEditingLocaleChange(event) {
		var descriptionInputLocalized = Liferay.component('<portlet:namespace />description');
		var titleInputLocalized = Liferay.component('<portlet:namespace />title');

		var editingLocale = event.newVal;
		var items = descriptionInputLocalized.get('items');
		var selectedIndex = items.indexOf(editingLocale);

		descriptionInputLocalized.set('selected', selectedIndex);
		descriptionInputLocalized.selectFlag(editingLocale);

		titleInputLocalized.set('selected', selectedIndex);
		titleInputLocalized.selectFlag(editingLocale);
	}

	var translationManager = Liferay.component('<portlet:namespace />translationManager');

	if (translationManager) {
		translationManager.on('deleteAvailableLocale', afterDeletingAvailableLocale);
		translationManager.on('editingLocaleChange', afterEditingLocaleChange);
	}
</aui:script>