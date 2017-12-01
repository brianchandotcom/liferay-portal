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
CPDefinitionSpecificationOptionValueDisplayContext cpDefinitionSpecificationOptionValueDisplayContext = (CPDefinitionSpecificationOptionValueDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue = cpDefinitionSpecificationOptionValueDisplayContext.getCPDefinitionSpecificationOptionValue();

CPSpecificationOption cpSpecificationOption = cpDefinitionSpecificationOptionValue.getCPSpecificationOption();

CPDefinition cpDefinition = cpDefinitionSpecificationOptionValueDisplayContext.getCPDefinition();

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "editProductDefinitionSpecificationOptionValue");

String screenNavigationCategoryKey = cpDefinitionSpecificationOptionValueDisplayContext.getScreenNavigationCategoryKey();

PortletURL productSpecificationOptionValueURL = renderResponse.createRenderURL();

productSpecificationOptionValueURL.setParameter("mvcRenderCommandName", "editProductDefinition");
productSpecificationOptionValueURL.setParameter("cpDefinitionId", String.valueOf(cpDefinitionSpecificationOptionValue.getCPDefinitionId()));
productSpecificationOptionValueURL.setParameter("screenNavigationCategoryKey", screenNavigationCategoryKey);

String title = cpSpecificationOption.getTitle(locale);

Map<String, Object> data = new HashMap<>();

data.put("direction-right", Boolean.TRUE.toString());

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "products"), catalogURL, data);
PortalUtil.addPortletBreadcrumbEntry(request, cpDefinition.getTitle(languageId), String.valueOf(cpDefinitionSpecificationOptionValueDisplayContext.getEditProductDefinitionURL()), data);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, screenNavigationCategoryKey), productSpecificationOptionValueURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);
%>

<%@ include file="/breadcrumb.jspf" %>

<portlet:actionURL name="editProductDefinitionSpecificationOptionValue" var="editProductDefinitionSpecificationOptionValueActionURL" />

<aui:form action="<%= editProductDefinitionSpecificationOptionValueActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= productSpecificationOptionValueURL %>" />
	<aui:input name="cpDefinitionSpecificationOptionValueId" type="hidden" value="<%= String.valueOf(cpDefinitionSpecificationOptionValue.getCPDefinitionSpecificationOptionValueId()) %>" />

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= productSpecificationOptionValueURL.toString() %>"
			formModelBean="<%= cpDefinitionSpecificationOptionValue %>"
			id="<%= CPDefinitionSpecificationOptionValueFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_DEFINITION_SPECIFICATION_OPTION_VALUE %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>