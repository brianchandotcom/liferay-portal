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
CommerceProductDefinitionOptionValueRelDisplayContext commerceProductDefinitionOptionValueRelDisplayContext = (CommerceProductDefinitionOptionValueRelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceProductDefinition commerceProductDefinition = commerceProductDefinitionOptionValueRelDisplayContext.getCommerceProductDefinition();

CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel = commerceProductDefinitionOptionValueRelDisplayContext.getCommerceProductDefinitionOptionRel();

long commerceProductDefinitionOptionRelId = commerceProductDefinitionOptionValueRelDisplayContext.getCommerceProductDefinitionOptionRelId();

CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel = commerceProductDefinitionOptionValueRelDisplayContext.getCommerceProductDefinitionOptionValueRel();

long commerceProductDefinitionOptionValueRelId = commerceProductDefinitionOptionValueRelDisplayContext.getCommerceProductDefinitionOptionValueRelId();

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "editProductDefinitionOptionValueRel");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

String contextTitle = commerceProductDefinition.getTitle(languageId) + " - " + commerceProductDefinitionOptionRel.getName(locale);

String title;

if (commerceProductDefinitionOptionValueRel == null) {
	title = LanguageUtil.get(request, "add-product-definition-option-value-rel-to-x", contextTitle);
}
else {
	title = contextTitle + " - " + commerceProductDefinitionOptionValueRel.getTitle(locale);
}

renderResponse.setTitle(title);
%>

<portlet:actionURL name="editProductDefinitionOptionValueRel" var="editProductDefinitionOptionValueRelActionURL" />

<aui:form action="<%= editProductDefinitionOptionValueRelActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="commerceProductDefinitionOptionRelId" type="hidden" value="<%= String.valueOf(commerceProductDefinitionOptionRelId) %>" />
	<aui:input name="commerceProductDefinitionOptionValueRelId" type="hidden" value="<%= String.valueOf(commerceProductDefinitionOptionValueRelId) %>" />

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= backURL %>"
			formModelBean="<%= commerceProductDefinitionOptionRel %>"
			id="<%= CommerceProductDefinitionOptionValueRelFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_DEFINITION_OPTION_VALUE_REL %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>