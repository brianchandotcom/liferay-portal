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
String toolbarItem = ParamUtil.getString(request, "toolbarItem");

CPDefinitionLinkDisplayContext cpDefinitionLinkDisplayContext = (CPDefinitionLinkDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionLinkDisplayContext.getCPDefinition();

CPDefinitionLink cpDefinitionLink = cpDefinitionLinkDisplayContext.getCPDefinitionLink();

long cpDefinitionLinkId = cpDefinitionLinkDisplayContext.getCPDefinitionLinkId();

CPDefinition cpDefinition2 = cpDefinitionLink.getCPDefinition2();

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "editCPDefinitionLink");

PortletURL relatedProductsURL = renderResponse.createRenderURL();

relatedProductsURL.setParameter("mvcRenderCommandName", "viewCPDefinitionLinks");
relatedProductsURL.setParameter("cpDefinitionId", String.valueOf(cpDefinition.getCPDefinitionId()));
relatedProductsURL.setParameter("toolbarItem", toolbarItem);
relatedProductsURL.setParameter("type", String.valueOf(cpDefinitionLink.getType()));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(relatedProductsURL.toString());

renderResponse.setTitle(cpDefinition.getTitle(languageId) + " - " + cpDefinition2.getTitle(languageId));
%>

<portlet:actionURL name="editCPDefinitionLink" var="editCPDefinitionLinkActionURL" />

<aui:form action="<%= editCPDefinitionLinkActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= relatedProductsURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinition.getCPDefinitionId() %>" />
	<aui:input name="cpDefinitionLinkId" type="hidden" value="<%= cpDefinitionLinkId %>" />

	<aui:model-context bean="<%= cpDefinitionLink %>" model="<%= CPDefinitionLink.class %>" />

	<div class="lfr-form-content">
		<aui:fieldset>
			<aui:input name="priority" />
		</aui:fieldset>

		<aui:button-row>
			<aui:button cssClass="btn-lg" name="saveButton" type="submit" value="save" />

			<aui:button cssClass="btn-lg" href="<%= relatedProductsURL.toString() %>" type="cancel" />
		</aui:button-row>
	</div>
</aui:form>