<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "products"), catalogURL, data);
PortalUtil.addPortletBreadcrumbEntry(request, cpDefinition.getName(languageId), String.valueOf(cpDefinitionSpecificationOptionValueDisplayContext.getEditProductDefinitionURL()), data);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, screenNavigationCategoryKey), productSpecificationOptionValueURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= CPNavigationItemRegistryUtil.getNavigationItems(renderRequest) %>"
/>

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