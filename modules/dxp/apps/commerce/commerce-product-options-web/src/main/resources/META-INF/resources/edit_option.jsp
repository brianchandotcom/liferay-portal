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
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-product-option-details");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("toolbarItem", toolbarItem);
portletURL.setParameter("mvcRenderCommandName", "editProductOption");

request.setAttribute("view.jsp-portletURL", portletURL);

CPOptionDisplayContext cpOptionDisplayContext = (CPOptionDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPOption cpOption = cpOptionDisplayContext.getCPOption();

long cpOptionId = cpOptionDisplayContext.getCPOptionId();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle((cpOption == null) ? LanguageUtil.get(request, "add-option") : cpOption.getTitle(locale));

String defaultLanguageId = LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault());

Set<Locale> availableLocalesSet = new HashSet<>();

availableLocalesSet.add(LocaleUtil.fromLanguageId(defaultLanguageId));
availableLocalesSet.addAll(cpOptionDisplayContext.getAvailableLocales());

Locale[] availableLocales = availableLocalesSet.toArray(new Locale[availableLocalesSet.size()]);
%>

<%@ include file="/option_navbar.jspf" %>

<portlet:actionURL name="editProductOption" var="editProductOptionActionURL" />

<aui:form action="<%= editProductOptionActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpOption == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="cpOptionId" type="hidden" value="<%= String.valueOf(cpOptionId) %>" />

	<aui:translation-manager
		availableLocales="<%= availableLocales %>"
		defaultLanguageId="<%= defaultLanguageId %>"
		id="translationManager"
	/>

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= backURL %>"
			formModelBean="<%= cpOption %>"
			id="<%= CPOptionFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_OPTION %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>