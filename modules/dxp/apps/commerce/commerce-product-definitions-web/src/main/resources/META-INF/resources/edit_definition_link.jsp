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
CPDefinitionLinkDisplayContext cpDefinitionLinkDisplayContext = (CPDefinitionLinkDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionLinkDisplayContext.getCPDefinition();

CPDefinitionLink cpDefinitionLink = cpDefinitionLinkDisplayContext.getCPDefinitionLink();

long cpDefinitionLinkId = cpDefinitionLinkDisplayContext.getCPDefinitionLinkId();

CPDefinition cpDefinition2 = cpDefinitionLink.getCPDefinition2();

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "editCPDefinitionLink");

String screenNavigationCategoryKey = cpDefinitionLinkDisplayContext.getScreenNavigationCategoryKey();

PortletURL relatedProductsURL = renderResponse.createRenderURL();

relatedProductsURL.setParameter("mvcRenderCommandName", "editProductDefinition");
relatedProductsURL.setParameter("cpDefinitionId", String.valueOf(cpDefinition.getCPDefinitionId()));
relatedProductsURL.setParameter("screenNavigationCategoryKey", screenNavigationCategoryKey);
relatedProductsURL.setParameter("type", String.valueOf(cpDefinitionLink.getType()));

String title = cpDefinition2.getName(languageId);

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "products"), catalogURL, data);
PortalUtil.addPortletBreadcrumbEntry(request, cpDefinition.getName(languageId), String.valueOf(cpDefinitionLinkDisplayContext.getEditProductDefinitionURL()), data);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, screenNavigationCategoryKey), relatedProductsURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);
%>

<%@ include file="/breadcrumb.jspf" %>

<portlet:actionURL name="editCPDefinitionLink" var="editCPDefinitionLinkActionURL" />

<aui:form action="<%= editCPDefinitionLinkActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= relatedProductsURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinition.getCPDefinitionId() %>" />
	<aui:input name="cpDefinitionLinkId" type="hidden" value="<%= cpDefinitionLinkId %>" />
	<aui:input name="type" type="hidden" value="<%= cpDefinitionLink.getType() %>" />

	<aui:model-context bean="<%= cpDefinitionLink %>" model="<%= CPDefinitionLink.class %>" />

	<div class="lfr-form-content">
		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input name="priority" />
			</aui:fieldset>

			<c:if test="<%= cpDefinitionLinkDisplayContext.hasCustomAttributesAvailable() %>">
				<aui:fieldset collapsible="<%= true %>" label="custom-attribute">
					<liferay-expando:custom-attribute-list
						className="<%= CPDefinitionLink.class.getName() %>"
						classPK="<%= (cpDefinitionLink != null) ? cpDefinitionLink.getCPDefinitionLinkId() : 0 %>"
						editable="<%= true %>"
						label="<%= true %>"
					/>
				</aui:fieldset>
			</c:if>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button cssClass="btn-lg" name="saveButton" type="submit" value="save" />

			<aui:button cssClass="btn-lg" href="<%= relatedProductsURL.toString() %>" type="cancel" />
		</aui:button-row>
	</div>
</aui:form>