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
%>

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "duplicate-product") %>'
>
	<aui:form cssClass="container-fluid container-fluid-max-xl p-0" method="post" name="duplicatefm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit(this.form);" %>'>
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<label class="control-label" for="catalogId"><liferay-ui:message key="catalog" /></label>

		<div id="autocomplete-root"></div>
	</aui:form>

	<portlet:renderURL var="editProductDefinitionURL">
		<portlet:param name="mvcRenderCommandName" value="/cp_definitions/edit_cp_definition" />
	</portlet:renderURL>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"editProductDefinitionURL", editProductDefinitionURL
			).put(
				"namespace", liferayPortletResponse.getNamespace()
			).put(
				"namespace", liferayPortletResponse.getNamespace()
			).put(
				"ppState", LiferayWindowState.MAXIMIZED.toString()
			).put(
				"productId", cpDefinition.getCProductId()
			).put(
				"productStatus", WorkflowConstants.STATUS_DRAFT
			).put(
				"productType", cpDefinition.getProductTypeName()
			).build()
		%>'
		module="{duplicateCpDefinitionAutocomplete} from commerce-product-definitions-web"
	/>
</commerce-ui:modal-content>