<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPConfigurationListDisplayContext cpConfigurationListDisplayContext = (CPConfigurationListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<CommerceCatalog> commerceCatalogs = cpConfigurationListDisplayContext.getCommerceCatalogs();
%>

<aui:form cssClass="container-fluid" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "submitForm();" %>'>
	<div class="lfr-form-content">
		<aui:model-context bean="<%= cpConfigurationListDisplayContext.getCPConfigurationList() %>" model="<%= CPConfigurationList.class %>" />

		<aui:input name="name" required="<%= true %>" type="text" />

		<aui:input name="priority" required="<%= true %>" type="number">
			<aui:validator name="number" />
		</aui:input>

		<aui:select label="catalog" name="commerceCatalogId" required="<%= true %>" showEmptyOption="<%= true %>">

			<%
			for (CommerceCatalog commerceCatalog : commerceCatalogs) {
			%>

				<aui:option label="<%= commerceCatalog.getName() %>" value="<%= commerceCatalog.getCommerceCatalogId() %>" />

			<%
			}
			%>

		</aui:select>

		<label class="control-label" for="parentCPConfigurationId"><liferay-ui:message key="parent-configuration" /></label>

		<div id="autocomplete-root"></div>

		<aui:button cssClass="hide" id="saveButton" type="submit" />
	</div>
</aui:form>

<portlet:renderURL var="editProductDefinitionURL">
	<portlet:param name="mvcRenderCommandName" value="/cp_definitions/edit_cp_definition" />
</portlet:renderURL>

<liferay-frontend:component
	context="<%= cpConfigurationListDisplayContext.getContext() %>"
	module="{CPConfigurationListAutocomplete} from commerce-product-definitions-web"
/>