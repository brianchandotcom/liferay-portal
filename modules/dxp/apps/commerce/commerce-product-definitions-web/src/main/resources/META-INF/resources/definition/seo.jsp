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

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="seo" />

<aui:model-context bean="<%= cpDefinition %>" model="<%= CPDefinition.class %>" />

<aui:fieldset>
	<div class="commerce-product-definition-url-title form-group">
		<label for="<portlet:namespace />friendlyURL"><liferay-ui:message key="friendly-url" /> <liferay-ui:icon-help message='<%= LanguageUtil.format(request, "for-example-x", "<em>/news</em>", false) %>' /></label>

		<div class="input-group lfr-friendly-url-input-group">
			<span class="input-group-addon" id="<portlet:namespace />urlBase">
				<span class="input-group-constrain"><liferay-ui:message key="<%= StringUtil.shorten(friendlyURLBase.toString(), 40) %>" /></span>
			</span>

			<liferay-ui:input-localized cssClass="form-control" defaultLanguageId="<%= LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()) %>" name="urlTitleMapAsXML" xml="<%= HttpUtil.decodeURL(cpDefinitionsDisplayContext.getUrlTitleMapAsXML()) %>" />

			<c:if test="<%= cpDefinition != null %>">

				<%
				Map<Locale, String> urlTitleMap = cpDefinition.getUrlTitleMap();

				String productURL = friendlyURLBase + urlTitleMap.get(themeDisplay.getSiteDefaultLocale());
				%>

				<span class="input-group-addon" id="<portlet:namespace />urlIcon">
					<liferay-ui:icon
						iconCssClass="icon-new-window"
						label="<%= false %>"
						message="go-to-page"
						target="_blank"
						url="<%= productURL %>"
					/>
				</span>
			</c:if>
		</div>
	</div>

	<aui:input label="meta-title" localized="<%= true %>" name="metaTitleMapAsXML" type="text" wrapperCssClass="commerce-product-definition-meta-title" />

	<aui:input label="meta-keywords" localized="<%= true %>" name="metaKeywordsMapAsXML" type="textarea" wrapperCssClass="commerce-product-definition-meta-keywords" />

	<aui:input label="meta-description" localized="<%= true %>" name="metaDescriptionMapAsXML" type="textarea" wrapperCssClass="commerce-product-definition-meta-description" />
</aui:fieldset>

<aui:script use="aui-base">
	function afterDeletingAvailableLocale(event) {
		var metaDescriptionInputLocalized = Liferay.component('<portlet:namespace />metaDescriptionMapAsXML');
		var metaKeywordsInputLocalized = Liferay.component('<portlet:namespace />metaKeywordsMapAsXML');
		var metaTitleInputLocalized = Liferay.component('<portlet:namespace />metaTitleMapAsXML');
		var urlTitleInputLocalized = Liferay.component('<portlet:namespace />urlTitleMapAsXML');

		var locale = event.locale;

		metaDescriptionInputLocalized.removeInputLanguage(locale);
		metaKeywordsInputLocalized.removeInputLanguage(locale);
		metaTitleInputLocalized.removeInputLanguage(locale);
		urlTitleInputLocalized.removeInputLanguage(locale);
	}

	function afterEditingLocaleChange(event) {
		var metaDescriptionInputLocalized = Liferay.component('<portlet:namespace />metaDescriptionMapAsXML');
		var metaKeywordsInputLocalized = Liferay.component('<portlet:namespace />metaKeywordsMapAsXML');
		var metaTitleInputLocalized = Liferay.component('<portlet:namespace />metaTitleMapAsXML');
		var urlTitleInputLocalized = Liferay.component('<portlet:namespace />urlTitleMapAsXML');

		var editingLocale = event.newVal;
		var items = metaDescriptionInputLocalized.get('items');
		var selectedIndex = items.indexOf(editingLocale);

		metaDescriptionInputLocalized.set('selected', selectedIndex);
		metaDescriptionInputLocalized.selectFlag(editingLocale);

		metaKeywordsInputLocalized.set('selected', selectedIndex);
		metaKeywordsInputLocalized.selectFlag(editingLocale);

		metaTitleInputLocalized.set('selected', selectedIndex);
		metaTitleInputLocalized.selectFlag(editingLocale);

		urlTitleInputLocalized.set('selected', selectedIndex);
		urlTitleInputLocalized.selectFlag(editingLocale);
	}

	var translationManager = Liferay.component('<portlet:namespace />translationManager');

	translationManager.on('deleteAvailableLocale', afterDeletingAvailableLocale)
	translationManager.on('editingLocaleChange', afterEditingLocaleChange)
</aui:script>