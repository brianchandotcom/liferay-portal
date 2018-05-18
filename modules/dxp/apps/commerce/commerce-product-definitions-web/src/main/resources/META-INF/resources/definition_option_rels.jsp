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
CPDefinitionOptionRelDisplayContext cpDefinitionOptionRelDisplayContext = (CPDefinitionOptionRelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionOptionRelDisplayContext.getCPDefinition();

long cpDefinitionId = cpDefinitionOptionRelDisplayContext.getCPDefinitionId();
%>

<portlet:resourceURL id="cpDefinitionOptionRels" var="cpDefinitionOptionsURL">
</portlet:resourceURL>

<liferay-portlet:renderURL var="cpDefinitionOptionRelURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="editProductDefinitionOptionRel" />
</liferay-portlet:renderURL>

<portlet:resourceURL id="cpDefinitionOptionValueRels" var="cpDefinitionOptionValueRelsURL">
</portlet:resourceURL>

<portlet:actionURL name="editProductDefinitionOptionRel" var="editProductDefinitionOptionRelURL" />

<liferay-portlet:renderURL var="cpDefinitionOptionValueRelURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="editCPDefinitionOptionValueRel" />
</liferay-portlet:renderURL>

<%
Map<String, Object> context = new HashMap<>();

context.put("cpDefinitionId", cpDefinitionId);
context.put("cpDefinitionOptionsURL", cpDefinitionOptionsURL);
context.put("cpDefinitionOptionValueRelsURL", cpDefinitionOptionValueRelsURL);
context.put("cpDefinitionOptionValueRelURL", cpDefinitionOptionValueRelURL);
context.put("editProductDefinitionOptionRelURL", editProductDefinitionOptionRelURL);
context.put("id", "CPDefinitionOptionsEditor");
context.put("namespace", liferayPortletResponse.getNamespace());
context.put("optionsItemSelectorURL", cpDefinitionOptionRelDisplayContext.getItemSelectorUrl());
context.put("optionURL", cpDefinitionOptionRelURL);
context.put("pathThemeImages", themeDisplay.getPathThemeImages());
%>

<div class="container-fluid-1280" id="<portlet:namespace />CPOptionsEditor">
	<soy:template-renderer
		context="<%= context %>"
		module="commerce-product-definitions-web/definition_option_rel/CPDefinitionOptionsEditor.es"
		templateNamespace="CPDefinitionOptionsEditor.render"
	/>
</div>