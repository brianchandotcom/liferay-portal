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

String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-commerce-product-definition-option-rel-details");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("toolbarItem", toolbarItem);
portletURL.setParameter("mvcRenderCommandName", "editProductDefinitionOptionRel");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
renderResponse.setTitle(commerceProductDefinition.getTitle(languageId) + " - " + commerceProductDefinitionOptionRel.getName(locale));
%>

<%@ include file="/commerce_product_definition_option_rel_navbar.jspf" %>

<portlet:actionURL name="editProductDefinitionOptionRel" var="editProductDefinitionOptionRelActionURL" />

<aui:form action="<%= editProductDefinitionOptionRelActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="commerceProductDefinitionId" type="hidden" value="<%= commerceProductDefinitionOptionRel.getCommerceProductDefinitionId() %>" />
	<aui:input name="commerceProductDefinitionOptionRelId" type="hidden" value="<%= String.valueOf(commerceProductDefinitionOptionRelId) %>" />
	<aui:input name="commerceProductOptionId" type="hidden" value="<%= commerceProductDefinitionOptionRel.getCommerceProductOptionId() %>" />

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= backURL %>"
			formModelBean="<%= commerceProductDefinitionOptionRel %>"
			id="<%= CommerceProductDefinitionOptionValueRelFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_DEFINITION_OPTION_VALUE_REL %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>