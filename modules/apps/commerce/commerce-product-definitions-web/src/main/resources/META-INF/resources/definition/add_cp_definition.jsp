<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "create-new-product") %>'
>
	<aui:form cssClass="container-fluid container-fluid-max-xl" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit();" %>'>
		<aui:input name="name" required="<%= true %>" type="text" />

		<label class="control-label" for="catalogId"><liferay-ui:message key="catalog" /></label>

		<div id="autocomplete-root"></div>
	</aui:form>

	<portlet:renderURL var="editProductDefinitionURL">
		<portlet:param name="mvcRenderCommandName" value="/cp_definitions/edit_cp_definition" />
	</portlet:renderURL>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"defaultSku", CPInstanceConstants.DEFAULT_SKU
			).put(
				"draft", WorkflowConstants.STATUS_DRAFT
			).put(
				"editProductDefinitionURL", editProductDefinitionURL
			).put(
				"namespace", liferayPortletResponse.getNamespace()
			).put(
				"ppState", LiferayWindowState.MAXIMIZED.toString()
			).put(
				"productTypeName", ParamUtil.getString(request, "productTypeName")
			).build()
		%>'
		module="{addCpDefinition} from commerce-product-definitions-web"
	/>
</commerce-ui:modal-content>