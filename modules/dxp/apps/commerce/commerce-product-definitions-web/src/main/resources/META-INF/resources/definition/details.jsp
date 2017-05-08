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
CPDefinition cpDefinition = (CPDefinition)request.getAttribute(CPWebKeys.COMMERCE_PRODUCT_DEFINITION);
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="details" />

<aui:model-context bean="<%= cpDefinition %>" model="<%= CPDefinition.class %>" />

<aui:fieldset>
	<aui:input name="name" />

	<aui:input autoFocus="<%= true %>" label="title" localized="<%= true %>" name="titleMapAsXML" type="text" wrapperCssClass="commerce-product-definition-title">
		<aui:validator name="required" />
	</aui:input>

	<aui:input autoFocus="<%= true %>" label="description" localized="<%= true %>" name="descriptionMapAsXML" type="text" wrapperCssClass="commerce-product-definition-description" />
</aui:fieldset>

<aui:script use="aui-base">
	function afterDeletingAvailableLocale(event) {
		var descriptionInputLocalized = Liferay.component('<portlet:namespace />descriptionMapAsXML');

		var titleInputLocalized = Liferay.component('<portlet:namespace />titleMapAsXML');

		var locale = event.locale;

		descriptionInputLocalized.removeInputLanguage(locale);

		titleInputLocalized.removeInputLanguage(locale);
	}

	function afterEditingLocaleChange(event) {
		var descriptionInputLocalized = Liferay.component('<portlet:namespace />descriptionMapAsXML');

		var titleInputLocalized = Liferay.component('<portlet:namespace />titleMapAsXML');

		var items = descriptionInputLocalized.get('items');

		var editingLocale = event.newVal;

		var selectedIndex = items.indexOf(editingLocale);

		descriptionInputLocalized.set('selected', selectedIndex);
		descriptionInputLocalized.selectFlag(editingLocale);

		titleInputLocalized.set('selected', selectedIndex);
		titleInputLocalized.selectFlag(editingLocale);
	}

	var translationManager = Liferay.component('<portlet:namespace />translationManager');

	translationManager.on('deleteAvailableLocale', afterDeletingAvailableLocale)
	translationManager.on('editingLocaleChange', afterEditingLocaleChange)

</aui:script>