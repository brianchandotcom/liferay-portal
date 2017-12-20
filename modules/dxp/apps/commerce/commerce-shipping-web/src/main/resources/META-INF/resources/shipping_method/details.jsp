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
String redirect = ParamUtil.getString(request, "redirect");

CommerceShippingMethodsDisplayContext commerceShippingMethodsDisplayContext = (CommerceShippingMethodsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShippingMethod commerceShippingMethod = commerceShippingMethodsDisplayContext.getCommerceShippingMethod();

long commerceShippingMethodId = commerceShippingMethod.getCommerceShippingMethodId();

String defaultLanguageId = LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault());

Set<Locale> availableLocalesSet = new HashSet<>();

availableLocalesSet.add(LocaleUtil.fromLanguageId(defaultLanguageId));
availableLocalesSet.addAll(commerceShippingMethodsDisplayContext.getAvailableLocales());

Locale[] availableLocales = availableLocalesSet.toArray(new Locale[availableLocalesSet.size()]);
%>

<portlet:actionURL name="editCommerceShippingMethod" var="editCommerceShippingMethodActionURL" />

<aui:form action="<%= editCommerceShippingMethodActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCommerceShippingMethod();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceShippingMethodId <= 0) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceShippingMethodId" type="hidden" value="<%= commerceShippingMethodId %>" />
	<aui:input name="engineKey" type="hidden" value="<%= commerceShippingMethod.getEngineKey() %>" />

	<liferay-ui:error exception="<%= CommerceShippingMethodNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= commerceShippingMethod %>" model="<%= CommerceShippingMethod.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<liferay-frontend:translation-manager
				availableLocales="<%= availableLocales %>"
				changeableDefaultLanguage="<%= true %>"
				componentId='<%= renderResponse.getNamespace() + "translationManager" %>'
				defaultLanguageId="<%= defaultLanguageId %>"
				id="translationManager"
			/>

			<aui:input autoFocus="<%= true %>" name="name" wrapperCssClass="commerce-shipping-method-name" />

			<aui:input name="description" wrapperCssClass="commerce-shipping-method-description" />

			<%
			String thumbnailSrc = StringPool.BLANK;

			if (commerceShippingMethod != null) {
				thumbnailSrc = commerceShippingMethod.getImageURL(themeDisplay);
			}
			%>

			<c:if test="<%= Validator.isNotNull(thumbnailSrc) %>">
				<div class="row">
					<div class="col-md-4">
						<img class="w-100" src="<%= thumbnailSrc %>" />
					</div>
				</div>
			</c:if>

			<aui:input label="" name="imageFile" type="file" />

			<aui:input name="priority" />

			<aui:input name="active" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base">
	function afterDeletingAvailableLocale(event) {
		var descriptionInputLocalized = Liferay.component('<portlet:namespace />description');
		var nameInputLocalized = Liferay.component('<portlet:namespace />name');

		var locale = event.locale;

		descriptionInputLocalized.removeInputLanguage(locale);
		nameInputLocalized.removeInputLanguage(locale);
	}

	function afterEditingLocaleChange(event) {
		var descriptionInputLocalized = Liferay.component('<portlet:namespace />description');
		var nameInputLocalized = Liferay.component('<portlet:namespace />name');

		var editingLocale = event.newVal;
		var items = descriptionInputLocalized.get('items');
		var selectedIndex = items.indexOf(editingLocale);

		descriptionInputLocalized.set('selected', selectedIndex);
		descriptionInputLocalized.selectFlag(editingLocale);

		nameInputLocalized.set('selected', selectedIndex);
		nameInputLocalized.selectFlag(editingLocale);
	}

	var translationManager = Liferay.component('<portlet:namespace />translationManager');

	if (translationManager) {
		translationManager.on('deleteAvailableLocale', afterDeletingAvailableLocale);
		translationManager.on('editingLocaleChange', afterEditingLocaleChange);
	}
</aui:script>

<aui:script>
	function <portlet:namespace />saveCommerceShippingMethod() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>