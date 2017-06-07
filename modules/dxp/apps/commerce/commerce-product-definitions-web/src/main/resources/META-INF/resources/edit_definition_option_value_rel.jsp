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
CPDefinitionOptionValueRelDisplayContext cpDefinitionOptionValueRelDisplayContext = (CPDefinitionOptionValueRelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionOptionValueRelDisplayContext.getCPDefinition();

CPDefinitionOptionRel cpDefinitionOptionRel = cpDefinitionOptionValueRelDisplayContext.getCPDefinitionOptionRel();

long cpDefinitionOptionRelId = cpDefinitionOptionValueRelDisplayContext.getCPDefinitionOptionRelId();

CPDefinitionOptionValueRel cpDefinitionOptionValueRel = cpDefinitionOptionValueRelDisplayContext.getCPDefinitionOptionValueRel();

long cpDefinitionOptionValueRelId = cpDefinitionOptionValueRelDisplayContext.getCPDefinitionOptionValueRelId();

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "editProductDefinitionOptionValueRel");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

String contextTitle = cpDefinition.getTitle(languageId) + " - " + cpDefinitionOptionRel.getTitle(locale);

String title;

if (cpDefinitionOptionValueRel == null) {
	title = LanguageUtil.format(request, "add-option-value-to-x", contextTitle);
}
else {
	title = contextTitle + " - " + cpDefinitionOptionValueRel.getTitle(locale);
}

renderResponse.setTitle(title);
%>

<portlet:actionURL name="editProductDefinitionOptionValueRel" var="editProductDefinitionOptionValueRelActionURL" />

<aui:form action="<%= editProductDefinitionOptionValueRelActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpDefinitionOptionValueRel == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="cpDefinitionOptionRelId" type="hidden" value="<%= String.valueOf(cpDefinitionOptionRelId) %>" />
	<aui:input name="cpDefinitionOptionValueRelId" type="hidden" value="<%= String.valueOf(cpDefinitionOptionValueRelId) %>" />

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= backURL %>"
			formModelBean="<%= cpDefinitionOptionValueRel %>"
			id="<%= CPDefinitionOptionValueRelFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_DEFINITION_OPTION_VALUE_REL %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>