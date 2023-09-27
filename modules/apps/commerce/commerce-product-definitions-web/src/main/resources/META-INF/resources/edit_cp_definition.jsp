<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPDefinitionsDisplayContext cpDefinitionsDisplayContext = (CPDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionsDisplayContext.getCPDefinition();
CProduct cProduct = cpDefinitionsDisplayContext.getCProduct();
PortletURL portletURL = cpDefinitionsDisplayContext.getEditProductDefinitionURL();

String headerTitle = LanguageUtil.get(request, "add-product");

if (cpDefinition != null) {
	headerTitle = cpDefinition.getName(languageId);
}

request.setAttribute("view.jsp-cpDefinition", cpDefinition);
request.setAttribute("view.jsp-cpType", cpDefinitionsDisplayContext.getCPType());
request.setAttribute("view.jsp-portletURL", portletURL);
request.setAttribute("view.jsp-showSearch", false);

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
}
else {
	portletDisplay.setURLBack(redirect);
}
%>

<liferay-portlet:renderURL var="editCProductExternalReferenceCodeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/cp_definitions/edit_c_product_external_reference_code" />
	<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionsDisplayContext.getCPDefinitionId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:header
	actions="<%= cpDefinitionsDisplayContext.getHeaderActionModels() %>"
	bean="<%= cpDefinition %>"
	beanIdLabel="id"
	dropdownItems="<%= cpDefinitionsDisplayContext.getDropdownItems() %>"
	externalReferenceCode="<%= (cProduct == null) ? StringPool.BLANK : cProduct.getExternalReferenceCode() %>"
	externalReferenceCodeEditUrl="<%= (cProduct == null) ? StringPool.BLANK : editCProductExternalReferenceCodeURL %>"
	model="<%= CPDefinition.class %>"
	thumbnailUrl="<%= cpDefinitionsDisplayContext.getCPDefinitionThumbnailURL() %>"
	title="<%= headerTitle %>"
	version="<%= (cpDefinition == null) ? StringPool.BLANK : String.valueOf(cpDefinition.getVersion()) %>"
	wrapperCssClasses="side-panel-top-anchor"
/>

<liferay-frontend:screen-navigation
	containerWrapperCssClass="container"
	key="<%= CPDefinitionScreenNavigationConstants.SCREEN_NAVIGATION_KEY_CP_DEFINITION_GENERAL %>"
	modelBean="<%= cpDefinition %>"
	portletURL="<%= currentURLObj %>"
/>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"message", LanguageUtil.get(request, "there-is-already-a-draft-version-of-this-product.-continuing-will-replace-that-draft-version-with-this-draft-version.-do-you-wish-to-proceed")
		).put(
			"showConfirmationMessage", cpDefinitionsDisplayContext.showConfirmationMessage(cpDefinition)
		).put(
			"title", LanguageUtil.get(request, "save-as-draft")
		).put(
			"WORKFLOW_ACTION_PUBLISH", WorkflowConstants.ACTION_PUBLISH
		).build()
	%>'
	module="js/edit_cp_definition"
/>