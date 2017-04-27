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
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-product-definition-details");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("toolbarItem", toolbarItem);
portletURL.setParameter("mvcRenderCommandName", "editProductDefinition");

request.setAttribute("view.jsp-portletURL", portletURL);

CPDefinitionsDisplayContext cpDefinitionsDisplayContext = (CPDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionsDisplayContext.getCPDefinition();

long cpDefinitionId = cpDefinitionsDisplayContext.getCPDefinitionId();

PortletURL backUrl = liferayPortletResponse.createRenderURL();

backUrl.setParameter("mvcPath", "/view.jsp");

String backURLString = backUrl.toString();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURLString);

renderResponse.setTitle((cpDefinition == null) ? LanguageUtil.get(request, "add-product") : cpDefinition.getTitle(languageId));

String defaultLanguageId = LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault());

Set<Locale> availableLocalesSet = new HashSet<>();

availableLocalesSet.add(LocaleUtil.fromLanguageId(defaultLanguageId));
availableLocalesSet.addAll(cpDefinitionsDisplayContext.getAvailableLocales());

Locale[] availableLocales = availableLocalesSet.toArray(new Locale[availableLocalesSet.size()]);
%>

<%@ include file="/commerce_product_definition_navbar.jspf" %>

<portlet:actionURL name="editProductDefinition" var="editProductDefinitionActionURL" />

<aui:form action="<%= editProductDefinitionActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURLString %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= String.valueOf(cpDefinitionId) %>" />

	<c:if test="<%= (cpDefinition != null) && !cpDefinition.isNew() %>">
		<liferay-frontend:info-bar>
			<aui:workflow-status
				id="<%= String.valueOf(cpDefinitionId) %>"
				markupView="lexicon"
				showHelpMessage="<%= false %>"
				showIcon="<%= false %>"
				showLabel="<%= false %>"
				status="<%= cpDefinition.getStatus() %>"
			/>
		</liferay-frontend:info-bar>
	</c:if>

	<liferay-frontend:translation-manager
		availableLocales="<%= availableLocales %>"
		componentId='<%= renderResponse.getNamespace() + "translationManager" %>'
		defaultLanguageId="<%= defaultLanguageId %>"
		id="translationManager"
	/>

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= backURLString %>"
			formModelBean="<%= cpDefinition %>"
			id="<%= CPDefinitionFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_DEFINITION %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>