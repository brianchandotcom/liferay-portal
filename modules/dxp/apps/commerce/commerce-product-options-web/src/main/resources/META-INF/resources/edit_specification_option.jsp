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
CPSpecificationOptionDisplayContext cpSpecificationOptionDisplayContext = (CPSpecificationOptionDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPSpecificationOption cpSpecificationOption = (CPSpecificationOption)request.getAttribute(CPWebKeys.CP_SPECIFICATION_OPTION);

long cpSpecificationOptionId = BeanParamUtil.getLong(cpSpecificationOption, request, "cpSpecificationOptionId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(specificationOptionsURL);

renderResponse.setTitle((cpSpecificationOption == null) ? LanguageUtil.get(request, "add-specification-option") : cpSpecificationOption.getTitle(locale));

String defaultLanguageId = LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault());

Set<Locale> availableLocalesSet = new HashSet<>();

availableLocalesSet.add(LocaleUtil.fromLanguageId(defaultLanguageId));
availableLocalesSet.addAll(cpSpecificationOptionDisplayContext.getAvailableLocales());

Locale[] availableLocales = availableLocalesSet.toArray(new Locale[availableLocalesSet.size()]);
%>

<portlet:actionURL name="editProductSpecificationOption" var="editProductSpecificationOptionActionURL" />

<aui:form action="<%= editProductSpecificationOptionActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpSpecificationOption == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= specificationOptionsURL %>" />
	<aui:input name="cpSpecificationOptionId" type="hidden" value="<%= String.valueOf(cpSpecificationOptionId) %>" />

	<aui:translation-manager
		availableLocales="<%= availableLocales %>"
		defaultLanguageId="<%= defaultLanguageId %>"
		id="translationManager"
	/>

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= specificationOptionsURL %>"
			formModelBean="<%= cpSpecificationOption %>"
			id="<%= CPSpecificationOptionFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_SPECIFICATION_OPTION %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>