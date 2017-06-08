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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CPDefinitionOptionRel cpDefinitionOptionRel = null;

if (row != null) {
	cpDefinitionOptionRel = (CPDefinitionOptionRel)row.getObject();
}
else {
	cpDefinitionOptionRel = (CPDefinitionOptionRel)request.getAttribute("info_panel.jsp-entry");
}
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<portlet:renderURL var="editURL">
		<portlet:param name="mvcRenderCommandName" value="editProductDefinitionOptionRel" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="backURL" value="<%= backURL %>" />
		<portlet:param name="cpDefinitionOptionRelId" value="<%= String.valueOf(cpDefinitionOptionRel.getCPDefinitionOptionRelId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editURL %>"
	/>

	<portlet:actionURL name="editProductDefinitionOptionRel" var="setFacetableURL">
		<portlet:param name="<%= Constants.CMD %>" value="SET_FACETABLE" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="backURL" value="<%= backURL %>" />
		<portlet:param name="cpDefinitionOptionRelId" value="<%= String.valueOf(cpDefinitionOptionRel.getCPDefinitionOptionRelId()) %>" />
		<portlet:param name="facetable" value="<%= (!cpDefinitionOptionRel.getFacetable()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message='<%= (cpDefinitionOptionRel.getFacetable()) ? LanguageUtil.get(request, "unset-facetable") : LanguageUtil.get(request, "set-facetable") %>'
		url="<%= setFacetableURL %>"
	/>

	<portlet:actionURL name="editProductDefinitionOptionRel" var="setRequiredURL">
		<portlet:param name="<%= Constants.CMD %>" value="SET_REQUIRED" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="backURL" value="<%= backURL %>" />
		<portlet:param name="cpDefinitionOptionRelId" value="<%= String.valueOf(cpDefinitionOptionRel.getCPDefinitionOptionRelId()) %>" />
		<portlet:param name="required" value="<%= (!cpDefinitionOptionRel.getRequired()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message='<%= (cpDefinitionOptionRel.getRequired()) ? LanguageUtil.get(request, "unset-required") : LanguageUtil.get(request, "set-required") %>'
		url="<%= setRequiredURL %>"
	/>

	<portlet:actionURL name="editProductDefinitionOptionRel" var="setSkuContributorURL">
		<portlet:param name="<%= Constants.CMD %>" value="SET_SKU_CONTRIBUTOR" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="backURL" value="<%= backURL %>" />
		<portlet:param name="cpDefinitionOptionRelId" value="<%= String.valueOf(cpDefinitionOptionRel.getCPDefinitionOptionRelId()) %>" />
		<portlet:param name="skuContributor" value="<%= (!cpDefinitionOptionRel.getSkuContributor()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message='<%= (cpDefinitionOptionRel.getSkuContributor()) ? LanguageUtil.get(request, "unset-sku-contributor") : LanguageUtil.get(request, "set-sku-contributor") %>'
		url="<%= setSkuContributorURL %>"
	/>

	<portlet:actionURL name="editProductDefinitionOptionRel" var="deleteURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="backURL" value="<%= backURL %>" />
		<portlet:param name="cpDefinitionOptionRelId" value="<%= String.valueOf(cpDefinitionOptionRel.getCPDefinitionOptionRelId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>