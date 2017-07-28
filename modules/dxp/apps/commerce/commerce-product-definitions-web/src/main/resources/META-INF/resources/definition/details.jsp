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
CPDefinitionsDisplayContext cpDefinitionsDisplayContext = (CPDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionsDisplayContext.getCPDefinition();

String friendlyURLBase = themeDisplay.getPortalURL() + CPConstants.SEPARATOR_PRODUCT_URL;
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="details" />

<aui:model-context bean="<%= cpDefinition %>" model="<%= CPDefinition.class %>" />

<liferay-ui:error exception="<%= CPFriendlyURLEntryException.class %>">

	<%
	CPFriendlyURLEntryException cpfuee = (CPFriendlyURLEntryException)errorException;
	%>

	<%@ include file="/error_friendly_url_exception.jspf" %>
</liferay-ui:error>

<aui:fieldset>
	<aui:input autoFocus="<%= true %>" label="title" localized="<%= true %>" name="titleMapAsXML" type="text" wrapperCssClass="commerce-product-definition-title">
		<aui:validator name="required" />
	</aui:input>

	<aui:input label="short-description" localized="<%= true %>" name="shortDescriptionMapAsXML" type="textarea" wrapperCssClass="commerce-product-definition-description" />

	<%
	String descriptionMapAsXML = StringPool.BLANK;

	if (cpDefinition != null) {
		descriptionMapAsXML = cpDefinition.getDescriptionMapAsXML();
	}
	%>

	<aui:field-wrapper cssClass="commerce-product-definition-description" label="full-description">
		<div class="entry-content form-group">
			<liferay-ui:input-localized editorName="alloyeditor" name="descriptionMapAsXML" type="editor" xml="<%= descriptionMapAsXML %>" />
		</div>
	</aui:field-wrapper>
</aui:fieldset>

<aui:script use="aui-base">
	function afterDeletingAvailableLocale(event) {
		var descriptionInputLocalized = Liferay.component('<portlet:namespace />descriptionMapAsXML');
		var shortDescriptionInputLocalized = Liferay.component('<portlet:namespace />shortDescriptionMapAsXML');
		var titleInputLocalized = Liferay.component('<portlet:namespace />titleMapAsXML');

		var locale = event.locale;

		descriptionInputLocalized.removeInputLanguage(locale);
		shortDescriptionInputLocalized.removeInputLanguage(locale);
		titleInputLocalized.removeInputLanguage(locale);
	}

	function afterEditingLocaleChange(event) {
		var descriptionInputLocalized = Liferay.component('<portlet:namespace />descriptionMapAsXML');
		var shortDescriptionInputLocalized = Liferay.component('<portlet:namespace />shortDescriptionMapAsXML');
		var titleInputLocalized = Liferay.component('<portlet:namespace />titleMapAsXML');

		var editingLocale = event.newVal;
		var items = descriptionInputLocalized.get('items');
		var selectedIndex = items.indexOf(editingLocale);

		descriptionInputLocalized.set('selected', selectedIndex);
		descriptionInputLocalized.selectFlag(editingLocale);

		shortDescriptionInputLocalized.set('selected', selectedIndex);
		shortDescriptionInputLocalized.selectFlag(editingLocale);

		titleInputLocalized.set('selected', selectedIndex);
		titleInputLocalized.selectFlag(editingLocale);
	}

	var translationManager = Liferay.component('<portlet:namespace />translationManager');

	translationManager.on('deleteAvailableLocale', afterDeletingAvailableLocale)
	translationManager.on('editingLocaleChange', afterEditingLocaleChange)
</aui:script>